package gc;

/**
 * 07
 * 影响GC的指标：
 * 1 吞吐量：关注在指定时间内，最大化一个应用的工作量，有以下两条衡量标准：
 *  a tps：在一小时内同一个事务/任务/请求完成的次数
 *  b qps：数据库一小时可以完成多少次查询
 * 对于关注吞吐量的系统来说卡顿是可以接受的，因为这个系统关注长时间的大量任务的执行能力，单次的快速响应不值得考虑
 * 2 响应能力：一个程序或系统对请求是否能够做到快速响应，例如：
 *  a 桌面UI能多快地响应一个事件
 *  b 网站能够多快地响应一个页面请求
 *  c 数据库能够多快地返回查询的数据
 * 对于响应能力敏感的场景，长时间的停顿是不可接受的
 *
 * G1收集器是一个面向服务端的垃圾收集器，适用于多核处理器、大内存容量的服务端系统（JDK7以上适用）
 * 【G1满足短时间GC停顿的同时达到一个较高的吞吐量】
 * 设计目标：
 * 1 与应用线程同时工作，“几乎”不需要STW（与CMS类似）
 * 2 整理剩余空间，不会产生内存碎片（CMS只能在Full GC时用STW的代价整理内存碎片）
 * 3 GC停顿更加可控，出现STW时会参考JVM启动时设定的最大停顿时间（例如在清理内存时只在时间范围内清理一定的内存）
 * 4 不牺牲系统的吞吐量
 * 5 GC不要求额外的内存空间（CMS需要预留空间存储【浮动垃圾】）
 *
 * 概念：
 * 1 区域/分区（Region）：将整个堆空间分成相同大小的区域，建议堆空间大小为6g，划分成两千个左右区域，最小1m，最大32m
 * 2 卡表（card table）：一个卡表将一个分区在逻辑上划分成大小相等的连续区域，每个区域称为卡（128到512字节之间）。默认情况每个卡都未被引用，当一个地址空间被引用时，这个地址空间对应的数组索引的值被标记为0
 * 3 已记忆集合（RSet）：HashTable，key是分区的其实地址，value是一个集合（里面的元素是card table的index）。记录了其他区域中的对象引用本区域对象的关系，属于points-into结构（谁引用了我的对象），其作用是GC不用扫描整个堆就知道了谁引用了当前分区
 * 4 收集集合（CSet）：一组可被回收的分区的集合（其中的分区可以来自任意一代），其中存活的数据会在GC过程中移动到另一个分区
 * 5 起始快照（SATB）：G1在【并发标记阶段】使用的增量式的标记算法，并发标记是多线程的，但并发标记在同一时刻只扫描一个分区
 * 6 全局并发标记（global concurrent marking）：过程类似CMS，主要为Mixed GC提供标记服务，并不是一次GC过程的必须环节，过程如下：
 *  a 初始标记【STW】：标记了从GC Root直接可达的对象（伴随着Young GC发生，公用停顿时间，复用根扫描操作）
 *  b 并发标记：对堆中的对象进行标记，与应用程序并发，同时收集各个对象存活Region的信息
 *  c 重新标记【STW】：标记在并发阶段发生变化的对象
 *  d 清除：清除空的区域（无存活对象），加入free list
 *
 * 堆结构：
 * 1 整个堆空间是一个整体，其中Eden Space、Survivor Space、Old Generation在物理内存上不存在固定的布局
 * 2 将内存空间分成许多块【相等但不连续】的内存区域（Region），每个区域都有一个分代的角色（Eden、Survivor、Old、Unused）但不确定作为哪种角色
 * 3 对每种分代角色的数量没有限定（即对每种分代的大小没有限制），可以动态变化
 * 4 大对象（Humongous Region）：对象大小超过分区容量一半以上时，G1专门开辟H区域存储大对象，一个分区装不下大对象时会启动Full GC来找到连续的区域
 *
 * 回收策略：
 * 1 采用GC停顿可预测的模型，来满足用户设定的GC停顿时间，G1会自定选择哪些区域要清除，一次清除多少个区域
 * 2 G1从多个区域中复制存活的对象，然后集中放入一个区域中，同时整理清除内存（采用复制算法）
 * 3 G1最大的特点是高效的执行回收，【优先回收大量对象可以回收的区域】
 * 4 新生代满了的时候进行回收，拷贝到老年代的过程实现了【局部的压缩】
 *
 * G1相比CMS的优势：
 * 1 压缩空间方面：采用复制算法，可以避免内存碎片的产生，没有压缩空间的成本
 * 2 内存使用效率更为灵活：Eden、Survivor、Old空间不再固定
 * 3 避免应用雪崩：G1可以预设STW时间
 * 4 G1会在回收内存后马上开始合并空闲内存的工作，CMS只能在STW时做
 * 5 G1可以同时适用于年轻代和老年代
 *
 * G1适用场景：
 * 1 服务端多核CPU、JVM内存占用较大的应用
 * 2 运行过程中会产生大量内存碎片、需要经常压缩空间
 * 3 想要更可控、可预期的GC停顿周期，预防高并发的雪崩
 *
 * GC模式：
 * 1 Young GC【STW】：
 *  a Eden空间充满时触发
 *  b 选定【所有年轻代Region】，通过控制其个数来控制YoungGC时间开销,回收之后所有存活对象会被移动到Survivor空间或老年代空间中（Survivor空间不足直接进入老年代），之前的Eden空间都变空白
 * 2 并发阶段
 * 3 Mixed GC【STW】：
 *  a 其触发由一些参数控制：
 *  G1HeapWastePercent（在全局并发标记之后统计到的【老年代中待回收对象的空间占比】超过该参数设定值时会触发Mixed GC）
 *  G1MixedGCLiveThresholdPercent（【老年代的Region中存活对象占比小于该参数】时Region会被选入CSet）
 *  G1MixedGCCountTarget（设定一次全局并发标记之后执行Mixed GC的次数上限）
 *  G1OldCSetRegionThresholdPercent（设定一次Mixed GC中被选入CSet的老年代region数量上限）
 *  b 选定【所有年轻代和“全局并发标记”统计出的收集收益最高的若干老年代Region】，在用户指定的STW时限内尽可能回收收益高的老年代Region
 * 4 Full GC：G1本身不提供Full GC，Mixed GC只能回收部分老年代Region，如果Mixed GC的速度赶不上内存分配速度导致老年代占满时，默认启用【Serial Old】来收集【整个堆内存】
 *
 * Young GC阶段：
 * 1 根扫描：静态和本地对象会被扫描
 * 2 更新RSet：处理dirty card队列更新RS
 * 3 处理RSet：检测从年轻代指向老年代的对象
 * 4 对象拷贝：拷贝存活对象到Survivor和Old区域
 * 5 处理引用队列：软引用，弱引用，虚引用处理
 * Mixed GC阶段：
 * 1 全局并发标记
 * 2 拷贝存活对象
 *
 * 三色标记算法（并发标记）：描述追踪式回收器的有效方法，利用他可以推演回收器的正确性
 * 讲对象分为三种类型：
 * 1 黑：根对象及其子对象（对象内部所引用的其他对象）都被扫描过，即对象和所有成员变量都被标记完了
 * 2 灰：对象本身被扫描但内部成员变量或子对象还没有被扫描
 * 3 白：对象未被扫描
 * 【当所有对象被扫描完之后白色的对象即为不可达对象，也就是垃圾对象】
 * 问题【标记线程和用户线程同时运行导致】：
 * 1 对象丢失问题（漏标）：对象引用变更，导致存活（正常被引用）的对象被回收
 *  解决方案：使用SATB（起始快照）的方式，删除时记录所有的对象
 *  步骤：
 *  a 在开始标记的时候生成一个快照图，标记存活对象
 *  b 在并发标记的时候所有被改变的对象入队【在写屏障里把所有旧的引用所指向的对象都变成非白的】
 *  c 可能存在浮动垃圾，将在下次被收集
 *  详解：
 *  每个Region记录着两个TAMS（top-at-mark-start），分别是prevTAMS和nextTAMS，在TAMS以上的对象都是新分配的，因而被视为隐式marked
 *  写屏障（write barrier）就是对引用字段进行赋值做了额外处理，通过写屏障就知道哪些对象引用发生了什么变化
 *
 * G1参数：
 * -XX:G1HeapRegionSize=n       设置Region大小，非最终值
 * -XX:MaxGCPauseMillis=200     最大GC停顿时间，默认200ms，非硬性条件
 * -XX:G1NewSizePercent=5       新生代最小值，默认5%
 * -XX:G1MaxNewSizePercent=60   新生代最小值，默认60%
 */
public class G1Introduction {
    public static void main(String[] args) {

    }
}
