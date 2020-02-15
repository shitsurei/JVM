package classLoader;

import java.util.Random;

/**
 * 07
 * 类加载器：
 * 第一种：Java虚拟机自带的类加载器
 * 1 根类加载器（Bootstrap）
 * 2 扩展类加载器（Extension）
 * 3 系统（应用）类加载器（System）
 * 第二种：用户自定义的类加载器
 * 1 java.lang.ClassLoader的子类
 * 2 用户可以定制类的加载方式
 * <p>
 * JVM规范允许类加载器在预料某个类将要被使用时预先加载它，如果加载过程中遇到错误，类加载器必须在首次使用该类时报告错误，不使用则不报告
 * <p>
 * 类验证的内容：类文件结构，语义检查，字节码验证，二进制兼容性验证
 * <p>
 * JVM初始化一个类时要求其父类都已完成初始化，但是这条规则并不适用于接口：
 * 1 初始化类时并不会先初始化其所实现接口
 * 2 初始化接口时并不会先初始化其父接口
 * 只有当程序首次使用特定接口的静态变量时，才会导致该接口的初始化
 */
public class CLoader {
    public static void main(String[] args) {
//        System.out.println(Child6.a);
//        只初始化Child类，输出静态代码块内容和a的值
//        new Child6();
//        输出静态代码块和实例化块
//        System.out.println(Child6.b);
//        只初始化接口，输出Parent6 invoked和b的值
        System.out.println(Child7.c);
//        只初始化子接口，输出Child7 invoked和c的值
    }
}

interface Parent6 {
    int b = new Random().nextInt(5);
    //    创建匿名内部类验证接口初始化
    Thread THREAD = new Thread() {
        //        实例化块，类似于静态代码块，但是静态代码块只执行一次，实例化块每次创建实例都会执行
        {
            System.out.println("Parent6 invoked");
        }
    };
}

class Child6 implements Parent6 {
    static {
        System.out.println("静态代码块");
    }

    {
        System.out.println("实例化块");
    }

    public static int a = 1;
}

interface Child7 extends Parent6 {
    int c = new Random().nextInt(5);
    Thread THREAD = new Thread() {
        {
            System.out.println("Child7 invoked");
        }
    };
}
