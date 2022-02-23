package com.zwm.controller;

import com.zwm.entity.User;
import com.zwm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/register";
    }

    //注册功能
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        //点击注册按钮提交数据，验证 user 信息
        Map<String, String> map = userService.register(user);
        //如果 map 为空或者为 null 表示注册信息没有问题，此时直接跳转到激活页面
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功,我们已经向您的邮箱发送了一封激活邮件,请尽快激活!");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            //有值表示注册失败，返回到注册页面
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }
}
