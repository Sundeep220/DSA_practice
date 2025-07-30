package Problems.StacksAndQueue.Easy;

import java.util.Stack;

public class PreviousSmallerElement {

    public static int[] previousSmallerElement(int[] arr) {
        int[] res = new int[arr.length];
        Stack<Integer> stack = new Stack<>();

        // Traverse from left to right
        for (int i = 0; i < arr.length; i++) {
            int current = arr[i];

            // Pop smaller or equal elements
            while (!stack.isEmpty() && stack.peek() >= current) {
                stack.pop();
            }

            // If stack is empty, no smaller element
            res[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current for future elements
            stack.push(current);
        }
        return res;
    }
}
