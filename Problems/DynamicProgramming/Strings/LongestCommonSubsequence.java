package Problems.DynamicProgramming.Strings;


import java.util.Arrays;

public class LongestCommonSubsequence {

    // Problem: https://leetcode.com/problems/longest-common-subsequence/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential (O(2^(n+m)))
    // Space: O(n + m) recursion stack
    // ---------------------------------------------------
    public static int lcsI(int i, int j, String s1, String s2) {

        // Base case: one string exhausted
        if (i < 0 || j < 0) return 0;

        // Characters match
        if (s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcsI(i - 1, j - 1, s1, s2);
        }

        // Characters don't match
        return Math.max(
                lcsI(i - 1, j, s1, s2),
                lcsI(i, j - 1, s1, s2)
        );
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int lcsII(int i, int j,
                            String s1, String s2,
                            int[][] dp) {

        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            dp[i][j] = 1 + lcsII(i - 1, j - 1, s1, s2, dp);
        } else {
            dp[i][j] = Math.max(
                    lcsII(i - 1, j, s1, s2, dp),
                    lcsII(i, j - 1, s1, s2, dp)
            );
        }

        return dp[i][j];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int lcsIII(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n + 1][m + 1];

        // Base case already 0 when i == 0 or j == 0

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

        return dp[n][m];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(n * m)
    // Space: O(m)
    // ---------------------------------------------------
    public static int lcsIV(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[] prev = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            prev = curr;
        }

        return prev[m];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";

        System.out.println("Recursion: "
                + lcsI(text1.length() - 1,
                text2.length() - 1,
                text1, text2));

        int[][] dp = new int[text1.length()][text2.length()];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + lcsII(text1.length() - 1,
                text2.length() - 1,
                text1, text2, dp));

        System.out.println("Tabulation: "
                + lcsIII(text1, text2));

        System.out.println("Space Optimized: "
                + lcsIV(text1, text2));
    }
}
