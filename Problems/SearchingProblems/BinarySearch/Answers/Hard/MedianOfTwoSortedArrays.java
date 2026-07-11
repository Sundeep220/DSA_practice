package Problems.SearchingProblems.BinarySearch.Answers.Hard;

import java.util.Arrays;

public class MedianOfTwoSortedArrays {
    // Problem: https://leetcode.com/problems/median-of-two-sorted-arrays/

    // Brute Force:
    // Merge the two arrays and find the median.
    // Time Complexity: O((m+n)log(m+n)) + O(m) + O(n)
    // Space Complexity: O(m+n)
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merged = new int[nums1.length + nums2.length];
        System.arraycopy(nums1, 0, merged, 0, nums1.length);  // O(n)
        System.arraycopy(nums2, 0, merged, nums1.length, nums2.length);  // O(m)
        Arrays.sort(merged);  // O((m+n)log(m+n))
        return merged.length % 2 == 0 ? (merged[merged.length / 2] + merged[merged.length / 2 - 1]) / 2.0 : merged[merged.length / 2];
    }

    // Better Approach: use two pointers to merge the arrays
    // Time Complexity: O(m+n)
    // Space Complexity: O(m+n)
    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] merged = new int[m + n];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) merged[k++] = nums1[i++];
            else merged[k++] = nums2[j++];
        }
        while (i < m) merged[k++] = nums1[i++];
        while (j < n) merged[k++] = nums2[j++];
        return merged.length % 2 == 0 ? (merged[merged.length / 2] + merged[merged.length / 2 - 1]) / 2.0 : merged[merged.length / 2];
    }

    // Better Solution: Using Two pointers again but avaoid using 3rd array
    // Time Complexity: O(m+n)
    // Space Complexity: O(1)
    public static double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        // Size of two given arrays
        int n1 = nums1.length;
        int n2 = nums2.length;

        int n = n1 + n2; //total size
        //required indices:
        int ind2 = n / 2;
        int ind1 = ind2 - 1;
        int cnt = 0;
        int ind1el = -1, ind2el = -1;

        //apply the merge step:
        int i = 0, j = 0;
        while (i < n1 && j < n2) {
            if (nums1[i] < nums2[j]) {
                if (cnt == ind1) ind1el = nums1[i];
                if (cnt == ind2) ind2el = nums1[i];
                cnt++;
                i++;
            } else {
                if (cnt == ind1) ind1el = nums2[j];
                if (cnt == ind2) ind2el = nums2[j];
                cnt++;
                j++;
            }
        }

        //copy the left-out elements:
        while (i < n1) {
            if (cnt == ind1) ind1el = nums1[i];
            if (cnt == ind2) ind2el = nums1[i];
            cnt++;
            i++;
        }
        while (j < n2) {
            if (cnt == ind1) ind1el = nums1[j];
            if (cnt == ind2) ind2el = nums1[j];
            cnt++;
            j++;
        }

        //Find the median:
        if (n % 2 == 1) {
            return ind2el;
        }

        return (ind1el + ind2el) / 2.0;
    }

    // Optimal Solution: Binary Search
    // Time Complexity: O(log(max(m, n)))
    // Space Complexity: O(1)
    /*
     * ========================== INTUITION ==========================
     *
     * Instead of merging both sorted arrays (O(m + n)), we find the
     * correct partition (cut) in both arrays using Binary Search.
     *
     * We divide the combined sorted array into:
     *
     *      Left Half        |       Right Half
     *
     * such that:
     * 1. Left half contains exactly half of the total elements.
     * 2. Every element in the left half <= every element in the right half.
     *
     * We binary search only on nums1 to decide how many elements
     * should be taken from nums1 into the left partition.
     *
     * If we take 'mid1' elements from nums1,
     * then we must take 'mid2 = left - mid1' elements from nums2.
     *
     * We never merge the arrays.
     * We only compare the four boundary elements around the partitions:
     *
     *              nums1                      nums2
     *
     *      ...  l1 | r1              ...  l2 | r2
     *
     * where,
     * l1 = largest element on left side of nums1
     * r1 = smallest element on right side of nums1
     * l2 = largest element on left side of nums2
     * r2 = smallest element on right side of nums2
     *
     * Correct partition condition:
     *
     *      l1 <= r2
     *      l2 <= r1
     *
     * If l1 > r2:
     *      -> We have taken too many elements from nums1.
     *      -> Move partition LEFT.
     *
     * If l2 > r1:
     *      -> We have taken too few elements from nums1.
     *      -> Move partition RIGHT.
     *
     * Once the correct partition is found:
     *
     * Odd total elements:
     *      Median = max(l1, l2)
     *
     * Even total elements:
     *      Median = (max(l1,l2) + min(r1,r2)) / 2
     *
     * Time Complexity : O(log(max(n1, n2)))   // O(log(min(n1,n2))) if binary searching on smaller array
     * Space Complexity: O(1)
     * ==============================================================
     */

    public static double findMedianSortedArraysOptimal(int[] nums1, int[] nums2) {

        int n1 = nums1.length;
        int n2 = nums2.length;

        // This implementation performs binary search on the larger array.
        // (Most standard implementations binary search on the smaller array.)
        if (n1 < n2)
            return findMedianSortedArraysOptimal(nums2, nums1);

        int low = 0;
        int high = n1;

        int total = n1 + n2;

        // Number of elements that should be present in the left partition.
        // (+1) ensures that for odd length, left side contains one extra element.
        int left = (total + 1) / 2;

        while (low <= high) {

            // Number of elements taken from nums1.
            int mid1 = (low + high) >> 1;

            // Remaining elements are taken from nums2.
            int mid2 = left - mid1;

            // Boundary values around the partition.
            // Use -∞ and +∞ to handle edge cases when partition
            // is at the beginning or end of an array.
            int l1 = Integer.MIN_VALUE;
            int l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE;
            int r2 = Integer.MAX_VALUE;

            if (mid1 > 0)
                l1 = nums1[mid1 - 1];

            if (mid1 < n1)
                r1 = nums1[mid1];

            if (mid2 > 0)
                l2 = nums2[mid2 - 1];

            if (mid2 < n2)
                r2 = nums2[mid2];

            /*
             * Partition looks like:
             *
             * nums1 : .... l1 | r1 ....
             * nums2 : .... l2 | r2 ....
             *
             * If every left element <= every right element,
             * then we have found the correct partition.
             */
            if (l1 <= r2 && l2 <= r1) {

                // Even number of elements:
                // Median is the average of the middle two elements.
                if (total % 2 == 0) {
                    return (Math.max(l1, l2) + Math.min(r1, r2)) / 2.0;
                }

                // Odd number of elements:
                // Left partition has one extra element.
                return Math.max(l1, l2);
            }

            // Too many elements taken from nums1.
            // Move partition to the left.
            else if (l1 > r2) {
                high = mid1 - 1;
            }

            // Too few elements taken from nums1.
            // Move partition to the right.
            else {
                low = mid1 + 1;
            }
        }

        // Should never reach here for valid sorted arrays.
        return 0;
    }

    public static void main(String[] args) {
        int[] a = {2, 4, 6};
        int[] b = {1, 3, 5};
        System.out.println(findMedianSortedArrays(a, b));
        System.out.println(findMedianSortedArrays2(a, b));
        System.out.println(findMedianSortedArrays3(a, b));
        System.out.println(findMedianSortedArraysOptimal(a, b));
    }
}
