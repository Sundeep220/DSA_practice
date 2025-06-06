# Sieve of Eratosthenes

The **Sieve of Eratosthenes** is a classic and efficient algorithm to find all **prime numbers up to a given number `n`**.

---

## ✅ When to Use

- To find all prime numbers up to `n`
- When multiple queries involve checking if a number ≤ `n` is prime
- As a preprocessing step for problems involving primes

---

## 🧠 Basic Idea

- Create a boolean array `isPrime[0...n]` initialized as `true`.
- Start from the first prime number (2), and **mark all of its multiples as non-prime**.
- Repeat this process for the next number that is still marked as prime.
- Continue this process up to √n.

---

## 📈 Time Complexity

- **O(n log log n)**

This is much faster than checking each number for primality individually, which would be **O(n√n)**.

---

## 🛠️ Steps

1. Create a boolean array `isPrime` of size `n+1`, and initialize all elements to `true`.
2. Mark `isPrime[0]` and `isPrime[1]` as `false`, since 0 and 1 are not prime.
3. Loop from `i = 2` to `sqrt(n)`:
    - If `isPrime[i] == true`:
        - Loop from `j = i*i` to `n`, incrementing by `i`:
            - Mark `isPrime[j] = false`
4. The remaining `true` values in `isPrime` represent prime numbers.

---

## ✅ Code in Java

```java
public class Sieve {
    public static boolean[] sieveOfEratosthenes(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }
}
