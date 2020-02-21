package classLoader;

import classLoader.samples.MyThread;

/**
 * 18
 * 线程中的上下文类加载器
 *
 * 当前类加载器（Current ClassLoader）：用于加载当前类的加载器
 * 【每个类都会使用加载自身的类加载器去加载他所依赖（类中引用到）的其他类】，前提是所依赖的类还没有被加载
 *
 * 线程上下文类加载器（Context ClassLoader）
 * 从JDK1.2版本开始引入，用于在线程运行期间加载类与资源，Thread类中的getContextClassLoader和setContextClassLoader方法分别用于获取和设置上下文类加载器
 * 【如果没有用setContextClassLoader方法设置当前线程的上下文类加载，则会自动继承父线程的上下文类加载器】
 * Java应用运行时的初始线程的上下文类加载器是系统类加载器
 *
 * SPI（Service Provider Interface）：JNDI、JDBC等等
 * 以JDBC为例，JDBC只是一种标准，不同数据库的厂商有其各自的实现，我们使用时只需要将厂商提供的jar包放入类路径下即可
 * 问题在于JDBC的接口相关代码位于rt.jar包下，是由根类加载器加载的，而具体实现的代码是系统类加载器加载的，如果遵循双亲委派机制根类加载器加载的类就无法访问子类加载器所加载的类
 * 在双亲委托模型下，类加载是由下至上的，即下层的类加载器会委托上层加载；
 * 但对于SPI来说有些接口是Java核心库所提供的，而Java核心库是由启动类加载器来加载的，而这些接口的实现却来自于不同的jar包（不同厂商提供）
 * Java的启动类加载器不会加载其他来源的jar包，这样传统的双亲委派模型就无法满足SPI的要求。
 * 【类加载器的双亲可以使用当前线程所指定的上下文类加载器所加载的类，这就改变了父类加载器不能使用子类加载器或】
 */
public class ThreadClassLoader {
    public static void main(String[] args) {
//        sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(Thread.currentThread().getContextClassLoader());
//        null  Thread为lang包中的类，由根类加载器加载
        System.out.println(Thread.class.getClassLoader());

        new MyThread();
    }
}
