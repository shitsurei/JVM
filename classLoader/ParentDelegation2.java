package classLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * 09
 * 双亲委托机制加载顺序
 * 四种获取类加载器的方式：
 * 1 获取某个类的类加载器
 * clazz = Class.forName("xxx")
 * clazz.getClassLoader()
 * 2 获取当前线程上下文的类加载器
 * Thread.currentThread().getContextClassLoader()
 * 3 获取系统类加载器
 * ClassLoader.getSystemClassLoader()
 * 4 获取调用者的类加载器
 *
 */
public class ParentDelegation2 {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//        sun.misc.Launcher$AppClassLoader@18b4aac2     应用类加载器/系统类加载器
        System.out.println(classLoader);
//        根类加载器Bootstrap会返回null
        while (classLoader != null) {
            classLoader = classLoader.getParent();
//            sun.misc.Launcher$ExtClassLoader@1b6d3586 扩展类加载器
//            null     根类加载器
            System.out.println(classLoader);
        }

//        当前线程的上下文类加载器也是应用类加载器
        classLoader = Thread.currentThread().getContextClassLoader();
        String sourceName = "classLoader/ParentDelegation.class";
        Enumeration<URL> urls = classLoader.getResources(sourceName);
        while (urls.hasMoreElements()){
            System.out.println(urls.nextElement());
        }
    }
}
