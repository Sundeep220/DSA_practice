package Problems.Heaps.Medium;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class StraightHands {
    // Problem: https://leetcode.com/problems/straight-hands

    // Using Min Heap
    // Since to fomr a consecutive sequence, we need to start with smallest number, so we need to use min heap.
    // Time Complexity: O(nlogn), Space Complexity: O(n)
    public boolean isStraightHand(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) return false; // if the length of the array is not divisible by groupSize, return false

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int card : hand) pq.offer(card);

        while (!pq.isEmpty()) {
            int first = pq.poll(); // smallest card
            for (int i = 1; i < groupSize; i++) {
                if (!pq.remove(first + i)) return false;
            }
        }
        return true;
    }

    // Optimal Solution: Greedy + TreeMap
    // Since here we can store te freq for each element but for making
    // a consecutive sequence, we need to start with smallest number, so we would
    // like the keys to be sorted in ascending order.

    public static boolean isStraightHandOptimal(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) return false; // if the length of the array is not divisible by groupSize, return false

        Map<Integer, Integer> freq = new TreeMap<>(); // TreeMap to store the frequency of each element
        for (int card : hand) freq.put(card, freq.getOrDefault(card, 0) + 1);

        // traverse though keys and see if we can form a consecutive sequence
        for(int key : freq.keySet()) {
            int count = freq.get(key); // frequency of current key
            if (count == 0) continue; // if the frequency is 0, continue
            for(int i = 0; i < groupSize; i++) {
                if(freq.getOrDefault(key + i, 0) < count) return false;
                freq.put(key + i, freq.get(key + i) - count);
            }
        }
        return true;
    }
}
