package Problems.SlidingWindowAndTwoPointers.Medium;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithUnique {
    // Problem: https://leetcode.com/problems/longest-substring-without-repeating-characters/description/

    // Brute Force: Generate all possible substrings and check if they have unique characters. O(n^2) time | O(1) space

    public static int lengthOfLongestSubstringBrute(String s) {
        int n = s.length();
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isUnique(s.substring(i, j + 1))) {
                    res = Math.max(res, j - i + 1);
                }
            }
        }
        return res;
    }

    public static boolean isUnique(String s) {
        boolean[] visited = new boolean[256];
        for (int i = 0; i < s.length(); i++) {
            if (visited[s.charAt(i)]) {
                return false;
            }
            visited[s.charAt(i)] = true;
        }
        return true;
    }

    // Better Solution: Instead of generating all substrings, we can epand j until we get unique and stop wherever we get non-unique.
    public static int lengthOfLongestSubstringBetter(String s) {
        int n = s.length();
        int res = 0;
        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[256];
            for (int j = i; j < n; j++) {
                if (visited[s.charAt(j)]) {
                    break;
                }
                visited[s.charAt(j)] = true;
                res = Math.max(res, j - i + 1);
            }
        }
        return res;
    }

    // Optimal Solution: Using Sliding Window
    public static int lengthOfLongestSubstringOptimal(String s) {
        int n = s.length();
        int res = 0;
        int left = 0, right = 0;
        boolean[] visited = new boolean[256];
        while (right < n) {
            if (visited[s.charAt(right)]) {
                visited[s.charAt(left)] = false;
                left++;
            } else {
                visited[s.charAt(right)] = true;
                res = Math.max(res, right - left + 1);
                right++;
            }
        }
        return res;
    }

    // Using HashMap
    public int lengthOfLongestSubstringOptimal2(String s) {
        Map<Character, Integer> lastIndex = new HashMap<>();
        int left = 0, maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
                // Move left pointer to next of last duplicate
                left = lastIndex.get(c) + 1;
            }

            lastIndex.put(c, right);  // update last index
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
