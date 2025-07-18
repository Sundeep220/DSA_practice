package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.List;

public class CombinationSum {
    // Problem: https://leetcode.com/problems/combination-sum/

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(candidates, 0, target, new ArrayList<>(), ans);
        return ans;
    }

    public static void backtrack(int[] candidates, int index, int target, List<Integer> current, List<List<Integer>> ans) {
        if (target == 0) {
            ans.add(new ArrayList<>(current));
            return;
        }

        if (index == candidates.length || target < 0) {
            return;
        }

        // Pick the current number if <= target
        if(candidates[index] <= target) {
            current.add(candidates[index]);
            backtrack(candidates, index, target - candidates[index], current, ans);
            current.removeLast(); // backtrack
        }

        // Skip the current number
        backtrack(candidates, index + 1, target, current, ans);
    }

    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> ans = combinationSum(candidates, target);
        System.out.println(ans);
    }
}
