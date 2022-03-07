package com.zwm.service;

import com.zwm.util.RedisKeyUtil;

public interface FollowService {
    //关注
    public abstract void follow(int userId, int entityType, int entityId);

    //取消关注
    public abstract void unfollow(int userId, int entityType, int entityId);

    //查询某个用户的粉丝数量
    public abstract long followerCount(int entityType, int entityId);

    //查询某个用户的关注个数
    public abstract long followeeCount(int userId, int entityType);

    //查询当前用户是否已关注当前实体
    public abstract boolean hasFollowed(int userId, int entityType, int entityId);
}
