package Problems.Recursion.Medium;
import java.util.*;

public class Subsets {
    // https://leetcode.com/problems/subsets/
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        for (int num : nums) {
            int n = ans.size();
            for (int i = 0; i < n; i++) {
                List<Integer> list = new ArrayList<>(ans.get(i));
                list.add(num);
                ans.add(list);
            }
        }
        return ans;
    }

    // Using Recursion
    public static List<List<Integer>> subsetsRecursive(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        generateSubsets(nums, 0, new ArrayList<>(), ans);
        return ans;
    }

    public static void generateSubsets(int[] nums, int index, List<Integer> subset, List<List<Integer>> ans) {
        ans.add(new ArrayList<>(subset));
        for (int i = index; i < nums.length; i++) {
            subset.add(nums[i]);
            generateSubsets(nums, i + 1, subset, ans);
            subset.removeLast();
        }
    }

    // Another way of using Recursion
    public static List<List<Integer>> generateSubsetsII(int[] nums, int index, List<Integer> subset, List<List<Integer>> ans) {
        if (index == nums.length) {
            ans.add(new ArrayList<>(subset));
            return ans;
        }

        // Include nums[index]
        subset.add(nums[index]);
        generateSubsetsII(nums, index + 1, subset, ans);
        subset.remove(subset.size() - 1); // backtrack

        // Exclude nums[index]
        generateSubsetsII(nums, index + 1, subset, ans);


        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(subsets(nums));
        System.out.println(subsetsRecursive(nums));
        List<List<Integer>> res = new ArrayList<>();
        res = generateSubsetsII(nums, 0, new ArrayList<>(), res);
        System.out.println(res);
    }
}
