package com.zwm.controller.interceptor;

import com.zwm.entity.LoginTicket;
import com.zwm.entity.User;
import com.zwm.service.impl.UserServiceImpl;
import com.zwm.util.CookieUtils;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //该方法作用：将用户存放到整个请求当中
        //获取cookie中的ticket进而直接获取 LoginTicket 对象
        LoginTicket loginTicket = userService.selectLoginTicketByTicket(CookieUtils.getCookieValue(request, "ticket"));
        //在 loginTicket 不为空并且登陆状态有效以及生存时间在当前时间之后情况下，说明有登录用户存在
        if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
            //根据用户凭证查询当前用户
            User user = userService.findUserById(loginTicket.getUserId());
            //现在考虑到服务器是并发的，需要将当前的用户一并发送给服务器
            //只要这个线程还在，请求还在那么这个用户就一直还在
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //获取当前线程的用户
        User loginUser = hostHolder.getUser();
        if (loginUser != null && modelAndView != null) {
            modelAndView.addObject("loginUser", loginUser);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //模板展示之后将数据清除掉
        hostHolder.clear();
    }
}
