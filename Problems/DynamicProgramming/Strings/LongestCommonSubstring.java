package Problems.DynamicProgramming.Strings;

import java.util.Arrays;

public class LongestCommonSubstring {

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // We track the current length of matching substring
    // Time: Exponential
    // Space: O(n + m)
    // ---------------------------------------------------
    public static int lcsI(int i, int j, int count,
                           String s1, String s2) {

        // Base case
        if (i < 0 || j < 0)
            return count;

        int curCount = count;

        // If characters match, extend substring
        if (s1.charAt(i) == s2.charAt(j)) {
            curCount = lcsI(i - 1, j - 1, count + 1, s1, s2);
        }

        // Try breaking substring from either side
        int left = lcsI(i - 1, j, 0, s1, s2);
        int right = lcsI(i, j - 1, 0, s1, s2);

        return Math.max(curCount, Math.max(left, right));
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // dp[i][j] = length of longest common substring
    // ending at s1[i] and s2[j]
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int lcsII(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n][m];
        for (int[] row : dp) Arrays.fill(row, -1);

        int[] maxLen = new int[1];
        lcsMemo(n - 1, m - 1, s1, s2, dp, maxLen);
        return maxLen[0];
    }

    private static int lcsMemo(int i, int j,
                               String s1, String s2,
                               int[][] dp, int[] maxLen) {

        if (i < 0 || j < 0)
            return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        // Explore other states (important!)
        lcsMemo(i - 1, j, s1, s2, dp, maxLen);
        lcsMemo(i, j - 1, s1, s2, dp, maxLen);

        if (s1.charAt(i) == s2.charAt(j)) {
            dp[i][j] = 1 + lcsMemo(i - 1, j - 1, s1, s2, dp, maxLen);
            maxLen[0] = Math.max(maxLen[0], dp[i][j]);
        } else {
            dp[i][j] = 0;
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
        int maxLen = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    maxLen = Math.max(maxLen, dp[i][j]);
                } else {
                    dp[i][j] = 0; // reset on mismatch
                }
            }
        }

        return maxLen;
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
        int maxLen = 0;

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                    maxLen = Math.max(maxLen, curr[j]);
                } else {
                    curr[j] = 0;
                }
            }
            prev = curr;
        }

        return maxLen;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String s1 = "abcdgh";
        String s2 = "acdghr";

        System.out.println("Recursion: "
                + lcsI(s1.length() - 1,
                s2.length() - 1,
                0, s1, s2));

        System.out.println("Memoization: "
                + lcsII(s1, s2));

        System.out.println("Tabulation: "
                + lcsIII(s1, s2));

        System.out.println("Space Optimized: "
                + lcsIV(s1, s2));
    }
}
