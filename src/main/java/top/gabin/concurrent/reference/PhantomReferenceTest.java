package top.gabin.concurrent.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 运行参数：-XX:+PrintGC -Xms11m -Xmx11m -XX:+UseG1GC
 */
public class PhantomReferenceTest {
    public static void main(String[] args) {
        ReferenceQueue linkedList = new ReferenceQueue<>();
        PhantomReference<byte[]> reference = new PhantomReference<byte[]>(new byte[1024 * 1024 * 6], linkedList);
        // 没回收也取不到，是NULL，虚引用的主要作用在于第二个参数，gc之后，ReferenceQueue会有被回收的对象
        System.out.println(reference.get());
        System.gc();
        // 队列里面会有被回收的对象
        System.out.println(linkedList.poll());
    }
}
