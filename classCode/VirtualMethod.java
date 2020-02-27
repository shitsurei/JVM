package classCode;

import java.util.Date;

/**
 * 07
 * 虚方法表：针对方法调用动态分配的过程，虚拟机会在类的方法区建立一个虚方法表的数据结构（virtual method table，简称vtable）
 * 接口方法表：针对virtualinterface指令，虚拟机会建立一个叫做接口方法表的数据结构（interface method table，简称itable）
 */
public class VirtualMethod {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        Animal animal = new Animal();
        Animal dog = new Dog();
        animal.str("hello");
        dog.date(new Date());
    }
}

class Animal {
    public void str(String str) {
        System.out.println("animal str");
    }

    public void date(Date date) {
        System.out.println("animal date");
    }
}

class Dog extends Animal {
    @Override
    public void str(String str) {
        System.out.println("dog str");
    }

    @Override
    public void date(Date date) {
        System.out.println("dog date");
    }
}
