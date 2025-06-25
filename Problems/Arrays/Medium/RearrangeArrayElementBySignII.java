package Problems.Arrays.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RearrangeArrayElementBySignII {
    // Problem: it is a variation of Rearrange Array Element by Sign
    // https://leetcode.com/problems/rearrange-array-elements-by-sign/description/
    // Here we have positive elements !== negative elements

    // We fall back to Brute force here

    public int[] rearrangeArray(int[] nums) {
        int n = nums.length;
        List<Integer> pos = new ArrayList<>(); // to store positive elements <0>
        List<Integer> neg = new ArrayList<>(); // to store negative elements <0>
        for (int num : nums) {
            if (num > 0) {
                pos.add(num);
            } else {
                neg.add(num);
            }
        }
        int[] ans = new int[n];

        if(pos.size() > neg.size()){  // if no. of positive elements > no. of negative elements, we add positive and negative elements alternatively till no. of negative elements
            for(int i=0; i < neg.size(); i++){
                ans[2*i] = pos.get(i);
                ans[2*i+1] = neg.get(i);
            }
            // then add remaining positive elements. starting from index = no. of negative elements * 2
            int index = neg.size() * 2;
            for(int i = neg.size(); i < pos.size(); i++){
                ans[index++] = pos.get(i);
            }
        } else {  // vice versa for no. of positive elements < no. of negative elements
            for(int i=0; i < pos.size(); i++){
                ans[2*i] = pos.get(i);
                ans[2*i+1] = neg.get(i);
            }
            int index = pos.size() * 2;
            for(int i = pos.size(); i < neg.size(); i++){
                ans[index++] = neg.get(i);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {3, 1, -2, -5, 2, -4};
        System.out.println(Arrays.toString(new RearrangeArrayElementBySignII().rearrangeArray(nums)));
    }
}
