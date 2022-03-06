package com.zwm.util;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    //某个实体的赞，包括帖子的赞和评论的赞，传入实体类型判断是帖子还是评论的赞，传入实体ID判断是哪个帖子或者哪个评论
    //用一个集合，因为集合是无序不重复的，当存入 userId 的时候表示某个 id 为 userId 的用户给该实体点了个赞
    //如果一个用户即给帖子点了赞又给评论点了赞不是重复了吗？
    //不对，这个 entityType 和 entityId 是不一样的，key 不一样就不用担心这个问题
    //使用集合可以防止重复 ---> 因为点双数的赞就是取消点赞
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
