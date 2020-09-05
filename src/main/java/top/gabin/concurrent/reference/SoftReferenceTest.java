package top.gabin.concurrent.reference;

import java.lang.ref.SoftReference;

/**
 * 运行参数：-XX:+PrintGC -Xms11m -Xmx11m -XX:+UseG1GC
 * 软应用在内存不够用的时候会触发垃圾回收
 */
public class SoftReferenceTest {

    public static void main(String[] args) {
        SoftReference<byte[]> softReference0 = new SoftReference<>(new byte[1024 * 1024 * 6]);
        SoftReference<byte[]> softReference1 = new SoftReference<>(new byte[1024 * 1024 * 6]);
        System.out.println(softReference0.get() + "\n"); // 空
        System.out.println(softReference1.get() + "\n"); // 不为空，证明内存不够用的时候会被回收
    }

}
