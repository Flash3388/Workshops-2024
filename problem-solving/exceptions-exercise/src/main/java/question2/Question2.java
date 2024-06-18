package question2;

import java.util.Arrays;

public class Question2 {

    public static void main(String[] args) {
        int[] arr = {};

        System.out.printf("For %s, sum=%d\n",
                Arrays.toString(arr),
                arraySum(arr));
    }

    public static int arraySum(int[] arr) {
        int sum = 0;
        for(int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        return sum / arr.length;
    }
}
