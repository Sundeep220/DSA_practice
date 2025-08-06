package Problems.SlidingWindowAndTwoPointers.Medium;

public class SubstringContainingAllThreeCharacters {
    // Problem: https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/

    // Brute Force: O(n^2) time | O(1) space
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int result = 0;
        for (int i = 0; i < n; i++) {
            char[] arr = new char[3];
            for (int j = i; j < n; j++) {
                arr[s.charAt(j) - 'a']++;
                if (arr[0] + arr[1] + arr[2] == 3) {
                    result += n - j;   // why? if we found the index where we got the valid substring, then after it for any character we will get valid substring, so we can add n - j
                    break;
                }
            }
        }
        return result;
    }


    public static int numberOfSubstringsOptimal(String s) {
        int n = s.length();
        int[] freq = new int[3]; // for 'a', 'b', 'c'
        int left = 0, right = 0;
        int res = 0, count = 0;

        while (right < n) {
            freq[s.charAt(right) - 'a']++;

            // shrink until the window is valid
            while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
                // all substrings starting at left and ending from right to end are valid
                res += n - right;

                // shrink the window
                freq[s.charAt(left) - 'a']--;
                left++;
            }
            right++;
        }
        return res;
    }

    // Optimal Solution: Using Concept of Last Seen Index
    // A substring is valid if it contains at least one a, b, and c.
    //Instead of using a sliding window, track the last seen index of each character.
    //At each index i (processing s[i]), the number of new substrings ending at i that contain all 3 characters is:
    //i - Math.max(lastSeenA, lastSeenB, lastSeenC) + 1
    public static int numberOfSubstringsOptimal2(String s) {
        int res = 0, lastSeenA = -1, lastSeenB = -1, lastSeenC = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') lastSeenA = i;
            if (s.charAt(i) == 'b') lastSeenB = i;
            if (s.charAt(i) == 'c') lastSeenC = i;
            res += Math.min(lastSeenA, Math.min(lastSeenB, lastSeenC)) + 1;
        }
        return res;
    }


}
