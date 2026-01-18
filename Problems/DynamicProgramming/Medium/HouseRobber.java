package Problems.DynamicProgramming.Medium;

import java.util.Arrays;

public class HouseRobber {
    // Problem: https://leetcode.com/problems/house-robber/description/

        // ---------------------------------------------------
        // Brute Force: Pure Recursion
        // At every index, choose pick or not pick
        // Time: O(2^N)
        // Space: O(N) recursion stack
        // ---------------------------------------------------
        public static int maxSumI(int index, int[] arr) {
            if (index == 0) return arr[0];
            if (index < 0) return 0;

            int pick = arr[index] + maxSumI(index - 2, arr);
            int notPick = maxSumI(index - 1, arr);

            return Math.max(pick, notPick);
        }

        // ---------------------------------------------------
        // Better: Recursion + Memoization (Top-Down DP)
        // Store results of overlapping subproblems
        // Time: O(N)
        // Space: O(N) dp array + recursion stack
        // ---------------------------------------------------
        public static int maxSumII(int index, int[] arr, int[] dp) {
            if (index == 0) return arr[0];
            if (index < 0) return 0;

            if (dp[index] != -1) return dp[index];

            int pick = arr[index] + maxSumII(index - 2, arr, dp);
            int notPick = maxSumII(index - 1, arr, dp);

            dp[index] = Math.max(pick, notPick);
            return dp[index];
        }

        // ---------------------------------------------------
        // Better: Tabulation (Bottom-Up DP)
        // Iteratively compute dp[] from left to right
        // Time: O(N)
        // Space: O(N)
        // ---------------------------------------------------
        public static int maxSumIII(int[] arr) {
            int n = arr.length;
            if (n == 0) return 0;
            if (n == 1) return arr[0];

            int[] dp = new int[n];
            dp[0] = arr[0];
            dp[1] = Math.max(arr[0], arr[1]);

            for (int i = 2; i < n; i++) {
                int pick = arr[i] + dp[i - 2];
                int notPick = dp[i - 1];
                dp[i] = Math.max(pick, notPick);
            }

            return dp[n - 1];
        }

        // ---------------------------------------------------
        // Most Optimal: Space Optimized DP
        // Only last two states are needed
        // Time: O(N)
        // Space: O(1)
        // ---------------------------------------------------
        public static int maxSumIV(int[] arr) {
            int n = arr.length;
            if (n == 0) return 0;
            if (n == 1) return arr[0];

            int prev2 = arr[0];                 // dp[i-2]
            int prev1 = Math.max(arr[0], arr[1]); // dp[i-1]

            for (int i = 2; i < n; i++) {
                int pick = arr[i] + prev2;
                int notPick = prev1;

                int curr = Math.max(pick, notPick);
                prev2 = prev1;
                prev1 = curr;
            }

            return prev1;
        }

        // ---------------------------------------------------
        // Driver Code
        // ---------------------------------------------------
        public static void main(String[] args) {
            int[] arr = {2, 1, 4, 9};
            int n = arr.length;

            int[] dp = new int[n];
            Arrays.fill(dp, -1);

            System.out.println("Brute Force Recursion: " + maxSumI(n - 1, arr));
            System.out.println("Memoization: " + maxSumII(n - 1, arr, dp));
            System.out.println("Tabulation: " + maxSumIII(arr));
            System.out.println("Space Optimized DP: " + maxSumIV(arr));
        }
}
