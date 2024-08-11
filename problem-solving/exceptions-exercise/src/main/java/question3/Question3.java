package question3;

public class Question3 {

    // this program demonstrates the concept of a singleton.
    // a singleton is a class with only one instance (single instance)
    // which is stored in a globally available variable (static variable).
    // such classes are used to provide a globally used services so that
    // any class may use it. this is actually not that good thing to use,
    // it introduces a set of problems, but that is not the point of the execise.
    //
    // the main retrieve the singleton instance and uses it. But there are
    // problems...

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.doSomething();
    }
}
