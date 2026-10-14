# 06. Combination Sum

**Tier:** 3 (Backtracking)
**Link:** https://leetcode.com/problems/combination-sum/

## Problem

Given an array of distinct positive integers `candidates` and a `target`, return all unique combinations whose values sum to `target`. Each candidate may be used any number of times.

## Intuition

This is a decision tree. At each step, pick a candidate and recurse, or move on to the next one. Two ideas keep the search clean:

- **Start index:** pass `startIndex` so the search only considers candidates at or after the current one. This prevents `[2, 3]` and `[3, 2]` from both appearing.
- **Reuse:** recurse with `i`, not `i + 1`, so the same candidate can be picked again.

Sorting the candidates allows pruning. Once a candidate exceeds the remaining target, every later candidate does too, so the loop can stop.

## Approach

1. Sort `candidates`.
2. Backtrack from index 0 with the full target and an empty path.
3. If `remaining == 0`, save a copy of the path.
4. Loop from `startIndex`. Break if `candidate > remaining`.
5. Add the candidate to the path, recurse with `remaining - candidate` and start index `i`, then remove the candidate.

## Complexity

- **Time:** O(n^(T/m)) in the worst case, where T is the target and m is the smallest candidate. The recursion depth is at most T/m.
- **Space:** O(T/m) for the recursion stack and current path, excluding the output.

## Edge cases

- No valid combination: returns an empty list.
- A candidate equals the target: returns that single combination.
- Large target with a small candidate: deep recursion, and many combinations.

## Code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> allCombinations = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, 0, new ArrayList<>(), allCombinations);
        return allCombinations;
    }

    private void backtrack(int[] candidates,
                           int remainingTarget,
                           int startIndex,
                           List<Integer> currentPath,
                           List<List<Integer>> allCombinations) {
        if (remainingTarget == 0) {
            allCombinations.add(new ArrayList<>(currentPath));
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            int candidate = candidates[i];

            if (candidate > remainingTarget) {
                break;
            }

            currentPath.add(candidate);
            backtrack(candidates, remainingTarget - candidate, i, currentPath, allCombinations);
            currentPath.remove(currentPath.size() - 1);
        }
    }
}
```

## Function explanations

Runnable file: `CombinationSum.java` (includes a `main` demo).

### `combinationSum`

- **`Arrays.sort`:** sorting lets the search stop early. Once one candidate is too large, every later candidate is too large as well.
- **Starts `backtrack` with index 0 and an empty path:** the search begins with nothing chosen and the full target.

### `backtrack`

- **Base case `remainingTarget == 0`:** the current path sums exactly to the target, so it is a valid answer.
- **`new ArrayList<>(currentPath)`:** the path is modified during backtracking, so a copy is saved. Adding the path itself would leave every stored answer pointing at the same list, which ends up empty.
- **Loop starts at `startIndex`:** only candidates at or after the current one are tried. This stops `[2, 3]` and `[3, 2]` from both appearing.
- **`break` when `candidate > remainingTarget`:** the array is sorted, so no later candidate can fit either.
- **Recurse with `i`, not `i + 1`:** keeps the same candidate available, which allows repeated use.
- **`remove` after recursion:** undoes the choice so the next iteration starts from the same state. This is the "backtrack" step.
- **No check for a negative remainder:** recursion happens only when `candidate <= remainingTarget`, so the remainder never drops below zero.

## Interview one-liner

"I backtrack over the candidates from a start index, reusing the same index so repeats are allowed, and prune once a candidate exceeds the remaining target. Sorting makes the prune safe."
