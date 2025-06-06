package Algorithms.NamedAlgorthims.SieveOfEratosthenes;

import java.util.Arrays;

public class SieveOfEratosthenes {
    // Algorithm: https://www.geeksforgeeks.org/sieve-of-eratosthenes/
    // This algorithm is used to find prime numbers in a given range.
    // Time Complexity: O(nlog(log(n)))

    static void sieveOfEratosthenes(int n) {
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        prime[0] = prime[1] = false;
        for (int i = 2; i * i <= n; i++) {
            if (prime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    prime[j] = false;
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            if (prime[i]) {
                System.out.print(i + " ");
            }
        }
    }

    public static void main(String[] args) {
        int n = 30;
        sieveOfEratosthenes(n);
    }
}
