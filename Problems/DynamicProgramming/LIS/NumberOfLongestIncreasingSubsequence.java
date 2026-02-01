package Problems.DynamicProgramming.LIS;

import java.util.Arrays;

public class NumberOfLongestIncreasingSubsequence {

    // ---------------------------------------------------
    // Problem: https://leetcode.com/problems/number-of-longest-increasing-subsequence/
    // 673. Number of Longest Increasing Subsequence
    //
    // Given an integer array nums, return the number of
    // longest strictly increasing subsequences.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    //
    // Intuition:
    // Generate all increasing subsequences using recursion.
    // Track:
    // - maxLen  → length of LIS found so far
    // - count   → number of subsequences having maxLen
    //
    // At each index:
    // 1) Skip the element
    // 2) Take the element if it maintains increasing order
    //
    // This approach is only for understanding.
    //
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    static int maxLen;
    static int count;

    public static void lisCountI(int index, int prevIndex,
                                 int currLen, int[] nums) {

        if (index == nums.length) {
            if (currLen > maxLen) {
                maxLen = currLen;
                count = 1;
            } else if (currLen == maxLen) {
                count++;
            }
            return;
        }

        // Not take
        lisCountI(index + 1, prevIndex, currLen, nums);

        // Take if increasing
        if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
            lisCountI(index + 1, index, currLen + 1, nums);
        }
    }

    // ---------------------------------------------------
    // Memoization
    //
    // Intuition:
    // Memoization is NOT very effective here because
    // the number of LIS depends on global max length,
    // not just on (index, prevIndex).
    //
    // Hence, we skip memoization and move to
    // a better DP formulation.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP) — Optimal Approach
    //
    // Intuition:
    // For each index i, maintain:
    // len[i]   → length of LIS ending at i
    // cnt[i]   → number of LIS ending at i
    //
    // Transition:
    // If nums[j] < nums[i]:
    //   - If len[j] + 1 > len[i]:
    //       len[i] = len[j] + 1
    //       cnt[i] = cnt[j]
    //   - Else if len[j] + 1 == len[i]:
    //       cnt[i] += cnt[j]
    //
    // Final Answer:
    // Sum cnt[i] where len[i] == max LIS length
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static int lisCountIII(int[] nums) {
        int n = nums.length;

        int[] len = new int[n];
        int[] cnt = new int[n];

        Arrays.fill(len, 1);
        Arrays.fill(cnt, 1);

        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (len[j] + 1 > len[i]) {
                        len[i] = len[j] + 1;
                        cnt[i] = cnt[j];
                    } else if (len[j] + 1 == len[i]) {
                        cnt[i] += cnt[j];
                    }
                }
            }
            maxLen = Math.max(maxLen, len[i]);
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            if (len[i] == maxLen) {
                result += cnt[i];
            }
        }

        return result;
    }

    // ---------------------------------------------------
    // Space Optimized
    //
    // Intuition:
    // The tabulation solution already uses optimal O(n)
    // space. Further optimization is not possible because
    // each state depends on all previous states.
    // ---------------------------------------------------
    public static int lisCountIV(int[] nums) {
        return lisCountIII(nums);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[] nums1 = {1, 3, 5, 4, 7};
        int[] nums2 = {2, 2, 2, 2, 2};

        // Brute Force
        maxLen = 0;
        count = 0;
        lisCountI(0, -1, 0, nums1);
        System.out.println("Recursion (nums1): " + count);

        maxLen = 0;
        count = 0;
        lisCountI(0, -1, 0, nums2);
        System.out.println("Recursion (nums2): " + count);

        // Tabulation
        System.out.println("Tabulation (nums1): "
                + lisCountIII(nums1));

        System.out.println("Tabulation (nums2): "
                + lisCountIII(nums2));
    }
}
