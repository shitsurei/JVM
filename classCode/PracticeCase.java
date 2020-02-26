package classCode;

/**
 * 02
 * 复杂字节码分析
 */
public class PracticeCase {
    String str = "welcome";
    private int x = 5;
    public static Integer y = 10;

    public static void main(String[] args) {
        PracticeCase pc = new PracticeCase();
        pc.setX(8);
        y = 20;
    }

    public void setX(int x) {
        this.x = x;
    }
}
/**
 * 反编译结果：
 * Classfile /C:/workspace/JVM/out/production/JVM/classCode/PracticeCase.class
 *   Last modified 2020-2-25; size 826 bytes
 *   MD5 checksum 73691d06d1d14fa67ed8494a495a7517
 *   Compiled from "PracticeCase.java"
 * public class classCode.PracticeCase
 *   minor version: 0
 *   major version: 52
 *   flags: ACC_PUBLIC, ACC_SUPER
 * Constant pool:
 *    #1 = Methodref          #10.#34        // java/lang/Object."<init>":()V
 *    #2 = String             #35            // welcome
 *    #3 = Fieldref           #5.#36         // classCode/PracticeCase.str:Ljava/lang/String;
 *    #4 = Fieldref           #5.#37         // classCode/PracticeCase.x:I
 *    #5 = Class              #38            // classCode/PracticeCase
 *    #6 = Methodref          #5.#34         // classCode/PracticeCase."<init>":()V
 *    #7 = Methodref          #5.#39         // classCode/PracticeCase.setX:(I)V
 *    #8 = Methodref          #40.#41        // java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
 *    #9 = Fieldref           #5.#42         // classCode/PracticeCase.y:Ljava/lang/Integer;
 *   #10 = Class              #43            // java/lang/Object
 *   #11 = Utf8               str
 *   #12 = Utf8               Ljava/lang/String;
 *   #13 = Utf8               x
 *   #14 = Utf8               I
 *   #15 = Utf8               y
 *   #16 = Utf8               Ljava/lang/Integer;
 *   #17 = Utf8               <init>
 *   #18 = Utf8               ()V
 *   #19 = Utf8               Code
 *   #20 = Utf8               LineNumberTable
 *   #21 = Utf8               LocalVariableTable
 *   #22 = Utf8               this
 *   #23 = Utf8               LclassCode/PracticeCase;
 *   #24 = Utf8               main
 *   #25 = Utf8               ([Ljava/lang/String;)V
 *   #26 = Utf8               args
 *   #27 = Utf8               [Ljava/lang/String;
 *   #28 = Utf8               pc
 *   #29 = Utf8               setX
 *   #30 = Utf8               (I)V
 *   #31 = Utf8               <clinit>
 *   #32 = Utf8               SourceFile
 *   #33 = Utf8               PracticeCase.java
 *   #34 = NameAndType        #17:#18        // "<init>":()V
 *   #35 = Utf8               welcome
 *   #36 = NameAndType        #11:#12        // str:Ljava/lang/String;
 *   #37 = NameAndType        #13:#14        // x:I
 *   #38 = Utf8               classCode/PracticeCase
 *   #39 = NameAndType        #29:#30        // setX:(I)V
 *   #40 = Class              #44            // java/lang/Integer
 *   #41 = NameAndType        #45:#46        // valueOf:(I)Ljava/lang/Integer;
 *   #42 = NameAndType        #15:#16        // y:Ljava/lang/Integer;
 *   #43 = Utf8               java/lang/Object
 *   #44 = Utf8               java/lang/Integer
 *   #45 = Utf8               valueOf
 *   #46 = Utf8               (I)Ljava/lang/Integer;
 * {
 *   java.lang.String str;
 *     descriptor: Ljava/lang/String;
 *     flags:
 *
 *   public static java.lang.Integer y;
 *     descriptor: Ljava/lang/Integer;
 *     flags: ACC_PUBLIC, ACC_STATIC
 *
 *   public classCode.PracticeCase();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=2, locals=1, args_size=1
 *          0: aload_0
 *          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *          4: aload_0
 *          5: ldc           #2                  // String welcome
 *          7: putfield      #3                  // Field str:Ljava/lang/String;
 *         10: aload_0
 *         11: iconst_5
 *         12: putfield      #4                  // Field x:I
 *         15: return
 *       LineNumberTable:
 *         line 7: 0
 *         line 8: 4
 *         line 9: 10
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0      16     0  this   LclassCode/PracticeCase;
 *
 *   public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=2, locals=2, args_size=1
 *          0: new           #5                  // class classCode/PracticeCase
 *          3: dup
 *          4: invokespecial #6                  // Method "<init>":()V
 *          7: astore_1
 *          8: aload_1
 *          9: bipush        8
 *         11: invokevirtual #7                  // Method setX:(I)V
 *         14: bipush        20
 *         16: invokestatic  #8                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
 *         19: putstatic     #9                  // Field y:Ljava/lang/Integer;
 *         22: return
 *       LineNumberTable:
 *         line 13: 0
 *         line 14: 8
 *         line 15: 14
 *         line 16: 22
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0      23     0  args   [Ljava/lang/String;
 *             8      15     1    pc   LclassCode/PracticeCase;
 *
 *   public void setX(int);
 *     descriptor: (I)V
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=2, locals=2, args_size=2
 *          0: aload_0
 *          1: iload_1
 *          2: putfield      #4                  // Field x:I
 *          5: return
 *       LineNumberTable:
 *         line 19: 0
 *         line 20: 5
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       6     0  this   LclassCode/PracticeCase;
 *             0       6     1     x   I
 *
 *   static {};
 *     descriptor: ()V
 *     flags: ACC_STATIC
 *     Code:
 *       stack=1, locals=0, args_size=0
 *          0: bipush        10
 *          2: invokestatic  #8                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
 *          5: putstatic     #9                  // Field y:Ljava/lang/Integer;
 *          8: return
 *       LineNumberTable:
 *         line 10: 0
 * }
 * SourceFile: "PracticeCase.java"
 */

/**
 * 分析
 * cafe babe        魔数
 * 0000 0034        版本号
 * 002f         常量池数量为46（47-1=46）
 * 0a       常量1：方法引用
 * 000a     常量1：声明方法的类描述符索引，第10个常量
 * 0022     常量1：方法名称和类型描述符索引，第34个常量
 * 08       常量2：字符串类型字面量
 * 0023     常量2：字符串字面量索引，第35个常量
 * 09       常量3：字段的符号引用
 * 0005     常量3：声明字段的类或接口描述符索引，第5个常量
 * 0024     常量3：字段描述符索引，第36个常量
 * 09       常量4：同3
 * 0005     常量4：声明类为常量5
 * 0025     常量4：描述符为常量37
 * 07       常量5：类或接口的符号引用
 * 0026     常量5：全限定名常量索引，第38个常量
 * 0a       常量6：同1
 * 0005     常量6：声明方法的类为常量5
 * 0022     常量6：方法描述为常量34
 * 0a       常量7：同1
 * 0005     常量7：声明方法的类为常量5
 * 0027     常量7：方法描述为常量39
 * 0a       常量8：同1
 * 0028     常量8：声明方法的类为常量40
 * 0029     常量8：方法描述为常量41
 * 09       常量9：同3
 * 0005     常量9：声明类为常量5
 * 002a     常量9：描述符为常量42
 * 07       常量10：同5
 * 002b     常量10：全限定名为常量43
 * 01       常量11：utf8编码字符串
 * 0003     常量11：字符串长度为3字节
 * 7374 72      常量11：0x737472对应utf8编码为‘str’
 * 01       常量12：同11
 * 0012     常量12：长18字节
 * 4c6a 6176 612f 6c61 6e67 2f53 7472 696e 673b     常量12：Ljava/lang/String;
 * 01       常量13：同11
 * 0001     常量13：长1
 * 78       常量13：‘x’
 * 01       常量14：同11
 * 0001     常量14：长1
 * 49       常量14：‘I’
 * 01       常量15：同11
 * 0001     常量15：长1
 * 79       常量15：‘y’
 * 01       常量16：同11
 * 0013     常量16：长19
 * 4c6a 6176 612f 6c61 6e67 2f49 6e74 6567 6572 3b      常量16：‘Ljava/lang/Integer;’
 * 01       常量17：同11
 * 0006     常量17：长6
 * 3c69 6e69 743e       常量17：‘<init>’
 * 01       常量18：同11
 * 0003     常量18：长3
 * 2829 56      常量18：‘()V’
 * 01       常量19：同11
 * 0004     常量19：长4
 * 436f 6465        常量19：‘Code’
 * 01       常量20：同11
 * 000f     常量20：长15
 * 4c69 6e65 4e75 6d62 6572 5461 626c 65        常量20：‘LineNumberTable’
 * 01       常量21：同11
 * 0012     常量21：长18
 * 4c6f 6361 6c56 6172 6961 626c 6554 6162 6c65     常量21：‘LocalVariableTable’
 * 01       常量22：同11
 * 0004     常量22：长4
 * 7468 6973        常量22：‘this’
 * 01       常量23：同11
 * 0018     常量23：长24
 * 4c63 6c61 7373 436f 6465 2f50 7261 6374 6963 6543 6173 653b      常量23：‘LclassCode/PracticeCase;’
 * 01       常量24：同11
 * 0004     常量24：长4
 * 6d61 696e        常量24：‘main’
 * 01       常量25：同11
 * 0016     常量25：长22
 * 285b 4c6a 6176 612f 6c61 6e67 2f53 7472 696e 673b 2956       常量25：‘([Ljava/lang/String;)V’
 * 01       常量26：同11
 * 0004     常量26：长4
 * 6172 6773        常量26：‘args’
 * 01       常量27：同11
 * 0013     常量27：长19
 * 5b4c 6a61 7661 2f6c 616e 672f 5374 7269 6e67 3b      常量27：‘[Ljava/lang/String;’
 * 01       常量28：同11
 * 0002     常量28：长2
 * 7063     常量28：‘pc’
 * 01       常量29：同11
 * 0004     常量29：长4
 * 7365 7458        常量29：‘setX’
 * 01       常量30：同11
 * 0004     常量30：长4
 * 2849 2956        常量30：‘(I)V’
 * 01       常量31：同11
 * 0008     常量31：长8
 * 3c63 6c69 6e69 743e      常量31：‘<clinit>’
 * 01       常量32：同11
 * 000a     常量32：长10
 * 536f 7572 6365 4669 6c65     常量32：‘SourceFile’
 * 01       常量33：同11
 * 0011     常量33：长17
 * 5072 6163 7469 6365 4361 7365 2e6a 6176 61       常量33：‘PracticeCase.java’
 * 0c       常量34：字段或方法的部分符号引用
 * 0011     常量34：字段或方法名称常量项索引，第17个常量
 * 0012     常量34：字段或方法描述符常量项索引，第18个常量
 * 01       常量35：同11
 * 0007     常量35：长7
 * 7765 6c63 6f6d 65        常量35：‘welcome’
 * 0c       常量36：同34
 * 000b     常量36：字段或方法名称为常量11
 * 000c     常量36：字段或方法描述符为常量12
 * 0c       常量37：同34
 * 000d     常量37：字段或方法名称为常量13
 * 000e     常量37：字段或方法描述符为常量14
 * 01       常量38：同11
 * 0016     常量38：长22
 * 636c 6173 7343 6f64 652f 5072 6163 7469 6365 4361 7365       常量38：‘classCode/PracticeCase’
 * 0c       常量39：同34
 * 001d     常量39：字段或方法名称为常量29
 * 001e     常量39：字段或方法描述符为常量30
 * 07       常量40：同5
 * 002c     常量40：全限定名为常量44
 * 0c       常量41：同34
 * 002d     常量41：字段或方法名称为常量45
 * 002e     常量41：字段或方法描述符为常量46
 * 0c       常量42：同34
 * 000f     常量42：字段或方法名称为常量15
 * 0010     常量42：字段或方法描述符为常量16
 * 01       常量43：同11
 * 0010     常量43：长16
 * 6a61 7661 2f6c 616e 672f 4f62 6a65 6374      常量43：‘java/lang/Object’
 * 01       常量44：同11
 * 0011     常量44：
 * 6a61 7661 2f6c 616e 672f 496e 7465 6765 72       常量44：‘java/lang/Integer’
 * 01       常量45：同11
 * 0007     常量45：长7
 * 7661 6c75 654f 66        常量45：‘valueOf’
 * 01       常量46：同11
 * 0016     常量46：长22
 * 2849 294c 6a61 7661 2f6c 616e 672f 496e 7465 6765 723b       常量46：‘(I)Ljava/lang/Integer;’
 * 0021     public的类，能调用invokespecial
 * 0005     当前类在常量池中索引
 * 000a     父类在常量池中索引
 * 0000     实现接口个数（不为0时后跟接口表）
 * 0003     【变量表】数量
 * 0000     变量1：默认的访问修饰符
 * 000b     变量1：变量名索引（str）
 * 000c     变量1：变量描述索引
 * 0000     变量1：【变量属性表】长度
 * 0002     变量2：private修饰
 * 000d     变量2：变量（x）
 * 000e     变量2：描述（I）
 * 0000     变量2：没有属性
 * 0009     变量3：public static修饰
 * 000f     变量3：变量（y）
 * 0010     变量3：描述（Ljava/lang/Integer;）
 * 0000     变量3：没有属性
 * 0004     【方法表】数量
 * 0001     方法1：public修饰符
 * 0011     方法1：方法名索引（<init>）
 * 0012     方法1：方法描述索引（()V）
 * 0001     方法1：【方法属性表】长度
 * 0013     方法1属性1：Code属性
 * 0000 0042        【Code属性表】长度为66
 * 0002     max_stack
 * 0001     max_local
 * 0000 0010        code_length为16
 * 2a       aload_0（将局部变量表中第一个引用类型本地变量推送至栈顶）
 * b7 0001      invokespecial（调用超类java/lang/Object的构造方法）
 * 2a       aload_0
 * 12 02        ldc（将int、float、String类型常量值从常量池推送至栈顶，“welcome”）
 * b5 0003      putfield（为指定的类的实例域赋值，给str赋值“welcome”）
 * 2a       aload_0
 * 08       iconst_5（将int类型5推送至栈顶）
 * b5 0004      putfield（给x赋值5）
 * b1       return（从当前方法返回void）
 * 0000     Code【异常表】数量为0
 * 0002     Code【属性表】数量为2
 * 0014     对应【行号表】
 * 0000 000e        【行号表】长度
 * 0003     3对映射关系
 * 0000 0007        偏移量0对应第7行代码
 * 0004 0008        偏移量4对应第8行代码
 * 000a 0009        偏移量10对应第9行代码
 * 0015     对应【局部变量表】
 * 0000 000c        【局部变量表】长度为12
 * 0001     1个局部变量
 * 0000     起始位置
 * 0010     偏移量长度
 * 0016     变量名索引（this）
 * 0017     变量描述索引（LclassCode/PracticeCase;）
 * 0000     占位符（变量为double和long类型时占位4个字节）
 * 0009     方法2：public static修饰符
 * 0018     方法2：方法名（main）
 * 0019     方法2：方法描述符（([Ljava/lang/String;)V）
 * 0001     方法2：【方法属性表】长度
 * 0013     方法2属性1：Code
 * 0000 0057        【Code表】长度为87
 * 0002     max_stack
 * 0002     max_local
 * 0000 0017        code_length为23
 * bb 0005      new（创建对象classCode/PracticeCase，将其压入栈顶）
 * 59       dup（复制栈顶数据并将其压入栈顶）
 * b7 0006      invokespecial（实例化classCode/PracticeCase初始化方法）
 * 4c       astore_1（将栈顶引用型数值存入第二个本地变量）
 * 2b       aload_1（将第二个引用型本地变量推送至栈顶）
 * 10 08        bipush（将单字节常量值推送至栈顶）
 * b6 0007      invokevirtual（调用实例方法setX）
 * 10 14        bipush（将20压入栈顶）
 * b8 0008      invokestatic（调用静态方法valueOf）
 * b3 0009      putstatic（为指定类的静态变量y赋值）
 * b1       return（返回void）
 * 0000     Code【异常表】数量为0
 * 0002     Code【属性表】数量为2
 * 0014     对应【行号表】
 * 0000 0012        【行号表】长度为18
 * 0004     4对映射关系
 * 0000 000d        偏移量0（0xbb），第13行代码
 * 0008 000e        偏移量8（0x4c），第14行代码
 * 000e 000f        偏移量14（第二个0x10），第15行代码
 * 0016 0010        偏移量22（0xb1），第16行代码
 * 0015     对应【局部变量表】
 * 0000 0016        【局部变量表】长度为22
 * 0002     2个局部变量
 * 0000     局部变量1：初始位置
 * 0017     局部变量1：长度
 * 001a     局部变量1：变量名索引（args）
 * 001b     局部变量1：变量描述索引（[Ljava/lang/String;）
 * 0000     局部变量1：index
 * 0008     局部变量2：初始位置
 * 000f     局部变量2：长度
 * 001c     局部变量2：变量名索引（pc）
 * 0017     局部变量2：变量描述索引（LclassCode/PracticeCase;）
 * 0001     局部变量2：index
 * 0001     方法3：public修饰
 * 001d     方法3：setX
 * 001e     方法3：(I)V
 * 0001     一个属性
 * 0013     Code
 * 0000 003e        属性长度
 * 0002     max_stack
 * 0002     max_local
 * 0000 0006        code_length
 * 2a       aload_0（将第1个引用型本地变量推送到栈顶）
 * 1b       iload_1（将第二个int型本地变量推送到栈顶）
 * b5 0004      putfield（给x赋值）
 * b1       return
 * 0000     无异常
 * 0002     2个Code属性
 * 0014     【行号表】
 * 0000 000a
 * 0002     2对映射
 * 0000 0013        偏移量0，13行
 * 0005 0014        偏移量5，14行
 * 0015     【局部变量表】
 * 0000 0016
 * 0002
 * 0000     从0
 * 0006     到6
 * 0016     this
 * 0017     LclassCode/PracticeCase;
 * 0000     index=0
 * 0000     从0
 * 0006     到6
 * 000d     x
 * 000e     I
 * 0001     index=1
 * 0008     方法4：静态代码块
 * 001f
 * 0012
 * 0001
 * 0013
 * 0000 0021        【Code表】长度
 * 0001     max_stack
 * 0000     max_local
 * 0000 0009        code_length
 * 10 0a        bipush 10
 * b8 0008      invokestatic（调用静态方法valueOf）
 * b3 0009      putstatic（给y赋值）
 * b1       return
 * 0000     无异常
 * 0001     一个方法属性
 * 0014     只有行号表
 * 0000 0006
 * 0001     一对映射
 * 0000 000a    偏移量0，10行代码
 * 0001     一个类属性【SourceFile表】
 * 0020     SourceFile
 * 0000 0002        长度
 * 0021     PracticeCase.java
 */
