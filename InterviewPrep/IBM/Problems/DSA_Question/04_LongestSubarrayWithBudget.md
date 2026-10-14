# 04. Longest Subarray with Sum at Most Budget

**Tier:** 2 (Sliding window and two pointers)
**Confluent tag:** Yes (reported as "longest continuous segment whose cost is at most B")
**Link:** No exact LeetCode match. Practise on paper.

## Problem

Given an array of non-negative integers `costs` and a budget `B`, return the length of the longest contiguous subarray whose sum is at most `B`.

## Intuition

All costs are non-negative, so extending the window to the right never decreases its sum, and removing from the left never increases it. When the window goes over budget, move `left` forward until it is valid again. `left` never moves backwards, so each index enters and leaves the window once.

The non-negative condition matters. With negative values, shrinking the window could increase the sum, and this approach breaks.

## Approach

1. Keep `left`, a running `windowSum`, and `maxLength`.
2. For each `right`, add `costs[right]` to `windowSum`.
3. While `windowSum > budget`, subtract `costs[left]` and increment `left`.
4. Update `maxLength` with `right - left + 1`.

## Complexity

- **Time:** O(n). Each index is added and removed at most once.
- **Space:** O(1).

## Edge cases

- Empty array: returns `0`.
- Every element is larger than the budget: returns `0`.
- `budget = 0`: returns the longest run of zeros.
- Single element: returns `1` if it fits the budget, otherwise `0`.

## Code

```java
public class LongestSubarrayWithBudget {

    public int longestSubarray(int[] costs, int budget) {
        int left = 0;
        long windowSum = 0;
        int maxLength = 0;

        for (int right = 0; right < costs.length; right++) {
            windowSum += costs[right];

            while (windowSum > budget) {
                windowSum -= costs[left];
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}
```

## Function explanations

Runnable file: `LongestSubarrayWithBudget.java` (includes a `main` demo).

### `longestSubarray`

- **Two indices, one pass:** `right` always moves forward and `left` only moves forward. Each index enters and leaves the window at most once, so the scan is O(n).
- **`windowSum` is `long`:** the total can exceed `int` range when many large costs are added. `long` avoids overflow.
- **`while` instead of `if` when shrinking:** adding one element can push the sum over budget by more than one element's cost, so `left` may need to move several times.
- **`maxLength` updated after the window is valid:** at that point `[left, right]` is the longest valid window ending at `right`, because `left` is as small as the budget allows.
- **Non-negative costs are required:** shrinking from the left must never increase the sum. With negative costs, a window could become invalid as it shrinks, and this logic would miss answers.

## Interview one-liner

"Costs are non-negative, so a sliding window works. I grow it on the right and shrink it from the left whenever it goes over budget. Each index is touched at most twice, so it's O(n) time and O(1) space."
