package Problems.Strings.StringHashing;

public class ShortestPalindrome {
    // Problem: https://leetcode.com/problems/shortest-palindrome/

    // Brute Force:
    // Time: O(N^2)
    // Space: O(1)
    public String shortestPalindromeBruteForce(String s) {
        int n = s.length();
        for (int end = n - 1; end >= 0; end--) {
            if (isPalindrome(s, 0, end)) {
                String suffix = s.substring(end + 1);
                return new StringBuilder(suffix).reverse().append(s).toString();
            }
        }
        return "";
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--))
                return false;
        }
        return true;
    }

    // Using Forward and Reverse Hash

    public String shortestPalindromeForwardReverseHash(String s) {
        int n = s.length();
        if (n <= 1)
            return s;

        long[] power = buildPower(n);
        long[] forward = buildPrefixHash(s);
        String rev = new StringBuilder(s).reverse().toString();
        long[] reverse = buildPrefixHash(rev);

        int longest = 0;

        for (int i = 0; i < n; i++) {
            if (isPalindrome(0, i, forward, reverse, power, n)) {
                longest = i + 1;
            }
        }

        String suffix = s.substring(longest);
        return new StringBuilder(suffix).reverse().append(s).toString();
    }

    private boolean isPalindrome(int left, int right, long[] forward, long[] reverse, long[] power, int n) {
        long h1 = getHash(left, right, forward,  power);
        int reverseLeft = n - 1 - right;
        int reverseRight = n - 1 - left;
        long h2 = getHash(reverseLeft, reverseRight, reverse, power);
        return h1 == h2;
    }

    private long getHash(int left, int right, long[] prefix, long[] power) {
        if (left == 0)
            return prefix[right];

        long ans = prefix[right] - prefix[left - 1] * power[right - left + 1];
        ans %= MOD;
        if (ans < 0)
            ans += MOD;
        return ans;
    }

    private long[] buildPrefixHash(String s) {
        int n = s.length();
        long[] prefix = new long[n];
        prefix[0] = value(s.charAt(0));
        for (int i = 1; i < n; i++) {
            prefix[i] = (prefix[i - 1] * BASE + value(s.charAt(i))) % MOD;
        }
        return prefix;
    }

    private long[] buildPower(int n) {
        long[] power = new long[n];
        power[0] = 1;
        for (int i = 1; i < n; i++) {
            power[i] = (power[i - 1] * BASE) % MOD;
        }
        return power;
    }

    private int value(char c) {
        return c;
    }


    // Using Rolling Forward and Reverse Hash
    // Time: O(N)
    // Space: O(1)
    private static final long BASE = 131;
    private static final long MOD = 1_000_000_007;

    public String shortestPalindromeRollingForwardReverseHash(String s) {

        long forward = 0;
        long reverse = 0;
        long power = 1;

        int longest = 0;

        for (int i = 0; i < s.length(); i++) {

            int value = s.charAt(i);

            forward = (forward * BASE + value) % MOD;
            reverse = (reverse + value * power) % MOD;

            if (forward == reverse)
                longest = i + 1;

            power = (power * BASE) % MOD;
        }

        String suffix = s.substring(longest);

        return new StringBuilder(suffix)
                .reverse()
                .append(s)
                .toString();
    }

    // Using KMP
    // Time: O(N)
    // Space: O(N)
    public String shortestPalindromeKMP(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        String combined = s + "#" + rev;

        int[] lps = buildLPS(combined);
        int longest = lps[lps.length - 1];

        String suffix = s.substring(longest);

        return new StringBuilder(suffix).reverse().append(s).toString();
    }

    private int[] buildLPS(String s) {
        int[] lps = new int[s.length()];
        int len = 0;
        int i = 1;
        while (i < s.length()) {
            if (s.charAt(i) == s.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0)
                    len = lps[len - 1];
                else
                    i++;
            }
        }
        return lps;
    }

    public static void main(String[] args) {
        ShortestPalindrome sp = new ShortestPalindrome();
        System.out.println(sp.shortestPalindromeBruteForce("aacecaaa"));
        System.out.println(sp.shortestPalindromeKMP("aacecaaa"));
        System.out.println(sp.shortestPalindromeRollingForwardReverseHash("aacecaaa"));
        System.out.println(sp.shortestPalindromeForwardReverseHash("aacecaaa"));
    }
}
