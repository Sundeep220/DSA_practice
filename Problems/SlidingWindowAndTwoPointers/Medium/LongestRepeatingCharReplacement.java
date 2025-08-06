package Problems.SlidingWindowAndTwoPointers.Medium;

import java.util.HashMap;
import java.util.Map;

public class LongestRepeatingCharReplacement {
    // Problem: https://leetcode.com/problems/longest-repeating-character-replacement/
    // Brute Force: O(n^2) time | O(1) space
    //Idea:
    //1. Check every substring.
    //2. For each substring:
        // Count the frequency of characters.
        //Let maxFreq = most frequent character count in this substring.
        //Let len = substring length.
        //If (len - maxFreq) <= k → valid (we can change all other chars to the most frequent one).
    //Track the maximum valid length.
    public static int characterReplacementBruteForce(String s, int k) {
        int n = s.length();
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            int[] freq = new int[26]; // count of each character
            int maxFreq = 0;

            for (int j = i; j < n; j++) {
                char c = s.charAt(j);
                freq[c - 'A']++;
                maxFreq = Math.max(maxFreq, freq[c - 'A']);

                int windowLen = j - i + 1;
                int charsToChange = windowLen - maxFreq;

                if (charsToChange <= k) {
                    maxLen = Math.max(maxLen, windowLen);
                }
            }
        }
        return maxLen;
    }

    // Better Approach: Using Sliding Window + HashMap
    // Time Complexity: O(n), Space Complexity: O(1)
    public static int characterReplacementBetter(String s, int k) {
        int n = s.length();
        int maxLen = 0;
        int left = 0, maxFreq = 0;
        int[] freq = new int[26];

        for (int right = 0; right < n; right++) {
            freq[s.charAt(right) - 'A']++;
            maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);

            // If window invalid, shrink from left
            while ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }

            // Update max valid window
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    // Another Way
    public static int characterReplacementBetter2(String s, int k) {
        Map<Character, Integer> freq = new HashMap<>();
        int left = 0, maxCount = 0, maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            freq.put(c, freq.getOrDefault(c, 0) + 1);

            // Track the count of the most frequent char
            maxCount = Math.max(maxCount, freq.get(c));

            // If window is invalid, shrink from the left
            while ((right - left + 1) - maxCount > k) {
                char leftChar = s.charAt(left);
                freq.put(leftChar, freq.get(leftChar) - 1);
                left++;
            }

            // Update max length
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    // Using SIngle Pass
    public static int characterReplacementSinglePass(String s, int k) {
        int[] freq = new int[26];
        int left = 0, maxFreq = 0, maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            int idx = s.charAt(right) - 'A';
            freq[idx]++;
            maxFreq = Math.max(maxFreq, freq[idx]);

            int windowLen = right - left + 1;

            // Single-step shrink (no while loop)
            if (windowLen - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
                windowLen--; // because we shrank the window by 1
            }

            maxLen = Math.max(maxLen, windowLen);
        }

        return maxLen;
    }

    // Another Single Pass Solution
    public static int characterReplacementSinglePass2(String s, int k) {
        Map<Character, Integer> freq = new HashMap<>();
        int left = 0, maxFreq = 0, maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            freq.put(c, freq.getOrDefault(c, 0) + 1);

            maxFreq = Math.max(maxFreq, freq.get(c));
            int windowLen = right - left + 1;

            // Shrink window by one step if invalid
            if (windowLen - maxFreq > k) {
                char leftChar = s.charAt(left);
                freq.put(leftChar, freq.get(leftChar) - 1);
                left++;
                windowLen--; // adjusted window length
            }

            maxLen = Math.max(maxLen, windowLen);
        }

        return maxLen;
    }
}
