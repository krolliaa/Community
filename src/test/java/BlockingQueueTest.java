import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(3);
        new Thread(new Producer(blockingQueue)).start();
        for (int i = 0; i < 20; i++) {
            new Thread(new Consumer(blockingQueue)).start();
        }
    }
}

class Producer implements Runnable {

    private BlockingQueue<Integer> blockingQueue;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        //生产者每 20 ms 生产一个消息，生产 100 个消息
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(20);
                blockingQueue.put(i);
                System.out.println(Thread.currentThread().getName() + " 生产消息：" + blockingQueue.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Integer> blockingQueue;

    public Consumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        //消费者随机时间消费数据
        try {
            Thread.sleep(new Random().nextInt(1000));
            blockingQueue.take();
            System.out.println(Thread.currentThread().getName() + " 消费消息： " + blockingQueue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}