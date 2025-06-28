package Problems.Arrays.Hard;

import java.util.HashMap;
import java.util.Map;

public class CountSubarraysWithXORK {
    // Problem: https://leetcode.com/problems/count-subarrays-with-xor-k/

    // Brute Force: O(n^2) time | O(1) space
    public static int subarrayXor(int[] nums, int k) {
        int count = 0;
        for(int i=0; i<nums.length; i++){
            int xor = 0;
            for(int j=i; j<nums.length; j++){
                xor ^= nums[j];
                if(xor == k){
                    count++;
                }
            }
        }
        return count;
    }

    // Better Solution: O(n) time | O(n) space
    public static int subarrayXorBetter(int[] nums, int k) {
        int count = 0, xor = 0;
        Map<Integer, Integer> prefix = new HashMap<>(); // storing the freq of xor for each subarray
        for (int num : nums) {
            xor ^= num;
            if (xor == k) {
                count++;
            }
            if (prefix.containsKey(xor ^ k)) {
                count += prefix.get(xor ^ k);
            }
            prefix.put(xor, prefix.getOrDefault(xor, 0) + 1);
        }
        return count;
    }

    // Optimal Solution: O(n) time | O(1) space
    public static int subarrayXorOptimal(int[] nums, int k) {
        int count = 0, xor = 0;
        for(int num: nums){
            xor ^= num;
            if(xor == k){
                count++;
            }
            int xor2 = xor ^ k;
            if(xor2 == 0){
                count++; // xor ^ k == 0 can only be true for the first xor == k
        }
    }
    return count;
    }

    public static void main(String[] args) {
        int[] nums = {4, 2, 2, 6, 4};
        int k = 6;
        System.out.println(subarrayXor(nums, k));
        System.out.println(subarrayXorBetter(nums, k));
        System.out.println(subarrayXorOptimal(nums, k));
    }
}
