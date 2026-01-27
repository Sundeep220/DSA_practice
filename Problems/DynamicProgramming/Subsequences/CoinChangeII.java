package Problems.DynamicProgramming.Subsequences;


import java.util.Arrays;

public class CoinChangeII {

    // Problem: https://leetcode.com/problems/coin-change-ii/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(amount) recursion stack
    // ---------------------------------------------------
    public static int coinChangeI(int index, int amount, int[] coins) {

        // Base case: only coin[0]
        if (index == 0) {
            return (amount % coins[0] == 0) ? 1 : 0;
        }

        // Not pick current coin
        int notPick = coinChangeI(index - 1, amount, coins);

        // Pick current coin (unbounded → same index)
        int pick = 0;
        if (coins[index] <= amount) {
            pick = coinChangeI(index, amount - coins[index], coins);
        }

        return pick + notPick;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(N * amount)
    // Space: O(N * amount)
    // ---------------------------------------------------
    public static int coinChangeII(int index, int amount,
                                   int[] coins, int[][] dp) {

        if (index == 0) {
            return (amount % coins[0] == 0) ? 1 : 0;
        }

        if (dp[index][amount] != -1)
            return dp[index][amount];

        int notPick = coinChangeII(index - 1, amount, coins, dp);

        int pick = 0;
        if (coins[index] <= amount) {
            pick = coinChangeII(index, amount - coins[index], coins, dp);
        }

        dp[index][amount] = pick + notPick;
        return dp[index][amount];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(N * amount)
    // Space: O(N * amount)
    // ---------------------------------------------------
    public static int coinChangeIII(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];

        // Base case
        for (int a = 0; a <= amount; a++) {
            if (a % coins[0] == 0)
                dp[0][a] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int a = 0; a <= amount; a++) {

                int notPick = dp[i - 1][a];

                int pick = 0;
                if (coins[i] <= a) {
                    pick = dp[i][a - coins[i]];
                }

                dp[i][a] = pick + notPick;
            }
        }

        return dp[n - 1][amount];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(N * amount)
    // Space: O(amount)
    // ---------------------------------------------------
    public static int coinChangeIV(int[] coins, int amount) {
        int n = coins.length;
        int[] prev = new int[amount + 1];

        // Base case
        for (int a = 0; a <= amount; a++) {
            if (a % coins[0] == 0)
                prev[a] = 1;
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[amount + 1];
            for (int a = 0; a <= amount; a++) {

                int notPick = prev[a];

                int pick = 0;
                if (coins[i] <= a) {
                    pick = curr[a - coins[i]];
                }

                curr[a] = pick + notPick;
            }
            prev = curr;
        }

        return prev[amount];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 5;
        int n = coins.length;

        int[][] dp = new int[n][amount + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Recursion: "
                + coinChangeI(n - 1, amount, coins));

        System.out.println("Memoization: "
                + coinChangeII(n - 1, amount, coins, dp));

        System.out.println("Tabulation: "
                + coinChangeIII(coins, amount));

        System.out.println("Space Optimized: "
                + coinChangeIV(coins, amount));
    }
}

