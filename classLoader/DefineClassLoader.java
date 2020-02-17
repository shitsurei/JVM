package classLoader;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 11
 * 自定义类加载器
 * 需要重写findClass方法，通过自己的逻辑找到二进制的名字所对应的类的class对象
 * <p>
 * loadClass方法执行的三个步骤:
 * 1 执行findLoadedClass方法检查这个类是否已经被加载过(一个类只被加载一次)
 * 2 执行父加载器的loadClass方法,如果父加载器为null则启动虚拟机内建的类加载器代替(即根类加载器)
 * 3 调用findClass方法寻找该类
 * 4 如果以上步骤找到了该类,就执行resolveClass解析该类
 */
public class DefineClassLoader extends ClassLoader {
    //    类加载器名，标识性作用
    private String classLoaderName;
    //    用于指定类加载器从某个绝对路径去加载
    private String path;
    //    文件扩展名
    private final String fileExtension = ".class";

    public DefineClassLoader(String classLoaderName) {
        //    默认使用系统类加载器作为双亲
        super();
        this.classLoaderName = classLoaderName;
    }

    public DefineClassLoader(ClassLoader parent, String classLoaderName) {
        //    使用指定的类加载器作为双亲
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 根据指定的二进制名字寻找到一个Class的对象
     * 该方法会在检查完父加载器之后被loadClass方法调用
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("通过自定义类加载器加载");
        System.out.println("加载的类：" + name);
        System.out.println("类加载器名：" + this.classLoaderName);
        byte[] data = this.loadClassData(name);
//        将字节数组转化为一个Class类的实例
        return this.defineClass(name, data, 0, data.length);
    }

    /**
     * 用户传入想要加载的类的名字，通过字节数组返回类的结构
     * 自己编写的寻找二进制文件的信息
     *
     * @param className
     * @return
     */
    private byte[] loadClassData(String className) {
//        IO操作
        InputStream is = null;
        byte[] data = null;
        ByteOutputStream bops = null;
        className = className.replace(".", "/");
        try {
            is = new FileInputStream(new File(this.path + className + this.fileExtension));
            bops = new ByteOutputStream();
            int ch;
            while (-1 != (ch = is.read())) {
                bops.write(ch);
            }
            data = bops.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                bops.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void main(String[] args) throws Exception {
//        如果系统类加载器可以加载该类，就不会通过我们自定义的类加载器去
//        test1();
//        test2();
//        两个类加载器的命名空间不同，因此相同的类加载了两次
//        【每个类加载器都有自己的命名空间，命名空间由该加载器及其所有父加载器所加载的类组成，在同一个命名空间中不会出现完整名字相同的两个类】
        test3();
    }

    public static void test1() throws Exception {
        DefineClassLoader defineClassLoader = new DefineClassLoader("loader");
        defineClassLoader.setPath("C:\\workspace\\JVM\\out\\production\\JVM");
        String name = "classLoader.ArrayLoader";
        Class<?> clazz = defineClassLoader.loadClass(name);
//        class classLoader.ArrayLoader
        System.out.println(clazz);
        Object o = clazz.newInstance();
//            classLoader.ArrayLoader@1b6d3586
        System.out.println(o);
//            DefineClassLoader类加载器的双亲系统类加载器可以加载ArrayLoader类，故不需要DefineClassLoader类加载器来加载
//            sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(o.getClass().getClassLoader());
    }

    public static void test2() throws Exception {
        DefineClassLoader defineClassLoader = new DefineClassLoader("loader");
        defineClassLoader.setPath("C:\\worktest\\");
        String name = "classLoader.ArrayLoader";
        Class<?> clazz = defineClassLoader.loadClass(name);
//        class classLoader.ArrayLoader
        System.out.println(clazz);
        Object o = clazz.newInstance();
//            classLoader.ArrayLoader@4554617c
        System.out.println(o);
//            系统类加载器无法加载ArrayLoader类，返回至DefineClassLoader类加载器来加载
//            classLoader.DefineClassLoader@1b6d3586
        System.out.println(o.getClass().getClassLoader());
    }


    public static void test3() throws Exception {
//        测试前需要先删除当前程序构建好的class文件，否则根据双亲委托机制系统类加载器会完成对测试类的加载
        String name = "classLoader.ArrayLoader";

        DefineClassLoader defineClassLoader = new DefineClassLoader("loader");
        defineClassLoader.setPath("C:\\worktest\\");
        Class<?> clazz = defineClassLoader.loadClass(name);
        Object o = clazz.newInstance();
//        两个类加载器的hashcode不同，代表两个类属于不同的命名空间
//        classLoader.ArrayLoader@1055e4af
        System.out.println(o);
//        classLoader.DefineClassLoader@1b6d3586
        System.out.println(o.getClass().getClassLoader());

        System.out.println("--------------------");

        DefineClassLoader defineClassLoader2 = new DefineClassLoader("loader2");
        defineClassLoader2.setPath("C:\\worktest\\");
        clazz = defineClassLoader2.loadClass(name);
        o = clazz.newInstance();
//        classLoader.ArrayLoader@5577140b
        System.out.println(o);
//        classLoader.DefineClassLoader@1540e19d
        System.out.println(o.getClass().getClassLoader());

//如果使用系统类加载器加载这两个类，那么加载器的hashcode就会相同，都为系统类加载器sun.misc.Launcher$AppClassLoader@18b4aac2
    }

}
