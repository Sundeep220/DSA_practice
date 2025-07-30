package Problems.GoodProblems;

import java.util.Stack;

public class SubarrayMinSum {
    // Problem: https://leetcode.com/problems/sum-of-subarray-minimums/description/
    // Brute Force: Generate all subarrays and find the minimum element in each subarray.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        int mod = (int)1e9 + 7;
        long result = 0;

        for (int i = 0; i < n; i++) {
            int min = arr[i];
            for (int j = i; j < n; j++) {
                min = Math.min(min, arr[j]);
                result = (result + min) % mod;
            }
        }

        return (int)result;
    }

    // Optimal Solution: Using Stack
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    // Combination of NGE and NSE will be applied,
    // for eg: {3, 1, 2, 4}
    // how many times each element conrtibutes to our answer:
    // 3: 1
    // 1: 6
    // 2: 2
    // 4: 1
    // so answer = 3 * 1 + 1 * 6 + 2 * 2 + 4 * 1 = 17
    // Now for above array eg: we can see that for 3, there is only 1 subarray [3] which has 3 as its min, (not [3,1,2,4] as its min is 1)
    // so we just need to check for every element, get Next Smallest element and whats previous smallest element to it and then just sburtact those indexes to get elements to
    // consider for generating subarrays. so lets say we got 2 elements in left and 3 element in right, so total number of subarrays that can be generated is 2 * 3 = 6
    // In-case where there are no PSE and NSE we just assign them to cornor indexes (-1 or n )

    public static int sumSubarrayMinsOptimal(int[] arr) {
        int n = arr.length;
        int[] next = findNextGreaterElement(arr);  // Next Smaller Element
        int[] prev = findPreviousSmallerOrEqualElement(arr);  // Previous Smaller Element

        int mod = (int)1e9 + 7;
        long result = 0;
        
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
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                stack.pop();
            }

            next[i] = stack.isEmpty() ? n : stack.peek();  // Store the index of the next greater element
            stack.push(i);  // Push the current index to the stack
        }

        return next;
    }

    public static int[] findPreviousSmallerOrEqualElement(int[] arr) {
        int n = arr.length;
        int[] prev = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                prev[stack.pop()] = i;
            }
            prev[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return prev;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4};
        System.out.println(sumSubarrayMins(arr));  // Output: 17
    }
}
