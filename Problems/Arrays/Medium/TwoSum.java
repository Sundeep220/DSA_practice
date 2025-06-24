package Problems.Arrays.Medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    //https://leetcode.com/problems/two-sum/description/

    // Time Complexity: O(n^2), Space Complexity: O(1)
    public static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    // Time Complexity: O(n), Space Complexity: O(n)
    public static int[] twoSumOptimal(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(target - nums[i])) {
                return new int[] { map.get(target - nums[i]), i };
            }
            map.put(nums[i], i);
        }
        return new int[] {-1, -1};
    }

    public static void main(String[] args) {
        int[] nums = { 2, 7, 11, 15 };
        int target = 9;
        System.out.println(Arrays.toString(twoSumBruteForce(nums, target)));
        System.out.println(Arrays.toString(twoSumOptimal(nums, target)));
    }
}
