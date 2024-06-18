package question4;

@SuppressWarnings("ALL")
public class Question4 {

    public static void main(String[] args) {
        System.out.println(calculateFibonachi(5));
    }

    public static int calculateFibonachi(int n) {
        return calculateFibonachi(n - 1) + calculateFibonachi(n - 2);
    }
}
