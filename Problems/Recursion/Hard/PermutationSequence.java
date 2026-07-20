package Problems.Recursion.Hard;

import java.util.ArrayList;
import java.util.List;

public class PermutationSequence {
    // Problem: https://leetcode.com/problems/permutation-sequence/\

    // Brute Force: O(n!) time | O(n) space
        private int count = 0;
        private String answer = "";

        public String getPermutation(int n, int k) {
            boolean[] used = new boolean[n + 1];
            generate(n, k, new StringBuilder(), used);
            return answer;
        }
        private void generate(int n, int k, StringBuilder current, boolean[] used) {
            // Stop once kth permutation is found
            if (count == k) {
                return;
            }
            // Complete permutation generated
            if (current.length() == n) {
                count++;
                if (count == k) {
                    answer = current.toString();
                }
                return;
            }
            // Try numbers in increasing order
            for (int num = 1; num <= n; num++) {
                if (used[num]) {
                    continue;
                }
                // Pick
                used[num] = true;
                current.append(num);
                generate(n, k, current, used);
                // Backtrack
                current.deleteCharAt(current.length() - 1);
                used[num] = false;
            }
        }

    // Optimal Solution: O(n) time | O(n) space
    /*
     * Intuition:
     * All permutations are divided into blocks of (n - 1)! permutations
     * based on the first number. Find which block contains the kth permutation,
     * choose that number, remove it, and repeat the same process for the
     * remaining numbers.
     *
     * k is converted to 0-based indexing so:
     * index = k / factorial  -> which number to choose
     * k = k % factorial      -> position inside that block
     *
     * Time: O(N^2)
     * Space: O(N)
     */

    public String getPermutationOptimal(int n, int k) {

        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        // Calculate (n - 1)!
        int factorial = 1;
        for (int i = 1; i < n; i++) {
            factorial *= i;
        }

        // Convert k from 1-based to 0-based
        k--;

        StringBuilder result = new StringBuilder();

        for (int remaining = n; remaining >= 1; remaining--) {

            // Find which block contains the kth permutation
            int index = k / factorial;

            // Choose the number from that block
            result.append(numbers.get(index));

            // Remove the chosen number
            numbers.remove(index);

            // Find position inside the selected block
            k = k % factorial;

            // Move from (remaining - 1)! to (remaining - 2)!
            if (remaining > 1) {
                factorial /= (remaining - 1);
            }
        }

        return result.toString();
    }
}
