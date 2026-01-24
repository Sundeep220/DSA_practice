package Problems.DynamicProgramming.Subsequences;

import java.util.Arrays;

public class PartitionWithMinimumDifferenceI {
    // Problem: https://www.naukri.com/code360/problems/partition-a-set-into-two-subsets-such-that-the-difference-of-subset-sums-is-minimum_842494?leftPanelTabValue=PROBLEM

    // Memoization: Recursion + Memoization (Top-Down DP)
    // Time: O(N * target)
    // Space: O(N * target)
    public boolean subsetSumUtil(int ind, int target, int[] arr, int[][] dp) {
        // Base case: If the target sum is 0, subset is always possible
        if (target == 0)
            // true represented by 1
            return dp[ind][target] == 1;

        // Base case: If only first element is considered, check if it equals target
        if (ind == 0)
            return (dp[ind][target] = (arr[0] == target ? 1 : 0)) == 1;

        // Return previously computed result to avoid recomputation
        if (dp[ind][target] != -1)
            return dp[ind][target] == 1;

        // Recursive case: Exclude current element
        boolean notTaken = subsetSumUtil(ind - 1, target, arr, dp);

        // Recursive case: Include current element if it does not exceed target
        boolean taken = false;
        if (arr[ind] <= target)
            taken = subsetSumUtil(ind - 1, target - arr[ind], arr, dp);

        // Store the result in dp and return
        dp[ind][target] = (notTaken || taken) ? 1 : 0;
        return notTaken || taken;
    }

    // Function to find minimum absolute difference between two subsets
    public int minSubsetSumDifference(int[] arr, int n) {
        // Calculate total sum of array elements
        int totSum = 0;
        for (int val : arr) {
            totSum += val;
        }

        // Initialize dp table with -1 indicating uncalculated states
        int[][] dp = new int[n][totSum + 1];
        for (int[] row : dp)
            Arrays.fill(row, -1);

        // Compute subset sums for all targets from 0 to total sum
        for (int i = 0; i <= totSum; i++) {
            subsetSumUtil(n - 1, i, arr, dp);
        }

        // Initialize minimum difference to a large value
        int mini = Integer.MAX_VALUE;

        // Check all possible subset sums in last row of dp table
        for (int s1 = 0; s1 <= totSum; s1++) {
            if (dp[n - 1][s1] == 1) {
                // Calculate other subset sum and difference
                int s2 = totSum - s1;
                int diff = Math.abs(s1 - s2);

                // Update minimum difference
                mini = Math.min(mini, diff);
            }
        }
        return mini;
    }

    // Tabulation:
    // Time: O(N * target)
    // Space: O(N * target)
    public int minSubsetSumDifferenceII(int[] arr, int n) {
        int totSum = 0;

        // Calculate the total sum of the array
        for (int i = 0; i < n; i++) {
            totSum += arr[i];
        }

        // Initialize a DP table to store the results of the subset sum problem
        boolean[][] dp = new boolean[n][totSum + 1];

        // Base case: If no elements are selected (sum is 0), it's a valid subset
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        // Initialize the first row based on the first element of the array
        if (arr[0] <= totSum)
            dp[0][arr[0]] = true;

        // Fill in the DP table using a bottom-up approach
        for (int ind = 1; ind < n; ind++) {
            for (int target = 1; target <= totSum; target++) {
                // Exclude the current element
                boolean notTaken = dp[ind - 1][target];

                // Include the current element if it doesn't exceed the target
                boolean taken = false;
                if (arr[ind] <= target)
                    taken = dp[ind - 1][target - arr[ind]];

                dp[ind][target] = notTaken || taken;
            }
        }

        int mini = Integer.MAX_VALUE;
        for (int i = 0; i <= totSum; i++) {
            if (dp[n - 1][i]) {
                // Calculate the absolute difference between two subset sums
                int diff = Math.abs(i - (totSum - i));
                mini = Math.min(mini, diff);
            }
        }
        return mini;
    }


    // Space Optimization:
    // Time: O(N * target)
    // Space: O(target)
    public int minSubsetSumDifferenceIII(int[] arr, int n) {
        int totSum = 0;

        // Calculate the total sum of the array
        for (int i = 0; i < n; i++) {
            totSum += arr[i];
        }

        // Initialize a boolean array 'prev' to represent the previous row of the DP table
        boolean[] prev = new boolean[totSum + 1];

        // Base case: If no elements are selected (sum is 0), it's a valid subset
        prev[0] = true;

        // Initialize the first row based on the first element of the array
        if (arr[0] <= totSum)
            prev[arr[0]] = true;

        // Fill in the DP table using a bottom-up approach
        for (int ind = 1; ind < n; ind++) {
            // Create a boolean array 'cur' to represent the current row of the DP table
            boolean[] cur = new boolean[totSum + 1];
            cur[0] = true;

            for (int target = 1; target <= totSum; target++) {
                // Exclude the current element
                boolean notTaken = prev[target];

                // Include the current element if it doesn't exceed the target
                boolean taken = false;
                if (arr[ind] <= target)
                    taken = prev[target - arr[ind]];

                cur[target] = notTaken || taken;
            }

            // Set 'cur' as the 'prev' for the next iteration
            prev = cur;
        }

        int mini = Integer.MAX_VALUE;
        for (int i = 0; i <= totSum; i++) {
            if (prev[i]) {
                // Calculate the absolute difference between two subset sums
                int diff = Math.abs(i - (totSum - i));
                mini = Math.min(mini, diff);
            }
        }
        return mini;
    }

}
