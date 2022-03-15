package com.zwm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "discusspost", indexStoreType = "_doc", shards = 6, replicas = 3)
public class DiscussPost {
    @Id
    private int id;//帖子ID
    @Field(type = FieldType.Integer)
    private int userId;//用户ID
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;//帖子标题
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;//帖子内容
    @Field(type = FieldType.Integer)
    private int type;//帖子类型：0-普通 + 1-置顶
    @Field(type = FieldType.Integer)
    private int status;//帖子状态：0-普通 + 1-精华 + 2-拉黑
    @Field(type = FieldType.Integer)
    private int commentCount;//帖子评论数量
    @Field(type = FieldType.Date)
    private Date createTime;//帖子创建日期
    @Field(type = FieldType.Double)
    private Double score;//帖子获得的分数

    @Override
    public String toString() {
        return "DiscussPost{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", commentCount=" + commentCount +
                ", createTime=" + createTime +
                ", score=" + score +
                '}';
    }
}
