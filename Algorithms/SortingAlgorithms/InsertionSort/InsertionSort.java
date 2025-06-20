package Algorithms.SortingAlgorithms.InsertionSort;

import java.util.Arrays;

public class InsertionSort {
    // https://takeuforward.org/data-structure/insertion-sort-algorithm/
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    // Insertion sort is an in-place sorting algorithm
    // The idea is to insert the element at its correct position in the sorted array
    // Then insert the next element at its correct position in the sorted array
    // Continue this until the array is sorted
    public static int[] insertionSort(int[] nums) {
        int n = nums.length;
        for(int i=0; i<=n-1; i++){
            int j = i;
            while(j > 0 && nums[j-1] > nums[j]){
                int t = nums[j-1];
                nums[j-1] = nums[j];
                nums[j] = t;
                j--;
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 5, 4};
        System.out.println(Arrays.toString(insertionSort(nums)));
    }
}
