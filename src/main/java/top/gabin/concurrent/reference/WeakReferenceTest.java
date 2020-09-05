package top.gabin.concurrent.reference;

import java.lang.ref.WeakReference;

/**
 * 运行参数：-XX:+PrintGC -Xms11m -Xmx11m -XX:+UseG1GC
 */
public class WeakReferenceTest {
    public static void main(String[] args) {
        WeakReference<byte[]> weakReference = new WeakReference<>(new byte[1024 * 1024 * 5]);
        // 这个时候还没回收，打印出来是正常的一个对象
        System.out.println(weakReference.get());
        System.gc();
        // 经过垃圾回收之后，弱引用会被回收，打印出来的是null
        System.out.println(weakReference.get());
    }
}
