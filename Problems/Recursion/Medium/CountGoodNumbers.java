package Problems.Recursion.Medium;

import java.util.HashMap;

public class CountGoodNumbers {
    private static final int MOD = 1_000_000_007;
    private static final char[] evenDigits = {'0', '2', '4', '6', '8'};
    private static final char[] primeDigits = {'2', '3', '5', '7'};
    private static final HashMap<Integer, Integer> memo = new HashMap<>();
    // Problem: https://leetcode.com/problems/count-good-numbers/


    // for good numbers:
    // for even index, choices we have = {0, 2, 4, 6, 8} = 5
    // for odd index, choices we have = {2, 3, 5, 7} = 4
    // Now for a string with length n, we have
    // n + 1 / 2 even index choices = evenPos
    // n / 2 odd index choices = oddPos
    // So to fill 5 even numbers into evenPos we get = 5 ^ evenPos (Simple permutation)
    // to fill 4 odd numbers into oddPos we get = 4 ^ oddPos (Simple permutation)
    // So total number of good numbers = 5 ^ evenPos * 4 ^ oddPos

    // Brute Force: Using Backtracking: generating all good numbers
    // Time Complexity =  O(5^ceil(n/2) * 4^floor(n/2)) Space Complexity: O(n)
    public static int countGoodNumbersBF(long n) {
        return backtrack(new StringBuilder(), n);
    }

    private static int backtrack(StringBuilder current, long n) {
        if (current.length() == n) {
            return 1;
        }

        int idx = current.length();  // Current position
        int count = 0;

        // Choose from allowed digits based on position
        char[] choices = (idx % 2 == 0) ? evenDigits : primeDigits;

        // Explore all possible digits at this index
        for (char digit : choices) {
            current.append(digit);                           // Choose
            count = (count + backtrack(current, n)) % MOD;  // Explore
            current.deleteCharAt(current.length() - 1);      // Backtrack
        }

        return count;
    }

    // Better Solution: Using iterative method
    // Time Complexity: O(n) Space Complexity: O(1)
    public static int countGoodNumbers(long n) {
        int mod = 1_000_000_007;
        long res = 1;
        for(int i = 0; i < n; i++) {
            res *= (i % 2 == 0) ? 5 : 4;
            res %= mod;
        }
        return (int) res;
    }

    // Better Solution: Using recursive method
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int countGoodNumbersRecursive(long n) {
        return helperRec(0, n);
    }

    public static int helperRec(int idx, long n) {
        if(idx == n) return 1;
        int options = (idx % 2 == 0) ? 5 : 4;
        int count = (options * helperRec(idx + 1, n)) % MOD;
        return count;
    }

    // Better Solution: Using DP-Tabulation
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int countGoodNumbersTabulation(long n) {
        int mod = 1_000_000_007;
        long[] dp = new long[(int) n + 1];
        dp[0] = 1;
        for(int i = 1; i <= n; i++) {
            dp[i] = (dp[i - 1] * (i % 2 == 0 ? 5 : 4)) % mod;
        }
        return (int) dp[Math.toIntExact(n)];
    }

    // Better Solution: Using DP-Memoization
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int countGoodNumbersMemo(long n) {
        return (int) helper(0, n);
    }

    public static long helper(int idx, long n) {
        if(idx == n) return 1;
        if(memo.containsKey(idx)) return memo.get(idx);
        long options = (idx % 2 == 0) ? 5 : 4;
        int count = (int) (options * helper(idx + 1, n) % MOD);
        memo.put(idx, count);
        return count;
    }


    // Optimal Solution: Using Binary Exponentiation
    // Time Complexity: O(logn) Space Complexity: O(1)
    public static int countGoodNumbersOptimal(long n) {
        int mod = 1_000_000_007;
        long res = 1;
        long evenPos = (n + 1) / 2;
        long oddPos = n / 2;


        long possibleEven = modPow(5, evenPos, mod);
        long possibleOdd = modPow(4, oddPos, mod);

        res = (possibleEven * possibleOdd) % mod;
        return (int) res;
    }

    public static long modPow(long base, long exp, long mod) {
        if(exp == 0) return 1;
        long res = 1L;
        while(exp != 0){
            if((exp & 1) == 1) {
                res = (res * base) % mod;
            }
            base = (base * base) % mod;
            exp = exp >> 1;
        }
        return res;
    }

    // Recursive modular exponentiation
    private long modPowRecursive(long base, long exp) {
        if (exp == 0) return 1;
        long half = modPowRecursive(base, exp / 2);
        long result = (half * half) % MOD;
        if (exp % 2 == 1) {
            result = (result * base) % MOD;
        }
        return result;
    }

    public static void main(String[] args) {
        long n = 4;
        System.out.println(countGoodNumbersBF(n));
        System.out.println(countGoodNumbersRecursive(n));
        System.out.println(countGoodNumbers(n));
        System.out.println(countGoodNumbersMemo(n));
        System.out.println(countGoodNumbersTabulation(n));
        System.out.println(countGoodNumbersOptimal(n));
    }
}
