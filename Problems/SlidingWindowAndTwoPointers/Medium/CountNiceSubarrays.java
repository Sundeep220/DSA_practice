package Problems.SlidingWindowAndTwoPointers.Medium;

public class CountNiceSubarrays {
    // Problem: https://leetcode.com/problems/count-number-of-nice-subarrays/

    // Brute Force: O(n^2) time | O(1) space
    public static int countSubarrays(int[] nums, int k){
        int n = nums.length;
        int result = 0;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum == k && ((j - i) & 1) == 0) {
                    result++;
                }
            }
        }
        return result;
    }

    // Optimal Solution: O(n) time | O(1) space
    // Using Sliding Window
    public static int countSubarraysOptimal(int[] nums, int k){
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    public static int atMost(int[] nums, int k){
        int n = nums.length;
        int left = 0, right = 0;
        int countOdd = 0, res = 0;

        while (right < n) {
            if ((nums[right] & 1) == 1)
                countOdd++;

            while (countOdd > k && left <= right) {
                if ((nums[left] & 1) == 1)
                    countOdd--;
                left++;
            }

            // all subarrays ending at 'right' and starting from any index in [left..right] are valid
            res += right - left + 1;
            right++;
        }

        return res;
    }
}
