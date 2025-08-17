package Problems.Heaps.Medium;

import java.util.PriorityQueue;

public class KthSmallestElement {
    // Problem: Given an array of integers nums and an integer k, return the kth smallest element in the array.
    // Note that it is the kth smallest element in the sorted order, not the kth distinct element.

    // Time Complexity: O(nlogk) Space Complexity: O(k)
    public static int findKthSmallest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // max heap
        for (int num : nums) {
            pq.offer(num);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek() == null ? -1 : pq.peek();
    }

    public static void main(String[] args) {
        int[] nums = {3, 5, 9, 6, 8, 20, 10, 12, 18, 9};
        int k = 5;
        System.out.println(findKthSmallest(nums, k)); // Output: 9
    }
}
