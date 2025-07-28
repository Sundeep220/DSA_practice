package Problems.StacksAndQueue.Easy;

import java.util.Arrays;
import java.util.Stack;

public class NextSmallerElement {
    //

    public int[] nextSmallerElement(int[] arr) {
        int n = arr.length;
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();

        // Traverse from right to left
        for (int i = n - 1; i >= 0; i--) {
            // Pop elements that are not smaller than current element
            while (!stack.isEmpty() && stack.peek() >= arr[i]) {
                stack.pop();
            }

            // If stack is empty, no smaller element to the right
            res[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current element for the next iteration
            stack.push(arr[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        int[] arr = {4, 8, 5, 2, 25};
        System.out.println(Arrays.toString(new NextSmallerElement().nextSmallerElement(arr)));
    }
}
