package Problems.Arrays.Hard;

import java.util.HashMap;
import java.util.Map;

public class MaximumProductSubarray {
    // Problem: https://leetcode.com/problems/maximum-product-subarray/


    //Brute Force
    // Time Complexity: O(n^2), Space Complexity: O(1)
    public static int maxProduct(int[] nums) {
        int n = nums.length;
        int max = nums[0];
        for (int i = 0; i < n; i++) {
            int prod = 1;
            for (int j = i; j < n; j++) {
                prod *= nums[j];
                max = Math.max(max, prod);
            }
        }
        return max;
    }

    // Better Solution: Using Observation
    // We know if:
    // if all elements are positive then max product is the product of all elements
    // if all elements are negative then max product is the product of all elements
    // if we have even number of negative numbers then max product is the product of all elements
    // if we have odd number of -ve, then we need to find the prefix and suffix products each -ve number index, and max will be our answer
    // If we encounter any zeros, then we reset our prefix and suffix products = 1
    // Time Complexity: O(n), Space Complexity: O(1)
    public static int maxProductOptimal(int[] nums) {
        int n = nums.length;
        int prefix = 1, suffix = 1;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < n; i++) {
            if(prefix == 0) prefix = 1;
            if(suffix == 0) suffix = 1;
            prefix *= nums[i];
            suffix *= nums[n - i - 1];
            max = Math.max(max, Math.max(prefix, suffix));
        }
        return max;
    }


    public static void main(String[] args) {
        int[] nums = {2, 3, -2, 4};
        System.out.println(maxProduct(nums));
        System.out.println(maxProductOptimal(nums));
    }
}
