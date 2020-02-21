package classLoader;

/**
 * 15
 * 不同层级的类加载器的加载路径及其所能加载的jar包
 */
public class OtherClassLoader {
    public static void main(String[] args) {
        OtherClassLoader otherClassLoader = new OtherClassLoader();
        otherClassLoader.showClassLoaders();
        otherClassLoader.test1();
    }

    public void test1() {
        try {
            DefineClassLoader defineClassLoader = new DefineClassLoader("loader1");
            Class<?> clazz = defineClassLoader.loadClass("classLoader.ArrayLoader");
//            通常情况下由系统类加载器加载
//            sun.misc.Launcher$AppClassLoader@18b4aac2
//            当在根类加载器的路径jdk1.8.0_201\jre\classes中创建classLoader\ArrayLoader的字节码文件时，根据双亲委派机制该类会被根类加载器加载
//            null
            System.out.println(clazz.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showClassLoaders() {
        //        根类加载器
//        jdk1.8.0_201\jre\lib\下的jar包：
//        resources rt sunrsasign jsse jce charsets jfr
//        jdk1.8.0_201\jre\classes
        System.out.println(System.getProperty("sun.boot.class.path"));
//        扩展类加载器
//        注意：扩展类加载器只能从jar包中加载类，不能直接加载所属路径下的class文件
//        jdk1.8.0_201\jre\lib\ext
//        C:\WINDOWS\Sun\Java\lib\ext
        System.out.println(System.getProperty("java.ext.dirs"));
//        系统类加载器
//        jdk1.8.0_201\jre\lib\下的jar包：
//        charsets deploy javaws jce jfr jfxswt jsse management-agent plugin resources rt
//        jdk1.8.0_201\jre\lib\ext\下的jar包
//        access-bridge-64 cldrdata dnsns jaccess jfxrt localedata nashorn sunec sunjce_provider sunmscapi sunpkcs11 zipfs
//        C:\workspace\JVM\out\production\JVM
//        C:\Program Files\JetBrains\IntelliJ IDEA 2019.1.2\lib\idea_rt.jar
        System.out.println(System.getProperty("java.class.path"));
    }
}
