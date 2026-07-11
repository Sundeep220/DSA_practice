package Problems.Arrays.Medium;

import java.util.Arrays;

public class ThreeSumClosest {
    // Problem: https://leetcode.com/problems/3sum-closest/?envType=problem-list-v2&envId=array

    /*
     * Brute Force
     *
     * Generate all possible triplets and keep track of
     * the sum closest to the target.
     *
     * Time Complexity: O(n^3)
     * Space Complexity: O(1)
     */
    public int threeSumClosest(int[] nums, int target) {

        int n = nums.length;
        int closest = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < n - 2; i++) {

            for (int j = i + 1; j < n - 1; j++) {

                for (int k = j + 1; k < n; k++) {

                    int sum = nums[i] + nums[j] + nums[k];

                    if (Math.abs(target - sum) < Math.abs(target - closest)) {
                        closest = sum;
                    }
                }
            }
        }

        return closest;
    }

    /*
     * Optimal Solution - Sorting + Two Pointers
     *
     * Sort the array. Fix one element and use two pointers
     * to find the triplet whose sum is closest to the target.
     *
     * Update the closest sum whenever a better candidate is found.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    public int threeSumClosestOptimal(int[] nums, int target) {

        Arrays.sort(nums);

        int n = nums.length;
        int closest = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < n - 2; i++) {

            int left = i + 1;
            int right = n - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];

                if (Math.abs(target - sum) < Math.abs(target - closest)) {
                    closest = sum;
                }

                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return closest;
    }
}
