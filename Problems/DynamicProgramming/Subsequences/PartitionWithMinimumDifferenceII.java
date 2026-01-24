package Problems.DynamicProgramming.Subsequences;


import java.util.*;
public class PartitionWithMinimumDifferenceII {
    // Problem: https://leetcode.com/problems/partition-array-into-two-subsets-with-equal-sum/description/

    /**
     * LeetCode 2035
     *
     * Problem:
     * Given an array nums of size 2*n, split it into two arrays of size n
     * such that the absolute difference of their sums is minimized.
     *
     * Why Meet in the Middle?
     * - We must choose EXACTLY n elements
     * - DP on sum is infeasible due to large sum range
     * - 2n <= 30 → split into two halves of size n (<= 15)
     *  Time: O(n * 2^n * log(2^n))
     * Space: O(2^n)
     */

        public static int minimumDifference(int[] nums) {

            // Each partition must contain n elements
            int n = nums.length / 2;

            // Split nums into two halves
            int[] left = Arrays.copyOfRange(nums, 0, n);
            int[] right = Arrays.copyOfRange(nums, n, 2 * n);

            // Total sum of all elements
            int totalSum = 0;
            for (int num : nums) totalSum += num;

            /*
             * leftSums[k] -> all subset sums by picking k elements from left half
             * rightSums[k] -> all subset sums by picking k elements from right half
             *
             * We will later combine:
             * k elements from left + (n - k) elements from right
             */
            List<List<Integer>> leftSums = new ArrayList<>();
            List<List<Integer>> rightSums = new ArrayList<>();

            for (int i = 0; i <= n; i++) {
                leftSums.add(new ArrayList<>());
                rightSums.add(new ArrayList<>());
            }

            // Generate all subset sums for both halves
            generateSubsets(left, 0, 0, 0, leftSums);
            generateSubsets(right, 0, 0, 0, rightSums);

            // Sort right sums so we can apply binary search
            for (int i = 0; i <= n; i++) {
                Collections.sort(rightSums.get(i));
            }

            int answer = Integer.MAX_VALUE;

            /*
             * Combine results:
             * For k elements chosen from left,
             * choose (n - k) elements from right.
             */
            for (int k = 0; k <= n; k++) {

                List<Integer> leftList = leftSums.get(k);
                List<Integer> rightList = rightSums.get(n - k);

                for (int leftSum : leftList) {

                    /*
                     * We want:
                     * leftSum + rightSum ≈ totalSum / 2
                     *
                     * So we search for:
                     * rightSum ≈ (totalSum / 2 - leftSum)
                     */
                    int target = totalSum / 2 - leftSum;

                    /*
                     * IMPORTANT: Understanding binarySearch result
                     *
                     * If target EXISTS in rightList:
                     *   binarySearch returns its index (>= 0)
                     *
                     * If target DOES NOT exist:
                     *   binarySearch returns:
                     *     -(insertionPoint) - 1
                     *
                     * insertionPoint = index where target should be inserted
                     * to keep the list sorted.
                     */
                    int idx = Collections.binarySearch(rightList, target);

                    /*
                     * If idx < 0, target was NOT found.
                     * Convert it back to insertion point using:
                     *
                     * idx = -idx - 1
                     *
                     * This gives us the position of the smallest element
                     * greater than target.
                     */
                    if (idx < 0) {
                        idx = -idx - 1;
                    }

                    /*
                     * Now we check two candidates:
                     * 1) rightList[idx] → the closest value >= target
                     * 2) rightList[idx - 1] → the closest value < target
                     *
                     * One of these will give the minimum difference.
                     */

                    // Check candidate at idx
                    if (idx < rightList.size()) {
                        int chosenSum = leftSum + rightList.get(idx);
                        answer = Math.min(answer,
                                Math.abs(totalSum - 2 * chosenSum));
                    }

                    // Check candidate just before idx
                    if (idx > 0) {
                        int chosenSum = leftSum + rightList.get(idx - 1);
                        answer = Math.min(answer,
                                Math.abs(totalSum - 2 * chosenSum));
                    }
                }
            }

            return answer;
        }

        /**
         * Generates all subset sums for a given array.
         *
         * @param arr    Current half (left or right)
         * @param index  Current index
         * @param count  Number of elements chosen so far
         * @param sum    Sum of chosen elements
         * @param result result[count] stores sums with 'count' picks
         *
         * Logic:
         * At each index, we have two choices:
         * 1) Do not pick current element
         * 2) Pick the current element
         */
        private static void generateSubsets(
                int[] arr,
                int index,
                int count,
                int sum,
                List<List<Integer>> result) {

            // Base case: processed all elements
            if (index == arr.length) {
                result.get(count).add(sum);
                return;
            }

            // Choice 1: Do not pick the current element
            generateSubsets(arr, index + 1, count, sum, result);

            // Choice 2: Pick the current element
            generateSubsets(arr, index + 1, count + 1, sum + arr[index], result);
        }

        // Driver code
        public static void main(String[] args) {
            int[] nums = {3, 9, 7, 3};
            System.out.println("Minimum Difference = " + minimumDifference(nums));
        }

}
