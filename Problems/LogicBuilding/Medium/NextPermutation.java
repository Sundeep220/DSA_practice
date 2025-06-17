package Problems.LogicBuilding.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NextPermutation {
    // Naive Solution:
    // Time Complexity: O(n × n! × log(n!)) => log(n!) ≈ n log n, so: O(n × n! × n log n) = O(n² × n! × log n)
    // Space Complexity: O(n × n!)
    // Main function to compute next lexicographical permutation of the given array
    void nextPermutation(int[] arr) {
        // Step 1: Generate all permutations using backtracking
        List<int[]> perms = new ArrayList<>();
        permutations(perms, 0, arr);

        // Step 2: Sort the list of permutations in lexicographical order
        perms.sort(Arrays::compare);

        // Step 3: Find the current permutation's index in the sorted list
        for (int i = 0; i < perms.size(); i++) {
            if (Arrays.equals(perms.get(i), arr)) {
                // Step 4: If current is not the last permutation, return next
                if (i < perms.size() - 1) {
                    int[] nextPerm = perms.get(i + 1);
                    System.arraycopy(nextPerm, 0, arr, 0, arr.length);
                }
                // Step 5: If current is the last permutation, wrap around to the first
                else {
                    int[] nextPerm = perms.get(0);
                    System.arraycopy(nextPerm, 0, arr, 0, arr.length);
                }
                break;
            }
        }
    }

    // Recursive function to generate all permutations using backtracking
    public void permutations(List<int[]> perms, int start, int[] currentPerm) {
        // Base case: add a copy of the current permutation to the list
        if (start == currentPerm.length) {
            perms.add(currentPerm.clone()); // must clone to avoid overwriting
            return;
        }

        // Try swapping each element from 'start' to end and recurse
        for (int i = start; i < currentPerm.length; i++) {
            swap(i, start, currentPerm); // choose
            permutations(perms, start + 1, currentPerm); // explore
            swap(i, start, currentPerm); // un-choose (backtrack)
        }
    }
    // Utility function to swap two elements in the array
    public void swap(int i, int j, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Optimal Solution: Using Greedy Algorithm
    // Time Complexity: O(n)
    // Space Complexity: O(1)
//          💡 Intuition
        //        When you look at a number like 1243, you might wonder:
        //                ➡️ "What's the next bigger number I can make by rearranging its digits?"
        //        That’s exactly what we’re trying to find out! 🔍
        //
        //        We want to find the next greater number that can be formed by changing the order of the digits.
        //        If there's no such number (like 4321), we return -1. 🙅‍♂️
        //
        //                🛠️ Approach
        //        Let’s break it down step by step! 🪜
//
//                🧠 Step 1: Convert number to array of digits
                    //    📦 Turn the number into an array to easily access and swap digits.
                    //
                    //                🔍 Step 2: Find the first digit (from right) that is smaller than its next digit
                    //        This is the point where the digits start decreasing — we call this the pivot point.
                    //
                    //    🔻 Example: In 1243, 2 < 4 → so 2 is our pivot.
                    //
                    //    🔁 Step 3: Find the smallest digit on the right of the pivot that is greater than the pivot
                    //    📌 This is the number we’ll swap with the pivot.
//
            //    🔄 Step 4: Swap them
            //        Just like rearranging puzzle pieces 🧩
            //
            //                ↩️ Step 5: Reverse the part after the pivot
            //        So we get the smallest possible next greater number.
            //
            //    🚫 Step 6: Check for overflow
//                  If the result is too big for an integer ➡️ return -1.

    public void nextPermutationGreedy(int[] arr) {
        int n = arr.length;
        int i = n - 2;

        // Step 1: Find first decreasing element from the end
        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // Step 2: Find next greater element to the right
            int j = n - 1;
            while (arr[j] <= arr[i]) {
                j--;
            }

            // Step 3: Swap
            swap(arr, i, j);
        }

        // Step 4: Reverse the suffix starting from i+1
        reverse(arr, i + 1, n - 1);
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void reverse(int[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start++, end--);
        }
    }


}
