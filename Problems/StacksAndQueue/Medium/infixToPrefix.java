package Problems.StacksAndQueue.Medium;

import java.util.*;

public class infixToPrefix {

    public static String infixToPrefixConversion(String exp) {
        // Step 1: Reverse the expression and swap brackets
        String reversed = reverseAndSwapBrackets(exp);

        // Step 2: Convert to postfix
        String postfix = infixToPostfix(reversed);

        // Step 3: Reverse postfix to get prefix
        return new StringBuilder(postfix).reverse().toString();
    }

//    private static String reverseAndSwapBrackets(String exp) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = exp.length() - 1; i >= 0; i--) {
//            char c = exp.charAt(i);
//            if (c == '(') sb.append(')');
//            else if (c == ')') sb.append('(');
//            else sb.append(c);
//        }
//        return sb.toString();
//    }

    private static String reverseAndSwapBrackets(String exp) {
        char[] arr = exp.toCharArray();
        int i = 0, j = arr.length - 1;

        while (i <= j) {
            // Swap and also convert brackets
            char left = swapBracket(arr[j]);
            char right = swapBracket(arr[i]);
            arr[i] = left;
            arr[j] = right;
            i++;
            j--;
        }
        return new String(arr);
    }

    private static char swapBracket(char c) {
        if (c == '(') return ')';
        if (c == ')') return '(';
        return c;
    }

    private static String infixToPostfix(String exp) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : exp.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop(); // pop '('
            } else { // operator
                while (!stack.isEmpty() && (precedence(c) < precedence(stack.peek()) ||
                        (precedence(c) == precedence(stack.peek()) && c != '^'))) {  // you cannot store two '^' in stack simultaneously
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    private static int precedence(char c) {
        return switch (c) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    public static void main(String[] args) {
        String infix = "((A+B)*C-D)*E";
        System.out.println("Infix:   " + infix);
        System.out.println("Prefix:  " + infixToPrefixConversion(infix));  // Output: *-*+ABCDE
    }
}
