package question7;

import java.util.ArrayList;

public class Question7 {

    public static void main(String[] args) {
        new ArrayList<>(0)

        int a = 8;
        int b = 6;

        int number = sumNumbers(a, b);
        System.out.println(number);
    }

    private static int sumNumbers(int a, int b) {
        return a + b;
    }
}
