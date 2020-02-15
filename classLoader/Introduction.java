package classLoader;

/**
 * 01
 * JVM和程序的生命周期：从main方法开始，四种方式结束（执行exit方法，程序正常运行结束，程序异常未被catch，操作系统错误导致）
 *
 * 类加载（除了动态代理，其他类都是程序运行前创建好的）
 * 类型（class，interface等，注意不是指对象而是类本身）的加载、连接、初始化过程都在程序运行期间（runtime）完成
 *  1 加载：查找并加载类的class文件中的二进制数据到内存中，然后创建java.lang.Class对象用来封装类在方法区的数据结构（反射的来源）
 * 注：规范并未要求Class对象放在哪，Hotspot将其放在runtime区的方法区中，jdk8改为元空间；且规范未要求加载来源，故可有多种：
 *      a 本地系统加载 b 网络下载 c zip包或jar包中加载 d 专有数据库提取 e 将Java源文件动态编译为class文件（动态代理） f jsp转servelet ……
 *  2 连接：将类与类之间的关系确定好（字节码的校验处理过程，类与类之间的符号引用转为地址引用）
 *      a 验证：确保被加载的类的正确性（字节码class文件未被恶意修改）
 *      b 准备：为类的静态变量（此时还不存在对象）分配内存，并将其初始化为默认值（0，false，null），注意此时不会赋予声明的初始化值
 *          public static final int A = 1; （准备阶段A赋值为0）
 *      c 解析：把类中的符号引用（通过变量间接引用）转换为直接引用（通过内存指针引用）
 *  3 初始化：为类的静态变量赋予声明的初始值（此时A赋值为1）
 *  4 使用：和开发人员打交道最多的阶段
 *  5 卸载：卸载后无法再用该类创建对象，OSGI
 *
 * 类的初始化只进行一次，是在“首次主动引用”该类时触发，包含以下几种情况：
 *  1 类创建实例
 *  2 访问类的静态变量（不包括常量，常量存在“调用”类的常量池中）或静态方法
 *  3 反射（Class.forName("com.xxx.A");）
 *  4 初始化该类的子类
 *  5 启动类（包含main方法的类）
 *  6 jdk1.7提供的动态语言支持，如果一个java.lang.invoke.MethodHandle实例最后的解析结果是REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄。且这个句柄对应的类没有初始化，则需要先触发其初始化
 */
public class Introduction {
    public static void main(String[] args) {
//        被动引用演示
//        执行该语句时子类未进行初始化
        System.out.println(Child1.STR);
//        执行该语句时父类未进行初始化(因为在上面的语句中已经初始化过)
//        System.out.println(Child.STR2);
//        被动引用:1.访问父类的静态变量
    }
}

/**
 * JVM参数：-XX:+TraceClassLoading
 * 用于输出类加载信息
 *
 * 首先加载了Object根类
 * [Loaded java.lang.Object from C:\Program Files\Java\jdk1.8.0_201\jre\lib\rt.jar]
 * ……
 * 加载main方法所在类
 * [Loaded classLoader.Introduction from file:/C:/workspace/JVM/out/production/JVM/]
 * ……
 * [Loaded classLoader.Parent from file:/C:/workspace/JVM/out/production/JVM/]
 * Child类虽然没有初始化，但是也加载了
 * [Loaded classLoader.Child from file:/C:/workspace/JVM/out/production/JVM/]
 * 输出信息
 * Parent static block
 * hello world
 */

class Parent1 {
    public static String STR = "hello world";
//类初始化时会执行类中的静态代码块
    static {
        System.out.println("Parent static block");
    }
}

class Child1 extends Parent1 {
    public static String STR2 = "hello Java";

    static {
        System.out.println("Child static block");
    }
}