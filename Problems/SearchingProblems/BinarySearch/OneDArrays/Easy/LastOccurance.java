package Problems.SearchingProblems.BinarySearch.OneDArrays.Easy;

public class LastOccurance {
    // Problem: Given a sorted array of N integers, write a program to find the index of the last occurrence of the target key. If the target is not found then return -1.

    // Brute Force: O(n) time | O(1) space
    public static int lastOccurrence(int[] arr, int target) {
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Optimal Solution: O(logn) time | O(1) space
    public static int lastOccurrenceOptimal(int[] arr, int target) {
        int n = arr.length;
        int low = 0, high = n - 1, ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > target) {
                high = mid - 1;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                ans = mid;
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {3,4,13,13,13,20,40};
        int target = 13;
        System.out.println(lastOccurrence(arr, target));
        System.out.println(lastOccurrenceOptimal(arr, target));
    }
}
