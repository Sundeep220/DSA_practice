package Problems.DynamicProgramming.Partitions;

import java.util.Arrays;

public class MatrixChainMultiplication {

    // ---------------------------------------------------
    // Problem: https://www.naukri.com/code360/problems/matrix-chain-multiplication_975344
    // Matrix Chain Multiplication (MCM)
    //
    // Given an array arr[] of size n,
    // matrices are:
    // A1 = arr[0] x arr[1]
    // A2 = arr[1] x arr[2]
    // ...
    // A(n-1) = arr[n-2] x arr[n-1]
    //
    // Find the minimum number of scalar multiplications
    // needed to multiply the chain.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    //
    // Intuition:
    // We try every possible place to split the matrix chain.
    //
    // If we multiply matrices from i to j:
    // - Try all k such that i <= k < j
    // - Cost =
    //     cost(i..k) +
    //     cost(k+1..j) +
    //     cost of multiplying the two resulting matrices
    //
    // Multiplication cost:
    // arr[i-1] * arr[k] * arr[j]
    //
    // State:
    // f(i, j) → minimum cost to multiply matrices from i to j
    //
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int mcmI(int i, int j, int[] arr) {

        // Base case: single matrix
        if (i == j)
            return 0;

        int minCost = Integer.MAX_VALUE;

        for (int k = i; k < j; k++) {
            int cost = mcmI(i, k, arr) + mcmI(k + 1, j, arr) + arr[i - 1] * arr[k] * arr[j];

            minCost = Math.min(minCost, cost);
        }

        return minCost;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    //
    // Intuition:
    // The recursive solution recomputes the same
    // subproblems (i, j) many times.
    //
    // So we store results in dp[i][j].
    //
    // Time: O(n^3)
    // Space: O(n^2) + recursion stack
    // ---------------------------------------------------
    public static int mcmII(int i, int j,
                            int[] arr, int[][] dp) {

        if (i == j)
            return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        int minCost = Integer.MAX_VALUE;

        for (int k = i; k < j; k++) {

            int cost =
                    mcmII(i, k, arr, dp)
                            + mcmII(k + 1, j, arr, dp)
                            + arr[i - 1] * arr[k] * arr[j];

            minCost = Math.min(minCost, cost);
        }

        dp[i][j] = minCost;
        return minCost;
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    //
    // Intuition:
    // Instead of recursion, we build the solution
    // bottom-up by increasing chain length.
    //
    // dp[i][j] → minimum cost to multiply matrices i to j
    //
    // We start from chains of length 1 (cost = 0)
    // and increase length step by step.
    //
    // Time: O(n^3)
    // Space: O(n^2)
    // ---------------------------------------------------

   public static int mcmIII(int[] arr) {
       int n = arr.length;
       int[][] dp = new int[n][n];


       for (int i = n - 1; i >= 1; i--) {
           for (int j = i + 1; j < n ; j++) {
               dp[i][j] = Integer.MAX_VALUE;
               for (int k = i; k < j; k++) {
                   int cost = dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j];
                   dp[i][j] = Math.min(dp[i][j], cost);
               }
           }
       }

       return dp[1][n - 1];
   }


    public static int mcmIV(int[] arr) {

        int n = arr.length;
        int[][] dp = new int[n][n];

        // dp[i][i] = 0 → already zero by default

        // len = length of matrix chain
        for (int len = 2; len < n; len++) {

            for (int i = 1; i + len - 1 < n; i++) {

                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {

                    int cost =
                            dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j];

                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[1][n - 1];
    }

    // ---------------------------------------------------
    // Space Optimized
    //
    // Intuition:
    // True space optimization is NOT possible for MCM
    // because dp[i][j] depends on multiple smaller ranges.
    //
    // So O(n^2) space is optimal.
    // ---------------------------------------------------
    public static int mcmV(int[] arr) {
        return mcmIV(arr);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[] arr = {10, 20, 30, 40};

        // Recursion
        System.out.println("Recursion: "
                + mcmI(1, arr.length - 1, arr));

        // Memoization
        int[][] dp = new int[arr.length][arr.length];
        for (int[] row : dp)
            Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + mcmII(1, arr.length - 1, arr, dp));

        // Tabulation
        System.out.println("Tabulation: "
                + mcmIII(arr));

        // Space Optimized
        System.out.println("Space Optimized: "
                + mcmIV(arr));
    }
}
