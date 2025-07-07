package Problems.Arrays.Easy;

import java.util.ArrayList;
import java.util.List;

public class GenerateAllSubarrays {
    public static List<List<Integer>> subarrays(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                List<Integer> sub = new ArrayList<>();
                for (int k = i; k <= j; k++) {
                    sub.add(nums[k]);
                }
                ans.add(sub);
            }
        }
        return ans;
    }

    public static List<List<Integer>> subarrays2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> sub = new ArrayList<>();
            for (int j = i; j < nums.length; j++) {
                sub.add(nums[j]);       // dynamically extend the subarray
                ans.add(new ArrayList<>(sub)); // add a copy of it to result
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(subarrays(nums));
        System.out.println(subarrays2(nums));
    }
}
