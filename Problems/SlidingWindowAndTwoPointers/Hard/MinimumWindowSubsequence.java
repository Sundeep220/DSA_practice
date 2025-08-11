package Problems.SlidingWindowAndTwoPointers.Hard;

public class MinimumWindowSubsequence {
    // Problem: https://leetcode.com/problems/minimum-window-subsequence/
    // Brute Force: O(n^3) time | O(1) space
//
//    1. How it’s different from Minimum Window Substring
//    Minimum Window Substring:
//    You want the smallest substring of s1 that contains all characters of s2, including duplicates, and in any order.
//    Order doesn’t matter — you just need the right counts.
//    2. Minimum Window Subsequence:
//    You want the smallest substring of s1 where s2 is a subsequence.
//    That means all characters of s2 must appear in the same relative order in the substring, but not necessarily consecutively.
//    s1 = "abcdebdde", s2 = "bde"
//
//    Minimum Window Substring (different problem):
//    smallest substring containing 'b', 'd', 'e' in any order
//    → "deb" or similar depending on counts
//
//    Minimum Window Subsequence (this problem):
//    we need 'b' → 'd' → 'e' in order
//    → smallest substring in s1 with that order is "bcde"
    public static String minWindowSubsequenceBrute(String s1, String s2) {
        int n = s1.length();
        String res = "";
        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                String sub = s1.substring(i, j + 1);
                if (isSubsequence(s2, sub) && sub.length() < minLen) {
                    minLen = sub.length();
                    res = sub;
                }
            }
        }
        return res;
    }

    private static boolean isSubsequence(String s2, String sub) {
        int i = 0, j = 0;
        while (i < sub.length() && j < s2.length()) {
            if (sub.charAt(i) == s2.charAt(j)) j++;
            i++;
        }
        return j == s2.length();
    }


    // Optimal Approach: Using Two Pointers
    // Time Complexity: O(n * m) where n is length of s1 and m is length of s2
    // Space Complexity: O(1) for the result string, but O(n) for the input strings in terms of space used by the result string
    public static String minWindowSubsequenceOptimal(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int minLen = Integer.MAX_VALUE, startIndex = -1;
        int i = 0, j = 0;

        while (i < n) {
            if (s1.charAt(i) == s2.charAt(j)) {
                j++;
                if (j == m) { // matched s2 completely
                    int end = i + 1;
                    // move backward to minimize window
                    j--;
                    int k = i;
                    while (j >= 0) {
                        if (s1.charAt(k) == s2.charAt(j)) j--;
                        k--;
                    }
                    k++;
                    if (end - k < minLen) {
                        minLen = end - k;
                        startIndex = k;
                    }
                    // reset for next search
                    i = k;
                    j = 0;
                }
            }
            i++;
        }
        return startIndex == -1 ? "" : s1.substring(startIndex, startIndex + minLen);
    }

}
