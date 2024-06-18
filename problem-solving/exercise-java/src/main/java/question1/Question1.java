package question1;

import java.util.Arrays;

public class Question1 {

    public static void main(String[] args) {
        int[] arr1 = {0, 1, 5, 4};
        int[] arr2 = {5, 4, 10};
        int[] arr3 = {4, -5, 3};

        System.out.printf("For %s, min=%d\n",
                Arrays.toString(arr1),
                findMinValueIndex(arr1));

        System.out.printf("For %s, min=%d\n",
                Arrays.toString(arr2),
                findMinValueIndex(arr2));


        System.out.printf("For %s, min=%d\n",
                Arrays.toString(arr3),
                findMinValueIndex(arr3));
    }

    public static int findMinValueIndex(int[] arr) {
        int min = 0;
        int minLocation = 0;

        for(int i = 1; i <= arr.length; i++) {
            if(arr[i] < min) {
                min = arr[i];
            }

            minLocation = i;
        }

        return minLocation;
    }
}
