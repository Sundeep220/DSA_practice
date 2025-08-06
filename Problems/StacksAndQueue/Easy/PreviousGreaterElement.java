package Problems.StacksAndQueue.Easy;

import java.util.Arrays;
import java.util.Stack;

public class PreviousGreaterElement {

    public static int[] previousGreaterElement(int[] arr) {
        int n = arr.length;
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < n; i++) {
            int current = arr[i];

            while(!stack.isEmpty() && stack.peek() <= current) {
                stack.pop();
            }

            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(current);
        }

        return res;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 4};
        System.out.println(Arrays.toString(previousGreaterElement(arr))); // Output: [-1, -1, 3, 4]
    }
}
