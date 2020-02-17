package classLoader.samples;

public class MyDog {
    public MyDog() {
        System.out.println("MyDog is loaded by " + this.getClass().getClassLoader());
        new MyCat();
        System.out.println(MyCat.class);
    }
}
