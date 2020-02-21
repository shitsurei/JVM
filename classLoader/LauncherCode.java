package classLoader;

// 非开源（加密相关算法专利限制），反编译的代码
// 可以通过openJDK查看源码

import sun.misc.Launcher;

/**
 * 17
 * Launcher和ClassLoader类源码分析
 */
public class LauncherCode {
    public static void main(String[] args) {
//        testGetSystemClassLoader();
//        testLauncher();
    }

    public static void testLauncher(){
        /**
         * Launcher源代码
         * 扩展类加载器和系统类加载器都是Launcher的内部类ExtClassLoader和AppClassLoader
         * 单例模式
         * 私有变量：
         * 1 单例launcher
         * 2 系统类加载器实例loader，通过其getParent方法可获得扩展类加载器实例
         * 构造方法：
         * 1 创建扩展类加载器：通过IO流方式从路径java.ext.dirs中读取文件数组（getExtClassLoader），进行安全校验，执行ExtClassLoader的构造方法
         * 2 创建系统类加载器：和1类似，不同点在系统类加载器的构造方法中传入扩展类加载器作为系统类加载器的【双亲】，从java.class.path中加载
         * 3 将当前的系统类加载器实例作为运行线程的上下文类加载器 Thread.currentThread().setContextClassLoader(this.loader);
         * 4 安全验证
         */
        System.out.println(Launcher.class.getClassLoader());

    }

    /**
     * ClassLoader类的核心方法getSystemClassLoader：
     * 1 初始化系统类加载器initSystemClassLoader：
     *  a 获取Launcher单例
     *  b 将类的静态变量scl设置为Launcher实例中的系统类加载器私有变量loader
     *  c 处理用户修改java.system.class.loader属性的情况，将系统类加载器作为用户自定义类加载器的双亲，
     *      并【将用户自定义类加载器的实例作为当前线程的上下文类加载器】，此处使用了函数式编程（要求自定义类加载器需要提供ClassLoader单参数构造方法）
     *  d 安全校验
     * 2 安全校验
     */
    public static void testGetSystemClassLoader() {
//        参数修改前输出为null，说明该类是由启动类加载器加载，其内部类也是启动类加载器加载
//        参数修改后输出为classLoader.DefineClassLoader
        System.out.println(System.getProperty("java.system.class.loader"));
//        方法要点：
//        1 静态方法，返回用于委托的系统类加载器
//        2 java.system.class.loader属性所指定的类会作为该方法返回的系统类加载器值，且该类会由默认的系统类加载器加载
        System.out.println(ClassLoader.getSystemClassLoader());//修改java.system.class.loader属性之后系统类加载器变为classLoader.DefineClassLoader@55f96302
        System.out.println(DefineClassLoader.class.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
        /**
         * java -Djava.system.class.loader=classLoader.DefineClassLoader classLoader.LauncherCode
         * 【指定的类需要设置一个单个参数的构造方法，参数类型为ClassLoader，参见DefineClassLoader类】
         * 注意，idea中的terminal需要转换为相应路径才能测试，或者在VM选项中加入参数-Djava.system.class.loader=classLoader.DefineClassLoader
         */
//        3 返回的类加载器是新的类加载器【实例】的默认双亲
        DefineClassLoader loader1 = new DefineClassLoader("loader1");
        System.out.println(loader1.getParent());//classLoader.DefineClassLoader@55f96302
//        4 返回的也是启动应用的类加载器，该方法会在运行期较早被调用，调用时会先创建系统类加载器（initSystemClassLoader），并将其设置为所调用线程的【上下文类加载器】
        System.out.println(Thread.currentThread().getContextClassLoader());//classLoader.DefineClassLoader@55f96302
        System.out.println(Thread.currentThread().getContextClassLoader().getParent());//sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(Thread.currentThread().getContextClassLoader().getParent().getParent());//sun.misc.Launcher$ExtClassLoader@74a14482
    }
}
