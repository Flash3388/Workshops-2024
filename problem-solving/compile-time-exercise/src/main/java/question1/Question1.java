package question1;

public class Question1 {

    public static void main(String[] args) {
        int[] arr = {2, -3, 1};

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                if (arr[i] % 2 == 0) {
                    System.out.println(arr[i]);
                }
            }
        }
    }
}
