package com.zwm.util;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOWER = "follower";
    private static final String PREFIX_FOLLOWEE = "followee";
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET = "ticket";

    //某个实体的赞，包括帖子的赞和评论的赞，传入实体类型判断是帖子还是评论的赞，传入实体ID判断是哪个帖子或者哪个评论
    //用一个集合，因为集合是无序不重复的，当存入 userId 的时候表示某个 id 为 userId 的用户给该实体点了个赞
    //如果一个用户即给帖子点了赞又给评论点了赞不是重复了吗？
    //不对，这个 entityType 和 entityId 是不一样的，key 不一样就不用担心这个问题
    //使用集合可以防止重复 ---> 因为点双数的赞就是取消点赞
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    //存放用户收到的赞 ---> like:user:1 100 ---> 使用 increment 和 decrement 自增自减
    public static String getUserLikeKey(int entityUserId) {
        return PREFIX_USER_LIKE + SPLIT + entityUserId;
    }

    //获取关注 ---> 某个用户关注了某个类型的实体 ---> value 为实体 id
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWER + SPLIT + userId + SPLIT + entityType;
    }

    //获取粉丝 ---> 实体类型和实体 Id 可以唯一确定一个 实体
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWEE + SPLIT + entityType + SPLIT + entityId;
    }

    //生成验证码Key
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    //生成登录凭证
    public static String getLoginTicketKey(String loginTicket) {
        return PREFIX_TICKET + SPLIT + loginTicket;
    }
}