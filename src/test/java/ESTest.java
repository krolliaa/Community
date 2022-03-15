import com.zwm.CommunityApplication;
import com.zwm.dao.DiscussPostMapper;
import com.zwm.dao.elasticsearch.DiscussPostRepository;
import com.zwm.entity.DiscussPost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ESTest {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testESAdd() {
        //测试往ES存储索引
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(243));
    }

    @Test
    public void testESAddAll() {
        //测试往ES添加多条数据
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(101, 0, 100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(102, 0, 100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(103, 0, 100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(111, 0, 100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(112, 0, 100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(131, 0, 100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(132, 0, 100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(133, 0, 100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(134, 0, 100));
    }

    @Test
    public void testESUpdate() {
        //测试往ES更新数据
        DiscussPost discussPost = discussPostMapper.selectDiscussPostById(231);
        discussPost.setContent("我是新人，使劲灌水");
        discussPostRepository.save(discussPost);
    }

    @Test
    public void testESDelete() {
        //测试删除ES数据
        discussPostRepository.deleteAll();
    }

    @Test
    public void testESSearchByRepository() {
        //构造搜索条件 ---> QueryBuilders.multiMatchQuery[复合]
        //构造排序条件 ---> SortBuilders
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        //查询
        Page<DiscussPost> discussPosts = discussPostRepository.search(searchQuery);
        System.out.println(discussPosts.getTotalElements());
        System.out.println(discussPosts.getTotalPages());
        System.out.println(discussPosts.getNumber());
        System.out.println(discussPosts.getSize());
        for (DiscussPost discussPost : discussPosts) {
            System.out.println(discussPost.toString());
        }
    }

    @Test
    public void testESSearchByTemplate() {
        //构造搜索条件 ---> QueryBuilders.multiMatchQuery[复合]
        //构造排序条件 ---> SortBuilders
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        Page<DiscussPost> discussPosts = elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
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
        System.out.println(discussPosts.getTotalElements());
        System.out.println(discussPosts.getTotalPages());
        System.out.println(discussPosts.getNumber());
        System.out.println(discussPosts.getSize());
        for (DiscussPost discussPost : discussPosts) {
            System.out.printf(discussPost.toString());
        }
    }
}
