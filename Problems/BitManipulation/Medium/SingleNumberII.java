package Problems.BitManipulation.Medium;

import java.util.Arrays;

public class SingleNumberII {
    // Problem: https://leetcode.com/problems/single-number-ii/

    // Count how many times each bit (0 to 31) appears across all numbers.
    //For each bit position:
    //Sum all bits at that position for all numbers.
    //Take modulo 3: if sum % 3 != 0, it means the single number has a 1 in that bit.
    //⏱ Time: O(32n) = O(n)
    //⏱ Space: O(1)

    public int singleNumber(int[] nums) {
        int result = 0;
        for(int bitIndex = 0; bitIndex < 32; bitIndex++) {
            int count = 0;
            for(int num : nums) {
                if((num & (1 << bitIndex)) != 0) {
                    count++;
                }
            }
            if(count % 3 != 0) {
                result |= 1 << bitIndex;  // set the that bit index
            }
        }
        return result;
    }


    // Optimal Solution: Using sorting
    // Time Complexity: O(nlogn), Space Complexity: O(1)
    public int singleNumberOptimal(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i += 3) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return nums[nums.length - 1];
    }

    public static void main(String[] args) {
        int[] nums = {2, 2, 3, 2};
        SingleNumberII solution = new SingleNumberII();
        int result = solution.singleNumber(nums);
        System.out.println(result);
    }
}
