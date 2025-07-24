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
    // Using Maths equations, lets assume X is repeating and Y is missing
    // S - Sn = X - Y
    // S2 - Sn2 = X2 - Y2
    // We can solve these equations to get the values of X and Y
    public static int[] findMissingAndRepeatingOptimal(int[] nums) {
        int n = nums.length;
        int[] ans = new int[2];
        int sum = 0, sumSquare = 0;
        for (int num : nums) {
            sum += num;
            sumSquare += num * num;
        }
        int sumN = (n * (n + 1)) / 2;
        int sumNSquare = (n * (n + 1) * (2 * n + 1)) / 6;

        int val1 = sum - sumN;  // X - Y
        int val2 = sumSquare - sumNSquare; // X2 - Y2
        val2 = val2 / val1; // X + Y = X2 - Y2 / X - Y
        int x = (val1 + val2) / 2; // lets say X + Y = 3, X - Y = 1 => 2X = 4 => X = 2
        int y = x - val1; // X + Y = 3, X - Y = 1 => Y = 4 - 2 => Y = 2
        return new int[]{x, y};
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
