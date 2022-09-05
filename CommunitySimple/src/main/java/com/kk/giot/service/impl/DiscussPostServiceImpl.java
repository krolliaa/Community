package com.kk.giot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.giot.mapper.DiscussPostMapper;
import com.kk.giot.pojo.DiscussPost;
import com.kk.giot.service.DiscussPostService;
import org.springframework.stereotype.Service;

@Service
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements DiscussPostService {
}
