package Problems.DynamicProgramming.TwoDArray;

import java.util.Arrays;

public class NinjaTraining {

    // Problem: https://www.naukri.com/code360/problems/ninja%E2%80%99s-training_3621003?leftPanelTabValue=PROBLEM
    // Ninja Training (No same activity on consecutive days)
    // Given N days and 3 activities per day with merit points.
    // Maximize total merit points with constraint:
    // Same activity cannot be done on consecutive days.

    // ---------------------------------------------------
    // Brute Force: Pure Recursion
    // Try all activities for each day
    // Time: O(3^n)
    // Space: O(n) -> recursion stack
    // ---------------------------------------------------
    public static int ninjaTrainingI(int day, int last, int[][] points) {

        // Base case: Day 0
        if (day == 0) {
            int max = 0;
            for (int task = 0; task < 3; task++) {
                if (task != last) {
                    max = Math.max(max, points[0][task]);
                }
            }
            return max;
        }

        int max = 0;
        for (int task = 0; task < 3; task++) {
            if (task != last) {
                int merit = points[day][task]
                        + ninjaTrainingI(day - 1, task, points);
                max = Math.max(max, merit);
            }
        }

        return max;
    }

    // ---------------------------------------------------
    // Better: Recursion + Memoization (Top-Down DP)
    // Avoid recomputation using dp array
    // Time: O(n * 3 * 3) ≈ O(n)
    // Space: O(n * 4) + recursion stack
    // ---------------------------------------------------
    public static int ninjaTrainingII(int day, int last,
                                      int[][] points, int[][] dp) {

        if (dp[day][last] != -1) return dp[day][last];

        // Base case
        if (day == 0) {
            int max = 0;
            for (int task = 0; task < 3; task++) {
                if (task != last) {
                    max = Math.max(max, points[0][task]);
                }
            }
            return dp[day][last] = max;
        }

        int max = 0;
        for (int task = 0; task < 3; task++) {
            if (task != last) {
                int merit = points[day][task]
                        + ninjaTrainingII(day - 1, task, points, dp);
                max = Math.max(max, merit);
            }
        }

        return dp[day][last] = max;
    }

    // ---------------------------------------------------
    // Better: Tabulation (Bottom-Up DP)
    // Build dp table iteratively
    // Time: O(n * 3 * 3) ≈ O(n)
    // Space: O(n * 4)
    // ---------------------------------------------------
    public static int ninjaTrainingIII(int[][] points) {

        int n = points.length;
        int[][] dp = new int[n][4];

        // Base case: Day 0
        dp[0][0] = Math.max(points[0][1], points[0][2]);
        dp[0][1] = Math.max(points[0][0], points[0][2]);
        dp[0][2] = Math.max(points[0][0], points[0][1]);
        dp[0][3] = Math.max(points[0][0],
                Math.max(points[0][1], points[0][2]));

        for (int day = 1; day < n; day++) {
            for (int last = 0; last < 4; last++) {
                dp[day][last] = 0;

                for (int task = 0; task < 3; task++) {
                    if (task != last) {
                        int merit = points[day][task]
                                + dp[day - 1][task];
                        dp[day][last] = Math.max(dp[day][last], merit);
                    }
                }
            }
        }

        return dp[n - 1][3];
    }

    // ---------------------------------------------------
    // Most Optimal: Space Optimized DP
    // Only previous day's values are required
    // Time: O(n)
    // Space: O(1)
    // ---------------------------------------------------
    public static int ninjaTrainingIV(int[][] points) {

        int n = points.length;
        int[] prev = new int[4];

        // Base case
        prev[0] = Math.max(points[0][1], points[0][2]);
        prev[1] = Math.max(points[0][0], points[0][2]);
        prev[2] = Math.max(points[0][0], points[0][1]);
        prev[3] = Math.max(points[0][0],
                Math.max(points[0][1], points[0][2]));

        for (int day = 1; day < n; day++) {
            int[] curr = new int[4];

            for (int last = 0; last < 4; last++) {
                curr[last] = 0;
                for (int task = 0; task < 3; task++) {
                    if (task != last) {
                        int merit = points[day][task] + prev[task];
                        curr[last] = Math.max(curr[last], merit);
                    }
                }
            }
            prev = curr;
        }

        return prev[3];
    }

    // ---------------------------------------------------
    // Driver Code
    // ---------------------------------------------------
    public static void main(String[] args) {

        int[][] points = {
                {1, 2, 5},
                {3, 1, 1},
                {3, 3, 3}
        };

        int n = points.length;

        int[][] dp = new int[n][4];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Brute Force Recursion: "
                + ninjaTrainingI(n - 1, 3, points));

        System.out.println("Memoization: "
                + ninjaTrainingII(n - 1, 3, points, dp));

        System.out.println("Tabulation: "
                + ninjaTrainingIII(points));

        System.out.println("Space Optimized DP: "
                + ninjaTrainingIV(points));
    }
}

