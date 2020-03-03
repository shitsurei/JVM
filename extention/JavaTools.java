package extention;

/**
 * 02
 * JVM分析工具
 * 可视化工具：
 * 1 jconsole
 * 2 jvisualvm（功能更强大）
 * 3 jmc（Java Mission Control）
 *
 * 命令行工具：
 * 1 jmap
 * -clstats 【PID】    查询当前线程的类加载器详情
 * -heap 【PID】     查询当前线程的对信息
 * 2 jstat
 * -gc 【PID】       查询当前进程的内存信息，主要关注MC（当前元空间容量）、MU（元空间已经被占用的容量）
 * 3 jps    查询所有Java进程PID
 * -l  打印带包名
 * -V  打印不带包名
 * -v  打印详细信息
 * 4 jcmd   从jdk1.7开始新增，和jps类似
 * 【PID】 help       列出当前进程可以执行的操作
 * 【PID】 help   【operation】 查看具体命令的选项
 * 【PID】 VM.system_properties   查询JVM的属性信息
 * 【PID】 VM.version   查询JVM和JDK的版本号
 * 【PID】 VM.flags   查询当前进程的启动参数
 * 【PID】 VM.uptime   查询当前进程的已经执行的时间
 * 【PID】 PerfCounter.print   查看JVM性能相关的参数
 * 【PID】 Thread.print   查看系统中线程的统计信息（可以检测死锁）
 * 【PID】 GC.class_histogram   查看系统中类的统计信息
 * 【PID】 GC.heap_dump 【filepath + filename（.hprof后缀）】  生成并存储堆内存快照文件
 * 5 jstack 【PID】   获取堆栈信息
 * 6 jhat 查询堆转储文件，可以执行OQL（Object Query Language）查询
 */
public class JavaTools {
    public static void main(String[] args) {
        int index = 0;
        while (true) {
            try {
                System.out.println(index++);
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
