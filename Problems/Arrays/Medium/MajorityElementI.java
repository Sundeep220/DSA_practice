package Problems.Arrays.Medium;

import java.util.HashMap;
import java.util.Map;

public class MajorityElementI {
    // Problem: https://leetcode.com/problems/majority-element/

    // Brute Force: Going for all elements and checking if that element is majority element or not
    // Time Complexity: O(n^2) Space Complexity: O(1)
    public static int majorityElement(int[] nums) {
        int n = nums.length;
        for(int i=0; i<n; i++) {
            int count = 0;
            for(int j=0; j<n; j++) {
                if(nums[i] == nums[j]) count++;
            }
            if(count > n/2) return nums[i];
        }
        return -1;
    }

    //Better Solution: Using HashMap
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int majorityElementMap(int[] nums) {
        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
//        for(int key: map.keySet()) {
//            if(map.get(key) > n/2) return key;
//        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > n / 2) return entry.getKey();
        }
        return -1;
    }

    // Optimal Solution: Using Moore's Voting Algorithm
    // Time Complexity: O(n) Space Complexity: O(1)
    public static int majorityElementOptimal(int[] nums) {
        int count = 0, candidate = 0;
        for (int num : nums) {
            if (count == 0) candidate = num;
            if (num == candidate) count++;
            else count--;
        }
        return candidate;
    }

    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("Brute Force Solution: " + majorityElement(nums));
        System.out.println("Using HashMap: " + majorityElementMap(nums));
        System.out.println("Optimal Solution: " + majorityElementOptimal(nums));
    }
}
