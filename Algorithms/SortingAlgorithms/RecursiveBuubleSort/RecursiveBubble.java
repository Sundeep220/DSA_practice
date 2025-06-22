package Algorithms.SortingAlgorithms.RecursiveBuubleSort;

import java.util.Arrays;

public class RecursiveBubble {
    // https://takeuforward.org/arrays/recursive-bubble-sort-algorithm/
    public void bubbleSort(int[] arr, int n) { // n = arr.length
        if (n == 1) return;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        bubbleSort(arr, n - 1);
    }

    public void bubbleSortOptimized(int[] arr, int n) {
        if(n == 1) return;
        boolean swapped = false;
        for(int i = 0; i < n - 1; i++) {
            if(arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
                swapped = true;
            }
        }
        if(swapped) {
            bubbleSortOptimized(arr, n - 1);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 12, 11, 13, 5, 6, 7 };
        RecursiveBubble obj = new RecursiveBubble();
        obj.bubbleSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }
}
