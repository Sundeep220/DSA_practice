package Problems.DynamicProgramming.Strings;

public class ShortestCommonSupersequence {

    // Problem: https://leetcode.com/problems/shortest-common-supersequence/description/
    // ---------------------------------------------------
    // Step 1: Build LCS DP table
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    private static int[][] buildLCS(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n + 1][m + 1];

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
        return dp;
    }

    // ---------------------------------------------------
    // Step 2: Backtrack to PRINT SCS
    // Time: O(n + m)
    // Space: O(n + m)
    // ---------------------------------------------------
    public static String shortestCommonSupersequence(String s1, String s2) {
        int[][] dp = buildLCS(s1, s2);

        int i = s1.length();
        int j = s2.length();

        StringBuilder sb = new StringBuilder();

        // Backtracking
        while (i > 0 && j > 0) {

            // Same character → part of LCS (add once)
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                sb.append(s1.charAt(i - 1));
                i--;
                j--;
            }
            // Move in direction of larger LCS
            else if (dp[i - 1][j] > dp[i][j - 1]) {
                sb.append(s1.charAt(i - 1));
                i--;
            } else {
                sb.append(s2.charAt(j - 1));
                j--;
            }
        }

        // Add remaining characters
        while (i > 0) {
            sb.append(s1.charAt(i - 1));
            i--;
        }

        while (j > 0) {
            sb.append(s2.charAt(j - 1));
            j--;
        }

        // We built it backwards
        return sb.reverse().toString();
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String str1 = "abac";
        String str2 = "cab";

        System.out.println("SCS: "
                + shortestCommonSupersequence(str1, str2));
    }
}
