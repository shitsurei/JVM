package classLoader;

import java.util.Random;

/**
 * 通过实验发现接口中的变量也会在编译阶段存入调用类的常量池中，不涉及该接口的主动使用，子接口没有被初始化因此该接口的父类也不会进行初始化
 */
public class Interface {
    public static void main(String[] args) {
//        由于接口中不允许执行静态代码块，所以我们无法通过静态代码块内部的执行来考察类的初始化
//        但是可以通过删除编译后的字节码文件，查看程序是否可以正常运行来判断是否需要类的初始化
        System.out.println(Child5.b);
//        访问父接口中的常量同样不会引起父子接口的初始化
        System.out.println(Child5.d);
    }
}

interface Parent5 {
    //    接口中的变量默认是 public static final 的，即默认为静态常量！！！！
    int a = new Random().nextInt(2);
    //    父接口的常量也会默认继承到子接口身上
    int d = 5;
}

interface Child5 extends Parent5 {
    int b = 6;
    int c = new Random().nextInt(20);
//    因为变量c只有在程序运行期间才能确定，因此删除字节码文件会导致java.lang.NoClassDefFoundError: classLoader/Child5异常产生
//    并且删除父类Parent5的字节码文件同样会导致程序报错java.lang.NoClassDefFoundError: classLoader/Parent5，因为子接口的初始化导致了父接口的初始化
}
