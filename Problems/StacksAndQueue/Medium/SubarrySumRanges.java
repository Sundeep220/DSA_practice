package Problems.StacksAndQueue.Medium;

import java.util.Stack;

import static Problems.StacksAndQueue.Medium.SubarrayMaxSum.subarrayMaxSumOptimal;
import static Problems.StacksAndQueue.Medium.SubarrayMinSum.sumSubarrayMinsOptimal;

public class SubarrySumRanges {
    // Problem: https://leetcode.com/problems/sum-of-subarray-ranges/description/
    // Brute Force: Generate all subarrays and find the minimum element in each subarray.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)

    public static long subArrayRanges(int[] nums) {
        long sum = 0;
        for(int i = 0; i < nums.length; i++){
            int mini = nums[i];
            int maxi = nums[i];
            for(int j = i; j < nums.length; j++){
                mini = Math.min(mini, nums[j]);
                maxi = Math.max(maxi, nums[j]);
                sum = sum + (long) (maxi - mini);
            }
        }
        return sum;
    }

    // Optimal Solution: O(n) time | O(1) space
    // Use combination of SubarrayMaxSum and SubarrayMinSum
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static long subArrayRangesOptimal(int[] nums) {
        long maxSum = subarrayMaxSumOptimal(nums);
        long minSum = sumSubarrayMinsOptimal(nums);
        return maxSum - minSum;
    }

    // ---------------------- MAX contribution ----------------------
    public static long subarrayMaxSumOptimal(int[] arr) {
        int n = arr.length;
        long result = 0;
        int[] next = findNextGreaterElement(arr);
        int[] prev = findPreviousGreaterOrEqualElement(arr);

        for (int i = 0; i < n; i++) {
            long left = i - prev[i];     // elements on the left where arr[i] is max
            long right = next[i] - i;    // elements on the right where arr[i] is max
            result += (long) arr[i] * left * right;
        }
        return result;
    }

    // ---------------------- MIN contribution ----------------------
    public static long sumSubarrayMinsOptimal(int[] arr) {
        int n = arr.length;
        int[] next = findNextSmallerElement(arr);
        int[] prev = findPreviousSmallerOrEqualElement(arr);

        long result = 0;
        for (int i = 0; i < n; i++) {
            long left = i - prev[i];    // elements on left where arr[i] is min
            long right = next[i] - i;   // elements on right where arr[i] is min
            result += (long) arr[i] * left * right;
        }
        return result;
    }

    // ---------------------- Monotonic Stack Helpers ----------------------
    // Next Greater Element (strictly greater)
    public static int[] findNextGreaterElement(int[] arr) {
        int n = arr.length;
        int[] next = new int[n];
        Stack<Integer> stack = new Stack<>();  // store indices

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
                stack.pop();
            }
            next[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        return next;
    }

    // Previous Greater Element (strictly greater)
    public static int[] findPreviousGreaterOrEqualElement(int[] arr) {
        int n = arr.length;
        int[] prev = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                stack.pop();
            }
            prev[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return prev;
    }

    // Next Smaller Element (strictly smaller)
    public static int[] findNextSmallerElement(int[] arr) {
        int n = arr.length;
        int[] next = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            next[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        return next;
    }

    // Previous Smaller Element (<= allowed)
    public static int[] findPreviousSmallerOrEqualElement(int[] arr) {
        int n = arr.length;
        int[] prev = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            prev[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return prev;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        System.out.println(subArrayRanges(nums));
        System.out.println(subArrayRangesOptimal(nums));
    }
}
