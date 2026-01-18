package Problems.DynamicProgramming.Medium;

import java.util.Arrays;

public class HouseRobberII {

    // ---------------------------------------------------
    // Brute Force: Pure Recursion (for linear robber)
    // Time: O(2^N)
    // Space: O(N) recursion stack
    // ---------------------------------------------------
    private static int robBrute(int index, int[] arr) {
        if (index < 0) return 0;
        if (index == 0) return arr[0];

        int pick = arr[index] + robBrute(index - 2, arr);
        int notPick = robBrute(index - 1, arr);

        return Math.max(pick, notPick);
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Time: O(N)
    // Space: O(N)
    // ---------------------------------------------------
    private static int robMemo(int index, int[] arr, int[] dp) {
        if (index < 0) return 0;
        if (index == 0) return arr[0];

        if (dp[index] != -1) return dp[index];

        int pick = arr[index] + robMemo(index - 2, arr, dp);
        int notPick = robMemo(index - 1, arr, dp);

        dp[index] = Math.max(pick, notPick);
        return dp[index];
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Time: O(N)
    // Space: O(N)
    // ---------------------------------------------------
    private static int robTabulation(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        if (n == 1) return arr[0];

        int[] dp = new int[n];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(arr[i] + dp[i - 2], dp[i - 1]);
        }

        return dp[n - 1];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP (O(1))
    // Time: O(N)
    // Space: O(1)
    // ---------------------------------------------------
    private static int robOptimal(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        if (n == 1) return arr[0];

        int prev2 = arr[0];                 // dp[i-2]
        int prev1 = Math.max(arr[0], arr[1]); // dp[i-1]

        for (int i = 2; i < n; i++) {
            int curr = Math.max(arr[i] + prev2, prev1);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    // ---------------------------------------------------
    // Main Function: House Robber II (Circular)
    // ---------------------------------------------------
    public static int rob(int[] nums) {
        int n = nums.length;

        if (n == 1) return nums[0];

        // Case 1: Exclude last house
        int[] case1 = Arrays.copyOfRange(nums, 0, n - 1);

        // Case 2: Exclude first house
        int[] case2 = Arrays.copyOfRange(nums, 1, n);

        return Math.max(
                robOptimal(case1),
                robOptimal(case2)
        );
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] nums = {2, 3, 2};

        System.out.println("House Robber II Answer: " + rob(nums));
    }
}
