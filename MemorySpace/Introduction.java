package MemorySpace;

/**
 * 01
 * 对象本身含有两种数据：本身固有的数据（属性），元数据（和类相关）
 * JVM内存区域划分：
 * 1 虚拟机栈（VM Stack）：线程私有，生命周期和线程保持一致，内部由【Stack Frame 栈帧】构成
 *      对象引用位于栈中，可以看做局部变量表中的一个变量，除了对象引用类型（Reference）外，局部变量表中还可以存放八种基本数据类型
 *      引用访问对象的两种方式：
 *      a 句柄（栈帧指向的堆中元素包含指向方法区元数据的【指针】和指向实际对象数据在堆空间位置的【指针】）
 *      b 直接引用（栈帧指向的堆中元素包含指向方法区元数据的【指针】和实际对象【数据】）:Hotspot所采用的的方式
 * 2 程序计数器（program counter）：记录线程执行到哪一行指令，线程私有
 * 3 本地方法栈：主要用于执行本地方法
 * 4 堆（heap）：最大的一块内存，线程共享，new出来的对象本身位于堆内
 *      现在几乎所有的垃圾收集器都是采用分代的收集算法，所以堆空间也基于这一点进行了相应的划分：新生代与老年代（细分为Eden空间、From Survivor空间和To Survivor空间）
 * 5 方法区：存储对象的元数据（class对象相关信息，常量信息），永久代（Permanent Generation），从JDK8开始已经废弃了永久代，使用元空间（mete space）替代
 * 6 运行时常量池：方法区的一部分内容
 * 7 直接内存（Direct Memory）：操作系统管辖，虚拟机申请，与Java NIO密切相关，JVM通过DirectByteBuffer来操作直接内存。
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 创建Java对象实例的方式：new clone 反序列化
 * 使用new关键字创建对象过程：
 * 1 在堆内存中创建对象的实例
 *  a 虚拟机检查new创建对象的实例时，检查指令的参数是否可以在常量池中定位到一个类的引用
 *  b 检查该引用的类是否被正确的加载、连接、初始化过了，如果没有先完成类加载过程
 *  c 虚拟机为对象在堆当中分配内存（【指针碰撞：指针分割内存空间，一次被占用，另一次空闲，创建时移动指针】、【空闲列表：内存空间非理想状态，列表中记录内存空间的空闲占用位置】两种方式，采取哪种方式和垃圾回收算法相关，带压缩的算法使用前者，否则使用后者）
 * 2 为对象的实例成员变量赋初值（静态变量在初始化阶段以及赋值）
 *  a 调用类的<init>方法
 * 3 将对象的引用返回
 *  a 将返回值赋给本地方法栈栈帧当中的引用
 *
 *  对象的组成：
 *  1 对象的头
 *  2 真正的实例数据（即我们在一个类中声明的各种信息）
 *  3 对齐填充（可选，起占位符的作用）
 */
public class Introduction {
    /**
     * -Xms5m   堆内存下限
     * -Xmx5m   堆内存上限
     * -XX:+HeapDumpOnOutOfMemoryError  堆内存溢出时创建快照
     * -XX:-UseGCOverheadLimit          关闭GC开销限制检查
     */
    public static void main(String[] args) {
        List<Introduction> l = new ArrayList<>();
        Introduction i = new Introduction();
//        方式1：生成2353个字符数组和2205个String字符串
//        while (true)
//            l.add(i);
//        方式2：生成240098个Introduction对象
//        while (true)
//            l.add(new Introduction());
        while (true){
            l.add(new Introduction());
//            显式调用垃圾回收（不推荐使用）
            System.gc();
        }
    }
    /**
     * 堆内存溢出【错误】
     * java.lang.OutOfMemoryError: Java heap space
     * Dumping heap to java_pid4300.hprof ...
     * Heap dump file created [4339170 bytes in 0.015 secs]
     */
    /**
     * 超出GC开销限制（JDK6新添的错误类型）是在GC占用大量时间为释放很小空间的时候发生的，是一种保护机制。
     * 官方定义：超过98%的时间用来做GC并且回收了不到2%的堆内存时会抛出此异常。一般是因为堆太小没有足够的内存导致的。
     * java.lang.OutOfMemoryError: GC overhead limit exceeded
     * Dumping heap to java_pid11880.hprof ...
     * Heap dump file created [8944123 bytes in 0.057 secs]
     */
}
