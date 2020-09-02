package top.gabin.jvm.lock;

import java.util.concurrent.TimeUnit;

/**
 * 死锁示例
 *
 * 参考日志（jstack导出）：
 * 2020-09-02 06:26:19
 * Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.151-b12 mixed mode):
 *
 * "Attach Listener" #14 daemon prio=9 os_prio=31 tid=0x00007f9fcb008800 nid=0xa703 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "DestroyJavaVM" #13 prio=5 os_prio=31 tid=0x00007f9fcf08b800 nid=0xf03 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "Thread-1" #12 prio=5 os_prio=31 tid=0x00007f9fcf08a800 nid=0xa803 waiting for monitor entry [0x000070000f785000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 * 	at top.gabin.jvm.lock.DealLock.lambda$main$1(DealLock.java:32)
 * 	- waiting to lock <0x00000007bbedc520> (a java.lang.Object)
 * 	- locked <0x00000007bbedc530> (a java.lang.Object)
 * 	at top.gabin.jvm.lock.DealLock$$Lambda$2/1607521710.run(Unknown Source)
 * 	at java.lang.Thread.run(Thread.java:748)
 *
 * "Thread-0" #11 prio=5 os_prio=31 tid=0x00007f9fcf08a000 nid=0x5603 waiting for monitor entry [0x000070000f682000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 * 	at top.gabin.jvm.lock.DealLock.lambda$main$0(DealLock.java:19)
 * 	- waiting to lock <0x00000007bbedc530> (a java.lang.Object)
 * 	- locked <0x00000007bbedc520> (a java.lang.Object)
 * 	at top.gabin.jvm.lock.DealLock$$Lambda$1/1452126962.run(Unknown Source)
 * 	at java.lang.Thread.run(Thread.java:748)
 *
 * "Service Thread" #10 daemon prio=9 os_prio=31 tid=0x00007f9fcc030000 nid=0x3d03 runnable [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C1 CompilerThread3" #9 daemon prio=9 os_prio=31 tid=0x00007f9fcc02d000 nid=0x3b03 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread2" #8 daemon prio=9 os_prio=31 tid=0x00007f9fce034000 nid=0x4003 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread1" #7 daemon prio=9 os_prio=31 tid=0x00007f9fce02b800 nid=0x3903 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread0" #6 daemon prio=9 os_prio=31 tid=0x00007f9fcc02c800 nid=0x4103 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "Monitor Ctrl-Break" #5 daemon prio=5 os_prio=31 tid=0x00007f9fcf01e800 nid=0x3703 runnable [0x000070000ef6d000]
 *    java.lang.Thread.State: RUNNABLE
 * 	at java.net.SocketInputStream.socketRead0(Native Method)
 * 	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
 * 	at java.net.SocketInputStream.read(SocketInputStream.java:171)
 * 	at java.net.SocketInputStream.read(SocketInputStream.java:141)
 * 	at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
 * 	at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
 * 	at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
 * 	- locked <0x00000007bbfc1b98> (a java.io.InputStreamReader)
 * 	at java.io.InputStreamReader.read(InputStreamReader.java:184)
 * 	at java.io.BufferedReader.fill(BufferedReader.java:161)
 * 	at java.io.BufferedReader.readLine(BufferedReader.java:324)
 * 	- locked <0x00000007bbfc1b98> (a java.io.InputStreamReader)
 * 	at java.io.BufferedReader.readLine(BufferedReader.java:389)
 * 	at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:61)
 *
 * "Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007f9fcc014800 nid=0x3603 runnable [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007f9fce80b800 nid=0x4a03 in Object.wait() [0x000070000ec61000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 * 	at java.lang.Object.wait(Native Method)
 * 	- waiting on <0x00000007bbd88ec8> (a java.lang.ref.ReferenceQueue$Lock)
 * 	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
 * 	- locked <0x00000007bbd88ec8> (a java.lang.ref.ReferenceQueue$Lock)
 * 	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
 * 	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)
 *
 * "Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007f9fcc011000 nid=0x4c03 in Object.wait() [0x000070000eb5e000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 * 	at java.lang.Object.wait(Native Method)
 * 	- waiting on <0x00000007bbd86b68> (a java.lang.ref.Reference$Lock)
 * 	at java.lang.Object.wait(Object.java:502)
 * 	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
 * 	- locked <0x00000007bbd86b68> (a java.lang.ref.Reference$Lock)
 * 	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
 *
 * "VM Thread" os_prio=31 tid=0x00007f9fcc00c800 nid=0x4d03 runnable
 *
 * "GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007f9fcd813800 nid=0x2507 runnable
 *
 * "GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007f9fce008800 nid=0x2303 runnable
 *
 * "GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007f9fcc009800 nid=0x2103 runnable
 *
 * "GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007f9fcc00a800 nid=0x2a03 runnable
 *
 * "GC task thread#4 (ParallelGC)" os_prio=31 tid=0x00007f9fcc809000 nid=0x5303 runnable
 *
 * "GC task thread#5 (ParallelGC)" os_prio=31 tid=0x00007f9fcc80a000 nid=0x5103 runnable
 *
 * "GC task thread#6 (ParallelGC)" os_prio=31 tid=0x00007f9fcc80a800 nid=0x4f03 runnable
 *
 * "GC task thread#7 (ParallelGC)" os_prio=31 tid=0x00007f9fcc80b000 nid=0x4e03 runnable
 *
 * "VM Periodic Task Thread" os_prio=31 tid=0x00007f9fcf02f800 nid=0x5503 waiting on condition
 *
 * JNI global references: 324
 *
 *
 * Found one Java-level deadlock:
 * =============================
 * "Thread-1":
 *   waiting to lock monitor 0x00007f9fcb80eac8 (object 0x00000007bbedc520, a java.lang.Object),
 *   which is held by "Thread-0"
 * "Thread-0":
 *   waiting to lock monitor 0x00007f9fcb810fe8 (object 0x00000007bbedc530, a java.lang.Object),
 *   which is held by "Thread-1"
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "Thread-1":
 * 	at top.gabin.jvm.lock.DealLock.lambda$main$1(DealLock.java:32)
 * 	- waiting to lock <0x00000007bbedc520> (a java.lang.Object)
 * 	- locked <0x00000007bbedc530> (a java.lang.Object)
 * 	at top.gabin.jvm.lock.DealLock$$Lambda$2/1607521710.run(Unknown Source)
 * 	at java.lang.Thread.run(Thread.java:748)
 * "Thread-0":
 * 	at top.gabin.jvm.lock.DealLock.lambda$main$0(DealLock.java:19)
 * 	- waiting to lock <0x00000007bbedc530> (a java.lang.Object)
 * 	- locked <0x00000007bbedc520> (a java.lang.Object)
 * 	at top.gabin.jvm.lock.DealLock$$Lambda$1/1452126962.run(Unknown Source)
 * 	at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.
 */
public class DealLock {

    public static void main(String[] args) throws InterruptedException {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("线程1Lock1");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("线程1Lock2");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("线程2Lock1");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("线程2Lock2");
                }
            }
        });
        thread1.start();
        thread2.start();
    }

}
