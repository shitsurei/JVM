package MemorySpace;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 方法区产生内存溢出错误
 * 对于一个64位的机器来说，JDK8中元空间初始默认大小为21m，到达21m后会执行垃圾回收，无法回收时会自动扩容，直到物理内存上限
 * -XX:MaxMetaspaceSize=10m 最大元空间大小（不扩容上限）
 * 元空间是一个本地的内存空间，通过元空间虚拟机来维护
 */
public class MethodAreaOverflow {
    public static void main(String[] args) {
        while (true) {
//            通过cglib无限创建类，class对象存入元空间（方法区）
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MethodAreaOverflow.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invokeSuper(obj, args1));
            System.out.println("hello world");
            enhancer.create();
            /**
             * Caused by: java.lang.OutOfMemoryError: Metaspace
             * 报错元空间内存溢出
             */
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
