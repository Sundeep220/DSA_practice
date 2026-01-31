package Problems.DynamicProgramming.LIS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LongestIncreasingSubsequence {

    // Problem: https://leetcode.com/problems/longest-increasing-subsequence/description/

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // State: (current index, previous index taken)
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int lisI(int index, int prevIndex, int[] nums) {

        if (index == nums.length)
            return 0;

        // Option 1: Skip current element
        int notTake = lisI(index + 1, prevIndex, nums);

        // Option 2: Take current element if valid
        int take = 0;
        if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
            take = 1 + lisI(index + 1, index, nums);
        }

        return Math.max(take, notTake);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n^2)
    // Space: O(n^2)
    // ---------------------------------------------------
    public static int lisII(int index, int prevIndex,
                            int[] nums, int[][] dp) {

        if (index == nums.length)
            return 0;

        if (dp[index][prevIndex + 1] != -1)
            return dp[index][prevIndex + 1];

        int notTake = lisII(index + 1, prevIndex, nums, dp);

        int take = 0;
        if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
            take = 1 + lisII(index + 1, index, nums, dp);
        }

        dp[index][prevIndex + 1] = Math.max(take, notTake);
        return dp[index][prevIndex + 1];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP) - 2D
    // State: dp[index][prevIndex + 1]
    // Time: O(n^2)
    // Space: O(n^2)
    // ---------------------------------------------------
    public static int lisIII(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 1][n + 1];

        // Base case:
        // dp[n][*] = 0 → already 0 by default

        for (int index = n - 1; index >= 0; index--) {
            for (int prevIndex = index - 1; prevIndex >= -1; prevIndex--) {

                // Not take current element
                int notTake = dp[index + 1][prevIndex + 1];

                // Take current element (if valid)
                int take = 0;
                if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
                    take = 1 + dp[index + 1][index + 1];
                }

                dp[index][prevIndex + 1] = Math.max(take, notTake);
            }
        }

        return dp[0][0]; // start from index 0, prevIndex = -1
    }


    // ---------------------------------------------------
    // Space Optimized Tabulation
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static int lisIV(int[] nums) {
        int n = nums.length;

        int[] ahead = new int[n + 1];

        for (int index = n - 1; index >= 0; index--) {
            int[] curr = new int[n + 1];

            for (int prevIndex = index - 1; prevIndex >= -1; prevIndex--) {

                int notTake = ahead[prevIndex + 1];

                int take = 0;
                if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
                    take = 1 + ahead[index + 1];
                }

                curr[prevIndex + 1] = Math.max(take, notTake);
            }

            ahead = curr; // safe swap (new array)
        }

        return ahead[0];
    }

    // Most Optimized Tabulation
    // dp[i] → max length of LIS ending at index i
    // Time: O(n^2)
    // Space: O(n)
    public static int lisVI(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    // ---------------------------------------------------
    // Most Optimal: Greedy + Binary Search
    // Time: O(n log n)
    // Space: O(n)
    // ---------------------------------------------------
    public static int lisV(int[] nums) {
        int n = nums.length;
        int[] temp = new int[n];
        int len = 0;

        for (int num : nums) {
            int idx = Arrays.binarySearch(temp, 0, len, num);

            // If not found, binarySearch returns (-insertionPoint - 1)
            if (idx < 0) {
                idx = -idx - 1;
            }

            temp[idx] = num;

            if (idx == len) {
                len++;
            }
        }

        return len;
    }

    // Another way
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        List<Integer> temp = new ArrayList<>();

        for (int num : nums) {
            int idx = Collections.binarySearch(temp, num);

            if (idx < 0) {
                idx = -idx - 1;  // insertion point
            }

            if (idx == temp.size()) {
                temp.add(num);  // extend the list
            } else {
                temp.set(idx, num);  // replace existing element
            }
        }

        return temp.size();
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};

        System.out.println("Recursion: "
                + lisI(0, -1, nums));

        int[][] dp = new int[nums.length][nums.length + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + lisII(0, -1, nums, dp));

        System.out.println("Tabulation: "
                + lisIII(nums));

        System.out.println("Space Optimized: "
                + lisIV(nums));

        System.out.println("Greedy + Binary Search: "
                + lisV(nums));
    }
}
