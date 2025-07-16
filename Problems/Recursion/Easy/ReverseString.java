package Problems.Recursion.Easy;

public class ReverseString {
    // Problem: https://leetcode.com/problems/reverse-string/

    // Iterative Solution: O(n) time | O(1) space
    public static String reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left <= right) {
            char t = s[left];
            s[left] = s[right];
            s[right] = t;
            left++;
            right--;
        }
        return new String(s);
    }

    // Using recursion
    public static void reveseStringRecursion(char[] s){
        helper(s, 0, s.length - 1);
    }

    public static void helper(char[] s, int left, int right){
        if(left > right) return;

        char t = s[left];
        s[left] = s[right];
        s[right] = t;
        helper(s, ++left, --right);
    }

    public static void main(String[] args) {
        String s = "hello";
        System.out.println(reverseString(s.toCharArray()));
        char[] word = {'h', 'e', 'l', 'l', 'o'};
        reveseStringRecursion(word);
        System.out.println(word);
    }
}
