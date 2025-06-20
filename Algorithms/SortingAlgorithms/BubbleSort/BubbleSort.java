package Algorithms.SortingAlgorithms.BubbleSort;

public class BubbleSort {
    // https://takeuforward.org/data-structure/bubble-sort-algorithm/
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    // Bubble sort is an in-place sorting algorithm
    // The idea is to compare two adjacent elements and swap them if they are in the wrong order
    // Then compare the next two elements and swap them if they are in the wrong order
    // Continue this until the array is sorted
    // The outer loop runs from the last element to the first element, and the inner loop runs from the first element to the second-to-last element
    public int[] bubbleSort(int[] nums) {
        int n = nums.length;
        for(int i=n-1; i>=0; i--){
            for(int j=0; j <= i-1; j++){
                if(nums[j] > nums[j+1]){
                    int t = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = t;
                }
            }
        }
        return nums;
    }
}
