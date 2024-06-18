package question3;

public class Singleton {

    private static Singleton singleton;

    public static Singleton getInstance() {
        return singleton;
    }

    public void doSomething() {
        System.out.println("yey");
    }
}
