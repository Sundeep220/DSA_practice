package Problems.DynamicProgramming.TwoDArray;

import java.util.Arrays;
import java.util.List;

public class TriangleMinimumPathSum {
    // Problem: https://leetcode.com/problems/triangle/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all paths from top to bottom
    // Time: O(2^N)
    // Space: O(N) recursion stack
    // ---------------------------------------------------
    public static int minPathSumI(int row, int col, List<List<Integer>> triangle) {
        int n = triangle.size();

        // Base case: last row
        if (row == n - 1) {
            return triangle.get(row).get(col);
        }

        int down = minPathSumI(row + 1, col, triangle);
        int diagonal = minPathSumI(row + 1, col + 1, triangle);

        return triangle.get(row).get(col) + Math.min(down, diagonal);
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Cache results of subproblems
    // Time: O(N^2)
    // Space: O(N^2) dp + O(N) recursion stack
    // ---------------------------------------------------
    public static int minPathSumII(int row, int col,
                                   List<List<Integer>> triangle,
                                   int[][] dp) {
        int n = triangle.size();

        if (row == n - 1) {
            return triangle.get(row).get(col);
        }

        if (dp[row][col] != -1) return dp[row][col];

        int down = minPathSumII(row + 1, col, triangle, dp);
        int diagonal = minPathSumII(row + 1, col + 1, triangle, dp);

        dp[row][col] =
                triangle.get(row).get(col) + Math.min(down, diagonal);

        return dp[row][col];
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build solution from last row to top
    // Time: O(N^2)
    // Space: O(N^2)
    // ---------------------------------------------------
    public static int minPathSumIII(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];

        // Base case: last row
        for (int j = 0; j < triangle.get(n - 1).size(); j++) {
            dp[n - 1][j] = triangle.get(n - 1).get(j);
        }

        // Build from bottom to top
        for (int row = n - 2; row >= 0; row--) {
            for (int col = 0; col <= row; col++) {
                dp[row][col] =
                        triangle.get(row).get(col)
                                + Math.min(dp[row + 1][col],
                                dp[row + 1][col + 1]);
            }
        }

        return dp[0][0];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Use only one 1D array
    // Time: O(N^2)
    // Space: O(N)
    // ---------------------------------------------------
    public static int minPathSumIV(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n];

        // Initialize with last row
        for (int i = 0; i < triangle.get(n - 1).size(); i++) {
            dp[i] = triangle.get(n - 1).get(i);
        }

        // Build upwards
        for (int row = n - 2; row >= 0; row--) {
            for (int col = 0; col <= row; col++) {
                dp[col] =
                        triangle.get(row).get(col)
                                + Math.min(dp[col], dp[col + 1]);
            }
        }

        return dp[0];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        List<List<Integer>> triangle = List.of(
                List.of(2),
                List.of(3, 4),
                List.of(6, 5, 7),
                List.of(4, 1, 8, 3)
        );

        int n = triangle.size();
        int[][] dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Brute Force Recursion: "
                + minPathSumI(0, 0, triangle));

        System.out.println("Memoization: "
                + minPathSumII(0, 0, triangle, dp));

        System.out.println("Tabulation: "
                + minPathSumIII(triangle));

        System.out.println("Space Optimized DP: "
                + minPathSumIV(triangle));
    }
}
