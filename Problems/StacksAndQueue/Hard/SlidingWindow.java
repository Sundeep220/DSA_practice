package Problems.StacksAndQueue.Hard;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindow {
    // Problem: https://leetcode.com/problems/sliding-window-maximum/

    // Brute Force: Go through all windows and find the maximum element in each window.
    // Time Complexity: O(n * k) Space Complexity: O(n)
    public static int[] maxSlidingWindowBrute(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0) return new int[0];

        int[] result = new int[n - k + 1];

        for (int i = 0; i <= n - k; i++) {
            int maxVal = nums[i];
            for (int j = i; j < i + k; j++) {
                maxVal = Math.max(maxVal, nums[j]);
            }
            result[i] = maxVal;
        }

        return result;
    }

    // Optimal Approach: Monotonic Deque
    //Idea:
    //Use a deque (double-ended queue) to store indices of elements.
    //Maintain deque in decreasing order of values (front always has the max of the window).
    //For each index i:
    //Remove smaller elements from the back (because they will never be the max if a larger element comes after them).
    //Add current index to the deque.
    //Remove indices from the front if they are out of the window (i - k).
    //The front of deque is always the max of current window.
    //Append max to result when the first window of size k is reached.

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0) return new int[0];

        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // store indices

        for (int i = 0; i < n; i++) {
            // 1. Remove indices out of current window
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // 2. Remove smaller elements from back (not useful)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // 3. Add current index
            deque.offerLast(i);

            // 4. Add result when window size is k
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
