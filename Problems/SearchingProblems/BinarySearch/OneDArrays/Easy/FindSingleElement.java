package Problems.SearchingProblems.BinarySearch.OneDArrays.Easy;

import java.util.ArrayList;
import java.util.Arrays;

public class FindSingleElement {
    // Problem: https://leetcode.com/problems/single-element-in-a-sorted-array/
    // Brute Force: O(n) time | O(1) space
    public static int singleNonDuplicate(int[] nums) {
        int n = nums.length;
        if(n == 1) return nums[0];
        for(int i=0; i<n; i++){
            if(i == 0 && nums[i] != nums[i+1]) return nums[i];
            if(i == n-1 && nums[i] != nums[i-1]) return nums[i];
            if(nums[i] != nums[i-1] && nums[i] != nums[i+1]) return nums[i];
        }
        return -1;
    }

    // Optimal Solution: O(logn) time | O(1) space
    public static int singleNonDuplicateOptimal(int[] nums) {
        int n = nums.length;
        if(n == 1) return nums[0];
        if(nums[0] != nums[1]) return nums[0];
        if(nums[n-1] != nums[n-2]) return nums[n-1];
        int low = 1, high = n-2;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]) return nums[mid];
            // (even , odd) elements are equal => element is on the right half
            // (odd, even) elements are equal => element is on the left half
            // if I am standing on even index, and mid and mid+1(even, odd) are equal, then I should go right OR
            // if I am standing on odd index, and mid and mid-1 are equal, then I should go right
            // Otherwise go left
            if ((mid % 2 == 0 && nums[mid] == nums[mid + 1]) || (mid % 2 == 1 && nums[mid] == nums[mid - 1])) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1,1,2,3,3,4,4,8,8};
        System.out.println(singleNonDuplicate(arr));
    }
}
