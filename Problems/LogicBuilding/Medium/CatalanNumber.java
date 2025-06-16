package Problems.LogicBuilding.Medium;

import java.util.Arrays;

public class CatalanNumber {
    // Function to calculate the nth Catalan number
    // This problem can be asked in different forms, such as:
    // 1. The number of binary trees with n nodes
    // 2. The number of ways to partition a set of n elements into k subsets
    // 3. The number of ways to place n queens on an n x n chessboard
    // 4. Count the number of expressions containing n pairs of parentheses that are correctly matched.
    // 5. Count the number of full binary trees (A rooted binary tree is full if every vertex has either two children or no children) with n+1 leaves.
    // 6. Given a number n, return the number of ways you can draw n chords in a circle with 2 x n points such that no 2 chords intersect.

//    🚀 Rule of Thumb
//    If a problem:
//      - Has a recursive structure
//      - Involves combinations or arrangements with constraints
//      - Needs to avoid crossings, invalid nesting, or improper orderings

    // Time Complexity: O(2^n)
    // Space Complexity: O(n)
    public static long catalanRecursive(int n) {
        if (n <= 1) {
            return 1;
        }

        long catalanNumber = 0;
        for (int i = 0; i < n; i++) {
            catalanNumber += catalanRecursive(i) * catalanRecursive(n - i - 1);
        }

        return catalanNumber;
    }

    // Time Complexity: O(n^2)
    // Space Complexity: O(n)
    public static long catalanMemo(int n, long[] dp) {
        if (n <= 1)
            return 1;

        // If already calculated, return it
        if (dp[n] != -1)
            return dp[n];

        long res = 0;
        for (int i = 0; i < n; i++) {
            res += catalanMemo(i, dp) * catalanMemo(n - i - 1, dp);
        }

        dp[n] = res;
        return res;
    }

    static long catalanTabulation(int n) {
        long[] catalan = new long[n + 1];
        catalan[0] = 1;
        catalan[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - j - 1];
            }
        }

        return catalan[n];
    }

    // Time Complexity: O(n)
    // Space Complexity: O(1)
    static int catalanBinomial(int n) {
        // Calculate the binomial coefficient (2n, n)
        // As nth term in Catalan number = (2n)! / ((n+1)! * n!) => Binomial Coefficient(2n, n) / (n+1)
        long res = 1L;
        for (int i = 0; i < n; i++) {
            res = res * (2 * n - i) / (i + 1);
        }
        return (int) res/(n+1);
    }

    public static void main(String[] args) {
        int n = 5;
        long[] dp = new long[n + 1];
        Arrays.fill(dp, -1);

        System.out.println(catalanRecursive(n));
        System.out.println(catalanMemo(n, dp));
        System.out.println(catalanTabulation(n));
        System.out.println(catalanBinomial(n));
    }
}
