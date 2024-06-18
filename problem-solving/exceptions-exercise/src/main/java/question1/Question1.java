package question1;

import java.util.Arrays;

public class Question1 {

    public static void main(String[] args) {
        int[] arr = {0, 1, 5, 4};
        int[] sorted = sortArray(arr);

        System.out.printf("For %s, sorted %s\n",
                Arrays.toString(arr),
                Arrays.toString(sorted));
    }

    public static int[] sortArray(int[] arr) {
        int[] arrWork = Arrays.copyOf(arr, arr.length);

        for(int i = 0; i < arrWork.length; i++) {
            for(int j = 0; j < arrWork.length; j++) {
                if (arrWork[j] > arrWork[j + 1]) {
                    int temp = arrWork[j];
                    arrWork[j] = arrWork[j + 1];
                    arrWork[j + 1] = temp;
                }
            }
        }

        return arrWork;
    }
}
