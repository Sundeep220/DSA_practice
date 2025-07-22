package Problems.Recursion.Hard;

import java.util.ArrayList;
import java.util.List;

public class ExpressionAddOperators {
    // Problem: https://leetcode.com/problems/expression-add-operators/

    // Using Recursion and Backtracking
    // Time Complexity: O(4^n)
    // Space Complexity: O(n)
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", num, target, 0, 0, 0);
        return result;
    }

    private void backtrack(List<String> result, String currentExpression, String num, int target, int index, long currentCalc, long prevOperand) {
        if (index == num.length()) {
            if (currentCalc == target) {
                result.add(currentExpression);
            }
            return;
        }

        for (int i = index; i < num.length(); i++) {
            // Skip numbers with leading 0
            if (i != index && num.charAt(index) == '0') break;

            String currentStr = num.substring(index, i + 1);
            long current = Long.parseLong(currentStr);

            if (index == 0) {
                // First number, no operator
                backtrack(result, currentExpression + currentStr, num, target, i + 1, current, current);
            } else {
                // Addition
                backtrack(result, currentExpression + "+" + currentStr, num, target, i + 1, currentCalc + current, current);

                // Subtraction
                backtrack(result, currentExpression + "-" + currentStr, num, target, i + 1, currentCalc - current, -current);

                // Multiplication
                backtrack(result, currentExpression + "*" + currentStr, num, target, i + 1,
                        currentCalc - prevOperand + prevOperand * current,
                        prevOperand * current);
            }
        }
    }


    // Usig StringBuilder and Backtracking
    // Time Complexity: O(4^n)
    // Space Complexity: O(n)
    public List<String> addOperatorsII(String num, int target) {
        List<String> ans = new ArrayList<>();
        backtrackII(ans, num, new StringBuilder(), 0, 0L, target, 0L);
        return ans;
    }

    private void backtrackII(List<String> ans, String num, StringBuilder expr, int index, long currRes, long target, long prevOperand) {
        if (index == num.length()) {
            if (currRes == target) {
                ans.add(expr.toString());
            }
            return;
        }

        int len = expr.length(); // Save current length to backtrack later

        for (int i = index; i < num.length(); i++) {
            // Avoid numbers with leading zeros
            if (i != index && num.charAt(index) == '0') break;

            String currStr = num.substring(index, i + 1);
            long currNum = Long.parseLong(currStr);

            if (index == 0) {
                // First number, directly append
                expr.append(currStr);
                backtrackII(ans, num, expr, i + 1, currNum, target, currNum);
                expr.setLength(len); // backtrack
            } else {
                // Addition
                expr.append('+').append(currStr);
                backtrackII(ans, num, expr, i + 1, currRes + currNum, target, currNum);
                expr.setLength(len); // backtrack

                // Subtraction
                expr.append('-').append(currStr);
                backtrackII(ans, num, expr, i + 1, currRes - currNum, target, -currNum);
                expr.setLength(len); // backtrack

                // Multiplication
                expr.append('*').append(currStr);
                backtrackII(ans, num, expr, i + 1,
                        currRes - prevOperand + prevOperand * currNum, target, prevOperand * currNum);
                expr.setLength(len); // backtrack
            }
        }
    }

    public static void main(String[] args) {
        String num = "105";
        int target = 5;
        ExpressionAddOperators solution = new ExpressionAddOperators();
        List<String> result = solution.addOperators(num, target);
        System.out.println(result);
    }
}
