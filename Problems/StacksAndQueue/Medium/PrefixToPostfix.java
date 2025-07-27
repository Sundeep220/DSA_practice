package Problems.StacksAndQueue.Medium;

import java.util.Stack;

public class PrefixToPostfix {
    // Convert prefix expression to postfix expression

    public static String prefixToPostfix(String prefix) {
        // Convert prefix expression to postfix expression
        // Iterate from right to left
        Stack<String> stack = new Stack<>();
        for (int i = prefix.length() - 1; i >= 0; i--) {
            char c = prefix.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                stack.push(String.valueOf(c));
            } else {
                // Since we are traversing from right to left we will get 1st operand at second
                String operand1 = stack.pop();
                String operand2 = stack.pop();
                String expr = operand1 + operand2 + c;
                stack.push(expr);
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String prefix = "*-A/BC-/AKL";
        System.out.println("Prefix: " + prefix);
        String postfix = prefixToPostfix(prefix);
        System.out.println("Postfix: " + postfix);
    }
}
