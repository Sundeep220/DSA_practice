package Problems.SearchingProblems.Easy;

public class UpperBound {
    // Problem: Similar to Lower Bound but returns index of first element > target

    public static int upperBound(int[] arr, int target) {
        int n = arr.length;
        int low = 0, high = n - 1, ans = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= target) {
                low = mid + 1;
            } else {

                ans = mid;
                high = mid - 1;
            }
        }
        return ans;
    }
}
