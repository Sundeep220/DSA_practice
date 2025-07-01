package Problems.SearchingProblems.BinarySearch.OneDArrays.Medium;

public class SearchRotatedArrayII {
    // Problem: https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
    // Optimal Solution: O(logn) time | O(1) space
    public static boolean search(int[] nums, int target) {
        int n = nums.length;
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) return true;
            if (nums[mid] == nums[low] && nums[mid] == nums[high]) {   // just do an extra check here
                low++; high--;
            } else if (nums[mid] >= nums[low]) {  // Left half is sorted
                if (nums[low] <= target && target < nums[mid]) high = mid - 1;
                else low = mid + 1;
            } else {  // Right half is sorted
                if (nums[mid] < target && target <= nums[high]) low = mid + 1;
                else high = mid - 1;
            }
        }
        return false;
    }
}
