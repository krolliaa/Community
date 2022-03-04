package com.zwm.service;

import com.zwm.entity.LoginTicket;
import com.zwm.entity.User;
import com.zwm.util.CommunityConstant;

import java.util.Map;

public interface UserService {
    public abstract User findUserById(int id);

    //前端传递注册数据过来，后端接收
    public abstract Map<String, String> register(User user);

    //查询激活码与账户 ID 是否正确
    public abstract CommunityConstant activation(int id, String activationCode);

    //查询匹配的用户名和密码并且设置生存时间【根据用户是否选择记住我来决定生存时间的长短】
    public abstract Map<String, String> login(String username, String password, int expiredSeconds);

    //退出登录
    public abstract void logout(String ticket);

    //查询用户凭证
    public abstract LoginTicket selectLoginTicketByTicket(String ticket);

    //更新用户头像路径
    public abstract int updateUserHeaderUrl(int id, String headerUrl);

    //根据用户名查找用户
    public abstract User selectUserByUsername(String username);

}
