package com.zwm.controller;

import com.zwm.annotation.LoginRequired;
import com.zwm.entity.Event;
import com.zwm.entity.User;
import com.zwm.event.EventProducer;
import com.zwm.service.impl.CommentServiceImpl;
import com.zwm.service.impl.DiscussPostServiceImpl;
import com.zwm.service.impl.LikeServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static com.zwm.util.CommunityConstantTwo.TOPIC_LIKE;

@Controller
public class LikeController {

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostServiceImpl discussPostService;

    @Autowired
    private CommentServiceImpl commentService;

    //发送点赞/取消点赞请求
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String like(int entityType, int entityId, int entityUserId) {
        User user = hostHolder.getUser();
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        //更新点赞数量
        long likeNumbers = likeService.findLikeNumbers(entityType, entityId);
        //更新点赞状态
        int likeStatus = likeService.findLikeStatus(user.getId(), entityType, entityId);
        //传递给页面
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeNumbers);
        map.put("likeStatus", likeStatus);

        //发送通知给被点赞的人 ---> 如果点赞才发送通知，没点就不要发送了
        if (likeStatus == 1) {
            Event event = new Event();
            event.setTopic(TOPIC_LIKE)
                    .setUserId(user.getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId);
            //触发点赞
            eventProducer.fireEvent(event);
        }
        //根据点赞的实体类型返回该实体Id的主人用户的Id ---> 系统要给谁发消息
        //如果是给帖子点赞，查询出该帖子，然后查询出持有人
        /*if (ENTITY_TYPE_POST == entityType) {
            DiscussPost discussPost = discussPostService.selectDiscussPost(entityId);
            event.setEntityUserId(discussPost.getUserId());
        } else if (ENTITY_TYPE_COMMENT == entityType) {
            //如果是给评论点赞，查询出该评论，然后查询出持有人
            Comment comment = commentService.findCommentById(entityId);
            event.setEntityUserId(comment.getUserId());
        }
*/
        //返回 JSON 字符串数据
        return CommunityUtils.getJsonString(0, null, map);
    }
}
