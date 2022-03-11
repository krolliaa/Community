package com.zwm.service;

import com.zwm.entity.Comment;

import java.util.List;

public interface CommentService {
    //分页查找当前所有评论
    public abstract List<Comment> findCommentsByEntity(int entityType, int entityId, int start, int end);

    //根据实体查找评论数量
    public abstract int findCountByEntity(int entityType, int entityId);

    //增加帖子
    public abstract int addComment(Comment comment);

    public abstract Comment findCommentById(int id);
}
