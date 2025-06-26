package Problems.Arrays.Medium;

import java.util.HashMap;
import java.util.Map;

public class CountSubarrayWithGivenSum {
    // https://leetcode.com/problems/subarray-sum-equals-k/

    // Brute Force: Find all subarrays and check if their sum is equal to k or not
    // Time Complexity: O(n^3)
    // Space Complexity: O(1)
    public static int subarraySum(int[] nums, int k) {
        int count = 0;
        for(int i=0; i<nums.length; i++){
            for(int j=i; j<nums.length; j++){
                int sum = 0;
                for(int l=i; l<=j; l++){
                    sum += nums[l];
                }
                if(sum == k){
                    count++;
                }
            }
        }
        return count;
    }

    // Slightly Better Solution: Get rid of the third loop and calculate the sum
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int subarraySumSlightly(int[] nums, int k) {
        int count = 0;
        for(int i=0; i<nums.length; i++){
            int sum = 0;
            for(int j=i; j<nums.length; j++){
                sum += nums[j];
                if(sum == k){
                    count++;
                }
            }

        }
        return count;
    }

    // Optimal Solution: Using Prefix Sum and Hashmap
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int subarraySumOptimal(int[] nums, int k) {
        Map<Integer, Integer> prefix = new HashMap<>(); // storing prefix sum with their count
        int count = 0, curSum = 0;
        for (int num : nums) {
            curSum += num;
            if (curSum == k) {
                count++;
            }
            if (prefix.containsKey(curSum - k)) {
                count += prefix.get(curSum - k);
            }
            prefix.put(curSum, prefix.getOrDefault(curSum, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        int[] nums = {3, 1, 2, 4};
        int k = 6;
        System.out.println(subarraySum(nums, k));
        System.out.println(subarraySumSlightly(nums, k));
        System.out.println(subarraySumOptimal(nums, k));
    }
}
