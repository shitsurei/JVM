package classCode;

/**
 * 01
 * JVM本身不是跨平台的，不同平台编译出来的字节码文件都是相同的
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
 *
 * 知识点：
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
 *
 * 字节码分解：
 * cafe babe                魔数
 * 0000 0034                版本号（小0，大52）
 * 0018                     常量池数量（24-1=23）
 * 0a                       常量1：CONSTANT_Methodref_info类型（后跟u2类型的class_index和u2类型的name_and_type_index）
 * 0004                     常量1：方法的类名索引，指向常量池中第4个常量
 * 0014                     常量1：方法名和方法描述索引，指向常量池中第20个常量
 * 09                       常量2：CONSTANT_Fieldref_info类型（后跟u2类型的class_index和u2类型的name_and_type_index）
 * 0003                     常量2：变量的类名索引，指向常量池中第3个常量
 * 0015                     常量2：变量名和变量描述索引，指向常量池中第21个常量
 * 07                       常量3：CONSTANT_Class_info类型（name_index）
 * 0016                     常量3：指向类名字面量的索引，指向常量池中第22个常量
 * 07                       常量4：同常量3
 * 0017                     常量4：指向类名字面量的索引，指向常量池中第23个常量
 * 01                       常量5：CONSTANT_Utf8_info类型（后跟一个u2类型length值和若干个u1类型字符值）
 * 0001                     常量5：指明该常量的长度为1
 * 61                       常量5：0x61对应ASCII码表中的‘a’
 * 01                       常量6：同常量5
 * 0001                     常量6：长度1
 * 49                       常量6：0x49对应ASCII码表中的‘I’
 * 01                       常量7：同常量5
 * 0006                     常量7：长度6
 * 3c69 6e69 743e           常量7：对应ASCII结果为‘<init>’
 * 01                       常量8：同常量5
 * 0003                     常量8：长度3
 * 2829 56                  常量8：对应ASCII结果为‘()V’
 * 01
 * 0004
 * 436f 6465                常量9：Code
 * 01
 * 000f
 * 4c69 6e65 4e75 6d62 6572 5461 626c 65                            常量10：LineNumberTable
 * 01
 * 0012
 * 4c6f 6361 6c56 6172 6961 626c 6554 6162 6c65                     常量11：LocalVariableTable
 * 01
 * 0004
 * 7468 6973                常量12：this
 * 01
 * 0018
 * 4c63 6c61 7373 436f 6465 2f49 6e74 726f 6475 6374 696f 6e3b      常量13：LclassCode/Introduction;
 * 01
 * 0004
 * 6765 7441                常量14：getA
 * 01
 * 0003
 * 2829 49                  常量15：()I
 * 01
 * 0004
 * 7365 7441                常量16：setA
 * 01
 * 0004
 * 2849 2956                常量17：(I)V
 * 01
 * 000a
 * 536f 7572 6365 4669 6c65 常量18：SourceFile
 * 01
 * 0011
 * 496e 7472 6f64 7563 7469 6f6e 2e6a 6176 61                       常量19：Introduction.java
 * 0c                       常量20：CONSTANT_NameAndType_info类型（后跟两个u2类型name_index）
 * 0007                     常量20：表明对方法或变量名的索引，指向常量池中第7个常量
 * 0008                     常量20：表明对方法或变量名的描述，指向常量池中第8个常量
 * 0c                       常量21：同常量20
 * 0005
 * 0006
 * 01
 * 0016
 * 636c 6173 7343 6f64 652f 496e 7472 6f64 7563 7469 6f6e           常量22：classCode/Introduction
 * 01
 * 0010
 * 6a61 7661 2f6c 616e 672f 4f62 6a65 6374                          常量23：java/lang/Object
 * 0021                     当前类的访问修饰符（access flags）：ACC_PUBLIC（该类的访问修饰符为public）【0x0001】和ACC_SUPER（该标志表示该类允许使用invokespecial字节码指令）【0x0020】的并集
 * 0003                     当前类名在常量池中的索引
 * 0004                     当前类的【父类】在常量池中的索引（u2类型，单继承）
 * 0000                     当前类实现的【接口表】数目（u2类型，多实现），后跟若干字节
 * 0001                     【变量/字段表】集合数量，其中字段表用于描述类和接口中声明的变量（不包含方法中的局部变量）
 * 0002                     变量1：0x0002对应访问修饰符为private
 * 0005                     变量1：变量名对应常量池索引（a）
 * 0006                     变量1：变量描述符对应常量池索引（I）
 * 0000                     变量1：属性个数
 * 0003                     【方法表】中方法数量
 * 0001                     方法1：0x0001对应访问修饰符为public
 * 0007                     方法1：方法名对应常量池索引（<init>）
 * 0008                     方法1：方法描述索引（()V）
 * 0001                     方法1：属性数量
 * 0009                     方法属性1：属性名索引（Code，每个方法基本都有，内容为为方法执行的字节码指令）
 * 0000 0038                【Code属性表】该属性所包含的字节数（u4类型），不包含本身和属性名索引（整个code属性表长度-6个字节），即之后的字节数
 * 0002                     【Code属性表】max_stack 方法运行时操作数栈的最大深度
 * 0001                     【Code属性表】max_local 方法运行时创建的局部变量的数目（包含形参个数）
 * 0000 000a                【Code属性表】code_length 方法所包含的字节码的字节数以及具体的指令码长度（字节码对应助记符，《JVM规范》中有定义，实际编译器最多只接受65535条字节码指令，即只使用了u2长度）
 * 2a                       对应助记符aload_0（aload_1对应0x2b，以此类推到aload_3）
 * b7 0001                  0xb7对应invokespecial 表示调用实例方法，后跟u2类型的方法索引
 * 2a                       对应助记符aload_0（aload_1对应0x2b，以此类推到aload_3）
 * 04                       对应iconst_1 表示将数字1压入运行时方法栈栈顶 （对应iconst_2对应0x5，以此类推到iconst_5，同时0和-1也适用，-1对应iconst_m1）
 * b5 0002                  0xb5对应putfield 表示为某个索引变量赋值（栈顶值），后跟u2类型的变量索引
 * b1                       对应return（从当前方法返回void）
 * 0000                     【Code属性表】异常数量（不为0时后跟【异常表】）
 * 0002                     【Code属性表】“属性”数量（不为0时后跟【属性表】）
 * 000a                     属性索引1（行号表，用于异常调试给出行号）
 * 0000 000a                【行号表】长度为10
 * 0002                     【行号表】中有几对映射（每对映射为两个u2类型，分别指字节码的偏移量和代码的行号，一一对应）
 * 0000 0007                偏移量为0，映射到行号为7（JVM自建的构造方法由于不出现在代码中，因此对应关系与类名同行）
 * 0004 0008                偏移量为4，映射到行号为8（为类变量a赋值1）
 * 000b                     属性索引2（局部变量表）
 * 0000 000c                【局部变量表】长度为12
 * 0001                     局部变量个数
 * 0000                     局部变量开始位置
 * 000a                     局部变量结束位置
 * 000c                     局部变量索引（当前对象this）
 * 000d                     局部变量描述（LclassCode/Introduction;）
 * 0000                     做校验检查用
 * 0001                     方法2：0x0001对应访问修饰符为public
 * 000e                     方法2：方法名对应常量池索引（getA）
 * 000f                     方法2：方法描述对应常量池索引（()I）
 * 0001                     方法2：属性数量
 * 0009                     方法2：对应Code属性索引
 * 0000 002f                【Code属性表】长度为47
 * 0001                     max_stack
 * 0001                     max_local
 * 0000 0005                code_length（方法真正的执行体）
 * 2a                       aload_0（将第一个引用型本地变量推动至栈顶）
 * b4 0002                  getfield（获取指定类的实例域并将其压入栈顶）0x0002对应常量池中第二个常量（变量a）
 * ac                       ireturn（从当前方法返回int类型）
 * 0000                     异常数量（不为0时后跟【异常表】）
 * 0002                     “属性”数量（不为0时后跟【属性表】）
 * 000a                     【行号表】
 * 0000 0006                行号表长度为6
 * 0001                     只一对映射
 * 0000 000b                偏移量0，对应行号11
 * 000b                     【局部变量表】
 * 0000 000c                局部变量表长度为12
 * 0001                     一个局部变量
 * 0000                     局部变量1：开始位置
 * 0005                     局部变量1：结束位置
 * 000c                     局部变量1：索引（当前对象this）
 * 000d                     局部变量1：描述（LclassCode/Introduction;）
 * 0000                     局部变量1：做校验检查用
 * 0001                     方法3：public
 * 0010                     方法3：方法名对应常量池第16个常量（setA）
 * 0011                     方法3：方法描述对应常量池第17个常量（(I)V）
 * 0001                     方法3：1个属性
 * 0009                     属性Code索引
 * 0000 003e                Code表长度为62
 * 0002                     max_stack
 * 0002                     max_local
 * 0000 0006                code_length
 * 2a                       aload_0
 * 1b                       iload_1（将第二个int型本地变量推送至栈顶）
 * b5 0002                  putfield（为指定的类的实例域赋值）0x0002对应常量池中第二个常量（变量a）
 * b1                       对应return（从当前方法返回void）
 * 0000                     【异常表】数量
 * 0002                     【方法属性表】数量
 * 000a                     【行号表】索引
 * 0000 000a                行号表长度
 * 0002                     2对映射关系
 * 0000 000f                偏移量0（0x2a），对应行号15
 * 0005 0010                偏移量5（0xb1），对应行号16
 * 000b                     【局部变量表】索引
 * 0000 0016                局部变量表长度为22
 * 0002                     两个局部变量
 * 0000                     局部变量1：开始位置
 * 0006                     局部变量1：长度
 * 000c                     局部变量1：变量名索引（当前对象this）
 * 000d                     局部变量1：变量描述符索引（LclassCode/Introduction;）
 * 0000                     局部变量1：做校验检查用
 * 0000                     局部变量2：开始位置
 * 0006                     局部变量2：长度
 * 0005                     局部变量2：变量名索引（a）
 * 0006                     局部变量2：变量描述符索引（I）
 * 0001                     局部变量2：做校验检查用
 * 0001                     当前【类属性】个数
 * 0012                     属性1：属性名索引（SourceFile）
 * 0000 0002                属性1：属性长度2字节
 * 0013                     属性1：属性值索引（Introduction.java）
 */

