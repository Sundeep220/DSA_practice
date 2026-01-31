package Problems.DynamicProgramming.Stocks;

import java.util.Arrays;

public class BestTimeToBuyAndSellStockII {

    // Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int stockI(int i, int buy, int[] prices) {

        // Base case
        if (i == prices.length)
            return 0;

        if (buy == 1) {
            return Math.max(
                    -prices[i] + stockI(i + 1, 0, prices), // buy
                    stockI(i + 1, 1, prices)               // skip
            );
        } else {
            return Math.max(
                    prices[i] + stockI(i + 1, 1, prices), // sell
                    stockI(i + 1, 0, prices)               // skip
            );
        }
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * 2)
    // Space: O(n * 2)
    // ---------------------------------------------------
    public static int stockII(int i, int buy, int[] prices, int[][] dp) {

        if (i == prices.length)
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
                    prices[i] + stockII(i + 1, 1, prices, dp),
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
        int[][] dp = new int[n + 1][2];

        // Base case: dp[n][0] = dp[n][1] = 0

        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {

                if (buy == 1) {
                    dp[i][buy] = Math.max(
                            -prices[i] + dp[i + 1][0],
                            dp[i + 1][1]
                    );
                } else {
                    dp[i][buy] = Math.max(
                            prices[i] + dp[i + 1][1],
                            dp[i + 1][0]
                    );
                }
            }
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
        int curBuy, curSell;

        for (int i = n - 1; i >= 0; i--) {

            curBuy = Math.max(
                    -prices[i] + aheadSell,
                    aheadBuy
            );

            curSell = Math.max(
                    prices[i] + aheadBuy,
                    aheadSell
            );

            aheadBuy = curBuy;
            aheadSell = curSell;
        }

        return aheadBuy;
    }


    // Greedy Solution
    public static int maxProfit(int[] prices) {
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }

        return profit;
    }


    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};

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

        System.out.println("Greedy: "
                + maxProfit(prices));
    }
}
