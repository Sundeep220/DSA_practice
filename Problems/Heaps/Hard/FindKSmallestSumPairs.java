package Problems.Heaps.Hard;

import java.util.*;

public class FindKSmallestSumPairs {
    // Problem: https://leetcode.com/problems/find-k-pairs-with-smallest-sums/description/

    // Brute Force:
    // Time Complexity: O(m * n) + O(m * n * log(m * n))
    // Space Complexity: O(1)
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<int[]> pairs = new ArrayList<>();
        for (int u : nums1) {
            for (int v : nums2) {
                pairs.add(new int[]{u, v});
            }
        }

        pairs.sort((a,b) -> (a[0] + a[1]) - (b[0] + b[1])); // sort by sum of each pair
        for(int i = 0; i < Math.min(k, pairs.size()); i++) {
            res.add(new ArrayList<>(List.of(pairs.get(i)[0], pairs.get(i)[1])));
        }
        return res;
    }


    // Better Solution: Using MinHeap
    // Time Complexity: O(klogk)
    public List<List<Integer>> kSmallestPairsBetter(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        List<List<Integer>> result = new ArrayList<>();
        if (m == 0 || n == 0 || k == 0) return result;

        // Min-heap: [sum, i, j]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Visited set to avoid duplicates
        Set<String> visited = new HashSet<>();

        // Start with (0,0)
        pq.offer(new int[]{nums1[0] + nums2[0], 0, 0});
        visited.add("0,0");

        while (k-- > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();
            int i = cur[1], j = cur[2];

            result.add(Arrays.asList(nums1[i], nums2[j]));

            // Expand to (i, j+1)
            if (j + 1 < n && visited.add(i + "," + (j + 1))) {
                pq.offer(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }

            // Expand to (i+1, j)
            if (i + 1 < m && visited.add((i + 1) + "," + j)) {
                pq.offer(new int[]{nums1[i + 1] + nums2[j], i + 1, j});
            }
        }

        return result;
    }


    // More elegant solution: using min-heap
    //## 🔹 More Optimal (Row-wise Expansion)
    //There’s a very common **LeetCode pattern** for this problem:
    //1. Instead of starting at `(0,0)` and expanding in both directions,
    //   we seed the heap with `(i, 0)` for i = 0...min(k, nums1.length-1)\`.
    //   (i.e., the first column of the matrix of sums).
    //2. Then for each pop `(i, j)` we **only expand to `(i, j+1)`**.
    //   * This guarantees no duplicate index-pairs.
    //   * No need for a `visited` set.
    //### 🔸 Why It’s Safe
    //Because the arrays are sorted:
    //* `(i,0)` ensures every row starts in the heap.
    //* Expanding only `(i,j+1)` ensures we cover each row in order, without missing pairs.
    //## 🔹 Complexity of Row-wise Version
    //* We still pop `k` elements.
    //* Each heap operation costs `O(log k)` (heap size ≤ `k`).
    //* **Time Complexity**: `O(k log k)`
    //* **Space Complexity**: `O(k)` (no `visited` set needed).

    public List<List<Integer>> kSmallestPairsOptimal(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k == 0) return result;

        // Min-heap storing [sum, i, j]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                (a, b) -> a[0] - b[0]
        );

        // Seed the heap with pairs (i, 0), i.e. first column
        for (int i = 0; i < Math.min(nums1.length, k); i++) {
            minHeap.offer(new int[]{nums1[i] + nums2[0], i, 0});
        }

        // Extract k pairs
        while (k-- > 0 && !minHeap.isEmpty()) {
            int[] cur = minHeap.poll();
            int i = cur[1], j = cur[2];
            result.add(Arrays.asList(nums1[i], nums2[j]));

            // Push the next element in the same row (i, j+1)
            if (j + 1 < nums2.length) {
                minHeap.offer(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }
        }

        return result;
    }
}
