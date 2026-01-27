package Problems.DynamicProgramming.Subsequences;



import java.util.Arrays;

public class CoinChange {

    static final int INF = (int) 1e9;

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential (very large)
    // Space: O(amount) recursion stack
    // ---------------------------------------------------
    public static int coinChangeI(int index, int amount, int[] coins) {

        // Base case: only coin[0] available
        if (index == 0) {
            if (amount % coins[0] == 0)
                return amount / coins[0];
            return INF;
        }

        // Not pick the coin
        int notPick = coinChangeI(index - 1, amount, coins);

        // Pick the coin (stay at same index)
        int pick = INF;
        if (coins[index] <= amount) {
            pick = 1 + coinChangeI(index, amount - coins[index], coins);
        }

        return Math.min(pick, notPick);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(N * amount)
    // Space: O(N * amount)
    // ---------------------------------------------------
    public static int coinChangeII(int index, int amount,
                                   int[] coins, int[][] dp) {

        if (index == 0) {
            if (amount % coins[0] == 0)
                return amount / coins[0];
            return INF;
        }

        if (dp[index][amount] != -1)
            return dp[index][amount];

        int notPick = coinChangeII(index - 1, amount, coins, dp);

        int pick = INF;
        if (coins[index] <= amount) {
            pick = 1 + coinChangeII(index, amount - coins[index], coins, dp);
        }

        dp[index][amount] = Math.min(pick, notPick);
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

        // Base case initialization
        for (int a = 0; a <= amount; a++) {
            if (a % coins[0] == 0)
                dp[0][a] = a / coins[0];
            else
                dp[0][a] = INF;
        }

        for (int i = 1; i < n; i++) {
            for (int a = 0; a <= amount; a++) {

                int notPick = dp[i - 1][a];

                int pick = INF;
                if (coins[i] <= a) {
                    pick = 1 + dp[i][a - coins[i]];
                }

                dp[i][a] = Math.min(pick, notPick);
            }
        }

        int ans = dp[n - 1][amount];
        return ans >= INF ? -1 : ans;
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
                prev[a] = a / coins[0];
            else
                prev[a] = INF;
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[amount + 1];
            for (int a = 0; a <= amount; a++) {

                int notPick = prev[a];

                int pick = INF;
                if (coins[i] <= a) {
                    pick = 1 + curr[a - coins[i]];
                }

                curr[a] = Math.min(pick, notPick);
            }
            prev = curr;
        }

        return prev[amount] >= INF ? -1 : prev[amount];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        int n = coins.length;

        int[][] dp = new int[n][amount + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        int res1 = coinChangeI(n - 1, amount, coins);
        System.out.println("Recursion: " + (res1 >= INF ? -1 : res1));

        int res2 = coinChangeII(n - 1, amount, coins, dp);
        System.out.println("Memoization: " + (res2 >= INF ? -1 : res2));

        System.out.println("Tabulation: " + coinChangeIII(coins, amount));
        System.out.println("Space Optimized: " + coinChangeIV(coins, amount));
    }
}
