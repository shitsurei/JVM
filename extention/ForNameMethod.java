package extention;

import classLoader.DefineClassLoader;
import extention.samples.MySample1;

/**
 * 01
 * Class类forName静态方法
 * 重载1：全类名（默认初始化，使用加载当前调用该方法的类的类加载器去加载，即this.getClass().getClassLoader()）
 * 重载2：全类名、是否初始化、用于加载该类的类加载器
 */
public class ForNameMethod {
    public static void main(String[] args) {
        new ForNameMethod().test();
    }

    /**
     * 通过删除编译好的字节码文件，测试MySample1类的类加载器和初始化过程
     * 删除class文件后不修改用于加载的类加载器会报错
     * 初始化为true时会执行类的静态块代码，否则不执行（即类没有被初始化）
     */
    public void test() {
        DefineClassLoader loader1 = new DefineClassLoader("loader1");
        loader1.setPath("C:\\worktest\\");
        Class<?> clazz = null;
        try {
//      通过自定义类加载器加载
//      加载的类：extention.samples.MySample1
//      类加载器名：loader1
            clazz = Class.forName("extention.samples.MySample1", true, loader1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//      class extention.samples.MySample1
        System.out.println(clazz);
//      classLoader.DefineClassLoader@4554617c
        System.out.println(clazz.getClassLoader());
    }
}
