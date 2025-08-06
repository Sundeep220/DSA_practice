package Problems.SlidingWindowAndTwoPointers.Medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FruitsInBasket {
    // Problem: https://leetcode.com/problems/fruit-into-baskets/

    // Brute Force: O(n^2) time | O(1) space
    // Generate all possible subarrays and find the maximum number of fruits in each subarray.
    public static int totalFruit(int[] fruits) {
        int n = fruits.length;
        int maxFruits = 0;

        for (int i = 0; i < n; i++) {
            Set<Integer> basket = new HashSet<>();
            int count = 0;

            for (int j = i; j < n; j++) {
                basket.add(fruits[j]);
                if (basket.size() > 2) break; // can't pick this fruit

                count++; // valid fruit picked
                maxFruits = Math.max(maxFruits, count);
            }
        }

        return maxFruits;
    }
    //Using sliding window + HashMap
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int totalFruit2(int[] fruits) {
        int left = 0, right = 0, maxFruits = 0, count = 0, basket = 0;
        HashMap<Integer, Integer> map = new HashMap<>(); // fruit, count>

        while (right < fruits.length) {
            map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1); // add fruit to map

            if (map.size() > 2) { // can't pick this fruit
                while(map.size() > 2) {
                    map.put(fruits[left], map.get(fruits[left]) - 1);
                    if (map.get(fruits[left]) == 0) {
                        map.remove(fruits[left]);
                    }
                    left++;
                }
            }
            count = right - left + 1;
            maxFruits = Math.max(maxFruits, count);
            right++;
        }

        return maxFruits;
    }



    // Optimized Sliding Window: O(n) time | O(1) space
    // Using Two Pointers
    public static int totalFruit3(int[] fruits) {
        int left = 0, right = 0, maxFruits = 0, count = 0;
        HashMap<Integer, Integer> map = new HashMap<>(); // fruit, count>

        while(right < fruits.length) {
            map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1); // add fruit to map
            if(map.size() > 2) {
                map.put(fruits[left], map.get(fruits[left]) - 1);
                if (map.get(fruits[left]) == 0) {
                    map.remove(fruits[left]);
                }
                left++;
            }
            count = right - left + 1;
            maxFruits = Math.max(maxFruits, count);
            right++;
        }

        return maxFruits;
    }

    // Another way to Hash freq counts:
    public static int totalFruit4(int[] fruits) {
        int n = fruits.length;

        // assuming fruit types are <= 100000 (LeetCode constraint)
        int maxType = 100000;
        int[] count = new int[maxType + 1];

        int left = 0, maxFruits = 0, distinct = 0;

        for (int right = 0; right < n; right++) {
            // Add current fruit
            if (count[fruits[right]] == 0) distinct++;
            count[fruits[right]]++;

            // Shrink if more than 2 types
            if (distinct > 2) {
                count[fruits[left]]--;
                if (count[fruits[left]] == 0) distinct--;
                left++;
            }

            maxFruits = Math.max(maxFruits, right - left + 1);
        }

        return maxFruits;
    }


    public static void main(String[] args) {
        int[] fruits = {1, 2, 3, 2, 2};
        System.out.println(totalFruit(fruits));
        System.out.println(totalFruit2(fruits));
        System.out.println(totalFruit3(fruits));
        System.out.println(totalFruit4(fruits));
    }
}
