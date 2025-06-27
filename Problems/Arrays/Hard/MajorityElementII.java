package Problems.Arrays.Hard;

import java.util.*;

public class MajorityElementII {
    // Problem link: https://leetcode.com/problems/majority-element-ii/
    // Given an array, return the elements that appear more than ⌊ n/3 ⌋ times.


    // Brute Force: O(n^2) O(1)
    // We select all elements and check if they are majority elements or not
    // By couting their frequency, we can get the majority elements
    public static int[] majorityElement(int[] nums) {
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        for(int i=0; i<n; i++) {
            int count = 1;
            for(int j=i+1; j<n; j++) {
                if(nums[j] == nums[i]) count++;
            }
            if(count > n/3) res.add(nums[i]);
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    //Better Solution: O(n) O(n)
    // We use a map to store the frequency of each element
    // If the frequency of an element is greater than n/3, we add it to the result
    public static List<Integer> majorityElementMap(int[] nums) {
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if(map.get(num) >=  (n/3)+1)
                res.add(num);
        }

        return res;
    }

    // Optimal Solution: O(nlogn + n) O(1)
    // We sort the array and count the frequency of each element
    // If the frequency of an element is greater than n/3, we add it to the result
    // and set the count to 1
    public static List<Integer> majorityElementOptimal(int[] nums) {
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        Arrays.sort(nums);  // O(nlogn)
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1]) {
                count++;
            } else {
                if (count > n / 3) {
                    res.add(nums[i - 1]);
                }
                count = 1;  // reset for new number
            }
        }

        // Check for the last element group
        if (count > n / 3) {
            res.add(nums[n - 1]);
        }
        return res;
    }

    // Most Optimal Solution: Extended Boyer-Moore Voting Algorithm
    //    Approach:
    //    Initialize 4 variables:
    //    cnt1 & cnt2 –  for tracking the counts of elements
    //    el1 & el2 – for storing the majority of elements.
    //    Traverse through the given array.
    //    If cnt1 is 0 and the current element is not el2 then store the current element of the array as el1 along with increasing the cnt1 value by 1.
    //    If cnt2 is 0 and the current element is not el1 then store the current element of the array as el2 along with increasing the cnt2 value by 1.
    //    If the current element and el1 are the same increase the cnt1 by 1.
    //    If the current element and el2 are the same increase the cnt2 by 1.
    //    Other than all the above cases: decrease cnt1 and cnt2 by 1.
    //    The integers present in el1 & el2 should be the result we are expecting. So, using another loop, we will manually check their counts if they are greater than the floor(N/3).
    //    Time Complexity: O(n) Space Complexity: O(1)
    public static List<Integer> majorityElementOptimal2(int[] nums) {
        int n = nums.length;
        int cnt1=0, cnt2=0;
        int el1=Integer.MIN_VALUE, el2=Integer.MIN_VALUE;
        List<Integer> res = new ArrayList<>();
        for (int num : nums) {
            if (cnt1 == 0 && num != el2) {
                el1 = num;
                cnt1 = 1;
            } else if (cnt2 == 0 && num != el1) {
                el2 = num;
                cnt2 = 1;
            } else if (num == el1) cnt1++;
            else if (num == el2) cnt2++;
            else {
                cnt1--;
                cnt2--;
            }
        }
        cnt1 = 0;
        cnt2 = 0;
        int minimumFreq = n /3 + 1;
        for(int i: nums){
            if(i == el1) cnt1++;
            if(i == el2) cnt2++;
        }

        if(cnt1 >= minimumFreq) res.add(el1);
        if(cnt2 >= minimumFreq) res.add(el2);
        return res;
    }
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 3, 3, 2, 2, 2};
        System.out.println("Brute Force Solution: " + Arrays.toString(majorityElement(nums))); // 1
        System.out.println("Using HashMap: " + majorityElementMap(nums));
        System.out.println("Optimal Solution: " + majorityElementOptimal(nums));
        System.out.println("Optimal Solution 2: " + majorityElementOptimal2(nums));
    }
}
