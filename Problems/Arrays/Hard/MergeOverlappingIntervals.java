package Problems.Arrays.Hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeOverlappingIntervals {
    // Problem: https://leetcode.com/problems/merge-intervals/

    // Brute Force
    //
    public List<List<Integer>> mergeIntervalsBrute(int[][] arr) {
        int n = arr.length; // size of the array
        //sort the given intervals:
        Arrays.sort(arr, Comparator.comparingInt(o -> o[0]));

        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            // if the current interval does not
            // lie in the last interval:
            if (ans.isEmpty() || arr[i][0] > ans.get(ans.size() - 1).get(1)) {
                ans.add(Arrays.asList(arr[i][0], arr[i][1]));
            }
            // if the current interval
            // lies in the last interval:
            else {
                ans.get(ans.size() - 1).set(1,
                        Math.max(ans.get(ans.size() - 1).get(1), arr[i][1]));
            }
        }
        return ans;
    }


    // Optimal Solution:
    public List<List<Integer>> mergeIntervalsOptimal(List<List<Integer>> intervals) {
        List<List<Integer>> res = new ArrayList<>();

        // Sort intervals based on the start time
        intervals.sort(Comparator.comparingInt(l -> l.get(0)));
//        res.add(intervals.get(0));
        for (int i = 0; i < intervals.size(); i++) {
            int start = intervals.get(i).get(0);
            int end = intervals.get(i).get(1);

            // Merge all overlapping intervals
            while (i + 1 < intervals.size() && intervals.get(i + 1).get(0) <= end) {
                end = Math.max(end, intervals.get(i + 1).get(1));
                i++;
            }

            res.add(List.of(start, end));
        }

        return res;
    }

    public static void main(String[] args) {

        List<List<Integer>> intervals = new ArrayList<>(List.of(List.of(1, 3), List.of(2, 6), List.of(8, 10), List.of(15, 18)));
        List<List<Integer>> res = new MergeOverlappingIntervals().mergeIntervalsOptimal(intervals);
        System.out.println(res);
    }

}
