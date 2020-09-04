package top.gabin.concurrent;

import java.util.concurrent.Exchanger;

public class ExchangeTest {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            new Thread(()-> {
                try {
                    String exchange = exchanger.exchange(finalI + "");
                    System.out.println("线程" + finalI + "获取的数据" + exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
