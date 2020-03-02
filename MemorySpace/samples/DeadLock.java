package MemorySpace.samples;

/**
 * 死锁模型
 */
public class DeadLock {
//    main方法的主线程在启动AB两个子线程之后直接结束
    public static void main(String[] args) {
        /**
         * 名称: Thread-A
         * 状态: java.lang.Class@1c794cb1上的BLOCKED, 拥有者: Thread-B
         * 总阻止数: 1, 总等待数: 1
         */
        new Thread(()->A.method(),"Thread-A").start();
        /**
         * 名称: Thread-B
         * 状态: java.lang.Class@767573d5上的BLOCKED, 拥有者: Thread-A
         * 总阻止数: 2, 总等待数: 1
         */
        new Thread(()->B.method(),"Thread-B").start();
    }
    /**
     * 只输出一次
     * method from A
     * method from B
     */
}

class A{
//    进入static synchronized修饰的方法时，会对类所对应的class对象上锁
    public static synchronized void method(){
        System.out.println("method from A");
        try {
            Thread.sleep(3000);
            B.method();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class B{
    public static synchronized void method(){
        System.out.println("method from B");
        try {
            Thread.sleep(3000);
            A.method();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
