package Problems.DynamicProgramming.LIS;


import java.util.Arrays;

public class LongestBitonicSubsequence {

    // ---------------------------------------------------
    // Problem: https://www.naukri.com/code360/problems/longest-bitonic-sequence_1062688?leftPanelTabValue=PROBLEM
    // Longest Bitonic Subsequence (LBS)
    // A sequence that first strictly increases,
    // then strictly decreases.
    //
    // Note:
    // Purely increasing OR purely decreasing sequences
    // are also considered bitonic.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    //
    // Intuition:
    // At every index, we have two choices:
    // 1) Skip the current element
    // 2) Take the current element if it follows bitonic rules
    //
    // To enforce bitonic behavior, we track:
    // - prevIndex     → last chosen index
    // - isDecreasing  → whether decreasing phase has started
    //
    // Once decreasing starts, we can NEVER increase again.
    //
    // State:
    // (index, prevIndex, isDecreasing)
    //
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int lbsI(int index, int prevIndex,
                           boolean isDecreasing, int[] arr) {

        if (index == arr.length)
            return 0;

        // Option 1: Skip current element
        int notTake = lbsI(index + 1, prevIndex, isDecreasing, arr);

        // Option 2: Take current element (if valid)
        int take = 0;

        if (prevIndex == -1) {
            // First element can always be taken
            take = 1 + lbsI(index + 1, index, false, arr);
        } else {
            if (!isDecreasing && arr[index] > arr[prevIndex]) {
                // Still in increasing phase
                take = 1 + lbsI(index + 1, index, false, arr);
            } else if (arr[index] < arr[prevIndex]) {
                // Enter or continue decreasing phase
                take = 1 + lbsI(index + 1, index, true, arr);
            }
        }

        return Math.max(take, notTake);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    //
    // Intuition:
    // The recursive solution recalculates the same states
    // again and again.
    //
    // So we memoize using:
    // dp[index][prevIndex + 1][isDecreasing]
    //
    // prevIndex + 1 is used to safely map -1 → 0
    //
    // Time: O(n^2)
    // Space: O(n^2)
    // ---------------------------------------------------
    public static int lbsII(int index, int prevIndex,
                            int isDecreasing, int[] arr,
                            int[][][] dp) {

        if (index == arr.length)
            return 0;

        if (dp[index][prevIndex + 1][isDecreasing] != -1)
            return dp[index][prevIndex + 1][isDecreasing];

        int notTake = lbsII(index + 1, prevIndex, isDecreasing, arr, dp);

        int take = 0;

        if (prevIndex == -1) {
            take = 1 + lbsII(index + 1, index, 0, arr, dp);
        } else {
            if (isDecreasing == 0 && arr[index] > arr[prevIndex]) {
                take = 1 + lbsII(index + 1, index, 0, arr, dp);
            } else if (arr[index] < arr[prevIndex]) {
                take = 1 + lbsII(index + 1, index, 1, arr, dp);
            }
        }

        dp[index][prevIndex + 1][isDecreasing] =
                Math.max(take, notTake);

        return dp[index][prevIndex + 1][isDecreasing];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP) — Optimal Approach
    //
    // Intuition:
    // A Bitonic Subsequence can be split into:
    // 1) LIS ending at index i
    // 2) LDS starting from index i
    //
    // For every index i:
    // LBS length = LIS[i] + LDS[i] - 1
    //
    // (-1 because arr[i] is counted twice)
    //
    // Steps:
    // 1) Compute LIS for each index (left → right)
    // 2) Compute LDS for each index (right → left)
    // 3) Take maximum LIS[i] + LDS[i] - 1
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static int lbsIII(int[] arr) {
        int n = arr.length;

        int[] lis = new int[n];
        int[] lds = new int[n];

        Arrays.fill(lis, 1);
        Arrays.fill(lds, 1);

        // LIS computation
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    lis[i] = Math.max(lis[i], 1 + lis[j]);
                }
            }
        }

        // LDS computation
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (arr[j] < arr[i]) {
                    lds[i] = Math.max(lds[i], 1 + lds[j]);
                }
            }
        }

        int maxLen = 1;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, lis[i] + lds[i] - 1);
        }

        return maxLen;
    }

    // ---------------------------------------------------
    // Space Optimized
    //
    // Intuition:
    // LIS and LDS already use O(n) space.
    // Further optimization is not possible because
    // each state depends on all previous elements.
    //
    // So this is already optimal.
    // ---------------------------------------------------
    public static int lbsIV(int[] arr) {
        return lbsIII(arr);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[] arr = {1, 2, 1, 2, 1};

        System.out.println("Recursion: "
                + lbsI(0, -1, false, arr));

        int[][][] dp = new int[arr.length][arr.length + 1][2];
        for (int[][] mat : dp)
            for (int[] row : mat)
                Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + lbsII(0, -1, 0, arr, dp));

        System.out.println("Tabulation: "
                + lbsIII(arr));

        System.out.println("Space Optimized: "
                + lbsIV(arr));
    }
}
