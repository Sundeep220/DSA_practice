package Problems.DynamicProgramming.Squares;

public class CountSquareSubmatricesWithAllOnes {

    // ---------------------------------------------------
    // Problem: https://leetcode.com/problems/count-square-submatrices-with-all-ones/description/
    // 1277. Count Square Submatrices with All Ones
    //
    // Given a binary matrix, count how many square
    // submatrices contain only 1s.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Brute Force
    //
    // Intuition:
    // For every cell (i, j), treat it as the top-left
    // corner of a square.
    //
    // Try to form squares of size:
    // 1, 2, 3, ...
    // and check if all cells inside the square are 1.
    //
    // Stop expanding when:
    // - square goes out of bounds, or
    // - any cell inside is 0
    //
    // Time: O(rows * cols * min(rows, cols)^2)
    // Space: O(1)
    // ---------------------------------------------------
    public static int countSquaresBruteForce(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                // try all square sizes starting from (i, j)
                for (int size = 1;
                     i + size - 1 < rows && j + size - 1 < cols;
                     size++) {

                    if (isAllOnes(matrix, i, j, size)) {
                        count++;
                    } else {
                        break; // further sizes will also fail
                    }
                }
            }
        }

        return count;
    }

    // helper for brute force
    private static boolean isAllOnes(int[][] matrix,
                                     int row, int col, int size) {

        for (int i = row; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                if (matrix[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------
    // DP Intuition (MOST IMPORTANT)
    //
    // dp[i][j] = size of the largest square of 1s
    //            with bottom-right corner at (i, j)
    //
    // If matrix[i][j] == 1:
    // dp[i][j] = 1 + min(
    //                  dp[i-1][j],      // top
    //                  dp[i][j-1],      // left
    //                  dp[i-1][j-1]     // top-left
    //                )
    //
    // Every dp[i][j] contributes dp[i][j] squares.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // DP Tabulation
    //
    // Time: O(rows * cols)
    // Space: O(rows * cols)
    // ---------------------------------------------------
    public static int countSquaresDP(int[][] matrix) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] dp = new int[rows][cols];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (matrix[i][j] == 1) {

                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 1 + Math.min(
                                dp[i - 1][j],
                                Math.min(dp[i][j - 1], dp[i - 1][j - 1])
                        );
                    }

                    count += dp[i][j];
                }
            }
        }

        return count;
    }

    // ---------------------------------------------------
    // Space Optimized DP
    //
    // Time: O(rows * cols)
    // Space: O(cols)
    // ---------------------------------------------------
    public static int countSquaresSpaceOptimized(int[][] matrix) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[] prev = new int[cols];
        int[] curr = new int[cols];

        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (matrix[i][j] == 1) {

                    if (i == 0 || j == 0) {
                        curr[j] = 1;
                    } else {
                        curr[j] = 1 + Math.min(
                                prev[j],
                                Math.min(curr[j - 1], prev[j - 1])
                        );
                    }

                    count += curr[j];
                } else {
                    curr[j] = 0;
                }
            }

            System.arraycopy(curr, 0, prev, 0, cols);
        }

        return count;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[][] matrix = {
                {0, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1}
        };

        System.out.println("Brute Force: "
                + countSquaresBruteForce(matrix));

        System.out.println("DP Tabulation: "
                + countSquaresDP(matrix));

        System.out.println("Space Optimized DP: "
                + countSquaresSpaceOptimized(matrix));
    }
}
