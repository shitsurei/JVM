package gc;

/**
 * 04
 * -verbose:gc
 * -Xmx200m
 * -Xmn50m
 * -XX:TargetSurvivorRatio=60   当survivor空间中存活对象占比超过60%时就会动态调整对象晋升老年代的阈值
 * -XX:+PrintTenuringDistribution    输出Survivor空间中对象的年龄分布
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps   输出当前执行GC的时间戳，UTC格式
 * -XX:+UseConcMarkSweepGC  选择CMS垃圾回收器作为老年代的GC
 * -XX:+UseParNewGC 选择ParNew垃圾回收器作为新生代的GC
 * -XX:MaxTenuringThreshold=3   设置晋升老年代的阈值，即Survivor空间中的对象最多经历3次GC就能晋升到老年代
 *
 */
public class ObjectPromote {
    public static void main(String[] args) throws InterruptedException {
        byte[] bytes1 = new byte[512 * 1024];
        byte[] bytes2 = new byte[512 * 1024];
        myGC();
        Thread.sleep(1000);
        System.out.println("-------1--------");
        myGC();
        Thread.sleep(1000);
        System.out.println("-------2--------");
        myGC();
        Thread.sleep(1000);
        System.out.println("-------3--------");
        myGC();
        Thread.sleep(1000);
        System.out.println("-------4--------");
        byte[] bytes3 = new byte[1024 * 1024];
        byte[] bytes4 = new byte[1024 * 1024];
        byte[] bytes5 = new byte[1024 * 1024];
        myGC();
        Thread.sleep(1000);
        System.out.println("-------5--------");
        myGC();
        Thread.sleep(1000);
        System.out.println("-------6--------");
        System.out.println("-------end--------");
    }

    /**
     * 每次执行该方法都会生成40个1m大小的字节数组对象，方法结束后这些数组称为垃圾待回收
     */
    private static void myGC() {
        for (int i = 0; i < 40; i++) {
            byte[] bytes = new byte[1024 * 1024];
        }
    }
}
/**
 * 输出：
 * 【时间戳，GC时机，GC原因，GCor】
 * 2020-03-06T10:12:52.896+0800: [GC (Allocation Failure) 2020-03-06T10:12:52.899+0800: [ParNew
 * 【我们只设定了新生代的大小为50m，因此Eden空间和Survivor空间的比例默认按照8比1比1划分，Survivor空间大小为5m；
 * 同时我们设定的Survivor空间调整阈值的存活对象占比标准为60%，即超过3m（等价于3145728 bytes）时需要调整晋升条件（当前为3次，符合初始设定）】
 * Desired survivor size 3145728 bytes, new threshold 3 (max 3)
 * - age   1:    1709944 bytes,    1709944 total
 * : 40141K->1696K(46080K), 0.0025199 secs] 40141K->1696K(123904K), 0.0062650 secs] [Times: user=0.02 sys=0.02, real=0.01 secs]
 * -------1--------
 * 2020-03-06T10:12:53.919+0800: [GC (Allocation Failure) 2020-03-06T10:12:53.919+0800: [ParNew
 * Desired survivor size 3145728 bytes, new threshold 3 (max 3)
 * - age   1:     342264 bytes,     342264 total
 * - age   2:    1703072 bytes,    2045336 total
 * : 42435K->2314K(46080K), 0.0012558 secs] 42435K->2314K(123904K), 0.0013021 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * -------2--------
 * 2020-03-06T10:12:54.941+0800: [GC (Allocation Failure) 2020-03-06T10:12:54.941+0800: [ParNew
 * Desired survivor size 3145728 bytes, new threshold 3 (max 3)
 * - age   1:         72 bytes,         72 total
 * - age   2:     341688 bytes,     341760 total
 * - age   3:    1701952 bytes,    2043712 total
 * : 42825K->2441K(46080K), 0.0037290 secs] 42825K->2441K(123904K), 0.0038297 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * -------3--------
 * 2020-03-06T10:12:55.962+0800: [GC (Allocation Failure) 2020-03-06T10:12:55.962+0800: [ParNew
 * Desired survivor size 3145728 bytes, new threshold 3 (max 3)
 * - age   1:         72 bytes,         72 total
 * - age   2:         72 bytes,        144 total
 * 【上一次年龄为3的对象在当前GC过程中晋升至老年代】
 * - age   3:     341664 bytes,     341808 total
 * : 43160K->931K(46080K), 0.0093243 secs] 43160K->2605K(123904K), 0.0094290 secs] [Times: user=0.02 sys=0.02, real=0.01 secs]
 * -------4--------
 * 2020-03-06T10:12:57.001+0800: [GC (Allocation Failure) 2020-03-06T10:12:57.001+0800: [ParNew
 * 【survivor中的存活对象所占空间超过3m，阈值动态调整为1，即年龄大于1的对象在下一次GC过程中直接晋升至老年代】
 * Desired survivor size 3145728 bytes, new threshold 1 (max 3)
 * - age   1:    3145848 bytes,    3145848 total
 * - age   2:         72 bytes,    3145920 total
 * - age   3:         72 bytes,    3145992 total
 * : 41657K->3230K(46080K), 0.0058614 secs] 43332K->5263K(123904K), 0.0059692 secs] [Times: user=0.03 sys=0.00, real=0.01 secs]
 * -------5--------
 * 2020-03-06T10:12:58.025+0800: [GC (Allocation Failure) 2020-03-06T10:12:58.026+0800: [ParNew
 * 【survivor中的存活对象所占空间回落到3m以下，阈值再次动态调整为3】
 * Desired survivor size 3145728 bytes, new threshold 3 (max 3)
 * - age   1:         72 bytes,         72 total
 * : 43961K->39K(46080K), 0.0042446 secs] 45994K->5144K(123904K), 0.0043478 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]
 * -------6--------
 * -------end--------
 * Heap
 *  par new generation   total 46080K, used 18040K [0x00000000f3800000, 0x00000000f6a00000, 0x00000000f6a00000)
 *   eden space 40960K,  43% used [0x00000000f3800000, 0x00000000f4994578, 0x00000000f6000000)
 *   from space 5120K,   0% used [0x00000000f6000000, 0x00000000f6009e30, 0x00000000f6500000)
 *   to   space 5120K,   0% used [0x00000000f6500000, 0x00000000f6500000, 0x00000000f6a00000)
 *  concurrent mark-sweep generation total 77824K, used 5105K [0x00000000f6a00000, 0x00000000fb600000, 0x0000000100000000)
 *  Metaspace       used 3731K, capacity 4536K, committed 4864K, reserved 1056768K
 *   class space    used 410K, capacity 428K, committed 512K, reserved 1048576K
 */
