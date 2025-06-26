## 🧠 Sliding Window Algorithm

### 🔍 **What is it?**

The **Sliding Window** algorithm is a technique to reduce the time complexity of problems involving **subarrays, substrings, or sequences** by using a **window** that slides over the data structure (usually arrays or strings).

Instead of using nested loops, we maintain a **window of elements** and slide it forward to find an optimal result (sum, length, count, etc.).

---

### 🔄 **Types of Sliding Windows**

| Type                     | Description                                           |
| ------------------------ | ----------------------------------------------------- |
| **Fixed-size window**    | Window size is constant throughout.                   |
| **Variable-size window** | Window size changes dynamically based on a condition. |

---

### ✅ **Preconditions to Use Sliding Window**

You can apply this algorithm when:

1. ✅ The problem involves **contiguous sequences** (subarrays or substrings).
2. ✅ You need to find **maximum**, **minimum**, **sum**, or **length** of a window satisfying certain condition.
3. ✅ You want to **optimize time complexity** from O(n²) to O(n) or O(n log n).
4. ✅ You can track the result using **window start and end** indices efficiently.

---

### 🧰 **Common Use Cases**

| Problem Type                                  | Example                                                    |
| --------------------------------------------- | ---------------------------------------------------------- |
| **Fixed sum/length**                          | Max sum of k-length subarray                               |
| **Longest substring with unique characters**  | Leetcode: "Longest Substring Without Repeating Characters" |
| **Min/Max length subarray with sum ≥ target** | Leetcode: "Minimum Size Subarray Sum"                      |
| **Counting subarrays/strings**                | Count anagrams, permutations in substrings, etc.           |

---

### ✍️ **Examples**

---

#### ✅ **1. Fixed-size Sliding Window**

> Find the maximum sum of a subarray of size `k`.

```java
public int maxSumFixedWindow(int[] arr, int k) {
    int maxSum = 0, windowSum = 0;

    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    maxSum = windowSum;

    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i] - arr[i - k];
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

---

#### ✅ **2. Variable-size Sliding Window**

> Find the length of the longest substring without repeating characters.

```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> seen = new HashSet<>();
    int left = 0, maxLen = 0;

    for (int right = 0; right < s.length(); right++) {
        while (seen.contains(s.charAt(right))) {
            seen.remove(s.charAt(left++));
        }
        seen.add(s.charAt(right));
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

---

### 🤔 **When Should You Think of Sliding Window?**

Ask yourself:

* Am I working with **contiguous elements** (subarray/substring)?
* Do I need to **optimize time complexity** from brute-force?
* Can I **incrementally update** a result by removing the old element and adding a new one?
* Do I need to maintain a **range** or **count** while iterating?

---

### 🧠 Intuition

The idea is to maintain a **window** that satisfies the problem condition and slide it forward by **adding an element** at the end and **removing one** from the start, adjusting the answer dynamically.

