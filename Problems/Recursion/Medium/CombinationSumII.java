package Problems.Recursion.Medium;
import java.util.*;
public class CombinationSumII {
    // Problem: https://leetcode.com/problems/combination-sum-ii/

    public static List<List<Integer>> combinationSumIIBrute(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(candidates);
        subsequenceWithSumKII(candidates, 0, 0, target, new ArrayList<>(), ans);
        return ans;
    }

    public static void subsequenceWithSumKII(int[] nums, int index, int sum, int k, List<Integer> subsequence, List<List<Integer>> ans) {
        if (index == nums.length) {
            if (sum == k && !ans.contains(subsequence)) {
                ans.add(new ArrayList<>(subsequence)); // Create a new List<Integer> and add it to ans
            }
            return;
        }
        // Include nums[index]
        subsequence.add(nums[index]);
        subsequenceWithSumKII(nums, index + 1, sum + nums[index], k, subsequence, ans);
        subsequence.removeLast(); // Backtrack

        // Exclude nums[index]
        subsequenceWithSumKII(nums, index + 1, sum, k, subsequence, ans);
    }

    public static List<List<Integer>> combinationSumOptimal(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(candidates);
        backtrackOptimal(candidates, 0, target, new ArrayList<>(), ans);
        return ans;
    }

    public static void backtrackOptimal(int[] candidates, int index, int target, List<Integer> current, List<List<Integer>> ans) {
        if (target == 0) {
            ans.add(new ArrayList<>(current));
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (i > index && candidates[i] == candidates[i - 1]) continue; // 🔁 Skip duplicates

            if (candidates[i] > target) break; // 💥 Prune path

            current.add(candidates[i]);
            backtrackOptimal(candidates, i + 1, target - candidates[i], current, ans); // ❌ i → ✅ i + 1
            current.removeLast(); // 🔙 backtrack
        }
    }

    public static void main(String[] args) {
        int[] nums = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        System.out.println("Brute Force: " + combinationSumIIBrute(nums, target));
        System.out.println("Optimal: " + combinationSumOptimal(nums, target));
    }
}
