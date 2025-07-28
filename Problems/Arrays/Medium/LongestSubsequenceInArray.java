package Problems.Arrays.Medium;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LongestSubsequenceInArray {

    // Problem: You are given an array of ‘N’ integers. You need to find the length of the longest sequence which contains the consecutive elements.
    // Solution: Sort the array in ascending order. Then, check for the consecutive elements.
    // Time Complexity: O(nlogn), Space Complexity: O(1)
    public static int longestSubsequence(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);  // Sort the array
        int longestSequence = 1;
        int currentSequence = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue; // skip duplicates
            }
            if (nums[i] == nums[i - 1] + 1) {
                currentSequence++;
            } else {
                currentSequence = 1;
            }
            longestSequence = Math.max(longestSequence, currentSequence);
        }
        return longestSequence;
    }

    //Optimal Solution: Using HashSet
    // Time Complexity: O(n), Space Complexity: O(n)
    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longestStreak = 0;

        for (int num : set) {
            // Only start counting if 'num' is the start of a sequence
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }




    public static void main(String[] args) {
//        int[] nums = {1, 9, 3, 10, 4, 20, 2};
        int[] nums =  {100, 200, 1, 3, 2, 4};
        System.out.println(longestSubsequence(nums)); // Output: 4: 1, 2, 3, 4
        System.out.println(longestConsecutive(nums));
    }
}
