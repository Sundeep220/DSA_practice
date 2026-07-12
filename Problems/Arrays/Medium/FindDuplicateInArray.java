package Problems.Arrays.Medium;

import java.util.*;

/*
==========================================================
287. Find the Duplicate Number
https://leetcode.com/problems/find-the-duplicate-number/

Constraints:
1. nums contains n + 1 integers.
2. Every integer is in the range [1, n].
3. Exactly one duplicate exists.
4. Cannot modify the input array.
5. Use only constant extra space.

Interview Progression:
1. Brute Force
2. Sorting
3. HashSet
4. Cyclic Sort
5. Floyd's Cycle Detection (Optimal)
==========================================================
*/

public class FindDuplicateInArray {

    /*
    ==========================================================
    Approach 1 : Brute Force

    Intuition:
    Count the frequency of every element.

    Time  : O(n²)
    Space : O(1)
    ==========================================================
    */
    public int bruteForce(int[] nums) {

        for (int num : nums) {

            int count = 0;

            for (int i : nums) {
                if (num == i) {
                    count++;
                }
            }

            if (count > 1) {
                return num;
            }
        }

        return -1;
    }

    /*
    ==========================================================
    Approach 2 : Sorting

    Intuition:
    Sort the array.
    Duplicate elements become adjacent.

    NOTE:
    ❌ Modifies the input array.

    Time  : O(n log n)
    Space : O(1) (Ignoring sorting implementation)
    ==========================================================
    */
    public int sorting(int[] nums) {

        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] == nums[i - 1]) {
                return nums[i];
            }
        }

        return -1;
    }

    /*
    ==========================================================
    Approach 3 : HashSet

    Intuition:
    Store every visited number.
    First repeated number is the duplicate.

    NOTE:
    ❌ Uses extra space.

    Time  : O(n)
    Space : O(n)
    ==========================================================
    */
    public int hashing(int[] nums) {

        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {

            if (set.contains(num)) {
                return num;
            }

            set.add(num);
        }

        return -1;
    }

    /*
    ==========================================================
    Approach 4 : Cyclic Sort

    Intuition:
    Every value belongs at index (value - 1).

    While placing numbers at their correct positions,
    if the correct position already contains the same value,
    we've found the duplicate.

    NOTE:
    ❌ Modifies the array, therefore NOT valid for this problem.

    Time  : O(n)
    Space : O(1)
    ==========================================================
    */
    public int cyclicSort(int[] nums) {

        int i = 0;

        while (i < nums.length) {

            int correct = nums[i] - 1;

            if (nums[i] != nums[correct]) {

                swap(nums, i, correct);

            } else {

                if (i != correct) {
                    return nums[i];
                }

                i++;
            }
        }

        return -1;
    }

    /*
    ==========================================================
    Approach 5 : Floyd's Cycle Detection (Optimal)

    Intuition:

    Think of the array as a linked list.

    index -> nums[index]

    Since every value lies in [1, n], every value points
    to another valid index.

    The duplicate creates a cycle.

    Phase 1:
    Find the meeting point of slow and fast pointers.

    Phase 2:
    Reset slow to the beginning.
    Move both pointers one step.
    The meeting point is the duplicate number.

    Time  : O(n)
    Space : O(1)

    ✅ Does NOT modify the array.
    ✅ Satisfies all constraints.
    ==========================================================
    */
    public int floydCycleDetection(int[] nums) {

        int slow = nums[0];
        int fast = nums[0];

        // Phase 1 : Detect Cycle
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Phase 2 : Find Cycle Entrance
        slow = nums[0];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    private void swap(int[] nums, int i, int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
