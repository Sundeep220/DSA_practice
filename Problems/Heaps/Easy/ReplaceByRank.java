package Problems.Heaps.Easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ReplaceByRank {
    // Problem: https://leetcode.com/problems/rank-transform-of-an-array/description/

    // Brute Force: Traverse for each element and find how many elements are lesser than them and based on cout give rank
    // Optimal Solution: O(n^2) time | O(n) space
    public static int[] arrayRankTransform(int[] arr) {
        int n = arr.length;
        int[] rankArr = new int[n];

        for (int i = 0; i < n; i++) {
            int rank = 1;
            for (int j = 0; j < n; j++) {
                if (arr[j] < arr[i]) {
                    rank++;
                }
            }
            rankArr[i] = rank;
        }

        return rankArr;
    }


    // Optimal Solution 1: Using Sorting and HashMap
    // Copy the array into dummy array, and then sort it, then use hashmap to sotre the rank of each element
    // Time Complexity: O(nlogn) time | O(n) space

    public static int[] arrayRankTransform1(int[] arr) {
        int n = arr.length;
        int[] res = new int[n];
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Map<Integer, Integer> rank = new HashMap<>();
        int r = 0; // dense rank
        for (int x : sorted) {
            if (!rank.containsKey(x)) {
                rank.put(x, ++r); // only advance when x is new
            }
        }

        for (int i = 0; i < n; i++) {
            res[i] = rank.get(arr[i]);
        }
        return res;
    }


    // Optimal Solution 2: Using MinHeap
    // Time Complexity: O(nlogn) time | O(n) space
    public static int[] arrayRankTransform2(int[] arr) {
        int n = arr.length;


        // Step 1: Put into min-heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : arr) {
            minHeap.add(num);
        }

        // Step 2: Pop and assign rank
        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1;
        while (!minHeap.isEmpty()) {
            int num = minHeap.poll();
            if (!rankMap.containsKey(num)) { // handle duplicates
                rankMap.put(num, rank++);
            }
        }

        // Step 3: Replace elements with rank
        int[] rankArr = new int[n];
        for (int i = 0; i < n; i++) {
            rankArr[i] = rankMap.get(arr[i]);
        }

        return rankArr;
    }


    // If we want to avoid using map with Heap we can simply store pair (value, index) in heap
    // Time Complexity: O(nlogn) time | O(n) space
    public static int[] arrayRankTransform3(int[] arr) {
        int[] ranks = new int[arr.length];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < arr.length; i++) {
            minHeap.add(new int[]{arr[i], i});
        }
        int rank = 1;
        int prev = Integer.MIN_VALUE;
        while (!minHeap.isEmpty()) {
            int[] pair = minHeap.poll();
            int currentValue = pair[0];
            int currentIndex = pair[1];

            // If value is different, increase rank
            if (currentValue != prev) {
                prev = currentValue;
                ranks[currentIndex] = rank;
                rank++;
            }else {
                // duplicate element gets same rank
                ranks[currentIndex] = rank - 1;
            }

            // Or
//            if (value != prev) {
//                prev = value;
//                rank++;
//            }
//            res[idx] = rank;
        }

        return ranks;
    }

    public static void main(String[] args) {
        int[] arr = {40,10,20,30};
        System.out.println(Arrays.toString(arrayRankTransform(arr)));//[4,1,2,3]
        System.out.println(Arrays.toString(arrayRankTransform1(arr)));//[4,1,2,3]
        System.out.println(Arrays.toString(arrayRankTransform2(arr)));//[4,1,2,3]
        System.out.println(Arrays.toString(arrayRankTransform3(arr)));//[4,1,2,3]
    }
}
