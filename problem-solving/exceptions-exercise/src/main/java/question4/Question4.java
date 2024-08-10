package question4;

@SuppressWarnings("ALL")
public class Question4 {

    // this program implements calculation for the fibonachi sequence.
    // the fibonachi is specific series of number build upon the determination
    // the any number is the sum of the previous two numbers.
    //
    // so, for the nth number in the series can be computed as
    // Nn = Nn-1 + Nn-2
    // and Nn-1 = Nn-2 + Nn-3
    // and so on.
    // the first two numbers of the series (required to be defined since nothing can define
    // them since they are the first two) are 0 and 1.
    // so, N3 = 1 + 0 = 1
    // and N4 = 1 + 1 = 2
    //
    // To implement this, the program uses recursion: the process of
    // a method calling itself with a different parameter in order
    // to perform something. This makes sense because to calculate
    // nummber n, we need to calculate numbers n-1 and n-2.
    //
    // main attempts to use the method to get N5. but there are
    // problems.

    public static void main(String[] args) {
        System.out.println(calculateFibonachi(5));
    }

    public static int calculateFibonachi(int n) {
        return calculateFibonachi(n - 1) + calculateFibonachi(n - 2);
    }
}
