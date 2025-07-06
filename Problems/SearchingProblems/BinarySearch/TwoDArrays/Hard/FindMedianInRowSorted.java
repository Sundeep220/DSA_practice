package Problems.SearchingProblems.BinarySearch.TwoDArrays.Hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindMedianInRowSorted {
    // Problem: https://www.naukri.com/code360/problems/median-in-matrix_981178

    //Brute force: Store elements in list and sort and return middle element
    //Time Complexity:O(MXN) + O(MXN(log(MXN)), Space Complexity: O(MXN)
    public static double findMedianInRowSorted(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        List<Integer> list = new ArrayList<>();
        for (int[] ints : mat) {
            for (int j : ints) {
                list.add(j);
            }
        }
        Collections.sort(list);
        return list.get(list.size() / 2);
    }

    //Optimal Solution: Binary Search
    static int upperBound(int[] arr, int x, int n) {
        int low = 0, high = n - 1;
        int ans = n;

        while (low <= high) {
            int mid = (low + high) / 2;
            // maybe an answer
            if (arr[mid] > x) {
                ans = mid;
                // look for a smaller index on the left
                high = mid - 1;
            } else {
                low = mid + 1; // look on the right
            }
        }
        return ans;
    }

    static int countSmallEqual(int[][] matrix, int m, int n, int x) {
        int cnt = 0;
        for (int i = 0; i < m; i++) {
            cnt += upperBound(matrix[i], x, n);
        }
        return cnt;
    }

    static int median(int[][] matrix, int m, int n) {
        int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;

        // point low and high to right elements
        for (int i = 0; i < m; i++) {
            low = Math.min(low, matrix[i][0]);
            high = Math.max(high, matrix[i][n - 1]);
        }

        int req = (n * m) / 2;
        while (low <= high) {
            int mid = (low + high) / 2;
            int smallEqual = countSmallEqual(matrix, m, n, mid);
            if (smallEqual <= req) low = mid + 1;
            else high = mid - 1;
        }
        return low;
    } 

    public static void main(String[] args) {
        int[][] mat = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        System.out.println(findMedianInRowSorted(mat)); // Output: 11
        System.out.println(median(mat, mat.length, mat[0].length)); // Output: 11
    }
}
