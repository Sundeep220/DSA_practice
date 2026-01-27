package Problems.DynamicProgramming.Subsequences;


import java.util.Arrays;

public class Knapsack01 {
    // Problem: https://www.naukri.com/code360/problems/0-1-knapsack_920542
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: O(2^N)
    // Space: O(N) recursion stack
    // ---------------------------------------------------
    public static int knapsackI(int index, int capacity,
                                int[] weight, int[] value) {

        // Base case: only first item
        if (index == 0) {
            if (weight[0] <= capacity)
                return value[0];
            return 0;
        }

        // Not pick current item
        int notPick = knapsackI(index - 1, capacity, weight, value);

        // Pick current item (if possible)
        int pick = 0;
        if (weight[index] <= capacity) {
            pick = value[index] +
                    knapsackI(index - 1,
                            capacity - weight[index],
                            weight, value);
        }

        return Math.max(pick, notPick);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(N * W)
    // Space: O(N * W)
    // ---------------------------------------------------
    public static int knapsackII(int index, int capacity,
                                 int[] weight, int[] value,
                                 int[][] dp) {

        if (index == 0) {
            if (weight[0] <= capacity)
                return value[0];
            return 0;
        }

        if (dp[index][capacity] != -1)
            return dp[index][capacity];

        int notPick = knapsackII(index - 1, capacity, weight, value, dp);

        int pick = 0;
        if (weight[index] <= capacity) {
            pick = value[index] +
                    knapsackII(index - 1,
                            capacity - weight[index],
                            weight, value, dp);
        }

        dp[index][capacity] = Math.max(pick, notPick);
        return dp[index][capacity];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(N * W)
    // Space: O(N * W)
    // ---------------------------------------------------
    public static int knapsackIII(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[][] dp = new int[n][W + 1];

        // Base case
        for (int w = weight[0]; w <= W; w++) {
            dp[0][w] = value[0];
        }

        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= W; w++) {

                int notPick = dp[i - 1][w];

                int pick = 0;
                if (weight[i] <= w) {
                    pick = value[i] + dp[i - 1][w - weight[i]];
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
    public static int knapsackIV(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[] prev = new int[W + 1];

        // Base case
        for (int w = weight[0]; w <= W; w++) {
            prev[w] = value[0];
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[W + 1];
            for (int w = 0; w <= W; w++) {

                int notPick = prev[w];

                int pick = 0;
                if (weight[i] <= w) {
                    pick = value[i] + prev[w - weight[i]];
                }

                curr[w] = Math.max(pick, notPick);
            }
            prev = curr;
        }

        return prev[W];
    }

    // One more Optimization, to just 1 row,
    // Since from our above solution we can see
    // To compute curr[i] we require prev[i - wt] and prev[wt] , so basically we require previous row
    // Also the elements will be at the left of curr[i] as i - wt would be left side, so if we start computing
    // from end we are good to go as the left will be intact and
    public static int knapsackV(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[] prev = new int[W + 1];

        // Base case
        for (int w = weight[0]; w <= W; w++) {
            prev[w] = value[0];
        }

        for (int i = 1; i < n; i++) {
            for (int w = W; w >= 0; w--) {

                int notPick = prev[w];

                int pick = 0;
                if (weight[i] <= w) {
                    pick = value[i] + prev[w - weight[i]];
                }

                prev[w] = Math.max(pick, notPick);
            }
        }

        return prev[W];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] weight = {1, 2, 4, 5};
        int[] value = {5, 4, 8, 6};
        int W = 5;
        int n = weight.length;

        int[][] dp = new int[n][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Recursion: "
                + knapsackI(n - 1, W, weight, value));

        System.out.println("Memoization: "
                + knapsackII(n - 1, W, weight, value, dp));

        System.out.println("Tabulation: "
                + knapsackIII(weight, value, W));

        System.out.println("Space Optimized: "
                + knapsackIV(weight, value, W));
    }
}
