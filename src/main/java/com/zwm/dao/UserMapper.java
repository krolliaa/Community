package com.zwm.dao;

import com.zwm.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //根据 Id 查询用户
    public abstract User selectUserById(int id);

    //根据姓名查询用户
    public abstract User selectUserByName(String username);

    //根据邮箱查询用户
    public abstract User selectUserByEmail(String email);

    //插入用户
    public abstract int insertUser(User user);

    //更新用户状态
    public abstract int updateUserStatus(int id, int status);

    //更新用户头像
    public abstract int updateUserHeader(int id, String headUrl);

    //更新用户密码
    public abstract int updateUserPassword(int id, String password);
}
