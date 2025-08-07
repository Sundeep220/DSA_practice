package Problems.SlidingWindowAndTwoPointers.Medium;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithKDistinctChars {
    // Problem: https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/

    // Using Brute Force: O(n^2) time | O(1) space
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        int maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            Set<Character> set = new HashSet<>();
            for (int j = i; j < s.length(); j++) {
                set.add(s.charAt(j));
                if (set.size() > k) break;
                maxLen = Math.max(maxLen, j - i + 1);
            }
        }

        return maxLen;
    }

    // Using Sliding Window + HashMap
    // Time Complexity: O(n), Space Complexity: O(1)

    public static int lengthOfLongestSubstringKDistinct2(String s, int k) {
        if (s == null || s.isEmpty() || k == 0) return 0;

        int n = s.length();
        int maxLen = 0;
        int left = 0, right = 0;
        char[] freq = new char[256];
        int distinct = 0;

        while (right < n) {
            char c = s.charAt(right);
            if(freq[c] == 0) distinct++; // new distinct char found, increment distinct count
            freq[c]++; // increment freq count of current char

            // shrink window if invalid
            while (distinct > k) {
                char leftChar = s.charAt(left);
                freq[leftChar]--; // decrement freq count of left char
                if (freq[leftChar] == 0) distinct--; // left char is no longer distinct, decrement distinct count
                left++; // shrink window from left side
            }

            // update max valid window
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }

        return maxLen;
    }
}
