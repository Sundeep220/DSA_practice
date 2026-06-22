package Problems.SearchingProblems.BinarySearch.Answers.Hard;

public class KokoBanana {
    //Problem Link: https://leetcode.com/problems/koko-eating-bananas/

    //Brute Force Solution: O(n) time | O(1) space
    public static int findMax(int[] v) {
        int maxi = Integer.MIN_VALUE;;
        int n = v.length;
        //find the maximum:
        for (int j : v) {
            maxi = Math.max(maxi, j);
        }
        return maxi;
    }

    public static int calculateTotalHours(int[] v, int hourly) {
        int totalH = 0;
        int n = v.length;
        //find total hours:
        for (int j : v) {
            totalH += (int) Math.ceil((double) j / (double) (hourly));
        }
        return totalH;
    }

    public static int minimumRateToEatBananas(int[] v, int h) {
        //Find the maximum number:
        int maxi = findMax(v);

        //Find the minimum value of k:
        for (int i = 1; i <= maxi; i++) {
            int reqTime = calculateTotalHours(v, i);
            if (reqTime <= h) {
                return i;
            }
        }

        //dummy return statement
        return maxi;
    }


    // Problem similar to Smallest Divisor problem
    // Again we will be using Binary Search
    public static int minEatingSpeed(int[] piles, int h) {
        int maxi = Integer.MIN_VALUE;
        for (int j : piles) {
            maxi = Math.max(maxi, j);
        }

        int low = 1, high = maxi;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            long hours = 0;
            for (int j : piles) {
                hours += (j + mid - 1) / mid;  // Trick to get the ceil value of j / mid
            }
            if (hours <= h) {  // If hours is less than or equal to h, then we can reduce the speed
                high = mid - 1;
            } else {
                low = mid + 1;  // If hours is greater than h, then we can increase the speed
            }
        }
        return low;
    }

    // Another way of writing
    public int minEatingSpeedII(int[] piles, int h) {
        int max = 0;
        for(int pile : piles){
            max = Math.max(max, pile);
        }

        int low = 1, high = max;
        int ans = max;

        while(low <= high){
            int mid = low + (high - low) / 2;

            if(canFinish(mid, piles, h)){
                ans = mid;          // possible → try smaller speed
                high = mid - 1;
            } else {
                low = mid + 1;      // too slow → increase speed
            }
        }
        return ans;
    }

    public boolean canFinish(int k, int[] piles, int h){
        int hours = 0;

        for(int pile : piles){
            hours += (pile + k - 1) / k;  // ceil(pile/k)
        }

        return hours <= h;
    }

    public static void main(String[] args) {
        int[] piles = {3, 6, 7, 11};
        int h = 8;
        System.out.println(minEatingSpeed(piles, h));
        System.out.println((double) 7 / (double) 5);
    }
}
