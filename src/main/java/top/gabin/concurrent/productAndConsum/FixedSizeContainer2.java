package top.gabin.concurrent.productAndConsum;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个固定大小的容器
 * 支持2个生产者线程
 * 支持10个消费者线程
 * 阻塞调用
 */
public class FixedSizeContainer2 {
    private final static int MAX = 10;
    private volatile Queue queue = new LinkedList();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition product = lock.newCondition();
    private final Condition consume = lock.newCondition();

    // 唯一添加元素的入口
    public void put(Object object) {
        try {
            lock.lock();
            while (queue.size() == MAX) {
                try {
                    product.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(object);
            consume.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // 唯一获取的入口
    public Object get() {
        try {
            lock.lock();
            while (queue.isEmpty()) {
                try {
                    consume.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Object poll = queue.poll();
//            product.notify();
            product.signalAll();
            return poll;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        FixedSizeContainer2 container = new FixedSizeContainer2();

        for (int i = 0; i < 10; i++) {
            Thread consumer = new Thread(() -> {
                Object o = container.get();
                System.out.println(o);
                o = container.get();
                System.out.println(o);
            }, "consumer" + i);
            consumer.start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    container.put("product" + j);
                }
            }, "product" + i).start();
        }
    }

}
