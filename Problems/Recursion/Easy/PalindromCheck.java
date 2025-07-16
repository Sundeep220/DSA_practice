package Problems.Recursion.Easy;

public class PalindromCheck {
    // Problem: Program to check is string is palindrom or not using recursion or check if number is palindrom or not using recursion

    public static boolean isStringPalindrome(String str) {
        return checkStringPalindrome(str, 0, str.length() - 1);
    }

    private static boolean checkStringPalindrome(String str, int left, int right) {
        if (left >= right) return true; // base case
        if (str.charAt(left) != str.charAt(right)) return false;
        return checkStringPalindrome(str, left + 1, right - 1);
    }
    public static boolean isNumberPalindrome(int num) {
        return num == reverseNumber(num, 0);
    }

    private static int reverseNumber(int num, int rev) {
        if (num == 0) return rev;
        return reverseNumber(num / 10, rev * 10 + num % 10);
    }

    public static void main(String[] args) {
        String str = "racecar"; // true
        int num = 121; // true
        System.out.println(isStringPalindrome(str));
        System.out.println(isNumberPalindrome(num));
    }
}
