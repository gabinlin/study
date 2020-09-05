package top.gabin.concurrent.reference;

/**
 * 强引用
 * -XX:+PrintGC -Xms11m -Xmx11m -XX:+UseG1GC
 * 打印垃圾回收，最大和最小内存设置为11M,使用G1垃圾回收器（默认的垃圾回收器，直接oom了）
 * 日志：
 * [GC pause (G1 Humongous Allocation) (young) (initial-mark) 1770K->592K(12M), 0.0027535 secs]
 * [GC concurrent-root-region-scan-start]
 * [GC pause (G1 Humongous Allocation) (young)[GC concurrent-root-region-scan-end, 0.0006785 secs]
 * [GC concurrent-mark-start]
 *  592K->518K(12M), 0.0023798 secs]
 * [GC concurrent-mark-end, 0.0018801 secs]
 * [GC remark, 0.0012885 secs]
 * [GC pause (G1 Evacuation Pause) (young)-- 10M->518K(12M), 0.0070966 secs]
 * [GC cleanup 621K->621K(12M), 0.0004545 secs]
 * [B@60e53b93
 */
public class StrongReference {
    public static void main(String[] args) {
        // 分配10M,
        byte[] bytes = new byte[1024 * 1024 * 10];
        // 原来以为到不了这里，结果发现G1如果把bytes变量设置为null，会触发垃圾回收，不会直接oom
        // 使用了默认的PS和PO的垃圾回收器，走不到这里，就oom了
        // 这里就不看class文件了，代码一样，只是改了JVM参数
        bytes = null; // 这里
        bytes = new byte[1024 * 1024 * 1];
        System.out.println(bytes);
    }
}
