package classCode;

/**
 * 03
 */
public class SynchronizeKeyword {
    /**
     * public void test1(java.lang.String);
     *         descriptor: (Ljava/lang/String;)V
     *         flags: ACC_PUBLIC
     *         Code:
     *         stack=2, locals=4, args_size=2
     *         0: aload_1
     *         1: dup
     *         2: astore_2
     *         3: monitorenter
     *         4: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         7: ldc           #3                  // String hello
     *         9: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *         12: aload_2
     *         13: monitorexit
     *         14: goto          22
     *         17: astore_3
     *         18: aload_2
     *         19: monitorexit
     *         20: aload_3
     *         21: athrow
     *         22: return
     *         Exception table:
     *         from    to  target type
     *         4    14    17   any
     *         17    20    17   any
     *         LineNumberTable:
     *         line 8: 0
     *         line 9: 4
     *         line 10: 12
     *         line 11: 22
     *         LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *         0      23     0  this   LclassCode/SynchronizeKeyword;
     *         0      23     1   str   Ljava/lang/String;
     *         StackMapTable: number_of_entries = 2
     */
    public void test1(String str){
//        对方法体而言，内部的synchronized代码块会对传入的对象或字面量加锁
//        反编译代码中的助记符monitorenter（进入到对象的监视器中）和monitorexit分别表示上锁和解锁
//        该指令一般成对出现，如果同步的代码中存在出现异常的可能则需要有异常情况下的解锁操作
        synchronized (str){
            System.out.println("hello");
        }
    }

    /**
     * public synchronized void test2();
     *         descriptor: ()V
     *         flags: ACC_PUBLIC, ACC_SYNCHRONIZED
     *         Code:
     *         stack=2, locals=1, args_size=1
     *         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         3: ldc           #5                  // String world
     *         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *         8: return
     *         LineNumberTable:
     *         line 14: 0
     *         line 15: 8
     *         LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *         0       9     0  this   LclassCode/SynchronizeKeyword;
     */
//    对实例化方法而言，synchronized关键字会对方法所在类实例化的对象加锁
    public synchronized void test2(){
        System.out.println("world");
    }

    /**
     * public static synchronized void test3();
     *         descriptor: ()V
     *         flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
     *         Code:
     *         stack=2, locals=0, args_size=0
     *         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         3: ldc           #6                  // String java
     *         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *         8: return
     *         LineNumberTable:
     *         line 18: 0
     *         line 19: 8
     */
//    对静态方法而言，synchronized关键字会对方法所在类对应的class对象加锁
    public static synchronized void test3(){
        System.out.println("java");
    }
}

