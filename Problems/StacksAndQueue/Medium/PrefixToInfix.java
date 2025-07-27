package Problems.StacksAndQueue.Medium;

import java.util.Stack;

public class PrefixToInfix {
    // Convert prefix expression to infix expression
    // Iterate from right to left
    public static String prefixToInfix(String prefix) {
        Stack<String> stack = new Stack<>();
        for(int i = prefix.length() - 1; i >= 0; i--) {
            char c = prefix.charAt(i);
            if(Character.isLetterOrDigit(c)) {
                stack.push(String.valueOf(c));
            } else {
                // Since we are traversing from right to left we will get 1st operand at second
                String operand1 = stack.pop();
                String operand2 = stack.pop();
                String expr = "(" + operand1 + c + operand2 + ")";
                stack.push(expr);
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String prefix = "*-A/BC-/AKL";
        System.out.println("Prefix: " + prefix);
        String infix = prefixToInfix(prefix);
        System.out.println("Infix: " + infix);
    }
}
