package com.zwm.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {
    public static String getCookieValue(HttpServletRequest httpServletRequest, String cookieName) {
        //根据请求request的cookies ---> 封装成为一套工具 ---> CookieUtils
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    //获取然后直接返回当前凭证
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
