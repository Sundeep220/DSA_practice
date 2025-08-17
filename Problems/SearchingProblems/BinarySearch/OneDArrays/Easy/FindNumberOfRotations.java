package Problems.SearchingProblems.BinarySearch.OneDArrays.Easy;

public class FindNumberOfRotations{
    // Problem: https://www.geeksforgeeks.org/find-rotation-count-rotated-sorted-array/
    // Given a rotated array, find the number of times the array has been rotated
    // Similar to finding a minimum element in a rotated array, just keep additional check
    // Time Complexity: O(logn) time | O(1) space
    public int findRotations(int[] nums) {
        int n = nums.length;
        int low = 0, high = n - 1;
        int ans = Integer.MAX_VALUE;
        int index = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[low] <= nums[mid]){ // Left half is sorted, then we take the minimum from left half and then,
                if(nums[low] <= ans){
                    ans = nums[low];// update ans here, and then neglect this half as its not needed
                    index = low;
                }
                low = mid + 1;
            } else {  // Right half is sorted, then we take the minimum from right half and then,
                if(nums[mid] <= ans){ // update ans here, and then neglect this half as its not needed
                    ans = nums[mid];
                    index = mid;
                }
                high = mid - 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] nums = {15, 18, 2, 3, 6, 12};
        System.out.println(new FindNumberOfRotations().findRotations(nums));
    }
}
