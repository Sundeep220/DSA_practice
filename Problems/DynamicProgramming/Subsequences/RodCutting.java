package Problems.DynamicProgramming.Subsequences;


import java.util.Arrays;

public class RodCutting {
    // Problem:  https://www.naukri.com/code360/problems/rod-cutting-problem_800284
    // Same as Unbounded Knapsack
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(N) recursion stack
    // ---------------------------------------------------
    public static int rodCutI(int index, int length, int[] price) {

        // Base case: only length 1 available
        if (index == 0) {
            return length * price[0];
        }

        // Not cut at this length
        int notCut = rodCutI(index - 1, length, price);

        // Cut rod of length (index + 1)
        int cut = 0;
        int rodLen = index + 1;
        if (rodLen <= length) {
            cut = price[index] +
                    rodCutI(index, length - rodLen, price);
        }

        return Math.max(cut, notCut);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(N * N)
    // Space: O(N * N)
    // ---------------------------------------------------
    public static int rodCutII(int index, int length,
                               int[] price, int[][] dp) {

        if (index == 0) {
            return length * price[0];
        }

        if (dp[index][length] != -1)
            return dp[index][length];

        int notCut = rodCutII(index - 1, length, price, dp);

        int cut = 0;
        int rodLen = index + 1;
        if (rodLen <= length) {
            cut = price[index] +
                    rodCutII(index, length - rodLen, price, dp);
        }

        dp[index][length] = Math.max(cut, notCut);
        return dp[index][length];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(N * N)
    // Space: O(N * N)
    // ---------------------------------------------------
    public static int rodCutIII(int[] price, int N) {
        int[][] dp = new int[N][N + 1];

        // Base case: only length 1
        for (int len = 0; len <= N; len++) {
            dp[0][len] = len * price[0];
        }

        for (int i = 1; i < N; i++) {
            int rodLen = i + 1;
            for (int len = 0; len <= N; len++) {

                int notCut = dp[i - 1][len];

                int cut = 0;
                if (rodLen <= len) {
                    cut = price[i] + dp[i][len - rodLen];
                }

                dp[i][len] = Math.max(cut, notCut);
            }
        }

        return dp[N - 1][N];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(N * N)
    // Space: O(N)
    // ---------------------------------------------------
    public static int rodCutIV(int[] price, int N) {
        int[] dp = new int[N + 1];

        // Base case
        for (int len = 0; len <= N; len++) {
            dp[len] = len * price[0];
        }

        for (int i = 1; i < N; i++) {
            int rodLen = i + 1;
            for (int len = 0; len <= N; len++) {

                int notCut = dp[len];

                int cut = 0;
                if (rodLen <= len) {
                    cut = price[i] + dp[len - rodLen];
                }

                dp[len] = Math.max(cut, notCut);
            }
        }

        return dp[N];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] price = {1, 5, 8, 9, 10, 17, 17, 20};
        int N = price.length;

        int[][] dp = new int[N][N + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Recursion: "
                + rodCutI(N - 1, N, price));

        System.out.println("Memoization: "
                + rodCutII(N - 1, N, price, dp));

        System.out.println("Tabulation: "
                + rodCutIII(price, N));

        System.out.println("Space Optimized: "
                + rodCutIV(price, N));
    }
}
