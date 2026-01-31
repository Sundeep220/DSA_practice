package Problems.DynamicProgramming.Stocks;

import java.util.Arrays;

public class BestTimeToBuyAndSellStockIV {

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // State: (day index, transaction number)
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int stockI(int i, int t, int[] prices, int k) {

        if (i == prices.length || t == 2 * k)
            return 0;

        // BUY state
        if (t % 2 == 0) {
            return Math.max(
                    -prices[i] + stockI(i + 1, t + 1, prices, k),
                    stockI(i + 1, t, prices, k)
            );
        }
        // SELL state
        else {
            return Math.max(
                    prices[i] + stockI(i + 1, t + 1, prices, k),
                    stockI(i + 1, t, prices, k)
            );
        }
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * 2k)
    // Space: O(n * 2k)
    // ---------------------------------------------------
    public static int stockII(int i, int t,
                              int[] prices, int k,
                              int[][] dp) {

        if (i == prices.length || t == 2 * k)
            return 0;

        if (dp[i][t] != -1)
            return dp[i][t];

        if (t % 2 == 0) { // BUY
            dp[i][t] = Math.max(
                    -prices[i] + stockII(i + 1, t + 1, prices, k, dp),
                    stockII(i + 1, t, prices, k, dp)
            );
        } else { // SELL
            dp[i][t] = Math.max(
                    prices[i] + stockII(i + 1, t + 1, prices, k, dp),
                    stockII(i + 1, t, prices, k, dp)
            );
        }

        return dp[i][t];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * 2k)
    // Space: O(n * 2k)
    // ---------------------------------------------------
    public static int stockIII(int[] prices, int k) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2 * k + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int t = 2 * k - 1; t >= 0; t--) {

                if (t % 2 == 0) { // BUY
                    dp[i][t] = Math.max(
                            -prices[i] + dp[i + 1][t + 1],
                            dp[i + 1][t]
                    );
                } else { // SELL
                    dp[i][t] = Math.max(
                            prices[i] + dp[i + 1][t + 1],
                            dp[i + 1][t]
                    );
                }
            }
        }

        return dp[0][0];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(n * 2k)
    // Space: O(2k)
    // ---------------------------------------------------
    public static int stockIV(int[] prices, int k) {
        int n = prices.length;

        int[] ahead = new int[2 * k + 1];

        for (int i = n - 1; i >= 0; i--) {
            int[] curr = new int[2 * k + 1];

            for (int t = 2 * k - 1; t >= 0; t--) {

                if (t % 2 == 0) { // BUY
                    curr[t] = Math.max(
                            -prices[i] + ahead[t + 1],
                            ahead[t]
                    );
                } else { // SELL
                    curr[t] = Math.max(
                            prices[i] + ahead[t + 1],
                            ahead[t]
                    );
                }
            }
            ahead = curr; // safe swap (new array)
        }

        return ahead[0];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] prices = {3, 2, 6, 5, 0, 3};
        int k = 2;

        System.out.println("Recursion: "
                + stockI(0, 0, prices, k));

        int[][] dp = new int[prices.length][2 * k];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + stockII(0, 0, prices, k, dp));

        System.out.println("Tabulation: "
                + stockIII(prices, k));

        System.out.println("Space Optimized: "
                + stockIV(prices, k));
    }
}
