package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.List;

public class GeneratePermutations {

    /*
     * Problem: https://leetcode.com/problems/permutations/
     *
     * APPROACH 1: Backtracking using a used[] array
     *
     * Intuition:
     * At every recursion level, choose one number that has not
     * already been used in the current permutation.
     *
     * Example: [1, 2, 3]
     *
     * Choose 1
     *   Choose 2
     *     Choose 3 -> [1, 2, 3]
     *
     * After exploring a choice, undo it and try another choice.
     */

    // Time: O(N * N!)
    // Space: O(N) recursion stack + O(N) used array
    // Output Space: O(N * N!)
    public List<List<Integer>> permuteUsingUsedArray(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        boolean[] used = new boolean[nums.length];

        backtrackUsingUsedArray(
                nums,
                used,
                new ArrayList<>(),
                result
        );

        return result;
    }

    private void backtrackUsingUsedArray(
            int[] nums,
            boolean[] used,
            List<Integer> current,
            List<List<Integer>> result
    ) {

        // If current contains all elements,
        // we have formed one complete permutation.
        if (current.size() == nums.length) {

            // Important:
            // Create a copy because current will be modified later.
            result.add(new ArrayList<>(current));

            return;
        }

        // Try every number as the next element.
        for (int i = 0; i < nums.length; i++) {

            // Skip the number if it is already used.
            if (used[i]) {
                continue;
            }

            // CHOOSE
            used[i] = true;
            current.add(nums[i]);

            // EXPLORE
            backtrackUsingUsedArray(
                    nums,
                    used,
                    current,
                    result
            );

            // UNDO CHOICE / BACKTRACK
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }


    /*
     * APPROACH 2: Backtracking using swapping
     *
     * Intuition:
     * At recursion level index, we need to decide
     * which element should be placed at position index.
     *
     * Example:
     *
     * index = 0
     * [1, 2, 3]
     *
     * Try:
     * 1 at index 0 -> [1, 2, 3]
     * 2 at index 0 -> [2, 1, 3]
     * 3 at index 0 -> [3, 2, 1]
     *
     * Then recursively arrange the remaining positions.
     */

    // Time: O(N * N!)
    // Space: O(N) recursion stack
    // Output Space: O(N * N!)
    public List<List<Integer>> permuteUsingSwap(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        backtrackUsingSwap(nums, 0, result);

        return result;
    }

    private void backtrackUsingSwap(
            int[] nums,
            int index,
            List<List<Integer>> result
    ) {

        // All positions have been fixed.
        // Therefore, one complete permutation is formed.
        if (index == nums.length) {

            List<Integer> permutation = new ArrayList<>();

            for (int num : nums) {
                permutation.add(num);
            }

            result.add(permutation);

            return;
        }

        // Try every element from index onwards
        // as a candidate for position index.
        for (int i = index; i < nums.length; i++) {

            // CHOOSE
            // Put nums[i] at the current index.
            swap(nums, index, i);

            // EXPLORE
            // Now recursively arrange the remaining positions.
            backtrackUsingSwap(
                    nums,
                    index + 1,
                    result
            );

            // UNDO CHOICE / BACKTRACK
            // Restore the original array
            // before trying the next possibility.
            swap(nums, index, i);
        }
    }

    private void swap(int[] nums, int i, int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
