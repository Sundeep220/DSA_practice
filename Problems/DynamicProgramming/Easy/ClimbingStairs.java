package Problems.DynamicProgramming.Easy;

import java.util.Arrays;

public class ClimbingStairs {

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all possibilities (1-step or 2-step)
    // Time: O(2^n)
    // Space: O(n) -> recursion stack
    // ---------------------------------------------------
    public static int climbStairsI(int n) {
        if (n == 0) return 1; // one valid way
        if (n < 0) return 0;  // invalid path

        return climbStairsI(n - 1) + climbStairsI(n - 2);
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Store results of subproblems to avoid recomputation
    // Time: O(n)
    // Space: O(n) -> dp array + recursion stack
    // ---------------------------------------------------
    public static int climbStairsII(int n, int[] dp) {
        if (n == 0) return 1;
        if (n < 0) return 0;

        if (dp[n] != -1) return dp[n];

        dp[n] = climbStairsII(n - 1, dp) + climbStairsII(n - 2, dp);
        return dp[n];
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build solution iteratively from base cases
    // Time: O(n)
    // Space: O(n) -> dp array only
    // ---------------------------------------------------
    public static int climbStairsIII(int n) {
        if (n == 0 || n == 1) return 1;

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Only last two states are needed
    // Time: O(n)
    // Space: O(1)
    // ---------------------------------------------------
    public static int climbStairsIV(int n) {
        if (n == 0 || n == 1) return 1;

        int prev2 = 1; // dp[0]
        int prev1 = 1; // dp[1]

        for (int i = 2; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    // ---------------------------------------------------
    // Most Optimal for Very Large n (n up to 1e9)
    // Uses Matrix Exponentiation
    // Time: O(log n)
    // Space: O(1)
    // ---------------------------------------------------
    public static long climbStairsV(long n) {
        if (n == 0 || n == 1) return 1;

        long[][] baseMatrix = {
                {1, 1},
                {1, 0}
        };

        long[][] result = matrixPower(baseMatrix, n);

        // Since ways(n) = F(n+1)
        return result[0][0];
    }

    // Fast exponentiation of matrix
    private static long[][] matrixPower(long[][] matrix, long power) {
        long[][] result = {
                {1, 0},
                {0, 1} // Identity matrix
        };

        while (power > 0) {
            if ((power & 1) == 1) {
                result = multiply(result, matrix);
            }
            matrix = multiply(matrix, matrix);
            power >>= 1;
        }

        return result;
    }

    // Multiply two 2x2 matrices using standard matrix multiplication rule
    private static long[][] multiply(long[][] A, long[][] B) {
        long[][] result = new long[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }


    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int n = 5;

        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        System.out.println("Brute Force Recursion: " + climbStairsI(n));
        System.out.println("Memoization: " + climbStairsII(n, dp));
        System.out.println("Tabulation: " + climbStairsIII(n));
        System.out.println("Space Optimized DP: " + climbStairsIV(n));
        long N = 1_000_000_000L;
        System.out.println("Ways to climb stairs: " + climbStairsV(N));
    }
}
