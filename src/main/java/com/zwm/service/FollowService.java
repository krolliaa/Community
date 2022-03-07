package com.zwm.service;

public interface FollowService {
    //关注
    public abstract void follow(int userId, int entityType, int entityId);

    //取消关注
    public abstract void unfollow(int userId, int entityType, int entityId);
}
