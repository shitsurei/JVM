package classLoader;

import java.lang.reflect.Method;

/**
 * 16
 * 类加载器命名空间演示
 */
public class NameSpace {
    public static void main(String[] args) throws Exception {
        DefineClassLoader loader1 = new DefineClassLoader("loader1");
        DefineClassLoader loader2 = new DefineClassLoader("loader2");
        loader1.setPath("C:\\worktest\\");
        loader2.setPath("C:\\worktest\\");
        String name = "classLoader.samples.MyPerson";
        Class<?> clazz1 = loader1.loadClass(name);
        Class<?> clazz2 = loader2.loadClass(name);
//        1 在不删除classPath下的字节码文件时，遵循双亲委派机制，clazz1和clazz2都由系统类加载器加载，是同一个命名空间下的类，返回true
//        2 删除项目空间下的class文件之后，只能由自定义加载器加载该类，此时loader1和loader2是不同的类加载器，不存在父子关系，命名空间不同，故会加载两次MyPerson类，返回false
        System.out.println(clazz1 == clazz2);
        System.out.println(clazz1.getClassLoader());
        System.out.println(clazz2.getClassLoader());

        /**
         * 通过自定义类加载器加载
         * 加载的类：classLoader.samples.MyPerson
         * 类加载器名：loader1
         * 通过自定义类加载器加载
         * 加载的类：classLoader.samples.MyPerson
         * 类加载器名：loader2
         * false
         * classLoader.DefineClassLoader@4554617c
         * classLoader.DefineClassLoader@1540e19d
         */

        Object o1 = clazz1.newInstance();
        Object o2 = clazz2.newInstance();
//        通过反射机制获取并调用samples.MyPerson类的setMyPerson方法，其中getMethod和invoke的第二个参数都是可变参数，且两个参数的长度类型需要一一对应
//        实际上就是第一个方法定义方法名和参数类型，第二个方法传入调用方法的实例对象和方法参数
        Method method = clazz1.getMethod("setMyPerson",Object.class);
        method.invoke(o1,o2);
//        当删除classPath下的字节码文件后，调用反射方法会报类型转换错误的异常，但是两个类的全类名完全相同，问题就出来加载这两个类的命名空间不同
//        Caused by: java.lang.ClassCastException: classLoader.samples.MyPerson cannot be cast to classLoader.samples.MyPerson
    }
}
