package Problems.Strings.StringHashing;

public class FindFirstIndexOfSubstring {
    // Problem: https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/description/

    // APPROACH 1 : Naive
    // Time: O((m-n+1) * n)
    // Space: O(1)
    public int strStrNaive(String text, String pattern) {
        int m = text.length();
        int n = pattern.length();

        if(n > m) return -1;
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;
            while (j < pattern.length() && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == pattern.length())
                return i;
        }

        return -1;
    }

    // Using Java Built In Method
    // Time: O(m + n)
    // Space: O(1)
    public int strStrBuiltIn(String text, String pattern) {
        return text.indexOf(pattern);
    }

    // Using KMP
    // Time: O(m + n)
    // Space: O(1)
    public int strStrKMP(String haystack, String needle) {
        if (needle.length() == 0)
            return 0;

        int[] lps = buildLPS(needle);

        int i = 0;      // haystack pointer
        int j = 0;      // needle pointer

        while (i < haystack.length()) {

            // Characters match
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }

            // Entire pattern matched
            if (j == needle.length()) {
                return i - j;
            }

            // Mismatch after some matches
            else if (i < haystack.length() &&
                    haystack.charAt(i) != needle.charAt(j)) {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return -1;
    }

    private int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int len = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(len)) {

                len++;
                lps[i] = len;
                i++;

            } else {

                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }

            }
        }

        return lps;
    }
}
