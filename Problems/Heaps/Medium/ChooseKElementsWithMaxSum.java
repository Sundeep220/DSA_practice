package Problems.Heaps.Medium;

import java.util.*;

public class ChooseKElementsWithMaxSum {

    // Time Complexity: O(n² * log n)
    // Space Complexity: O(n)
    public long[] findMaxSumBruteForce(
            int[] nums1,
            int[] nums2,
            int k
    ) {
        int n = nums1.length;

        long[] answer = new long[n];

        for (int i = 0; i < n; i++) {

            List<Integer> values = new ArrayList<>();

            // Find all nums2[j] where nums1[j] < nums1[i]
            for (int j = 0; j < n; j++) {

                if (nums1[j] < nums1[i]) {
                    values.add(nums2[j]);
                }
            }

            // Sort ascending
            Collections.sort(values);

            // Take largest k values
            long sum = 0;

            for (int x = values.size() - 1; x >= 0 && k > values.size() - 1 - x; x--) {

                sum += values.get(x);
            }

            answer[i] = sum;
        }

        return answer;
    }

    // Better
    // Time Complexity: O(n² log k)
    // Space Complexity: O(n + k)
    public long[] findMaxSumBetter(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        long[] answer = new long[n];
        for (int i = 0; i < n; i++) {

            PriorityQueue<Integer> minHeap = new PriorityQueue<>();

            long sum = 0;

            for (int j = 0; j < n; j++) {

                if (nums1[j] < nums1[i]) {

                    minHeap.offer(nums2[j]);
                    sum += nums2[j];

                    /*
                     * Keep only the largest k values.
                     */
                    if (minHeap.size() > k) {
                        sum -= minHeap.poll();
                    }
                }
            }

            answer[i] = sum;
        }

        return answer;
    }

    //Optimal
    // Time Complexity: O(n log n + n log k)
    // Space Complexity: O(n + k)
    public long[] findMaxSum(
            int[] nums1,
            int[] nums2,
            int k
    ) {
        int n = nums1.length;

        /*
         * Store:
         *
         * [nums1[i], nums2[i], originalIndex]
         */
        int[][] pairs = new int[n][3];

        for (int i = 0; i < n; i++) {
            pairs[i][0] = nums1[i];
            pairs[i][1] = nums2[i];
            pairs[i][2] = i;
        }

        /*
         * Sort by nums1.
         */
        Arrays.sort(
                pairs,
                Comparator.comparingInt(a -> a[0])
        );

        long[] answer = new long[n];

        /*
         * Min heap stores the largest k nums2 values
         * among previously processed smaller nums1 values.
         */
        PriorityQueue<Integer> minHeap =
                new PriorityQueue<>();

        long sum = 0;

        int i = 0;

        while (i < n) {

            int j = i;

            /*
             * Find all elements with the same nums1 value.
             */
            while (
                    j < n &&
                            pairs[j][0] == pairs[i][0]
            ) {
                j++;
            }

            /*
             * STEP 1:
             *
             * Calculate answers for the current group.
             *
             * The heap currently contains only values whose
             * nums1 is strictly smaller.
             */
            for (int x = i; x < j; x++) {

                int originalIndex = pairs[x][2];

                answer[originalIndex] = sum;
            }

            /*
             * STEP 2:
             *
             * Add the current group's nums2 values.
             *
             * These values will be available for future
             * larger nums1 values.
             */
            for (int x = i; x < j; x++) {

                int value = pairs[x][1];

                minHeap.offer(value);

                sum += value;

                /*
                 * Keep only the largest k values.
                 */
                if (minHeap.size() > k) {

                    sum -= minHeap.poll();
                }
            }

            i = j;
        }

        return answer;
    }
}
