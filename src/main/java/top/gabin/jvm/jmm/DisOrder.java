package top.gabin.jvm.jmm;

/**
 * CPU重排序的问题
 * <p>
 * 如果指令1和指令2之间没有依赖关系，那么在CPU执行的时候，可能先执行指令2，再去执行指令1
 * <p>
 * 比如说一般一个方法的JVM汇编指令第一个是去加载this对象，从内存中读取的速度远低于CPU 寄存器执行的时间，这时候可能
 * 第二条指令只需要执行1ms，那么有可能，CPU会选择先执行第二条指令
 * <p>
 * 这个在多线程中可能存在一个执行顺序的问题，
 * 比如
 * <p>
 * 线程1
 * b=1
 * x=a
 * <p>
 * 线程2
 * a=1
 * y=b
 * <p>
 * 看上去线程1的两行代码和线程2的两行代码没有直接关联，所以存在CPU指令乱序的可能性，
 */
public class DisOrder {

    private static int x, y, a, b;

    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread t1 = new Thread(() -> {
                // 不加这行的话，如果CPU执行速度很快，说不准，压根就测不出来，这个问题可以类比到某些手机端IOS根本出现不了问题，但是ANDROID就会
                // 以前大概遇到过一次是IOS渲染比较快，所以就不会出现问题，ANDROID渲染执行比较慢，出现了执行顺序的问题
                shortWait(100000);
                // 这里必须是很单纯的代码调用，不能是非原子性的，比如long，64位，不是原子性操作
                a = 1;
                x = b;
            });
            Thread t2 = new Thread(() -> {
                // 这里必须是很单纯的代码调用，不能是非原子性的，比如long，64位，不是原子性操作
                b = 1;
                y = a;
            });
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            if (x == 0 && y == 0) {
                System.out.println("存在CPU指令重排，x==y==0");
                break;
            }
        }
    }

    public static void shortWait(long interval) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + interval >= end);
    }

}
