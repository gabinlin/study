package top.gabin.concurrent;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            LockSupport.park();
            System.out.println("等待结束");
        });
        thread1.start();
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        LockSupport.unpark(thread1);
    }
}
