# 🔍 Binary Search Mastery Practice List

This curated list helps you master binary search and its variants including classical problems, binary search on answers, and real interview questions. It also includes guidance on recognizing binary search patterns.

---

## 🧠 When to Use Binary Search

Use binary search when:

1. **The array is sorted** or can be made sorted.
2. **You’re asked for optimal/min/max value**, and you can verify a candidate solution using a condition (Binary Search on Answer).
3. You need to **search efficiently** in **monotonic functions** or **range-based search spaces**.
4. **Search condition has a predictable yes/no pattern**, allowing elimination of half of the space.

---

## ✅ **Beginner Level (Classic Binary Search)**

1. [Binary Search](https://leetcode.com/problems/binary-search/) — *Array / Classic BS*
2. [First Bad Version](https://leetcode.com/problems/first-bad-version/) — *Binary Search on condition*
3. [Search Insert Position](https://leetcode.com/problems/search-insert-position/) — *Basic Insertion Index BS*
4. [Guess Number Higher or Lower](https://leetcode.com/problems/guess-number-higher-or-lower/) — *Simple BS simulation*
5. [Find Square Root of x](https://leetcode.com/problems/sqrtx/) — *BS on answer*
6. [Peak Index in a Mountain Array](https://leetcode.com/problems/peak-index-in-a-mountain-array/) — *BS / Unimodal Pattern*

---

## ⚙️ **Medium Level (Variants and Applications)**

1. [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) — *Array / BS with rotation logic*
2. [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/) — *Modified BS*
3. [Find Peak Element](https://leetcode.com/problems/find-peak-element/) — *Binary Search on neighbors*
4. [Find First and Last Position of Element](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) — *Binary Search (Left + Right Bounds)*
5. [Kth Missing Positive Number](https://leetcode.com/problems/kth-missing-positive-number/) — *Binary Search on index-value diff*
6. [Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/) — *Flattened Binary Search*
7. [Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/) — *Binary Search on Answer*
8. [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/) — *Binary Search on Answer*
9. [Minimum Number of Days to Make m Bouquets](https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/) — *Binary Search on minimum day*
10. [Aggressive Cows (GFG)](https://www.geeksforgeeks.org/aggressive-cows-dynamic-programming-solution/) — *Binary Search on Answer*

---

## 🔥 **Hard Level (Advanced BS on Answer / Monotonic Functions)**

1. [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/) — *Partition Binary Search*
2. [Split Array Largest Sum](https://leetcode.com/problems/split-array-largest-sum/) — *Binary Search on Answer*
3. [Find K-th Smallest Pair Distance](https://leetcode.com/problems/find-k-th-smallest-pair-distance/) — *Binary Search + Counting Pairs*
4. [Find Kth Smallest Number in Multiplication Table](https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/) — *BS + Row Count Logic*
5. [Find Smallest Divisor Given a Threshold](https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/) — *Binary Search on Divisor*
6. [Maximum Value at a Given Index in a Bounded Array](https://leetcode.com/problems/maximum-value-at-a-given-index-in-a-bounded-array/) — *Binary Search on Height + Math*
7. [Longest Subsequence With Limited Sum](https://leetcode.com/problems/longest-subsequence-with-limited-sum/) — *Prefix Sum + Binary Search*
8. [Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/) — *BS on value / Pigeonhole Principle*
9. [Count of Smaller Numbers After Self](https://leetcode.com/problems/count-of-smaller-numbers-after-self/) — *Merge Sort / Binary Indexed Tree / BS*

---

## 📘 **Patterns & Techniques**

### 🔹 Classic Binary Search

* Sorted array
* Equal/greater/less than comparisons

### 🔹 Lower Bound / Upper Bound

* First occurrence / Last occurrence of target

### 🔹 Binary Search on Answer

* Answer lies in a numeric range, and validity of mid can be checked
* E.g., Koko Banana, Split Array Sum, Minimum Pages Allocation

### 🔹 Search in Rotated Array

* Modified mid/left/right logic
* Useful for rotated or partially sorted arrays

### 🔹 Monotonic Predicate Function

* Function changes predictably from true → false or vice versa

---

## 📝 **Tips to Master Binary Search**

* Think in terms of **search space**, not just arrays.
* Always maintain invariant: **\[low, high]** or **\[low, high)** consistently.
* Debug with print statements to validate mid and updates to bounds.
* Use **while (low < high)** for lower bound problems.
* Use **while (low <= high)** when you might need to return mid directly.
* Practice both **template 1** (inclusive) and **template 2** (exclusive).

---