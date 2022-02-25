package com.zwm.controller;

import com.google.code.kaptcha.Producer;
import com.zwm.entity.User;
import com.zwm.service.impl.UserServiceImpl;
import com.zwm.util.CommunityConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.zwm.util.CommunityConstant.ACTIVATION_FAILURE;
import static com.zwm.util.CommunityConstant.ACTIVATION_SUCCESS;
import static com.zwm.util.CommunityConstantTwo.DEFAULT_EXPIRED_SECONDS;
import static com.zwm.util.CommunityConstantTwo.REMEMBER_EXPIRED_SECONDS;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private Producer kaptchaProducer;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
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

    //激活码激活
    @RequestMapping(value = "/activation/{id}/{code}")
    public String activeCode(Model model, @PathVariable("id") int id, @PathVariable("code") String code) {
        CommunityConstant communityConstant = userService.activation(id, code);
        if (communityConstant == ACTIVATION_FAILURE) {
            model.addAttribute("msg", "激活失败,您提供的激活码不正确!");
            model.addAttribute("target", "/index");//跳转返回首页
        } else if (communityConstant == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功,您的账号已经可以正常使用了!");
            model.addAttribute("target", "/login");
        } else {
            model.addAttribute("msg", "无效操作,该账号已经激活过了!");
            model.addAttribute("target", "/index");//跳转返回首页
        }
        return "/site/operate-result";
    }

    //验证码功能 ---> 返回图片
    @RequestMapping(value = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse httpServletResponse, HttpSession httpSession) {
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage bufferedImage = kaptchaProducer.createImage(text);
        //保存验证码文件到本地
        httpSession.setAttribute("kaptcha", text);
        //设置返回的格式
        httpServletResponse.setContentType("image/png");
        try {
            OutputStream outputStream = httpServletResponse.getOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //登录功能
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(Model model, HttpServletResponse httpServletResponse, HttpSession httpSession, String username, String password, String code, boolean rememberMe) {
        //参数分别为：数据 + 响应类型 + 会话 + 用户名 + 密码 + 验证码 + 是否记住我
        //session保存在服务器，内置属性：kaptcha
        String kaptcha = (String) httpSession.getAttribute("kaptcha");
        //如果验证码为空或者不匹配直接返回到登录页面
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equals(code)) {
            model.addAttribute("codeMsg", "验证码不正确!");
            return "/site/login";
        }
        //查询用户名和密码匹配的用户信息 ---> UserService
        //设置存活时间：要么半天要么100天
        int expiredSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, String> map = userService.login(username, password, expiredSeconds);
        //验证map，如果包含`ticket`表明账户验证通过，否则表示不通过
        if (map.containsKey("loginTicket")) {
            Cookie cookie = new Cookie("ticket", map.get("loginTicket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            httpServletResponse.addCookie(cookie);
            //重定向到首页
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String userLogout() {
        return "/index";
    }


}