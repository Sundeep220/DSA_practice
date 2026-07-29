package Problems.StacksAndQueue.Hard;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

public class MaximumOfMinimumOfEveryWindow {

    public static int[] maxMinWindow(int[] arr, int n) {

        /*
         * Intuition:
         * ----------
         * For every possible window size, generate all windows.
         * For each window, scan all elements to find its minimum.
         * Among all window minimums, keep the maximum.
         *
         * Time Complexity:
         * O(N³)
         *
         * Outer loop      -> N window sizes
         * Middle loop     -> O(N) windows for each size
         * Inner loop      -> O(N) scan to find minimum
         *
         * Overall:
         * O(N³)
         *
         * Space Complexity:
         * O(1)
         */

        int[] answer = new int[n];

        // Try every window size
        for (int windowSize = 1; windowSize <= n; windowSize++) {

            int maxOfMinimum = Integer.MIN_VALUE;

            // Generate every window of current size
            for (int start = 0; start <= n - windowSize; start++) {

                int minimum = Integer.MAX_VALUE;

                // Find minimum inside current window
                for (int i = start; i < start + windowSize; i++) {
                    minimum = Math.min(minimum, arr[i]);
                }

                // Keep the maximum among all window minimums
                maxOfMinimum = Math.max(maxOfMinimum, minimum);
            }

            answer[windowSize - 1] = maxOfMinimum;
        }

        return answer;
    }

    public static int[] maxMinWindowBetter(int[] arr, int n) {

        /*
         * Intuition:
         * ----------
         * For every window size, compute the minimum of every
         * window using a monotonic increasing deque.
         *
         * The front of the deque always stores the minimum
         * element of the current window.
         *
         * Then keep the maximum among all those minimums.
         *
         * Time Complexity:
         * O(N²)
         *
         * N window sizes
         * ×
         * O(N) deque traversal
         *
         * Space Complexity:
         * O(N)
         */

        int[] answer = new int[n];

        // Try every possible window size
        for (int windowSize = 1; windowSize <= n; windowSize++) {

            Deque<Integer> deque = new ArrayDeque<>();

            int maxOfMinimum = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {

                // Remove indices that are outside the window
                while (!deque.isEmpty() &&
                        deque.peekFirst() <= i - windowSize) {

                    deque.pollFirst();
                }

                // Maintain increasing order
                while (!deque.isEmpty() &&
                        arr[deque.peekLast()] >= arr[i]) {

                    deque.pollLast();
                }

                deque.offerLast(i);

                // Window formed
                if (i >= windowSize - 1) {

                    int minimum = arr[deque.peekFirst()];

                    maxOfMinimum = Math.max(maxOfMinimum, minimum);
                }
            }

            answer[windowSize - 1] = maxOfMinimum;
        }

        return answer;
    }

    /*
     * Intuition:
     * ----------
     * Instead of processing every window size separately,
     * process every element once.
     *
     * For every element, find the largest window
     * in which it remains the minimum.
     *
     * Previous Smaller Element (PSE)
     * and Next Smaller Element (NSE)
     * give that maximum window length.
     *
     * The element becomes a candidate answer
     * for exactly that window length.
     *
     * Finally, fill missing window sizes
     * by propagating answers from right to left.
     *
     * Time Complexity:
     * O(N)
     *
     * Space Complexity:
     * O(N)
     */

    public static int[] maxMinWindowOptimal(int[] arr, int n) {

        int[] prev = previousSmaller(arr);
        int[] next = nextSmaller(arr);

        int[] answer = new int[n + 1];

        Arrays.fill(answer, Integer.MIN_VALUE);

        // Compute largest valid window for every element
        for (int i = 0; i < n; i++) {
            int windowLength = next[i] - prev[i] - 1;
            answer[windowLength] = Math.max(answer[windowLength], arr[i]);
        }

        // Fill missing entries
        for (int i = n - 1; i >= 1; i--) {
            answer[i] = Math.max(answer[i], answer[i + 1]);
        }

        int[] result = new int[n];

        for (int i = 1; i <= n; i++) {
            result[i - 1] = answer[i];
        }

        return result;
    }

    private static int[] previousSmaller(int[] arr) {

        int n = arr.length;

        int[] prev = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {

            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {

                stack.pop();
            }

            prev[i] = stack.isEmpty() ? -1 : stack.peek();

            stack.push(i);
        }

        return prev;
    }

    private static int[] nextSmaller(int[] arr) {

        int n = arr.length;

        int[] next = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {

            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            next[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        return next;
    }
}
