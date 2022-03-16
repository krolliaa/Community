package com.zwm.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwm.dao.DiscussPostMapper;
import com.zwm.entity.DiscussPost;
import com.zwm.entity.Event;
import com.zwm.entity.Message;
import com.zwm.service.impl.DiscussPostServiceImpl;
import com.zwm.service.impl.ElasticsearchServiceImpl;
import com.zwm.service.impl.MessageServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.zwm.util.CommunityConstantTwo.*;

@Component
public class EventConsumer {

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private DiscussPostServiceImpl discussPostService;

    @Autowired
    private ElasticsearchServiceImpl elasticsearchService;

    private static Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    //消费者消费消息 ---> 生产者生产的是事件对象
    @KafkaListener(groupId = "test-consumer-group", topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handlerEvent(ConsumerRecord consumerRecord) {
        Event event = handleException(consumerRecord);
        if (event == null) return;
        //获取到消息就可以发送系统消息了，当时发送消息的时候使用的是 MessageService 所以这里要使用 MessageService
        //可以打印一下获取到的消息
        System.out.println(event.toString());
        //Event{topic='comment', userId=152, entityType=2, entityId=232, entityUserId=152, data=null}
        //根据传递过来的 Event 往数据库存储 Message 消息，我们规定 from_id = 1 表示系统用户
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        message.setCreateTime(new Date());
        //构造消息内容 ---> 内容为： XXX 评论了你的帖子/回复
        //XXX 就需要传入 userId ， 帖子/回复就需要传入 entityType，点击该内容可以跳转到具体的帖子/回复 ---> 所以还需要 entityId
        Map<String, Object> content = new HashMap<>();
        content.put("user", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());
        //如果传递过来的Event还有其它内容data的话，需要也将这些内容放到消息内容中去
        if (event.getData() != null && !(event.getData().isEmpty())) {
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }
        //存入JSON字符串格式的数据
        message.setContent(JSONObject.toJSONString(content));
        //将消息存入到数据库中
        messageService.insertMessage(message);
    }

    //消费发帖消息
    @KafkaListener(groupId = "test-consumer-group", topics = {TOPIC_PUBLISH})
    public void consumePublish(ConsumerRecord consumerRecord) {
        Event event = handleException(consumerRecord);
        if (event == null) return;
        //获取到帖子Id ---> event.getEntityId
        DiscussPost discussPost = discussPostService.selectDiscussPost(event.getEntityId());
        elasticsearchService.insertPostToES(discussPost);
    }

    //处理消费异常
    public Event handleException(ConsumerRecord consumerRecord) {
        //如果消费到的消息是null或者是空的，日志报错
        if (consumerRecord == null || consumerRecord.value() == null) {
            logger.error("消息的内容为空!");
            return null;
        }

        //获取到 JSON 对象转化为 Event 事件对象
        Event event = JSON.parseObject(consumerRecord.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return null;
        }
        return event;
    }
}
