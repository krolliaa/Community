package com.zwm.service;

import com.zwm.entity.DiscussPost;
import org.springframework.data.domain.Page;

public interface ElasticsearchService {
    public abstract void insertPostToES(DiscussPost discussPost);

    //从ES服务器中删除帖子
    public abstract void deletePostFromES(int id);

    //在ES中搜索帖子 ---> 关键字 当前页 显示条数
    public abstract Page<DiscussPost> searchPostFromES(String keyword, int current, int limit);
}
