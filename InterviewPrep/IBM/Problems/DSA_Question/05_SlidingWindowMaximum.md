# 05. Sliding Window Maximum

**Tier:** 2 (Sliding window and two pointers)
**Link:** https://leetcode.com/problems/sliding-window-maximum/

## Problem

Given an integer array `nums` and a window size `k`, return the maximum value of each window of size `k` as it moves from left to right.

## Intuition

Scanning each window costs O(k), so O(n·k) overall. Instead, keep a deque of indices whose values are strictly decreasing from front to back. The front is always the index of the current window's maximum.

When a new element arrives, pop smaller values from the back. They can never be the maximum while the new element is in the window, because the new element is larger and stays longer. When the front index falls outside the window, pop it from the front.

## Approach

1. For each `right` index, pop indices from the front that are less than `right - k + 1`.
2. Pop indices from the back whose values are less than `nums[right]`.
3. Push `right` to the back.
4. Once `right >= k - 1`, record `nums[deque.peekFirst()]` as the maximum for the window starting at `right - k + 1`.

## Complexity

- **Time:** O(n). Each index is pushed and popped at most once.
- **Space:** O(k). The deque holds at most k indices.

## Edge cases

- `k = 1`: the output is the array itself.
- `k = n`: one window, so one output value.
- All elements equal: the deque keeps only the newest index.
- Duplicates: the strict `<` comparison keeps earlier equal indices, which is fine because the front index still tracks a maximum.

## Code

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }

        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int right = 0; right < nums.length; right++) {
            while (!deque.isEmpty() && deque.peekFirst() < right - k + 1) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[right]) {
                deque.pollLast();
            }

            deque.offerLast(right);

            if (right >= k - 1) {
                result[right - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
```

## Interview one-liner

"I keep a deque of indices with decreasing values. The front is the window maximum. Each index enters and leaves once, so it's O(n) time, and the deque never holds more than k indices."
