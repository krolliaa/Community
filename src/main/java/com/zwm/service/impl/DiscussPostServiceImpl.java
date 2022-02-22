package com.zwm.service.impl;

import com.zwm.dao.DiscussPostMapper;
import com.zwm.entity.DiscussPost;
import com.zwm.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int start, int end) {
        return discussPostMapper.selectDiscussPosts(userId, start, end);
    }

    @Override
    public int findDiscussPostsCount(int userId) {
        return discussPostMapper.selectDiscussPostsCount(userId);
    }
}
