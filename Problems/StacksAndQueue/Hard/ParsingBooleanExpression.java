package Problems.StacksAndQueue.Hard;

import java.util.Stack;

public class ParsingBooleanExpression {
    // Problem: https://leetcode.com/problems/parsing-a-boolean-expression/description/
    // | Metric | Value |
    //| ------ | ----- |
    //| Time   | O(n)  |
    //| Space  | O(n)  |
    public boolean parseBoolExpr(String expression) {

        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {

            // Ignore commas and opening brackets
            if (ch == ',' || ch == '(') {
                continue;
            }

            // If closing bracket, evaluate sub-expression
            if (ch == ')') {

                boolean hasTrue = false;
                boolean hasFalse = false;

                // Collect operands
                while (stack.peek() == 't' || stack.peek() == 'f') {
                    char val = stack.pop();
                    if (val == 't') hasTrue = true;
                    else hasFalse = true;
                }

                // Operator
                char operator = stack.pop();

                char result;
                if (operator == '!') {
                    // NOT has exactly one operand
                    result = hasTrue ? 'f' : 't';
                } else if (operator == '&') {
                    result = hasFalse ? 'f' : 't';
                } else { // '|'
                    result = hasTrue ? 't' : 'f';
                }

                stack.push(result);
            }
            else {
                // Push operators and literals
                stack.push(ch);
            }
        }

        return stack.pop() == 't';
    }

    // Driver
    public static void main(String[] args) {
        ParsingBooleanExpression obj = new ParsingBooleanExpression();

        System.out.println(obj.parseBoolExpr("|(&(t,f,t),!(t))")); // false
        System.out.println(obj.parseBoolExpr("&(t,|(f,t))"));     // true
        System.out.println(obj.parseBoolExpr("!(f)"));            // true
    }
}

