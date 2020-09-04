package top.gabin.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 门栓，和数字的倒计时相关，可以结合可重入锁来理解，比如上了10道锁之后，也必须解10到锁之后才可以释放
 * 同理门栓要倒计时到0之后才可以执行
 */
public class CountdownLatchTest {

    private volatile static Object single;
    private static CountDownLatch get = new CountDownLatch(1);

    public static Object getSingle() throws InterruptedException {
        get.await();
        return single;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()-> {
                try {
                    Object single = getSingle();
                    System.out.println(single);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 有一个单独的线程负责初始化，上面的get方法都需要等待
        new Thread(()-> {
            single = new Object();
            get.countDown();
        }).start();

    }

}
