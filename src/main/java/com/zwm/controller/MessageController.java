package com.zwm.controller;

import com.zwm.entity.Message;
import com.zwm.entity.Page;
import com.zwm.entity.User;
import com.zwm.service.impl.MessageServiceImpl;
import com.zwm.service.impl.UserServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class MessageController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(path = "/letter/list", method = RequestMethod.GET)
    public String getLetterList(Model model, Page page) {
        User user = hostHolder.getUser();
        //分页信息
        page.setLimit(5);
        page.setPath("/letter/list/");
        page.setRows(messageService.findConversationCount(user.getId()));
        //会话列表
        List<Message> conversationList = messageService.findConversations(user.getId(), page.getStart(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            //获取会话集合里的每一个会话
            for (Message conversation : conversationList) {
                Map<String, Object> map = new HashMap<>();
                map.put("conversation", conversation);
                //每个会话里头包含着私信总数量
                map.put("letterCount", messageService.findLetterCount(conversation.getConversationId()));
                //每个会话里头包含着未读私信总数量
                map.put("unreadCount", messageService.findLetterUnreadCount(user.getId(), conversation.getConversationId()));
                //显示发信人的用户信息
                //如果发信人是自己显示收信人的用户信息
                //如果收信人是自己显示发信人的用户信息
                int targetId = user.getId() == conversation.getFromId() ? conversation.getToId() : conversation.getFromId();
                map.put("target", userService.findUserById(targetId));
                //将每个会话的资源包放入一个大的资源包中
                conversations.add(map);
            }
        }
        model.addAttribute("conversations", conversations);
        //查询未读消息的数量
        int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(), null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);
        return "/site/letter";
    }

    @RequestMapping(value = "/letter/detail/{conversationId}", method = RequestMethod.GET)
    public String getConversationDetail(@PathVariable(value = "conversationId") String conversationId, Model model, Page page) {
        //分页信息
        page.setLimit(5);
        page.setPath("/letter/detail/" + conversationId);
        page.setRows(messageService.findLetterCount(conversationId));
        //私信列表
        List<Message> letterList = messageService.findLetters(conversationId, page.getStart(), page.getLimit());
        List<Map<String, Object>> mapList = new ArrayList<>();
        //每条私信里面有用户头像信息
        if (letterList != null) {
            for (Message letter : letterList) {
                //使用 Map 数据结构存储数据
                Map<String, Object> map = new HashMap<>();
                map.put("letter", letter);
                map.put("fromUser", userService.findUserById(letter.getFromId()));
                //将 Map 数据结构资源包放到一个大的资源包中
                mapList.add(map);
            }
        }
        model.addAttribute("letters", mapList);
        model.addAttribute("target", getLetterTarget(conversationId));
        //点开以后之前未读消息将变成已读 getLetterIds 封装专门的方法获取未读消息的 ids
        List<Integer> unReadIds = getLetterIds(letterList);
        //如果存在未读消息，则将未读消息设置为已读
        if (!unReadIds.isEmpty()) {
            messageService.updateStatus(unReadIds, 1);
        }

        return "/site/letter-detail";
    }

    //不要显示当前用户的信息，尽量显示他人的用户信息
    private User getLetterTarget(String conversationId) {
        String[] ids = conversationId.split("_");
        int id0 = Integer.parseInt(ids[0]);
        int id1 = Integer.parseInt(ids[1]);

        if (hostHolder.getUser().getId() == id0) {
            return userService.findUserById(id1);
        } else {
            return userService.findUserById(id0);
        }
    }

    //获取当前会话中未读消息的 id
    private List<Integer> getLetterIds(List<Message> messageList) {
        List<Integer> unReadIds = new ArrayList<>();
        if (messageList != null) {
            for (Message message : messageList) {
                //如果当前状态为 0 表示未读消息，并且当前用户是接收方
                if (message.getStatus() == 0 && hostHolder.getUser().getId() == message.getToId()) {
                    unReadIds.add(message.getId());
                }
            }
        }
        return unReadIds;
    }

    @RequestMapping(path = "/letter/send", method = RequestMethod.POST)
    @ResponseBody
    public String sendLetter(String toName, String content) {
        //根据用户名查找用户
        User toUser = userService.selectUserByUsername(toName);
        //获取收信人用户 ID
        int toId = toUser.getId();
        //获取当前用户的 ID
        User fromUser = hostHolder.getUser();
        int fromId = fromUser.getId();
        Message message = new Message();
        message.setContent(content);
        message.setFromId(fromId);
        message.setToId(toId);
        message.setStatus(0);
        message.setCreateTime(new Date());
        message.setConversationId(fromId < toId ? (fromId + "_" + toId) : (toId + "_" + fromId));
        messageService.insertMessage(message);
        return CommunityUtils.getJsonString(0);
    }
}
