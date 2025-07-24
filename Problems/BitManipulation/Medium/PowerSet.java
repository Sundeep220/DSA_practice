package Problems.BitManipulation.Medium;

import java.util.ArrayList;
import java.util.List;

public class PowerSet {
    // Problem: https://leetcode.com/problems/subsets/

    // Using Bit Manipulation
    public static List<List<Integer>> powerSet(int[] nums) {
        int subsets = 1 << nums.length; // 2^n, n = nums.length
        List<List<Integer>> ans = new ArrayList<>();
        for(int i = 0; i < subsets; i++) {
            List<Integer> subset = new ArrayList<>();
            for(int j = 0; j < nums.length; j++) {
                if((i & (1 << j)) != 0) {
                    subset.add(nums[j]);
                }
            }
            ans.add(subset);
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> ans = powerSet(nums);
        System.out.println(ans);
    }
}
