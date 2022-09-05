package com.kk.giot.controller;

import com.kk.giot.pojo.User;
import com.kk.giot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 访问注册页面
     *
     * @return
     */
    @GetMapping(value = "/register")
    public String getRegisterPage() {
        return "/site/register";
    }

    /**
     * 注册账户
     */
    @PostMapping(value = "/register")
    public String register(Model model, User user) {
        Map<String, String> map = userService.register(user);
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
