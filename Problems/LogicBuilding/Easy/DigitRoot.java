package Problems.LogicBuilding.Easy;

public class DigitRoot {
    // PROBLEM: Given a number N, find the repeated sum of its digits until it becomes a single digit number.
    // Example: Input: N = 1234 Output: 1 + 2 + 3 + 4 = 10 => 1 + 0 = 1 => Answer: 1
// Solution:    Optimal Solution
//    We know that every number in the decimal system can be expressed as a sum of its digits multiplied by powers of 10. For example, a number represented as abcd can be written as follows:
//    abcd = a*10^3 + b*10^2 + c*10^1 + d*10^0
//    We can separate the digits and rewrite this as:
//    abcd = a + b + c + d + (a*999 + b*99 + c*9)
//    abcd = a + b + c + d + 9*(a*111 + b*11 + c)
//
//    This implies that any number can be expressed as the sum of its digits plus a multiple of 9.
//    So, if we take modulo with 9 on each side,
//    abcd % 9 = (a + b + c + d) % 9 + 0
//
//    This means that the remainder when abcd is divided by 9 is equal to the remainder where the sum of its digits (a + b + c + d) is divided by 9.
// Time Complexity: O(1)
// Space Complexity: O(1)
    static int singleDigit(int n) {

        // If given number is zero its
        // digit sum will be zero only
        if (n == 0)
            return 0;

        // If result of modulo operation is
        // zero then, the digit sum is 9
        if(n % 9 == 0)
            return 9;

        return (n % 9);
    }

    // Recursive solution
    // Time Complexity: O(log n)
    // Space Complexity:  O(log N)
    static int digitRoot(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return singleDigit(sum);
    }

}
