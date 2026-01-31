package Problems.DynamicProgramming.Stocks;

import java.util.Arrays;

public class BestTimeToBuyAndSellStockWithCooldown {

    // Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // State: (day index, buy)
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int stockI(int i, int buy, int[] prices) {

        if (i >= prices.length)
            return 0;

        // BUY allowed
        if (buy == 1) {
            return Math.max(
                    -prices[i] + stockI(i + 1, 0, prices), // buy
                    stockI(i + 1, 1, prices)               // skip
            );
        }
        // SELL allowed (cooldown applies)
        else {
            return Math.max(
                    prices[i] + stockI(i + 2, 1, prices), // sell → cooldown
                    stockI(i + 1, 0, prices)               // skip
            );
        }
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * 2)
    // Space: O(n * 2)
    // ---------------------------------------------------
    public static int stockII(int i, int buy,
                              int[] prices, int[][] dp) {

        if (i >= prices.length)
            return 0;

        if (dp[i][buy] != -1)
            return dp[i][buy];

        if (buy == 1) {
            dp[i][buy] = Math.max(
                    -prices[i] + stockII(i + 1, 0, prices, dp),
                    stockII(i + 1, 1, prices, dp)
            );
        } else {
            dp[i][buy] = Math.max(
                    prices[i] + stockII(i + 2, 1, prices, dp),
                    stockII(i + 1, 0, prices, dp)
            );
        }

        return dp[i][buy];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * 2)
    // Space: O(n * 2)
    // ---------------------------------------------------
    public static int stockIII(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n + 2][2]; // n+2 to handle i+2 safely

        for (int i = n - 1; i >= 0; i--) {

            // buy == 1
            dp[i][1] = Math.max(
                    -prices[i] + dp[i + 1][0],
                    dp[i + 1][1]
            );

            // buy == 0 (sell → cooldown)
            dp[i][0] = Math.max(
                    prices[i] + dp[i + 2][1],
                    dp[i + 1][0]
            );
        }

        return dp[0][1];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(n)
    // Space: O(1)
    // ---------------------------------------------------
    public static int stockIV(int[] prices) {
        int n = prices.length;

        int aheadBuy = 0, aheadSell = 0;
        int ahead2Buy = 0; // dp[i+2][1]

        for (int i = n - 1; i >= 0; i--) {

            int currBuy = Math.max(
                    -prices[i] + aheadSell,
                    aheadBuy
            );

            int currSell = Math.max(
                    prices[i] + ahead2Buy,
                    aheadSell
            );

            // shift states
            ahead2Buy = aheadBuy;
            aheadBuy = currBuy;
            aheadSell = currSell;
        }

        return aheadBuy;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] prices = {1, 2, 3, 0, 2};

        System.out.println("Recursion: "
                + stockI(0, 1, prices));

        int[][] dp = new int[prices.length][2];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + stockII(0, 1, prices, dp));

        System.out.println("Tabulation: "
                + stockIII(prices));

        System.out.println("Space Optimized: "
                + stockIV(prices));
    }
}
