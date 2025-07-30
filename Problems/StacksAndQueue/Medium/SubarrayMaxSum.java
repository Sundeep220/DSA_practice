package Problems.StacksAndQueue.Medium;

import java.util.Stack;

public class SubarrayMaxSum {
    // Problem: Opposite of SubarrayMinSum

    // Brute Force Solution
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int subarrayMaxSum(int[] arr) {
        int n = arr.length;
        int mod = (int)1e9 + 7;
        long result = 0;

        for (int i = 0; i < n; i++) {
            int max = arr[i];
            for (int j = i; j < n; j++) {
                max = Math.max(max, arr[j]);
                result = (result + max) % mod;
            }
        }

        return (int)result;
    }

    // Optimal Solution
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int subarrayMaxSumOptimal(int[] arr) {
        int n = arr.length;
        int mod = (int)1e9 + 7;
        long result = 0;
        int[] next = findNextGreaterElement(arr);  // Next Smaller Element
        int[] prev = findPreviousGreaterOrEqualElement(arr);  // Previous Smaller Element

        for (int i = 0; i < n; i++) {
            long left = i - prev[i];
            long right = next[i] - i;
            result = (result + (long) arr[i] * left * right) % mod;
        }

        return (int)result;
    }

    public static int[] findNextGreaterElement(int[] arr) {
        int n = arr.length;
        int[] next = new int[n];
        Stack<Integer> stack = new Stack<>();  // Stack to store indices of elements

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
                stack.pop();  // Remove elements smaller than or equal to current element
            }

            next[i] = stack.isEmpty() ? n : stack.peek();  // Store the index of the next greater element
            stack.push(i);  // Push the current index to the stack
        }

        return next;
    }

    public static int[] findPreviousGreaterOrEqualElement(int[] arr) {
        int n = arr.length;
        int[] prev = new int[n];
        Stack<Integer> stack = new Stack<>();  // Stack to store indices of elements

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                stack.pop();  // Remove elements smaller than current element
            }

            prev[i] = stack.isEmpty() ? -1 : stack.peek();  // Store the index of the previous greater or equal element
            stack.push(i);  // Push the current index to the stack
        }

        return prev;
    }

}
