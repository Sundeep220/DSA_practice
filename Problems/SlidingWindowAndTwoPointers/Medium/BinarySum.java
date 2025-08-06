package Problems.SlidingWindowAndTwoPointers.Medium;

import java.util.HashMap;
import java.util.Map;

public class BinarySum {
    // Problem: https://leetcode.com/problems/binary-subarrays-with-sum/

    // Brute Force: O(n^2) time | O(1) space
    public static int numSubarraysWithSumBrute(int[] nums, int goal) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum == goal) {
                    count++;
                }
            }
        }
        return count;
    }


    // Better Solution: Using Prefix Sum and HashMap
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int numSubarraysWithSumBetter(int[] nums, int goal) {
        int n = nums.length;
        int count = 0;
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int num : nums) {
            sum += num;
            if (map.containsKey(sum - goal)) {
                count += map.get(sum - goal);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    // Optimal Solution: Using Sliding Window
    // Time Complexity: O(n) Space Complexity: O(1)
    // Idea:
    // 1. Count the number of subarrays with sum <= k
    // 2. Count the number of subarrays with sum <= k - 1
    // 3. Return the difference between the two and we get the number of subarrays with sum == k
    // 4. For calculating the number of subarrays with sum <= k or sum <= k - 1, we use the formula: right - left + 1
    // to get the number of subarrays until the current index(right) where the sum is less than or equal to k or k - 1(for respective function)
    public int numSubarraysWithSumOptimal(int[] nums, int goal) {
        return atMost(nums, goal) - atMost(nums, goal - 1);
    }

    // Count subarrays with sum <= k
    private int atMost(int[] nums, int k) {
        if (k < 0) return 0;
        int left = 0, sum = 0, res = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > k) {
                sum -= nums[left];
                left++;
            }

            res += right - left + 1;
        }

        return res;
    }
}
