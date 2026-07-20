package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneratePermutationsII {

        public List<List<Integer>> permuteUnique(int[] nums) {

            List<List<Integer>> res = new ArrayList<>();

            // Sort duplicates together.
            Arrays.sort(nums);

            boolean[] used = new boolean[nums.length];

            backtrack(
                    0,
                    nums,
                    new ArrayList<>(),
                    res,
                    used
            );

            return res;
        }

        private void backtrack(
                int index,
                int[] nums,
                List<Integer> current,
                List<List<Integer>> res,
                boolean[] used
        ) {

            // We have selected all elements.
            // One complete unique permutation is formed.
            if (index == nums.length) {

                res.add(new ArrayList<>(current));

                return;
            }

            for (int i = 0; i < nums.length; i++) {

                // Already used in the current permutation.
                if (used[i]) {
                    continue;
                }

                /*
                 * Skip duplicate choices at the SAME recursion level.
                 *
                 * Example:
                 *
                 * nums = [1, 1, 2]
                 *
                 * If we already tried the first 1 at this level,
                 * do not start another branch with the second 1.
                 *
                 * !used[i - 1] means:
                 * the previous duplicate is NOT currently being used
                 * in the current permutation.
                 */
                if (i > 0 &&
                        nums[i] == nums[i - 1] &&
                        !used[i - 1]) {

                    continue;
                }

                // CHOOSE
                used[i] = true;
                current.add(nums[i]);

                // EXPLORE
                backtrack(
                        index + 1,
                        nums,
                        current,
                        res,
                        used
                );

                // UNDO CHOICE / BACKTRACK
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
}
