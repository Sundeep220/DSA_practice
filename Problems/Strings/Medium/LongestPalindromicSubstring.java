package Problems.Strings.Medium;

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

    public static void main(String[] args) {
        String s = "babad";
        System.out.println(longestPalindrome(s));
    }
}
