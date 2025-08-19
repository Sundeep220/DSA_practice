package Problems.Heaps.Hard.TaskScheduler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskScheduler {
    // Problem: https://leetcode.com/problems/task-scheduler/



    // Using MaxHeap, Queue
    // we found in the problem that for all given taks we must complete the task with
    // maximum frequency. So we can use the maxHeap to find the maximum frequency and
    // we then use the queue to store the cooldown time for the task with maximum frequency.
    // The after the cooldown time is over, we can add the task to the heap again.

    public static int leastIntervalI(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char c : tasks) {
            freq[c - 'A']++;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(int f: freq){
            if(f > 0) maxHeap.offer(f);
        }

        // Now start the time and process the task
        int time = 0;
        Queue<int[]> cooldown = new LinkedList<>();  // {remainingFreq, nextAvailableTime}
        while(!maxHeap.isEmpty() || !cooldown.isEmpty()){
            time++;
            // Step 1: Execute one task from heap
            if(!maxHeap.isEmpty()){
                int currentTaskFreq = maxHeap.poll();
                currentTaskFreq--;
                if(currentTaskFreq > 0)
                    cooldown.offer(new int[]{currentTaskFreq, time + n });  // this task will be available at time + n
            }
            // Step 2: If a task finished cooldown, push back to heap
            if (!cooldown.isEmpty() && cooldown.peek()[1] == time) {
                maxHeap.add(cooldown.poll()[0]);
            }
        }

        return time;
    }








    // Most Optimal Solution: O(n) time | O(1) space
    // Using Greedy + Maths Approach
    public static int leastIntervalII(char[] tasks, int n) {
        int[] count = new int[26];
        for (char c : tasks) {
            count[c - 'A']++;
        }
        int maxCount = 0;
        for (int i : count) {
            maxCount = Math.max(maxCount, i);
        }
        int maxCountCount = 0;
        for (int i : count) {
            if (i == maxCount) {
                maxCountCount++;
            }
        }
        return Math.max(tasks.length, (maxCount - 1) * (n + 1) + maxCountCount);
    }

    public static void main(String[] args) {
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n = 2;
        System.out.println(leastIntervalI(tasks, n)); // 8
        System.out.println(leastIntervalII(tasks, n)); // 8
    }
}
