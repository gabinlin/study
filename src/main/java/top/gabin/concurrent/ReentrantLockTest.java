package top.gabin.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 与synchronized不同点试验
 * 1、需要显式解锁 try final {unLock}
 * 2、可以使用tryLock
 * 3、条件等待
 * 4、指定获取锁的超时时间
 * 5、CAS实现机制
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        // 只会打印出1，没拿到锁就放弃
        testTryLock();
        // 打印1、2，等待2秒后成功拿到锁
        testTryLockWaiting();
        testCondition();
    }

    private static void testCondition() throws InterruptedException {
        ReentrantLock lock = getLock();
        Condition condition = lock.newCondition();
        CountDownLatch latch = new CountDownLatch(1);
        Thread thread1 = new Thread(() -> {
            try {
                latch.await();
                lock.lock();
                condition.await();
                System.out.println("t1条件等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                lock.lock();
                condition.signal();
            } finally {
                latch.countDown();
                lock.unlock();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }


    private static void testTryLock() throws InterruptedException {
        ReentrantLock lock = getLock();
        Thread thread1 = new Thread(() -> {
            try {
                if (lock.tryLock()) {
                    System.out.println(1);
                    TimeUnit.SECONDS.sleep(2);
                    try {

                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            if (lock.tryLock()) {
                System.out.println(2);
                try {
                } finally {
                    lock.unlock();
                }
            }
        });
        thread2.start();
        thread1.join();
        thread2.join();
    }

    private static ReentrantLock getLock() {
        return new ReentrantLock();
    }

    private static void testTryLockWaiting() throws InterruptedException {
        ReentrantLock lock = getLock();
        Thread thread1 = new Thread(() -> {
            try {
                if (lock.tryLock()) {
                    System.out.println(1);
                    TimeUnit.SECONDS.sleep(2);
                    try {

                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            try {
                if (lock.tryLock(2, TimeUnit.SECONDS)) {
                    System.out.println(2);
                    try {
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
        thread1.join();
        thread2.join();
    }

}
