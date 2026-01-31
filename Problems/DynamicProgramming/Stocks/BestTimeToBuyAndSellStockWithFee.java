package Problems.DynamicProgramming.Stocks;

import java.util.Arrays;

public class BestTimeToBuyAndSellStockWithFee {

    // Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // State: (day index, buy)
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int stockI(int i, int buy, int[] prices, int fee) {

        if (i == prices.length)
            return 0;

        // BUY allowed
        if (buy == 1) {
            return Math.max(
                    -prices[i] + stockI(i + 1, 0, prices, fee), // buy
                    stockI(i + 1, 1, prices, fee)               // skip
            );
        }
        // SELL allowed (fee applied)
        else {
            return Math.max(
                    prices[i] - fee + stockI(i + 1, 1, prices, fee), // sell
                    stockI(i + 1, 0, prices, fee)                     // skip
            );
        }
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * 2)
    // Space: O(n * 2)
    // ---------------------------------------------------
    public static int stockII(int i, int buy,
                              int[] prices, int fee,
                              int[][] dp) {

        if (i == prices.length)
            return 0;

        if (dp[i][buy] != -1)
            return dp[i][buy];

        if (buy == 1) {
            dp[i][buy] = Math.max(
                    -prices[i] + stockII(i + 1, 0, prices, fee, dp),
                    stockII(i + 1, 1, prices, fee, dp)
            );
        } else {
            dp[i][buy] = Math.max(
                    prices[i] - fee + stockII(i + 1, 1, prices, fee, dp),
                    stockII(i + 1, 0, prices, fee, dp)
            );
        }

        return dp[i][buy];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * 2)
    // Space: O(n * 2)
    // ---------------------------------------------------
    public static int stockIII(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];

        for (int i = n - 1; i >= 0; i--) {

            // buy == 1
            dp[i][1] = Math.max(
                    -prices[i] + dp[i + 1][0],
                    dp[i + 1][1]
            );

            // buy == 0
            dp[i][0] = Math.max(
                    prices[i] - fee + dp[i + 1][1],
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
    public static int stockIV(int[] prices, int fee) {
        int n = prices.length;

        int aheadBuy = 0;
        int aheadSell = 0;

        for (int i = n - 1; i >= 0; i--) {

            int currBuy = Math.max(
                    -prices[i] + aheadSell,
                    aheadBuy
            );

            int currSell = Math.max(
                    prices[i] - fee + aheadBuy,
                    aheadSell
            );

            aheadBuy = currBuy;
            aheadSell = currSell;
        }

        return aheadBuy;
    }

    // Greedy Approach
    // cash → max profit when NOT holding a stock
    //hold → max profit when HOLDING a stock
    // cash represents state after selling today
    //hold uses updated cash to represent buying today
    // Time: O(n)
    // Space: O(1)
    public int maxProfit(int[] prices, int fee) {
        int cash = 0;
        int hold = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i] - fee); // sell
            hold = Math.max(hold, cash - prices[i]); // buy
        }

        return cash;
    }


    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] prices = {1, 3, 2, 8, 4, 9};
        int fee = 2;

        System.out.println("Recursion: "
                + stockI(0, 1, prices, fee));

        int[][] dp = new int[prices.length][2];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + stockII(0, 1, prices, fee, dp));

        System.out.println("Tabulation: "
                + stockIII(prices, fee));

        System.out.println("Space Optimized: "
                + stockIV(prices, fee));
    }
}
