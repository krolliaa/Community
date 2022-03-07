package com.zwm.service.impl;

import com.zwm.service.FollowService;
import com.zwm.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    RedisTemplate redisTemplate;

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

    //查询某个用户的关注者个数
    public long followeeCount(int userId, int entityId) {
        String followeeKey = RedisKeyUtil.getFollowerKey(userId, entityId);
        return redisTemplate.opsForZSet().size(followeeKey);
    }

    //查询当前用户是否已关注当前实体
    public boolean hasFollow(int userId, int entityType, int entityId) {
        System.out.println(redisTemplate.opsForZSet().zCard(RedisKeyUtil.getFolloweeKey(userId, entityType)) == null ? "null" : redisTemplate.opsForZSet().zCard(RedisKeyUtil.getFolloweeKey(userId, entityType)));
        return redisTemplate.opsForZSet().zCard(RedisKeyUtil.getFolloweeKey(userId, entityType)) != null ? true : false;
    }
}
