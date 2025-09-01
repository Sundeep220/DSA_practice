package Problems.Greedy.Medium;

public class ValidParenthesis {
    // Problem: https://leetcode.com/problems/valid-parentheses/

    // Brute Force: Trying out all possible combinations
    // Time Complexity: O(3 ^ n) time | O(1) space
    public static boolean checkValidString(String s) {
        return isValid(s, 0 , 0);
    }

    public static boolean isValid(String s, int index, int open) {
        // open = number of unmatched '(' so far
        if (open < 0) return false; // too many ')'
        if (index == s.length()) return open == 0; // must be balanced at end

        char ch = s.charAt(index);
        if (ch == '(') {
            return isValid(s, index + 1, open + 1);
        } else if (ch == ')') {
            return isValid(s, index + 1, open - 1);
        } else { // '*'
            // 3 possibilities
            return isValid(s, index + 1, open + 1) ||  // '*' -> '('
                    isValid(s, index + 1, open - 1) ||  // '*' -> ')'
                    isValid(s, index + 1, open);        // '*' -> ""
        }
    }

    // Better Solution: Using DP-Memoization
    // Time Complexity: O(n^2) Space Complexity: O(n^2)
    public static boolean checkValidString2(String s) {
        int[][] dp = new int[s.length()][s.length() + 1];  // dp (index, open) -> index goes from 0 to n-1, open goes from 0 to n
        return isValid2(s, 0 , 0, dp);
    }

    public static boolean isValid2(String s, int index, int open, int[][] dp) {
        // open = number of unmatched '(' so far
        if (open < 0) return false; // too many ')'
        if (index == s.length()) return open == 0; // must be balanced at end

        if (dp[index][open] != 0) return dp[index][open] == 1; // memoization

        char ch = s.charAt(index);
        boolean result = false;
        if (ch == '(') {
            result = isValid2(s, index + 1, open + 1, dp);
        } else if (ch == ')') {
            result = isValid2(s, index + 1, open - 1, dp);
        } else { // '*'
            // 3 possibilities
            result = isValid2(s, index + 1, open + 1, dp) ||  // '*' -> '('
                    isValid2(s, index + 1, open - 1, dp) ||  // '*' -> ')'
                    isValid2(s, index + 1, open, dp);        // '*' -> ""
        }

        dp[index][open] = result ? 1 : -1; // memoization
        return result;
    }


    // Optimal Solution: Using Greedy
    // Convert the recursive solution into an iterative solution -> Greedy
    // Idea:
    //- You don’t need to decide immediately how each * is used.
    //- Instead, you just track a range of possible open parentheses counts:
        //low = min possible opens
        //high = max possible opens
    //If at the end low == 0, then some assignment of * makes the string valid. showing that open parenthesis can be closed.

    // Time Complexity: O(n) time | O(1) space
    public static boolean checkValidString3(String s) {
        int min = 0, max = 0;  // min, max is to track the possible open parenthesis
        // min -> min possible opens parenthesis count so far
        // max -> max possible opens parenthesis count so far
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                min++;
                max++; // add open and close
            } else if (s.charAt(i) == ')') {
                min--;
                max--;  // range decreases by 1
            } else {
                min--;  // treating * as closed
                max++;  // treating * as open
            }

            if (min < 0) min = 0; // min can never go below 0
            if (max < 0) return false; // max can never go below 0
        }
        return min == 0;
    }

    public static void main(String[] args) {
        String s = "(*))";
        System.out.println(checkValidString(s));
        System.out.println(checkValidString2(s));
        System.out.println(checkValidString3(s));
    }
}
