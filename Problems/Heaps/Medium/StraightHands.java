package Problems.Heaps.Medium;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class StraightHands {
    // Problem: https://leetcode.com/problems/straight-hands

    // Using Min Heap
    // Since to fomr a consecutive sequence, we need to start with smallest number, so we need to use min heap.
    // Time Complexity: O(N^2), Space Complexity: O(n)
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

    // Better Soluiton: MinHeap + HashMap
    /*
     * Intuition:
     * The smallest remaining card must start the next group.
     *
     * The min heap gives us the smallest remaining card.
     * The frequency map tells us how many copies of each card remain.
     *
     * If the smallest card appears 'count' times, then we must create
     * 'count' groups starting from that card. Therefore, every consecutive
     * card must have at least 'count' copies available.
     *
     * We use the heap only to find the smallest value. We do NOT use
     * PriorityQueue.remove(value), because that operation is O(N).
     *
     * Time Complexity: O(N log N)
     * Space Complexity: O(N)
     */
    public boolean isStraightHandHeap(int[] hand, int groupSize) {

        if (hand.length % groupSize != 0) {
            return false;
        }

        Map<Integer, Integer> freq = new HashMap<>();
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Build frequency map and add each unique card to the heap
        for (int card : hand) {
            if (!freq.containsKey(card)) {
                minHeap.offer(card);
            }
            freq.put(card, freq.getOrDefault(card, 0) + 1);
        }

        while (!minHeap.isEmpty()) {

            // Smallest remaining card must start the next group
            int start = minHeap.peek();

            int count = freq.get(start);

            // Build 'count' groups starting from start
            for (int i = 0; i < groupSize; i++) {

                int currentValue = start + i;

                int currentFreq = freq.getOrDefault(currentValue, 0);

                // Not enough consecutive cards
                if (currentFreq < count) {
                    return false;
                }

                // Consume 'count' cards
                int remaining = currentFreq - count;

                if (remaining == 0) {

                    /*
                     * The smallest value should be removed from the heap
                     * only when all its copies are consumed.
                     */
                    if (currentValue != minHeap.peek()) {
                        return false;
                    }

                    minHeap.poll();
                    freq.remove(currentValue);

                } else {
                    freq.put(currentValue, remaining);
                }
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
