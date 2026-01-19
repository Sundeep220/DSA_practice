package Problems.DynamicProgramming.Medium;

import java.util.Arrays;

public class MinimumPathSum {

    // Problem:
    // LeetCode 64 - Minimum Path Sum
    // Given an m x n grid with non-negative values.
    // Start at (0,0), end at (m-1,n-1).
    // You can only move RIGHT or DOWN.
    // Find the minimum sum path.

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all possible paths
    // Time: O(2^(m+n))
    // Space: O(m+n) -> recursion stack
    // ---------------------------------------------------
    public static int minPathSumI(int i, int j, int[][] grid) {

        // Out of bounds
        if (i < 0 || j < 0) return Integer.MAX_VALUE;

        // Base case: start cell
        if (i == 0 && j == 0) return grid[0][0];

        int up = minPathSumI(i - 1, j, grid);
        int left = minPathSumI(i, j - 1, grid);

        return grid[i][j] + Math.min(up, left);
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Avoid recomputation using dp array
    // Time: O(m * n)
    // Space: O(m * n) + recursion stack
    // ---------------------------------------------------
    public static int minPathSumII(int i, int j,
                                   int[][] grid, int[][] dp) {

        if (i < 0 || j < 0) return Integer.MAX_VALUE;

        if (i == 0 && j == 0) return grid[0][0];

        if (dp[i][j] != -1) return dp[i][j];

        int up = minPathSumII(i - 1, j, grid, dp);
        int left = minPathSumII(i, j - 1, grid, dp);

        return dp[i][j] = grid[i][j] + Math.min(up, left);
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build dp table iteratively
    // Time: O(m * n)
    // Space: O(m * n)
    // ---------------------------------------------------
    public static int minPathSumIII(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        // Create DP table
        int[][] dp = new int[n][m];

        // Fill the table
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // First cell initialization
                if (i == 0 && j == 0)
                    dp[i][j] = grid[i][j];
                else {
                    // Calculate from top
                    int up = grid[i][j];
                    if (i > 0) up += dp[i - 1][j];
                    else up += (int)1e9;

                    // Calculate from left
                    int left = grid[i][j];
                    if (j > 0) left += dp[i][j - 1];
                    else left += (int)1e9;

                    // Take minimum
                    dp[i][j] = Math.min(up, left);
                }
            }
        }

        // Return result
        return dp[n - 1][m - 1];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Only previous row is required
    // Time: O(m * n)
    // Space: O(n)
    // ---------------------------------------------------
    public static int minPathSumIV(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        // Create 1D array for previous row
        int[] prev = new int[m];

        // Loop through each row
        for (int i = 0; i < n; i++) {

            // Create temp array for current row
            int[] temp = new int[m];

            // Loop through each column
            for (int j = 0; j < m; j++) {

                // If at the start cell
                if (i == 0 && j == 0) {
                    temp[j] = grid[i][j];
                } else {

                    // Take up direction if valid
                    int up = grid[i][j];
                    if (i > 0)
                        up += prev[j];
                    else
                        up += (int)1e9;

                    // Take left direction if valid
                    int left = grid[i][j];
                    if (j > 0)
                        left += temp[j - 1];
                    else
                        left += (int)1e9;

                    // Take minimum of both directions
                    temp[j] = Math.min(up, left);
                }
            }

            // Move current row to previous
            prev = temp;
        }

        // Return result at destination
        return prev[m - 1];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Brute Force Recursion: "
                + minPathSumI(m - 1, n - 1, grid));

        System.out.println("Memoization: "
                + minPathSumII(m - 1, n - 1, grid, dp));

        System.out.println("Tabulation: "
                + minPathSumIII(grid));

        System.out.println("Space Optimized DP: "
                + minPathSumIV(grid));
    }
}
