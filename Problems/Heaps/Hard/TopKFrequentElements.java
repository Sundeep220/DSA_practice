package Problems.Heaps.Hard;
import java.util.*;
public class TopKFrequentElements {
    // https://leetcode.com/problems/top-k-frequent-elements/

    // Brute Force: Using max heap to get the top K elements
    // Time Complexity: O(nlogk) Space Complexity: O(n)
    public static int[] topKFrequentBrute(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int num: nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for(Map.Entry<Integer, Integer> entry: map.entrySet()){
            pq.offer(new int[]{entry.getValue(), entry.getKey()});
        }
        int[] res = new int[k];
        int i = 0;
        while(k-- >0 && !pq.isEmpty()){
            int[] curr = pq.poll();
            res[i++] = curr[1];
        }
        return res;
    }

    // Optimal: Using Min Heap
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int num: nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for(Map.Entry<Integer, Integer> entry: map.entrySet()){
            minHeap.offer(new int[]{entry.getValue(), entry.getKey()});

            if(minHeap.size() > k){
                minHeap.poll();
            }
        }

        int[] res = new int[k];
        int i = 0;
        while(!minHeap.isEmpty()){
            int[] curr = minHeap.poll();
            res[i++] = curr[1];
        }
        return res;
    }
}
