package com.kk.giot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.giot.mapper.UserMapper;
import com.kk.giot.pojo.User;
import com.kk.giot.service.UserService;
import com.kk.giot.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> register(User user) {
        //如果传递进来的 user 为 null 则抛异常
        if (user == null) throw new IllegalArgumentException("参数不能为空");
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        Map<String, String> map = new HashMap<>();
        //字段不能为空
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(email)) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }
        //用户名、邮箱账号不能重复
        User selectUserByName = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (selectUserByName != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }
        User selectUserByEmail = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (selectUserByEmail != null) {
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }
        String salt = MyUtils.generateUUID().substring(0, 5);
        password = password + salt;
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setCreateTime(new Date());
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setType(0);
        user.setStatus(1);
        user.setActivationCode(MyUtils.generateUUID());
        userMapper.insert(user);
        //发送邮件暂时不做
        return map;
    }
}