package gc;

/**
 * 05
 * 枚举根节点：当执行系统停下来之后并不需要一个不漏的检查所有执行上下文和全局引用的位置，虚拟机能够直接得知存放对象引用的地方，Hotspot中通过OopMap这一数据结构实现
 * OopMap：
 * 安全点（SafePoint）：在OopMap的帮助下Hotspot可以快速完成GC Root枚举，但是每条指令都生成OopMap空间成本较高，因此只在“安全点”记录；即程序执行过程中并非可以在任何位置暂停进行GC，只有到达安全点才可以
 * 【安全点既不能太少（GC等待时间太长）又不能太多（运行负载高），选定标准：“是否具有让程序长时间运行的特征”，例如方法跳转、循环跳转、异常跳转等指令序列复用】
 * 如何让GC发生时让所有线程（除了JNI）都跑到安全点上再停顿？
 * 1 抢占式中断（Preemptive Suspension）
 *  不需要线程的执行代码主动配合，GC发生时将所有业务线程中断，如果有线程不在安全点上，就让他恢复运行，跑到安全点上再停下来
 * 2 主动式中断（Voluntary Suspension）【主流方式】
 *  GC需要中断线程时设置标志（轮询标志与安全点重合）供各个线程轮询，线程主动轮询主动中断挂起
 * 安全区域（SafeRegion）：用于解决线程处于休眠或阻塞状态，无法响应JVM的中断请求的状态
 *
 * CMS（Concurrent Mark Sweep）并发【指GC线程可以和用户线程同时运行，不会出现较长时间的STW】 标记-清除GC
 * 以获取最短的回收停顿时间为目标，多用于互联网项目或B/S架构服务器系统中
 * 步骤：
 * 1 初始标记（需要STW）：只标记【GC Root引用或年轻代存活对象引用】的对象，速度很快
 * 2 并发标记：进行GC Root Tracing，根据初始标记的对象找到所有被引用的存活对象（因为和用户线程并发，其引用关系可能发生改变）
 * 3 并发预清理
 * 4 并发可失败的预清理
 * 5 重新标记（需要STW）：【修正并发标记期间因为用户程序运行导致的标记产生变动的记录】，速度慢于初始标记但远快于并发标记
 * 6 并发清除
 * 7 重置线程
 * 优点：并发，低停顿
 * 缺点：
 * 1 对CPU资源敏感
 * 2 无法处理浮动垃圾：由于并发标记和并发清理阶段用户线程并未停止，此时生成新的垃圾对象只能留待下一次GC处理，如果老年代空间不足容纳新对象则会产生“Concurrent Mode Failure”
 * 【对于“Concurrent Mode Failure”，虚拟机会采用Serial Old收集器作为备选方案对老年代重新收集，这样STW停顿时间就会变长】
 * 【参数-XX:CMSInitiatingOccupancyFraction=88用来设置老年代中对象占比超过多少百分比时触发CMS收集，该参数设置太小会频繁触发CMS，设置太大会产生“Concurrent Mode Failure”问题】
 * 【JDK1.5中该参数默认为68，JDK1.6中提升至92】
 * 3 收集结束后老年代会产生大量碎片：无连续空间存放大对象时容易触发Full GC（默认开启的参数-XX:+UseCMSCompactAtFullCollection用于在老年代触发Full GC之前先进行内存碎片整理，整理过程无法并发需等待）
 *
 * 空间分配担保：在发生Minor GC之前虚拟机会做一次判断，老年代最大可用连续空间是否大于新生代所有对象总大小？
 * 1 大于，此次GC时安全的，存在GC之后新生代大量对象都是存活的情况，Survivor空间无法容纳直接进入老年代
 * 2 不足，触发Full GC（判断条件是以往晋升老年代的对象容量的平均值）
 */
public class CMS {
    /**
     * -verbose:gc
     * -Xms20m
     * -Xmx20m
     * -Xmn10m
     * -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8
     * -XX:+UseConcMarkSweepGC
     */
    public static void main(String[] args) {
        int size = 1024 * 1024;
        byte[] myAlloc1 = new byte[4 * size];
//        Thread.sleep(1000);
        System.out.println("-------1--------");
        byte[] myAlloc2 = new byte[4 * size];
        System.out.println("-------2--------");
        byte[] myAlloc3 = new byte[4 * size];
        System.out.println("-------3--------");
        byte[] myAlloc4 = new byte[2 * size];
        System.out.println("-------4--------");
    }
}
/**
 * -------1--------
 * [GC (Allocation Failure) [ParNew: 6109K->644K(9216K), 0.0044451 secs] 6109K->4742K(19456K), 0.0050749 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * -------2--------
 * [GC (Allocation Failure) [ParNew: 4979K->248K(9216K), 0.0028341 secs] 9077K->9067K(19456K), 0.0028672 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 【老年代初始标记】
 * [GC (CMS Initial Mark) [1 CMS-initial-mark: 8819K(10240K)] 13163K(19456K), 0.0001764 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 【老年代并发标记开始】
 * [CMS-concurrent-mark-start]
 * -------3--------
 * -------4--------
 * [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 【老年代并发预清理开始】
 * [CMS-concurrent-preclean-start]
 * [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 【老年代可失败的并发预清理开始】
 * [CMS-concurrent-abortable-preclean-start]
 * [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 【老年代重新标记】
 * [GC (CMS Final Remark) [YG occupancy: 6604 K (9216 K)][Rescan (parallel) , 0.0001322 secs][weak refs processing, 0.0000178 secs][class unloading, 0.0003763 secs][scrub symbol table, 0.0006500 secs][scrub string table, 0.0001478 secs][1 CMS-remark: 8819K(10240K)] 15423K(19456K), 0.0014295 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 【老年代并发清除开始】
 * [CMS-concurrent-sweep-start]
 * [CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 【老年代并发重置开始】
 * [CMS-concurrent-reset-start]
 * [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * Heap
 *  par new generation   total 9216K, used 6850K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   eden space 8192K,  80% used [0x00000000fec00000, 0x00000000ff272848, 0x00000000ff400000)
 *   from space 1024K,  24% used [0x00000000ff400000, 0x00000000ff43e0d0, 0x00000000ff500000)
 *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
 *  concurrent mark-sweep generation total 10240K, used 8818K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *  Metaspace       used 3220K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 349K, capacity 388K, committed 512K, reserved 1048576K
 */
