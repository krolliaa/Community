import com.zwm.CommunityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testHyperLogLogNoRepeat() {
        String redisKey0 = "test:hyperloglog";
        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey0, i);
        }
        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey0, (int) (Math.random() * 10000 + 1));
        }
        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKey0));
    }

    @Test
    public void testHyperLogLogMerge() {
        String redisKey1 = "test:hyperloglog:redisKey1";
        String redisKey2 = "test:hyperloglog:redisKey2";
        String redisKey3 = "test:hyperloglog:redisKey3";
        String redisKeyUnion = "test:hyperloglog:redisKeyUnion";
        for (int i = 1; i <= 10; i++) redisTemplate.opsForHyperLogLog().add(redisKey1, i);
        for (int i = 11; i <= 20; i++) redisTemplate.opsForHyperLogLog().add(redisKey2, i);
        for (int i = 21; i <= 30; i++) redisTemplate.opsForHyperLogLog().add(redisKey3, i);
        redisTemplate.opsForHyperLogLog().union(redisKeyUnion, redisKey1, redisKey2, redisKey3);
        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKeyUnion));
    }

    @Test
    public void testBitmapStatistics() {
        String redisKey0 = "test:bitmap:redisKey0";
        redisTemplate.opsForValue().setBit(redisKey0, 0, true);
        redisTemplate.opsForValue().setBit(redisKey0, 2, true);
        redisTemplate.opsForValue().setBit(redisKey0, 4, true);
        for (int i = 1; i <= 4; i++) System.out.println(redisTemplate.opsForValue().getBit(redisKey0, i));
        Object object = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(redisKey0.getBytes());
            }
        });
        System.out.println(object);
    }

    @Test
    public void testBitmapOperation() {
        String redisKey0 = "test:bitmap:redisKey0";
        String redisKey1 = "test:bitmap:redisKey1";
        String redisKey2 = "test:bitmap:redisKey2";
        redisTemplate.opsForValue().setBit(redisKey0, 1, true);
        redisTemplate.opsForValue().setBit(redisKey0, 3, true);
        redisTemplate.opsForValue().setBit(redisKey0, 5, true);
        redisTemplate.opsForValue().setBit(redisKey1, 1, false);
        redisTemplate.opsForValue().setBit(redisKey1, 3, true);
        redisTemplate.opsForValue().setBit(redisKey1, 5, true);
        redisTemplate.opsForValue().setBit(redisKey2, 1, true);
        redisTemplate.opsForValue().setBit(redisKey2, 3, true);
        redisTemplate.opsForValue().setBit(redisKey2, 5, true);
        String redisKeyOperation = "test:bitmap:redisOperation";
        Object object = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.bitOp(RedisStringCommands.BitOperation.AND, redisKeyOperation.getBytes(), redisKey0.getBytes(), redisKey1.getBytes(), redisKey2.getBytes());
                return redisConnection.bitCount(redisKeyOperation.getBytes());
            }
        });
        System.out.println(object);
        for (int i = 0; i < 6; i++) System.out.println(redisTemplate.opsForValue().getBit(redisKeyOperation, i));
    }
}
