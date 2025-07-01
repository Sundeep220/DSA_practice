package Problems.SearchingProblems.BinarySearch.OneDArrays.Medium;

public class FindMinRotatedArray {
    // Problem: https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/

    public static int findMin(int[] nums) {
        int n = nums.length;
        int low = 0, high = n - 1;
        int ans = Integer.MAX_VALUE;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[low] <= nums[mid]){ // Left half is sorted, then we take the minimum from left half and then,
                ans = Math.min(ans, nums[low]); // update ans here, and then neglect this half as its not needed
                low = mid + 1;
            } else {  // Right half is sorted, then we take the minimum from right half and then,
                ans = Math.min(ans, nums[mid]); // update ans here, and then neglect this half as its not needed
                high = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {4,5,6,7,0,1,2};
        System.out.println(findMin(nums));
    }
}
