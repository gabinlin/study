package top.gabin.patterns;

// 双重检查锁示例
public class SimpleSingleton {

    // 2、静态变量，一个类只有一个，多个实例也是共用一个;volatile告诉虚拟机和编译器
    static volatile SimpleSingleton simpleSingleton;

    // 1、私有化构造器，使得外部不能新建实例
    private SimpleSingleton() {

    }

    // 3、全局访问点
    public static SimpleSingleton getInstance() {
        if (simpleSingleton == null) {
            synchronized (SimpleSingleton.class) {
                simpleSingleton = new SimpleSingleton();
            }
        }
        return simpleSingleton;
    }
}

