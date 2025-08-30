package Problems.Heaps.Hard;
import java.util.*;
public class MaximumSumCombinations {
    // https://www.geeksforgeeks.org/problems/maximum-sum-combination/1

    // Brute Force: Generate all possible combinations and find the maximum sum
    // Time Complexity:
    //Generating sums: O(n²)
    //Sorting: O(n² log n²)
    //Overall: O(n² log n)
    //Space: O(n²)
    public ArrayList<Integer> bruteForceTopKSumPairs(int[] a, int[] b, int k) {
        ArrayList<Integer> allSums = new ArrayList<>();

        // Generate all n^2 sums
        for (int i : a) {
            for (int j : b) {
                allSums.add(i + j);
            }
        }

        // Sort in descending order
        allSums.sort(Collections.reverseOrder());

        // Pick top k
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < k && i < allSums.size(); i++) {
            res.add(allSums.get(i));
        }

        return res;
    }

    // Better Solution: Using Min Heap
    // Instead of storing all sums, maintain a min-heap of size k,
    // Traverse all pairs (i, j), but keep only the top k in the heap.
    // Time Complexity: O(n^2logk)
    // Space Complexity: O(k)
    public ArrayList<Integer> topKSumPairsBetter(int[] a, int[] b, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int value : a) {
            for (int i : b) {
                int sum = value + i;
                if (minHeap.size() < k) {
                    minHeap.offer(sum);
                } else if (sum > minHeap.peek()) {
                    minHeap.poll();
                    minHeap.offer(sum);
                }
            }
        }

        ArrayList<Integer> res = new ArrayList<>(minHeap);
        res.sort(Collections.reverseOrder()); // final result must be sorted
        return res;
    }


    // Best Solution: Sorting the arrays, and then using MaxHeap,
    // Solution is similar to FindKSmallestSumPairs, just return the top K pairs.
    // with maximum sum.
    // Time Complexity: O(nlogn + klogk)
    // Space Complexity: O(k)
    public ArrayList<Integer> topKSumPairsBest(int[] a, int[] b, int k) {
        Arrays.sort(a);
        Arrays.sort(b);

        // Reverse both arrays to make them descending
        reverse(a);
        reverse(b);

        ArrayList<Integer> res = new ArrayList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> y[0] - x[0]);

        // Initialize with sums using b[0]
        for (int i = 0; i < a.length; i++) {
            pq.offer(new int[]{a[i] + b[0], i, 0}); // {sum, i, j}
        }

        while (k-- > 0 && !pq.isEmpty()) {
            int[] curr = pq.poll();
            int sum = curr[0];
            int i = curr[1];
            int j = curr[2];

            res.add(sum);

            if (j + 1 < b.length) {
                pq.offer(new int[]{a[i] + b[j + 1], i, j + 1});
            }
        }

        return res;
    }

    private void reverse(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}
