package com.zwm.service;

import com.zwm.entity.User;

import java.util.Map;

public interface UserService {
    public abstract User findUserById(int id);

    //前端传递注册数据过来，后端接收
    public abstract Map<String, String> register(User user);
}
