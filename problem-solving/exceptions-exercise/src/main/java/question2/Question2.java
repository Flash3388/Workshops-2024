package question2;

import java.util.Arrays;

public class Question2 {

    // the following program calculates the sum of all the values
    // in an integer array. It is implemented in arraySum.
    // main calls this method several times with examples.
    //
    // run this program and see if the outputs are as expected. Spoiler: they aren't.
    // this is because there are problems in the code.

    public static void main(String[] args) {
        int[] arr = {};
        int[] arr2 = {0, 4, 1};

        // expected: 0
        System.out.printf("For %s, sum=%d\n",
                Arrays.toString(arr),
                arraySum(arr));

        // expected: 5
        System.out.printf("For %s, sum=%d\n",
                Arrays.toString(arr2),
                arraySum(arr2));
    }

    public static int arraySum(int[] arr) {
        int sum = 0;
        for(int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        return sum / arr.length;
    }
}
