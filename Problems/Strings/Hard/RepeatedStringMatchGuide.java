package Problems.Strings.Hard;/*
 * LeetCode 686 - Repeated String Match
 *
 * ===============================================================
 * APPROACH 1 : Naive Substring Search (Brute Force)
 * ===============================================================
 * Intuition:
 * Repeat string 'a' until its length becomes at least the length of 'b'.
 * Then check whether 'b' exists using a manual character-by-character search.
 * If not found, append one more copy of 'a' because the match may cross
 * one repetition boundary. Otherwise return -1.
 *
 * Time:
 *   Building string : O(k * |a|)
 *   Naive search    : O(N * M)
 * * Space: O(N)
 *
 * ===============================================================
 * APPRO==============
 * Same repetition observation.
 * Uses Java's optimized indexOf() implementation.
 *
 * Time:
 *   Building string : O(N)
 *   indexOf()       : Implementation dependent (typically very efficient)
 *
 * Space: O(N)
 *
 * ===============================================================
 * APPROACH 3 : KMP (Optimal Worst Case)
 * ===============================================================
 * Same repetition observation.
 * Replace indexOf() with KMP substring search.
 *
 * Time:
 *   Build repeated string : O(N)
 *   Build LPS             : O(M)
 *   Search                : O(N)
 * Overall : O(N + M)
 *
 * Space : O(M)
 */

public class RepeatedStringMatchGuide {

    // ------------------------------------------------------------
    // Approach 1 : Naive
    // ------------------------------------------------------------
    public int repeatedStringMatchNaive(String a, String b) {

        StringBuilder sb = new StringBuilder();
        int count = 0;

        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        if (containsNaive(sb.toString(), b))
            return count;

        sb.append(a);

        if (containsNaive(sb.toString(), b))
            return count + 1;

        return -1;
    }

    private boolean containsNaive(String text, String pattern) {

        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;
            while (j < pattern.length() && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == pattern.length())
                return true;
        }

        return false;
    }

    // ------------------------------------------------------------
    // Approach 2 : Java indexOf (Recommended in interviews unless
    // interviewer asks to implement substring search.)
    // ------------------------------------------------------------
    public int repeatedStringMatchIndexOf(String a, String b) {

        StringBuilder sb = new StringBuilder();
        int count = 0;

        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        if (sb.indexOf(b) != -1)
            return count;

        sb.append(a);

        if (sb.indexOf(b) != -1)
            return count + 1;

        return -1;
    }

    // ------------------------------------------------------------
    // Approach 3 : KMP
    // ------------------------------------------------------------
    public int repeatedStringMatchKMP(String a, String b) {

        StringBuilder sb = new StringBuilder();
        int count = 0;

        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        if (kmpSearch(sb.toString(), b))
            return count;

        sb.append(a);

        if (kmpSearch(sb.toString(), b))
            return count + 1;

        return -1;
    }

    private boolean kmpSearch(String text, String pattern) {

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length())
                    return true;
            } else {

                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }

        return false;
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

                if (len != 0)
                    len = lps[len - 1];
                else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    public static void main(String[] args) {

        RepeatedStringMatchGuide sol = new RepeatedStringMatchGuide();

        String a = "abcd";
        String b = "cdabcdab";

        System.out.println("Naive   : " + sol.repeatedStringMatchNaive(a, b));
        System.out.println("indexOf : " + sol.repeatedStringMatchIndexOf(a, b));
        System.out.println("KMP     : " + sol.repeatedStringMatchKMP(a, b));
    }
}
