package gc;

/**
 * 06
 * GC参数:
 * -XX:+PrintGC                     输出GC时机，GC原因，总堆内存释放情况，GC耗费时间，等价于【-verbose:gc】
 * -XX:+PrintGCTimeStamps           输出每次GC的时间戳，需要配合其他参数使用
 * -XX:+PrintHeapAtGC               输出每次GC前后堆内存占用详情，配合其他参数使用可以分析整个GC过程内存变化
 * -XX:+PrintGCDetails              输出详细的GC时分代，分空间参数和最终堆内存中新生代、老年代和元空间的内存占用情况
 * -XX:+UseSerialGC                 指定垃圾收集器为【客户端模式】下的Serial+Serial Old的收集器组合进行内存回收
 * -XX:+UseParallelGC               指定垃圾收集器为【服务端模式】下的Parallel Scavenge+Serial Old的收集器组合进行内存回收
 * -XX:+UseParNewGC                 指定垃圾收集器为ParNew+Serial Old的收集器组合进行内存回收
 * -XX:+UseParallelOldGC            指定垃圾收集器为Parallel Scavenge+Parallel Old的收集器组合进行内存回收
 * -XX:+UseConcMarkSweepGC          指定垃圾收集器为ParNew+CMS+Serial Old（备用）的收集器组合进行内存回收
 * 其他参数：
 * -XX:+PrintFlagsFinal             输出JVM参数默认值列表
 * -XX:+PrintCommandLineFlags       输出程序运行的参数（包含默认参数，相比jcmd命令得到的结果不全）
 */
public class GCParams {
    /**
     * jcmd命令获取的程序运行参数：
     * -XX:CICompilerCount=3            最大并行编译数，适当提高可以提升编译速度，但会影响系统稳定性
     * -XX:InitialHeapSize=20971520     初始堆大小
     * -XX:MaxHeapSize=20971520         最大堆大小
     * -XX:MaxNewSize=10485760          新生代空间上限
     * -XX:MinHeapDeltaBytes=524288     最小堆增量512k（默认）
     * -XX:NewSize=10485760             新生代大小
     * -XX:OldSize=10485760             老年代大小
     * -XX:+PrintGCDetails
     * -XX:+PrintHeapAtGC
     * -XX:SurvivorRatio=8
     * -XX:+UseCompressedClassPointers
     * -XX:+UseCompressedOops
     * -XX:+UseFastUnorderedTimeStamps
     * -XX:-UseLargePagesIndividualAllocation
     * -XX:+UseParallelGC               使用服务器模式下的垃圾回收器组合（默认选项）
     */
    public static void main(String[] args){
        int size = 1024 * 1024;
        System.out.println("-------1--------");
        byte[] bytes1 = new byte[2 * size];
        System.out.println("-------2--------");
        byte[] bytes2 = new byte[2 * size];
//        byte[] bytes5 = new byte[10 * size];
        System.out.println("-------3--------");
        byte[] bytes3 = new byte[2 * size];
        System.out.println("-------4--------");
        byte[] bytes4 = new byte[2 * size];
        System.out.println("-------5--------");
        /**
         * 实验1：
         * -Xms20m
         * -Xmx20m
         * -Xmn10m
         * 新生代和老年代各10m空间，变量bytes4申请内存空间时新生代Eden空间不足触发Minor GC，GC后除回收的空间外还剩6m多对象仍然存活
         * 由于SurvivorRatio设定为8，即Survivor空间大小为1m无法放下6m多对象，根据内存分配担保机制，6m对象直接进入老年代
         * 【此时触发Full GC (Ergonomics)】
         * 原因猜想：由于老年代空间大小为10m，6m对象大小占比超过老年代剩余可用空间的一半，Parallel Scavenge GC处于吞吐量的考虑自发调节，触发Full GC
         * 实验2（对照试验）：
         * -Xms30m
         * -Xmx30m
         * -Xmn10m
         * 对照结果：触发Minor GC后，6m多对象没有超过老年代剩余可用空间（20m）的一半，没有触发Full GC (Ergonomics)
         * 实验3（对照实验）：
         * -Xms30m
         * -Xmx30m
         * -Xmn10m
         * 在bytes2空间申请完之后增加额外的bytes5（10m）空间申请，根据内存分配担保机制，该对象无法在新生代存放，会直接存入老年代
         * 对照结果：触发Minor GC后，6m多对象超过了老年代剩余可用空间（10m）的一半（老年代的10m空间已被bytes5占据），触发了Full GC (Ergonomics)
         * 实验4（对照实验）：
         * -Xms20m
         * -Xmx20m
         * -Xmn10m
         * -XX:+UseSerialGC（新生代使用Serial垃圾回收器）
         * 对照结果：触发Minor GC后，6m多对象没有超过老年代剩余可用空间（10m）的一半，但没有触发Full GC (Ergonomics)
         *
         * 【结论】
         * 对于Parallel Scavenge垃圾回收器来说，当一次Minor GC中晋升到老年代的对象大小超过老年代可用空间的一半以上时会触发Full GC (Ergonomics)
         *
         */
    }
}
/**
 * 引用【https://developer.aliyun.com/ask/65415?spm=a2c6h.13159736】：
 * -verbose:gc
 * 稳定版本
 * 参见：http://docs.oracle.com/javase/7/docs/technotes/tools/windows/java.html
 * -XX:+PrintGC
 * 非稳定版本，可能在未通知的情况下删除，在下面官方文档中是-XX:-PrintGC。
 * 因为被标记为manageable，所以可以通过如下三种方式修改：
 * 1、com.sun.management.HotSpotDiagnosticMXBean API
 * 2、JConsole
 * 3、jinfo -flag
 * 参见：http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html
 */
