package Problems.SearchingProblems.BinarySearch.Answers.Easy;

public class SmallestDivisor {

    // Problem link: https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/

    // Brute Force Solution: O(n) time | O(1) space
    public static int smallestDivisor(int[] arr, int limit) {
        int n = arr.length; //size of array.
        //Find the maximum element:
        int maxi = Integer.MIN_VALUE;
        for (int j : arr) {
            maxi = Math.max(maxi, j);
        }

        //Find the smallest divisor:
        for (int d = 1; d <= maxi; d++) {
            //Find the summation result:
            int sum = 0;
            for (int j : arr) {
                sum += (int) Math.ceil((double) j / (double) (d));
            }
            if (sum <= limit)
                return d;
        }
        return -1;
    }


    // Binary Search Solution: O(nlogn) time | O(1) space
    public static int sumByD(int[] arr, int div) {
        int n = arr.length; //size of array
        //Find the summation of division values:
        int sum = 0;
        for (int j : arr) {
            sum += (int) Math.ceil((double) j / (double) (div));
        }
        return sum;
    }
    public static int smallestDivisorOptimized(int[] arr, int limit) {
        int n = arr.length; //size of array.
        if(n > limit) return -1;
        //Find the maximum element:
        int maxi = Integer.MIN_VALUE;
        for (int j : arr) {
            maxi = Math.max(maxi, j);
        }

        int low = 1, high = maxi;

        //Apply binary search:
        while (low <= high) {
            int mid = (low + high) / 2;
            if (sumByD(arr, mid) <= limit) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        int[] arr = {8, 5, 6, 9};
        int limit = 9;
        System.out.println(smallestDivisor(arr, limit));
        System.out.println(smallestDivisorOptimized(arr, limit));
    }
}
