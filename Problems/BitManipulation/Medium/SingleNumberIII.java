package Problems.BitManipulation.Medium;

public class SingleNumberIII {
    // Problem: https://leetcode.com/problems/single-number-iii/

    // Brute Force: O(n) time | O(1) space
    public int[] singleNumber(int[] nums) {
        int[] ans = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    continue;
                } else {
                    ans[0] = nums[i];
                    ans[1] = nums[j];
                }
            }
        }
        return ans;
    }

    // Optimal Solution: O(n) time | O(1) space
    public int[] singleNumberOptimal(int[] nums) {
        // Step 1: XOR all numbers to get xor = a ^ b
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }

        // Step 2: Find any set bit (rightmost set bit) in xor or another formula => (num & (num - 1)) ^ num
        int diffBit = xor & -xor; // isolates the rightmost set bit

        // Step 3: Partition numbers based on diffBit and XOR separately using bucket technique
        int a = 0, b = 0;
        for (int num : nums) {
            if ((num & diffBit) == 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }

        return new int[] {a, b};
    }
}
