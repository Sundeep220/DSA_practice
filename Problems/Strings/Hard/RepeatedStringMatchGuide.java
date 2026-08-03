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
    // Approach 2 : Using Java's built-in indexOf()
    // ------------------------------------------------------------
    //
    // Intuition:
    // ------------
    // We need to find the minimum number of times string 'a' should
    // be repeated so that 'b' becomes a substring.
    //
    // Observation:
    // If 'b' is going to appear, then we must first repeat 'a'
    // until the repeated string's length is at least equal to 'b'.
    //
    // However, 'b' may start near the end of one repetition of 'a'
    // and continue into the next repetition.
    //
    // Example:
    // a = "abcd"
    // b = "cdabcdab"
    //
    // Repeating until length >= b:
    //
    // "abcdabcd"      (length = 8)
    //
    // b is NOT present.
    //
    // Add one more repetition:
    //
    // "abcdabcdabcd"
    //
    // Now "cdabcdab" appears.
    //
    // Therefore, we only need to check:
    //
    // 1. Current repeated string
    // 2. Current repeated string + one extra copy of 'a'
    //
    // If still not found, it is impossible.
    //
    // Time Complexity:
    // O((m+n) * n) approximately (depends on Java's indexOf implementation)
    // In practice, very efficient.
    //
    // Space Complexity:
    // O(m + n)
    // (StringBuilder stores repeated string)
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
    // Approach 4 : Rabin-Karp
    // ------------------------------------------------------------
    // Time:
    // Building the repeated string: O(n)
    //Rabin-Karp search:
        //Pattern hash: O(m)
        //First window hash: O(m)
        //Rolling window: O(n)
    // Average: O(n + m)
    //Worst: O(n × m)
    public int repeatedStringMatch(String a, String b) {

        StringBuilder sb = new StringBuilder();
        int count = 0;

        // Repeat until the repeated string becomes at least as long as b
        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        // Check current repeated string
        if (rabinKarp(sb.toString(), b))
            return count;

        // One extra repetition handles overlap across the boundary
        sb.append(a);

        if (rabinKarp(sb.toString(), b))
            return count + 1;

        return -1;
    }

    // ------------------------------------------------------------
    // Rabin-Karp String Matching
    // ------------------------------------------------------------
    private boolean rabinKarp(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        if (m > n)
            return false;

        long BASE = 31;
        long MOD = 1_000_000_007;

        long patternHash = 0;
        long windowHash = 0;
        long power = 1;

        // Compute BASE^(m-1)
        for (int i = 0; i < m - 1; i++) {
            power = (power * BASE) % MOD;
        }

        // Compute hash of pattern and first window
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + (pattern.charAt(i) - 'a' + 1)) % MOD;
            windowHash = (windowHash * BASE + (text.charAt(i) - 'a' + 1)) % MOD;
        }

        // Slide the window
        for (int i = 0; i <= n - m; i++) {

            // If hashes match, verify characters (collision check)
            if (patternHash == windowHash) {
                boolean same = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        same = false;
                        break;
                    }
                }

                if (same)
                    return true;
            }

            // Update hash for next window
            if (i < n - m) {

                // Remove outgoing character's contribution
                windowHash = (windowHash - (text.charAt(i) - 'a' + 1) * power) % MOD;

                // Convert negative modulo into positive
                if (windowHash < 0)
                    windowHash += MOD;

                // Shift remaining characters one position left
                windowHash = (windowHash * BASE) % MOD;

                // Add incoming character
                windowHash = (windowHash + (text.charAt(i + m) - 'a' + 1)) % MOD;
            }
        }

        return false;
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
