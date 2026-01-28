package Problems.DynamicProgramming.Strings;

import java.util.Arrays;

public class LongestPalindromicSubsequence {

    // Problem: https://leetcode.com/problems/longest-palindromic-subsequence/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int lpsI(int i, int j, String s) {

        // Base cases
        if (i > j) return 0;
        if (i == j) return 1;

        // Characters match
        if (s.charAt(i) == s.charAt(j)) {
            return 2 + lpsI(i + 1, j - 1, s);
        }

        // Characters don't match
        return Math.max(
                lpsI(i + 1, j, s),
                lpsI(i, j - 1, s)
        );
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * n)
    // Space: O(n * n)
    // ---------------------------------------------------
    public static int lpsII(int i, int j, String s, int[][] dp) {

        if (i > j) return 0;
        if (i == j) return 1;

        if (dp[i][j] != -1)
            return dp[i][j];

        if (s.charAt(i) == s.charAt(j)) {
            dp[i][j] = 2 + lpsII(i + 1, j - 1, s, dp);
        } else {
            dp[i][j] = Math.max(
                    lpsII(i + 1, j, s, dp),
                    lpsII(i, j - 1, s, dp)
            );
        }

        return dp[i][j];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * n)
    // Space: O(n * n)
    // ---------------------------------------------------
    public static int lpsIII(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // Base case: single characters
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // Build for increasing substring length
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                            dp[i + 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        return dp[0][n - 1];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(n * n)
    // Space: O(n)
    // ---------------------------------------------------
    public static int lpsIV(String s) {
        int n = s.length();
        int[] prev = new int[n];

        // Base case: length 1 substrings
        for (int i = 0; i < n; i++) {
            prev[i] = 1;
        }

        // Build DP diagonally
        for (int i = n - 2; i >= 0; i--) {
            int[] curr = new int[n];
            curr[i] = 1;

            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    curr[j] = 2 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            prev = curr;
        }

        return prev[n - 1];
    }

    // LPS using LCS(s, reverse(s))
    // Time: O(n * n)
    // Space: O(n * n)
    // ---------------------------------------------------
    public static int lpsUsingLCS(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        int n = s.length();

        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {

                if (s.charAt(i - 1) == rev.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                            dp[i - 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        return dp[n][n];
    }



    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String s = "bbbab";

        System.out.println("Recursion: "
                + lpsI(0, s.length() - 1, s));

        int[][] dp = new int[s.length()][s.length()];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + lpsII(0, s.length() - 1, s, dp));

        System.out.println("Tabulation: "
                + lpsIII(s));

        System.out.println("Space Optimized: "
                + lpsIV(s));

        System.out.println("LPS Length: " + lpsUsingLCS(s));
    }
}
