package com.zwm.controller;

import com.zwm.service.impl.DiscussPostServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/test", method = RequestMethod.GET)
public class DiscussPostController {

    @Autowired
    DiscussPostServiceImpl discussPostService;

    @Autowired
    SensitiveFilter sensitiveFilter;

    @RequestMapping(value = "/select1")
    public Object findDiscussPosts() {
        return discussPostService.findDiscussPosts(1, 0, 10);
    }

    @RequestMapping(value = "/select2")
    public Object findDiscussPostsCount() {
        return discussPostService.findDiscussPostsCount(1);
    }

    @ResponseBody
    @RequestMapping(value = "/getCookie", method = RequestMethod.GET)
    public String sendCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("code", CommunityUtils.generateUUID());
        cookie.setPath("/community/discussPost");
        cookie.setMaxAge(60 * 10);
        httpServletResponse.addCookie(cookie);
        return "add Cookie!";
    }

    @RequestMapping(value = "/sendCookieToServer", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue(value = "code") String code) {
        return "get Cookie: code ---> " + code;
    }

    @RequestMapping(value = "/setSession", method = RequestMethod.GET)
    @ResponseBody
    public String testSession(HttpSession httpSession) {
        //Session 比 Cookie 强大，它可以设置任何类型的数据
        httpSession.setAttribute("id", 888);
        httpSession.setAttribute("name", "wow");
        return "Set Session Ok";
    }

    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
    @ResponseBody
    public String testSession(@CookieValue(value = "JSESSIONID") String JSESSIONID) {
        return "Get Session OK: " + JSESSIONID;
    }

    @RequestMapping(value = "/testSensitive", method = RequestMethod.GET)
    public String testSensitiveWord() {
        String filterTest = sensitiveFilter.filter("赌+++++++++++++博好啊赌博好");
        return filterTest;
    }
}
