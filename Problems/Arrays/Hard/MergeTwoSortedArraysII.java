package Problems.Arrays.Hard;

import java.util.Arrays;

public class MergeTwoSortedArraysII {
    // Problem: https://takeuforward.org/plus/dsa/problems/merge-two-sorted-arrays-without-extra-space/
    //Given two sorted arrays arr1[] and arr2[] of sizes n and m in non-decreasing order.
    // Merge them in sorted order. Modify arr1 so that it contains the first N elements and modify arr2 so that it contains the last M elements.
    //Example 1:
    //Input:
    //n = 4, arr1[] = [1 4 8 10]
    //m = 5, arr2[] = [2 3 9]
    //Output:
    //arr1[] = [1 2 3 4]
    //arr2[] = [8 9 10]
    //
    //Explanation:
    //After merging the two non-decreasing arrays, we get, 1,2,3,4,8,9,10.

    // Brute Force
    // Time Complexity: O(n+m) + O(n+m), Space Complexity: O(n + m)
    // We are going to use a third array to merge them and then copy first n elements to arr1 and last m elements to arr2
    // Note: This solution is not correct if the given constraint is to solve in O(1) space
    public static void merge(int[] arr1, int[] arr2) {
        int n = arr1.length, m = arr2.length, i = 0, j = 0, k = 0;
        int[] merged = new int[n + m];
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

        // Copy merged array to original arrays
        System.arraycopy(merged, 0, arr1, 0, n);
        System.arraycopy(merged, n, arr2, 0, m);
    }

    // Better Approach: Two Pointers
    // Time Complexity: O(min(n, m)) + O(n*logn) + O(m*logm), Space Complexity: O(1)
    // Since it is given that arrays are sorted, we can you two pointers to solve this in constant space
    // we willl keep left at n - 1 (arr1 size) and right at 0 (arr2 size) and compare them:
    // if arr1[left] > arr2[right] then we will swap the elements and move the pointers accordingly
    // if arr1[left] <= arr2[right] then we will break out as we already have them sorted already (Given info in the problem)
    public static void mergeBetter(int[] arr1, int[] arr2) {
        int n = arr1.length, m = arr2.length, left = n - 1, right = 0;
        while (left >= 0 && right < m) {
            if (arr1[left] > arr2[right]) {
                int temp = arr1[left];
                arr1[left] = arr2[right];
                arr2[right] = temp;
                left--; right++;
            } else break;
        }
        Arrays.sort(arr1);
        Arrays.sort(arr2);
    }

    public static void main(String[] args) {
        int[] arr1 = { 1, 4, 8, 10 };
        int[] arr2 = { 2, 3, 9 };
        merge(arr1, arr2);
        System.out.println("Brute Force Solution: " + Arrays.toString(arr1) + " " + Arrays.toString(arr2));
    }
}
