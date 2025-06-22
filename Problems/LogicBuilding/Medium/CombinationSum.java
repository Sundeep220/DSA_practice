package Problems.LogicBuilding.Medium;
import java.util.*;
public class CombinationSum {
    // Problem Link: https://leetcode.com/problems/combination-sum/
    // Time Complexity: Worst case O(2^T) where T is the target sum
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates); // ✅ sort to help with pruning to avoid duplicates
        backtrack(0, candidates, target, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int index, int[] candidates, int target, List<Integer> current, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(current));
            return;
        }

        if (index == candidates.length || target < 0) return;

        // Pick the current number
        current.add(candidates[index]);
        backtrack(index, candidates, target - candidates[index], current, res);
        current.removeLast(); // backtrack

        // Skip the current number
        backtrack(index + 1, candidates, target, current, res);
    }

    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        System.out.println(new CombinationSum().combinationSum(candidates, target));
    }
}
