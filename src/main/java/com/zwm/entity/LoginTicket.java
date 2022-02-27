package com.zwm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTicket {
    private int id;
    private int userId;//用户id
    private String ticket;//凭证
    private int status;//状态：0-无效 1-有效
    private Date expired;//过期时间

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", user_id=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }
}
