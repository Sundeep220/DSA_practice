package Problems.DynamicProgramming.TwoDArray;

import java.util.Arrays;

public class NinjaAndHisFriends {

    //Problem: https://www.naukri.com/code360/problems/ninja-and-his-friends_3125885
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all 9 movement combinations at each row
    // Time: O(9^R)
    // Space: O(R) recursion stack
    // ---------------------------------------------------
    public static int collectI(int row, int colA, int colB,
                               int[][] grid, int R, int C) {

        // Out of bounds
        if (colA < 0 || colA >= C || colB < 0 || colB >= C)
            return Integer.MIN_VALUE;

        // Base case: last row
        if (row == R - 1) {
            if (colA == colB) return grid[row][colA];
            return grid[row][colA] + grid[row][colB];
        }

        int max = Integer.MIN_VALUE;

        for (int dA = -1; dA <= 1; dA++) {
            for (int dB = -1; dB <= 1; dB++) {
                int next = collectI(row + 1,
                        colA + dA,
                        colB + dB,
                        grid, R, C);
                max = Math.max(max, next);
            }
        }

        int curr =
                (colA == colB)
                        ? grid[row][colA]
                        : grid[row][colA] + grid[row][colB];

        return curr + max;
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Time: O(R * C * C * 9)
    // Space: O(R * C * C)
    // ---------------------------------------------------
    public static int collectII(int row, int colA, int colB,
                                int[][] grid, int[][][] dp,
                                int R, int C) {

        if (colA < 0 || colA >= C || colB < 0 || colB >= C)
            return Integer.MIN_VALUE;

        if (row == R - 1) {
            if (colA == colB) return grid[row][colA];
            return grid[row][colA] + grid[row][colB];
        }

        if (dp[row][colA][colB] != -1)
            return dp[row][colA][colB];

        int max = Integer.MIN_VALUE;

        for (int dA = -1; dA <= 1; dA++) {
            for (int dB = -1; dB <= 1; dB++) {
                int next = collectII(row + 1,
                        colA + dA,
                        colB + dB,
                        grid, dp, R, C);
                max = Math.max(max, next);
            }
        }

        int curr =
                (colA == colB)
                        ? grid[row][colA]
                        : grid[row][colA] + grid[row][colB];

        dp[row][colA][colB] = curr + max;
        return dp[row][colA][colB];
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Time: O(R * C * C * 9)
    // Space: O(R * C * C)
    // ---------------------------------------------------
    public static int collectIII(int[][] grid) {
        int R = grid.length;
        int C = grid[0].length;

        int[][][] dp = new int[R][C][C];

        // Base case: last row
        for (int colA = 0; colA < C; colA++) {
            for (int colB = 0; colB < C; colB++) {
                if (colA == colB)
                    dp[R - 1][colA][colB] = grid[R - 1][colA];
                else
                    dp[R - 1][colA][colB] =
                            grid[R - 1][colA] + grid[R - 1][colB];
            }
        }

        for (int row = R - 2; row >= 0; row--) {
            for (int colA = 0; colA < C; colA++) {
                for (int colB = 0; colB < C; colB++) {

                    int max = Integer.MIN_VALUE;

                    for (int dA = -1; dA <= 1; dA++) {
                        for (int dB = -1; dB <= 1; dB++) {
                            int nA = colA + dA;
                            int nB = colB + dB;

                            if (nA >= 0 && nA < C &&
                                    nB >= 0 && nB < C) {
                                max = Math.max(
                                        max,
                                        dp[row + 1][nA][nB]
                                );
                            }
                        }
                    }

                    int curr =
                            (colA == colB)
                                    ? grid[row][colA]
                                    : grid[row][colA] + grid[row][colB];

                    dp[row][colA][colB] = curr + max;
                }
            }
        }

        return dp[0][0][C - 1];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Use only two 2D arrays
    // Time: O(R * C * C * 9)
    // Space: O(C * C)
    // ---------------------------------------------------
    public static int collectIV(int[][] grid) {
        int R = grid.length;
        int C = grid[0].length;

        int[][] prev = new int[C][C];
        int[][] curr = new int[C][C];

        // Base case: last row
        for (int colA = 0; colA < C; colA++) {
            for (int colB = 0; colB < C; colB++) {
                if (colA == colB)
                    prev[colA][colB] = grid[R - 1][colA];
                else
                    prev[colA][colB] = grid[R - 1][colA] + grid[R - 1][colB];
            }
        }

        // Bottom-up DP
        for (int row = R - 2; row >= 0; row--) {
            for (int colA = 0; colA < C; colA++) {
                for (int colB = 0; colB < C; colB++) {

                    int max = Integer.MIN_VALUE;

                    for (int dA = -1; dA <= 1; dA++) {
                        for (int dB = -1; dB <= 1; dB++) {
                            int nA = colA + dA;
                            int nB = colB + dB;

                            if (nA >= 0 && nA < C && nB >= 0 && nB < C) {
                                max = Math.max(max, prev[nA][nB]);
                            }
                        }
                    }

                    int value = (colA == colB)
                            ? grid[row][colA]
                            : grid[row][colA] + grid[row][colB];

                    curr[colA][colB] = value + max;
                }
            }

            // ? swap references instead of clone
            int[][] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[0][C - 1];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[][] grid = {
                {2, 3, 1, 2},
                {3, 4, 2, 2},
                {5, 6, 3, 5}
        };

        int R = grid.length;
        int C = grid[0].length;

        int[][][] dp = new int[R][C][C];
        for (int[][] mat : dp)
            for (int[] row : mat)
                Arrays.fill(row, -1);

        System.out.println("Brute Force: " +
                collectI(0, 0, C - 1, grid, R, C));

        System.out.println("Memoization: " +
                collectII(0, 0, C - 1, grid, dp, R, C));

        System.out.println("Tabulation: " +
                collectIII(grid));

        System.out.println("Space Optimized: " +
                collectIV(grid));
    }
}
