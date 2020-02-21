package classLoader.samples;

public class MyThread implements Runnable {
    private Thread thread;
    public MyThread(){
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        ClassLoader classLoader = this.thread.getContextClassLoader();
//        class sun.misc.Launcher$AppClassLoader
        System.out.println(classLoader.getClass());
//        sun.misc.Launcher$ExtClassLoader@2a0170e1
        System.out.println(classLoader.getParent());
    }
}
