package com.zwm.dao;

import com.zwm.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    //根据 userId 分页查询状态为没有拉黑的帖子，如果是 userId = 0 代表查询所有
    public abstract List<DiscussPost> selectDiscussPosts(int userId, int start, int end);

    //根据 userId 分页查询状态为没有拉黑的帖子数量
    public abstract int selectDiscussPostsCount(int userId);

    //插入新的帖子
    public abstract int insertDiscussPost(DiscussPost discussPost);

    //根据帖子id查询出帖子
    public abstract DiscussPost selectDiscussPostById(int id);

    //更新评论的数量
    int updateDiscussPostCommentCount(int id, int commentCount);
}
