package com.zwm.util;

public interface CommunityConstantTwo {
    //默认状态的登录凭证的超时时间 12 个小时
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    //记住状态的登录凭证超时时间 100天
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    //评论为回复帖子的类型
    int ENTITY_TYPE_POST = 1;

    //评论为回复评论的类型
    int ENTITY_TYPE_COMMENT = 2;

    //实体为用户类型
    int ENTITY_TYPE_USER = 3;
}
