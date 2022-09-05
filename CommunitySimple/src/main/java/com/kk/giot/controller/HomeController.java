package com.kk.giot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.giot.pojo.DiscussPost;
import com.kk.giot.pojo.DiscussPostAndUser;
import com.kk.giot.pojo.User;
import com.kk.giot.pojo.page.PageDTO;
import com.kk.giot.service.DiscussPostService;
import com.kk.giot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    /**
     * 使用 MyBatisPlus 进行分页查询
     *
     * @param model
     * @param current
     * @param size
     * @return
     */
    @GetMapping(value = "/index")
    public String getIndexPage(Model model, @RequestParam(value = "current", defaultValue = "1") Long current, @RequestParam(value = "size", defaultValue = "6") Long size) {
        Page<DiscussPost> discussPostPage = discussPostService.query().ne("status", 2).page(new Page<DiscussPost>(current, size));
        List<DiscussPost> discussPostList = discussPostPage.getRecords();
        List<DiscussPostAndUser> discussPosts = new ArrayList<>();
        if (discussPostList != null) {
            for (DiscussPost discussPost : discussPostList) {
                Integer userId = discussPost.getUserId();
                User user = userService.getById(userId);
                DiscussPostAndUser discussPostAndUserPageDTO = new DiscussPostAndUser(discussPost, user);
                discussPosts.add(discussPostAndUserPageDTO);
            }
        }
        PageDTO pageDTO = new PageDTO(discussPostPage.getTotal(), discussPosts, discussPostPage);
        model.addAttribute("pageDTO", pageDTO);
        return "/index";
    }
}
