package com.zwm.event;

import com.alibaba.fastjson.JSONObject;
import com.zwm.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //触发事件
    public void fireEvent(Event event) {
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSON(event));
    }
}
