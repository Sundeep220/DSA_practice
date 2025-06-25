package Problems.Arrays.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RearrangeArrayElementsBySign {
    // https://leetcode.com/problems/rearrange-array-elements-by-sign/
    // Note: It is given that no. of positive and negative elements is same

    // Brute Force:
    // Since no. of positive and negative elements is same, we can store them in
    // two separate arrays and merge them in a single array
    // Time Complexity: O(n) Space Complexity: O(n)
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
        for(int i=0; i<n/2; i++){
            ans[2*i] = pos.get(i);
            ans[2*i+1] = neg.get(i);
        }
        return ans;
    }

    // Optimal Solution: We will add the positive elements first and then the negative elements
    // in-place in the array. We can do this by using two pointers, one for positive elements
    // and one for negative elements, we initialize posIdx = 0 as we have to start ans from positive
    // elements and negIdx = 1
    // If the current element is positive, we will add it to the array at index posIdx and
    // increment posIdx by 2, and if the current element is negative, we will add it to the
    // array at index negIdx and increment negIdx by 2
    // Time Complexity: O(n) Space Complexity: O(1)
    public int[] rearrangeArrayOptimal(int[] nums) {
        int n = nums.length;
        int posIdx = 0, negIdx = 1;
        int[] ans = new int[n];
        for (int num : nums) {
            if(num > 0){
                ans[posIdx] = num;
                posIdx += 2;
            }else{
                ans[negIdx] = num;
                negIdx += 2;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {3,1,-2,-5,2,-4};
        RearrangeArrayElementsBySign obj = new RearrangeArrayElementsBySign();
        System.out.println(Arrays.toString(obj.rearrangeArray(nums)));
        System.out.println(Arrays.toString(obj.rearrangeArrayOptimal(nums)));
    }
}
