import com.zwm.CommunityApplication;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class KafkaTest {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void test() throws InterruptedException {
        kafkaProducer.sendMessage("tests", "hello?");
        kafkaProducer.sendMessage("tests", "anybody home?");
        Thread.sleep(1000 * 10);
    }

}

@Component
class KafkaProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    //生产消息
    public void sendMessage(String topic, String content) {
        kafkaTemplate.send(topic, content);
    }
}

@Component
class KafkaConsumer {
    @KafkaListener(topics = {"tests"})
    public void handleMessage(ConsumerRecord consumerRecord) {
        System.out.println(consumerRecord.value());
    }
}
