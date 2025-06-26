## 🧠 Prefix Sum Algorithm

### 🔍 **What is it?**

The **Prefix Sum** (or cumulative sum) technique is a way to **precompute** the sums of elements from the start of an array up to each index. Once computed, you can answer **range sum queries** in **constant time**.

---

### 🧾 **Prefix Sum Definition**

For an array `arr`, the prefix sum array `prefix` is defined as:

```
prefix[0] = arr[0]
prefix[i] = prefix[i - 1] + arr[i]
```

So, the sum of elements in the range `[i...j]` is:

```
sum = prefix[j] - prefix[i - 1]    (if i > 0)
sum = prefix[j]                    (if i == 0)
```

---

### ✅ **Preconditions to Use Prefix Sum**

Use prefix sum when:

1. ✅ You have **multiple range sum queries** on the same array.
2. ✅ The array is **static** (i.e., no frequent updates).
3. ✅ You want to improve time complexity from O(n) per query to **O(1)**.
4. ✅ You're dealing with **contiguous range-based queries** (like sum from index `i` to `j`).

---

### 🧰 **Common Use Cases**

| Problem Type               | Example                             |
| -------------------------- | ----------------------------------- |
| Range sum queries          | Sum of elements between two indices |
| Subarray count problems    | Count subarrays with sum = k        |
| 2D grid sums               | Matrix sum in rectangular region    |
| Binary difference problems | Number of 1s in a binary range      |

---

### ✍️ **Examples**

---

#### ✅ **1. Basic Prefix Sum for Range Sum Queries**

```java
public class PrefixSum {
    private int[] prefix;

    public PrefixSum(int[] arr) {
        prefix = new int[arr.length];
        prefix[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            prefix[i] = prefix[i - 1] + arr[i];
        }
    }

    public int rangeSum(int i, int j) {
        if (i == 0) return prefix[j];
        return prefix[j] - prefix[i - 1];
    }
}
```

---

#### ✅ **2. Count Subarrays with Sum K**

> Leetcode: [560. Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);  // Base case
    int count = 0, sum = 0;

    for (int num : nums) {
        sum += num;
        if (map.containsKey(sum - k)) {
            count += map.get(sum - k);
        }
        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;
}
```

---

### 🔁 **2D Prefix Sum**

> Used in matrix sum queries.
> For matrix `m x n`, build prefix sum:

```java
prefix[i][j] = matrix[i][j] 
             + prefix[i-1][j] 
             + prefix[i][j-1] 
             - prefix[i-1][j-1]
```

Then to query sum of submatrix `(r1, c1)` to `(r2, c2)`:

```java
sum = prefix[r2][c2] 
    - prefix[r1-1][c2] 
    - prefix[r2][c1-1] 
    + prefix[r1-1][c1-1];
```

---

### 🤔 **When Should You Think of Prefix Sum?**

Ask yourself:

* Do I have **multiple queries for range sums**?
* Is the array or matrix mostly **static**?
* Do I need to **optimize repeated subarray calculations**?
* Can I afford **O(n)** preprocessing for **O(1)** query speed?

---

### 🧠 Intuition

Prefix sum is all about **precomputing** to save time later. Instead of summing elements again and again for every query, we sum once and reuse partial results.

---
