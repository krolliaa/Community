package com.zwm.util;

public interface CommunityConstantTwo {
    //默认状态的登录凭证的超时时间 12 个小时
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    //记住状态的登录凭证超时时间 100天
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;
}
