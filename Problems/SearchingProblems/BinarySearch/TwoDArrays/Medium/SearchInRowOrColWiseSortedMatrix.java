package Problems.SearchingProblems.BinarySearch.TwoDArrays.Medium;

public class SearchInRowOrColWiseSortedMatrix {
    // Problem: https://leetcode.com/problems/search-a-2d-matrix-ii/

    // Brute Force: O(m*n) time | O(1) space
    public static boolean searchMatrix(int[][] matrix, int target) {
        for (int[] ints : matrix) {
            for(int j: ints) {
                if(j == target) return true;
            }
        }
        return false;
    }

    //Better Solution: O(mlog(n)) time | O(1) space
    public static boolean searchMatrixBetter(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        for (int[] ints : matrix) {
            if (ints[0] <= target && ints[n - 1] >= target) {
                if (binarySearch(ints, target)) return true;
            }
        }
        return false;
    }

    public static boolean binarySearch(int[] arr, int target) {
        int start = 0, end = arr.length - 1;
        while(start <= end) {
            int mid = start + (end - start) / 2;
            if(arr[mid] == target) return true;
            else if(arr[mid] < target) start = mid + 1;
            else end = mid - 1;
        }
        return false;
    }

    // Optimal Solution: O(M + N) time | O(1) space
    // Applying Binary Search but by using observations:
    // - Matrix is sorted row-wise and column-wise
    // - So we consider 4 cornors, (0, 0), (0, m-1), (n-1, 0), (n-1, m-1) to find the target
    // - for (0,0) and (n-1, m-1), we dont know which way to traverse and compare as from these points, all elements are in
    //   increasing order in both directions
    // - for (0, m-1) and (n-1, 0), we know which way to traverse and compare as from these points, all elements are in
    // IN this solution, I will be traversing from (0, m-1)
    public static boolean searchMatrixOptimal(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int row = 0, col = n - 1;
        while(row < m && col >= 0) {
            if(matrix[row][col] == target) return true;
            else if(matrix[row][col] < target) row++;
            else col--;
        }
        return false;
    }

    public static boolean searchMatrixOptimal2(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int row = 0, col = n - 1;
        while(row < m && col >= 0) {
            if(matrix[row][col] == target) return true;
            else if(matrix[row][col] < target) row++;
            else col--;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        int target = 5;
        System.out.println(searchMatrix(matrix, target));
        System.out.println(searchMatrixBetter(matrix, target));
        System.out.println(searchMatrixOptimal(matrix, target));
        System.out.println(searchMatrixOptimal2(matrix, target));

    }
}
