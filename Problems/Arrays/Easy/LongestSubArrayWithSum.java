package Problems.Arrays.Easy;

import java.util.HashMap;
import java.util.Map;

public class LongestSubArrayWithSum {
    // Given an array of positive and negative numbers, find the length of the longest subarray with given sum.
    // Input: arr[] = {1, 2, 3, 2, 1}, sum = 4
    // Output: 3
    // Explanation: Subarray with sum 4 is {2, 1}
//    Example 2:
//    Input Format: N = 5, k = 10, array[] = {2,3,5,1,9}
//    Result: 3
//    Explanation: We got two subarrays with sum 10: {2, 3, 5} and {1, 9}. The longest subarray with sum 10 is {2, 3, 5}. And its length is 3.

    // Using Sliding Window: Only works with positive numbers
    // Time Complexity: O(n), Space Complexity: O(1)
    public static int longestSubarrayWithSum(int[] arr, int sum) {
        int n = arr.length;
        int left = 0, right = 0;
        int currSum = 0, maxLen = 0;

        while (right < n) {
            currSum += arr[right];

            while (currSum > sum && left <= right) {
                currSum -= arr[left];
                left++;
            }

            if (currSum == sum) {
                maxLen = Math.max(maxLen, right - left + 1);
            }

            right++;
        }

        return maxLen;
    }

    // Using Prefix Sum + HashMap
    // Time Complexity: O(n), Space Complexity: O(n)
    // Works with both positive and negative numbers
    // Idea is to store prefix sum with their index,
    // if currentSum - k seen before, that subarray has sum k already found
    // for eg: arr[j+1]+arr[j+2]+...+arr[i] = CurrentSum
    // then CurrentSum - arr[j+1]-arr[j+2]-...-arr[i] = k
    // Time Complexity: O(n), Space Complexity: O(n)
    public static int longestSubarrayWithSumPrefix(int[] arr, int k) {
        // Map to store prefix sum with their index
        Map<Integer, Integer> prefixSumIndex = new HashMap<>();
        int currSum = 0, maxLen = 0;
        // Calculate prefix sum
        for (int i = 0; i < arr.length; i++) {
            currSum += arr[i];

            // If currentSum = k, that subarray has sum k
            if (currSum == k) {
                maxLen = i + 1;
            }

            // If currentSum - k seen before, that subarray has sum k
            if (prefixSumIndex.containsKey(currSum - k)) {
                int len = i - prefixSumIndex.get(currSum - k); // length of subarray
                maxLen = Math.max(maxLen, len);
            }

            // Only store first occurrence of this sum
            if (!prefixSumIndex.containsKey(currSum)) {
                prefixSumIndex.put(currSum, i);
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        int[] arr = {10, 5, 2, 7, 1, -10};
        int sum = 15;
        System.out.println(longestSubarrayWithSum(arr, sum));
        System.out.println(longestSubarrayWithSumPrefix(arr, sum));
    }
}
