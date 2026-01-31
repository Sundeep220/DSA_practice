package Problems.DynamicProgramming.Stocks;

import java.util.Arrays;

public class BestTimeToBuyAndSellStockIII_TransactionNumber {

    // ---------------------------------------------------
    // Intutiton:
    // So for at most 2 transactions, we have 4 actions in total:
    //Transaction No:   Action
    //0                 BUY
    //1                 SELL
    //2                 BUY
    //3                 SELL
    // Brute Force: Pure Recursion
    // State: (day index, transaction number)
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int stockI(int i, int t, int[] prices) {

        // Base cases
        if (i == prices.length || t == 4)
            return 0;

        // BUY state (even transaction number)
        if (t % 2 == 0) {
            return Math.max(
                    -prices[i] + stockI(i + 1, t + 1, prices), // buy
                    stockI(i + 1, t, prices)                  // skip
            );
        }
        // SELL state (odd transaction number)
        else {
            return Math.max(
                    prices[i] + stockI(i + 1, t + 1, prices), // sell
                    stockI(i + 1, t, prices)                  // skip
            );
        }
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * 4)
    // Space: O(n * 4)
    // ---------------------------------------------------
    public static int stockII(int i, int t,
                              int[] prices, int[][] dp) {

        if (i == prices.length || t == 4)
            return 0;

        if (dp[i][t] != -1)
            return dp[i][t];

        if (t % 2 == 0) { // BUY
            dp[i][t] = Math.max(
                    -prices[i] + stockII(i + 1, t + 1, prices, dp),
                    stockII(i + 1, t, prices, dp)
            );
        } else { // SELL
            dp[i][t] = Math.max(
                    prices[i] + stockII(i + 1, t + 1, prices, dp),
                    stockII(i + 1, t, prices, dp)
            );
        }

        return dp[i][t];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * 4)
    // Space: O(n * 4)
    // ---------------------------------------------------
    public static int stockIII(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n + 1][5];

        // Base case already 0 when i == n or t == 4

        for (int i = n - 1; i >= 0; i--) {
            for (int t = 3; t >= 0; t--) {

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
    // Time: O(n * 4)
    // Space: O(4)
    // ---------------------------------------------------
    public static int stockIV(int[] prices) {
        int n = prices.length;

        int[] ahead = new int[5];

        for (int i = n - 1; i >= 0; i--) {
            int[] curr = new int[5];

            for (int t = 3; t >= 0; t--) {

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
        int[] prices = {3, 3, 5, 0, 0, 3, 1, 4};

        System.out.println("Recursion: "
                + stockI(0, 0, prices));

        int[][] dp = new int[prices.length][5];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + stockII(0, 0, prices, dp));

        System.out.println("Tabulation: "
                + stockIII(prices));

        System.out.println("Space Optimized: "
                + stockIV(prices));
    }
}
