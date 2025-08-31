package Problems.SearchingProblems.BinarySearch.OneDArrays.Medium;

public class SearchInKSortedArray {
    // Problem: Given an almost sorted array where each element in the array is at most k places away from its sorted position
    // Your task is to search in this given array
    // Can you do this in O(k log n) time complexity?

    // Using Modified Binary Search
    public static int searchInKSortedArray(int[] arr, int target, int k) {
        int low = 0;
        int high = arr.length - 1;

        while(low <= high) {
            int mid = low + (high - low) / 2;

            // check within k distance around mid
            for (int i = Math.max(low, mid - k); i <= Math.min(high, mid + k); i++) {
                if (arr[i] == target) {
                    return i;
                }
            }

            if (arr[mid] < target) {
                low = mid + k + 1;
            } else if (arr[mid] > target) {
                high = mid - k - 1;
            }
        }

        return -1;
    }
}
