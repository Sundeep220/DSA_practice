package Problems.DynamicProgramming.LIS;



import java.util.*;

public class LargestDivisibleSubset {

    // Problem: https://leetcode.com/problems/largest-divisible-subset/description/
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // State: (index, prevIndex)
    // Time: Exponential
    // Space: O(n)
    // ---------------------------------------------------
    public static int ldsI(int index, int prevIndex, int[] nums) {

        if (index == nums.length)
            return 0;

        // Not take
        int notTake = ldsI(index + 1, prevIndex, nums);

        // Take if divisible
        int take = 0;
        if (prevIndex == -1 || nums[index] % nums[prevIndex] == 0) {
            take = 1 + ldsI(index + 1, index, nums);
        }

        return Math.max(take, notTake);
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(n^2)
    // Space: O(n^2)
    // ---------------------------------------------------
    public static int ldsII(int index, int prevIndex,
                            int[] nums, int[][] dp) {

        if (index == nums.length)
            return 0;

        if (dp[index][prevIndex + 1] != -1)
            return dp[index][prevIndex + 1];

        int notTake = ldsII(index + 1, prevIndex, nums, dp);

        int take = 0;
        if (prevIndex == -1 || nums[index] % nums[prevIndex] == 0) {
            take = 1 + ldsII(index + 1, index, nums, dp);
        }

        dp[index][prevIndex + 1] = Math.max(take, notTake);
        return dp[index][prevIndex + 1];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static List<Integer> ldsIII(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);

        int[] dp = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dp, 1);

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int maxLen = 1;
        int lastIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // Reconstruct subset
        List<Integer> subset = new ArrayList<>();
        subset.add(nums[lastIndex]);

        while (parent[lastIndex] != lastIndex) {
            lastIndex = parent[lastIndex];
            subset.add(nums[lastIndex]);
        }

        Collections.reverse(subset);
        return subset;
    }

    // ---------------------------------------------------
    // Space Optimized
    // NOTE: Same as Tabulation
    // Parent array needed to print subset
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static List<Integer> ldsIV(int[] nums) {
        return ldsIII(nums);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 8};

        System.out.println("Recursion (length): "
                + ldsI(0, -1, Arrays.stream(nums).sorted().toArray()));

        int[][] dp = new int[nums.length][nums.length + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoization (length): "
                + ldsII(0, -1, Arrays.stream(nums).sorted().toArray(), dp));

        System.out.println("Tabulation (subset): "
                + ldsIII(nums));

        System.out.println("Space Optimized (subset): "
                + ldsIV(nums));
    }
}
