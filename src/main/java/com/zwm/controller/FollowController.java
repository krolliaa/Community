package com.zwm.controller;

import com.zwm.entity.Page;
import com.zwm.entity.User;
import com.zwm.service.impl.FollowServiceImpl;
import com.zwm.service.impl.UserServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_USER;

@Controller
public class FollowController {
    @Autowired
    private FollowServiceImpl followService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserServiceImpl userService;

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

    //获取关注列表
    @RequestMapping(value = "/followees/{userId}", method = RequestMethod.GET)
    public String getFolloweeListPage(@PathVariable(value = "userId") int userId, Page page, Model model) {
        page.setLimit(5);
        page.setPath("/followees/" + userId);
        //查询总共有多少条关注的人数
        page.setRows((int) followService.followeeCount(userId, ENTITY_TYPE_USER));
        //判断当前用户是否为空，如果是空的报错
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }
        //查询出用户是为了显示页面信息："XXX关注的人" "关注XXX的人"
        model.addAttribute("user", user);
        List<Map<String, Object>> followeeList = followService.getFolloweeUsersList(userId, page.getStart(), page.getLimit());
        //判断是不是已经关注过的人【这样就可以直接关注或者想取消关注就取消关注了】
        if (followeeList != null) {
            for (Map<String, Object> followeeMap : followeeList) {
                User followeeUser = (User) followeeMap.get("user");
                //判断用户是否已关注
                boolean hasFollowed = false;
                if (hostHolder.getUser() == null) {
                    hasFollowed = false;
                } else {
                    hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, followeeUser.getId());
                }
                followeeMap.put("hasFollowed", hasFollowed);
            }
        }

        model.addAttribute("users", followeeList);
        return "/site/followee";
    }

    //获取粉丝列表
    @RequestMapping(value = "/followers/{userId}", method = RequestMethod.GET)
    public String getFollowerListPage(@PathVariable(value = "userId") int userId, Page page, Model model) {
        page.setLimit(5);
        page.setPath("/followeRs/" + userId);
        //查询总共有多少条关注的人数
        page.setRows((int) followService.followerCount(ENTITY_TYPE_USER, userId));
        //判断当前用户是否为空，如果是空的报错
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }
        //查询出用户是为了显示页面信息："XXX关注的人" "关注XXX的人"
        model.addAttribute("user", user);
        List<Map<String, Object>> followerList = followService.getFollowerUsersList(userId, page.getStart(), page.getLimit());
        //判断是不是已经关注过的人【这样就可以直接关注或者想取消关注就取消关注了】
        if (followerList != null) {
            for (Map<String, Object> followerMap : followerList) {
                User followerUser = (User) followerMap.get("user");
                //判断用户是否已关注
                boolean hasFollowed = false;
                if (hostHolder.getUser() == null) {
                    hasFollowed = false;
                } else {
                    hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, followerUser.getId());
                }
                followerMap.put("hasFollowed", hasFollowed);
            }
        }
        model.addAttribute("users", followerList);
        return "/site/follower";
    }
}
