package Problems.DynamicProgramming.LIS;



import java.util.*;

public class LongestStringChain {

    // Problem:
    // https://leetcode.com/problems/longest-string-chain/
    // ---------------------------------------------------
    // Helper: Check if s1 is predecessor of s2
    // s2 must be exactly one character longer than s1
    // ---------------------------------------------------
    private static boolean isPredecessor(String s1, String s2) {
        if (s2.length() != s1.length() + 1)
            return false;

        int i = 0, j = 0;
        boolean skipped = false;

        while (i < s1.length() && j < s2.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                i++;
                j++;
            } else {
                if (skipped) return false;
                skipped = true;
                j++; // skip one char in s2
            }
        }

        return true;
    }

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // State: (index, prevIndex)
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    public static int lscI(int index, int prevIndex, String[] words) {

        if (index == words.length)
            return 0;

        // Not take
        int notTake = lscI(index + 1, prevIndex, words);

        // Take if valid predecessor
        int take = 0;
        if (prevIndex == -1 || isPredecessor(words[prevIndex], words[index])) {
            take = 1 + lscI(index + 1, index, words);
        }

        return Math.max(take, notTake);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n^2 * L)
    // Space: O(n^2)
    // ---------------------------------------------------
    public static int lscII(int index, int prevIndex,
                            String[] words, int[][] dp) {

        if (index == words.length)
            return 0;

        if (dp[index][prevIndex + 1] != -1)
            return dp[index][prevIndex + 1];

        int notTake = lscII(index + 1, prevIndex, words, dp);

        int take = 0;
        if (prevIndex == -1 ||
                isPredecessor(words[prevIndex], words[index])) {
            take = 1 + lscII(index + 1, index, words, dp);
        }

        dp[index][prevIndex + 1] = Math.max(take, notTake);
        return dp[index][prevIndex + 1];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n^2 * L)
    // Space: O(n)
    // ---------------------------------------------------
    public static int lscIII(String[] words) {
        int n = words.length;

        // Sort words by length
        Arrays.sort(words, Comparator.comparingInt(String::length));

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (isPredecessor(words[j], words[i])) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    // ---------------------------------------------------
    // Space Optimized
    // Same as Tabulation (already O(n))
    // ---------------------------------------------------
    public static int lscIV(String[] words) {
        return lscIII(words);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String[] words = {"a", "b", "ba", "bca", "bda", "bdca"};

        // Sort once for recursion/memoization
        Arrays.sort(words, Comparator.comparingInt(String::length));

        System.out.println("Recursion: "
                + lscI(0, -1, words));

        int[][] dp = new int[words.length][words.length + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + lscII(0, -1, words, dp));

        System.out.println("Tabulation: "
                + lscIII(words));

        System.out.println("Space Optimized: "
                + lscIV(words));
    }
}
