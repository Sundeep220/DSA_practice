package Problems.SearchingProblems.BinarySearch.TwoDArrays.Hard;

import java.util.Arrays;

public class FindPeak {
    // Problem: https://leetcode.com/problems/find-a-peak-element-ii/

    // Brute Force: O(m*n) time | O(1) space
    public static int[] findPeakGrid(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int curr = mat[i][j];

                boolean top = i == 0 || mat[i - 1][j] < curr;
                boolean bottom = i == m - 1 || mat[i + 1][j] < curr;
                boolean left = j == 0 || mat[i][j - 1] < curr;
                boolean right = j == n - 1 || mat[i][j + 1] < curr;

                if (top && bottom && left && right) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{-1, -1}; // should never happen according to the problem
    }

    // Better Solution: O(m*n) time | O(1) space
    // Find and return the maximum element in the matrix
    public static int[] findPeakGridBetter(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        int maxVal = Integer.MIN_VALUE;
        int maxRow = -1, maxCol = -1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] > maxVal) {
                    maxVal = mat[i][j];
                    maxRow = i;
                    maxCol = j;
                }
            }
        }

        return new int[]{maxRow, maxCol}; // This is a guaranteed peak
    }

    // Optimal Solution: Binary Search
    // Time Complexity: O(mlogn) time | O(1) space
    public static int[] findPeakGridOptimal(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int low = 0, high = n - 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            int maxRow = findMaxInRow(mat,m, mid);
            int left = mid - 1 >= 0 ? mat[maxRow][mid - 1] : -1;
            int right = mid + 1 < n ? mat[maxRow][mid + 1] : -1;
            if(mat[maxRow][mid] > left && mat[maxRow][mid] > right) return new int[]{maxRow, mid};
            else if(mat[maxRow][mid] < left) high = mid - 1;
            else low = mid + 1;
        }
        return new int[]{-1, -1};
    }

    public static int findMaxInRow(int[][] mat, int m, int col) {
        int maxRow = 0;
        for(int i = 0; i < m; i++) {
            if(mat[i][col] > mat[maxRow][col]) maxRow = i;
        }
        return maxRow;
    }


    public static void main(String[] args) {
        int[][] mat = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        System.out.println(Arrays.toString(findPeakGrid(mat)));
        System.out.println(Arrays.toString(findPeakGridBetter(mat)));
        System.out.println(Arrays.toString(findPeakGridOptimal(mat)));

    }
}
