package Problems.StacksAndQueue.Easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NextGreaterElement {
    // Problem: this is same as NextGreaterElementII, but we have a normal array instead of circular array
    //Brute Force:
    // Time Complexity: O(n1 * n2) Space Complexity: O(n1)
    public int[] nextGreaterElement(int[] nums){
        int n = nums.length;
        int[] res = new int[nums.length];
        for (int i = 0; i < n; i++) {
            res[i] = -1;  // default
            for (int j = i+1; j < n; j++) {
                if (nums[j] > nums[i]) {
                    res[i] = nums[j];
                    break;
                }
            }
        }
        return res;
    }

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();

        // Traverse from right to left
        for (int i = n - 1; i >= 0; i--) {
            int current = nums[i];

            // Pop smaller or equal elements
            while (!stack.isEmpty() && stack.peek() <= current) {
                stack.pop();
            }

            // If stack is empty, no greater element
            res[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current for future elements
            stack.push(current);
        }

        return res;
    }


}
