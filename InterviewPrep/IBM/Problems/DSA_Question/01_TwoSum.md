# 01. Two Sum

**Tier:** 1 (Hash tables and arrays)
**Link:** https://leetcode.com/problems/two-sum/

## Problem

Given an integer array `nums` and a `target`, return the indices of the two numbers that add up to `target`. Each input has exactly one solution, and the same element may not be used twice.

## Intuition

A brute-force double loop checks every pair in O(n²). While scanning once, the partner needed for the current number is `target - nums[i]`. If that partner was already seen, it is the answer. Otherwise, store the current number so later elements can find it. A `HashMap` answers "have I seen this value?" in O(1).

## Approach

1. Create a `HashMap` from value to index for numbers already visited.
2. For each index `i`, compute `needed = target - nums[i]`.
3. If `needed` is in the map, return `[map.get(needed), i]`.
4. Otherwise, put `nums[i] -> i` into the map.

## Complexity

- **Time:** O(n). One pass, O(1) average per map operation.
- **Space:** O(n). The map can hold every element.

## Edge cases

- Negative numbers.
- Duplicate values, such as `[3, 3]` with target `6`. The map stores the first index, and the second `3` finds it.
- `target = 0`.

## Code

```java
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seenNumbers = new HashMap<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            int neededPartner = target - nums[currentIndex];

            if (seenNumbers.containsKey(neededPartner)) {
                return new int[]{seenNumbers.get(neededPartner), currentIndex};
            }

            seenNumbers.put(nums[currentIndex], currentIndex);
        }

        throw new IllegalArgumentException("No two-sum solution found");
    }
}
```

## Function explanations

Runnable file: `TwoSum.java` (same folder, includes a `main` demo).

### `twoSum`

- **Map instead of nested loops:** a nested loop checks every pair in O(n²). A map turns "does the partner exist?" into an O(1) lookup, so one pass is enough.
- **`neededPartner = target - nums[currentIndex]`:** this is the only value that completes a pair with the current element, so it is the only lookup needed.
- **Lookup before `put`:** the current value is stored only after the check. Otherwise an element could pair with itself, for example `3` with target `6`.
- **Store `value -> index`:** the answer needs indices, so the map stores the index as the value.
- **Return `[earlier, current]`:** the earlier index was stored first, so the array follows scan order.
- **`throw` at the end:** the problem guarantees a solution, so this line is reached only on invalid input. It also satisfies the compiler, since the method must return or throw on every path.

## Interview one-liner

"I store each value with its index as I go, so I can check for the complement in O(1). That makes it one pass, O(n) time and O(n) space."
