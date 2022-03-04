package com.zwm.controller;

import com.zwm.entity.DiscussPost;
import com.zwm.entity.Page;
import com.zwm.entity.User;
import com.zwm.service.impl.DiscussPostServiceImpl;
import com.zwm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DiscussPostServiceImpl discussPostService;

    @RequestMapping(value = "/selectUser")
    @ResponseBody
    public Object findUserById() {
        return userService.findUserById(122);
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        //page 需要知道总数是和路径
        page.setRows(discussPostService.findDiscussPostsCount(0));
        page.setPath("/index");
        List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0, page.getStart(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (discussPostList != null) {
            for (DiscussPost discussPost : discussPostList) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", discussPost);
                User user = userService.findUserById(discussPost.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "/index";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }
}
