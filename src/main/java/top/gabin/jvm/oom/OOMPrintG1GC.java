package top.gabin.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * ###########################################################################
 * 打印普通的GC日志
 * 设置运行时参数（JVM）-Xms10m -Xmx10m -XX:+PrintGC -XX:+PrintGCDetails -XX:+UseG1GC
 * -Xms 最小内存
 * -Xmx 最大内存
 * -XX:+PrintGC 打印GC日志
 * -XX:+PrintGCDetails 打印GC详细日志
 * -XX:+UseG1GC 使用Garbage First垃圾回收器，Java7中出现，Java9中默认
 *
 * <p>
 * 参考日志
 * 0.138: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 5835K->3578K(10M), 0.0201075 secs]
 * # 解释，时间戳 [YGC 暂停(G1 大对象分配)(年轻代)(初始标记) 整理前内存->整理后内存(总内存),处理时间]
 * 0.158: [GC concurrent-root-region-scan-start]
 * # 解释：时间戳[GC 并发根区域扫描，应该和CMS一样，是扫描根变量下直接关联的对象]
 * 0.159: [GC concurrent-root-region-scan-end, 0.0007647 secs]
 * # 解释:[GC 扫描结束，话费时间]
 * 0.159: [GC concurrent-mark-start]
 * # 解释:时间戳:[并发标记开始]
 * 0.162: [GC concurrent-mark-end, 0.0031228 secs]
 * # 解释:时间戳: [并发标记结束，花费时间]
 * 0.162: [GC remark, 0.0012451 secs]
 * # 解释: 时间戳:[重新标记，花费时间]
 * 0.164: [GC cleanup 5729K->4166K(10M), 0.0004791 secs]
 * # 解释：时间戳:[GC 清理 清理前内存大小->清理后内存大小(内存大小),花费时间]
 * 0.164: [GC concurrent-cleanup-start]
 * 0.164: [GC concurrent-cleanup-end, 0.0000155 secs]
 * 0.164: [GC pause (G1 Humongous Allocation) (young) 4166K->4219K(10M), 0.0159309 secs]
 * 0.180: [Full GC (Allocation Failure)  4219K->2479K(10M), 0.0123721 secs]
 * 0.199: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 5767K->5696K(10M), 0.0076014 secs]
 * 0.207: [GC concurrent-root-region-scan-start]
 * 0.207: [GC pause (G1 Humongous Allocation) (young)0.207: [GC concurrent-root-region-scan-end, 0.0001607 secs]
 * 0.207: [GC concurrent-mark-start]
 * 5696K->5699K(10M), 0.0027255 secs]
 * 0.215: [Full GC (Allocation Failure)  5699K->3558K(10M), 0.0152234 secs]
 * 0.231: [GC concurrent-mark-abort]
 * 0.239: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 8306K->8306K(10M), 0.0042204 secs]
 * 0.243: [GC concurrent-root-region-scan-start]
 * 0.243: [GC concurrent-root-region-scan-end, 0.0000139 secs]
 * 0.243: [GC concurrent-mark-start]
 * 0.243: [GC pause (G1 Humongous Allocation) (young) 8306K->8306K(10M), 0.0011212 secs]
 * 0.253: [Full GC (Allocation Failure)  8306K->5140K(10M), 0.0243183 secs]
 * # 解释：时间戳:[Full GC(分配对象失败) 整理前大小->整理后内存大小(总内存大小)，花费时间]
 * 0.277: [Full GC (Allocation Failure)  5140K->5123K(10M), 0.0233785 secs]
 * 0.301: [GC concurrent-mark-abort]
 * # 解释：时间戳:[并发标记中断]
 * ###########################################################################
 */
public class OOMPrintG1GC {

    public static void main(String[] args) {
        List<Object> objects = new ArrayList<>();
        while (true) {
            objects.add("");
        }
    }
}
