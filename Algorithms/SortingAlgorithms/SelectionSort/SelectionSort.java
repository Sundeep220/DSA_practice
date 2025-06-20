package Algorithms.SortingAlgorithms.SelectionSort;

public class SelectionSort {
    // https://takeuforward.org/sorting/selection-sort-algorithm/
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    // Selection sort is an in-place sorting algorithm
    // The idea is to find the minimum element in the unsorted array and swap it with the first element
    // Then find the minimum element in the unsorted array and swap it with the second element
    // Continue this until the array is sorted
    public int[] selectionSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int currMinIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                // Compare with the current minimum element
                if (nums[j] < nums[currMinIndex]) {
                    currMinIndex = j;
                }
            }
            // Swap only if a smaller element is found
            int temp = nums[i];
            nums[i] = nums[currMinIndex];
            nums[currMinIndex] = temp;
        }
        return nums;
    }

}
