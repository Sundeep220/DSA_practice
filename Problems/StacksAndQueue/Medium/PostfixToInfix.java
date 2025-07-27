package Problems.StacksAndQueue.Medium;

import java.util.Stack;

public class PostfixToInfix {
    // PRoblem: Convert a postfix expression to infix expression

    public static String postfixToInfix(String postfix) {
        Stack<String> stack = new Stack<>();

        for (char c : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                stack.push(String.valueOf(c));  // Push operand directly
            } else {
                String operand2 = stack.pop();  // Right operand
                String operand1 = stack.pop();  // Left operand
                String expr = "(" + operand1 + c + operand2 + ")";
                stack.push(expr);  // Push combined infix expression
            }
        }

        return stack.pop();  // Final infix expression
    }

    public static void main(String[] args) {
        String postfix = "AB+CD-*";  // Postfix expression
        System.out.println("Postfix: " + postfix);
        String infix = postfixToInfix(postfix);
        System.out.println("Infix: " + infix);
    }
}
