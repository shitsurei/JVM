package classLoader;

/**
 * 12
 * 类加载器的命名空间
 */
public class DefineClassLoader2 {
    public static void main(String[] args) throws Exception {
//        两个类加载器由于是父子关系，属于同一命名空间，因此加载同一个类时只会加载一次
        test1();
    }

    public static void test1() throws Exception {
        String name = "classLoader.ArrayLoader";
        DefineClassLoader loader1 = new DefineClassLoader("loader1");
//        将自定义的两个类加载器设置为父子类加载器关系
        DefineClassLoader loader2 = new DefineClassLoader(loader1, "loader2");
        loader1.setPath("C:\\worktest\\");
        loader2.setPath("C:\\worktest\\");

        System.out.println("------------");
//        classLoader.DefineClassLoader@4554617c
        System.out.println(loader1);
//        classLoader.DefineClassLoader@74a14482
        System.out.println(loader2);
        System.out.println("------------");

        Class<?> clazz = loader1.loadClass(name);
        Object o = clazz.newInstance();
//        classLoader.ArrayLoader@1540e19d
        System.out.println(o);
//        classLoader.DefineClassLoader@4554617c
        System.out.println(o.getClass().getClassLoader());

        System.out.println("--------------------");

        clazz = loader2.loadClass(name);
        o = clazz.newInstance();
//        classLoader.ArrayLoader@677327b6
        System.out.println(o);
//        classLoader.DefineClassLoader@4554617c
        System.out.println(o.getClass().getClassLoader());

//        测试类的类加载器的hashcode相同
    }
}
