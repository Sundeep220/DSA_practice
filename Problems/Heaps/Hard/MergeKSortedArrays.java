package Problems.Heaps.Hard;

import java.util.*;

public class MergeKSortedArrays {

    /*
     * ============================================================
     * 1. BRUTE FORCE
     * ============================================================
     *
     * Combine all arrays and sort.
     *
     * Time:  O(N log N)
     * Space: O(N)
     */
    public static ArrayList<Integer> mergeBruteForce(
            ArrayList<ArrayList<Integer>> arr,
            int K
    ) {
        ArrayList<Integer> result =
                new ArrayList<>();

        for (int i = 0; i < K; i++) {
            result.addAll(arr.get(i));
        }

        Collections.sort(result);

        return result;
    }


    /*
     * ============================================================
     * 2. BETTER - SEQUENTIAL MERGING
     * ============================================================
     *
     * Merge the result with one array at a time.
     *
     * Time:  O(NK) worst case
     * Space: O(N)
     */
    public static ArrayList<Integer> mergeSequential(
            ArrayList<ArrayList<Integer>> arr,
            int K
    ) {
        if (K == 0) {
            return new ArrayList<>();
        }

        ArrayList<Integer> result =
                new ArrayList<>(arr.get(0));

        for (int i = 1; i < K; i++) {

            result = mergeTwoSortedArrays(
                    result,
                    arr.get(i)
            );
        }

        return result;
    }


    /*
     * ============================================================
     * 3. BETTER - DIVIDE AND CONQUER
     * ============================================================
     *
     * Merge arrays in pairs.
     *
     * Time:  O(N log K)
     * Space: O(N)
     */
    public static ArrayList<Integer> mergeDivideAndConquer(
            ArrayList<ArrayList<Integer>> arr,
            int K
    ) {
        if (K == 0) {
            return new ArrayList<>();
        }

        return mergeRange(arr, 0, K - 1);
    }

    private static ArrayList<Integer> mergeRange(ArrayList<ArrayList<Integer>> arr, int left, int right) {
        if (left == right) {
            return new ArrayList<>(arr.get(left));
        }

        int mid = left + (right - left) / 2;

        ArrayList<Integer> leftResult = mergeRange(arr, left, mid);

        ArrayList<Integer> rightResult = mergeRange(arr, mid + 1, right);

        return mergeTwoSortedArrays(leftResult, rightResult);
    }


    /*
     * ============================================================
     * 4. OPTIMAL - MIN HEAP
     * ============================================================
     *
     * Keep one smallest remaining element from every array.
     *
     * Time:  O(N log K)
     * Space: O(N + K)
     */
    static class Node {

        int value;
        int arrayIndex;
        int elementIndex;

        Node(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
    }

    public static ArrayList<Integer> mergeMinHeap(ArrayList<ArrayList<Integer>> arr, int K) {
        ArrayList<Integer> result = new ArrayList<>();

        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.value));

        /*
         * Add first element from every array.
         */
        for (int i = 0; i < K; i++) {
            if (!arr.get(i).isEmpty()) {
                minHeap.offer(new Node(arr.get(i).get(0), i, 0));
            }
        }

        /*
         * Extract smallest and add next element
         * from the same array.
         */
        while (!minHeap.isEmpty()) {

            Node current = minHeap.poll();

            result.add(current.value);

            int nextIndex = current.elementIndex + 1;

            if (nextIndex < arr.get(current.arrayIndex).size()) {
                minHeap.offer(new Node(arr.get(current.arrayIndex).get(nextIndex), current.arrayIndex, nextIndex));
            }
        }

        return result;
    }


    /*
     * ============================================================
     * MERGE TWO SORTED ARRAYS
     * ============================================================
     */
    private static ArrayList<Integer> mergeTwoSortedArrays(
            ArrayList<Integer> a,
            ArrayList<Integer> b
    ) {
        ArrayList<Integer> result =
                new ArrayList<>();

        int i = 0;
        int j = 0;

        while (
                i < a.size() &&
                        j < b.size()
        ) {

            if (a.get(i) <= b.get(j)) {
                result.add(a.get(i));
                i++;
            } else {
                result.add(b.get(j));
                j++;
            }
        }

        while (i < a.size()) {
            result.add(a.get(i));
            i++;
        }

        while (j < b.size()) {
            result.add(b.get(j));
            j++;
        }

        return result;
    }


    /*
     * ============================================================
     * MAIN METHOD
     * ============================================================
     */
    public static void main(String[] args) {

        ArrayList<ArrayList<Integer>> arr =
                new ArrayList<>();

        arr.add(
                new ArrayList<>(
                        Arrays.asList(1, 4, 7)
                )
        );

        arr.add(
                new ArrayList<>(
                        Arrays.asList(2, 5, 8)
                )
        );

        arr.add(
                new ArrayList<>(
                        Arrays.asList(3, 6, 9)
                )
        );

        int K = arr.size();

        System.out.println(
                "Brute Force: " +
                        mergeBruteForce(arr, K)
        );

        System.out.println(
                "Sequential: " +
                        mergeSequential(arr, K)
        );

        System.out.println(
                "Divide and Conquer: " +
                        mergeDivideAndConquer(arr, K)
        );

        System.out.println(
                "Min Heap: " +
                        mergeMinHeap(arr, K)
        );
    }
}