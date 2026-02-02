package Problems.DynamicProgramming.Partitions;


import java.util.Arrays;

public class PartitionArrayForMaximumSum {

    // ---------------------------------------------------
    // Problem: https://leetcode.com/problems/partition-array-for-maximum-sum/description/
    // 1043. Partition Array for Maximum Sum
    //
    // Given an array arr and an integer k,
    // partition the array into contiguous subarrays
    // of length at most k.
    //
    // Each subarray is replaced by its maximum value.
    //
    // Return the maximum possible sum after partitioning.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    //
    // Intuition:
    // At index i, try all partitions of length 1 to k.
    //
    // For a partition arr[i..j]:
    // - Find max element in this range
    // - Contribution = max * length
    // - Add best answer from j+1
    //
    // f(i) → maximum sum obtainable from index i to end
    //
    // Base case:
    // i == n → no elements → sum = 0
    //
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    public static int maxSumI(int i, int[] arr, int k) {
        int n = arr.length;
        if (i == n)
            return 0;

        int max = 0;
        int best = 0;

        for (int j = i; j < Math.min(n, i + k); j++) {
            max = Math.max(max, arr[j]);
            int len = j - i + 1;
            int sum = max * len + maxSumI(j + 1, arr, k);
            best = Math.max(best, sum);
        }

        return best;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    //
    // Intuition:
    // The recursive solution recalculates f(i)
    // many times, so we memoize it.
    //
    // dp[i] → maximum sum from index i to end
    //
    // Time: O(n * k)
    // Space: O(n)
    // ---------------------------------------------------
    public static int maxSumII(int i, int[] arr, int k, int[] dp) {
        int n = arr.length;
        if (i == n)
            return 0;

        if (dp[i] != -1)
            return dp[i];

        int max = 0;
        int best = 0;

        for (int j = i; j < Math.min(n, i + k); j++) {
            max = Math.max(max, arr[j]);
            int len = j - i + 1;
            int sum = max * len + maxSumII(j + 1, arr, k, dp);
            best = Math.max(best, sum);
        }

        dp[i] = best;
        return best;
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP) — Optimal
    //
    // Intuition:
    // dp[i] → maximum sum from index i to end
    //
    // We compute dp[] from right to left.
    //
    // Transition:
    // dp[i] = max over j in [i..i+k-1]:
    //         (max(arr[i..j]) * (j-i+1)) + dp[j+1]
    //
    // Time: O(n * k)
    // Space: O(n)
    // ---------------------------------------------------
    public static int maxSumIII(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {
            int max = 0;
            int best = 0;

            for (int j = i; j < Math.min(n, i + k); j++) {
                max = Math.max(max, arr[j]);
                int len = j - i + 1;
                best = Math.max(best, max * len + dp[j + 1]);
            }

            dp[i] = best;
        }

        return dp[0];
    }

    // ---------------------------------------------------
    // Space Optimized
    //
    // Intuition:
    // dp already uses O(n) space and depends
    // on future states dp[j+1].
    //
    // Further optimization is not possible.
    // ---------------------------------------------------
    public static int maxSumIV(int[] arr, int k) {
        return maxSumIII(arr, k);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[] arr1 = {1, 15, 7, 9, 2, 5, 10};
        int k1 = 3;

        int[] arr2 = {1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3};
        int k2 = 4;

        // Recursion
        System.out.println("Recursion: "
                + maxSumI(0, arr1, k1));

        // Memoization
        int[] dp = new int[arr1.length];
        Arrays.fill(dp, -1);

        System.out.println("Memoization: "
                + maxSumII(0, arr1, k1, dp));

        // Tabulation
        System.out.println("Tabulation: "
                + maxSumIII(arr1, k1));

        // Space Optimized
        System.out.println("Space Optimized: "
                + maxSumIV(arr1, k1));
    }
}
