package Problems.SearchingProblems.BinarySearch.OneDArrays.Easy;

public class LowerBound {
    // Problem: https://www.naukri.com/code360/problems/lower-bound_8165382

    // Optimal Solution: O(logn) time | O(1) space
    // Using Binary Search
    public static int lowerBound(int[] arr, int target) {
        int n = arr.length;
        int low = 0, high = n - 1, ans = n;
        while(low <= high){
            int mid = low + (high - low) / 2;
            if(arr[mid] >= target){
                ans = mid;
                //look for smaller index on the left
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {3,5,8,15,19};
        int target = 9;
        System.out.println(lowerBound(arr, target));
    }
}
