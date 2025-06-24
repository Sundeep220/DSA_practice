# 🗳️ Moore’s Voting Algorithm - Explanation & Intuition

## 🔍 Problem Statement

Given an integer array `nums`, find the **majority element**, which appears **more than ⌊n/2⌋ times** (i.e., more than half the array length).

> Example:  
> Input:  [2, 2, 1, 1, 1, 2, 2]  
> Output: 2

---

## 🧠 Intuition

Moore's Voting Algorithm is a **linear time** and **constant space** algorithm to find the **majority element**, assuming one exists.

### 💡 Key Insight

If we pair each occurrence of the majority element with a different element, the majority element will still remain because it appears more than `n/2` times.

---

## 🔄 Algorithm Steps

1. **Initialize two variables:**
   - `candidate`: stores the current potential majority element.
   - `count`: keeps track of the count of the candidate.

2. **Iterate through the array:**
   - If `count == 0`, pick the current element as the new `candidate`.
   - If current element is the same as `candidate`, increment `count`.
   - Otherwise, decrement `count`.

3. After the loop, the `candidate` will hold the majority element.

---

## ✅ Java Implementation

```java
public static int majorityElement(int[] nums) {
    int count = 0;
    int candidate = 0;

    for (int num : nums) {
        if (count == 0) {
            candidate = num;
        }
        count += (num == candidate) ? 1 : -1;
    }

    return candidate;
}
```

---

## 📌 Why It Works

- Majority element occurs more than all others combined.
- Pairing them off with non-majority elements reduces the count, but the majority survives.

---

## ✅ Time and Space Complexity

| Metric | Value |
|--------|--------|
| Time   | O(n)   |
| Space  | O(1)   |

Single pass through array and constant memory.

---

## 🧪 When To Use

- You know a majority element is **guaranteed** to exist.
- You need a very efficient, **O(n)** time and **O(1)** space solution.

---

## ❗ Caution

If majority element is **not guaranteed**, a second pass is needed to **verify** the candidate.

```java
public static int majorityElementWithCheck(int[] nums) {
    int count = 0, candidate = 0;

    for (int num : nums) {
        if (count == 0) candidate = num;
        count += (num == candidate) ? 1 : -1;
    }

    // Second pass to verify
    count = 0;
    for (int num : nums) {
        if (num == candidate) count++;
    }

    return count > nums.length / 2 ? candidate : -1; // or throw exception if needed
}
```

---

## 🔚 Summary

Moore’s Voting Algorithm is an elegant and powerful method for finding the majority element in linear time with constant space.

Happy Coding! 🚀
