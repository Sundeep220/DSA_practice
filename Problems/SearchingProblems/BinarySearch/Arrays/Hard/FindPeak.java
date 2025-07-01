package Problems.SearchingProblems.BinarySearch.Arrays.Hard;
import java.util.*;
public class FindPeak {
    // Problem: https://leetcode.com/problems/find-peak-element/
    // Time Complexity: O(logn) time | O(1) space

    public static int findPeakElement(ArrayList<Integer> arr) {
        int n = arr.size(); // Size of array.

        for (int i = 0; i < n; i++) {
            // Checking for the peak:
            if ((i == 0 || arr.get(i - 1) < arr.get(i))
                    && (i == n - 1 || arr.get(i) > arr.get(i + 1))) {
                return arr.get(i); // Return the peak element at index i;
            }
        }
        // Dummy return statement
        return -1;
    }


    //Optimal Solution: Binary Search
    // Time Complexity: O(logn) time | O(1) space
    public static int findPeakElementOptimal(ArrayList<Integer> arr) {
        int n = arr.size(); // Size of array

        // Edge cases:
        if (n == 1) return 0;   // Only one element then no peak
        if (arr.get(0) > arr.get(1)) return 0;   // First element is the peak if it is greater than its neighbour to the right
        if (arr.get(n - 1) > arr.get(n - 2)) return n - 1; // Last element is the peak if it is greater than its neighbour to the left

        int low = 1, high = n - 2;
        while (low <= high) {
            int mid = (low + high) / 2;

            // If arr[mid] is the peak:
            if (arr.get(mid - 1) < arr.get(mid) && arr.get(mid) > arr.get(mid + 1))
                return mid;

            // If we are in the left:
            if (arr.get(mid) > arr.get(mid - 1)) low = mid + 1;

                // If we are in the right:
                // Or, arr[mid] is a common point:
            else high = mid - 1;
        }
        // Dummy return statement
        return -1;
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1, 2, 3));
        System.out.println(findPeakElement(arr));
        System.out.println(findPeakElementOptimal(arr));
    }
}
