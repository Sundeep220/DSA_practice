package Problems.SearchingProblems.BinarySearch.OneDArrays.Easy;

public class SearchInsertPosition {
    //Problem: https://leetcode.com/problems/search-insert-position/

    public static int searchInsert(int[] nums, int target) {
        int n = nums.length;
        int low =0, high = n-1, ans = n;
        while(low <= high){
            int mid = low + (high - low) / 2;
            if(nums[mid] >= target){
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,4,7};
        int target = 6;
        System.out.println(searchInsert(nums, target));
    }
}
