package Problems.DynamicProgramming.Squares;


import java.util.Stack;

public class MaximalRectangle {

    // ---------------------------------------------------
    // Problem: https://leetcode.com/problems/maximal-rectangle/description/
    // 85. Maximal Rectangle
    //
    // Given a binary matrix of 0s and 1s,
    // find the largest rectangle containing only 1s
    // and return its area.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Core Intuition (MOST IMPORTANT):
    //
    // Think row by row.
    //
    // For each row, treat it as the base of a histogram:
    // - height[j] = number of consecutive 1s ending at row i in column j
    //
    // Then:
    // - For each row's histogram, compute the
    //   Largest Rectangle in Histogram (LeetCode 84)
    //
    // The maximum over all rows is the answer.
    //
    // This converts a 2D problem into:
    // DP (heights) + Stack (largest rectangle).
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Main Function
    //
    // Time: O(rows * cols)
    // Space: O(cols)
    // ---------------------------------------------------
    public static int maximalRectangle(char[][] matrix) {

        if (matrix == null || matrix.length == 0)
            return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[] heights = new int[cols];
        int maxArea = 0;

        // Build histogram row by row
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1')
                    heights[j] += 1;
                else
                    heights[j] = 0;
            }

            // For current histogram, find max rectangle
            maxArea = Math.max(maxArea, largestRectangleHistogram(heights));
        }

        return maxArea;
    }

    // ---------------------------------------------------
    // Helper: Largest Rectangle in Histogram
    //
    // Intuition:
    // Use a monotonic increasing stack.
    //
    // When we find a bar smaller than the top of stack,
    // we pop and calculate area with popped height
    // as the smallest bar.
    //
    // Time: O(n)
    // Space: O(n)
    // ---------------------------------------------------
    private static int largestRectangleHistogram(int[] heights) {

        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {

            int currHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {

                int height = heights[stack.pop()];
                int width;

                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }

                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        char[][] matrix = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };

        System.out.println("Maximal Rectangle Area: "
                + maximalRectangle(matrix));
    }
}
