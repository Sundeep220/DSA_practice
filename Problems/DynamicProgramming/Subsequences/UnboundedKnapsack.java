package Problems.DynamicProgramming.Subsequences;

import java.util.Arrays;

public class UnboundedKnapsack {

    // Problem: https://www.naukri.com/code360/problems/unbounded-knapsack_1215029
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(W) recursion stack
    // ---------------------------------------------------
    public static int knapsackI(int index, int capacity,
                                int[] weight, int[] profit) {

        // Base case: only item 0 available
        if (index == 0) {
            return (capacity / weight[0]) * profit[0];
        }

        // Not pick current item
        int notPick = knapsackI(index - 1, capacity, weight, profit);

        // Pick current item (unbounded → same index)
        int pick = 0;
        if (weight[index] <= capacity) {
            pick = profit[index] +
                    knapsackI(index,
                            capacity - weight[index],
                            weight, profit);
        }

        return Math.max(pick, notPick);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(N * W)
    // Space: O(N * W)
    // ---------------------------------------------------
    public static int knapsackII(int index, int capacity,
                                 int[] weight, int[] profit,
                                 int[][] dp) {

        if (index == 0) {
            return (capacity / weight[0]) * profit[0];
        }

        if (dp[index][capacity] != -1)
            return dp[index][capacity];

        int notPick = knapsackII(index - 1, capacity, weight, profit, dp);

        int pick = 0;
        if (weight[index] <= capacity) {
            pick = profit[index] +
                    knapsackII(index,
                            capacity - weight[index],
                            weight, profit, dp);
        }

        dp[index][capacity] = Math.max(pick, notPick);
        return dp[index][capacity];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(N * W)
    // Space: O(N * W)
    // ---------------------------------------------------
    public static int knapsackIII(int[] weight, int[] profit, int W) {
        int n = weight.length;
        int[][] dp = new int[n][W + 1];

        // Base case: only item 0
        for (int w = 0; w <= W; w++) {
            dp[0][w] = (w / weight[0]) * profit[0];
        }

        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= W; w++) {

                int notPick = dp[i - 1][w];

                int pick = 0;
                if (weight[i] <= w) {
                    pick = profit[i] + dp[i][w - weight[i]];
                }

                dp[i][w] = Math.max(pick, notPick);
            }
        }

        return dp[n - 1][W];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(N * W)
    // Space: O(W)
    // ---------------------------------------------------
    public static int knapsackIV(int[] weight, int[] profit, int W) {
        int n = weight.length;
        int[] prev = new int[W + 1];

        // Base case
        for (int w = 0; w <= W; w++) {
            prev[w] = (w / weight[0]) * profit[0];
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[W + 1];
            for (int w = 0; w <= W; w++) {

                int notPick = prev[w];

                int pick = 0;
                if (weight[i] <= w) {
                    pick = profit[i] + curr[w - weight[i]];
                }

                curr[w] = Math.max(pick, notPick);
            }
            prev = curr;
        }

        return prev[W];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] weight = {2, 4, 6};
        int[] profit = {5, 11, 13};
        int W = 10;
        int n = weight.length;

        int[][] dp = new int[n][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Recursion: "
                + knapsackI(n - 1, W, weight, profit));

        System.out.println("Memoization: "
                + knapsackII(n - 1, W, weight, profit, dp));

        System.out.println("Tabulation: "
                + knapsackIII(weight, profit, W));

        System.out.println("Space Optimized: "
                + knapsackIV(weight, profit, W));
    }
}

