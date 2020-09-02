package top.gabin.jvm.instance;

import org.openjdk.jol.info.ClassLayout;

public class ObjectLayout {
    public static void main(String[] args) {
        Object o = new Object();
        logLayout(o);
        synchronized (o) { // 打印出来就会发现markword部分变了，说明里面记录了锁的信息
            logLayout(o);
        }
        logLayout(new Object[2]);
    }

    private static void logLayout(Object o) {
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
