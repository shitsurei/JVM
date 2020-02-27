package classCode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

/**
 * 04
 * 在Java当中，每一个【实例方法】的局部变量表中至少都存在一个指向当前对象的this，一般this位于方法的第一个参数位置
 * 因此我们可以在实例方法中通过this关键字访问当前对象的属性以及其他方法
 */
public class ExceptionCase {
    public void test1() throws Exception, FileNotFoundException, IOException {
        try {
            InputStream is = new FileInputStream("test.txt");
            ServerSocket serverSocket = new ServerSocket(9999);
            serverSocket.accept();
//            抛出异常时指向goto指令码，直接跳转到异常处理位置
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        } finally {
            System.out.println("finally");
        }
    }
//    Java字节码对于处理的处理：
//    1 统一采用异常表的方式对异常进行处理
//    2 在JDK 1.4.2之前的版本中并非如此，采用的是特定的指令方式（jsr ret）
//    3 当异常处理存在finally块时，JVM处理方式是将该块相关的字节码【拼接】到每一个catch块后面，后面再跟goto指令跳转到异常捕获块之后的指令
//    4 对Java程序来说Exception类可以捕获程序运行期和非运行期的所有异常，但字节码会默认追加any异常，用于捕获未指定的异常种类
//    5 在方法声明中抛出的异常体现在字节码中，该方法会有一个和Code同级的属性Exceptions，该异常表中记录了方法抛出的异常
    /**
     * public void test1();
     *         descriptor: ()V
     *         flags: ACC_PUBLIC
     *         Code:
     *         stack=3, locals=4（最大局部变量：this、is、serverSocket、e）, args_size=1（对于实例方法来说，隐含着this这个参数）
     *         0: new           #2                  // class java/io/FileInputStream
     *         3: dup
     *         4: ldc           #3                  // String test.txt
     *         6: invokespecial #4                  // Method java/io/FileInputStream."<init>":(Ljava/lang/String;)V
     *         9: astore_1
     *         10: new           #5                  // class java/net/ServerSocket
     *         13: dup
     *         14: sipush        9999
     *         17: invokespecial #6                  // Method java/net/ServerSocket."<init>":(I)V
     *         20: astore_2
     *         21: aload_2
     *         22: invokevirtual #7                  // Method java/net/ServerSocket.accept:()Ljava/net/Socket;
     *         25: pop
     *         26: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         29: ldc           #9                  // String finally
     *         31: invokevirtual #10                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *         34: goto          84
     *         37: astore_1
     *         38: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         41: ldc           #9                  // String finally
     *         43: invokevirtual #10                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *         46: goto          84
     *         49: astore_1
     *         50: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         53: ldc           #9                  // String finally
     *         55: invokevirtual #10                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *         58: goto          84
     *         61: astore_1
     *         62: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         65: ldc           #9                  // String finally
     *         67: invokevirtual #10                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *         70: goto          84
     *         73: astore_3
     *         74: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         77: ldc           #9                  // String finally
     *         79: invokevirtual #10                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *         82: aload_3
     *         83: athrow
     *         84: return
     *         Exception table（异常表，前两项start_pc和end_pc表示捕获异常的指令范围，第三项handle_pc表示捕获异常后转向执行的指令位置，第四项catch_type表示捕捉的异常类型）:
     *         from     to    target    type
     *         0        26    37        Class java/io/FileNotFoundException
     *         0        26    49        Class java/io/IOException
     *         0        26    61        Class java/lang/Exception
     *         0        26    73        any
     *         LineNumberTable:
     *         line 15: 0
     *         line 16: 10
     *         line 17: 21
     *         line 25: 26
     *         line 26: 34
     *         line 18: 37
     *         line 25: 38
     *         line 26: 46
     *         line 20: 49
     *         line 25: 50
     *         line 26: 58
     *         line 22: 61
     *         line 25: 62
     *         line 26: 70
     *         line 25: 73
     *         line 26: 82
     *         line 27: 84
     *         LocalVariableTable:
     *         Start  Length  Slot  Name            Signature
     *         10     16      1     is              Ljava/io/InputStream;
     *         21     5       2     serverSocket    Ljava/net/ServerSocket;
     *         0      85      0     this            LclassCode/ExceptionCase;
     */
}




