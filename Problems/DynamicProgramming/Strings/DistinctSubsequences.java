package Problems.DynamicProgramming.Strings;

import java.util.Arrays;

public class DistinctSubsequences {

    // Problem: https://leetcode.com/problems/distinct-subsequences/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(n + m)
    // ---------------------------------------------------
    public static int numDistinctI(int i, int j, String s, String t) {

        // All characters of t matched
        if (j < 0) return 1;

        // s exhausted but t not matched
        if (i < 0) return 0;

        if (s.charAt(i) == t.charAt(j)) {
            return numDistinctI(i - 1, j - 1, s, t)
                    + numDistinctI(i - 1, j, s, t);
        }

        return numDistinctI(i - 1, j, s, t);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int numDistinctII(int i, int j,
                                    String s, String t,
                                    int[][] dp) {

        if (j < 0) return 1;
        if (i < 0) return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        if (s.charAt(i) == t.charAt(j)) {
            dp[i][j] = numDistinctII(i - 1, j - 1, s, t, dp)
                    + numDistinctII(i - 1, j, s, t, dp);
        } else {
            dp[i][j] = numDistinctII(i - 1, j, s, t, dp);
        }

        return dp[i][j];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int numDistinctIII(String s, String t) {
        int n = s.length();
        int m = t.length();

        int[][] dp = new int[n + 1][m + 1];

        // Base case: empty t can be formed in 1 way
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        // dp[0][j>0] = 0 by default

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]
                            + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][m];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(n * m)
    // Space: O(m)
    // ---------------------------------------------------
    public static int numDistinctIV(String s, String t) {
        int n = s.length();
        int m = t.length();

        int[] prev = new int[m + 1];
        prev[0] = 1; // empty t

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            curr[0] = 1;

            for (int j = 1; j <= m; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    curr[j] = prev[j - 1] + prev[j];
                } else {
                    curr[j] = prev[j];
                }
            }
            prev = curr;
        }

        return prev[m];
    }

    // One more Space Optimization
    // Time: O(n * m)
    // Space: O(m)
    public static int  numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();

        int[] dp = new int[m + 1];
        dp[0] = 1; // Empty t can always be formed

        for (int i = 1; i <= n; i++) {
            // IMPORTANT: iterate j from right to left
            for (int j = m; j >= 1; j--) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] = dp[j] + dp[j - 1];
                }
                // else dp[j] stays as dp[j] (skip s[i-1])
            }
        }

        return dp[m];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String s = "rabbbit";
        String t = "rabbit";

        System.out.println("Recursion: "
                + numDistinctI(s.length() - 1,
                t.length() - 1,
                s, t));

        int[][] dp = new int[s.length()][t.length()];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + numDistinctII(s.length() - 1,
                t.length() - 1,
                s, t, dp));

        System.out.println("Tabulation: "
                + numDistinctIII(s, t));

        System.out.println("Space Optimized: "
                + numDistinctIV(s, t));

        System.out.println("Most Optimized: " + numDistinct(s, t));
    }
}
