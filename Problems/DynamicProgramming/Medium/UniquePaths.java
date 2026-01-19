package Problems.DynamicProgramming.Medium;

import java.util.Arrays;

public class UniquePaths {

    // Problem: https://leetcode.com/problems/unique-paths/description/
    // LeetCode 62 - Unique Paths
    // A robot is placed at (0,0) in an m x n grid.
    // It can only move RIGHT or DOWN.
    // Find number of unique paths to reach (m-1, n-1).

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all possible paths
    // Time: O(2^(m+n))
    // Space: O(m+n) -> recursion stack
    // ---------------------------------------------------
    public static int uniquePathsI(int i, int j) {

        // Base case: reached start
        if (i == 0 && j == 0) return 1;

        // Out of bounds
        if (i < 0 || j < 0) return 0;

        int up = uniquePathsI(i - 1, j);
        int left = uniquePathsI(i, j - 1);

        return up + left;
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Avoid recomputation using dp array
    // Time: O(m * n)
    // Space: O(m * n) + recursion stack
    // ---------------------------------------------------
    public static int uniquePathsII(int i, int j, int[][] dp) {

        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        int up = uniquePathsII(i - 1, j, dp);
        int left = uniquePathsII(i, j - 1, dp);

        return dp[i][j] = up + left;
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build dp table iteratively
    // Time: O(m * n)
    // Space: O(m * n)
    // ---------------------------------------------------
    public static int uniquePathsIII(int m, int n) {

        int[][] dp = new int[m][n];

        // Base case: starting cell
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) continue;

                int up = (i > 0) ? dp[i - 1][j] : 0;
                int left = (j > 0) ? dp[i][j - 1] : 0;

                dp[i][j] = up + left;
            }
        }

        return dp[m - 1][n - 1];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Only previous row is required
    // Time: O(m * n)
    // Space: O(n)
    // ---------------------------------------------------
    public static int uniquePathsIV(int m, int n) {

        int[] prev = new int[n];

        for (int i = 0; i < m; i++) {
            int[] curr = new int[n];
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    curr[j] = 1;
                } else {
                    int up = (i > 0) ? prev[j] : 0;
                    int left = (j > 0) ? curr[j - 1] : 0;
                    curr[j] = up + left;
                }
            }
            prev = curr;
        }

        return prev[n - 1];
    }

    // ---------------------------------------------------
    // Most Optimal: Mathematical (Combinations)
    // Using nCr = (n * (n-1) * ... ) / (r * (r-1) * ...)
    // We compute nCr using:
    // nCr = (N - R + 1)(N - R + 2)...N
    //       --------------------------
    //             1 * 2 * ... * R
    // Time: O(min(m, n))
    // Space: O(1)
    // ---------------------------------------------------
    public static int uniquePaths(int m, int n) {

        int N = m + n - 2;          // total moves
        int R = Math.min(m - 1, n - 1); // choose smaller for optimization

        long result = 1;

        for (int i = 1; i <= R; i++) {
            result = result * (N - R + i) / i;
        }

        return (int) result;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int m = 3;
        int n = 7;

        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Brute Force Recursion: "
                + uniquePathsI(m - 1, n - 1));

        System.out.println("Memoization: "
                + uniquePathsII(m - 1, n - 1, dp));

        System.out.println("Tabulation: "
                + uniquePathsIII(m, n));

        System.out.println("Space Optimized DP: "
                + uniquePathsIV(m, n));

        System.out.println("Permutation and Combination (Most Optimal) = " + uniquePaths(m, n));
    }
}

