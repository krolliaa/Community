package com.zwm.service.impl;

import com.zwm.dao.DiscussPostMapper;
import com.zwm.entity.DiscussPost;
import com.zwm.service.DiscussPostService;
import com.zwm.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int start, int end) {
        return discussPostMapper.selectDiscussPosts(userId, start, end);
    }

    @Override
    public int findDiscussPostsCount(int userId) {
        return discussPostMapper.selectDiscussPostsCount(userId);
    }

    @Override
    public int addDiscussPost(DiscussPost discussPost) {
        //如果discussPost参数为空，抛出异常
        if (discussPost == null) throw new IllegalArgumentException("参数不能为空！");
        //为了防止XSS攻击，需要将帖子标题 + 内容做一个HTML转换
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        //过滤敏感词
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));
        return discussPostMapper.insertDiscussPost(discussPost);
    }

    @Override
    public DiscussPost selectDiscussPost(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    @Override
    public int updateDiscussPostCommentCount(int id, int commentCount) {
        return discussPostMapper.updateDiscussPostCommentCount(id, commentCount);
    }
}
