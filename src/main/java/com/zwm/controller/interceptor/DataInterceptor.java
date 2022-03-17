package com.zwm.controller.interceptor;

import com.zwm.entity.User;
import com.zwm.service.impl.DataServiceImpl;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DataInterceptor implements HandlerInterceptor {

    @Autowired
    private DataServiceImpl dataService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取 IP 地址
        String ip = request.getRemoteHost();
        //记录IP独立访客数据
        dataService.recordUV(ip);
        User user = hostHolder.getUser();
        //如果当前存在登录用户，记录存活用户
        if (user != null) dataService.recordDAU(user.getId());
        return true;
    }
}
