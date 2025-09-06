package Problems.Greedy.Easy;

import java.util.Arrays;

public class SJFI {

    // Problem: https://www.geeksforgeeks.org/problems/shortest-job-first/0

    // Using Greedy Approach: Time Complexity: O(nlogn) | Space Complexity: O(1)
    // Sort the jobs in increasing order of their burst time.
    static int solve(int bt[]) {
        // code here
        int n = bt.length;

        Arrays.sort(bt);


        int totalTime = 0, currTime = 0;
        for(int i = 0; i < n; i++){
            totalTime += currTime;
            currTime += bt[i];
        }


        double avgTime = (double) totalTime / n;
        return (int) Math.floor(avgTime);

    }

    public static void main(String[] args) {
        int[] bt = {1, 2, 3, 4, 5};
        System.out.println(solve(bt));
    }
}
