package Problems.StacksAndQueue.Easy;

import java.util.Stack;

public class ValidParenthesis {

    // Using Built-in Stack
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);

            // Push opening brackets
            if (current == '(' || current == '{' || current == '[') {
                stack.push(current);
            }
            // Handle closing brackets
            else {
                if (stack.isEmpty()) return false;  // prevent peek() on empty
                char top = stack.peek();
                if ((current == ')' && top == '(') ||
                        (current == '}' && top == '{') ||
                        (current == ']' && top == '[')) {
                    stack.pop();
                } else {
                    return false; // mismatched bracket
                }
            }
        }

        return stack.isEmpty(); // must be empty if all brackets matched
    }

    // Using Arrays
    public boolean isValid2(String s) {
        int n = s.length();
        if (n % 2 != 0) return false; // Odd-length strings can't be valid

        char[] stack = new char[n];
        int top = -1; // Index for top of stack

        for (char c : s.toCharArray()) {
            // Push expected closing bracket
            if (c == '(') stack[++top] = ')';
            else if (c == '{') stack[++top] = '}';
            else if (c == '[') stack[++top] = ']';
            else {
                if (top == -1 || stack[top] != c) return false;
                top--; // Pop from stack
            }
        }

        return top == -1; // If stack is empty, it's valid
    }
}
