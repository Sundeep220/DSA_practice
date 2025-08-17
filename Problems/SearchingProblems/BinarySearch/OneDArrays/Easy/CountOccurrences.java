package Problems.SearchingProblems.BinarySearch.OneDArrays.Easy;

import static Problems.SearchingProblems.BinarySearch.OneDArrays.Easy.LastOccurance.lastOccurrence;

public class CountOccurrences {
    // problem: Given a sorted array, and element X, find the number of occurrences of X in the array


    //Brute Force Solution: O(n) time | O(1) space
    public static int countOccurrences(int[] arr, int x) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                count++;
            }
        }
        return count;
    }

    //Optimal Solution: O(logn) time | O(1) space
    public static int countOccurrencesOptimal(int[] arr, int x) {
        int firstOccurrence = firstOccurrence(arr, x);
        if(firstOccurrence == -1) return 0;
        int lastOccurrence = lastOccurrenceOptimal(arr, x);
        return lastOccurrence - firstOccurrence + 1;
    }

    public static int firstOccurrence(int[] arr, int x) {
        int n = arr.length;
        int low = 0, high = n - 1;
        int ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > x) {
                high = mid - 1;
            } else if (arr[mid] < x) {
                low = mid + 1;
            } else {
                ans = mid;
                high = mid - 1;
            }
        }
        return ans;
    }

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
        int[] arr = {1, 2, 3, 3, 3, 3, 4, 5, 6, 7, 8};
        int x = 3;
        System.out.println(countOccurrencesOptimal(arr, x));
    }
}
