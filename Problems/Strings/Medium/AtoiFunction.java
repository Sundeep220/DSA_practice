package Problems.Strings.Medium;

public class AtoiFunction {
    // Problem Link: https://leetcode.com/problems/atoi/

    // Time Complexity: O(n) Space Complexity: O(1)
    public static int myAtoi(String s){
        long ans = 0;
        int i = 0;
        int n = s.length();
        int sign = 1;


        // 1. Skip leading whitespaces
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        // 2. Check for sign
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }

        // 3. Read digits
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            ans = ans * 10 + digit;

            // 4. Clamp to 32-bit integer bounds, as per the problem statement,
            if (sign * ans < Integer.MIN_VALUE) return Integer.MIN_VALUE;
            if (sign * ans > Integer.MAX_VALUE) return Integer.MAX_VALUE;

            i++;
        }
        return (int)(sign * ans);
    }

    public static void main(String[] args) {
        String s = "   -42";
        System.out.println(myAtoi(s));
    }
}
