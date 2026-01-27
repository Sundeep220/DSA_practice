package Problems.DynamicProgramming.Subsequences;


import java.util.Arrays;

public class CountPartitionsWithGivenDifference {

    static final int MOD = 1_000_000_007;

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Time: O(2^N)
    // Space: O(N)
    // ---------------------------------------------------
    public static int countI(int index, int target, int[] arr) {
        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2;
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        int notPick = countI(index - 1, target, arr);
        int pick = 0;

        if (arr[index] <= target) {
            pick = countI(index - 1, target - arr[index], arr);
        }

        return (pick + notPick) % MOD;
    }

    // ---------------------------------------------------
    // Memoization (Top-Down DP)
    // Time: O(N * K)
    // Space: O(N * K)
    // ---------------------------------------------------
    public static int countII(int index, int target,
                              int[] arr, int[][] dp) {

        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2;
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        if (dp[index][target] != -1)
            return dp[index][target];

        int notPick = countII(index - 1, target, arr, dp);
        int pick = 0;

        if (arr[index] <= target) {
            pick = countII(index - 1, target - arr[index], arr, dp);
        }

        dp[index][target] = (pick + notPick) % MOD;
        return dp[index][target];
    }

    // ---------------------------------------------------
    // Tabulation (Bottom-Up DP)
    // Time: O(N * K)
    // Space: O(N * K)
    // ---------------------------------------------------
    public static int countIII(int[] arr, int D) {
        int totalSum = 0;
        for (int num : arr) totalSum += num;

        if (totalSum < D || (totalSum + D) % 2 != 0)
            return 0;

        int K = (totalSum + D) / 2;
        int n = arr.length;

        int[][] dp = new int[n][K + 1];

        // Base case
        if (arr[0] == 0) {
            dp[0][0] = 2;
        } else {
            dp[0][0] = 1;
        }

        if (arr[0] != 0 && arr[0] <= K) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int target = 0; target <= K; target++) {
                int notPick = dp[i - 1][target];
                int pick = 0;

                if (arr[i] <= target) {
                    pick = dp[i - 1][target - arr[i]];
                }

                dp[i][target] = (pick + notPick) % MOD;
            }
        }

        return dp[n - 1][K];
    }

    // ---------------------------------------------------
    // Space Optimized DP
    // Time: O(N * K)
    // Space: O(K)
    // ---------------------------------------------------
    public static int countIV(int[] arr, int D) {
        int totalSum = 0;
        for (int num : arr) totalSum += num;

        if (totalSum < D || (totalSum + D) % 2 != 0)
            return 0;

        int K = (totalSum + D) / 2;
        int n = arr.length;

        int[] prev = new int[K + 1];

        if (arr[0] == 0) {
            prev[0] = 2;
        } else {
            prev[0] = 1;
        }

        if (arr[0] != 0 && arr[0] <= K) {
            prev[arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[K + 1];
            for (int target = 0; target <= K; target++) {
                int notPick = prev[target];
                int pick = 0;

                if (arr[i] <= target) {
                    pick = prev[target - arr[i]];
                }

                curr[target] = (pick + notPick) % MOD;
            }
            prev = curr;
        }

        return prev[K];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] arr = {5, 2, 5, 1};
        int D = 3;

        int totalSum = Arrays.stream(arr).sum();
        int K = (totalSum + D) / 2;

        int[][] dp = new int[arr.length][K + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Recursion: "
                + countI(arr.length - 1, K, arr));

        System.out.println("Memoization: "
                + countII(arr.length - 1, K, arr, dp));

        System.out.println("Tabulation: "
                + countIII(arr, D));

        System.out.println("Space Optimized: "
                + countIV(arr, D));
    }
}
