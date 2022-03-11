package com.zwm.entity;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private String topic; //区分类型 ---> CommunityConstantTwo
    private int userId; //是谁评论点赞关注了
    private int entityType; //评论点赞关注的实体类型
    private int entityId; //评论点赞关注的实体id
    private int entityUserId; //评论点赞关注了哪个用户
    private Map<String, Object> data = new HashMap<>(); //后续可能有其它数据都可以放到 Map 中去

    //增添 Getter 和 Setter 方法支持链式编程并对 Map 做一个小改动

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "Event{" +
                "topic='" + topic + '\'' +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", entityUserId=" + entityUserId +
                ", data=" + data +
                '}';
    }
}