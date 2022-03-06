package com.zwm.service;

public interface LikeService {
    //点赞
    public abstract void like(int userId, int entityType, int entityId);

    //点赞的数量
    public long findLikeNumbers(int entityType, int entityId);

    //点赞的状态
    public int findLikeStatus(int userId, int entityType, int entityId);
}
