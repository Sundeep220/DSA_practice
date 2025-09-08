package Problems.Greedy.Medium;

import java.util.Arrays;

public class NonOverlappingIntervals {
    // Problem: https://leetcode.com/problems/non-overlapping-intervals/


    // Taking the idea from N meetings in one room problem
    // Time Complexity: O(nlog(n))
    public int eraseOverlapIntervals(int[][] intervals) {
        int n = intervals.length;
        // sort the intervals with end intervals
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        // count the max intervals non-overlapping intervals you can take
        int maxCount = 1;
        int last = intervals[0][1];

        for(int i = 1; i < n; i++){
            if(intervals[i][0] >= last){
                maxCount++;
                last = intervals[i][1];
            }
        }
        //Minimum number of intervals to remove
        int minToRemove = n - maxCount;
        return minToRemove;
    }
}
