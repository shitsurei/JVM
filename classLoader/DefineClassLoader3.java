package classLoader;

/**
 * 14
 * 命名空间详解
 * 1 在一个类（A）的内部调用另一个类（B）时（创建B的实例或是访问B的静态变量），如果被调用的类（B）对应的Class对象没有加载过，则会默认使用加载类A的加载器去加载类B，此时依然遵循双亲委派机制
 * 2 子加载器加载的类可以访问父加载器加载的类，父加载器加载的类不能访问子加载器加载的类
 */
public class DefineClassLoader3 {
    public static void main(String[] args) throws Exception{
        DefineClassLoader loader1 = new DefineClassLoader("loader1");
        loader1.setPath("c:\\worktest\\");
        Class<?> clazz = loader1.loadClass("classLoader.samples.MyDog");
        System.out.println(clazz.getClassLoader());
//        创建MyDog类的实例，构造方法中创建MyCat的实例之前会先加载MyCat类
//        默认加载MyCat类的类加载器为MyDog的加载器
//        1 不删除两个类在classPath下的字节码文件，两个类均由系统类加载器加载
//        2 将两个类的字节码文件都删除，两个类均由自定义的loader1加载器加载
//        3 只删除外部类MyDog的字节码文件，MyDog类由自定义类加载器加载，MyCat类由系统类加载器加载（因为MyCat的字节码文件位于classPath文件夹下同时系统类加载器是自定义类加载器的父加载器）
//        4 只删除内部类MyCat的字节码文件，MyDog类由系统类加载器加载，内部的MyCat类默认也由系统类加载器加载，但此时由于删除了classPath下MyCat类的字节码文件，系统类加载器无法对其进行加载，因此会报错NoClassDefFoundError
        Object o = clazz.newInstance();
//        在上面的第三种情况中，MyDog是父加载器加载的类，MyCat是子加载器加载的类，因此在MyDog中无法访问MyCat（会报错），反之可以访问
    }
}


