package Problems.DynamicProgramming.Strings;

public class PrintLongestCommonSubsequence {

    // ---------------------------------------------------
    // Print LCS using DP + Backtracking
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static String printLCS(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        // Step 1: Build DP table
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

        // Step 2: Backtrack to build LCS string
        int i = n, j = m;
        StringBuilder lcs = new StringBuilder();

        while (i > 0 && j > 0) {

            // Characters match → part of LCS
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs.append(s1.charAt(i - 1));
                i--;
                j--;
            }
            // Move in the direction of larger DP value
            else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        // We built LCS in reverse
        return lcs.reverse().toString();
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";

        System.out.println("LCS: " + printLCS(text1, text2));
    }
}
