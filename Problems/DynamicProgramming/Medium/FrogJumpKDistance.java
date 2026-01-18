package Problems.DynamicProgramming.Medium;

import java.util.Arrays;

public class FrogJumpKDistance {

    // Follow up for FrogJump Problem, what if the frog was allowed to jump upto K distance instead of just 1 and 2


    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all possible jumps from 1 to K
    // Time: O(K^N) (exponential)
    // Space: O(N) recursion stack
    // ---------------------------------------------------
    public static int frogJumpI(int index, int[] heights, int k) {
        if (index == 0) return 0;

        int minEnergy = Integer.MAX_VALUE;

        for (int jump = 1; jump <= k; jump++) {
            if (index - jump >= 0) {
                int energy = frogJumpI(index - jump, heights, k)
                        + Math.abs(heights[index] - heights[index - jump]);
                minEnergy = Math.min(minEnergy, energy);
            }
        }

        return minEnergy;
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Store results of subproblems
    // Time: O(N * K)
    // Space: O(N) dp array + recursion stack
    // ---------------------------------------------------
    public static int frogJumpII(int index, int[] heights, int k, int[] dp) {
        if (index == 0) return 0;

        if (dp[index] != -1) return dp[index];

        int minEnergy = Integer.MAX_VALUE;

        for (int jump = 1; jump <= k; jump++) {
            if (index - jump >= 0) {
                int energy = frogJumpII(index - jump, heights, k, dp)
                        + Math.abs(heights[index] - heights[index - jump]);
                minEnergy = Math.min(minEnergy, energy);
            }
        }

        dp[index] = minEnergy;
        return dp[index];
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Iteratively compute dp[] from 0 to n-1
    // Time: O(N * K)
    // Space: O(N)
    // ---------------------------------------------------
    public static int frogJumpIII(int[] heights, int k) {
        int n = heights.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            for (int jump = 1; jump <= k; jump++) {
                if (i - jump >= 0) {
                    dp[i] = Math.min(
                            dp[i],
                            dp[i - jump] + Math.abs(heights[i] - heights[i - jump])
                    );
                }
            }
        }

        return dp[n - 1];
    }

    // ---------------------------------------------------
    // Space Optimized DP using Sliding Window
    // Time: O(N * K)
    // Space: O(K)
    // ---------------------------------------------------
    public static int frogJumpOptimized(int[] heights, int k) {
        int n = heights.length;

        // Circular buffer of size k
        int[] window = new int[k];
        window[0] = 0; // dp[0]

        for (int i = 1; i < n; i++) {
            int minEnergy = Integer.MAX_VALUE;

            for (int jump = 1; jump <= k; jump++) {
                if (i - jump >= 0) {
                    int prevIndex = (i - jump) % k;
                    int energy =
                            window[prevIndex]
                                    + Math.abs(heights[i] - heights[i - jump]);
                    minEnergy = Math.min(minEnergy, energy);
                }
            }

            window[i % k] = minEnergy;
        }

        return window[(n - 1) % k];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] heights = {10, 20, 30, 10};
        int k = 3;
        int n = heights.length;

        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        System.out.println("Brute Force Recursion: "
                + frogJumpI(n - 1, heights, k));

        System.out.println("Memoization: "
                + frogJumpII(n - 1, heights, k, dp));

        System.out.println("Tabulation: "
                + frogJumpIII(heights, k));

        System.out.println("O(K) Space Optimized Answer: "
                + frogJumpOptimized(heights, k));
    }
}
