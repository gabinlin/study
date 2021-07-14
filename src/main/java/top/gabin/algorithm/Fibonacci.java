package top.gabin.algorithm;

public class Fibonacci {

    public static int fibonacci(int n) {
        int one = 0;
        int two = 1;
        for (int i = 0; i < n; i++) {
            int total = one + two;
            one = two;
            two = total;
        }
        return one;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(fibonacci(i));
        }
    }

}
