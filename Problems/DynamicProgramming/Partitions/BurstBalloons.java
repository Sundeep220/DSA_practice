package Problems.DynamicProgramming.Partitions;

import java.util.Arrays;

public class BurstBalloons {

    // ---------------------------------------------------
    // Problem: https://leetcode.com/problems/burst-balloons/description/
    // 312. Burst Balloons
    //
    // Given an array nums[], each balloon has a value.
    // When bursting balloon i:
    // coins = nums[i-1] * nums[i] * nums[i+1]
    //
    // If i-1 or i+1 is out of bounds, treat it as 1.
    //
    // Goal: Maximize total coins.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Preprocessing Intuition (CRITICAL):
    //
    // Add virtual balloons with value 1 at both ends.
    //
    // Example:
    // nums = [3,1,5,8]
    // becomes:
    // arr  = [1,3,1,5,8,1]
    //
    // This removes boundary checks and converts the
    // problem into an MCM-style DP.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    //
    // Intuition:
    // Think in REVERSE:
    // Instead of choosing which balloon to burst FIRST,
    // choose which balloon to burst LAST in a segment.
    //
    // If balloon k is the LAST to burst between (i, j):
    // coins =
    //   arr[i-1] * arr[k] * arr[j+1]
    //   + solve(i, k-1)
    //   + solve(k+1, j)
    //
    // State:
    // f(i, j) → max coins from bursting balloons i..j
    //
    // Base case:
    // if i > j → no balloons → 0 coins
    //
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    public static int burstI(int i, int j, int[] arr) {

        if (i > j)
            return 0;

        int maxCoins = 0;

        for (int k = i; k <= j; k++) {

            int coins =
                    arr[i - 1] * arr[k] * arr[j + 1]
                            + burstI(i, k - 1, arr)
                            + burstI(k + 1, j, arr);

            maxCoins = Math.max(maxCoins, coins);
        }

        return maxCoins;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    //
    // Intuition:
    // Recursive solution recomputes the same (i, j)
    // ranges many times.
    //
    // So we store results in dp[i][j].
    //
    // Time: O(n^3)
    // Space: O(n^2)
    // ---------------------------------------------------
    public static int burstII(int i, int j,
                              int[] arr, int[][] dp) {

        if (i > j)
            return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        int maxCoins = 0;

        for (int k = i; k <= j; k++) {

            int coins =
                    arr[i - 1] * arr[k] * arr[j + 1]
                            + burstII(i, k - 1, arr, dp)
                            + burstII(k + 1, j, arr, dp);

            maxCoins = Math.max(maxCoins, coins);
        }

        dp[i][j] = maxCoins;
        return maxCoins;
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP) — Optimal
    //
    // Intuition:
    // dp[i][j] → maximum coins from bursting balloons
    //            between i and j (inclusive)
    //
    // We build the solution by increasing the length
    // of the balloon interval.
    //
    // Transition:
    // dp[i][j] = max over k in [i..j]:
    //   dp[i][k-1] + dp[k+1][j]
    //   + arr[i-1] * arr[k] * arr[j+1]
    //
    // Time: O(n^3)
    // Space: O(n^2)
    // ---------------------------------------------------
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] updatedArr = new int[n + 2];
        updatedArr[0] = updatedArr[n + 2 - 1] = 1;
        for(int i = 0; i < n; i++)
            updatedArr[i + 1] = nums[i];
        int[][] dp = new int[n + 2][n + 2];
        for(int i = n; i >= 1; i--){
            for(int j = 1; j <= n; j++){
                if(i > j) continue;
                int maxCoins = 0;
                for (int k = i; k <= j; k++) {
                    int coins = updatedArr[i - 1] * updatedArr[k] * updatedArr[j + 1] + dp[i][k - 1] + dp[k + 1][j];
                    maxCoins = Math.max(maxCoins, coins);
                    dp[i][j] = maxCoins;
                }
            }
        }
        return dp[1][n];
    }

    // Another way
    public static int burstIII(int[] nums) {

        int n = nums.length;

        // Build new array with boundaries
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[n + 1] = 1;

        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];

        // length = number of balloons in the segment
        for (int len = 1; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {

                int j = i + len - 1;

                for (int k = i; k <= j; k++) {

                    int coins =
                            dp[i][k - 1]
                                    + dp[k + 1][j]
                                    + arr[i - 1] * arr[k] * arr[j + 1];

                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }

        return dp[1][n];
    }

    // ---------------------------------------------------
    // Space Optimized
    //
    // Intuition:
    // Space optimization is NOT possible here because
    // dp[i][j] depends on multiple overlapping ranges.
    //
    // O(n^2) space is optimal.
    // ---------------------------------------------------
    public static int burstIV(int[] nums) {
        return burstIII(nums);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[] nums = {3, 1, 5, 8};

        // Prepare array for recursion & memoization
        int[] arr = new int[nums.length + 2];
        arr[0] = 1;
        arr[arr.length - 1] = 1;
        for (int i = 0; i < nums.length; i++) {
            arr[i + 1] = nums[i];
        }

        // Recursion
        System.out.println("Recursion: "
                + burstI(1, nums.length, arr));

        // Memoization
        int[][] dp = new int[arr.length][arr.length];
        for (int[] row : dp)
            Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + burstII(1, nums.length, arr, dp));

        // Tabulation
        System.out.println("Tabulation: "
                + burstIII(nums));

        // Space Optimized
        System.out.println("Space Optimized: "
                + burstIV(nums));
    }
}
