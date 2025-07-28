package Problems.StacksAndQueue.Easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NextGreaterElementI {
    // Problem: https://leetcode.com/problems/next-greater-element-ii/
    // Using Brute Force
    // Time Complexity: O(n1 * n2) Space Complexity: O(n)
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length];
        for(int i = 0; i < nums1.length; i++){
            int index = -1;
            // Step 1: Find index of nums1[i] in nums2
            for (int j = 0; j < nums2.length; j++) {
                if (nums2[j] == nums1[i]) {
                    index = j;
                    break;
                }
            }

            // Step 2: Look for next greater element after index in nums2
            boolean found = false;
            for (int k = index + 1; k < nums2.length; k++) {
                if (nums2[k] > nums1[i]) {
                    res[i] = nums2[k];
                    found = true;
                    break;
                }
            }

            if (!found) res[i] = -1;
        }
        return res;
    }

    // Better Solution: Using HashMap to precompute the index of nums1[i] in nums2
    // Time Complexity:
    // So total: O(n₁ × n₂) (same worst case as brute force)
    //  But the average case is much better because finding the index is O(1) and the scan can break early
    public static int[] nextGreaterElementBetter(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length];
        HashMap<Integer, Integer> indexMap = new HashMap<>();
        // Map indexes of nums2 in map
        for(int i = 0; i< nums2.length; i++){
            indexMap.put(nums2[i], i);
        }
        for(int i = 0; i < nums1.length; i++){
            // Use map to get index of nums1[i]
            int index = indexMap.get(nums1[i]);

            // Step 2: Look for next greater element after index in nums2
            boolean found = false;
            for (int k = index + 1; k < nums2.length; k++) {
                if (nums2[k] > nums1[i]) {
                    res[i] = nums2[k];
                    found = true;
                    break;
                }
            }

            if (!found) res[i] = -1;
        }
        return res;
    }

    // Using Monotonic Stack and HashMap
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int[] nextGreaterElementOptimal(int[] nums1, int[] nums2) {
        Map<Integer, Integer> ngeMap = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        // Step 1: Traverse nums2 in reverse to build next greater map
        for (int i = nums2.length - 1; i >= 0; i--) {
            int current = nums2[i];

            // Maintain decreasing stack: pop smaller or equal elements
            while (!stack.isEmpty() && stack.peek() <= current) {
                stack.pop();
            }

            // If stack is not empty, top is next greater
            if (!stack.isEmpty()) {
                ngeMap.put(current, stack.peek());
            } else {
                ngeMap.put(current, -1);
            }

            // Push current element into stack
            stack.push(current);
        }

        // Step 2: Build result for nums1 using the map
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = ngeMap.get(nums1[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums1 = { 4, 1, 2 };
        int[] nums2 = { 1, 3, 4, 2 };
        System.out.println(Arrays.toString(nextGreaterElement(nums1, nums2)));
        System.out.println(Arrays.toString(nextGreaterElementBetter(nums1, nums2)));
        System.out.println(Arrays.toString(nextGreaterElementOptimal(nums1, nums2)));
    }
}
