package question1;

import java.util.Arrays;

public class Question1 {

    // the following program implements basic sorting of integer
    // array. Given an array [0, 5, 3, 1], sorting it in ascending order
    // should yield a resulting array [0, 1, 3, 5].
    //
    // sortArray creates a copy of the array and sorts it
    // in ascending order using a bubble sort algorithm.
    // this algorithm involves iterating on the array multiple times.
    // for each iteration we swap neighboring values i and i+1 if
    // i > i+1. This basically slowly swaps bigger values in the array
    // with smaller values (they swap positions). After a few iterations
    // all the elements would have been moved to the right position.
    //
    // the main uses this method with an example array.
    // But there are problems in the code.

    public static void main(String[] args) {
        int[] arr = {0, 1, 5, 4};
        int[] sorted = sortArray(arr);

        // expected: [0, 1, 4, 5]]
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
