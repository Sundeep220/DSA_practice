package Problems.DynamicProgramming.Strings;

import java.util.Arrays;

public class EditDistance {


    // Problem: https://leetcode.com/problems/edit-distance/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: Exponential
    // Space: O(n + m)
    // ---------------------------------------------------
    public static int editI(int i, int j, String s1, String s2) {

        // Base cases
        if (i < 0) return j + 1; // insert all remaining
        if (j < 0) return i + 1; // delete all remaining

        if (s1.charAt(i) == s2.charAt(j)) {
            return editI(i - 1, j - 1, s1, s2);
        }

        return 1 + Math.min(
                editI(i - 1, j, s1, s2),     // delete
                Math.min(
                        editI(i, j - 1, s1, s2), // insert
                        editI(i - 1, j - 1, s1, s2) // replace
                )
        );
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int editII(int i, int j,
                             String s1, String s2,
                             int[][] dp) {

        if (i < 0) return j + 1;
        if (j < 0) return i + 1;

        if (dp[i][j] != -1)
            return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            dp[i][j] = editII(i - 1, j - 1, s1, s2, dp);
        } else {
            dp[i][j] = 1 + Math.min(
                    editII(i - 1, j, s1, s2, dp),
                    Math.min(
                            editII(i, j - 1, s1, s2, dp),
                            editII(i - 1, j - 1, s1, s2, dp)
                    )
            );
        }

        return dp[i][j];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n * m)
    // Space: O(n * m)
    // ---------------------------------------------------
    public static int editIII(String s1, String s2) {
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
                            dp[i - 1][j],     // delete
                            Math.min(
                                    dp[i][j - 1], // insert
                                    dp[i - 1][j - 1] // replace
                            )
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
    public static int editIV(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[] prev = new int[m + 1];

        // Base case: converting empty s1 to s2
        for (int j = 0; j <= m; j++)
            prev[j] = j;

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            curr[0] = i;

            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    curr[j] = 1 + Math.min(
                            prev[j],       // delete
                            Math.min(
                                    curr[j - 1], // insert
                                    prev[j - 1]  // replace
                            )
                    );
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
        String word1 = "horse";
        String word2 = "ros";

        System.out.println("Recursion: "
                + editI(word1.length() - 1,
                word2.length() - 1,
                word1, word2));

        int[][] dp = new int[word1.length()][word2.length()];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + editII(word1.length() - 1,
                word2.length() - 1,
                word1, word2, dp));

        System.out.println("Tabulation: "
                + editIII(word1, word2));

        System.out.println("Space Optimized: "
                + editIV(word1, word2));
    }
}
