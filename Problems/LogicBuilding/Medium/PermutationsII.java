package Problems.LogicBuilding.Medium;
import java.util.*;
public class PermutationsII {
    // Problem: https://leetcode.com/problems/permutations-ii/
    // Solution: Approach will be same as we did in generating all permutations but we will exclude the duplicates
    // How to exclude the duplicates: We will sort the array and then we will generate the permutations by smartly
    // excluding the duplicates
    // Solution1: Use Set to store the elements we have already visited and then exclude the duplicates
    // Solution2(Better): Use a boolean check which checks if the element is already visited or not in current recursion call

        public List<List<Integer>> permuteUniqueUsingBoolean(int[] nums) {
            List<List<Integer>> ans = new ArrayList<>();
            if(nums == null || nums.length == 0)return ans;
            Arrays.sort(nums); // grouping duplicates together to avoid them simultaneously
            permuteUsingBoolean(ans, 0, nums);
            return ans;
        }

        public void permuteUsingBoolean(List<List<Integer>> ans, int start, int[] nums){
            if(start == nums.length){
                ArrayList<Integer> temp = new ArrayList<>();
                for(int ele: nums) temp.add(ele);
                ans.add(temp);
                return;
            }

            for(int i = start; i< nums.length; i++){
                if(shouldSwap(nums, start, i)){
                    swap(nums, start, i);
                    permuteUsingBoolean(ans, start+1, nums);
                    swap(nums, start, i);
                }
            }
        }

        private boolean shouldSwap(int[] nums, int start, int curr) {
            for (int i = start; i < curr; i++) {
                if (nums[i] == nums[curr]) return false; // duplicate found
            }
            return true;
        }


    public List<List<Integer>> permuteUniqueUsingSet(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums); // grouping duplicates together to avoid them simultaneously
        permuteUsingSet(ans, 0, nums);
        return ans;
    }

    // Using Set: Good but slow in comparison to above
    public void permuteUsingSet(List<List<Integer>> ans, int start, int[] nums){
        if(start == nums.length){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int ele: nums) temp.add(ele);
            ans.add(temp);
            return;
        }

        // To keep track of duplicates
        HashSet<Integer> set = new HashSet<>();
        for(int i = start; i< nums.length; i++){
            if(set.contains(nums[i])){
                continue;
                // Skip swapping and backtracking as it was already done
            }
            set.add(nums[i]);
            swap(nums, start, i);
            permuteUsingSet(ans, start+1, nums);
            swap(nums, start, i);
        }
    }

        public void swap(int[] nums, int start, int end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
        }
}
