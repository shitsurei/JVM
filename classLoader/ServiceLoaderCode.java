package classLoader;

//顶层接口，描述SQL驱动的规范
import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 19
 * ServiceLoader 服务提供者加载器，用于实现SPI
 * 【服务】指的是一个已知（功能，规范已知）的接口和抽象类的集合
 * 用法：
 * 1 会在该目录下META-INF/services/寻找和服务全类名相同的文件
 * 2 解析文件中的每一行全类名作为服务具体实现的全类名，加载并创建其单例
 */
public class ServiceLoaderCode {
    /**
     * main方法执行后的类加载过程：
     * 1 main方法所在的ServiceLoaderCode类位于classPath目录下，该类会由系统类加载器加载
     * 2 根据双亲委托机制，系统类加载器加载ServiceLoader类时会逐级向上委托，最终rt.jar包中的ServiceLoader类被根类加载器加载
     * 3 ServiceLoader类在执行load方法时需要加载服务的实现类而非接口，因此根类加载器无法加载classPath目录下的jar包中的类，此时需要借助线程上下文类加载器
     * 4 创建匿名内部类延迟迭代器LazyIterator，传入上下文类加载器和服务集合
     */
    public static void main(String[] args) {
//        如果手动修改了当前的线程上下文类加载器，ServiceLoader就无法扫描到位于classPath下的jar包
//        Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader().getParent());

//        该类内部通过LazyIterator对服务实现类进行（延迟）懒定位和加载
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = serviceLoader.iterator();

        while (iterator.hasNext()){
            Driver driver = iterator.next();
//            driver:com.mysql.cj.jdbc.Driver@b4c966a--loader:sun.misc.Launcher$AppClassLoader@18b4aac2
            System.out.println("driver:"+driver+"--loader:"+driver.getClass().getClassLoader());
        }

        System.out.println("context class loader:"+Thread.currentThread().getContextClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
//        ServiceLoader位于util包中，由根类加载器加载
        System.out.println("ServiceLoader loader:"+ServiceLoader.class.getClassLoader());//null
//        Driver位于sql包中，由根类加载器加载
        System.out.println("Driver loader:"+Driver.class.getClassLoader());//null
    }
}
