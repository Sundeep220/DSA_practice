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

    boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
