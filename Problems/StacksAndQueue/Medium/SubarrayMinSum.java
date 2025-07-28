package Problems.StacksAndQueue.Medium;

public class SubarrayMinSum {
    // Problem: https://leetcode.com/problems/sum-of-subarray-minimums/description/
    // Brute Force: Generate all subarrays and find the minimum element in each subarray.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        int mod = (int)1e9 + 7;
        long result = 0;

        for (int i = 0; i < n; i++) {
            int min = arr[i];
            for (int j = i; j < n; j++) {
                min = Math.min(min, arr[j]);
                result = (result + min) % mod;
            }
        }

        return (int)result;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4};
        System.out.println(sumSubarrayMins(arr));  // Output: 17
    }
}
