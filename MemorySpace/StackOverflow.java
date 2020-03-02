package MemorySpace;

/**
 * 02
 * 演示虚拟机栈溢出
 * -Xss100k 设置栈大小
 */
public class StackOverflow {
    private int length;

    public int getLength() {
        return length;
    }

    public void test() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.length++;
        test();
    }

    public static void main(String[] args) {
        StackOverflow stackOverflow = new StackOverflow();
        try {
            stackOverflow.test();
        } catch (Throwable error) {
//            java.lang.StackOverflowError
            error.printStackTrace();
            //23967
            //20600
            //29055
//            980
            System.out.println(stackOverflow.getLength());
        }
    }
}
