# Kadane's Algorithm - Maximum Subarray Sum (Contiguous)

## 🧠 Problem Statement

Given an integer array `nums`, find the **contiguous subarray** (containing at least one number) which has the **largest sum** and return its sum.

---

## ✅ Intuition

Kadane’s Algorithm is a dynamic programming approach to solve this problem in **O(n)** time.

The key idea is:
- **At each index**, decide whether to **extend the previous subarray** or **start a new subarray**.
- Track the maximum sum seen so far.

---

## 🔁 Algorithm Steps

1. Initialize two variables:
   - `maxSum` = `nums[0]` (stores the overall max sum)
   - `currentSum` = `nums[0]` (stores the current subarray sum)

2. Loop from index 1 to end of array:
   - `currentSum = max(nums[i], currentSum + nums[i])`
     - Decide whether to start new subarray or extend the previous one
   - `maxSum = max(maxSum, currentSum)`

3. Return `maxSum`

---

## 💻 Java Code

```java
public class KadaneAlgorithm {
    public static int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Maximum Subarray Sum: " + maxSubArray(nums)); // Output: 6
    }
}
```

---

## 📈 Dry Run

Given: `nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]`

| Index | nums[i] | currentSum                      | maxSum |
|-------|---------|----------------------------------|--------|
| 0     | -2      | -2                               | -2     |
| 1     | 1       | max(1, -2+1) = 1                 | 1      |
| 2     | -3      | max(-3, 1-3) = -2                | 1      |
| 3     | 4       | max(4, -2+4) = 4                 | 4      |
| 4     | -1      | max(-1, 4-1) = 3                 | 4      |
| 5     | 2       | max(2, 3+2) = 5                  | 5      |
| 6     | 1       | max(1, 5+1) = 6                  | 6      |
| 7     | -5      | max(-5, 6-5) = 1                 | 6      |
| 8     | 4       | max(4, 1+4) = 5                  | 6      |

✅ Final Answer: `6` (from subarray `[4, -1, 2, 1]`)

---

## 🕒 Time and Space Complexity

- **Time Complexity:** O(n) – single pass
- **Space Complexity:** O(1) – only variables used

---

## 🧠 Bonus Tip

Kadane’s Algorithm can also be extended to return:
- The subarray itself (store start & end indices)
- Maximum subarray product (with sign tracking)

Let me know if you want that too!

---
