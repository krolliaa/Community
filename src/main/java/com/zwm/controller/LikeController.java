package com.zwm.controller;

import com.zwm.annotation.LoginRequired;
import com.zwm.entity.User;
import com.zwm.service.impl.LikeServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import com.zwm.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private HostHolder hostHolder;

    //发送点赞/取消点赞请求
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String like(int entityType, int entityId) {
        User user = hostHolder.getUser();
        likeService.like(user.getId(), entityType, entityId);
        //更新点赞数量
        long likeNumbers = likeService.findLikeNumbers(entityType, entityId);
        //更新点赞状态
        int likeStatus = likeService.findLikeStatus(user.getId(), entityType, entityId);
        //传递给页面
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeNumbers);
        map.put("likeStatus", likeStatus);
        //返回 JSON 字符串数据
        return CommunityUtils.getJsonString(0, null, map);
    }
}
