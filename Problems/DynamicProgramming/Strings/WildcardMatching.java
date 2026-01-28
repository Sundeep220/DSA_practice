package Problems.DynamicProgramming.Strings;

import java.util.Arrays;

public class WildcardMatching {

    //Problem: https://leetcode.com/problems/wildcard-matching/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(n + m)
    // ---------------------------------------------------
    public static boolean matchI(int i, int j, String s, String p) {

        // Both strings exhausted
        if (i < 0 && j < 0) return true;

        // Pattern exhausted but string not
        if (j < 0 && i >= 0) return false;

        // String exhausted but pattern not
        if (i < 0 && j >= 0) {
            // Only valid if remaining pattern is all '*'
            for (int k = 0; k <= j; k++) {
                if (p.charAt(k) != '*')
                    return false;
            }
            return true;
        }

        // Characters match or '?'
        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            return matchI(i - 1, j - 1, s, p);
        }

        // '*'
        if (p.charAt(j) == '*') {
            return matchI(i, j - 1, s, p)   // '*' matches empty
                    || matchI(i - 1, j, s, p);  // '*' matches one char
        }

        return false;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static boolean matchII(int i, int j,
                                  String s, String p,
                                  int[][] dp) {

        if (i < 0 && j < 0) return true;
        if (j < 0 && i >= 0) return false;

        if (i < 0 && j >= 0) {
            for (int k = 0; k <= j; k++) {
                if (p.charAt(k) != '*')
                    return false;
            }
            return true;
        }

        if (dp[i][j] != -1)
            return dp[i][j] == 1;

        boolean ans;

        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            ans = matchII(i - 1, j - 1, s, p, dp);
        }
        else if (p.charAt(j) == '*') {
            ans = matchII(i, j - 1, s, p, dp)
                    || matchII(i - 1, j, s, p, dp);
        }
        else {
            ans = false;
        }

        dp[i][j] = ans ? 1 : 0;
        return ans;
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static boolean matchIII(String s, String p) {
        int n = s.length();
        int m = p.length();

        boolean[][] dp = new boolean[n + 1][m + 1];

        dp[0][0] = true;

        // pattern matches empty string
        for (int j = 1; j <= m; j++) {
            dp[0][j] = dp[0][j - 1] && p.charAt(j - 1) == '*';
        }

        for (int i = 1; i <= n; i++) {
            dp[i][0] = false;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (s.charAt(i - 1) == p.charAt(j - 1)
                        || p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1]   // '*' = empty
                            || dp[i - 1][j];  // '*' = consume char
                }
                else {
                    dp[i][j] = false;
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
    public static boolean matchIV(String s, String p) {
        int n = s.length();
        int m = p.length();

        boolean[] prev = new boolean[m + 1];
        prev[0] = true;

        for (int j = 1; j <= m; j++) {
            prev[j] = prev[j - 1] && p.charAt(j - 1) == '*';
        }

        for (int i = 1; i <= n; i++) {
            boolean[] curr = new boolean[m + 1];
            curr[0] = false;

            for (int j = 1; j <= m; j++) {

                if (s.charAt(i - 1) == p.charAt(j - 1)
                        || p.charAt(j - 1) == '?') {
                    curr[j] = prev[j - 1];
                }
                else if (p.charAt(j - 1) == '*') {
                    curr[j] = curr[j - 1] || prev[j];
                }
                else {
                    curr[j] = false;
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
        String s = "aa";
        String p = "*";

        System.out.println("Recursion: "
                + matchI(s.length() - 1, p.length() - 1, s, p));

        int[][] dp = new int[s.length()][p.length()];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + matchII(s.length() - 1, p.length() - 1, s, p, dp));

        System.out.println("Tabulation: "
                + matchIII(s, p));

        System.out.println("Space Optimized: "
                + matchIV(s, p));
    }
}
