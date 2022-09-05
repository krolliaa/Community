package com.kk.giot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.giot.pojo.User;

import java.util.Map;

public interface UserService extends IService<User> {
    public abstract Map<String, String> register(User user);
}
