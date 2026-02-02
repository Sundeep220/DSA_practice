package Problems.DynamicProgramming.Partitions;


import java.util.Arrays;

public class PalindromePartitioningII {

    // ---------------------------------------------------
    // Problem: https://leetcode.com/problems/palindrome-partitioning-ii/description/
    // 32. Palindrome Partitioning II
    //
    // Given a string s, partition it such that
    // every substring is a palindrome.
    //
    // Return the minimum number of cuts needed.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Helper: Precompute Palindrome Table
    //
    // Intuition:
    // We need to frequently check if s[i..j] is a palindrome.
    // Precompute this using DP in O(n^2).
    //
    // pal[i][j] = true if s[i..j] is palindrome
    // ---------------------------------------------------
    private static boolean[][] buildPalindromeTable(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];

        // length 1
        for (int i = 0; i < n; i++) {
            pal[i][i] = true;
        }

        // length 2
        for (int i = 0; i < n - 1; i++) {
            pal[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }

        // length >= 3
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                pal[i][j] =
                        (s.charAt(i) == s.charAt(j)) &&
                                pal[i + 1][j - 1];
            }
        }

        return pal;
    }

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    //
    // Intuition:
    // Try all possible partitions starting at index i.
    // If s[i..j] is palindrome, we can make a cut after j.
    //
    // f(i) → minimum cuts needed for substring s[i..n-1]
    //
    // Base case:
    // i == n → no cuts needed
    // Remember to return ans - 1 as we need number of cuts and not number of partitions
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    public static int minCutI(int i, String s, boolean[][] pal) {

        if (i == s.length())
            return 0;

        int minCuts = Integer.MAX_VALUE;

        for (int j = i; j < s.length(); j++) {
            if (pal[i][j]) {
                int cuts = 1 + minCutI(j + 1, s, pal);
                minCuts = Math.min(minCuts, cuts);
            }
        }

        return minCuts;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    //
    // Intuition:
    // Same recursive idea, but cache results of f(i).
    //
    // dp[i] → minimum cuts for s[i..n-1]
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static int minCutII(int i, String s,
                               boolean[][] pal, int[] dp) {

        if (i == s.length())
            return 0;

        if (dp[i] != -1)
            return dp[i];

        int minCuts = Integer.MAX_VALUE;

        for (int j = i; j < s.length(); j++) {
            if (pal[i][j]) {
                int cuts = 1 + minCutII(j + 1, s, pal, dp);
                minCuts = Math.min(minCuts, cuts);
            }
        }

        dp[i] = minCuts;
        return minCuts;
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    //
    // Intuition:
    // dp[i] → minimum cuts needed for substring s[i..n-1]
    //
    // Answer:
    // dp[0] - 1
    // (because last partition adds an extra cut)
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static int minCutIII(String s) {
        int n = s.length();
        boolean[][] pal = buildPalindromeTable(s);

        int[] dp = new int[n + 1];
        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {
            int minCuts = Integer.MAX_VALUE;

            for (int j = i; j < n; j++) {
                if (pal[i][j]) {
                    minCuts = Math.min(minCuts, 1 + dp[j + 1]);
                }
            }
            dp[i] = minCuts;
        }

        // subtract 1 because last partition doesn't need a cut
        return dp[0] - 1;
    }

    // ---------------------------------------------------
    // Optimized Tabulation (Standard LeetCode Answer)
    //
    // Intuition:
    // cuts[i] = minimum cuts for prefix s[0..i-1]
    //
    // If s[j..i-1] is palindrome:
    // cuts[i] = min(cuts[i], cuts[j] + 1)
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static int minCutIV(String s) {
        int n = s.length();
        boolean[][] pal = buildPalindromeTable(s);

        int[] cuts = new int[n + 1];
        for (int i = 0; i <= n; i++)
            cuts[i] = i - 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (pal[j][i - 1]) {
                    cuts[i] = Math.min(cuts[i], cuts[j] + 1);
                }
            }
        }

        return cuts[n];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        String s1 = "aab";
        String s2 = "a";
        String s3 = "ab";

        boolean[][] pal1 = buildPalindromeTable(s1);

        // Recursion
        System.out.println("Recursion: "
                + (minCutI(0, s1, pal1) - 1));

        // Memoization
        int[] dp = new int[s1.length()];
        Arrays.fill(dp, -1);

        System.out.println("Memoization: "
                + (minCutII(0, s1, pal1, dp) - 1));

        // Tabulation
        System.out.println("Tabulation: "
                + minCutIII(s1));

        // Optimized
        System.out.println("Optimized: "
                + minCutIV(s1));

        System.out.println("Edge cases:");
        System.out.println(minCutIV(s2)); // 0
        System.out.println(minCutIV(s3)); // 1
    }
}
