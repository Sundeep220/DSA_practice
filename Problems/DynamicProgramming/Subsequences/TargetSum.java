package Problems.DynamicProgramming.Subsequences;


import java.util.Arrays;

public class TargetSum {
    // Problem: https://leetcode.com/problems/target-sum/description/
    // This problem is nothing but Count Partitions with Given Difference just the language is different
    static final int MOD = 1_000_000_007;

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try + and - for every element
    // Time: O(2^N)
    // Space: O(N)
    // ---------------------------------------------------
    public static int targetSumI(int index, int sum,
                                 int target, int[] nums) {

        if (index == nums.length) {
            return sum == target ? 1 : 0;
        }

        int plus = targetSumI(index + 1,
                sum + nums[index], target, nums);

        int minus = targetSumI(index + 1,
                sum - nums[index], target, nums);

        return plus + minus;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP on transformed problem)
    // Count subsets with sum = K
    // Time: O(N * K)
    // Space: O(N * K)
    // ---------------------------------------------------
    public static int targetSumII(int index, int target,
                                  int[] nums, int[][] dp) {

        if (index == 0) {
            if (target == 0 && nums[0] == 0) return 2;
            if (target == 0 || target == nums[0]) return 1;
            return 0;
        }

        if (dp[index][target] != -1)
            return dp[index][target];

        int notPick = targetSumII(index - 1, target, nums, dp);
        int pick = 0;

        if (nums[index] <= target) {
            pick = targetSumII(index - 1,
                    target - nums[index], nums, dp);
        }

        dp[index][target] = pick + notPick;
        return dp[index][target];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(N * K)
    // Space: O(N * K)
    // ---------------------------------------------------
    public static int targetSumIII(int[] nums, int target) {

        int totalSum = 0;
        for (int num : nums) totalSum += num;

        if (totalSum < Math.abs(target) || (totalSum + target) % 2 != 0)
            return 0;

        int K = (totalSum + target) / 2;
        int n = nums.length;

        int[][] dp = new int[n][K + 1];

        // Base case
        if (nums[0] == 0) {
            dp[0][0] = 2;
        } else {
            dp[0][0] = 1;
        }

        if (nums[0] != 0 && nums[0] <= K) {
            dp[0][nums[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= K; t++) {
                int notPick = dp[i - 1][t];
                int pick = 0;

                if (nums[i] <= t) {
                    pick = dp[i - 1][t - nums[i]];
                }

                dp[i][t] = pick + notPick;
            }
        }

        return dp[n - 1][K];
    }

    // ---------------------------------------------------
    // Space Optimized DP
    // Time: O(N * K)
    // Space: O(K)
    // ---------------------------------------------------
    public static int targetSumIV(int[] nums, int target) {

        int totalSum = 0;
        for (int num : nums) totalSum += num;

        if (totalSum < Math.abs(target) ||
                (totalSum + target) % 2 != 0)
            return 0;

        int K = (totalSum + target) / 2;

        int[] prev = new int[K + 1];

        if (nums[0] == 0) {
            prev[0] = 2;
        } else {
            prev[0] = 1;
        }

        if (nums[0] != 0 && nums[0] <= K) {
            prev[nums[0]] = 1;
        }

        for (int i = 1; i < nums.length; i++) {
            int[] curr = new int[K + 1];
            for (int t = 0; t <= K; t++) {
                int notPick = prev[t];
                int pick = 0;

                if (nums[i] <= t) {
                    pick = prev[t - nums[i]];
                }

                curr[t] = pick + notPick;
            }
            prev = curr;
        }

        return prev[K];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;

        System.out.println("Recursion: "
                + targetSumI(0, 0, target, nums));

        int totalSum = Arrays.stream(nums).sum();
        int K = (totalSum + target) / 2;

        int[][] dp = new int[nums.length][K + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization: "
                + targetSumII(nums.length - 1, K, nums, dp));

        System.out.println("Tabulation: "
                + targetSumIII(nums, target));

        System.out.println("Space Optimized: "
                + targetSumIV(nums, target));
    }
}

