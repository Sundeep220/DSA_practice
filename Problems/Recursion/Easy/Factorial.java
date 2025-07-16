package Problems.Recursion.Easy;

public class Factorial {
    // Problem: https://leetcode.com/problems/factorial-of-a-number/

    public static int fact(int n) {
        if(n <= 1) return 1;

        return n * fact(n -  1);
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println(fact(n));
    }
}
