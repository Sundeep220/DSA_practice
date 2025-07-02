package Problems.SearchingProblems.BinarySearch.Answers.Hard;

import java.util.Arrays;

public class AggresiveCows {
    // Problem: https://www.naukri.com/code360/problems/aggressive-cows_1082559

    // Brute Force Solution: O(n) time | O(1) space
    public boolean isPossible(int[] stalls, int cows, int dist) {
        int n = stalls.length;
        int countCows = 1, prevCowLoc = stalls[0];
        for(int i = 1; i < n; i++) {
            if(stalls[i] - prevCowLoc >= dist) {
                countCows++;
                prevCowLoc = stalls[i];
            }
        }
        return countCows >= cows;
    }

    public int maxDistance(int[] stalls, int cows) {
        int n = stalls.length;
        Arrays.sort(stalls);
        int start = 1, end = stalls[n - 1] - stalls[0], ans = 0;
        for(int i = start; i <= end; i++) {
            if(isPossible(stalls, cows, i)) {
                ans = i;
            }
        }
        return ans;
    }

    // Optimal Solution: O(nlogn) time | O(1) space
    public int maxDistanceOptimal(int[] stalls, int cows) {
        int n = stalls.length;
        Arrays.sort(stalls);
        int start = 1, end = stalls[n - 1] - stalls[0];
        while(start <= end) {
            int mid = start + (end - start) / 2;
            if(isPossible(stalls, cows, mid)) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return end;
    }

    public static void main(String[] args) {
        int[] stalls = {0, 3, 4, 7, 9, 10};
        int cows = 4;
        System.out.println(new AggresiveCows().maxDistance(stalls, cows));
        System.out.println(new AggresiveCows().maxDistanceOptimal(stalls, cows));
    }
}
