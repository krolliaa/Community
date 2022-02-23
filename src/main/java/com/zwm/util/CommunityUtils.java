package com.zwm.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtils {
    //获取随机字符串用于生成激活码、密码加盐等
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll(",", "");
    }

    //密码加盐加MD5
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) return null;
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}