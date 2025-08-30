package Problems.Heaps.Hard;
import java.util.*;
public class TopKFrequentWords {
    // Problem: https://leetcode.com/problems/top-k-frequent-words/


    // same as TopKFrequentElements
    // Comparator Explanation:
    // Custom comparator for the PriorityQueue:
    // ------------------------------------------
    // 1. We are building a min-heap based on frequency.
    //    - If a word has a lower frequency, it should come first.
    //    - This ensures that when the heap grows beyond size k,
    //      we can easily remove the "least frequent" word.
    //
    // 2. If two words have the same frequency:
    //    - We compare them lexicographically in reverse order (w2.compareTo(w1)).
    //    - Why reverse? Because in the min-heap, we want the "larger"
    //      word (lexicographically) to be removed first.
    //    - This ensures that when we pop from the heap, we correctly
    //      maintain lexicographical order for ties.
    //
    // Example:
    //   Suppose frequencies are:
    //     "apple" -> 3, "banana" -> 3, "cat" -> 2
    //   For "apple" and "banana" (same frequency):
    //     "banana".compareTo("apple") > 0
    //     So "banana" is considered smaller (since we reversed order).
    //     If heap exceeds size k, "banana" would be removed first.
    //
    // Final effect:
    //   - Heap always keeps k most frequent words.
    //   - For ties, lexicographically smaller word survives.
    public List<String> topKFrequent(String[] words, int k) {
        // Step 1: Count frequencies
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        // Step 2: MinHeap with custom comparator
        PriorityQueue<String> heap = new PriorityQueue<>((w1, w2) -> {
            int freq1 = map.get(w1);
            int freq2 = map.get(w2);
            if (freq1 == freq2) {
                return w2.compareTo(w1); // reverse lexicographical
            }
            return freq1 - freq2; // lower frequency first
        });

        // Step 3: Maintain top k in heap
        for (String word : map.keySet()) {
            heap.offer(word);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        // Step 4: Build result (reverse because minHeap gives least frequent first)
        List<String> res = new ArrayList<>();
        while (!heap.isEmpty()) {
            res.add(heap.poll());
        }
        Collections.reverse(res);
        return res;
    }

}
