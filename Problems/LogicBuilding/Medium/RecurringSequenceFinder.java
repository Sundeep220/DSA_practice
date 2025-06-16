package Problems.LogicBuilding.Medium;

import java.util.HashMap;
import java.util.Map;

public class RecurringSequenceFinder {
    // Same as GetFraction but here we are returning the recurring sequence
    // to do that we are adding one more variable to store the last position of the remainders
    // added to result
    public static void findRecurringSequence(int numerator, int denominator) {
        Map<Integer, Integer> remainderPos = new HashMap<>();
        StringBuilder decimal = new StringBuilder();

        int intPart = numerator / denominator;
        int remainder = numerator % denominator;

        decimal.append(intPart);

        if (remainder == 0) {
            System.out.println("No recurring sequence");
            return;
        }

        decimal.append(".");
        int decimalStart = decimal.length(); // mark where decimal digits start

        while (remainder != 0) {
            // If a remainder is already seen, we found a repeating cycle
            if (remainderPos.containsKey(remainder)) {
                int start = remainderPos.get(remainder);
                System.out.println("Recurring sequence is " + decimal.substring(start));
                System.out.println("Division is " + decimal);
                return;
            }

            remainderPos.put(remainder, decimal.length()); // store actual string index
            remainder *= 10;
            int digit = remainder / denominator;
            decimal.append(digit);
            remainder %= denominator;
        }

        System.out.println("No recurring sequence");
        System.out.println("Divsion is " + decimal);
    }


    public static void main(String[] args) {
        findRecurringSequence(8, 3);     // Recurring sequence is 6
        findRecurringSequence(50, 22);   // Recurring sequence is 27
        findRecurringSequence(1, 2);     // No recurring sequence
        findRecurringSequence(13, 5);    // No recurring sequence
    }
}
