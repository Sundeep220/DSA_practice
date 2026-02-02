package Problems.DynamicProgramming.Partitions;

import java.util.Arrays;

public class MinimumCostToCutStick {

    // ---------------------------------------------------
    // Problem:
    // 1547. Minimum Cost to Cut a Stick
    //
    // Given a stick of length n and an array cuts[],
    // each cut costs the length of the current stick.
    //
    // We can choose the order of cuts.
    // Goal: Minimize total cost.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Preprocessing Intuition (VERY IMPORTANT):
    //
    // 1. Add boundaries:
    //    - Add 0 (start of stick)
    //    - Add n (end of stick)
    //
    // 2. Sort cuts
    //
    // After this:
    // cuts[i] and cuts[j] define a segment
    // whose length = cuts[j] - cuts[i]
    //
    // This converts the problem into
    // a Matrix Chain Multiplication–style DP.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    //
    // Intuition:
    // We try every possible cut between indices i and j.
    //
    // If we cut at k (i < k < j):
    // Cost =
    //   cost(i..k) +
    //   cost(k..j) +
    //   (cuts[j] - cuts[i])
    //
    // State:
    // f(i, j) → minimum cost to cut stick
    //           between cuts[i] and cuts[j]
    //
    // Base case:
    // If j - i <= 1 → no cut possible → cost = 0
    //
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    public static int minCostI(int i, int j, int[] cuts) {

        if (j - i <= 1)
            return 0;

        int minCost = Integer.MAX_VALUE;

        for (int k = i + 1; k < j; k++) {

            int cost = minCostI(i, k, cuts) + minCostI(k, j, cuts) + (cuts[j] - cuts[i]);

            minCost = Math.min(minCost, cost);
        }

        return minCost;
    }

    // Another way of writing Recursion
    public int helper(int i, int j, int[] cuts, int[][] dp){
        if(i > j) return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        int minCost = Integer.MAX_VALUE;
        for(int k = i; k <= j; k++){

            int cost = cuts[j + 1] - cuts[i - 1] + helper(i , k - 1, cuts, dp) + helper(k + 1, j, cuts, dp);
            minCost = Math.min(minCost, cost);
        }

        dp[i][j] = minCost;
        return minCost;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    //
    // Intuition:
    // The recursive solution recomputes the same
    // (i, j) segments again and again.
    //
    // So we store results in dp[i][j].
    //
    // Time: O(m^3)
    // Space: O(m^2)
    // where m = number of cuts + 2
    // ---------------------------------------------------
    public static int minCostII(int i, int j,
                                int[] cuts, int[][] dp) {

        if (j - i <= 1)
            return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        int minCost = Integer.MAX_VALUE;

        for (int k = i + 1; k < j; k++) {

            int cost =
                    minCostII(i, k, cuts, dp)
                            + minCostII(k, j, cuts, dp)
                            + (cuts[j] - cuts[i]);

            minCost = Math.min(minCost, cost);
        }

        dp[i][j] = minCost;
        return minCost;
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP) — Optimal
    //
    // Intuition:
    // dp[i][j] → minimum cost to cut stick
    //            between cuts[i] and cuts[j]
    //
    // We increase the length of the segment gradually.
    //
    // Base case:
    // dp[i][i+1] = 0 (no cut possible)
    //
    // Transition:
    // dp[i][j] = min over k:
    //   dp[i][k] + dp[k][j] + (cuts[j] - cuts[i])
    //
    // Time: O(m^3)
    // Space: O(m^2)
    // ---------------------------------------------------
    public static int minCostIII(int n, int[] originalCuts) {

        int m = originalCuts.length;

        // Step 1: Prepare cuts array with boundaries
        int[] cuts = new int[m + 2];
        cuts[0] = 0;
        cuts[m + 1] = n;

        for (int i = 0; i < m; i++) {
            cuts[i + 1] = originalCuts[i];
        }

        Arrays.sort(cuts);

        // Step 2: DP table
        int[][] dp = new int[m + 2][m + 2];

        // length = distance between indices
        for (int len = 2; len < m + 2; len++) {
            for (int i = 0; i + len < m + 2; i++) {

                int j = i + len;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i + 1; k < j; k++) {

                    int cost =
                            dp[i][k]
                                    + dp[k][j]
                                    + (cuts[j] - cuts[i]);

                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][m + 1];
    }

    public int tabulationII(int n, int[] originalCuts) {
        int m = originalCuts.length;

        // Step 1: Prepare cuts array with boundaries
        int[] cuts = new int[m + 2];
        cuts[0] = 0;
        cuts[m + 1] = n;

        for (int i = 0; i < m; i++) {
            cuts[i + 1] = originalCuts[i];
        }
//        System.arraycopy(originalCuts, 0, cuts, 1, m);

        Arrays.sort(cuts);

        int[][] dp = new int[m + 2][m + 2];
        for (int i = m; i >= 1; i--) {
            for (int j = 1; j <= m; j++) {
                if(i > j) continue;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {

                    int cost = dp[i][k - 1] + dp[k + 1][j] + (cuts[j+1] - cuts[i-1]);

                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        return dp[1][m];
    }

    // ---------------------------------------------------
    // Space Optimized
    //
    // Intuition:
    // Space optimization is NOT possible here
    // because dp[i][j] depends on many subranges.
    //
    // O(m^2) space is optimal.
    // ---------------------------------------------------
    public static int minCostIV(int n, int[] cuts) {
        return minCostIII(n, cuts);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int n = 7;
        int[] cuts = {1, 3, 4, 5};

        // Preprocess for recursion & memoization
        int[] allCuts = new int[cuts.length + 2];
        allCuts[0] = 0;
        allCuts[allCuts.length - 1] = n;
        for (int i = 0; i < cuts.length; i++) {
            allCuts[i + 1] = cuts[i];
        }
        Arrays.sort(allCuts);

        // Recursion
        System.out.println("Recursion: "
                + minCostI(0, allCuts.length - 1, allCuts));

        // Memoization
        int[][] dp = new int[allCuts.length][allCuts.length];
        for (int[] row : dp)
            Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + minCostII(0, allCuts.length - 1, allCuts, dp));

        // Tabulation
        System.out.println("Tabulation: "
                + minCostIII(n, cuts));

        // Space Optimized
        System.out.println("Space Optimized: "
                + minCostIV(n, cuts));
    }
}
