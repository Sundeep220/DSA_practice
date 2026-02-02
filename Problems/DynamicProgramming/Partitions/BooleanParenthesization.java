package Problems.DynamicProgramming.Partitions;



import java.util.Arrays;

public class BooleanParenthesization {

    // ---------------------------------------------------
    // Problem:
    // Boolean Parenthesization
    //
    // Given a boolean expression with:
    // Operands: T (true), F (false)
    // Operators: &, |, ^
    //
    // Count the number of ways to parenthesize
    // the expression such that it evaluates to TRUE.
    //
    // Return answer modulo 1e9+7.
    // ---------------------------------------------------

    static final int MOD = 1000000007;

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    //
    // Intuition:
    // We try every operator as the LAST operator to evaluate.
    //
    // For expression from index i to j:
    // - Pick an operator at position k (i < k < j)
    // - Solve left and right parts
    // - Combine results based on operator truth table
    //
    // State:
    // f(i, j, isTrue)
    // → number of ways exp[i..j] evaluates to isTrue
    //
    // Base case:
    // If i == j:
    //   - return 1 if symbol matches isTrue
    //
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    public static int boolI(int i, int j, boolean isTrue, String exp) {

        if (i > j) return 0;

        if (i == j) {
            if (isTrue)
                return exp.charAt(i) == 'T' ? 1 : 0;
            else
                return exp.charAt(i) == 'F' ? 1 : 0;
        }

        int ways = 0;

        for (int k = i + 1; k <= j - 1; k += 2) {

            char op = exp.charAt(k);

            int lt = boolI(i, k - 1, true, exp);
            int lf = boolI(i, k - 1, false, exp);
            int rt = boolI(k + 1, j, true, exp);
            int rf = boolI(k + 1, j, false, exp);

            ways = (ways + evaluate(op, lt, lf, rt, rf, isTrue)) % MOD;
        }

        return ways;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    //
    // Intuition:
    // The recursive solution recomputes the same
    // (i, j, isTrue) states again and again.
    //
    // So we memoize using:
    // dp[i][j][isTrue]
    //
    // Time: O(n^3)
    // Space: O(n^2)
    // ---------------------------------------------------
    public static int boolII(int i, int j, int isTrue,
                             String exp, int[][][] dp) {

        if (i > j) return 0;

        if (i == j) {
            if (isTrue == 1)
                return exp.charAt(i) == 'T' ? 1 : 0;
            else
                return exp.charAt(i) == 'F' ? 1 : 0;
        }

        if (dp[i][j][isTrue] != -1)
            return dp[i][j][isTrue];

        int ways = 0;

        for (int k = i + 1; k <= j - 1; k += 2) {

            char op = exp.charAt(k);

            int lt = boolII(i, k - 1, 1, exp, dp);
            int lf = boolII(i, k - 1, 0, exp, dp);
            int rt = boolII(k + 1, j, 1, exp, dp);
            int rf = boolII(k + 1, j, 0, exp, dp);

            ways = (ways + evaluate(op, lt, lf, rt, rf, isTrue == 1)) % MOD;
        }

        dp[i][j][isTrue] = ways;
        return ways;
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    //
    // Intuition:
    // dp[i][j][0] → ways exp[i..j] evaluates to FALSE
    // dp[i][j][1] → ways exp[i..j] evaluates to TRUE
    //
    // We increase segment length gradually.
    //
    // Time: O(n^3)
    // Space: O(n^2)
    // ---------------------------------------------------
    public static int boolIII(String exp) {

        int n = exp.length();
        int[][][] dp = new int[n][n][2];

        // Base case: single operands
        for (int i = 0; i < n; i += 2) {
            dp[i][i][1] = exp.charAt(i) == 'T' ? 1 : 0;
            dp[i][i][0] = exp.charAt(i) == 'F' ? 1 : 0;
        }

        // length of expression
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                for (int k = i + 1; k <= j - 1; k += 2) {

                    char op = exp.charAt(k);

                    int lt = dp[i][k - 1][1];
                    int lf = dp[i][k - 1][0];
                    int rt = dp[k + 1][j][1];
                    int rf = dp[k + 1][j][0];

                    dp[i][j][1] = (dp[i][j][1]
                            + evaluate(op, lt, lf, rt, rf, true)) % MOD;

                    dp[i][j][0] = (dp[i][j][0]
                            + evaluate(op, lt, lf, rt, rf, false)) % MOD;
                }
            }
        }

        return dp[0][n - 1][1];
    }

    // ---------------------------------------------------
    // Helper: Evaluate operator truth combinations
    // ---------------------------------------------------
    private static int evaluate(char op,
                                int lt, int lf,
                                int rt, int rf,
                                boolean isTrue) {

        long res = 0;

        if (op == '&') {
            if (isTrue)
                res = (long) lt * rt;
            else
                res = (long) lt * rf + (long) lf * rt + (long) lf * rf;
        } else if (op == '|') {
            if (isTrue)
                res = (long) lt * rt + (long) lt * rf + (long) lf * rt;
            else
                res = (long) lf * rf;
        } else if (op == '^') {
            if (isTrue)
                res = (long) lt * rf + (long) lf * rt;
            else
                res = (long) lt * rt + (long) lf * rf;
        }

        return (int) (res % MOD);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        String exp = "T|T&F";

        // Recursion
        System.out.println("Recursion: "
                + boolI(0, exp.length() - 1, true, exp));

        // Memoization
        int[][][] dp = new int[exp.length()][exp.length()][2];
        for (int[][] mat : dp)
            for (int[] row : mat)
                Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + boolII(0, exp.length() - 1, 1, exp, dp));

        // Tabulation
        System.out.println("Tabulation: "
                + boolIII(exp));
    }
}

