package Problems.DynamicProgramming.OneDArray;

import java.util.Arrays;

public class FibonacciNumbers {
    // Brute force: Using recursion
    // Time: O(2^n)
    // Space: O(n) Recursion stack
    public static int fib(int n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }

    // Better: Using Memoization
    // Time: O(n)
    // Space: O(n) Recursion Stack
    public static int fibII(int n, int[] dp) {
        if (n <= 1) return n;

        if (dp[n] != -1) return dp[n];

        dp[n] = fibII(n - 1, dp) + fibII(n - 2, dp);
        return dp[n];
    }

    // Better: Using Tabulation
    // Time: O(n)
    // Space: O(n) No Recursion stack
    public static int fibIII(int n) {
        if (n <= 1) return n;

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // Most Optimal: Doing Space Optimization
    // Time: O(n)
    // Space: O(1)
    public static int fibIV(int n) {
        if (n <= 1) return n;

        int prev2 = 0; // fib(0)
        int prev = 1;  // fib(1)

        for (int i = 2; i <= n; i++) {
            int cur = prev + prev2;
            prev2 = prev;
            prev = cur;
        }

        return prev;
    }


    public static void main(String[] args) {
        int n = 10;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        System.out.println("Recursion: "+ fib(n));
        System.out.println("Memoization: "+ fibII(n, dp));
        System.out.println("Tabulation: "+ fibIII(n));
        System.out.println("Tabulation: "+ fibIV(n));
    }
}
