package com.zwm.controller;

import com.zwm.entity.Comment;
import com.zwm.entity.DiscussPost;
import com.zwm.entity.Event;
import com.zwm.entity.User;
import com.zwm.event.EventProducer;
import com.zwm.service.impl.CommentServiceImpl;
import com.zwm.service.impl.DiscussPostServiceImpl;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

import static com.zwm.util.CommunityConstantTwo.*;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostServiceImpl discussPostService;

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

        //测试收到的事件
        Event event = new Event();
        event.setTopic(TOPIC_COMMENT)
                .setUserId(user.getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId());
        //这里需要根据实体类型：帖子还是评论，获取该帖子/评论的主人信息
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            //根据当前 entityId 也就是帖子编号查找到帖子，从而获取 userId 主人 id 编号
            DiscussPost discussPost = discussPostService.selectDiscussPost(comment.getEntityId());
            event.setEntityUserId(discussPost.getUserId());
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            //如果是评论回复类型，需要找到该条评论的主人，通过 entityId 找到该评论然后再找到这个评论的主人
            Comment thisComment = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(thisComment.getUserId());
        }
        eventProducer.fireEvent(event);

        return "redirect:/discuss/detail/" + discussPostId;
    }


}
