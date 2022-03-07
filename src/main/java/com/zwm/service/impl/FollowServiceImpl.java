package com.zwm.service.impl;

import com.zwm.entity.User;
import com.zwm.service.FollowService;
import com.zwm.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_USER;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private UserServiceImpl userService;

    //完成关注功能 ---> userId 关注类型为 entityType 的 entityId
    //因为某个用户关注了另外一个用户，需要完成 followee 和 follower 两个，这两个是捆绑在一起的，需要使用事务
    public void follow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //开启事务
                operations.multi();
                //关注之前首先需要获取 key
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                //关注，关注需要有一个排序 ---> 到时候会发送通知给被关注的用户
                redisTemplate.opsForZSet().add(followeeKey, entityId, System.currentTimeMillis());
                //某个实体收到了一个粉丝，这个信息也需要存放到数据库中
                //获取粉丝 key ---> 某个实体entityType entityId拥有粉丝 userId
                //我觉得这个很强词夺理，因为它根本就是为后面发送通知消息这个操作来描述现在的场景
                //而不是根据现在的当前业务出发点出发
                //就是因为后面需要开发出，XXX通过帖子/评论XXX关注了你，才需要搞这种东西
                //如果实实在在以当前场景出发的话，应该直接使用 userId ，某个人的粉丝有 XXX 用户，XXX 用户关注了 XXX 用户
                //服了。。。真的服了
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                redisTemplate.opsForZSet().add(followerKey, userId, System.currentTimeMillis());
                return redisTemplate.exec();
            }
        });
    }

    @Override
    public void unfollow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //开启事务
                operations.multi();
                //关注之前首先需要获取 key
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                //关注，关注需要有一个排序 ---> 到时候会发送通知给被关注的用户
                redisTemplate.opsForZSet().remove(followeeKey, entityId);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                redisTemplate.opsForZSet().remove(followerKey, userId);
                return redisTemplate.exec();
            }
        });
    }

    //查询某个用户的粉丝个数
    public long followerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return redisTemplate.opsForZSet().size(followerKey);
    }

    //查询某个用户的关注个数
    public long followeeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().size(followeeKey);
    }

    //查询当前用户是否已关注当前实体
    public boolean hasFollowed(int userId, int entityType, int entityId) {
        System.out.println(redisTemplate.opsForZSet().score(RedisKeyUtil.getFolloweeKey(userId, entityType), entityId) == null ? "null" : redisTemplate.opsForZSet().zCard(RedisKeyUtil.getFolloweeKey(userId, entityType)));
        return redisTemplate.opsForZSet().score(RedisKeyUtil.getFolloweeKey(userId, entityType), entityId) != null ? true : false;
    }

    //获取关注列表 ---> 支持分页
    public List<Map<String, Object>> getFolloweeUsersList(int userId, int start, int limit) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, ENTITY_TYPE_USER);
        //分页获取所有关注的人的 user_id
        Set<Integer> followeeZSet = redisTemplate.opsForZSet().reverseRange(followeeKey, start, limit - 1);
        //判断到底有没有关注人即有无关注的人
        if (followeeZSet == null) {
            return null;
        } else {
            //如果有关注列表，需要往集合中放 User 用户，还要放置关注时间，所以这里用 map 来存储
            //最后将 Map 放入到整个 List 集合中
            List<Map<String, Object>> followeeList = new ArrayList<>();
            for (Integer followeeUserId : followeeZSet) {
                Map<String, Object> followeeMap = new HashMap<>();
                User user = userService.findUserById(followeeUserId);
                //放入用户
                followeeMap.put("user", user);
                //放入关注时间
                Double followeeTime = redisTemplate.opsForZSet().score(followeeKey, followeeUserId);
                followeeMap.put("followTime", new Date(followeeTime.longValue()));
                followeeList.add(followeeMap);
            }
            return followeeList;
        }
    }

    //获取粉丝列表 ---> 支持分页
    public List<Map<String, Object>> getFollowerUsersList(int userId, int start, int limit) {
        String followerKey = RedisKeyUtil.getFollowerKey(ENTITY_TYPE_USER, userId);
        //获取粉丝列表的集合
        Set<Integer> followerUserIds = redisTemplate.opsForZSet().reverseRange(followerKey, start, limit - 1);
        if (followerKey == null) {
            return null;
        } else {
            List<Map<String, Object>> followerList = new ArrayList<>();
            for (Integer followerUserId : followerUserIds) {
                Map<String, Object> followerMap = new HashMap<>();
                User user = userService.findUserById(followerUserId);
                followerMap.put("user", user);
                //获取成为粉丝的时间
                Double followerTime = redisTemplate.opsForZSet().score(followerKey, followerUserId);
                followerMap.put("followTime", new Date(followerTime.longValue()));
                followerList.add(followerMap);
            }
            return followerList;
        }
    }

}
