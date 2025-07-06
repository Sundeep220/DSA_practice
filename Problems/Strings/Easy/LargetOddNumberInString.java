package Problems.Strings.Easy;

public class LargetOddNumberInString {
    // Problem Link: https://leetcode.com/problems/largest-odd-number-in-string/


    // Optimal Solution: O(n) time | O(1) space
    public static String largestOddNumber(String num) {
        for (int i = num.length() - 1; i >= 0; i--) {
            if (num.charAt(i) % 2 == 1) {
                return num.substring(0, i + 1);
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String num = "35427"; // Output: "35427"
        System.out.println(largestOddNumber(num));
    }
}
