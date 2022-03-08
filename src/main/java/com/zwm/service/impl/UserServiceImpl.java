package com.zwm.service.impl;

import com.zwm.dao.LoginTicketMapper;
import com.zwm.dao.UserMapper;
import com.zwm.entity.LoginTicket;
import com.zwm.entity.User;
import com.zwm.service.UserService;
import com.zwm.util.CommunityConstant;
import com.zwm.util.CommunityUtils;
import com.zwm.util.MailServer;
import com.zwm.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.zwm.util.CommunityConstant.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Value("${community.path.domain}")
    private String domain;


    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private MailServer mailServer;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User findUserById(int id) {
        //return userMapper.selectUserById(id);
        return getUserCache(id);
    }

    @Override
    public Map<String, String> register(User user) {
        Map<String, String> map = new HashMap<>();
        if (user == null) throw new IllegalArgumentException("参数不能为空");
        //获取前端传递的用户名、密码和邮箱
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        //map集合用于放置错误信息
        //用户名不能为空否则报错
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "用户名不能为空");
            return map;
        }
        //密码不能为空否则报错
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        //邮箱不能为空否则报错
        if (StringUtils.isBlank(email)) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }
        //验证账号，用户名不能是使用过的
        User selectUser = userMapper.selectUserByName(username);
        if (selectUser != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }
        selectUser = userMapper.selectUserByEmail(email);
        if (selectUser != null) {
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }
        //到这里都没有问题的话，表示可以正常创建用户了
        //User: id username password email headerUrl status type salt activeCode createTime
        //id 会自动生成，因为在 application.properties 添加了：mybatis.configuration.use-generated-keys=true 配置
        //username password email 都有了，但是这里 password 要存储到数据库中需要加盐加密
        //生成随机盐然后跟密码合并进行MD5加密 ---> 存储盐 存储密码
        String salt = CommunityUtils.generateUUID().substring(0, 5);
        user.setSalt(salt);
        user.setPassword(CommunityUtils.md5(user.getPassword() + user.getSalt()));
        //以当前时间为基准作为创建时间
        user.setCreateTime(new Date());
        //牛客网自带随机头像可以直接拿来使用，只需要换数字即可
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        //设置用户 type 类型：0-普通 1-管理员 2-版主 ---> 设置为普通用户
        user.setType(0);
        //设置用户 status 状态：0-未激活 1-激活 ---> 设置为未激活用户
        user.setStatus(0);
        //设置激活码，发送激活邮件，这里需要获取到当前网站的域名，已经写在了 application.properties 里头
        //community.path.domain=http://localhost:8080
        user.setActivationCode(CommunityUtils.generateUUID());
        //向数据库中插入用户数据
        userMapper.insertUser(user);

        //发送激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        //http:localhost:8080/community/activation/{id}/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailServer.sendMessage(user.getEmail(), "激活账号", content);
        return map;
    }

    @Override
    public CommunityConstant activation(int id, String activationCode) {
        User user = userMapper.selectUserById(id);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getStatus() == 0 && user.getActivationCode().equals(activationCode)) {
            //只有状态为 0 即未激活并且激活码跟链接相同才准许激活
            int result = userMapper.updateUserStatus(id);
            if (result == 1) return ACTIVATION_SUCCESS;
            else return ACTIVATION_FAILURE;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    //用户登录
    @Override
    public Map<String, String> login(String username, String password, int expiredSeconds) {
        //查询用户信息[用户名是唯一的] ---> 有错误信息存放到 map
        Map<String, String> map = new HashMap<>();
        //验证用户名是否为空
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空！");
        }
        //验证密码是否为空
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        User user = userMapper.selectUserByName(username);
        //如果 user 查询没有直接返回
        if (user == null) {
            map.put("usernameMsg", "该账号不存在!");
            return map;
        }
        //未激活账号无法使用 0-未激活 1-已激活
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "未激活账号无法使用！");
            return map;
        }
        //验证密码是否正确
        if (user.getPassword().equals(CommunityUtils.md5(password + user.getSalt()))) {
            map.put("passwordMsg", "密码不正确！");
            return map;
        }
        //前面都顺利执行过来表示用户可以正常登录使用，制作登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtils.generateUUID());
        loginTicket.setStatus(0);
        //生存时间根据是否记住决定 ---> 这里由 LoginController 完成传递过来，Service 这边直接赋值即可
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        //创建登陆凭证记录存储到数据库中
        //loginTicketMapper.insertLoginTicket(loginTicket);

        //获取登录凭证的 Key
        String ticketKey = RedisKeyUtil.getLoginTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(ticketKey, loginTicket);


        //前端 cookie 需要保存登录凭证，所以需要使用 map 传递回去
        map.put("loginTicket", loginTicket.getTicket());
        return map;
    }

    //用户登出 ---> 设置当前票据的状态为无效 0-有效 1-无效
    public void logout(String ticket) {
        //loginTicketMapper.updateStatusByLoginTicket(ticket, 1);
        String ticketKey = RedisKeyUtil.getLoginTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(ticketKey, loginTicket);
    }

    //查询用户登录凭证
    public LoginTicket selectLoginTicketByTicket(String ticket) {
        String loginTicketKey = RedisKeyUtil.getLoginTicketKey(ticket);
        //return loginTicketMapper.selectLoginTicketByTicket(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(loginTicketKey);
    }

    @Override
    //更新用户头像路径
    public int updateUserHeaderUrl(int id, String headerUrl) {
        return userMapper.updateUserHeader(id, headerUrl);
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByName(username);
    }

    //获取缓存中的用户数据
    public User getUserCache(int userId) {
        String userKey = RedisKeyUtil.getUserKey(userId);
        //如果 userKey 从 Redis 搜出来的用户为空表示没有缓存，需从数据库中拿过来缓存
        User user = (User) redisTemplate.opsForValue().get(userKey);
        if (user == null) {
            user = userMapper.selectUserById(userId);
            redisTemplate.opsForValue().set(userKey, user, 3600, TimeUnit.SECONDS);
        }
        return user;
    }

    //当 User 舒心更改时清楚缓存
    public void deleteUserCache(int userId) {
        String userKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(userKey);
    }
}