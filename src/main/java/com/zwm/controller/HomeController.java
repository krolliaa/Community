package com.zwm.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zwm.entity.DiscussPost;
import com.zwm.entity.Page;
import com.zwm.entity.User;
import com.zwm.service.impl.DiscussPostServiceImpl;
import com.zwm.service.impl.LikeServiceImpl;
import com.zwm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_POST;

@Controller
public class HomeController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DiscussPostServiceImpl discussPostService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LikeServiceImpl likeService;

    @RequestMapping(value = "/selectUser")
    @ResponseBody
    public Object findUserById() {
        return userService.findUserById(122);
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {

        /*redisTemplate.delete(redisTemplate.keys("*"));
        System.out.println("----------String----------");
        String stringKey = "test:count";
        redisTemplate.opsForValue().set(stringKey, 1);
        System.out.println(redisTemplate.opsForValue().get(stringKey));
        System.out.println(redisTemplate.opsForValue().increment(stringKey));
        System.out.println(redisTemplate.opsForValue().decrement(stringKey));

        System.out.println("----------Hash----------");
        String hashKey = "test:user";
        redisTemplate.opsForHash().put(hashKey, "id", 1);
        redisTemplate.opsForHash().put(hashKey, "username", "ZhangSan");
        System.out.println(redisTemplate.opsForHash().get(hashKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(hashKey, "username"));

        System.out.println("----------List----------");
        String listKey = "test:ids";
        System.out.println(redisTemplate.opsForList().leftPush(listKey, "aaa"));
        System.out.println(redisTemplate.opsForList().leftPush(listKey, "bbb"));
        System.out.println(redisTemplate.opsForList().leftPush(listKey, "ccc"));
        System.out.println(redisTemplate.opsForList().size(listKey));
        System.out.println(redisTemplate.opsForList().index(listKey, 0));
        System.out.println(redisTemplate.opsForList().index(listKey, 1));
        System.out.println(redisTemplate.opsForList().index(listKey, 2));
        System.out.println(redisTemplate.opsForList().range(listKey, 0, 2));
        System.out.println(redisTemplate.opsForList().leftPop(listKey));
        System.out.println(redisTemplate.opsForList().leftPop(listKey));
        System.out.println(redisTemplate.opsForList().leftPop(listKey));

        System.out.println("----------Set----------");
        String setKey = "test:teachers";
        redisTemplate.opsForSet().add(setKey, "teacherA", "teacherB", "teacherC");
        System.out.println(redisTemplate.opsForSet().size(setKey));
        System.out.println(redisTemplate.opsForSet().members(setKey));
        System.out.println(redisTemplate.opsForSet().pop(setKey));

        System.out.println("----------ZSet----------");
        String zSetKey = "test:students";
        redisTemplate.opsForZSet().add(zSetKey, "studentA", 50);
        redisTemplate.opsForZSet().add(zSetKey, "studentB", 60);
        redisTemplate.opsForZSet().add(zSetKey, "studentC", 70);
        redisTemplate.opsForZSet().add(zSetKey, "studentD", 80);
        redisTemplate.opsForZSet().add(zSetKey, "studentE", 90);
        redisTemplate.opsForZSet().add(zSetKey, "studentF", 100);
        System.out.println(redisTemplate.opsForZSet().score(zSetKey, "studentF"));
        System.out.println(redisTemplate.opsForZSet().range(zSetKey, 0, 5));
        System.out.println(redisTemplate.opsForZSet().zCard(zSetKey));
        System.out.println(redisTemplate.opsForZSet().reverseRange(zSetKey, 0, 4));

        System.out.println("----------Key----------");
        System.out.println(redisTemplate.hasKey("test:teachers"));
        redisTemplate.expire("test:teachers", 10, TimeUnit.SECONDS);
        System.out.println(redisTemplate.keys("*"));

        System.out.println("----------Transaction----------");
        //???????????????????????????????????????redis?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        Object result = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String setKey = "text:tx";
                //????????????
                redisOperations.multi();
                redisOperations.opsForSet().add(setKey, "ZhangSan");
                redisOperations.opsForSet().add(setKey, "LiSi");
                redisOperations.opsForSet().add(setKey, "WangWu");
                //??????????????????????????????
                System.out.println(redisOperations.opsForSet().members(setKey));
                //????????????
                return redisOperations.exec();
            }
        });*/
        //page ??????????????????????????????
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
                //??????????????????????????????????????????????????????????????????????????????????????????
                long likeCount = likeService.findLikeNumbers(ENTITY_TYPE_POST, discussPost.getId());
                map.put("likeCount", likeCount);
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
