package classLoader;

/**
 * 05
 * 这个案例用于区分父子类和父子接口
 */
public class SingleTon {
    public static void main(String[] args) {
//        调用类的静态变量表示对该类的主动使用
        System.out.println(Single.count1);//2
        System.out.println(Single.count2);//0
    }
}

/**
 * 单例类
 */
class Single {
    //    初始化阶段“按变量声明顺序”执行，第一步先执行静态代码块
    static {
        System.out.println("single static block");
//        该语句位于count1声明之前编译会报错
//        count1++;
    }

    //    第二步初始化count1为1
    public static int count1 = 1;

    private Single() {
        count1++;
//        注意执行构造方法之前count2有默认值0，是在连接的准备阶段赋值完成的！！
        count2++;
//        以下输出早于main方法中的输出
        System.out.println(count1);//2
        System.out.println(count2);//1
    }

    //    第三步初始化静态变量single，即执行私有构造方法
    private static Single single = new Single();
    //    注意这句声明的位置
    //    第四步，为count2进行赋值操作，根据语句顺序该发生在single对象的初始化之后
    public static int count2 = 0;

    //    获取单例的方法
    public static Single getInstance() {
        return single;
    }
}

/**
 * class classLoader.Single {
 * public static int count1;
 * <p>
 * public static int count2;
 * <p>
 * public static classLoader.Single getInstance();
 * Code:
 * 0: getstatic     #4                  // Field single:LclassLoader/Single;
 * 3: areturn
 * <p>
 * static {};
 * Code:
 * 0: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
 * 3: ldc           #6                  // String single static block
 * 5: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 * 8: new           #8                  // class classLoader/Single
 * 11: dup
 * 12: invokespecial #9                  // Method "<init>":()V
 * 15: putstatic     #4                  // Field single:LclassLoader/Single;
 * 18: iconst_0
 * 19: putstatic     #3                  // Field count2:I
 * 22: return
 * }
 */
