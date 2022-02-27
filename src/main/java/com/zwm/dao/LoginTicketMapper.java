package com.zwm.dao;

import com.zwm.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {
    //插入登录凭证
    @Insert({
            "insert into login_ticket(user_id, ticket, status, expired) values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public abstract int insertLoginTicket(LoginTicket loginTicket);

    //查询登陆凭证
    @Select({
            "select id, user_id, ticket, status, expired from login_ticket where ticket = #{ticket}"
    })
    public abstract LoginTicket selectLoginTicketByTicket(String ticket);


    //更改登陆凭证状态
    @Update({
            "update login_ticket set status = #{status} where ticket = #{ticket}"
    })
    public abstract int updateStatusByLoginTicket(String ticket, int status);
}
