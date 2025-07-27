package Problems.StacksAndQueue.Medium;

import java.util.Stack;

public class infixToPostfix {
    // Problem: Convert Infix Expression to Postfix Expression

    public static String infixToPostfix(String exp) {
        Stack<Character> stack = new Stack<>(); // Stack to store operators// >
        StringBuilder postfix = new StringBuilder(); // StringBuilder to store postfix expression// >
        for(char c: exp.toCharArray()) {
            if(Character.isLetterOrDigit(c)) {
                postfix.append(c); // If character is a letter or digit, append it to postfix expression// >
            } else if(c == '(') {
                stack.push(c); // If character is an opening parenthesis, push it to stack// >
            } else if(c == ')') {
                while(!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()); // Pop operators from stack and append them to postfix expression until an opening parenthesis is encountered// >
                }
                stack.pop(); // Pop the opening parenthesis from stack// >
            } else {
                while(!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(stack.pop()); // Pop operators from stack with higher or equal precedence and append them to postfix expression// >
                }
                stack.push(c); // Push the current operator to stack// >
            }
        }
        while(!stack.isEmpty()) {
            postfix.append(stack.pop()); // Pop all remaining operators from stack and append them to postfix expression// >
        }
        return postfix.toString(); // Return postfix expression// >
    }
    public static int precedence(char c) {
        return switch (c) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        String infix = "((A+B)*C-D)*E"; // Infix expression// >
        System.out.println("Infix:   " + infix); // Print infix expression// >
        System.out.println("Postfix: " + infixToPostfix(infix)); // Print postfix expression// >
    }
}
