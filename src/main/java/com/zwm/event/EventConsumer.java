package com.zwm.event;

import com.alibaba.fastjson.JSON;
import com.zwm.entity.Event;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import static com.zwm.util.CommunityConstantTwo.*;

public class EventConsumer {

    private static Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    //消费者消费消息 ---> 生产者生产的是事件对象
    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handlerEvent(ConsumerRecord consumerRecord) {
        //如果消费到的消息是null或者是空的，日志报错
        if (consumerRecord == null || consumerRecord.value() == null) {
            logger.error("消息的内容为空!");
            return;
        }

        //获取到 JSON 对象转化为 Event 事件对象
        Event event = JSON.parseObject(consumerRecord.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        //获取到消息就可以发送系统消息了，当时发送消息的时候使用的是 MessageService 所以这里也可以使用 MessageService
        

    }
}
