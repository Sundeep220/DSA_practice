package Problems.SlidingWindowAndTwoPointers.Hard;

public class MinimunWindowSubstring {
    // Problem: https://leetcode.com/problems/minimum-window-substring/description/


    // Brute Force: O(n^3) time | O(1) space
    // Generate all possible substrings and check if they contain all characters of t.
    public static String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) return "";

        int minLen = Integer.MAX_VALUE;
        String result = "";

        // Count of t's characters
        int[] tFreq = new int[256];
        for (char c : t.toCharArray()) {
            tFreq[c]++;
        }

        // Check all substrings
        for (int i = 0; i < m; i++) {
            for (int j = i; j < m; j++) {
                if (containsAll(s, i, j, tFreq)) {
                    int len = j - i + 1;
                    if (len < minLen) {
                        minLen = len;
                        result = s.substring(i, j + 1);
                    }
                }
            }
        }
        return result;
    }


    // Helper to check if substring contains all t chars
    private static boolean containsAll(String s, int start, int end, int[] tFreq) {
        int[] freq = new int[256];
        for (int i = start; i <= end; i++) {
            freq[s.charAt(i)]++;
        }
        for (int c = 0; c < 256; c++) {
            if (freq[c] < tFreq[c]) {
                return false;
            }
        }
        return true;
    }


    // Another Brute Force: O(n^2) time | O(1) space
    // Instead of running loops multiple times for each substring for checking frequency of each cahracter, we can use Map
    // to do this in single step
    // For each substring creation, we fill the frequency map of t,
    // Then while building substring for each char in s, we decrement the frequency of that char in the map.
    // But in any case we find that the frequency of current char > 0, we also increment a count of matched characters.
    // If in any case, we reach the count of matched characters equal to the length of t, can stop expanding and
    // check if the current substring is smaller than the previous minimum found.
    // This way we can avoid checking all characters in t for each substring, and instead just check the frequency map.
    // Time Complexity: O(n^2) Space Complexity: O(256) for frequency map
    // Note: This is still a brute force solution, but more optimized than the first one
    // This solution is still not optimal, but it is better than the first one
    public static String minWindowBruteII(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) return "";

        int minLen = Integer.MAX_VALUE;
        int startIndex = -1;

        for (int i = 0; i < m; i++) {
            int[] freq = new int[256];
            for (char c : t.toCharArray()) freq[c]++;

            int count = 0;
            for (int j = i; j < m; j++) {
                if (freq[s.charAt(j)] > 0) count++;
                freq[s.charAt(j)]--;

                if (count == n) { // all chars matched
                    int len = j - i + 1;
                    if (len < minLen) {
                        minLen = len;
                        startIndex = i;
                    }
                    break; // stop expanding this i, move to next start
                }
            }
        }

        if (startIndex == -1) return "";
        return s.substring(startIndex, startIndex + minLen);
    }

    // Optimal Solution: O(n + m) time | O(1) space
    // Using the same Hashing technique above, but using a sliding window approach to
    // to avoid checking all substrings.
    public static String minWindowSlidingWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) return "";

        int[] freq = new int[256]; // for all ASCII chars
        for (char c : t.toCharArray()) freq[c]++;

        int left = 0, minLen = Integer.MAX_VALUE, startIndex = -1, right = 0;
        int required = 0;
         // total chars we still need

        while (right < m) {
            char rc = s.charAt(right);
            if (freq[rc] > 0) required++; // matched a needed char
            freq[rc]--; // mark char as used (can go negative if extra)

            // shrink while window contains all chars
            while (required == n) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    startIndex = left;
                }
                char lc = s.charAt(left);
                freq[lc]++;
                if (freq[lc] > 0) required--; // lost a needed char
                left++;
            }
            right++;
        }

        return (startIndex == -1) ? "" : s.substring(startIndex, startIndex + minLen);
    }



    // Another way for writing the above solution
    public static String minWindowOptimal(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) return "";

        // Frequency map for t's characters
        int[] tFreq = new int[256];
        for (char c : t.toCharArray()) {
            tFreq[c]++;
        }

        int left = 0, right = 0, count = 0, minLen = Integer.MAX_VALUE;
        String result = "";
        int[] windowFreq = new int[256];

        while (right < m) {
            char cRight = s.charAt(right);
            windowFreq[cRight]++;
            if (windowFreq[cRight] <= tFreq[cRight]) count++; // valid char added

            while (count == n) { // valid window found
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    result = s.substring(left, right + 1);
                }
                char cLeft = s.charAt(left);
                windowFreq[cLeft]--;
                if (windowFreq[cLeft] < tFreq[cLeft]) count--; // invalid char removed
                left++;
            }
            right++;
        }

        return result;
    }

}
