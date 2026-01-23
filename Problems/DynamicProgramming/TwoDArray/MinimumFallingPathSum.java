package Problems.DynamicProgramming.TwoDArray;

import java.util.Arrays;

public class MinimumFallingPathSum {
    // Problem: https://leetcode.com/problems/minimum-falling-path-sum/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all falling paths starting from first row
    // Time: O(3^N)
    // Space: O(N) recursion stack
    // ---------------------------------------------------
    public static int minPathI(int row, int col, int[][] matrix) {
        int n = matrix.length;

        // Out of bounds
        if (col < 0 || col >= n) return Integer.MAX_VALUE;

        // Base case: first row
        if (row == 0) return matrix[0][col];

        int up = minPathI(row - 1, col, matrix);
        int leftDiag = minPathI(row - 1, col - 1, matrix);
        int rightDiag = minPathI(row - 1, col + 1, matrix);

        return matrix[row][col] + Math.min(up, Math.min(leftDiag, rightDiag));
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Cache overlapping subproblems
    // Time: O(N^2)
    // Space: O(N^2) dp + O(N) recursion stack
    // ---------------------------------------------------
    public static int minPathII(int row, int col, int[][] matrix, int[][] dp) {
        int n = matrix.length;

        if (col < 0 || col >= n) return Integer.MAX_VALUE;

        if (row == 0) return matrix[0][col];

        if (dp[row][col] != Integer.MAX_VALUE) return dp[row][col];

        int up = minPathII(row - 1, col, matrix, dp);
        int leftDiag = minPathII(row - 1, col - 1, matrix, dp);
        int rightDiag = minPathII(row - 1, col + 1, matrix, dp);

        dp[row][col] = matrix[row][col] + Math.min(up, Math.min(leftDiag, rightDiag));

        return dp[row][col];
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build dp row by row
    // Time: O(N^2)
    // Space: O(N^2)
    // ---------------------------------------------------
    public static int minPathIII(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];

        // Base case: first row
        for (int col = 0; col < n; col++) {
            dp[0][col] = matrix[0][col];
        }

        for (int row = 1; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int up = dp[row - 1][col];

                int leftDiag = col > 0 ? dp[row - 1][col - 1] : Integer.MAX_VALUE;

                int rightDiag = col < n - 1 ? dp[row - 1][col + 1] : Integer.MAX_VALUE;

                dp[row][col] = matrix[row][col] + Math.min(up, Math.min(leftDiag, rightDiag));
            }
        }

        // Find minimum in last row
        int ans = Integer.MAX_VALUE;
        for (int col = 0; col < n; col++) {
            ans = Math.min(ans, dp[n - 1][col]);
        }

        return ans;
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Use only previous row
    // Time: O(N^2)
    // Space: O(N)
    // ---------------------------------------------------
    public static int minPathIV(int[][] matrix) {
        int n = matrix.length;
        int[] prev = new int[n];

        // Initialize first row
        for (int col = 0; col < n; col++) {
            prev[col] = matrix[0][col];
        }

        for (int row = 1; row < n; row++) {
            int[] curr = new int[n];
            for (int col = 0; col < n; col++) {
                int up = prev[col];

                int leftDiag = col > 0 ? prev[col - 1] : Integer.MAX_VALUE;

                int rightDiag = col < n - 1 ? prev[col + 1] : Integer.MAX_VALUE;

                curr[col] = matrix[row][col] + Math.min(up, Math.min(leftDiag, rightDiag));
            }
            prev = curr;
        }

        int ans = Integer.MAX_VALUE;
        for (int val : prev) {
            ans = Math.min(ans, val);
        }

        return ans;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[][] matrix = {
                {2, 1, 3},
                {6, 5, 4},
                {7, 8, 9}
        };

        int n = matrix.length;
        int[][] dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);

        int bruteAns = Integer.MAX_VALUE;
        int memoAns = Integer.MAX_VALUE;

        for (int col = 0; col < n; col++) {
            bruteAns = Math.min(bruteAns, minPathI(n - 1, col, matrix));
            memoAns = Math.min(memoAns, minPathII(n - 1, col, matrix, dp));
        }

        System.out.println("Brute Force Recursion: " + bruteAns);
        System.out.println("Memoization: " + memoAns);
        System.out.println("Tabulation: " + minPathIII(matrix));
        System.out.println("Space Optimized DP: " + minPathIV(matrix));
    }
}
