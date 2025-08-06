package Problems.StacksAndQueue.Medium;

import java.util.Stack;

public class removeKdigits {
    // Problem: https://leetcode.com/problems/remove-k-digits/

    // Brute Force: Generate all subarrays and find the minimum element in each subarray.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)

    public static String removeKdigits(String num, int k) {
        if (k == 0) {
            return num;
        }
        if (num.length() == k) {
            return "0";
        }

        String res = backtrack(num, k, 0, new StringBuilder());
        assert res != null;
        res = res.replaceFirst("^0+(?!$)", ""); // remove leading zeros
        return res.isEmpty() ? "0" : res;
    }

    public static String backtrack(String num, int k, int index, StringBuilder current) {
        if(index == num.length()) {
            if(k == 0) {
                return current.toString();
            }
            return null;
        }

        char c = num.charAt(index);
        // Option 1: Remove current digit if k > 0
        String removeOption = null;
        if (k > 0) {
            removeOption = backtrack(num, k - 1, index + 1, current);
        }

        // Option 2: Keep current digit
        current.append(c);
        String keepOption = backtrack(num, k, index + 1, current);
        current.deleteCharAt(current.length() - 1); // backtrack

        // Compare two options and return the smaller valid one
        return minString(removeOption, keepOption);
    }

    // Utility to return numerically smaller string (handling nulls and leading zeros)
    private static String minString(String a, String b) {
        if (a == null) return b;
        if (b == null) return a;
        return numericCompare(a, b) <= 0 ? a : b;
    }

    // Compare two numeric strings correctly
    private static int numericCompare(String a, String b) {
        a = a.replaceFirst("^0+(?!$)", ""); // remove leading zeros
        b = b.replaceFirst("^0+(?!$)", "");

        // Shorter string is numerically smaller
        if (a.length() != b.length()) return a.length() - b.length();

        // If same length, lexicographic comparison works
        return a.compareTo(b);
    }

    // Optimal Solution: Monotonic Stack
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static String removeKdigitsOptimal(String num, int k) {
        Stack<Character> stack = new Stack<>();

        for (char digit : num.toCharArray()) {
            // Remove larger digits from the stack to get a smaller number
            while (!stack.isEmpty() && k > 0 && stack.peek() > digit) {
                stack.pop();
                k--;
            }
            stack.push(digit);
        }

        // If still k > 0, remove from the end
        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }

        // Build the result from the stack (bottom to top)
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        res.reverse();

        // Remove leading zeros
        while (!res.isEmpty() && res.charAt(0) == '0') {
            res.deleteCharAt(0);
        }

        return res.isEmpty() ? "0" : res.toString();
    }

}
