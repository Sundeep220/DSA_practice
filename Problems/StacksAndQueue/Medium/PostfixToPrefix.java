package Problems.StacksAndQueue.Medium;

import java.util.Stack;

public class PostfixToPrefix {
    // Convert postfix expression to prefix expression

    public static String postfixToPrefix(String postfix) {
       Stack<String> stack = new Stack<>();

        for (char c : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                stack.push(String.valueOf(c));
            } else {
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String expr = c + operand1 + operand2;
                stack.push(expr);
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        String postfix = "AB+CD-*";  // Postfix expression
        System.out.println("Postfix: " + postfix);
        String prefix = postfixToPrefix(postfix);
        System.out.println("Prefix: " + prefix);  // Output: *+AB-CD
    }
}
