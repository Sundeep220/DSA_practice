package Problems.Recursion.Easy;

import java.util.Stack;

public class ReverseStack {

    // Brute Force: Using a temp stack
    // Time Complexity: O(n) Space Complexity: O(n)
    public static void reverseStack(Stack<Integer> stack) {
        Stack<Integer> temp = new Stack<>();
        while (!stack.isEmpty()) {
            temp.push(stack.pop());
        }
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
    }

    // Using Recurion
    // Time Complexity: O(n^2) Space Complexity: O(n)
    public static void reverseStackRec(Stack<Integer> stack) {
        if (!stack.isEmpty()) {
            int top = stack.pop();
            reverseStackRec(stack);
            insertElement(stack, top);
        }
    }

    private static void insertElement(Stack<Integer> stack, int element) {
        if(stack.isEmpty()){
            stack.push(element);
        } else {
            int top = stack.pop();
            insertElement(stack, element);
            stack.push(top);
        }
    }
}
