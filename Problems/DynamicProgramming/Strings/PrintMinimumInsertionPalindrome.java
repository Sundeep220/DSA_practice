package Problems.DynamicProgramming.Strings;

public class PrintMinimumInsertionPalindrome {

    // ---------------------------------------------------
    // Build DP table for minimum insertions
    // ---------------------------------------------------
    private static int[][] buildDP(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i + 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }
        return dp;
    }

    // ---------------------------------------------------
    // Print one palindrome with minimum insertions
    // ---------------------------------------------------
    public static String printPalindrome(String s) {
        int n = s.length();
        int[][] dp = buildDP(s);

        int i = 0, j = n - 1;
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();

        while (i <= j) {

            // Middle character
            if (i == j) {
                left.append(s.charAt(i));
                break;
            }

            // Characters already match
            if (s.charAt(i) == s.charAt(j)) {
                left.append(s.charAt(i));
                right.insert(0, s.charAt(j));
                i++;
                j--;
            }
            // Insert s[i]
            else if (dp[i + 1][j] < dp[i][j - 1]) {
                left.append(s.charAt(i));
                right.insert(0, s.charAt(i));
                i++;
            }
            // Insert s[j]
            else {
                left.append(s.charAt(j));
                right.insert(0, s.charAt(j));
                j--;
            }
        }

        return left.toString() + right.toString();
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        String s = "mbadm";

        String palindrome = printPalindrome(s);
        System.out.println("Palindrome: " + palindrome);
        System.out.println("Insertions needed: "
                + (palindrome.length() - s.length()));
    }
}
