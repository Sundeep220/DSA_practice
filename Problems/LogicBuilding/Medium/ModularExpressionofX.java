package Problems.LogicBuilding.Medium;

public class ModularExpressionofX {
    //https://www.geeksforgeeks.org/dsa/modular-exponentiation-power-in-modular-arithmetic/
    // Used Binary Exponentiation to calculate x^n % m
    // x^n = { x^(n/2) * x^(n/2) } if n is even
    // x^n = { x^(n/2) * x^(n/2) * x } if n is odd
    // Time Complexity: O(log n)
    public long PowMod(long x, long n, long m) {
        long result = 1;
        x = x % m;

        while (n > 0) {
            if ((n & 1) == 1) { // n is odd or n % 2 == 1
                result = (result * x) % m; // multiply result by x
            }
            // square x and halve n until n = 0
            x = (x * x) % m;
            n = n >> 1;
        }

        return result;
    }
}
