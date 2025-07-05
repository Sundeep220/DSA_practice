package Problems.SearchingProblems.BinarySearch.Answers.Hard;

import java.util.Arrays;

public class KthElementInSortedArray {
    // Problem: Given two sorted arrays of size m and n respectively, you are tasked with finding the element that would be at the kth position of the final sorted array.

    //Brute Force: Using Two pointers and merge the two arrays and find the kth element
    // Time Complexity: O((m+n)log(m+n)) + O(m) + O(n), Space Complexity: O(m+n)
    public static int findKthElement(int[] nums1, int[] nums2, int k) {
        int[] merged = new int[nums1.length + nums2.length];
        int i = 0, j = 0, l = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                merged[l++] = nums1[i++];
            } else {
                merged[l++] = nums2[j++];
            }
        }
        while (i < nums1.length) {
            merged[l++] = nums1[i++];
        }
        while (j < nums2.length) {
            merged[l++] = nums2[j++];
        }

        if(merged.length < k) return -1;
        return merged[k - 1];
    }

    // Better Approach: Remove the 3rd array and use two pointers to find the kth element
    // Time Complexity: O(m+n), Space Complexity: O(1)
    public static int findKthElement2(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int i = 0, j = 0, cnt = 0;
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                if (cnt == k - 1) return nums1[i];
                i++;
                cnt++;
            }
            else {
                if (cnt == k - 1) return nums2[j];
                j++;
                cnt++;
            }
        }
        while (i < m) {
            if (cnt == k - 1) return nums1[i];
            i++;
            cnt++;
        }
        while (j < n) {
            if (cnt == k - 1) return nums2[j];
            j++;
            cnt++;
        }
        return -1;
    }


    // Optimal Solution: Binary Search
    // Time Complexity: O(log(min(m, n))), Space Complexity: O(1)
    public static int findKthElementOptimal(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        if (m > n) return findKthElementOptimal(nums2, nums1, k);
        int low = 0, high = m;
        while (low <= high) {
            int l1 = Integer.MIN_VALUE;
            int l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE;
            int r2 = Integer.MAX_VALUE;
            int mid1 = (low + high) >> 1;   // mid1 : number of elements to take from nums1
            int mid2 = k - mid1;  // mid2 : number of elements to take from nums2 which is equal to k - mid1
            if (mid1 < m) r1 = nums1[mid1];   // r1 : max element from nums1 only if mid1 < m
            if (mid1 - 1 >= 0) l1 = nums1[mid1 - 1]; // l1 : min element from nums1 only if mid1 - 1 >= 0
            if (mid2 < n) r2 = nums2[mid2]; // r2 : max element from nums2
            if (mid2 - 1 >= 0) l2 = nums2[mid2 - 1]; // l2 : min element from nums2
            if (l1 <= r2 && l2 <= r1) return Math.max(l1, l2);
            else if (l1 > r2) high = mid1 - 1;
            else low = mid1 + 1;
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 4, 5};
        int[] nums2 = {6, 7, 8, 9, 10};
        int k = 5;
        System.out.println(findKthElement(nums1, nums2, k)); // Output: 5
        System.out.println(findKthElement2(nums1, nums2, k)); // Output: 5
        System.out.println(findKthElementOptimal(nums1, nums2, k)); // Output: 5
    }
}
