package Problems.SearchingProblems.BinarySearch.Answers.Hard;

import java.util.Arrays;

public class SplitArrayWithLargestSum {
    // Problem Link: https://leetcode.com/problems/split-array-largest-sum/

    // Brute Force: O(n) time | O(1) space
    public static int splitArray(int[] nums, int m) {
        if(nums.length < m) return -1;
        int minSum = Arrays.stream(nums).max().getAsInt(); // minSum = max(nums) = sum(nums
        int maxSum = Arrays.stream(nums).sum(); // maxSum = sum(nums)
        for(int i=minSum; i<=maxSum; i++){ // O(n) time
            if(countSubarrays(nums, i) <= m){
                return i;// O(n) time
            }
        }
        return -1;
    }

    public static int countSubarrays(int[] nums, int sum){
        int subArrays = 1;
        int currSum = 0;
        for (int num : nums) { // O(n) time
            currSum += num;
            if (currSum > sum) {
                currSum = num;
                subArrays++; // O(n) time
            }
        }
        return subArrays;

    }

    // Optimal Solution: O(nlogn) time | O(1) space
    public static int splitArrayOptimal(int[] nums, int m) {
        int low = Arrays.stream(nums).max().getAsInt(), high = Arrays.stream(nums).sum();
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (countSubarrays(nums, mid) <= m) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        int[] nums = {7,2,5,10,8};
        int m = 2;
        System.out.println(splitArray(nums, m));
        System.out.println(splitArrayOptimal(nums, m));
    }
}
