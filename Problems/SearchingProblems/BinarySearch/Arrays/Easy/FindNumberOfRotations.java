package Problems.SearchingProblems.BinarySearch.Arrays.Easy;

public class FindNumberOfRotations{
    // Problem: https://www.geeksforgeeks.org/find-rotation-count-rotated-sorted-array/
    // Given a rotated array, find the number of times the array has been rotated
    // Similar to finding a minimum element in a rotated array, just keep additional check
    // Time Complexity: O(logn) time | O(1) space
    public int findRotations(int[] nums) {
        int start = 0, end = nums.length - 1;
        int index = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > nums[mid + 1]) {
                index = mid;
                break;
            } else if (nums[mid] < nums[start]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return index + 1;
    }

    public static void main(String[] args) {
        int[] nums = {15, 18, 2, 3, 6, 12};
        System.out.println(new FindNumberOfRotations().findRotations(nums));
    }
}
