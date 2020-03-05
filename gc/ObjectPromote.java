package gc;

/**
 * 04
 */
public class ObjectPromote {
    public static void main(String[] args) throws InterruptedException {
        byte[] bytes1 = new byte[512 * 1024];
        byte[] bytes2 = new byte[512 * 1024];
        myGC();
        Thread.sleep(1000);
        System.out.println("-------1--------");
        myGC();
        Thread.sleep(1000);
        System.out.println("-------2--------");
        myGC();
        Thread.sleep(1000);
        System.out.println("-------3--------");
        myGC();
        Thread.sleep(1000);
        System.out.println("-------4--------");
    }

    /**
     * 每次执行该方法都会生成40个1m大小的字节数组对象，方法结束后这些数组称为垃圾待回收
     */
    private static void myGC() {
        for (int i = 0; i < 40; i++) {
            byte[] bytes = new byte[1024 * 1024];
        }
    }
}
