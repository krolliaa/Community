package com.zwm.service.impl;

import com.zwm.dao.UserMapper;
import com.zwm.entity.User;
import com.zwm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(int id) {
        System.out.println("111");
        return userMapper.selectUserById(id);
    }
}
