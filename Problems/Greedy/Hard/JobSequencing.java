package Problems.Greedy.Hard;

import java.util.Arrays;

public class JobSequencing {
    // Problem: https://www.naukri.com/code360/problems/job-sequencing-problem_1169460

    // Using Greedy Algorithm
    // Follow these steps:
    // 1. For any job with deadline d, delay the job till the end day so that other jobs can also be done
    // 2. Maximise profit, so sort the jobs in order of profit

    static class Job {
        int id, deadline, profit;
        Job(int id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    public static int[] jobScheduling(int[][] Jobs) {
        int n = Jobs.length;
        Job[] arr = new Job[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Job(Jobs[i][0], Jobs[i][1], Jobs[i][2]);
        }

        // Step 1: Sort by profit descending
        Arrays.sort(arr, (a, b) -> b.profit - a.profit);

        // Step 2: Find maximum deadline
        int maxDeadline = 0;
        for (Job job : arr) {
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }

        // Step 3: Time slots for jobs (-1 means free)
        int[] slot = new int[maxDeadline + 1];
        Arrays.fill(slot, -1);

        int jobCount = 0, totalProfit = 0;

        // Step 4: Schedule jobs greedily
        for (Job job : arr) {
            for (int t = job.deadline; t > 0; t--) {
                if (slot[t] == -1) {
                    slot[t] = job.id;
                    jobCount++;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        return new int[]{jobCount, totalProfit};
    }
}
