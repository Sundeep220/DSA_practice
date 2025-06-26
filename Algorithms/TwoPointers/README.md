## 🧠 Two Pointer Algorithm

### 🔍 **What is it?**

The **Two Pointer** technique uses two indices (pointers) that iterate over a data structure, usually a list or array, from either:

* **Start and end** (opposite directions), or
* **Start and start+1** (same direction)

Depending on the problem, pointers either move toward each other or in the same direction to reduce the search space **efficiently**, avoiding nested loops.

---

### 🧰 **Common Use Cases**

| Scenario                              | Description                                                                                         |
| ------------------------------------- | --------------------------------------------------------------------------------------------------- |
| **Sorted arrays**                     | Often used in problems involving **sorted arrays**, especially for target sum, pairs, or triplets.  |
| **Finding pairs or subarrays**        | Great for problems like: <br> `Two Sum`, `Container With Most Water`, `3Sum`, `Trapping Rain Water` |
| **Sliding window with optimizations** | Can work together with the **sliding window** technique for variable-sized windows.                 |
| **Merging arrays**                    | Also useful in merge-like operations, e.g., **merge step** in merge sort.                           |

---

### ✅ **Preconditions to Use Two Pointers**

To apply the Two Pointer method, **you should check**:

1. **Sorted Input (often needed)**
   Many problems require the array to be **sorted**, or you must sort it first.

2. **Problem involving subarrays, pairs, or range conditions**

3. **You can traverse from both ends or have a clear movement strategy**

    * One pointer starts from the beginning
    * Another from the end or next index

4. **Monotonic behavior**
   The movement of pointers should follow some rule or monotonic condition. For example, when the sum is too high, move the right pointer left.

---

### 🧮 **Basic Examples**

---

#### ✅ **1. Pair with Target Sum**

Given a **sorted** array and a target, find if a pair sums to target.

```java
public boolean hasPairWithSum(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) return true;
        if (sum < target) left++;
        else right--;
    }
    return false;
}
```

---

#### ✅ **2. Remove Duplicates from Sorted Array**

Remove duplicates **in-place** using two pointers.

```java
public int removeDuplicates(int[] nums) {
    int slow = 0;
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    return slow + 1;
}
```

---

### 🤔 **When Should You Think of Two Pointers?**

Ask yourself:

* Is the array **sorted** or can it be sorted?
* Am I looking for **pairs**, **triplets**, or **subarrays**?
* Can I reduce the time complexity of **nested loops**?
* Is there a **monotonic condition** to guide movement of pointers?
* Can a **greedy forward or backward movement** help?

---

### 🧠 Intuition

Instead of brute-forcing all combinations (O(n²)), you use logical moves with two pointers to reduce to **O(n)** or **O(n log n)** (after sorting). It trades off unnecessary comparisons by utilizing **sorted nature or structure** of data.


