package top.gabin.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * ###########################################################################
 * 打印普通的GC日志
 * 设置运行时参数（JVM）-Xms10m -Xmx10m -XX:+PrintGC
 * -Xms 最小内存
 * -Xmx 最大内存
 * -XX:+PrintGC
 * 14的版本也不知道不支持哪个，X和XX分别代表，非标准参数和不稳定参数，非标准应该不难理解吧，不稳定大概是指不同的版本可能不支持
 * <p>
 * 打印出来的日志参考：
 * [GC (Allocation Failure)  1885K->697K(9728K), 0.0032099 secs]
 * [GC (Allocation Failure)  2118K->1354K(9728K), 0.0025982 secs]
 * [GC (Allocation Failure)  5893K->4863K(9728K), 0.0060923 secs]
 * [GC (Allocation Failure)  4863K->4847K(9728K), 0.0057476 secs]
 * [Full GC (Allocation Failure)  4847K->2477K(9728K), 0.0495625 secs]
 * [GC (Allocation Failure)  5699K->5835K(9728K), 0.0015904 secs]
 * [GC (Allocation Failure)  5835K->5739K(8704K), 0.0014995 secs]
 * [Full GC (Allocation Failure)  5739K->3536K(8704K), 0.0658359 secs]
 * [GC (Allocation Failure)  3536K->3536K(9216K), 0.0016345 secs]
 * [Full GC (Allocation Failure)  3536K->3518K(9216K), 0.0319355 secs]
 * <p>
 * <p>
 * 第一位：【GC】表示YGC，MinorGC，年轻代的垃圾回收；【Full GC】表示Old GC，Major GC，老年代GC（会触发STW：stop the world，全代垃圾回收）
 * 第二位：括号里面表示垃圾回收的原因，Allocation Failure表示分配内存失败
 * 第三位：回收前内存->回收后内存(总内存)
 * 第四位: 执行时间
 * <p>
 * ###########################################################################
 */
public class OOMTest1 {

    public static void main(String[] args) {
        List<Object> objects = new ArrayList<>();
        while (true) {
            objects.add("");
        }
    }
}
