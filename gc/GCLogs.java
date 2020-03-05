package gc;

/**
 * 03
 * 当新创建的对象在新生代没有足够的内存空间分配时会直接进入老年代（不存在将对象拆分存放在不同的分代空间中）
 * 在Serial和ParNew收集器中可以通过-XX:+PretenureSizeThreshold=11111参数设置对象大于11111时直接进入老年代（使用Serial收集器的参数为-XX:+UseSerialGC）
 *
 * 在经历了多次GC后，存活的对象会在From Survivor和To Survivor之间来回存放，而这里的前提是这两个空间有足够的大小来存放数据
 * 在可以自动调节对象晋升（promote）到老年代阈值的GC中，通过参数-XX:MaxTenuringThreshold=5设置该阈值的最大值，即对象最多不超过5次GC晋升到老年代
 * 该参数的默认值为15，CMS垃圾回收器中为6，G1垃圾回收器中为15（在JVM中该数值由四个bit来标示，故最大值为1111，即15）
 * 参数-XX:+PrintTenuringDistribution用于输出分属于不同年龄段的对象的大小
 * 在GC算法中会计算每个对象年龄的大小，如果达到某个年龄后发现总大小已经大于Survivor空间的一半，这时就需要调整阈值，不能再等到默认的15次GC之后再晋升，因为这样会导致Survivor空间不足，因此需要尽快调整阈值让存活对象尽快晋升到老年代
 *
 */
public class GCLogs {
    public static void main(String[] args) throws InterruptedException {
        int size = 1024 * 1024;
        byte[] alloc1 = new byte[5 * size];
        byte[] alloc2 = new byte[3 * size];
    }
    /**
     * GC日志输出：
     * [GC (Allocation Failure) [PSYoungGen: 7531K->831K(9216K)] 7531K->5959K(19456K), 0.0115379 secs] [Times: user=0.05 sys=0.01, real=0.01 secs]
     * [Full GC (Ergonomics) [PSYoungGen: 831K->0K(9216K)] [ParOldGen: 5128K->5854K(10240K)] 5959K->5854K(19456K), [Metaspace: 3057K->3057K(1056768K)], 0.0254879 secs] [Times: user=0.06 sys=0.00, real=0.03 secs]
     */
}
/**
 * 堆内存回收详情输出：
 * Heap
 *  PSYoungGen      total 9216K, used 3318K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 8192K, 40% used [0x00000000ff600000,0x00000000ff93d8a0,0x00000000ffe00000)
 *   from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 *  ParOldGen       total 10240K, used 5854K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   object space 10240K, 57% used [0x00000000fec00000,0x00000000ff1b7bc0,0x00000000ff600000)
 *  Metaspace       used 3064K, capacity 4556K, committed 4864K, reserved 1056768K
 *   class space    used 326K, capacity 392K, committed 512K, reserved 1048576K
 *
 * 设置阈值之后5m对象直接进入老年代：
 * Heap
 *  def new generation   total 9216K, used 5250K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   eden space 8192K,  64% used [0x00000000fec00000, 0x00000000ff120850, 0x00000000ff400000)
 *   from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
 *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
 *  tenured generation   total 10240K, used 5120K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *    the space 10240K,  50% used [0x00000000ff600000, 0x00000000ffb00010, 0x00000000ffb00200, 0x0000000100000000)
 *  Metaspace       used 3223K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 350K, capacity 388K, committed 512K, reserved 1048576K
 */
