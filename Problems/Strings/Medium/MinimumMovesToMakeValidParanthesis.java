package Problems.Strings.Medium;

import java.util.Stack;

public class MinimumMovesToMakeValidParanthesis {
    // PRoblem: https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/description/

    /*
     * Intuition:
     * open = number of unmatched '('
     *
     * When we see '(':
     *      increase open
     *
     * When we see ')':
     *      if open exists
     *          match it
     *      else
     *          insert '('
     *
     * At the end,
     * every unmatched '(' needs one ')'.
     *
     * Time : O(n)
     * Space: O(1)
     */

    public int minAddToMakeValid(String s) {

        int open = 0;
        int moves = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {

                open++;

            } else {
                if (open > 0)
                    open--;
                else
                    moves++;
            }
        }

        return moves + open;
    }

    /*
     * Intuition:
     * Store every unmatched '(' in the stack.
     * Whenever ')' appears:
     *      if stack isn't empty -> pop
     *      else -> insert '('
     *
     * Remaining '(' need ')'
     *
     * Time : O(n)
     * Space: O(n)
     */

    public int minAddToMakeValidStack(String s) {

        Stack<Character> stack = new Stack<>();
        int ans = 0;
        for(char c : s.toCharArray()){

            if(c=='('){

                stack.push(c);

            }else{
                if(!stack.isEmpty())
                    stack.pop();
                else
                    ans++;
            }
        }

        return ans + stack.size();
    }
}
