package Problems.SearchingProblems.BinarySearch.Arrays.Medium;

public class SearchRotatedArray {
    // Problem: https://leetcode.com/problems/search-in-rotated-sorted-array/

    // Brute Force Solution: O(n) time | O(1) space
    public static int search(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == target) return i;
        }
        return -1;
    }

    // Optimal Solution: O(logn) time | O(1) space
    public static int searchOptimal(int[] nums, int target) {
        int n = nums.length;
        int low = 0, high = n - 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] == target)
                return mid;
            if(nums[low] <= nums[mid]){ // Left half is sorted
                if(target >= nums[low] && target < nums[mid]){  // target is in left half
                    high = mid-1;
                }
                else low = mid+1;
            }
            else{ // Right half is sorted
                if(target > nums[mid] && target <= nums[high]){ // target is in right half
                    low = mid+1;
                }
                else high = mid-1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {4,5,6,7,0,1,2};
        int target = 0;
        System.out.println(searchOptimal(nums, target));
    }
}
