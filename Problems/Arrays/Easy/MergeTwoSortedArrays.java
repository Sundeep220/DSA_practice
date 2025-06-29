package Problems.Arrays.Easy;

import java.util.Arrays;

public class MergeTwoSortedArrays {
    // https://leetcode.com/problems/merge-sorted-array/


    // Brute Force
    // Time Complexity: O(n * m), Space Complexity: O(1)
    int[] mergeNaive(int[] arr1, int[] arr2) {
        int n = arr1.length, m = arr2.length;
        int[] merged = new int[n + m];

        // Copy elements
        System.arraycopy(arr1, 0, merged, 0, n);
        System.arraycopy(arr2, 0, merged, n, m);

        // Sort
        Arrays.sort(merged);
        return merged;
    }


    // Better Approach: Two Pointers
    // Time Complexity: O(n + m), Space Complexity: O(1)
    int[] mergeBetter(int[] arr1, int[] arr2) {
        int n = arr1.length, m = arr2.length;
        int[] merged = new int[n + m];
        int i = 0, j = 0, k = 0;

        // Merge arrays
        while (i < n && j < m) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }

        // Add remaining elements
        while (i < n) merged[k++] = arr1[i++];
        while (j < m) merged[k++] = arr2[j++];

        return merged;
    }


    // Optimal Solution
    // Time Complexity: O(n + m), Space Complexity: O(1)
    // Given if nums1 size is n + m
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m-1;
        int j = n-1;
        int k = m+n-1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }

        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }
    }
}
