package Problems.GoodProblems;

public class DivideNumbers {
    // Problem: https://leetcode.com/problems/divide-two-integers/


    // BRute Force:
    // Time Complexity: O(dividend)
    // Space Complexity: O(1)
    public static int divideBrute(int dividend, int divisor) {
        int sum = 0, count = 0;
        while(sum + divisor <= dividend) {
            sum += divisor;
            count++;
        }
        return count;
        }

        // Optimal Solution: Using BitManipulation
        // Time Complexity: O(logn)
        // Space Complexity: O(1)

    public static int divideOptimal(int dividend, int divisor) {
        long res = 0;
//        boolean sign = true;
//        if(dividend < 0 && divisor > 0 || dividend > 0 && divisor < 0) sign = false;

        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        boolean isNegative = (dividend < 0) ^ (divisor < 0);  // XOR will be true if either dividend or divisor is negative, but if both are negative then it will be false

        long absDividend = Math.abs((long)dividend);
        long absDivisor = Math.abs((long)divisor);
        while(absDividend >= absDivisor) {
            int count = 0;
            while(absDividend >= (absDivisor << (count+1))) {
                count++;
            }
            res += 1L << count;
            absDividend -= absDivisor << count;
        }

        if(res > Integer.MAX_VALUE) return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        return isNegative ? -(int)res : (int)res;
    }

    public static void main(String[] args) {
        int dividend = 10, divisor = 3;
        System.out.println(divideBrute(dividend, divisor));
        System.out.println(divideOptimal(dividend, divisor));
    }
}
