package Problems.Arrays.Easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Union {
    // Using HashSet
    // Time Complexity: O(n), Space Complexity: O(n)
    public static int[] union(int[] arr1, int[] arr2) {
        HashSet<Integer> set = new HashSet<Integer>();

        for (int i = 0; i < arr1.length; i++) {
            set.add(arr1[i]);
        }
        for (int i = 0; i < arr2.length; i++) {
            set.add(arr2[i]);
        }

        int[] res = new int[set.size()];
        int index = 0;
        for (int num : set) {
            res[index++] = num;
        }
        return res;
    }

    // Optimal Solution
    // Time Complexity: O(n + m), Space Complexity: O(n + m)
    // USing two pointers
    // Note: This solution will only work if the arrays are sorted
    public static List<Integer> unionSortedArrays(int[] arr1, int[] arr2) {
        int i = 0, j = 0;
        List<Integer> union = new ArrayList<>();

        while (i < arr1.length && j < arr2.length) {
            // Skip duplicates in arr1
            while (i > 0 && i < arr1.length && arr1[i] == arr1[i - 1]) i++;
            // Skip duplicates in arr2
            while (j > 0 && j < arr2.length && arr2[j] == arr2[j - 1]) j++;

            if (i < arr1.length && j < arr2.length) {
                if (arr1[i] < arr2[j]) {  // arr1[i] < arr2[j]
                    union.add(arr1[i++]);
                } else if (arr1[i] > arr2[j]) { // arr1[i] > arr2[j]
                    union.add(arr2[j++]);
                } else { // arr1[i] == arr2[j]
                    union.add(arr1[i]);
                    i++;
                    j++;
                }
            }
        }

        // Add remaining elements
        while (i < arr1.length) {
            if (i == 0 || arr1[i] != arr1[i - 1]) union.add(arr1[i]);
            i++;
        }

        while (j < arr2.length) {
            if (j == 0 || arr2[j] != arr2[j - 1]) union.add(arr2[j]);
            j++;
        }

        return union;
    }

    // Another way to write the above method
    static ArrayList<Integer> FindUnion(int[] arr1, int[] arr2, int n, int m) {
        int i = 0, j = 0; // pointers
        ArrayList<Integer > Union=new ArrayList<>(); // Uninon vector
        while (i < n && j < m) {
            if (arr1[i] <= arr2[j]) // Case 1 and 2
            {
                if (Union.isEmpty() || Union.getLast() != arr1[i])
                    Union.add(arr1[i]);
                i++;
            } else // case 3
            {
                if (Union.isEmpty() || Union.getLast() != arr2[j])
                    Union.add(arr2[j]);
                j++;
            }
        }
        while (i < n) // IF any element left in arr1
        {
            if (Union.getLast() != arr1[i])
                Union.add(arr1[i]);
            i++;
        }
        while (j < m) // If any elements left in arr2
        {
            if (Union.getLast() != arr2[j])
                Union.add(arr2[j]);
            j++;
        }
        return Union;
    }

    public static void main(String[] args) {
        int[] arr1 = { 1, 2, 3, 4, 5 };
        int[] arr2 = { 2, 4, 6, 8 };
        int[] res = union(arr1, arr2);
        System.out.println("Brute Force Solution: " + Arrays.toString(res));

        List<Integer> res2 = unionSortedArrays(arr1, arr2);
        System.out.println("Optimal Solution: " + Arrays.toString(res2.toArray()));
    }

}
