package Problems.LogicBuilding.Medium;
import java.util.*;

public class PrintPrimeFactors {
//    🧠 Explanation:
//            🔹 Step-by-step logic:
            //    Remove all 2s:
            //
            //    - 2 is the only even prime. We handle it first to allow skipping even numbers later.
            //
            //    - Try all odd numbers (i = 3, 5, 7...):
            //
                //    For each factor i of N, if i is prime, add to result.
                //
                //    Keep dividing N by i to eliminate its powers.
            //
            //   - Final step:
            //
            //    If remaining N > 2, it must be a prime number.
//

    public int[] AllPrimeFactors(int N) {
        List<Integer> ans = new ArrayList<>();

        // Check for factor 2 separately
        if (N % 2 == 0) {
            ans.add(2);
            while (N % 2 == 0)
                N /= 2;
        }

        // Check odd numbers from 3 onwards
        for (int i = 3; i * i <= N; i += 2) {
            if (N % i == 0) {
                if (isPrime(i)) {
                    ans.add(i);
                }
                while (N % i == 0)
                    N /= i;
            }
        }

        // If N is still > 2, it's a prime number
        if (N > 2)
            ans.add(N);

        // Convert list to int[]
        int[] res = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            res[i] = ans.get(i);
        }
        return res;
    }

    // Optimal Solution: Using Modified Sieve of Eratosthenes
    // The idea in the SPF approach is to precompute the smallest prime factor (SPF) for every number up to n using a modified sieve.
    // Once SPF is ready, we can efficiently find the unique prime factors of any number by repeatedly dividing it by its SPF. This
    // makes each factorization run in O(log n) time.
    //Step by step approach:
        //Precompute spf for every number up to n:
        //Create an array spf[] of size n+1, where spf[i] stores the smallest prime factor of i.
        //Initialize spf[i] = i for all i.
        //Use the Sieve of Eratosthenes, for each prime i, mark spf[j] = i for all multiples j of i (if not already marked).
        //To find unique prime factors of a number n:
        //Repeatedly divide n by spf[n] (i.e n = n/spf[n]).
        //Store each new spf[n] encountered in a set (to keep it unique and in ascending order).
        //Continue until n becomes 1.
    public static List<List<Integer>> primeFactors(List<Integer> queries) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = queries.size();
        int[] spf = new int[n + 1];

        // precompute smallest prime factor
        for (int i = 0; i <= n; i++) {
            spf[i] = i;
        }

        // find smallest prime factor for every number
        for (int i = 2; i * i <= n; i++) {
            if (spf[i] == i) {
                for (int j = i * i; j <= n; j += i)
                    if (spf[j] == j)
                        spf[j] = i;
            }
        }

        // find unique prime factors for each query number
        for (int i = 0; i < n; i++) {
            List<Integer> factors = new ArrayList<>();
            int num = queries.get(i);
            while (num > 1) {
                factors.add(spf[num]);
                num /= spf[num];
            }
            ans.add(factors);
        }
        return ans;
    }

    boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
