package classLoader;

/**
 * 10
 * 数组的加载
 * 除数组之外任何类都有自己的类加载器，数组中的元素是JVM在运行期根据需要自动创建的
 * 如果数组类型为原生类型（int，float，boolean等），则数组没有类加载器
 */
public class ArrayLoader {
    public static void main(String[] args) {
        String[] strs = new String[2];
        System.out.println(strs.getClass());//class [Ljava.lang.String;
//        此处的null表示根类加载器
        System.out.println(strs.getClass().getClassLoader());//null

        System.out.println("--------------");

        ArrayLoader[] als = new ArrayLoader[2];
        System.out.println(als.getClass());//class [LclassLoader.ArrayLoader;
        System.out.println(als.getClass().getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2

        System.out.println("--------------");

        int[] is = new int[2];
        System.out.println(is.getClass());//class [I
//        此处的null表示没有类加载器
        System.out.println(is.getClass().getClassLoader());//null
    }
}
