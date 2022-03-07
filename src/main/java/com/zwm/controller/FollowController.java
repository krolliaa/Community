package com.zwm.controller;

import com.zwm.entity.User;
import com.zwm.service.impl.FollowServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FollowController {
    @Autowired
    private FollowServiceImpl followService;

    @Autowired
    private HostHolder hostHolder;

    //点击关注
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType, int entityId) {
        //当前用户关注了某人
        User user = hostHolder.getUser();
        followService.follow(user.getId(), entityType, entityId);
        return CommunityUtils.getJsonString(0, "已关注");
    }

    //点击取消关注
    @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(int entityType, int entityId) {
        //当前用户关注了某人
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(), entityType, entityId);
        return CommunityUtils.getJsonString(0, "已取消关注");
    }
}
