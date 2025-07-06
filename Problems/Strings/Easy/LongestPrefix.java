package Problems.Strings.Easy;

public class LongestPrefix {
    // Problem: https://leetcode.com/problems/longest-common-prefix/
    // Brute Force: O(n^2) time | O(1) space
    // Comparing character by character from first word with all the other words
    public static String longestCommonPrefix(String[] strs) {
        if(strs.length == 0) return "";
        for(int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++) {
                if(i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    // Solution 2:
    public static String longestCommonPrefix2(String[] strs) {
        if(strs.length == 0) return "";
        String prefix = strs[0];  // take the first word as prefix
        for(int i = 1; i < strs.length; i++) {
            while(strs[i].indexOf(prefix) != 0) {  // while prefix is not a prefix of current word
                prefix = prefix.substring(0, prefix.length() - 1);  // remove last character and try again
                if(prefix.isEmpty()) return "";  // if prefix is empty, there is no common prefix
            }
        }
        return prefix;
    }

    public static void main(String[] args) {
        String[] strs = {"flower","flow","flight"};
        System.out.println(longestCommonPrefix(strs));
        System.out.println(longestCommonPrefix2(strs));
    }
}
