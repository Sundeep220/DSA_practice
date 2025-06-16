package Problems.LogicBuilding.Medium;

import java.util.HashMap;
import java.util.Map;

public class GetFraction {
    // The idea is to perform the long division that we already do in maths,
    // but here we are keeping track of the remainders and their index in the
    // result string. So if the same remainder is encountered again, we can
    // insert the '(' at the index of the first occurrence and ')' at the end.
    public String fractionToDecimal(int numerator, int denominator) {
        // Edge case for 0
        if (numerator == 0) return "0";

        // Initialize result
        StringBuilder result = new StringBuilder();

        // Handle sign
        // for example, -2/3 = -(2/3) , 2/-3 = -(2/3)
        // XOR will be true if either numerator or denominator is negative, but if both are negative then it will be false
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }

        // Convert to long to avoid overflow
        long dividend = Math.abs((long) numerator);
        long divisor = Math.abs((long) denominator);

        // Append integer part
        // for example, 2/3 = 0 and 2 % 3 = 2
        // result = "0", remainder = 2
        result.append(dividend / divisor);
        long remainder = dividend % divisor;

        // No fractional part
        if (remainder == 0) return result.toString();

        // Start fractional part
        // Since the remainder is not 0, it means there is a fractional part
        // result = "0.", remainder = 2
        result.append(".");

        // Store seen remainders and their index in result
        Map<Long, Integer> map = new HashMap<>();

        while (remainder != 0) {
            if (map.containsKey(remainder)) {
                // Insert '(' at the index of first occurrence
                // remainder = 2 is already in map
                int index = map.get(remainder);
                // result = "0.(6"
                result.insert(index, "(");
                // result = "0.(6)"
                result.append(")");
                break;
            }

            // Store the current position of remainder
            // map<remainder, index> = {2: 2}
            map.put(remainder, result.length());

            // Multiply remainder by 10
            // remainder = 2 * 10 = 20
            remainder *= 10;

            // Append the quotient to result
            // result = "0.6", remainder = 2
            result.append(remainder / divisor);

            // Update remainder
            // remainder = 20 % 3 = 2
            remainder %= divisor;
        }

        return result.toString();
    }
}
