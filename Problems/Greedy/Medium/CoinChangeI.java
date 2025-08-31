package Problems.Greedy.Medium;

import java.util.Arrays;

public class CoinChangeI {
    // Problem: https://leetcode.com/problems/coin-change/

    // Brute Force: Try all possible combinations
    // Time Complexity: O(n ^ amount) time | O(n) space
    public static int coinChange(int[] coins, int amount) {
            int result = helper(coins, amount);
            return (result == Integer.MAX_VALUE) ? -1 : result;
    }

    private static int helper(int[] coins, int amount) {
        if (amount == 0) return 0;   // no coins needed
        if (amount < 0) return Integer.MAX_VALUE; // impossible

        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = helper(coins, amount - coin);
            if (res != Integer.MAX_VALUE) {
                min = Math.min(min, res + 1);
            }
        }
        return min;
    }

    // Optimal Solution: Dynamic Programming Memoization
    // Time Complexity: O(n * amount) time | O(amount) space
    public int coinChangeDP(int[] coins, int amount) {
        // memo array: -2 means not computed, -1 means impossible
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, -2);
        return helper(coins, amount, dp);
    }

    private int helper(int[] coins, int amount, int[] dp) {
        if (amount == 0) return 0;     // no coins needed
        if (amount < 0) return -1;     // not possible

        if (dp[amount] != -2) return dp[amount]; // already computed

        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = helper(coins, amount - coin, dp);
            if (res >= 0) {  // valid solution
                min = Math.min(min, res + 1);
            }
        }

        dp[amount] = (min == Integer.MAX_VALUE) ? -1 : min;
        return dp[amount];
    }
}