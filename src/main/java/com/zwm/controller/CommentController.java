package com.zwm.controller;

import com.zwm.entity.Comment;
import com.zwm.entity.User;
import com.zwm.service.impl.CommentServiceImpl;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentServiceImpl commentService;

    @RequestMapping(value = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable(value = "discussPostId") int discussPostId, Comment comment) {
        //获取当前线程的用户
        User user = hostHolder.getUser();
        if (user != null) {
            comment.setUserId(user.getId());
            comment.setStatus(0);
            comment.setCreateTime(new Date());
            //添加帖子
            commentService.addComment(comment);
        }
        return "redirect:/discuss/detail/" + discussPostId;
    }
}
