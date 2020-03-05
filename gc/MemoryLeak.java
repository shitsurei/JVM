package gc;

/**
 * 02
 * Java内存泄漏经典原因：
 * 1 对象定义在错误的范围内（在不影响代码逻辑的情况下，类中的变量尽量定义为方法中的局部变量）
 * 2 异常处理不当（资源关闭需要放进finally块中）
 * 3 集合数据管理不当：
 * a 使用基于数组的数据结构时尽可能在创建时确定数组大小，尽量减少resize，减少扩容导致的数据复制成本和垃圾回收碎片问题
 * b 对于【只需要顺序访问】的列表使用链表结构，可以避免碎片问题
 */
public class MemoryLeak {
    /**
     * JVM日志参数：
     * -verbose:gc  输出详细的GC日志
     * -Xms20m  堆初始大小
     * -Xmx20m  堆上限（设置相同可以避免堆内存抖动）
     * -Xmn10m  新生代大小
     * -XX:+PrintGCDetails  输出GC详细信息
     * -XX:SurvivorRatio=8  Eden空间和两个Survivor空间的比例为8比1比1
     */
    public static void main(String[] args) {
//        通过参数设置，堆内存空间划分为10m新生代和10m老年代，Eden空间:From Survivor空间:To Survivor空间 = 8:1:1
//        注意：由于新生代采用Parallel Scavenge收集器，即复制算法来进行GC，因此From Survivor空间和To Survivor空间互相作为备用空间复制存活对象，因此新生代的空间内存仅为9m
        int size = 1024 * 1024;
        byte[] myAlloc1 = new byte[2 * size];
        byte[] myAlloc2 = new byte[2 * size];
//        申请myAlloc3空间时由于堆空间不足创建失败触发GC（此处触发的是Scavenge GC）
//        此时新生代已有6109k对象，约为5.966m，Eden无法再申请3m空间（这里除去之前申请myAlloc1和myAlloc2的4m空间，JVM虚拟机启动时加载的空间占大约2m）
        byte[] myAlloc3 = new byte[3 * size];
//        GC完成后：
//        新生代释放6109-792=5317k空间，其中包括【晋升至老年代的对象】和【真正回收的对象】
//        整个堆内存释放6109-4896=1213k空间，只有【真正回收的对象】
//        因此【晋升至老年代的对象】占5317-1213=4104k空间
        System.out.println("hello world");
    }
    /**
     * -verbose:gc参数输出结果：
     * 【GC时机（产生原因）【A代：GC之前A代存活的对象所占空间——》GC之后A代存活的对象所占空间（A代总的空间容量）】GC之前总的堆空间大小——》GC之后总的堆空间大小（堆空间总容量），GC所耗费时间】【用户空间耗时，系统内核空间耗时，真正运行耗时】
     * [GC (Allocation Failure) [PSYoungGen: 6109K->792K(9216K)] 6109K->4896K(19456K), 0.0025607 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     *
     * PrintGCDetails参数输出结果：
     * Heap
     *   PSYoungGen      total 9216K, used 4101K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *   eden space 8192K, 40% used [0x00000000ff600000,0x00000000ff93b518,0x00000000ffe00000)
     *   from space 1024K, 77% used [0x00000000ffe00000,0x00000000ffec6030,0x00000000fff00000)
     *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     *  ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002020,0x00000000ff600000)
     *  Metaspace       used 3225K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 350K, capacity 388K, committed 512K, reserved 1048576K
     *
     */
}
