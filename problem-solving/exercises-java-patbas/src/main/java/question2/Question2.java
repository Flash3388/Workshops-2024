package question2;

public class Question2 {

    public static void main(String[] args) {
        System.out.println(sumDigits(1554));
    }

    private static int sumDigits(int number) {
        int sum = 0;

        while (number != 0) {
            int digit = number % 10;
            number /= 10;

            sum += digit;
        }

        return sum;
    }
}
