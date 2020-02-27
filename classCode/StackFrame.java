package classCode;

/**
 * 05
 * 栈帧：一种用于帮助虚拟机进行方法调用和方法执行的数据结构，归属于单个线程不存在并发调用的问题
 * 栈帧本身是一种数据结构，封装了方法的【局部变量表】、【动态链接信息】、【方法的返回地址】以及【操作数栈】等信息
 * “动态链接”：Java程序在编译期间无法确定类与类之间调用的地址关系，需要等到类加载或者真正调用时才能确定，基于此导致了符号引用和直接引用的概念
 * 1 有些符号引用是在类加载阶段或是第一次使用时就会转换为直接引用，这种转换叫做静态解析
 * 2 另外一些符号引用则是在每次运行期转换为直接引用（对象下转型），这种转换叫动态链接，这体现Java的多态性
 * slot：存储局部变量的最小单位，一个方法中slot的个数取决于需要同时存在（引用的声明周期未结束还不能被回收）的局部变量个数
 */

/**
 * 方法调用指令：
 * 1 invokeinterface    调用接口中的方法（实际上是在运行期决定调用哪个实现类中的方法）
 * 2 invokestatic       调用静态方法
 * 3 invokespecial      调用自身的私有方法、构造方法（<init>）和父类方法
 * 4 invokevirtual      调用虚方法，运行期动态查找的过程
 * 5 invokedynamic      动态调用方法
 * 静态解析的四种情形：静态方法、父类方法、构造方法、私有方法（不能被重写，因此不存在多态可能），这四类也称为“非虚方法”
 */
public class StackFrame {
    public static void main(String[] args) {
//        test1();//        invokestatic #5
        test2();
    }

    public static void test1() {
        System.out.println("test invoked");
    }

    /**
     * 方法的“静态分派”
     * 输出gr--classCode.Father@74a14482说明最终执行的重载方法是family(Grandpa grandpa)，即方法new StackFrame().family(p1);调用时传入p1参数是作为Grandpa类型的
     * Grandpa p1 = new Father(); 中p1的【静态类型】是Grandpa，而【实际类型】即真正指向的类型是Father
     * 结论：变量的静态类型不会发生变化，而实际类型则会随着代码的执行发生变化，实际上是多态的体现！
     */
    public static void test2() {
        Grandpa p1 = new Father();
        Grandpa p2 = new Son();
        StackFrame sf = new StackFrame();
//        以下方法调用的指令码为 invokevirtual
        sf.family(p1);//gr--classCode.Father@74a14482
        sf.family(p2);//gr--classCode.Son@1540e19d
    }

    /**
     * 方法重载对JVM来说是一种纯粹的静态行为
     * 【调用哪个重载的方法是在编译器已经确定的】，以传入参数的静态类型为准
     */
    public void family(Grandpa grandpa) {
        System.out.println("gr--"+grandpa);
    }

    public void family(Father father) {
        System.out.println("fa--"+father);
    }

    public void family(Son son) {
        System.out.println("so--"+son);
    }
}

class Grandpa {

}

class Father extends Grandpa {

}

class Son extends Father {

}
