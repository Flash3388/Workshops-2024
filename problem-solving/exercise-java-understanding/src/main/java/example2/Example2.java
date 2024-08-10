package example2;

public class Example2 {

    public static void main(String[] args) {
        // expected: 13
        System.out.println(binaryToDecimal("1101"));
        // expected: 3
        System.out.println(binaryToDecimal("0011"));
        // expected: 202
        System.out.println(binaryToDecimal("11001010"));
    }

    private static int binaryToDecimal(String binary) {
        int result = 0;
        int numLength = binary.length();

        for (int i = numLength - 1; i >= 0; i--) {
            char ch = binary.charAt(i);
            if (ch == '1') {
                int position = numLength - i;
                result += Math.pow(2, position);
            }
        }

        return result;
    }
}
