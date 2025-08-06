package Problems.SlidingWindowAndTwoPointers.Medium;

public class MaxConsecutiveOnesIII {
    // Problem: https://leetcode.com/problems/max-consecutive-ones-iii/

    // We can modify the question to max subarray with atlmost k zeroes in it
    // Brute Force: For every subarray find the number of zeroes and check if it is <= k
    // Time Complexity: O(n^2) Space Complexity: O(1)

    public static int longestOnesBrute(int[] nums, int k) {
        int max = 0, n = nums.length;
        for(int i = 0; i < n; i++) {
            int zeroes = 0;
            for(int j = i; j < n; j++) {
                if (nums[j] == 0) zeroes++;
                if (zeroes <= k) {
                    max = Math.max(max, j - i + 1);
                } else {
                    break; // no need to expand further
                }
            }
        }
        return max;
    }

    // Using Sliding Window
    // Time Complexity: O(n + n) Space Complexity: O(1)
    public static int longestOnesBetter(int[] nums, int k) {
        int left = 0, right = 0, max = 0, zeroes = 0;
        while (right < nums.length) {
            if (nums[right] == 0) zeroes++; // count zeroes
            while (zeroes > k) { // while zeroes > k, shrink the window
                if (nums[left] == 0) zeroes--; // count zeroes
                left++; // shrink the window
            }
            max = Math.max(max, right - left + 1); // update max
            right++; // expand the window
        }
        return max;
    }

    // Optimal Solution: Using Optimized Sliding Window
    // Time Complexity: O(n) Space Complexity: O(1)
    public static int longestOnesOptimal(int[] nums, int k) {
        int left = 0, right = 0, max = 0, zeroes = 0;
        while (right < nums.length) {
            if (nums[right] == 0) zeroes++; // count zeroes
            if (zeroes > k) { // while zeroes > k, shrink the window
                if (nums[left] == 0) zeroes--; // count zeroes
                left++; // shrink the window
            }
            if(zeroes <= k)
                max = Math.max(max, right - left + 1); // update max
            right++; // expand the window
        }
        return max;
    }



    public static void main(String[] args) {
        int[] nums = {1,1,1,0,0,0,1,1,1,1,0};
        int k = 2;
        System.out.println(longestOnesBrute(nums, k)); // 6
    }
}
