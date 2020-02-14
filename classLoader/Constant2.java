package classLoader;

import java.util.UUID;

/**
 * 当一个常量的值并非编译期间可以确定，那么其值就不会放到调用类的常量池中，这时在程序运行时，会导致主动使用这个常量所在的类（类被初始化）
 * 对于数组实例来说，其类型是JVM在运行期动态生成的，表示为[Lxxxx.xxxx，其父类是Object
 */
public class Constant2 {
    public static void main(String[] args) {
        System.out.println(Parent3.STR);
//        未导致对类的初始化，说明new出来的实例不是Parent4类型
        Parent4[] parent4s = new Parent4[1];
        System.out.println(parent4s.getClass());
//        class [LclassLoader.Parent4;
        Parent4[][] parent4ss = new Parent4[1][1];
        System.out.println(parent4ss.getClass());
//        class [[LclassLoader.Parent4;
        int a[] = new int[2];
        System.out.println(a.getClass());
//        class [I
        char b[] = new char[2];
        System.out.println(b.getClass());
//        class [C
        boolean c[] = new boolean[2];
        System.out.println(c.getClass());
//        class [Z
//        同理short类型为[S，byte类型为[B
    }
}

class Parent3 {
    //    该常量在编译期间无法确定，需要等到运行期间才能确定
    public static final String STR = UUID.randomUUID().toString();

    //    静态代码块输出，说明类初始化完成
    static {
        System.out.println("Parent3 static block");
    }
}

class Parent4 {
    static {
        System.out.println("Parent4 static block");
    }
}

/**
 * public class classLoader.Constant2 {
 * public classLoader.Constant2();
 * Code:
 * 0: aload_0
 * 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 * 4: return
 * <p>
 * public static void main(java.lang.String[]);
 * Code:
 * 0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 * 3: getstatic     #3                  // Field classLoader/Parent3.STR:Ljava/lang/String;
 * 6: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 * 9: iconst_1
 * 助记符anewarray表示创建一个引用类型（如类、接口、数组）的数组并将其引用值压入栈顶
 * 10: anewarray     #5                  // class classLoader/Parent4
 * 13: astore_1
 * 14: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 * 17: aload_1
 * 18: invokevirtual #6                  // Method java/lang/Object.getClass:()Ljava/lang/Class;
 * 21: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
 * 24: iconst_1
 * 25: iconst_1
 * 26: multianewarray #8,  2             // class "[[LclassLoader/Parent4;"
 * 30: astore_2
 * 31: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 * 34: aload_2
 * 35: invokevirtual #6                  // Method java/lang/Object.getClass:()Ljava/lang/Class;
 * 38: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
 * 41: iconst_2
 * newarray：表示创建一个指定的原始类型（如int，float，char等）的数组并将其引用值压入栈顶
 * 42: newarray       int
 * 44: astore_3
 * 45: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 * 48: aload_3
 * 49: invokevirtual #6                  // Method java/lang/Object.getClass:()Ljava/lang/Class;
 * 52: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
 * 55: return
 * }
 */
