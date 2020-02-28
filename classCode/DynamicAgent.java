package classCode;

import classCode.samples.AgentInterface;
import classCode.samples.DynamicSubject;
import classCode.samples.ImplementClass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 09
 * 动态代理：只能代理接口
 * 优点：在真实对象还不存在的情况下将代理对象创建出来
 * spring中对bean采取动态代理或cglib的方式实现方法的拓展（打印日志等功能）
 */
public class DynamicAgent {

    public static void main(String[] args) {
//        将动态代理所生成的类（字节码文件）写入磁盘，该属性设置为true后系统目录下生成com/sun/proxy/$Proxy0.class
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
//        被代理的类
        ImplementClass ic = new ImplementClass();
//        配置代理类
        InvocationHandler invocationHandler = new DynamicSubject(ic);
        Class<?> clazz = ic.getClass();
//        创建代理实例（动态代理实例的类加载器和接口采用真实实现类的类加载器和接口）
        AgentInterface agent = (AgentInterface) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), invocationHandler);
//        调用方法
        agent.request();
        /**
         * 输出：
         * before method:public abstract void classCode.samples.AgentInterface.request()
         * implement agent interface
         * after method:public abstract void classCode.samples.AgentInterface.request()
         */
//        agent的真实类型是com.sun.proxy.$Proxy0
        System.out.println(agent.getClass());
//        其父类是java.lang.reflect.Proxy类
        System.out.println(agent.getClass().getSuperclass());
    }
}
/**
 * 处理被代理的接口中的方法，代理类还会默认代理Object类中的这三个方法：equals toString hashCode
 * 通过查看反编译的代理类即可证实
 * package com.sun.proxy;
 *
 * import classCode.samples.AgentInterface;
 * import java.lang.reflect.InvocationHandler;
 * import java.lang.reflect.Method;
 * import java.lang.reflect.Proxy;
 * import java.lang.reflect.UndeclaredThrowableException;
 *
 * public final class $Proxy0 extends Proxy implements AgentInterface {
 *     private static Method m1;
 *     private static Method m2;
 *     private static Method m3;
 *     private static Method m0;
 *
 *     public $Proxy0(InvocationHandler var1) throws  {
 *         super(var1);
 *     }
 *
 *     public final boolean equals(Object var1) throws  {
 *         try {
 *             return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
 *         } catch (RuntimeException | Error var3) {
 *             throw var3;
 *         } catch (Throwable var4) {
 *             throw new UndeclaredThrowableException(var4);
 *         }
 *     }
 *
 *     public final String toString() throws  {
 *         try {
 *             return (String)super.h.invoke(this, m2, (Object[])null);
 *         } catch (RuntimeException | Error var2) {
 *             throw var2;
 *         } catch (Throwable var3) {
 *             throw new UndeclaredThrowableException(var3);
 *         }
 *     }
 *
 *     public final void request() throws  {
 *         try {
 *             super.h.invoke(this, m3, (Object[])null);
 *         } catch (RuntimeException | Error var2) {
 *             throw var2;
 *         } catch (Throwable var3) {
 *             throw new UndeclaredThrowableException(var3);
 *         }
 *     }
 *
 *     public final int hashCode() throws  {
 *         try {
 *             return (Integer)super.h.invoke(this, m0, (Object[])null);
 *         } catch (RuntimeException | Error var2) {
 *             throw var2;
 *         } catch (Throwable var3) {
 *             throw new UndeclaredThrowableException(var3);
 *         }
 *     }
 *
 *     static {
 *         try {
 *             m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
 *             m2 = Class.forName("java.lang.Object").getMethod("toString");
 *             m3 = Class.forName("classCode.samples.AgentInterface").getMethod("request");
 *             m0 = Class.forName("java.lang.Object").getMethod("hashCode");
 *         } catch (NoSuchMethodException var2) {
 *             throw new NoSuchMethodError(var2.getMessage());
 *         } catch (ClassNotFoundException var3) {
 *             throw new NoClassDefFoundError(var3.getMessage());
 *         }
 *     }
 * }
 */
