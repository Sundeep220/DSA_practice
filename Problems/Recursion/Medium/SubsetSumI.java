package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class SubsetSumI {
    // Problem: https://www.naukri.com/code360/problems/subset-sum_3843086?leftPanelTabValue=PROBLEM
    //You are given an array 'nums' of ‘n’ integers
   //Return all subset sums of 'nums' in a non-decreasing order.

    public static ArrayList<Integer> subsetSumI(int[] nums) {
        ArrayList<Integer> ans = new ArrayList<>(); // to store all the subset sums
        generateSubsets(nums, 0, 0, ans);
        Collections.sort(ans);
        return ans;
    }

    public static void generateSubsets(int[] nums, int index, int sum, ArrayList<Integer> ans) {
        if (index == nums.length) {
                ans.add(sum);
            return;
        }
        // Include nums[index]
        generateSubsets(nums, index + 1, sum + nums[index], ans);

        // Exclude nums[index]
        generateSubsets(nums, index + 1, sum, ans);
    }

    public static void main(String[] args) {
        int[] nums = {5, 2, 1};
        System.out.println(subsetSumI(nums));
    }
}

