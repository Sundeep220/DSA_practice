package Problems.Arrays.Hard;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FindMissingAndRepeatingNumbber {
    // Problem: Given an array of size N-1 such that it only contains distinct integers in the range of 1 to N. Find the missing element and the repeating element.
    //Input Format:  array[] = {3,1,2,5,3}
    //Result: {3,4)
    //Explanation: A = 3 , B = 4
    //Since 3 is appearing twice and 4 is missing

    // Brute Force: O(n^2) time | O(1) space
    // Finding freq of each number using linear search
    public static int[] findMissingAndRepeatingBrute(int[] a) {
        int n = a.length; // size of the array
        int repeating = -1, missing = -1;

        //Find the repeating and missing number:
        for (int i = 1; i <= n; i++) {
            //Count the occurrences:
            int cnt = 0;
            for (int k : a) {
                if (k == i) cnt++;
            }

            if (cnt == 2) repeating = i;
            else if (cnt == 0) missing = i;

            if (repeating != -1 && missing != -1)
                break;
        }
        int[] ans = {repeating, missing};
        return ans;
    }

    // Better Solution: O(n) time | O(n) space
    // Using Hashing to find missing and repeating
    public static int[] findMissingAndRepeatingHash(int[] a) {
        int n = a.length; // size of the array
        int[] hash = new int[n + 1]; // hash array

        //update the hash array:
        for (int j : a) {
            hash[j]++;
        }

        //Find the repeating and missing number:
        int repeating = -1, missing = -1;
        for (int i = 1; i <= n; i++) {
            if (hash[i] == 2) repeating = i;
            else if (hash[i] == 0) missing = i;

            if (repeating != -1 && missing != -1)
                break;
        }
        return new int[]{repeating, missing};
    }

    // Optimal Solution: O(nlogn) time | O(1) space
    // Using sorting
    public static int[] findMissingAndRepeating(int[] nums) {
        int n = nums.length;
        int[] ans = new int[2];
        Arrays.sort(nums);
        int sum = 0;
        for(int i=0; i<n; i++){
            if(i > 0 && nums[i] == nums[i-1]){
                ans[0] = nums[i]; // repeating element
            }else{
                sum += nums[i]; // sum of all elements
            }
        }
        int totalSum = (n*(n+1))/2;
        ans[1] = totalSum - sum; // missing element
        return ans;

    }

    // Optimal Solution: O(n) time | O(1) space
        /*
    ==========================================================
    Approach : Math (Sum + Sum of Squares)

    Intuition:

    Let:
    R = Repeating number
    M = Missing number

    1. Compare expected sum with actual sum.

    Expected Sum     = n(n+1)/2
    Actual Sum       = sum(arr)

    => R - M = ActualSum - ExpectedSum

    2. Compare expected square sum with actual square sum.

    Expected Square Sum = n(n+1)(2n+1)/6
    Actual Square Sum   = sum(arr[i]²)

    => R² - M² = ActualSquareSum - ExpectedSquareSum

    Using:
    R² - M² = (R - M)(R + M)

    We already know (R - M), so we can find (R + M).

    Finally solve:

    R = ((R - M) + (R + M)) / 2
    M = (R + M) - R

    Time  : O(n)
    Space : O(1)

    Note:
    Use long to avoid integer overflow.
    ==========================================================
    */
    public static int[] findMissingAndRepeatingOptimal(int[] nums) {
        int n = nums.length;

        long expectedSum = (long) n * (n + 1) / 2;
        long expectedSquareSum = (long) n * (n + 1) * (2L * n + 1) / 6;

        long actualSum = 0;
        long actualSquareSum = 0;

        for (int num : nums) {
            actualSum += num;
            actualSquareSum += (long) num * num;
        }

        long diff = actualSum - expectedSum;                  // R - M

        long squareDiff = actualSquareSum - expectedSquareSum; // R² - M²

        long sum = squareDiff / diff;                          // R + M

        int repeating = (int) ((diff + sum) / 2);
        int missing = (int) (sum - repeating);

        return new int[]{repeating, missing};
    }

    // Optimal Solution: Using Bit Manipulation with Bucket Technique
    public static int[] findMissingAndRepeatingXOR(int[] a) {
        int n = a.length; // size of the array
        int xr = 0;

        //Step 1: Find XOR of all elements:
        for (int i = 0; i < n; i++) {
            xr = xr ^ a[i];
            xr = xr ^ (i + 1);
        }

        //Step 2: Find the differentiating bit number:
//        int number = (xr & ~(xr - 1));  one line trick to find the differentiating bit

        int bitPos = 0;
        while ((xr & (1 << bitPos)) == 0) {
            bitPos++;
        }
        int number = 1 << bitPos;
        //Step 3: Group the numbers:
        int zero = 0;
        int one = 0;

        // Step 3: Group the numbers in array
        for (int j : a) {
            //part of 1 group:
            if ((j & number) != 0) {
                one = one ^ j;
            }
            //part of 0 group:
            else {
                zero = zero ^ j;
            }
        }
        // Step 3: Group the natural numbers
        for (int i = 1; i <= n; i++) {
            //part of 1 group:
            if ((i & number) != 0) {
                one = one ^ i;
            }
            //part of 0 group:
            else {
                zero = zero ^ i;
            }
        }

        // Last step: Identify the numbers:
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] == zero) cnt++;
        }

        if (cnt == 2) return new int[] {zero, one};
        return new int[] {one, zero};
    }

    public static void main(String[] args) {
        int[] nums = {3,1,2,5,3};
        System.out.println(Arrays.toString(findMissingAndRepeatingBrute(nums)));
        System.out.println(Arrays.toString(findMissingAndRepeatingHash(nums)));
        System.out.println(Arrays.toString(findMissingAndRepeating(nums)));
        System.out.println(Arrays.toString(findMissingAndRepeatingOptimal(nums)));
        System.out.println(Arrays.toString(findMissingAndRepeatingXOR(nums)));

    }
}
