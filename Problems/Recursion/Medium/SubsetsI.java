package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.List;

public class SubsetsI {
    // Problem: https://leetcode.com/problems/subsets/

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        generateSubsets(nums, 0, new ArrayList<>(), ans);
        return ans;
    }

    public void generateSubsets(int[] nums, int index, List<Integer> subset, List<List<Integer>> ans) {
        if(index == nums.length) {
            ans.add(new ArrayList<>(subset));
            return;
        }

        // Include nums[index]
        subset.add(nums[index]);
        generateSubsets(nums, index + 1, subset, ans);
        subset.remove(subset.size() - 1); // backtrack

        // Exclude nums[index]
        generateSubsets(nums, index + 1, subset, ans);
    }
}
