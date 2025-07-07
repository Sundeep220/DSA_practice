package Problems.Strings.Easy;

public class RotatedStrings {
    // Problem: https://leetcode.com/problems/rotate-string/description/

    // Brute Force:
    // Time Complexity: O(n^2) time | O(1) space
    public static boolean rotateString(String s, String goal) {
        if(s.length() != goal.length()) return false;
        for(int i = 0; i < s.length(); i++) {
            String temp = s.substring(i) + s.substring(0, i);
            if(temp.equals(goal)) return true;
        }
        return false;
    }

    // Optimal Solution:
    // Time Complexity: O(n) time | O(1) space
    // If goal is a rotation of s, then it must be a substring of s + s
    public static boolean rotateStringOptimal(String s, String goal) {
        return s.length() == goal.length() && (s + s).contains(goal);
    }

    public static void main(String[] args) {
        String s = "abcde", goal = "cdeab"; // Output: true
        System.out.println(rotateString(s, goal));
        System.out.println(rotateStringOptimal(s, goal));
    }
}
