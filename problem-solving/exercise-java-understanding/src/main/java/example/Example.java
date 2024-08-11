package example;

public class Example {

    // this program implements calculation of the sum of all digits in a number.
    // for the number: 1123, the sum would be 1 + 1 + 2 + 3 = 7
    // the main runs an example

    public static void main(String[] args) {
        // expected: 15
        System.out.println(sumDigits(1554));
        System.out.println("hello");
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
