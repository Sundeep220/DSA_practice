package Problems.Greedy.Hard;
import java.util.*;
public class SingleThreadedCPU {
    // Problem: https://leetcode.com/problems/single-threaded-cpu/
    // Solution: https://leetcode.com/problems/single-threaded-cpu/solution/

    // Using Greedy approach with minHeap, similarly what we did in SJFII problem, just need to return order instead of waiting time.
    static class Task {
        int enqueue;
        int processing;
        int index;

        Task(int enqueue, int processing, int index) {
            this.enqueue = enqueue;
            this.processing = processing;
            this.index = index;
        }
    }
    public static int[] getOrder(int[][] tasks) {
        int n = tasks.length;

        // Step 1: store tasks as objects
        Task[] taskArr = new Task[n];
        for (int i = 0; i < n; i++) {
            taskArr[i] = new Task(tasks[i][0], tasks[i][1], i);
        }

        // Step 2: sort by enqueueTime
        Arrays.sort(taskArr, Comparator.comparingInt(t -> t.enqueue));

        // Step 3: priority queue by (processingTime, index)
        PriorityQueue<Task> pq = new PriorityQueue<>(
                (a, b) -> (a.processing == b.processing) ? a.index - b.index : a.processing - b.processing
        );

        int[] result = new int[n];
        int resIdx = 0;
        long time = 0;
        int i = 0;


        // Step 4: simulation
        while (i < n || !pq.isEmpty()) {
            // If no task available, jump time to next task’s enqueue
            if (pq.isEmpty() && time < taskArr[i].enqueue) {
                time = taskArr[i].enqueue;
            }

            // Add all tasks that are available
            while (i < n && taskArr[i].enqueue <= time) {
                pq.add(taskArr[i]);
                i++;
            }

            // Pick task with the shortest processing time (and the smallest index)
            Task curr = pq.poll();
            result[resIdx++] = curr.index;
            time += curr.processing;
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] tasks = {{1,2},{2,4},{3,2},{4,1}}; // [0,2,3,1]
        System.out.println(Arrays.toString(getOrder(tasks)));
    }
}
