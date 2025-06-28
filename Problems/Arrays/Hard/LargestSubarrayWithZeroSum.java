package Problems.Arrays.Hard;

import java.util.HashMap;
import java.util.Map;

public class LargestSubarrayWithZeroSum {
    // Problem: https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/

    // Brute Force: O(n^2) time | O(1) space
    public static int maxSubArrayLen(int[] nums, int k) {
        int count = 0;
        for(int i=0; i<nums.length; i++){
            for(int j=i; j<nums.length; j++){
                int sum = 0;
                for(int l=i; l<=j; l++){
                    sum += nums[l];
                }
                if(sum == k){
                    count = Math.max(count, j-i+1);
                }
            }
        }
        return count;
    }

    // Optimal Solution: O(n) time | O(n) space
    // Using prefix sum and Hashmap
    public static int maxSubArrayLenOptimal(int[] nums, int k) {
        Map<Integer, Integer> prefix = new HashMap<>(); // storing prefix sum with their starting index where it appears
        int maxLen = 0, curSum = 0;
        for (int i=0; i<nums.length; i++) {
            curSum += nums[i];
            if (curSum == k) {
                maxLen = i + 1; // entire array from 0 to i
            }
            if (prefix.containsKey(curSum - k)) {
                maxLen = Math.max(maxLen, i - prefix.get(curSum - k));
            }
            // Only store first occurrence of this sum
            if (!prefix.containsKey(curSum)) {
                prefix.put(curSum, i);
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        int[] nums =  {9, -3, 3, -1, 6, -5};
        int k = 0;
        System.out.println(maxSubArrayLen(nums, k));
        System.out.println(maxSubArrayLenOptimal(nums, k));
    }
}
