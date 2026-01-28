package Problems.DynamicProgramming.Strings;

import java.util.Arrays;

public class MinimumInsertionsToMakePalindrome {

    // Problem: https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(n) recursion stack
    // ---------------------------------------------------
    public static int minInsertI(int i, int j, String s) {

        // Base case: empty or single char
        if (i >= j) return 0;

        if (s.charAt(i) == s.charAt(j)) {
            return minInsertI(i + 1, j - 1, s);
        }

        return 1 + Math.min(
                minInsertI(i + 1, j, s),
                minInsertI(i, j - 1, s)
        );
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * n)
    // Space: O(n * n)
    // ---------------------------------------------------
    public static int minInsertII(int i, int j, String s, int[][] dp) {

        if (i >= j) return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        if (s.charAt(i) == s.charAt(j)) {
            dp[i][j] = minInsertII(i + 1, j - 1, s, dp);
        } else {
            dp[i][j] = 1 + Math.min(
                    minInsertII(i + 1, j, s, dp),
                    minInsertII(i, j - 1, s, dp)
            );
        }

        return dp[i][j];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * n)
    // Space: O(n * n)
    // ---------------------------------------------------
    public static int minInsertIII(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // length from 2 to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i + 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        return dp[0][n - 1];
    }

    // ---------------------------------------------------
    // Most Optimal: Using LPS (via LCS)
    // Time: O(n * n)
    // Space: O(n * n)
    // ---------------------------------------------------
    public static int minInsertIV(String s) {
        int n = s.length();
        String rev = new StringBuilder(s).reverse().toString();

        int[][] dp = new int[n + 1][n + 1];

        // LCS(s, reverse(s))
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

        int lps = dp[n][n];
        return n - lps;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String s = "mbadm";

        System.out.println("Recursion: "
                + minInsertI(0, s.length() - 1, s));

        int[][] dp = new int[s.length()][s.length()];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + minInsertII(0, s.length() - 1, s, dp));

        System.out.println("Tabulation: "
                + minInsertIII(s));

        System.out.println("Using LPS (Optimal): "
                + minInsertIV(s));
    }
}
