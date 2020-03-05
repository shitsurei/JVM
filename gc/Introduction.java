package gc;

/**
 * 01
 * 运行时数据：
 * 1 线程共享：堆【存对象，GC主要区域】，方法区【存元数据，包括常量池、字段描述、方法描述】
 * 2 线程隔离：虚拟机栈【栈帧】，本地方法栈（Hotspot中两个栈合并），程序计数器
 *
 * GC模型：
 * 1 垃圾判断算法
 *  a 引用计数算法（Reference Counting）
 *      问题：无法解决对象循环引用的问题
 *  b 根搜索算法（Root Tracing）：Java，C#
 * 2 GC算法
 *  a 标记-清除（Mark-sweep）
 *      算法：先标记再清除
 *      缺点：1 标记、清除效率都不高（堆越大GC越慢）； 2 容易产生空间碎片化（GC次数越多碎片化越严重），导致找不到足够的连续空间从而提前触发下一次垃圾回收
 *  b 标记-整理/压缩（Mark-compact）
 *      算法：先标记，再将存活对象向一端移动，最后清空存活对象以外的空间
 *      优点：1 不会产生内存碎片；
 *      缺点：1 压缩过程比清除更加耗费时间；
 *  c 复制（Copying）【现代商业虚拟机中都是采用该算法回收新生代】
 *      算法：将内存化分为两块，永远只用一半区域，回收时将存活的对象复制到另一半未使用的内存，然后将之前的一般全部回收
 *      有点：1 只需要扫描存活的对象；2 不会产生碎片空间；3 适合处理存活周期短的对象
 *      缺点：1 内存缩小为原来的一半（空间代价高）；2 在对象存活率高（例如老年代）的情况下效率有所下降
 *  d 分代（Generational）【当前商业虚拟机都是采用分代收集算法】
 *      算法：将Java堆分为新生代/年轻代、老年代和永久代（JDK8之前，之后永久代变为元空间），不同代采用不同的回收算法
 *      年轻代：分为一个Eden区，两个Survivor区（数量可调），回收对象时存活的对象从Eden区向两个Survivor区移动，多次存活的对象晋升至老年代
 *      实现：Eden:From Survivor:To Survivor = 8:1:1   需要额外空间做担保以应付Eden中较多对象存活的情况
 * 3 垃圾回收器的实现和选择
 *
 * 类回收需要满足的三个条件：
 * 1 该类所有实例对象都被回收
 * 2 加载该类的classLoader已经被回收
 * 3 该类所对应的class对象不能在任何地方被引用，如不能在任何地方通过反射引用该类
 * JVM规范不要求虚拟机在方法区实现垃圾回收，因为性价比一般；
 * 但在大量使用反射、动态代理、cglib等字节码框架，动态生成jsp以及OSGI这类频繁自定义classLoader的场景都需要JVM具备类卸载的支持以防方法区溢出
 *
 * 内存回收：Hotspot认为没有引用的对象是dead的
 * Hotspot将对象引用分为四种：
 * 1 强引用（Strong）：默认通过new关键字创建的引用
 * 【其他三种继承自Reference，在Full GC时会对Reference类型的引用进行特殊处理】
 * 2 软引用（Soft）：内存不够或长期不用时会被GC
 * 3 弱引用（Weak）：当被标记为dead时，会在ReferenceQueue中通知
 * 4 虚引用（Phantom）：本来就没有引用，当从堆中释放时会通知
 *
 * 在分代模型的基础上，GC从时机上分两种：
 * 1 Scavenge GC（Minor GC）：触发时机为新对象生成时Eden空间已满，理论上Eden空间大量对象会在Scavenge GC时被回收，复制算法时间短执行效率高
 * 2 Full GC：对整个JVM内存空间进行整理，包括young、old和perm，执行效率很低（STW，业务需要暂停等待），应该尽量减少
 *  触发时机有三种：1 老年代满了；2 永久代满了；3 显式调用System.gc();
 *
 * 垃圾回收器（Garbage Collector）：GC的具体实现，Hotspot JVM提供了多种，需要根据不用业务场景来选择适用的垃圾回收器
 * “并行”：多个收集器线程同时工作，但是用户线程处于等待状态
 * “并发”：收集器线程在工作的同时允许用户线程工作（并发在关键步骤还是要停顿，例如收集器标记垃圾时需要停顿，清除垃圾时可以并发执行）
 *
 * 1 Serial收集器：
 * 算法：最早的【单线程】收集器，收集时会暂停所有工作线程（STW），新生代使用复制收集算法，老年代采用标记-整理算法
 * 使用场景：虚拟机运行在【Client模式】下默认的新生代收集器
 * 2 ParNew收集器：
 * 算法：Serial收集器的【多线程】版本，除了使用多线程外其余算法、收集策略都与Serial收集器相同
 * 使用场景：虚拟机运行在【Server模式】下默认的新生代收集器（单核环境中并不会比Serial收集器效果更好）
 * 通过-XX:ParallelGCThreads参数控制GC线程数量
 * 3 Parallel Scavenge收集器：
 * 算法：和ParNew收集器唯一不同的一点在于以【吞吐量最大化（GC时间最小化）】为目标的实现，允许较长时间STW换取吞吐量最大化
 * 使用场景：
 * 4 Serial Old收集器：
 * 算法：单线程收集器，使用标记-整理算法，是老年代的收集器
 * 5 Parallel Old收集器：JDK1.6提供，采用多线程，老年代版本吞吐量优先收集器
 * 6 CMS（Concurrent Mark Sweep）收集器：以最短停顿时间为目标【适合web应用】的收集器
 *  算法：只针对老年代，一般结合ParNew使用；GC线程和用户线程并发工作，在多核环境下才有意义；使用标记清除算法
 *  启动参数：-XX:+UseConcMarkSweepGC
 */
public class Introduction {
    public static void main(String[] args) {
        new Introduction().test1();
    }

    /**
     * 该方法生成了两部分的内存区域：
     * 1 变量i为方法中的引用变量，存储在Java虚拟机栈中，占用4个字节
     * 2 Introduction类的实例对象存储在堆中，空对象占用8个字节
     * 方法结束后变量i的内存空间随着栈帧出栈直接被回收
     * 但对象要等到下一次GC时才被回收
     */
    public void test1() {
        Introduction i = new Introduction();
    }
}