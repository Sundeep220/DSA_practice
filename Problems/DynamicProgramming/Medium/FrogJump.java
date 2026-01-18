package Problems.DynamicProgramming.Medium;

import java.util.Arrays;

public class FrogJump {

    // Problem: https://www.naukri.com/code360/problems/frog-jump_3621012

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all paths recursively
    // Time: O(2^n)
    // Space: O(n) -> recursion stack
    // ---------------------------------------------------
    public static int frogJumpI(int index, int[] height) {
        if (index == 0) return 0;

        int oneStep = frogJumpI(index - 1, height)
                + Math.abs(height[index] - height[index - 1]);

        int twoStep = Integer.MAX_VALUE;
        if (index > 1) {
            twoStep = frogJumpI(index - 2, height)
                    + Math.abs(height[index] - height[index - 2]);
        }

        return Math.min(oneStep, twoStep);
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Avoid recomputation using dp array
    // Time: O(n)
    // Space: O(n) -> dp array + recursion stack
    // ---------------------------------------------------
    public static int frogJumpII(int index, int[] height, int[] dp) {
        if (index == 0) return 0;

        if (dp[index] != -1) return dp[index];

        int oneStep = frogJumpII(index - 1, height, dp)
                + Math.abs(height[index] - height[index - 1]);

        int twoStep = Integer.MAX_VALUE;
        if (index > 1) {
            twoStep = frogJumpII(index - 2, height, dp)
                    + Math.abs(height[index] - height[index - 2]);
        }

        dp[index] = Math.min(oneStep, twoStep);
        return dp[index];
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build solution iteratively
    // Time: O(n)
    // Space: O(n) -> dp array
    // ---------------------------------------------------
    public static int frogJumpIII(int[] height) {
        int n = height.length;
        int[] dp = new int[n];

        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            int oneStep = dp[i - 1] + Math.abs(height[i] - height[i - 1]);

            int twoStep = Integer.MAX_VALUE;
            if (i > 1) {
                twoStep = dp[i - 2] + Math.abs(height[i] - height[i - 2]);
            }

            dp[i] = Math.min(oneStep, twoStep);
        }

        return dp[n - 1];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Only previous two states are required
    // Time: O(n)
    // Space: O(1)
    // ---------------------------------------------------
    public static int frogJumpIV(int[] height) {
        int n = height.length;

        int prev2 = 0; // dp[i-2]
        int prev1 = 0; // dp[i-1]

        for (int i = 1; i < n; i++) {
            int oneStep = prev1 + Math.abs(height[i] - height[i - 1]);

            int twoStep = Integer.MAX_VALUE;
            if (i > 1) {
                twoStep = prev2 + Math.abs(height[i] - height[i - 2]);
            }

            int curr = Math.min(oneStep, twoStep);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] height = {10, 20, 30, 10};
        int n = height.length;

        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        System.out.println("Brute Force Recursion: " + frogJumpI(n - 1, height));
        System.out.println("Memoization: " + frogJumpII(n - 1, height, dp));
        System.out.println("Tabulation: " + frogJumpIII(height));
        System.out.println("Space Optimized DP: " + frogJumpIV(height));
    }
}
