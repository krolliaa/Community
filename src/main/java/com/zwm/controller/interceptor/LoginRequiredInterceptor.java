package com.zwm.controller.interceptor;

import com.zwm.annotation.LoginRequired;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前请求的是不是一个方法【避免静态资源】
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired =  method.getAnnotation(LoginRequired.class);
            //如果该注解存在但是却没有登陆，需要重定向到登录页面
            if(loginRequired != null && hostHolder.getUser() == null) {
                response.sendRedirect( request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}
