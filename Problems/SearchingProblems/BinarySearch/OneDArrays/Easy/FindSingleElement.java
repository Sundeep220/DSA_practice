package Problems.SearchingProblems.BinarySearch.OneDArrays.Easy;

import java.util.ArrayList;
import java.util.Arrays;

public class FindSingleElement {
    // Problem: https://leetcode.com/problems/single-element-in-a-sorted-array/
    // Time Complexity: O(logn) time | O(1) space

    public static int singleNonDuplicate(ArrayList<Integer> arr) {
        int n = arr.size(); // Size of the array.

        // Edge cases:
        if (n == 1)
            return arr.get(0);
        if (!arr.get(0).equals(arr.get(1)))
            return arr.get(0);
        if (!arr.get(n - 1).equals(arr.get(n - 2)))
            return arr.get(n - 1);

        int low = 1, high = n - 2;
        while (low <= high) {
            int mid = (low + high) / 2;

            // If arr[mid] is the single element:
            if (!arr.get(mid).equals(arr.get(mid + 1)) && !arr.get(mid).equals(arr.get(mid - 1))) {
                return arr.get(mid);
            }

            // We are in the left:
            if ((mid % 2 == 1 && arr.get(mid).equals(arr.get(mid - 1)))
                    || (mid % 2 == 0 && arr.get(mid).equals(arr.get(mid + 1)))) {
                // Eliminate the left half:
                low = mid + 1;
            }
            // We are in the right:
            else {
                // Eliminate the right half:
                high = mid - 1;
            }
        }

        // Dummy return statement:
        return -1;
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 4, 4, 8, 8));
        System.out.println(singleNonDuplicate(arr));
    }
}
