package com.zwm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussPost {
    private int id;//帖子ID
    private int userId;//用户ID
    private String title;//帖子标题
    private String content;//帖子内容
    private int type;//帖子类型：0-普通 + 1-置顶
    private int status;//帖子状态：0-普通 + 1-精华 + 2-拉黑
    private int commentCount;//帖子评论数量
    private Date createTime;//帖子创建日期
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
