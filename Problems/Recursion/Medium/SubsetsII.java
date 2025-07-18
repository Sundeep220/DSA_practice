package Problems.Recursion.Medium;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class SubsetsII {
    // Problem: https://leetcode.com/problems/subsets-ii/

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Sort to group duplicates together
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current)); // Add current subset

        for (int i = index; i < nums.length; i++) {
            if (i > index && nums[i] == nums[i - 1]) continue; // Skip duplicates

            current.add(nums[i]);
            backtrack(nums, i + 1, current, result); // Move to next index
            current.remove(current.size() - 1); // Backtrack
        }
    }
}
