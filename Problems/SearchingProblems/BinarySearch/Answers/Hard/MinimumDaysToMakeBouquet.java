package Problems.SearchingProblems.BinarySearch.Answers.Hard;

public class MinimumDaysToMakeBouquet {
    // Problem: https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/description/

    //Brute Force
    public static boolean isPossible(int[] bloomDay, int m, int k, int days) {
        int bouquets = 0, flowers = 0;
        for (int j : bloomDay) {
            if (j <= days) {
                flowers++;
                if (flowers == k) {
                    bouquets++;
                    flowers = 0;
                }
            } else {
                flowers = 0;
            }
        }
        return bouquets >= m;
    }

    public static int minDays(int[] bloomDay, int m, int k) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(int j: bloomDay) {
            min = Math.min(min, j);
            max = Math.max(max, j);
        }

        for(int i = min; i <= max; i++) {
            if(isPossible(bloomDay, m, k, i)) {
                return i;
            }
        }
        return -1;
    }

    // Optimal Solution: Binary Search
    // Time Complexity: O(nlogn) time | O(1) space
    public static int minDaysOptimal(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        if(n < m*k) return -1;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(int j: bloomDay) {
            min = Math.min(min, j);
            max = Math.max(max, j);
        }

        int low = min, high = max;
        int ans = -1;
        while(low <= high){
            int mid = low + (high - low) / 2;
            if(isPossible(bloomDay, m, k, mid)){
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }

        }
        return ans;
    }

    public static void main(String[] args) {
        int[] bloomDay = {1,10,3,10,2};
        int m = 3, k = 1;
        System.out.println(minDays(bloomDay, m, k));
        System.out.println(minDaysOptimal(bloomDay, m, k));
    }
}
