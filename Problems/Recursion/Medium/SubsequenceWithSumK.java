package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.List;

public class SubsequenceWithSumK {
    // Problem: https://leetcode.com/problems/subsequence-sum-equals-k/

    // Using Recursion + Backtracking
    public static int subsequenceWithSumKI(int[] nums, int index, int sum, int k) {
        if (index == nums.length) {
            return sum == k ? 1 : 0;
        }
        int count = 0;
        count += subsequenceWithSumKI(nums, index + 1, sum + nums[index], k);
        count += subsequenceWithSumKI(nums, index + 1, sum, k);
        return count;
    }

    public static int subsequenceWithSumKRI(int[] nums, int k) {
        return subsequenceWithSumKI(nums, 0, 0, k);
    }

    // Also return subsequences with sum k
    public static void subsequenceWithSumKII(int[] nums, int index, int sum, int k, List<Integer> subsequence, List<List<Integer>> ans) {
        if (index == nums.length) {
            if (sum == k) {
                ans.add(new ArrayList<>(subsequence)); // Create a new List<Integer> and add it to ans
            }
            return;
        }
        // Include nums[index]
        subsequence.add(nums[index]);
        subsequenceWithSumKII(nums, index + 1, sum + nums[index], k, subsequence, ans);
        subsequence.remove(subsequence.size() - 1); // Backtrack

        // Exclude nums[index]
        subsequenceWithSumKII(nums, index + 1, sum, k, subsequence, ans);
    }

    public static List<List<Integer>> subsequenceWithSumKRII(int[] nums, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        subsequenceWithSumKII(nums, 0, 0, k, new ArrayList<>(), ans);
        return ans;
    }

    // To only print any One Subsequence with sum k
    public static boolean findAnySubsequenceWithSumK(int[] nums, int index, int sum, int k, List<Integer> subsequence) {
        if (index == nums.length) {
            if (sum == k) {
//                System.out.println("Subsequence: " + subsequence); // or return the list if needed
                return true;
            }
            return false;
        }

        // Include nums[index]
        subsequence.add(nums[index]);
        if (findAnySubsequenceWithSumK(nums, index + 1, sum + nums[index], k, subsequence)) {
            return true;
        }
        subsequence.remove(subsequence.size() - 1); // backtrack

        // Exclude nums[index]
        if (findAnySubsequenceWithSumK(nums, index + 1, sum, k, subsequence)) {
            return true;
        }

        return false;
    }

    public static List<Integer> subsequenceWithSumK_One(int[] nums, int k) {
        List<Integer> subsequence = new ArrayList<>();
        boolean found = findAnySubsequenceWithSumK(nums, 0, 0, k, subsequence);
        return found ? subsequence : null;
    }


    public static void main(String[] args) {
        int[] nums = {1, 1, 4, 5};
        int k = 5;

        System.out.println("Count: " + subsequenceWithSumKRI(nums, k)); // 2
        System.out.println("Subsequences: " + subsequenceWithSumKRII(nums, k));
        System.out.println("One Subsequence: " + subsequenceWithSumK_One(nums, k));
    }
}
