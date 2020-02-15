package classLoader;

/**
 * 06
 * 类加载器的双亲委托机制
 * 各个加载器按照父子关系形成树形结构，除了根类加载器之外，其余的加载器有且只有一个父加载器
 * 当加载器想要加载某个类时，先去委托其父类加载器加载（自底向上不断追溯到根类），到根类加载器后再自顶向下尝试加载类，直到返回到原加载器
 * 在这一过程中一旦某个加载器可以加载该类，则加载成功
 *
 * 根类加载器（Bootstrap ClassLoader）从jre\lib\rt.jar包下或-Xbootclasspath参数指定下的包中加载类
 * 扩展加载器（Extension ClassLoader）从jre\lib\ext\*.jar包下或-Djava.ext.dirs参数指定下的包中加载类
 * 应用加载器（App ClassLoader）从CLASSPATH路径下或-Djava.class.path参数指定下的包中加载类
 * 自定义加载器（Custom ClassLoader）通过java.lang.ClassLoader的子类自定义加载类
 */
public class ParentDelegation {
    public static void main(String[] args) throws Exception{
//        调用ClassLoader类的loadClass方法加载一个类，并不是对类的主动使用，不会导致类的初始化
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class<?> clazz = classLoader.loadClass("classLoader.C");
//        仅输出class classLoader.C，C类未初始化
        System.out.println(clazz);

        clazz = Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader());//null，表示该类是由根类加载器加载
        clazz = Class.forName("classLoader.C");
        System.out.println(clazz.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2，应用类加载器
    }
}
class C{
    static {
        System.out.println("C static block");
    }
}
