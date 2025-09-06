package Problems.Greedy.Medium;
import java.util.*;

public class SJFII {
    // Problem: https://www.naukri.com/code360/problems/sjf_1172165

    // Difference from the SJFI Question
    //In the previous version:
        //All processes arrived at time 0, so we just sorted by burst time.
    //In this variation:
    //We are given arrival time + burst time for each process.
        //→ That means a process may not be ready when CPU is free.
        //→ We need to carefully choose the next process that is already arrived and has the shortest burst time.
    //This is closer to a real OS scheduling problem.
    // Arrival Time (AT): When a process enters the ready queue.
    //Burst Time (BT): How long a process needs CPU.
    //Completion Time (CT): Time at which a process finishes.
    //Turnaround Time (TAT): TAT = CT - AT
    //Waiting Time (WT): WT = TAT - BT
    // Average Waiting Time (AWT): AWT = sum(WT) / n
    // Using Greedy Approach:

    static class Process {
        int at, bt, id;
        Process(int id, int at, int bt) {
            this.id = id;
            this.at = at;
            this.bt = bt;
        }
    }
    public static float sjf(int n, int []arrivalTime, int []burstTime)
    {
        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            processes.add(new Process(i, arrivalTime[i], burstTime[i]));
        }

        // Step 1: Sort by arrival time
        processes.sort(Comparator.comparingInt(p -> p.at));

        // Min-heap based on burst time
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.bt));

        int time = 0, idx = 0;
        int totalWT = 0;

        while (idx < n || !pq.isEmpty()) {
            // Add all processes that have arrived by current time
            while (idx < n && processes.get(idx).at <= time) {
                pq.add(processes.get(idx));
                idx++;
            }

            if (pq.isEmpty()) {
                // Jump to the next process arrival if no process in queue
                time = processes.get(idx).at;
                continue;
            }

            // Pick shortest burst process
            Process curr = pq.poll();
            time += curr.bt;
            int tat = time - curr.at;
            int wt = tat - curr.bt;
            totalWT += wt;
        }

        return (float) totalWT / n;

    }

    public static void main(String[] args) {
        int n = 3;
        int[] arrivalTime = {0, 0, 0};
        int[] burstTime = {3, 1, 2};
        System.out.println(sjf(n, arrivalTime, burstTime)); // 10
    }
}
