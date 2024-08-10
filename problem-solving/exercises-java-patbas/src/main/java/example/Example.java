package example;

public class Example {

    public static void main(String[] args) {
        System.out.println(stringToInt("6"));
        System.out.println(stringToInt("10"));
        System.out.println(stringToInt("53"));
        System.out.println(stringToInt("4910"));
    }

    private static int stringToInt(String str) {
        int result = 0;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            int digit = charToDigit(ch);

            result *= 10;
            result += digit;
        }

        return result;
    }

    private static int charToDigit(char ch) {
        switch (ch) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                return -1;
        }
    }
}
