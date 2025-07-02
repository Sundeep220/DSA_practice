package Problems.SearchingProblems.BinarySearch.Answers.Hard;

public class CapacityToShipPackages {
    // Problem: https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/

    //Brute Force:
    public static boolean isPossible(int[] weights, int days, int capacity) {
        int daysRequired = 1, currWeight = 0;
        for (int j : weights) {
            if (j > capacity) return false;
            if (currWeight + j <= capacity) {
                currWeight += j;
            } else {
                daysRequired++;   // new day
                currWeight = j;   // start new day with current weight
            }
        }
        return daysRequired <= days;
    }

    public static int shipWithinDays(int[] weights, int days) {
        int max = 0;
        int min = Integer.MIN_VALUE;
        for (int j : weights) {
            max += j;
            min = Math.max(min, j);
        }

        for(int i = min; i <= max; i++) {
            if(isPossible(weights, days, i)) {
                return i;
            }
        }
        return -1;
    }

    //Optimized Binary Search:
    public static int shipWithinDaysOptimized(int[] weights, int days) {
        int max = 0;
        int min = Integer.MIN_VALUE;
        for (int j : weights) {
            max += j;
            min = Math.max(min, j);
        }

        int low = min, high = max;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(isPossible(weights, days, mid)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        int[] weights = {5,4,5,2,3,4,5,6};
        int days = 5;
        System.out.println(shipWithinDays(weights, days));
        System.out.println(shipWithinDaysOptimized(weights, days));
    }
}
