package com.zwm.dao;

import com.zwm.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    //entityType 决定评论类型：1-帖子评论 2-回复评论
    //entityId 决定发表评论的实体 Id
    public abstract List<Comment> selectCommentsByEntity(int entityType, int entityId, int start, int end);

    //entityType：1-帖子评论 2-回复评论
    //entityId：发表评论的实体 Id
    public abstract int selectCountByEntity(int entityType, int entityId);

    //增加评论
    public abstract int insertComment(Comment comment);
}
