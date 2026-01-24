package Problems.DynamicProgramming.Subsequences;


public class PartitionEqualSubsetSum {

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Check if subset with sum = target exists
    // Time: O(2^N)
    // Space: O(N) recursion stack
    // ---------------------------------------------------
    public static boolean canPartitionI(int index, int target, int[] nums) {
        if (target == 0) return true;
        if (index == 0) return nums[0] == target;

        boolean notPick = canPartitionI(index - 1, target, nums);

        boolean pick = false;
        if (nums[index] <= target) {
            pick = canPartitionI(index - 1, target - nums[index], nums);
        }

        return pick || notPick;
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Time: O(N * target)
    // Space: O(N * target)
    // ---------------------------------------------------
    public static boolean canPartitionII(int index, int target,
                                         int[] nums, int[][] dp) {

        if (target == 0) return true;
        if (index == 0) return nums[0] == target;

        if (dp[index][target] != -1)
            return dp[index][target] == 1;

        boolean notPick = canPartitionII(index - 1, target, nums, dp);

        boolean pick = false;
        if (nums[index] <= target) {
            pick = canPartitionII(index - 1, target - nums[index], nums, dp);
        }

        dp[index][target] = (pick || notPick) ? 1 : 0;
        return pick || notPick;
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Time: O(N * target)
    // Space: O(N * target)
    // ---------------------------------------------------
    public static boolean canPartitionIII(int[] nums, int target) {
        int n = nums.length;
        boolean[][] dp = new boolean[n][target + 1];

        // Base case: sum 0 always possible
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        // First element
        if (nums[0] <= target) {
            dp[0][nums[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int t = 1; t <= target; t++) {
                boolean notPick = dp[i - 1][t];

                boolean pick = false;
                if (nums[i] <= t) {
                    pick = dp[i - 1][t - nums[i]];
                }

                dp[i][t] = pick || notPick;
            }
        }

        return dp[n - 1][target];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Time: O(N * target)
    // Space: O(target)
    // ---------------------------------------------------
    public static boolean canPartitionIV(int[] nums, int target) {
        int n = nums.length;
        boolean[] prev = new boolean[target + 1];

        prev[0] = true;
        if (nums[0] <= target) {  // doing this to not get array index out of bounds
            prev[nums[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[target + 1];
            curr[0] = true;

            for (int t = 1; t <= target; t++) {
                boolean notPick = prev[t];

                boolean pick = false;
                if (nums[i] <= t) {
                    pick = prev[t - nums[i]];
                }

                curr[t] = pick || notPick;
            }
            prev = curr;
        }

        return prev[target];
    }

    // ---------------------------------------------------
    // Main Function (LeetCode Style)
    // ---------------------------------------------------
    public static boolean canPartition(int[] nums) {
        int totalSum = 0;
        for (int num : nums) totalSum += num;

        // Odd sum → impossible
        if (totalSum % 2 != 0) return false;

        int target = totalSum / 2;
        return canPartitionIV(nums, target);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] nums = {1, 5, 11, 5};

        System.out.println("Partition Equal Subset Sum: "
                + canPartition(nums));
    }
}

