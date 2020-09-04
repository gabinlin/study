package top.gabin.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    private volatile static int status;
    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 1000; i++) {
            if (i % 30 == 0) {
                new Thread(()->{
                    try {
                        latch.await();
                        writeLock.lock();
                        status = ThreadLocalRandom.current().nextInt(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        writeLock.unlock();
                    }
                }).start();
            }
            new Thread(()->{
                try {
                    readLock.lock();
                    System.out.println(status);
                } finally {
                    readLock.unlock();
                    latch.countDown();
                }
            }).start();
        }

    }
}
