package classLoader;

/**
 * 08
 * 访问静态变量或静态方法时，变量定义在哪个类中，才会对哪个类进行主动使用，和调用变量的类无关
 * 即使用子类访问父类的静态变量和静态方法时，子类不会初始化
 */
public class ClassInit {
    public static void main(String[] args) {
//        无任何输出，说明声明变量不会导致主动使用
        B b;
//        只有父类A进行初始化，输出A static block和a的值
//        System.out.println(B.a);
//        只有父类A进行初始化，输出A static block和a的值
        B.method();
    }
}

class A{
    static {
        System.out.println("A static block");
    }
    public static int a = 1;
    public static void method(){
        System.out.println("do something");
    }
}
class B extends A{
    static {
        System.out.println("B static block");
    }
}
