package classCode;

/**
 * 01
 * JVM本身不是跨平台的，不同平台编译出来的字节码文件都是相同的
 *
 * 反编译该类-c结果：
 * public class classCode.Introduction {
 *   public classCode.Introduction();
 *     Code:
 *        0: aload_0
 *        【invokespecial】表示调用父类方法
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: aload_0
 *        5: iconst_1
 *        6: putfield      #2                  // Field a:I
 *        9: return
 *
 *   public int getA();
 *     Code:
 *        0: aload_0
 *        1: getfield      #2                  // Field a:I
 *        4: ireturn
 *
 *   public void setA(int);
 *     Code:
 *        0: aload_0
 *        1: iload_1
 *        2: putfield      #2                  // Field a:I
 *        5: return
 * }
 *
 * 反编译该类-verbose结果：
 * public class classCode.Introduction
 *   minor version: 0
 *   major version: 52
 *   flags: ACC_PUBLIC, ACC_SUPER
 * Constant pool:
 *    #1 = Methodref          #4.#20         // java/lang/Object."<init>":()V
 *    #2 = Fieldref           #3.#21         // classCode/Introduction.a:I
 *    #3 = Class              #22            // classCode/Introduction
 *    #4 = Class              #23            // java/lang/Object
 *    #5 = Utf8               a
 *    #6 = Utf8               I
 *    #7 = Utf8               <init>
 *    #8 = Utf8               ()V
 *    #9 = Utf8               Code
 *   #10 = Utf8               LineNumberTable
 *   #11 = Utf8               LocalVariableTable
 *   #12 = Utf8               this
 *   #13 = Utf8               LclassCode/Introduction;
 *   #14 = Utf8               getA
 *   #15 = Utf8               ()I
 *   #16 = Utf8               setA
 *   #17 = Utf8               (I)V
 *   #18 = Utf8               SourceFile
 *   #19 = Utf8               Introduction.java
 *   #20 = NameAndType        #7:#8          // "<init>":()V
 *   #21 = NameAndType        #5:#6          // a:I
 *   #22 = Utf8               classCode/Introduction
 *   #23 = Utf8               java/lang/Object
 * {
 *   public classCode.Introduction();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=2, locals=1, args_size=1
 *          0: aload_0
 *          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *          4: aload_0
 *          5: iconst_1
 *          6: putfield      #2                  // Field a:I
 *          9: return
 *       LineNumberTable:
 *         line 7: 0
 *         line 8: 4
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0      10     0  this   LclassCode/Introduction;
 *
 *   public int getA();
 *     descriptor: ()I
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=1, locals=1, args_size=1
 *          0: aload_0
 *          1: getfield      #2                  // Field a:I
 *          4: ireturn
 *       LineNumberTable:
 *         line 11: 0
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       5     0  this   LclassCode/Introduction;
 *
 *   public void setA(int);
 *     descriptor: (I)V
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=2, locals=2, args_size=2
 *          0: aload_0
 *          1: iload_1
 *          2: putfield      #2                  // Field a:I
 *          5: return
 *       LineNumberTable:
 *         line 15: 0
 *         line 16: 5
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       6     0  this   LclassCode/Introduction;
 *             0       6     1     a   I
 * }
 *
 * 字节码二进制内容：
 * 该文件中显示为十六进制，每两个十六进制数表示一个字节（byte），因为一个字节等于八位（bit）二进制数，而四个二进制数表示的范围等于一个十六进制数表示的范围
 * 例如，二进制数1111表示为十六进制数0xF，二进制数0011表示为十六进制数0x3
 * 因此魔数 cafe babe 占4个字节
 * cafe babe 0000 0034 0018 0a00 0400 1409
 * 0003 0015 0700 1607 0017 0100 0161 0100
 * 0149 0100 063c 696e 6974 3e01 0003 2829
 * 5601 0004 436f 6465 0100 0f4c 696e 654e
 * 756d 6265 7254 6162 6c65 0100 124c 6f63
 * 616c 5661 7269 6162 6c65 5461 626c 6501
 * 0004 7468 6973 0100 184c 636c 6173 7343
 * 6f64 652f 496e 7472 6f64 7563 7469 6f6e
 * 3b01 0004 6765 7441 0100 0328 2949 0100
 * 0473 6574 4101 0004 2849 2956 0100 0a53
 * 6f75 7263 6546 696c 6501 0011 496e 7472
 * 6f64 7563 7469 6f6e 2e6a 6176 610c 0007
 * 0008 0c00 0500 0601 0016 636c 6173 7343
 * 6f64 652f 496e 7472 6f64 7563 7469 6f6e
 * 0100 106a 6176 612f 6c61 6e67 2f4f 626a
 * 6563 7400 2100 0300 0400 0000 0100 0200
 * 0500 0600 0000 0300 0100 0700 0800 0100
 * 0900 0000 3800 0200 0100 0000 0a2a b700
 * 012a 04b5 0002 b100 0000 0200 0a00 0000
 * 0a00 0200 0000 0700 0400 0800 0b00 0000
 * 0c00 0100 0000 0a00 0c00 0d00 0000 0100
 * 0e00 0f00 0100 0900 0000 2f00 0100 0100
 * 0000 052a b400 02ac 0000 0002 000a 0000
 * 0006 0001 0000 000b 000b 0000 000c 0001
 * 0000 0005 000c 000d 0000 0001 0010 0011
 * 0001 0009 0000 003e 0002 0002 0000 0006
 * 2a1b b500 02b1 0000 0002 000a 0000 000a
 * 0002 0000 000f 0005 0010 000b 0000 0016
 * 0002 0000 0006 000c 000d 0000 0000 0006
 * 0005 0006 0001 0001 0012 0000 0002 0013
 */
public class Introduction {
    private int a = 1;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
/**
 * 总结：
 * 1 使用javap -verbose命令分析字节码文件时，会得到字节码文件的魔数、版本号、常量池、类信息、类的构造方法、类中的方法信息、类变量与成员变量等信息
 * 2 魔数：所有的class文件前四个字节都为固定值0xCAFEBABE
 * 3 版本号： 魔术之后的八个字节是【minor version】次版本号（前两个字节）和【major version】主版本号（后两个字节），以0034为例，主版本号52对应jdk8，以此类推51对应jdk7……
 * 4 常量池（constant pool）：主版本号之后就是常量池入口，一个Java类中定义的很多信息例如定义的变量和方法信息都是常量池来描述和维护，可以将其看做class文件的资源仓库。常量池中主要存储两类常量：
 *  a 字面量：文本字符串，声明为final的常量值【常量池中也会存储变量，不是只存常量】
 *  b 符号引用：类和接口的全局限定名，字段的名称和描述符，方法的名称和描述符等
 * 5 常量池的总体结构：
 *  a 常量池数量：紧跟在主板号后面，占两个字节，以0018为例，表示常量池有23个常量，从1开始（常量池数组个数 = 常量池数量 - 1，其中0暂留，目的是满足某些常量池索引值的数据在特定情况下需要表达【不引用任何一个常量池的含义】，根本原因是索引为0也是一个常量，只不过不位于常量表中，对应null值）
 *  b 常量池数组/常量表：紧跟在常量池数量之后，常量池数组不同元素的类型、结构均不同，但是每种元素的第一个数据都是u1类型（u1、u2、u4等分别表示占一个字符、两个字符、四个字符……）作为标志位，JVM在解析常量池时，就会根据u1类型来获取元素的具体类型。
 * 6 在JVM规范中每个变量/字段都有描述信息，描述信息主要的作用是描述字段的数据类型、方法的参数列表（数量、类型和顺序）和返回值，为了压缩字节码文件的体积，对于基本数据类型和void，JVM都只用一个大写字母来表示：
 *  B - byte
 *  C - char
 *  D - double
 *  F - float
 *  I - int
 *  L - long
 *  S - short
 *  Z - boolean
 *  V - void
 *  L - 对象类型（使用字符L加对象的全限定名来表示，如Ljava/lang/String; ，注意包名用斜杠分割）
 *  [ - 数组类型（使用[加具体类型来表示，几个左中括号代表几位数组，如[I表示int数组,[[Ljava/lang/String;表示字符串二维数组）
 * 7 用描述符描述方法时，按照先参数列表后返回值的顺序来描述，参数列表按严格的参数顺序方法一组()之内
 *      如方法String getRealName(int id,String name)描述为(I,Ljava/lang/String;)Ljava/lang/String;
 */
