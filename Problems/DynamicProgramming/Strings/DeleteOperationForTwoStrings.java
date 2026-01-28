package Problems.DynamicProgramming.Strings;

import java.util.Arrays;

public class DeleteOperationForTwoStrings {

    // Problem: https://leetcode.com/problems/delete-operation-for-two-strings/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(n + m)
    // ---------------------------------------------------
    public static int minDelI(int i, int j, String s1, String s2) {

        // If one string is exhausted
        if (i < 0) return j + 1;
        if (j < 0) return i + 1;

        if (s1.charAt(i) == s2.charAt(j)) {
            return minDelI(i - 1, j - 1, s1, s2);
        }

        return 1 + Math.min(
                minDelI(i - 1, j, s1, s2),
                minDelI(i, j - 1, s1, s2)
        );
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int minDelII(int i, int j,
                               String s1, String s2,
                               int[][] dp) {

        if (i < 0) return j + 1;
        if (j < 0) return i + 1;

        if (dp[i][j] != -1)
            return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            dp[i][j] = minDelII(i - 1, j - 1, s1, s2, dp);
        } else {
            dp[i][j] = 1 + Math.min(
                    minDelII(i - 1, j, s1, s2, dp),
                    minDelII(i, j - 1, s1, s2, dp)
            );
        }

        return dp[i][j];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int minDelIII(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n + 1][m + 1];

        // Base cases
        for (int i = 0; i <= n; i++)
            dp[i][0] = i;
        for (int j = 0; j <= m; j++)
            dp[0][j] = j;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        return dp[n][m];
    }

    // ---------------------------------------------------
    // Most Optimal: Using LCS Formula
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int minDelIV(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n + 1][m + 1];

        // LCS DP
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                            dp[i - 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        int lcs = dp[n][m];
        return (n - lcs) + (m - lcs);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String word1 = "sea";
        String word2 = "eat";

        System.out.println("Recursion: "
                + minDelI(word1.length() - 1,
                word2.length() - 1,
                word1, word2));

        int[][] dp = new int[word1.length()][word2.length()];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + minDelII(word1.length() - 1,
                word2.length() - 1,
                word1, word2, dp));

        System.out.println("Tabulation: "
                + minDelIII(word1, word2));

        System.out.println("Using LCS (Optimal): "
                + minDelIV(word1, word2));
    }
}
