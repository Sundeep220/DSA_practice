package Problems.DynamicProgramming.LIS;

import java.util.*;

public class PrintAllLongestIncreasingSubsequence {

    // ---------------------------------------------------
    // Problem:
    // Print ALL Longest Increasing Subsequences (LIS)
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Intuition (Overall):
    //
    // Step 1:
    // First compute LIS length DP (same as LIS problem).
    //
    // Step 2:
    // Build a DAG (Directed Acyclic Graph) of valid LIS transitions.
    //
    // Step 3:
    // Backtrack from all indices having maximum LIS length
    // and print all sequences.
    //
    // This is a classic:
    // "DP + Backtracking" problem.
    // ---------------------------------------------------

    // ---------------------------------------------------
    // Step 1: Compute LIS Length DP
    //
    // dp[i] → length of LIS ending at index i
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    private static int[] computeLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
        }
        return dp;
    }

    // ---------------------------------------------------
    // Step 2: Build graph for LIS transitions
    //
    // Intuition:
    // For index j -> i:
    // nums[j] < nums[i] AND dp[j] + 1 == dp[i]
    // means j can come before i in some LIS.
    //
    // We store reverse edges:
    // i -> all valid previous j
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    private static Map<Integer, List<Integer>> buildGraph(
            int[] nums, int[] dp) {

        int n = nums.length;
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 == dp[i]) {
                    graph.get(i).add(j);
                }
            }
        }
        return graph;
    }

    // ---------------------------------------------------
    // Step 3: Backtracking to print all LIS
    //
    // Intuition:
    // Start DFS from every index whose dp[i] == maxLen.
    // Move backwards using the graph.
    //
    // We build sequence in reverse and then print it.
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    private static void dfs(int index, int[] nums,
                            Map<Integer, List<Integer>> graph,
                            List<Integer> path, List<List<Integer>> answer) {

        path.add(nums[index]);

        if (graph.get(index).isEmpty()) {
            // Reached start of LIS
            List<Integer> result = new ArrayList<>(path);
            Collections.reverse(result);
            answer.add(result);
            System.out.println(result);
        } else {
            for (int prev : graph.get(index)) {
                dfs(prev, nums, graph, path, answer);
            }
        }

        path.remove(path.size() - 1); // Backtrack
    }

    // ---------------------------------------------------
    // Main function to print all LIS
    //
    // Time: O(n^2)
    // Space: O(n)
    // ---------------------------------------------------
    public static void printAllLIS(int[] nums) {

        int[] dp = computeLIS(nums);

        int maxLen = 0;
        for (int len : dp) {
            maxLen = Math.max(maxLen, len);
        }

        Map<Integer, List<Integer>> graph = buildGraph(nums, dp);

        List<List<Integer>> answer = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (dp[i] == maxLen) {
                dfs(i, nums, graph, new ArrayList<>(), answer);
            }
        }

        System.out.println("List of All LIS: " + answer);
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[] nums = {1, 3, 5, 4, 7};

        System.out.println("All Longest Increasing Subsequences:");
        printAllLIS(nums);
    }
}
