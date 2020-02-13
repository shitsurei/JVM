package classLoader;

/**
 * 在编译阶段，常量就会被存入调用这个常量的方法所在类的常量池中
 * 以代码为例，【hello world】存入调用其的main方法所在的Constant类的常量池中
 * 本质上，调用方法并没有直接引用到定义这个常量的类，因此并不会触发定义常量的类的初始化
 * 甚至我们可以将常量所在类的class文件删除
 *
 * 反编译class文件:javap -c C:\workspace\JVM\out\production\JVM\classLoader\Constant.class
 * 输出：
 * Compiled from "Constant.java"
 * public class classLoader.Constant {
 *   public classLoader.Constant();
 *     Code:
 *        0: aload_0
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: return
 *
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;  访问out对象（静态常量）
 *        【ldc助记符表示将int，float或string类型的常量值从常量池中推送到栈顶】
 *        【类似的助记符有bipush，表示将单字节（-128~127）的常量（short）推送至栈顶】
 *        【类似的助记符有sipush，表示将短整型（-128~127）的常量（int）推送至栈顶】
 *        【类似的助记符有iconst_1，表示将int类型的数字1推送至栈顶】
 *        【类似的助记符有iconst_2，表示将int类型的数字2推送至栈顶】
 *        【类似的助记符有iconst_3，表示将int类型的数字3推送至栈顶】
 *        ……最多到5
 *        3: ldc           #4                  // String hello world    已经将常量放入常量池
 *        5: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *        8: return
 * }
 * 其中 getstatic，invokevirtual等叫做助记符，分别表示访问静态变量等含义
 */
public class Constant {
    public static void main(String[] args) {
        System.out.println(Child2.STR);
    }
}
class Parent2 {
    //当添加final关键字时静态代码块中的输出不再执行，即访问类的常量变量时该类不初始化
    public static final String STR = "hello world";
    //类初始化时会执行类中的静态代码块
    static {
        System.out.println("Parent static block");
    }
}

class Child2 extends Parent2 {
    public static String STR2 = "hello Java";

    static {
        System.out.println("Child static block");
    }
}
