package Problems.Recursion.Medium;

public class MyAtoi {
    // Problem: https://leetcode.com/problems/string-to-integer-atoi/
    // SOlve it using Recursion

    public static int myAtoi(String s) {
        return parseRecursive(s, 0, 0L, 1, false);
    }

    public static int parseRecursive(String s, int i, long num, int sign, boolean signProcessed) {
        if(i >= s.length()) return sign * (int) num;

        char ch = s.charAt(i);

        // 1. Skip whitespace
        if(ch == ' ')
            return parseRecursive(s, i + 1, num, sign, signProcessed);

        // 2. Check for sign
        if(!signProcessed && (ch == '+' || ch == '-')) {
            sign = ch == '+' ? 1 : -1;
            return parseRecursive(s, i + 1, num, sign, true);
        }

        // 3. Read digits
        if(Character.isDigit(ch)){
            int digit = ch - '0';
            num = num * 10 + digit;

            // 4. Clamp to 32-bit integer bounds, as per the problem statement,
            if (sign * num < Integer.MIN_VALUE) return Integer.MIN_VALUE;
            if (sign * num > Integer.MAX_VALUE) return Integer.MAX_VALUE;

            return parseRecursive(s, i + 1, num, sign, false);
        }

        // 5. If not a digit, return
        return sign * (int) num;
    }

    public static void main(String[] args) {
        String s = "   -424545abv";
        System.out.println(myAtoi(s));
    }
}
