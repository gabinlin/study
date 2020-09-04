package top.gabin.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5, () -> System.out.println("所有人都到了"));
        // 适用于多任务执行，最终得到一个结果的场景
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(finalI);
                    barrier.await();
                    System.out.println("收到" + finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
