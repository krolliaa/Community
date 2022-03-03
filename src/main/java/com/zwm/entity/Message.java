package com.zwm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private int fromId;//发信人Id
    private int toId;//收信人Id
    private String conversationId;//交流ID，如：111_112
    private String content;//私信内容
    private int status;//当前状态：0-未读 1-已读 2-删除
    private Date createTime;//私信创建时间

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", conversationId='" + conversationId + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
