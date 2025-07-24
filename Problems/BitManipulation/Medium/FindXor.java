package Problems.BitManipulation.Medium;

public class FindXor {

    // Using Bit Manipulation
    // Time Complexity: O(1)
    //n % 4 == 0 → result = n
    //n % 4 == 1 → result = 1
    //n % 4 == 2 → result = n + 1
    //n % 4 == 3 → result = 0
    // To understand the above relations check by doing Xor or numbers starting from 0 to n and for every n you will get a pattern
    // Why take L - 1? :
    // XOR(0 to R) = 0 ^ 1 ^ 2 ^ ... ^ (L - 1) ^ L ^ (L+1) ^ ... ^ R
    //XOR(0 to L - 1) = 0 ^ 1 ^ 2 ^ ... ^ (L - 1)
    //→ XOR(L to R) = XOR(0 to R) ^ XOR(0 to L - 1)
    public static int findXor(int L, int R) {
        return XorTill(R) ^ XorTill(L - 1);
    }

    public static int XorTill(int n) {
        if (n % 4 == 0) return n;
        if (n % 4 == 1) return 1;
        if (n % 4 == 2) return n + 1;
        return 0;
    }
}
