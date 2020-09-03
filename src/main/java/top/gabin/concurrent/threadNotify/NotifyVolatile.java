package top.gabin.concurrent.threadNotify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 面试题：
 * 写一个容器，
 * 2个线程
 * 线程1往容器添加10个对象
 * 线程2监控容器数量到达5的时候，打印并停止
 */
public class NotifyVolatile {
    volatile List<Object> list = Collections.synchronizedList(new ArrayList<>());

    void add(Object o) {
        list.add(o);
    }

    int size() {
        return list.size();
    }

    public static void main(String[] args) {
        NotifyVolatile notifyVolatile = new NotifyVolatile();
        notifyVolatile.wayOne();
        notifyVolatile.wayTwo();
    }

    private void wayOne() {
        CountDownLatch end = new CountDownLatch(5);
        CountDownLatch start = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                add(new Object());
                System.out.println(i);
                end.countDown();
                if (end.getCount() == 0) {
                    try {
                        start.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                end.await();
                start.countDown();
                System.out.println("t2执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();
        t1.start();
        try {
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void wayTwo() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                add(new Object());
                System.out.println(i);
                synchronized (this) {
                    if (size() == 5) {
                        this.notify();
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (this) {
                try {
                    this.wait();
                    System.out.println("t2执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    this.notify();
                }
            }
        });
        t2.start();
        t1.start();
        try {
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
