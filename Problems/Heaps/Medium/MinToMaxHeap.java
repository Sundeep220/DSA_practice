package Problems.Heaps.Medium;

import java.util.Arrays;

public class MinToMaxHeap {
    // Problem: Given a array of Min Heap, convert it into a Max Heap
    // Time Complexity: O(n) Space Complexity: O(1)
    // Same concept as verify, we will apply heapify from the last parent to the root
    public static int[] minToMaxHeap(int[] nums) {
        int n = nums.length;
        for(int i = (n-2)/2; i >= 0; i--) { // n-2/2 is the last parent index of the tree. (n-2)/2 is the last parent index of the half of the tree (left half)
            heapifyDown(nums, i, n);
        }
        return nums;
    }

    public static void heapifyDown(int[] nums, int i, int n) {
        int largest = i;
        int left = 2 * i + 1; // left child index of the current node
        int right = 2 * i + 2; // right child index of the current node

        if(left < n && nums[left] > nums[largest]) // if the current node is greater than its left child, return false
            largest = left;
        if(right < n && nums[right] > nums[largest]) // if the current node is greater than its right child, return false
            largest = right;

        if(largest != i) {
            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;
            heapifyDown(nums, largest, n);
        }
    }

    public static void main(String[] args) {
        int[] nums = {3, 5, 9, 6, 8, 20, 10, 12, 18, 9};
        System.out.println(Arrays.toString(minToMaxHeap(nums)));
    }
}
