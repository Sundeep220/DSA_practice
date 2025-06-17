package Problems.LogicBuilding.Medium;

public class DivisibilityChecker {
    // Function to check if a number is divisible by a given divisor
    // The idea is to calculate the remainder when the number is divided by the divisor until the last digit is reached
    // If the remainder is 0, then the number is divisible by the divisor
    // This works for both positive and negative numbers and for large numbers
    public static boolean isDivisibleBy(String number, int divisor) {
        int remainder = 0;

        for (int i = 0; i < number.length(); i++) {

            // for eg: 507
            int digit = number.charAt(i) - '0'; // we get digit as 5
            remainder = (remainder * 10 + digit) % divisor; // now we do, 0 * 10 + 5 % 13 = 5
        }

        return remainder == 0;
    }


    public static void main(String[] args) {
        String num1 = "507";
        String num2 = "1001";

        int divisor = 13;

        System.out.println(num1 + " is divisible by " + divisor + "? " + isDivisibleBy(num1, divisor));
        System.out.println(num2 + " is divisible by " + divisor + "? " + isDivisibleBy(num2, divisor));
    }
}
