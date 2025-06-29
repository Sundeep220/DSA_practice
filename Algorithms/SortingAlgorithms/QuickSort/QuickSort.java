package Algorithms.SortingAlgorithms.QuickSort;

import java.util.Arrays;

public class QuickSort {
    // https://takeuforward.org/data-structure/quick-sort-algorithm/
    // Time Complexity: O(n log n)
    // Space Complexity: O(log n)
    // Quick sort is a divide and conquer algorithm.
    // First, it picks an element, called a pivot, from the array.
    // Then, it partitions the other elements into two sub-arrays,
    // according to whether they are less than or greater than the pivot.
    // Finally, it recursively sorts the sub-arrays.
    // The base case of the recursion is when the starting index is greater
    // than or equal to the ending index.
    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {  // base case
            int pi = partition(arr, low, high); // finding pivot
            quickSort(arr, low, pi - 1);        // note: not pi - 1
            quickSort(arr, pi + 1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];
        int i = low;
        int j = high;

        while (i < j) {
            // find the first element from left greater than pivot
            while (arr[i] <= pivot && i <= high - 1) {  // move i forward if arr[i] <= pivot
                i++;
            }
            // find the first element from right less than pivot
            while (arr[j] > pivot && j >= low + 1) {  // move j backward if arr[j] > pivot
                j--;
            }
            if (i < j) {   // swap arr[i] and arr[j] if i < j
                swap(arr, i, j);
            }
        }
        swap(arr, low, j);  // Finally, swap pivot with arr[j]
        return j;
    }


    public int partition2(int[] arr, int low, int high) {
        int pivot = arr[low];
        int i = low - 1;
        int j = high + 1;

        while (true) {
            do {
                i++;
            } while (arr[i] < pivot);

            do {
                j--;
            } while (arr[j] > pivot);

            if (i >= j)
                return j;

            swap(arr, i, j);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void main(String[] args) {
        int[] arr = { 12, 11, 13, 5, 6, 7 };
        QuickSort obj = new QuickSort();
        obj.quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
