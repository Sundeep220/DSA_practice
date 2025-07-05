package Problems.SearchingProblems.BinarySearch.TwoDArrays.Medium;


public class SeachIn2DMatrix {
    // Problem: https://leetcode.com/problems/search-a-2d-matrix/

    //Brute Force: O(m*n) time | O(1) space
    public static boolean searchMatrix(int[][] matrix, int target) {
        for (int[] ints : matrix)
            for(int j: ints)
                if(j == target) return true;
        return false;
    }

    // Better Solution: O(log(m*n)) time | O(1) space
    public static boolean searchMatrixOptimal(int[][] matrix, int target) {
        for (int[] row : matrix) {
            if (row[0] <= target && target <= row[row.length - 1]) {
                if(binarySearch(row, target))
                    return true;
            }
        }
        return false;
    }

    public static boolean binarySearch(int[] arr, int target) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] == target) return true;
            else if (arr[mid] < target) start = mid + 1;
            else end = mid - 1;
        }
        return false;
    }

    // Optimal Solution: O(log(m*n)) time | O(1) space
    // Applyig Binary Search by flattening the matrix , using maths,
    // For 1D array, the corresponding index i maps to 2d matrix as follows:
    // (i / n)th row and (i % n)th column, where n is the number of columns in the 2D matrix
    public static boolean searchMatrixOptimal2(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int start = 0, end = m * n - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            int midElement = matrix[mid / n][mid % n];
            if (midElement == target) return true;
            else if (midElement < target) start = mid + 1;
            else end = mid - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        int target = 5;
        System.out.println(searchMatrix(matrix, target));
        System.out.println(searchMatrixOptimal(matrix, target));
        System.out.println(searchMatrixOptimal2(matrix, target));
    }

}
