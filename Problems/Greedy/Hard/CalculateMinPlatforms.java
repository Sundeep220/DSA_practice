package Problems.Greedy.Hard;

import java.util.Arrays;

public class CalculateMinPlatforms {
    // Problem:https://www.naukri.com/code360/problems/minimum-number-of-platforms_799400

    // Brute Force Solution: O(n^2) time | O(1) space
    public static int findPlatforms(int[] AT, int[] DT, int n) {
        int result = 1; // at least one platform needed

        for (int i = 0; i < n; i++) {
            int count = 1; // platform for train i

            for (int j = i + 1; j < n; j++) {
                // If train j overlaps with train i
                if (AT[i] <= DT[j] && AT[j] <= DT[i]) {
                    count++;
                }
            }
            result = Math.max(result, count);
        }
        return result;
    }

    // Optimal Solution: O(n) time | O(1) space
    public static int findPlatformsOptimal(int[] at, int[] dt) {
        int i = 0, j = 0;
        int platformCount = 0, maxPlatforms = 0;
            Arrays.sort(at);
            Arrays.sort(dt);
            while (i < at.length) {
            if (at[i] <= dt[j]) {   // new train arrives before the earliest train departs
                platformCount++;
                i++;
            } else {                // a train has departed
                platformCount--;
                j++;
            }
            maxPlatforms = Math.max(maxPlatforms, platformCount);
        }
            return maxPlatforms;
    }

    public static void main(String[] args) {
        int[] at = { 900, 940, 950, 1100, 1500, 1800 };
        int[] dt = { 945, 1000, 1020, 1150, 1900, 2000 };
        System.out.println(findPlatforms(at, dt, at.length));
        System.out.println(findPlatformsOptimal(at, dt));
    }
}
