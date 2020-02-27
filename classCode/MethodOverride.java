package classCode;

/**
 * 06
 * 方法重写，动态分派（方法接收者，即调用者），多态
 * invokevirtual字节码指令的多态查找流程（运行期间）：
 * 首先找到操作数栈顶的第一个元素，寻找这个元素（引用）所指向的对象的实际类型
 *  a 如果找到了与常量池中的名称和描述符都相同的方法，并且也具备相应的访问权限，返回目标方法的直接引用
 *  b 如果找不到，就按继承的层次关系从子类向父类依次重复这种查找过程，直到找到各方面都匹配的特定对象的方法
 *  c 如果一直找不到，则会抛出异常
 *
 *  结论：方法重载是静态的编译器行为，方法重写是动态的运行期行为（方法接收者不同）
 */
public class MethodOverride {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        Fruit app = new Apple();//         invokespecial #4 <classCode/Apple.<init>>
        Fruit ora = new Orange();//        invokespecial #6 <classCode/Orange.<init>>
//        调用虚方法，字节码的参数依然是静态分派的值（以下三个均如此）
        System.out.println(app);
        System.out.println(app.hashCode());
        app.name();//         invokevirtual #7 <classCode/Fruit.name>
        /**
         * 1 首先找到操作数栈顶的第一个元素（app对象引用，即内存地址）
         * 2 寻找这个元素（引用）所指向的对象的实际类型（内存空间0x74a14482的对象）
         * 3 寻找与常量池中的名称和描述符都相同的方法，并且也具备相应的访问权限（Apple类中重写的name方法符合条件）
         * 4 返回目标方法的直接引用
         */
        ora.name();
        app = new Orange();
        app.name();
    }
}

class Fruit {
    public void name() {
        System.out.println("fruit");
    }
}

class Apple extends Fruit {
    @Override
    public void name() {
        System.out.println("apple");
    }
}

class Orange extends Fruit {
    @Override
    public void name() {
        System.out.println("orange");
    }
}
