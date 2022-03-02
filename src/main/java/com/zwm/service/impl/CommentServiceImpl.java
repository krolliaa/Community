package com.zwm.service.impl;

import com.zwm.dao.CommentMapper;
import com.zwm.entity.Comment;
import com.zwm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int start, int end) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, start, end);
    }

    @Override
    public int findCountByEntity(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }
}
