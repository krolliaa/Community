package com.zwm.service.impl;

import com.zwm.dao.UserMapper;
import com.zwm.entity.User;
import com.zwm.service.UserService;
import com.zwm.util.CommunityConstant;
import com.zwm.util.CommunityUtils;
import com.zwm.util.MailServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    @Override
    public User findUserById(int id) {
        System.out.println("111");
        return userMapper.selectUserById(id);
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
            if(result == 1) return ACTIVATION_SUCCESS;
            else return ACTIVATION_FAILURE;
        } else {
            return ACTIVATION_FAILURE;
        }
    }
}