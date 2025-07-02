package Problems.SearchingProblems.BinarySearch.Answers.Hard;

import java.util.PriorityQueue;

public class MinimizeMaxDistanceBwGasStations {
    //Problem Link: https://leetcode.com/problems/minimum-maximum-distance-between-gas-stations/

    //Brute Force Solution: O(n^2) time | O(1) space
    public static int minMaxDistance(int[] nums, int k) {
        int n = nums.length;
        int[] stations = new int[n-1];
        for(int i = 0; i < k; i++){
            int maxDiff = 0, maxDiffIndex = -1;
            for(int j =0; j < n - 1; j++){
                int diff = (nums[j + 1] - nums[j])/ (stations[j] + 1);
                if(diff > maxDiff){
                    maxDiff = diff;
                    maxDiffIndex = j;
                }
            }
            stations[maxDiffIndex]++;
        }
        int maxDiff = 0;
        for(int i = 0; i < n - 1; i++){
            int diff = (nums[i + 1] - nums[i])/ (stations[i] + 1);
            maxDiff = Math.max(maxDiff, diff);
        }
        return maxDiff;

    }

    // Better Solution: O(k*log(n)) time | O(1) space
    // Using Priority Queue
    public static double minMaxDistanceBetter(int[] nums, int k) {
        int n = nums.length;
        int[] stations = new int[n-1];
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Double.compare(b.diff, a.diff));  // max heap
        // insert the first n-1 elements into pq
        // with respective distance values:
        for (int i = 0; i < n - 1; i++) {
            pq.add(new Pair(nums[i + 1] - nums[i], i));
        }
        for(int i = 0; i < k; i++){
            // Find the maximum section
            // and insert the gas station:
            Pair tp = pq.poll();
//            assert tp != null;  // ensure tp is not null if this is not true, then the program will crash
            int maxDiffIndex = tp.index;

            // Update the station array:
            stations[maxDiffIndex]++;

            double inidiff = nums[maxDiffIndex + 1] - nums[maxDiffIndex];
            double newSecLen = inidiff / (double) (stations[maxDiffIndex] + 1);
            pq.add(new Pair(newSecLen, maxDiffIndex));
        }
        return pq.peek().diff;
    }

    public static void main(String[] args) {
        int[] nums = {1, 13, 17, 23};
        int k = 5;
        System.out.println(minMaxDistance(nums, k));
        System.out.println(minMaxDistanceBetter(nums, k));
    }
}
