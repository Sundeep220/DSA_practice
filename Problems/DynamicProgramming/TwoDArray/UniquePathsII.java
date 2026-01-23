package Problems.DynamicProgramming.TwoDArray;

import java.util.Arrays;

public class UniquePathsII {

    // Problem: https://leetcode.com/problems/unique-paths-ii/description/
    // LeetCode 63 - Unique Paths II
    // Grid contains obstacles (1 = obstacle, 0 = free cell)
    // Robot starts at (0,0) and moves only RIGHT or DOWN
    // Return number of unique paths avoiding obstacles

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all paths, stop when obstacle is found
    // Time: O(2^(m+n))
    // Space: O(m+n) -> recursion stack
    // ---------------------------------------------------
    public static int uniquePathsI(int i, int j, int[][] grid) {

        // Out of bounds or obstacle
        if (i < 0 || j < 0 || grid[i][j] == 1) return 0;

        // Reached start
        if (i == 0 && j == 0) return 1;

        int up = uniquePathsI(i - 1, j, grid);
        int left = uniquePathsI(i, j - 1, grid);

        return up + left;
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Avoid recomputation using dp array
    // Time: O(m * n)
    // Space: O(m * n) + recursion stack
    // ---------------------------------------------------
    public static int uniquePathsII(int i, int j,
                                    int[][] grid, int[][] dp) {

        if (i < 0 || j < 0 || grid[i][j] == 1) return 0;
        if (i == 0 && j == 0) return 1;

        if (dp[i][j] != -1) return dp[i][j];

        int up = uniquePathsII(i - 1, j, grid, dp);
        int left = uniquePathsII(i, j - 1, grid, dp);

        return dp[i][j] = up + left;
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build dp table iteratively
    // Time: O(m * n)
    // Space: O(m * n)
    // ---------------------------------------------------
    public static int uniquePathsIII(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];

        // If start is blocked, no paths exist
        if (grid[0][0] == 1) return 0;


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    dp[i][j] = 0;
                }
                else if (i == 0 && j == 0) dp[i][j] = 1;
                else {

                    int up = (i > 0) ? dp[i - 1][j] : 0;
                    int left = (j > 0) ? dp[i][j - 1] : 0;
                    dp[i][j] = up + left;
                }
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
    public static int uniquePathsIV(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int[] prev = new int[n];

        for (int i = 0; i < m; i++) {
            int[] curr = new int[n];

            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 1) {
                    curr[j] = 0;
                }
                else if (i == 0 && j == 0) {
                    curr[j] = 1;
                }
                else {
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
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[][] grid = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };

        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Brute Force Recursion: "
                + uniquePathsI(m - 1, n - 1, grid));

        System.out.println("Memoization: "
                + uniquePathsII(m - 1, n - 1, grid, dp));

        System.out.println("Tabulation: "
                + uniquePathsIII(grid));

        System.out.println("Space Optimized DP: "
                + uniquePathsIV(grid));
    }
}
