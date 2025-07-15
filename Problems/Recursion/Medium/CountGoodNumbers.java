package Problems.Recursion.Medium;

public class CountGoodNumbers {
    private static final int MOD = 1_000_000_007;
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

    // Optimal Solution: Using Binary Exponentiation
    // Time Complexity: O(logn) Space Complexity: O(1)
    public static int countGoodNumbersOptimal(long n) {
        int mod = 1_000_000_007;
        long res = 1;
        int evenPos = (int) (n + 1) / 2;
        int oddPos = (int) n / 2;

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
        System.out.println(countGoodNumbers(n));
        System.out.println(countGoodNumbersOptimal(n));
    }
}
