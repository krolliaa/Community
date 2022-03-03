package com.zwm.dao;

import com.zwm.entity.Message;

import java.util.List;

public interface MessageMapper {
    //查询当前用户的所有会话，每个会话只显示私信最新一条消息，分页查询【排除系统通知即 from_id != 2】
    public abstract List<Message> selectConversations(int userId, int start, int limit);

    //查询当前用户的会话数量
    public abstract int selectConversationCount(int userId);

    //查询某个会话中所包含的私信列表
    public abstract List<Message> selectLetters(String conversationId, int start, int limit);

    //查询某个会话中所包含的私信列表的数量
    public abstract int selectLetterCount(String conversationId);

    //查询某个会话中未读私信的数量
    public abstract int selectLetterUnreadCount(int userId, String conversationId);
}
