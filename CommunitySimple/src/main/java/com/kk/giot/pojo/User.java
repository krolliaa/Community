package com.kk.giot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;//主键
    private String username;//用户名
    private String password;//密码
    private String salt;//加密盐值
    private String email;//邮箱地址
    private String activationCode;//激活码
    private Date createTime;//注册时间
    private int type;//用户类型 0-普通 1-管理员 2-版主
    private int status;//用户状态 0-已激活 1-未激活
    private String headerUrl;//头像Url地址
}
