package Algorithms.SortingAlgorithms.RecursiveInsertionSort;

import java.util.Arrays;

public class RecursiveInsertionSort {
    // https://takeuforward.org/arrays/recursive-insertion-sort-algorithm/
    // Time Complexity: O(n^2)
    // Space Complexity: O(n)
    public void insertionSort(int[] arr,int i , int n) {
        if (i == n) return;
        int j = i;
        while (j > 0 && arr[j - 1] > arr[j]) {
            int temp = arr[j];
            arr[j] = arr[j - 1];
            arr[j - 1] = temp;
            j--;
        }
    }

    public static void main(String[] args) {
        int[] arr = { 12, 11, 13, 5, 6, 7 };
        RecursiveInsertionSort obj = new RecursiveInsertionSort();
        obj.insertionSort(arr, 0, arr.length);
        System.out.println(Arrays.toString(arr));
    }
}
