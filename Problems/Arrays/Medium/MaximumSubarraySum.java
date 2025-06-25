package Problems.Arrays.Medium;

import java.util.Arrays;
import java.util.HashMap;

public class MaximumSubarraySum {
    // https://leetcode.com/problems/maximum-subarray/

    // Brute force approach: Generating all possible subarrays and checking for the maximum sum
    // Time Complexity: O(n^3), Space Complexity: O(1)
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += nums[k];
                }
                max = Math.max(max, sum);
            }
        }
        return Math.max(max, 0);
    }

    // Better approach: We can optmize and remove the third loop by observation
    // You can see that for arr[] the sum of subarray arr[i..j] = arr[i...j-1] + arr[j]
    // Time Complexity: O(n^2), Space Complexity: O(1)
    public int maxSubArrayObs(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for(int j = i; j < nums.length; j++) {
                sum += nums[j];
                max = Math.max(max, sum);
            }
        }
        return Math.max(max, 0);
    }


    //Optimal Solution: Kadane's Algorithm
    // Time Complexity: O(n), Space Complexity: O(1)
        public int maxSubArrayOptimal(int[] nums) {
            int max = Integer.MIN_VALUE, curr = 0;
            for(int num : nums) {
                curr += num;
                max = Math.max(max, curr);
                if(curr < 0) {
                    curr = 0;
                }
            }
            return Math.max(max, 0);
        }


        public int maxSubArrayWithSubarray(int[] nums) {
            int max = Integer.MIN_VALUE, curr = 0;
            int start=0, end=0, temp=0;
            int curSum = 0;
            for(int i=0; i<nums.length; i++) {
                curSum += nums[i];

                if(curSum > max){
                    max = curSum;;
                    start = temp;
                    end = i;
                }
                if(curSum < 0) {
                    curSum = 0;
                    temp = i+1;
                }
            }

            int[] subarray = Arrays.copyOfRange(nums, start, end + 1);
            System.out.println("Max Sum: " + max);
            System.out.println("Subarray: " + Arrays.toString(subarray));
            return max;

        }


    public static void main(String[] args) {
        int[] nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        MaximumSubarraySum obj = new MaximumSubarraySum();
        System.out.println("Brute Force Solution: " + obj.maxSubArray(nums));
        System.out.println("Better Solution Using Observation: " + obj.maxSubArrayObs(nums));
        System.out.println("Optimal Solution: " + obj.maxSubArrayOptimal(nums));
        System.out.println("Using Subarray: " + obj.maxSubArrayWithSubarray(nums));
    }
}
