package Problems.Arrays.Easy;

import java.util.HashMap;
import java.util.Map;

public class FindNumberThatAppearsOnce {
    // Given an array of integers where, each element appears twice except for one. Find that single one.

    // Better Solution: Using HashMap
    // Time Complexity: O(n), Space Complexity: O(n)
    // Using Hashing
    public static int singleNumberMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) return entry.getKey();
        }
        return -1;
    }

    public static int singleNumberHashingArray(int[] nums) {
        int max = 0;
        for(int num : nums){
            max = Math.max(max, num);
        }
        // create hash array with size max + 1
        int[] hash = new int[max + 1];
        for(int num : nums){
            hash[num]++;   // storing frequency of each number
        }

        // return the number with frequency 1
        for(int i = 0; i < hash.length; i++){
            if(hash[i] == 1){
                return i;
            }
        }
        return -1;

    }

    // Optimal Solution: Using XOR
    // Time Complexity: O(n), Space Complexity: O(1)
    // Using XOR properties:
    // 1. a ^ a = 0
    // 2. a ^ 0 = a
    // So if we XOR all the elements in the array, the result will be the single number as all the numbers except the single number will cancel out.
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = { 4, 1, 2, 1, 2 };
        System.out.println("Using HashMap: " + singleNumberMap(nums));
        System.out.println("Using Hashing Array: " + singleNumberHashingArray(nums));
        System.out.println("Using XOR: " + singleNumber(nums));
    }
}
