package classLoader;

/**
 * 13
 * 类的卸载
 * 可以通过JVM参数 -XX:+TraceClassUnloading 追踪类的卸载
 * 当代表类A的Class对象不再被引用，Class对象就会结束生命周期，类A在方法区内的数据结构也会被卸载，从而结束类A的生命周期
 * <p>
 * 1 由Java虚拟机自带的类加载器加载的类（根类加载器，扩展类加载器，系统类加载器）在虚拟机的生命周期内始终不会被卸载
 * 2 由用户自定义的类加载器所加载的类是可以被卸载的
 */
public class UnloadClass {
    public static void main(String[] args) throws Exception {
        String name = "classLoader.ArrayLoader";
        DefineClassLoader loader1 = new DefineClassLoader("loader1");
        loader1.setPath("C:\\worktest\\");
        Class<?> clazz = loader1.loadClass(name);
        Object o = clazz.newInstance();
        System.out.println(o);
        System.out.println(o.getClass().getClassLoader());

        loader1 = null;
        clazz = null;
        o = null;
//        [Unloading class classLoader.ArrayLoader 0x0000000100061028]
        System.gc();
    }
}
