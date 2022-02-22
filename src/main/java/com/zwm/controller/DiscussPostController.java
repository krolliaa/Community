package com.zwm.controller;

import com.zwm.service.impl.DiscussPostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/discussPost", method = RequestMethod.GET)
public class DiscussPostController {

    @Autowired
    DiscussPostServiceImpl discussPostService;

    @RequestMapping(value = "/select1")
    public Object findDiscussPosts() {
        return discussPostService.findDiscussPosts(1, 0, 10);
    }

    @RequestMapping(value = "/select2")
    public Object findDiscussPostsCount() {
        return discussPostService.findDiscussPostsCount(1);
    }
}
