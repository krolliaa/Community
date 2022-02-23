package com.zwm.service;

import com.zwm.entity.User;
import com.zwm.util.CommunityConstant;

import java.util.Map;

public interface UserService {
    public abstract User findUserById(int id);

    //前端传递注册数据过来，后端接收
    public abstract Map<String, String> register(User user);

    //查询激活码与账户 ID 是否正确
    public abstract CommunityConstant activation(int id, String activationCode);
}
