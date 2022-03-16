package com.zwm.controller;

import com.zwm.entity.DiscussPost;
import com.zwm.entity.Page;
import com.zwm.entity.User;
import com.zwm.service.impl.ElasticsearchServiceImpl;
import com.zwm.service.impl.LikeServiceImpl;
import com.zwm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_POST;

@Controller
public class ElasticsearchController {

    @Autowired
    private ElasticsearchServiceImpl elasticsearchService;

    //搜索到帖子之后还要展示作者信息和点赞数量
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private LikeServiceImpl likeService;

    //search?keyword=xxx ---> GET 请求
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchPost(String keyword, Page page, Model model) {
        org.springframework.data.domain.Page<DiscussPost> discussPostPage = elasticsearchService.searchPostFromES(keyword, page.getCurrent() - 1, page.getLimit());
        //聚合数据 ---> 封装给前端 ---> 因为有多个 用户+帖子+点赞，所以需要一个集合装着
        List<Map<String, Object>> discussPostList = new ArrayList<>();
        if (discussPostPage != null) {
            for (DiscussPost discussPost : discussPostPage) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", discussPost);
                //搜索出当前帖子的用户
                System.out.println(discussPost.toString());
                User user = userService.findUserById(discussPost.getUserId());
                map.put("user", user);
                //搜索出当前帖子的点赞数量
                map.put("likeCount", likeService.findLikeNumbers(ENTITY_TYPE_POST, discussPost.getId()));
                discussPostList.add(map);
            }
        }
        //传递搜索出的帖子
        model.addAttribute("discussPosts", discussPostList);
        //传递关键字
        model.addAttribute("keyword", keyword);
        //传递分页信息
        page.setPath("/search?keyword=" + keyword);
        page.setRows(discussPostPage == null ? 0 : (int) discussPostPage.getTotalElements());
        return "/site/search";
    }
}
