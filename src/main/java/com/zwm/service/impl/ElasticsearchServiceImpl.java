package com.zwm.service.impl;

import com.zwm.dao.DiscussPostMapper;
import com.zwm.dao.elasticsearch.DiscussPostRepository;
import com.zwm.entity.DiscussPost;
import com.zwm.service.ElasticsearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //添加帖子到ES服务器
    public void insertPostToES(DiscussPost discussPost) {
        discussPostRepository.save(discussPost);
    }

    //从ES服务器中删除帖子
    public void deletePostFromES(int id) {
        discussPostRepository.deleteById(id);
    }

    //在ES中搜索帖子 ---> 关键字 当前页 显示条数
    public Page<DiscussPost> searchPostFromES(String keyword, int current, int limit) {
        //构造搜索条件 ---> QueryBuilders.multiMatchQuery[复合]
        //构造排序条件 ---> SortBuilders
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current, limit))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        return elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits searchHits = searchResponse.getHits();
                if (searchHits.getTotalHits() <= 0) return null;
                List<DiscussPost> discussPostList = new ArrayList<>();
                for (SearchHit searchHit : searchHits) {
                    DiscussPost discussPost = new DiscussPost();
                    discussPost.setId(Integer.valueOf(searchHit.getSourceAsMap().get("id").toString()));
                    discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("userId").toString()));
                    discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("status").toString()));
                    discussPost.setTitle(searchHit.getSourceAsMap().get("title").toString());
                    discussPost.setTitle(searchHit.getSourceAsMap().get("content").toString());
                    discussPost.setCreateTime(new Date(Long.valueOf(searchHit.getSourceAsMap().get("createTime").toString())));
                    discussPost.setCommentCount(Integer.valueOf(searchHit.getSourceAsMap().get("commentCount").toString()));
                    //处理高亮结果
                    HighlightField highlightField = searchHit.getHighlightFields().get("title");
                    //如果搜索到的结果不为空，则传递给 discussPost 后面前端将高亮显示内容 ---> <em></em>包括了搜索关键词
                    if (highlightField != null) {
                        //因为搜索时会分词这里只要查询第一个就可以了
                        discussPost.setTitle(highlightField.getFragments()[0].toString());
                    }
                    HighlightField highlightField1 = searchHit.getHighlightFields().get("content");
                    if (highlightField1 != null) {
                        discussPost.setContent(highlightField1.getFragments()[0].toString());
                    }
                    discussPostList.add(discussPost);
                }
                return new AggregatedPageImpl(discussPostList, pageable, searchHits.getTotalHits(), searchResponse.getAggregations(), searchResponse.getScrollId(), searchHits.getMaxScore());
            }
        });
    }
}
