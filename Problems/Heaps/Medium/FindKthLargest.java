package Problems.Heaps.Medium;

import java.util.PriorityQueue;

public class FindKthLargest {
    // Problem: https://leetcode.com/problems/kth-largest-element-in-an-array/


    // Using Max Heap
    // Time Complexity: O(nlogk + K) Space Complexity: O(k)
    public static int findKthLargest(int[] nums, int k) {
        // PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for(int num: nums)
            pq.offer(num);

        for(int i = 0; i <k - 1; i++)
            pq.poll();

        return pq.peek();

    }

    // Better: Using Min Heap
    // Time Complexity: O(nlogk) Space Complexity: O(k)
    public static int findKthLargestBetter(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> a - b);
        for(int num: nums){
            pq.offer(num);
            if(pq.size() > k)
                pq.poll(); // remove smallest
        }

        return pq.peek(); // return kth largest
    }
}
