package Problems.DynamicProgramming.Subsequences;


import java.util.Arrays;

public class SubsetSumEqualToK {
    // Problem: https://www.naukri.com/code360/problem-details/subset-sum-equal-to-k_1550954
    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try pick / not pick for each element
    // Time: O(2^N)
    // Space: O(N) recursion stack
    // ---------------------------------------------------
    public static boolean subsetSumI(int index, int target, int[] arr) {
        if (target == 0) return true;
        if (index == 0) return arr[0] == target;

        boolean notPick = subsetSumI(index - 1, target, arr);

        boolean pick = false;
        if (arr[index] <= target) {
            pick = subsetSumI(index - 1, target - arr[index], arr);
        }

        return pick || notPick;
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Cache overlapping subproblems
    // Time: O(N * K)
    // Space: O(N * K) dp + recursion stack
    // ---------------------------------------------------
    public static boolean subsetSumII(int index, int target,
                                      int[] arr, int[][] dp) {

        if (target == 0) return true;
        if (index == 0) return arr[0] == target;

        if (dp[index][target] != -1)
            return dp[index][target] == 1;

        boolean notPick = subsetSumII(index - 1, target, arr, dp);

        boolean pick = false;
        if (arr[index] <= target) {
            pick = subsetSumII(index - 1, target - arr[index], arr, dp);
        }

        dp[index][target] = (pick || notPick) ? 1 : 0;
        return pick || notPick;
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build dp table iteratively
    // Time: O(N * K)
    // Space: O(N * K)
    // ---------------------------------------------------
    public static boolean subsetSumIII(int[] arr, int K) {
        int n = arr.length;
        boolean[][] dp = new boolean[n][K + 1];

        // Base case: sum 0 is always possible 1st base case of recurrence relation
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        // First element  2nd base case of recurrence relation
        if (arr[0] <= K) {
            dp[0][arr[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int target = 1; target <= K; target++) {
                boolean notPick = dp[i - 1][target];

                boolean pick = false;
                if (arr[i] <= target) {
                    pick = dp[i - 1][target - arr[i]];
                }

                dp[i][target] = pick || notPick;
            }
        }

        return dp[n - 1][K];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Use single 1D array
    // Time: O(N * K)
    // Space: O(K)
    // ---------------------------------------------------
    public static boolean subsetSumIV(int[] arr, int K) {
        int n = arr.length;
        boolean[] prev = new boolean[K + 1];

        prev[0] = true;
        if (arr[0] <= K) {
            prev[arr[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[K + 1];
            curr[0] = true;

            for (int target = 1; target <= K; target++) {
                boolean notPick = prev[target];

                boolean pick = false;
                if (arr[i] <= target) {
                    pick = prev[target - arr[i]];
                }

                curr[target] = pick || notPick;
            }
            prev = curr;
        }

        return prev[K];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        int K = 4;
        int n = arr.length;

        int[][] dp = new int[n][K + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Brute Force Recursion: "
                + subsetSumI(n - 1, K, arr));

        System.out.println("Memoization: "
                + subsetSumII(n - 1, K, arr, dp));

        System.out.println("Tabulation: "
                + subsetSumIII(arr, K));

        System.out.println("Space Optimized DP: "
                + subsetSumIV(arr, K));
    }
}
