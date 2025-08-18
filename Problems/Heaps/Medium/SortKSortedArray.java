package Problems.Heaps.Medium;
import java.util.Arrays;
import java.util.PriorityQueue;

public class SortKSortedArray {
    // Problem: Given an K sorted array, i.e., an array where each element is at most k places away from its sorted position,
    // sort the array in O(n log k) time. You can assume that k <= n.

    // Naive Solution: Sort the array and return it.
    // Time Complexity: O(nlogn) time | O(1) space
    public static int[] sortKSortedArray(int[] nums, int k) {
        Arrays.sort(nums);
        return nums;
    }

    // Efficient Solution: Use a min heap to store the first k + 1 elements of the array.
    // Why k + 1? since any element in the array can be at most k places away from its sorted position.
    // So for a element: the range of indexs = (0, k) => k + 1 elements in total.
    // Time Complexity: O(nlogk) time | O(k) space
    public static int[] sortKSortedArrayEfficient(int[] arr, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        int index = 0;

        // Step 1: Insert first k+1 elements
        for (int i = 0; i <= k && i < arr.length; i++) {
            minHeap.add(arr[i]);
        }

        // Step 2: Process remaining elements
        for (int i = k + 1; i < arr.length; i++) {
            arr[index++] = minHeap.poll();
            minHeap.add(arr[i]);
        }

        // Step 3: Empty remaining heap
        while (!minHeap.isEmpty()) {
            arr[index++] = minHeap.poll();
        }

        return arr;
    }

}
