package top.gabin.jmm;

/**
 * CPU，硬件级别的缓存是一行读取的，一般是64位
 * <p>
 * 如果4字节的x和4字节的y同时存储在同一个缓存行中
 * <p>
 * 那么当对x做写操作的时候，需要阻塞对y的操作
 * 对y的操作也要阻塞x的操作
 * <p>
 * 所以理论上，如果能够将锁需要读取的数据独立在一个缓存行中，或者在同一个缓存行的其他数据只读，则可以提高读写效率
 */
public class CPUCacheLinePadding {

    static class DataContainer {
        private volatile long p1, p2, p3, p4, p5, p6, p7;
        public volatile long data; // 真正有用的数据
        private volatile long p8, p9, p10, p11, p12, p13, p14;

    }

    static class DataContainer1 {
        public volatile long data;

    }

    public static void main(String[] args) throws InterruptedException {
        // 伪缓存问题

        // 1、使用了补齐的方式，使得真正有意义的数据被保存在一个缓存行中，不和其他会被写的数据一起
        long start = System.currentTimeMillis();
        test1();
        System.out.println("补齐，解决伪共享问题:" + (System.currentTimeMillis() - start));
        // 2、普通的方式
        start = System.currentTimeMillis();
        test2();
        System.out.println("一般场景:" + (System.currentTimeMillis() - start));

    }

    private static void test1() throws InterruptedException {
        DataContainer[] containers = new DataContainer[2];
        containers[0] = new DataContainer();
        containers[1] = new DataContainer();

        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 100_000_000; j++) {
                containers[0].data = j;
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            for (int j = 0; j < 100_000_000; j++) {
                containers[1].data = j;
            }
        });
        t2.start();
        t1.join();
        t2.join();

    }

    private static void test2() throws InterruptedException {
        DataContainer1[] containers = new DataContainer1[2];
        containers[0] = new DataContainer1();
        containers[1] = new DataContainer1();
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 100_000_000; j++) {
                containers[0].data = j;
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            for (int j = 0; j < 100_000_000; j++) {
                containers[1].data = j;
            }
        });
        t2.start();
        t1.join();
        t2.join();
    }

}
