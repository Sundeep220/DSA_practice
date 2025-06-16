package Problems.LogicBuilding.Easy;

import java.math.BigInteger;

public class KthDigit {
    static long kthDigit(int a, int b, int k) {
        // This code does not work for large values of a and b
        // Step 1: Calculate a^b using Math.pow (returns double)
        long pow = (long)Math.pow(a, b);

        // Step 2: Convert result to string
        String number = String.valueOf(pow);

        // Step 3: Find k-th digit from right (index: length - k)
        int len = number.length();
        if (k > len) return -1; // k is out of bounds

        return number.charAt(len - k) - '0';
    }

    static long kthDigitUsingMod(int a, int b, int k) {
        // code here
        // The idea is to calculate (a^b) % (10^k) and then return the k-th digit from the right.
        // Why are we doing this? Well, if we have a number like 1234567890, and we want to find the 5th digit from the right,
        // we need to mod it by 10^5 to 67890, then divide it by 10 (k-1)times or in this case 4 times to get 6:
        // like 67890 / 10 => 6789 / 10 => 678 / 10 => 67 / 10 => 6.
        long modValue = (long) Math.pow(10 , k);
        long res = 1;
        long base = a;
        while(b > 0){
            if((b & 1) == 1){
                res = res * base % modValue;
            }

            base = base * base % modValue;
            b >>= 1;
        }

        for(int i = 1; i < k; i++){
            res /= 10;
        }

        return (int) res;
    }

    static int kthDigitWithBigInteger(int a, int b, int k) {
        // Step 1: Calculate a^b using BigInteger.pow
        BigInteger pow = BigInteger.valueOf(a).pow(b);

        // BigInteger is a special data type that is used to represent large integers.
        // It is used to perform operations on very large numbers without overflowing.
        // It is a built-in class in Java. Wrapped on top of the long primitive data type.

        // Step 2: Convert result to string
        String number = pow.toString();

        // Step 3: Find k-th digit from right (index: length - k)
        int len = number.length();
        if (k > len) return -1; // k is out of bounds

        return number.charAt(len - k) - '0';
    }

    public static void main(String[] args) {
        int a = 3434;
        int b = 43542;
        int k = 4;
        System.out.println(kthDigit(a, b, k)); // Will fail for large values of a and b
        System.out.println(kthDigitUsingMod(a, b, k));
        System.out.println(kthDigitWithBigInteger(a, b, k));
    }

}
