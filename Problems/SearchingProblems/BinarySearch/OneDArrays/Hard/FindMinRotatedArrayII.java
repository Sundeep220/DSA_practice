package Problems.SearchingProblems.BinarySearch.OneDArrays.Hard;

public class FindMinRotatedArrayII {
    // Problem: https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
    // Time Complexity: O(logn) time | O(1) space
    public int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] < nums[high]) {
                // Minimum must be in left half including mid
                high = mid;
            } else if (nums[mid] > nums[high]) {
                // Minimum must be in right half excluding mid
                low = mid + 1;
            } else {
                // nums[mid] == nums[high], can't determine side — safely shrink search space
                high--;
            }
        }

        return nums[low];
    }

    public static void main(String[] args) {
        FindMinRotatedArrayII obj = new FindMinRotatedArrayII();
        System.out.println(obj.findMin(new int[]{3, 3, 3, 3, 1, 3}));
    }
}
