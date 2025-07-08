package Problems.GoodProblems;

public class LongestPalindromicSubstring {
    // https://leetcode.com/problems/longest-palindromic-substring/

    //Brute Force: O(n^3) time | O(1) space
    public static String longestPalindrome(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                String sub = s.substring(i, j + 1);
                if (isPalin(sub) && sub.length() > res.length()) {
                    res = sub;
                }
            }
        }

        return res;

    }

    public static boolean isPalin(String s){
        int left = 0, right = s.length() - 1;
        while(left <= right){
            if(s.charAt(left) != s.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }

    // Solution 2: Using Mirroring approach: O(n^2) time | O(1) space
    // The idea is this, for a string with n characters, we can have 2n - 1 centers for each palindromes
    // 1. n for odd length palindromes (having single center)
    // 2. n - 1 for even length palindromes (having 2 centers)
    // So, for each center, we will expand outwards to find the longest palindromic substring
    public static String longestPalindrome2(String s) {
        if(s == null || s.isEmpty())
            return s;
        int start = 0, end = 0;
        for (int i=0; i<s.length(); i++) {
            int len1 = expand(s, i, i);
            int len2 = expand(s, i, i+1);
            int len = Math.max(len1, len2);
            if(len > end - start) {
                start = i - (len - 1) / 2;  // to understand this, do a dry run for odd and even length palindromes
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public static int expand(String s, int left, int right) {
        while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;  // subtracting 1 because at the end of loop,both left and right will point to the character after the palindrome
    }

    public static void main(String[] args) {
        String s = "babad";
        System.out.println(longestPalindrome(s));
    }
}
