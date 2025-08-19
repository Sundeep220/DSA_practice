# **Problem: Task Scheduler**

You are given:

* An array of tasks (`char[]`), each task is represented by a capital letter `A-Z`.
* A cooldown interval `n`.
  Rule:
* The same task cannot be executed again until **at least `n` intervals** have passed.
* Each interval can either execute a task or remain **idle**.

You need to return the **minimum number of intervals** to finish all tasks.

---

## **Step 1: Understanding the Problem with Example**

Example:

```
tasks = [A, A, A, B, B, B], n = 2
```

* Without cooldown, minimum intervals = 6 (just run them all).
* But with `n=2`, two identical tasks must be **separated by at least 2 other intervals**.

One possible valid scheduling:

```
A B idle A B idle A B   → 8 intervals
```

But a better scheduling is:

```
A B idle A B idle A B  → 8 intervals (minimum possible)
```

So output = **8**.

---

## **Step 2: Brute Force Approach (Simulation)**

**Intuition:**

* Always try to schedule the most frequent task first, then fill gaps with other tasks or idles.
* Simulate interval by interval, checking which task can be placed (respecting the cooldown).

**Steps:**

1. Count frequency of tasks.
2. Use a max-heap to always pick the task with highest remaining frequency.
3. Use a queue to keep track of cooldown (when a task can be reused).
4. Keep simulating until all tasks are done.

**Complexity:**

* O(N log 26) = O(N log N) (since heap operations dominate).
* Works fine, but not optimal for explaining the formula.

---

## **Step 3: Greedy + Heap Approach**

### Idea:

* Tasks with highest frequency dictate the schedule structure.
* Spread them out as much as possible to minimize idle time.

### Example Walkthrough:

Tasks = `[A, A, A, B, B, B], n = 2`

* Count: `A=3, B=3`.
* The most frequent task appears `maxCount = 3` times.
* Arrange them in blocks:

```
A _ _ A _ _ A
```

Here:

* Gaps between `A`s = `maxCount - 1 = 2` gaps.
* Each gap must be at least `n = 2` long.
* So we fill with other tasks (like B).

Filling:

```
A B _ A B _ A B
```

No idle needed → total intervals = 8.

---

## **Step 4: Formula Derivation**

Let’s derive the exact formula used in the greedy approach.

### Step 4.1: Block Structure

Suppose the maximum frequency is `maxCount`.
We can structure them like:

```
X _ _ ... X _ _ ... X  (maxCount times)
```

Where `X` is the most frequent task, and between them we must leave **at least n intervals**.

* Number of gaps = `maxCount - 1`.
* Each gap has length = `n`.
* So total required empty slots = `(maxCount - 1) * n`.

---

### Step 4.2: Filling Slots

Other tasks can be used to fill these empty slots.

* If we have enough tasks, no idle time is needed.
* Otherwise, we must add idle slots.

---

### Step 4.3: Special Case (Multiple Max Frequency Tasks)

Suppose multiple tasks have the same highest frequency (`maxCount`).
Example:

```
tasks = [A, A, A, B, B, B, C, C], n = 2
```

Here:

* `maxCount = 3`
* `maxCountCount = 2` (A and B both occur 3 times)

Block structure:

```
A B _ A B _ A B
```

Now last block has more than one task at the end.
So formula must **add `maxCountCount`** tasks in the last block.

---

### Step 4.4: Final Formula

The minimum intervals required =

$$
\max(\text{total tasks}, (maxCount - 1) \times (n + 1) + maxCountCount)
$$

* `(maxCount - 1) * (n + 1)` → all full blocks with `n` gaps filled.
* `+ maxCountCount` → fill last block with all equally max frequent tasks.
* `max(total tasks, …)` → in case tasks fill all slots and no idle is needed.

---

## **Step 5: Code Implementation (Greedy Formula)**

```java
public int leastInterval(char[] tasks, int n) {
    int[] count = new int[26];
    for (char c : tasks) {
        count[c - 'A']++;
    }

    int maxCount = 0;
    for (int freq : count) {
        maxCount = Math.max(maxCount, freq);
    }

    int maxCountCount = 0;
    for (int freq : count) {
        if (freq == maxCount) {
            maxCountCount++;
        }
    }

    return Math.max(tasks.length, (maxCount - 1) * (n + 1) + maxCountCount);
}
```

---

## **Step 6: Complexity Analysis**

* Counting frequencies: O(N).
* Finding `maxCount` and `maxCountCount`: O(26) = O(1).
* Formula calculation: O(1).

**Final Complexity = O(N).**

---

✅ So, in summary:

* **Brute Force (Heap Simulation):** Interval-by-interval simulation → O(N log N).
* **Greedy Formula:** Derived from arranging blocks → O(N).

---
