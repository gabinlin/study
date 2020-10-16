package top.gabin.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadExchangeDataTest {


    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<Integer> queue2 = new LinkedBlockingQueue<>();
        queue1.add(0);
        Thread t1, t2;
        t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    if (queue1.take() == 0) {
                        System.out.println(i);
                        queue2.put(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2 = new Thread(() -> {
            for (int i = 'A'; i < 'K'; i++) {
                try {
                    if (queue2.take() == 1) {
                        System.out.println((char) i);
                        queue1.put(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }

    static AtomicInteger atomicInteger = new AtomicInteger();

    public static void way4() {
        Thread t1, t2;
        t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                while (!(atomicInteger.get() == 0)) {
                }
                System.out.println(i);
                atomicInteger.incrementAndGet();
            }
        });
        t1.start();
        t2 = new Thread(() -> {
            for (int i = 'A'; i < 'K'; i++) {
                while (!(atomicInteger.get() == 1)) {
                }
                System.out.println((char) i);
                atomicInteger.decrementAndGet();
            }
        });
        t2.start();
    }

    public static void way3() {
        Thread t1, t2;
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        t1 = new Thread(() -> {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                condition1.signal();
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2 = new Thread(() -> {
            lock.lock();
            for (int i = 'A'; i < 'K'; i++) {
                System.out.println((char) i);
                condition2.signal();
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }

    static Thread t1, t2;

    public static void way2() {
        t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });
        t1.start();
        t2 = new Thread(() -> {
            for (int i = 'A'; i < 'K'; i++) {
                System.out.println((char) i);
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        });
        t2.start();
    }

    public static void way1() {
        Thread t1, t2;
        Object lock = new Object();
        t1 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    System.out.println(i);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        t2 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 'A'; i < 'K'; i++) {
                    System.out.println((char) i);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t2.start();
    }

}
