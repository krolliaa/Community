package com.zwm.service;

import com.zwm.entity.DiscussPost;

import java.util.List;

public interface DiscussPostService {
    //根据 userId 分页查询状态为没有拉黑的帖子
    public abstract List<DiscussPost> findDiscussPosts(int userId, int start, int end);

    //根据 userId 分页查询状态为没有拉黑的帖子数量
    public abstract int findDiscussPostsCount(int userId);

    //插入新的帖子
    public abstract int addDiscussPost(DiscussPost discussPost);
}
