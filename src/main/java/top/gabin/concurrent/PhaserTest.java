package top.gabin.concurrent;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PhaserTest {
    public static void main(String[] args) {
        // 10对应的参数名是parties，所以这边就用party来做例子吧
        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                switch (phase) {
                    case 0:
                        System.out.println("派对召集ing");
                        return false;
                    case 1:
                        System.out.println("所有人都到达现场");
                        return false;
                    case 2:
                        System.out.println("剩下谁买单" + registeredParties);
                        return false;
                }
                return true;
            }
        };
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                phaser.register();
                phaser.arrive();
                // arriveAndAwaitAdvance方法需要等待下个屏障中的线程开始执行，才会依次推进
//                phaser.arriveAndAwaitAdvance();
                System.out.println(finalI + "我到了,准备开吃");
                try {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (finalI < 9) {
                    phaser.arriveAndDeregister();
                } else {
                    phaser.arriveAndAwaitAdvance();
                    try {
                        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    phaser.arrive();
                    System.out.println(finalI + "我买单了");
                }
            }).start();
        }
    }
}
