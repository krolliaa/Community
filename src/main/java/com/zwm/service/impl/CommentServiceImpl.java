package com.zwm.service.impl;

import com.zwm.dao.CommentMapper;
import com.zwm.entity.Comment;
import com.zwm.service.CommentService;
import com.zwm.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostServiceImpl discussPostService;

    @Override
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int start, int end) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, start, end);
    }

    @Override
    public int findCountByEntity(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    @Override
    public int addComment(Comment comment) {
        //需要将评论内容过滤敏感词 + 防止 XSS 攻击
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        //添加评论
        int row = commentMapper.insertComment(comment);
        //增加评论的数量 ---> DiscussPostServiceImpl
        //获取全部的评论数量
        int counts = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
        discussPostService.updateDiscussPostCommentCount(comment.getEntityId(), counts);
        return row;
    }
}
