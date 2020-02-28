package classCode;

/**
 * 08
 * 现代JVM在执行Java代码的时候，通常都会将解释执行和编译执行二者结合起来进行：
 * 1 所谓【解释执行】就是通过解释器读取字节码，遇到相应的指令就去执行该指令；（读取字节码流）
 * 2 所谓【编译执行】就是通过即时编译器（Just in Time，JIT）将字节码装换成本地机器码来执行。（编译原理相关，词法语法分析）
 * 现代JVM会根据代码热点来生成相应的本地机器码
 * <p>
 * 【基于栈】的指令集：
 * 1 JVM采取该方式
 * 2 主要操作有入栈、出栈两种
 * 3 在内存中完成操作
 * 4 优点在于可移植性好
 * eg：2 - 1 操作的指令集
 * iconst_1    1入栈
 * iconst_2    2入栈
 * isub    1 2弹栈相减，结果入栈
 * istore_0    弹栈存储到第一个slot中
 * 【基于寄存器】的指令集：
 * 1 和硬件架构紧密相关
 * 2 在高速缓冲区中执行
 * 3 相比基于栈的指令集指令数要少
 * 4 优点在于速度快
 * eg：2 - 1 操作的指令集
 * mov 2   把2放入寄存器中
 * sub 1   寄存器中的2减1
 */
public class BaseStack {
    public int myCalculate() {
        /**
         * iconst_1 将数字1推送至操作数栈中（iconst_n支持-1到5），此时操作数栈深度为1
         * istore_1 将操作数栈顶的元素弹出，存储到局部变量表中的第2个索引，即索引值为1处（istore_n支持0到3），此时操作数栈深度为0
         * 其中istore_n要求操作数栈顶元素必须为int类型，且n必须是局部变量表的一个索引位置
         */
        int a = 1;
//        和int a = 1;类似
        int b = 2;
//        和int a = 1;类似
        int c = 3;
//        和int a = 1;类似，但是由于istore_n只支持0到3，故此处为istore 4
        int d = 4;
        /**
         * iload_1  将局部变量表中第2个索引处，即索引值为1的值推送到操作数栈顶（iload_n支持0到3），此时操作数栈深度为1
         * iload_2  ……，此时操作数栈深度为2
         * iadd     操作数栈弹出两个元素相加后结果入栈（要求操作数栈顶的两个元素为int类型），此时操作数栈深度为1
         * iload_3  …………，此时操作数栈深度为2
         * isub     操作数栈弹出两个元素相减后结果入栈，先弹减数再弹被减数（要求操作数栈顶的两个元素为int类型），此时操作数栈深度为1
         * iload 4  …………，此时操作数栈深度为2
         * imul     操作数栈弹出两个元素相乘后结果入栈（要求操作数栈顶的两个元素为int类型），此时操作数栈深度为1
         * istore 5 将操作数栈顶的元素弹出，存储到局部变量表中的第6个索引，即索引值为5处，此时操作数栈深度为0
         */
        int result = (a + b - c) * d;
        /**
         * iload 5  将局部变量表中第6个索引处，即索引值为5的值推送到操作数栈顶，此时操作数栈深度为1
         * ireturn  返回操作数栈顶部元素（元素必须是int类型），此时操作数栈深度为0
         */
        return result;
//        综上，操作数栈的最大深度为2，max_stack=2
    }
    /**
     * 反编译字节码：
     * public int myCalculate();
     *     descriptor: ()I
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=2, locals=6, args_size=1
     *          0: iconst_1
     *          1: istore_1
     *          2: iconst_2
     *          3: istore_2
     *          4: iconst_3
     *          5: istore_3
     *          6: iconst_4
     *          7: istore        4
     *          9: iload_1
     *         10: iload_2
     *         11: iadd
     *         12: iload_3
     *         13: isub
     *         14: iload         4
     *         16: imul
     *         17: istore        5
     *         19: iload         5
     *         21: ireturn
     *       LineNumberTable:
     *         line 31: 0
     *         line 32: 2
     *         line 33: 4
     *         line 34: 6
     *         line 35: 9
     *         line 36: 19
     *       LocalVariableTable:
     *         Start  Length  Slot  Name    Signature
     *         0      22     0      this    LclassCode/BaseStack;
     *         2      20     1      a       I
     *         4      18     2      b       I
     *         6      16     3      c       I
     *         9      13     4      d       I
     *         19     3      5      result  I
     */
}
