package Problems.SlidingWindowAndTwoPointers.Hard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubarrayWithKDifferent {

    // Brute Force Solution: Get all subarrays and check if they have k distinct elements
    // Time Complexity: O(n^2) Space Complexity: O(1)
    public int subarraysWithKDistinctBrute(int[] nums, int k) {
        int n = nums.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = i; j < n; j++) {
                set.add(nums[j]);
                if (set.size() == k) count++;
                else if (set.size() > k) break;
            }
        }

        return count;
    }




    // Optimal Solution: Using Sliding Window
    // Time Complexity: O(n) Space Complexity: O(k)
    // Idea:
    // 1. Count the number of subarrays with k distinct elements
    // 2. Count the number of subarrays with k - 1 distinct elements
    // 3. Return the difference between the two and we get the number of subarrays with k distinct elements
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMostK(nums, k) - atMostK(nums, k - 1);
    }

    private int atMostK(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        int left = 0, count = 0;

        for (int right = 0; right < n; right++) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            while (map.size() > k) {
                map.put(nums[left], map.get(nums[left]) - 1);
                if (map.get(nums[left]) == 0)
                    map.remove(nums[left]);
                left++;
            }

            count += (right - left + 1);
        }

        return count;
    }
}
