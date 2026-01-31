package Problems.DynamicProgramming.LIS;



import java.util.*;

public class PrintLongestIncreasingSubsequence {

    // ---------------------------------------------------
    // Print one Longest Increasing Subsequence
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static List<Integer> printLIS(int[] nums) {
        int n = nums.length;

        int[] dp = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dp, 1);

        // Initialize parent pointers
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int maxLen = 1;
        int lastIndex = 0;

        // DP computation
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            // Track maximum LIS length
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // Reconstruct LIS
        List<Integer> lis = new ArrayList<>();
        lis.add(nums[lastIndex]);

        while (parent[lastIndex] != lastIndex) {
            lastIndex = parent[lastIndex];
            lis.add(nums[lastIndex]);
        }

        // Reverse to get correct order
        Collections.reverse(lis);
        return lis;
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};

        List<Integer> lis = printLIS(nums);

        System.out.println("LIS length: " + lis.size());
        System.out.println("LIS: " + lis);
    }
}
