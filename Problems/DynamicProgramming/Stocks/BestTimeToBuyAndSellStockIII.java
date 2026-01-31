package Problems.DynamicProgramming.Stocks;

import java.util.Arrays;

public class BestTimeToBuyAndSellStockIII {

    // Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    public static int stockI(int i, int buy, int cap, int[] prices) {

        // Base cases
        if (i == prices.length || cap == 0)
            return 0;

        if (buy == 1) {
            return Math.max(
                    -prices[i] + stockI(i + 1, 0, cap, prices), // buy
                    stockI(i + 1, 1, cap, prices)               // skip
            );
        } else {
            return Math.max(
                    prices[i] + stockI(i + 1, 1, cap - 1, prices), // sell
                    stockI(i + 1, 0, cap, prices)                  // skip
            );
        }
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * 2 * 3)
    // Space: O(n * 2 * 3)
    // ---------------------------------------------------
    public static int stockII(int i, int buy, int cap,
                              int[] prices, int[][][] dp) {

        if (i == prices.length || cap == 0)
            return 0;

        if (dp[i][buy][cap] != -1)
            return dp[i][buy][cap];

        if (buy == 1) {
            dp[i][buy][cap] = Math.max(
                    -prices[i] + stockII(i + 1, 0, cap, prices, dp),
                    stockII(i + 1, 1, cap, prices, dp)
            );
        } else {
            dp[i][buy][cap] = Math.max(
                    prices[i] + stockII(i + 1, 1, cap - 1, prices, dp),
                    stockII(i + 1, 0, cap, prices, dp)
            );
        }

        return dp[i][buy][cap];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * 2 * 3)
    // Space: O(n * 2 * 3)
    // ---------------------------------------------------
    public static int stockIII(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][3];

        // Base case already 0 when i == n or cap == 0

        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= 2; cap++) {

                    if (buy == 1) {
                        dp[i][buy][cap] = Math.max(
                                -prices[i] + dp[i + 1][0][cap],
                                dp[i + 1][1][cap]
                        );
                    } else {
                        dp[i][buy][cap] = Math.max(
                                prices[i] + dp[i + 1][1][cap - 1],
                                dp[i + 1][0][cap]
                        );
                    }
                }
            }
        }

        return dp[0][1][2];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(n * 2 * 3)
    // Space: O(2 * 3)
    // ---------------------------------------------------
    public static int stockIV(int[] prices) {
        int n = prices.length;

        int[][] ahead = new int[2][3];

        for (int i = n - 1; i >= 0; i--) {

            int[][] curr = new int[2][3];  // ✅ new array every iteration

            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= 2; cap++) {

                    if (buy == 1) {
                        curr[buy][cap] = Math.max(
                                -prices[i] + ahead[0][cap],
                                ahead[1][cap]
                        );
                    } else {
                        curr[buy][cap] = Math.max(
                                prices[i] + ahead[1][cap - 1],
                                ahead[0][cap]
                        );
                    }
                }
            }

            ahead = curr;  // ✅ SAFE swap
        }

        return ahead[1][2];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] prices = {3, 3, 5, 0, 0, 3, 1, 4};

        System.out.println("Recursion: "
                + stockI(0, 1, 2, prices));

        int[][][] dp = new int[prices.length][2][3];
        for (int[][] mat : dp)
            for (int[] row : mat)
                Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + stockII(0, 1, 2, prices, dp));

        System.out.println("Tabulation: "
                + stockIII(prices));

        System.out.println("Space Optimized: "
                + stockIV(prices));
    }
}
