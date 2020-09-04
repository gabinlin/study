package top.gabin.concurrent.productAndConsum;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 一个固定大小的容器
 * 支持2个生产者线程
 * 支持10个消费者线程
 * 阻塞调用
 */
public class FixedSizeContainer {
    private volatile Queue queue = new LinkedBlockingQueue(10);

    // 唯一添加元素的入口
    public void put(Object object) {
        if (queue.size() < 10) {
            queue.offer(object);
        }
    }

    // 唯一获取的入口
    public Object get() {
        return queue.poll();
    }

    public static void main(String[] args) {
        FixedSizeContainer container = new FixedSizeContainer();
        Thread product1 = new Thread(() -> {
            container.put("product1");
        }, "product1");
        product1.start();
        Thread product2 = new Thread(() -> {
            container.put("product2");
        }, "product2");
        product2.start();

        for (int i = 0; i < 10; i++) {
            Thread consumer = new Thread(() -> {
                Object o = container.get();
                System.out.println(o);
            }, "consumer" + i);
            consumer.start();
        }
    }

}
