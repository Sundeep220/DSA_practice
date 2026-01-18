# Dynamic Programming – Complete Introduction & Approach Guide

---

## 1. What is Dynamic Programming?

**Dynamic Programming (DP)** is an optimization technique used to solve problems by:

> **Breaking a problem into overlapping subproblems and storing their results so they are not recomputed.**

### Core Idea

* Solve **each subproblem once**
* Store its result
* Reuse it whenever needed

---

## 2. When Should You Think of DP?

A problem is a good candidate for DP if it has:

### ✅ 1. Overlapping Subproblems

The same subproblems are solved repeatedly.

Example:

```
fib(5) → fib(4) + fib(3)
fib(4) → fib(3) + fib(2)
fib(3) → fib(2) + fib(1)
```

👉 `fib(3)` and `fib(2)` are computed multiple times.

---

### ✅ 2. Optimal Substructure

The optimal solution of a problem depends on optimal solutions of its subproblems.

Example:

```
fib(n) = fib(n-1) + fib(n-2)
```

---

## 3. Two Main DP Approaches

| Approach        | Direction   | Uses Recursion | Stack Space |
| --------------- | ----------- | -------------- | ----------- |
| **Memoization** | Top → Down  | Yes            | Yes         |
| **Tabulation**  | Bottom → Up | No             | No          |

---

## 4. Fibonacci Example (Foundation DP Problem)

### Problem Definition

* Fibonacci series:
  `0, 1, 1, 2, 3, 5, 8, 13, ...`
* `fib(n)` = `fib(n-1) + fib(n-2)`
* Base cases:

  ```
  fib(0) = 0
  fib(1) = 1
  ```

---

## 5. Why Simple Recursion is Bad ❌

### Recursive Tree for `fib(5)`

```
fib(5)
├── fib(4)
│   ├── fib(3)
│   │   ├── fib(2)
│   │   └── fib(1)
│   └── fib(2)
└── fib(3)
    ├── fib(2)
    └── fib(1)
```

### Issues:

* Same calls repeated
* **Time Complexity: O(2ⁿ)** ❌

---

## 6. Part 1 – Memoization (Top-Down DP)

### Idea

> **Cache the result of each subproblem when it is computed the first time.**

---

### Steps to Apply Memoization

1. Create a `dp[]` array initialized with `-1`
2. Before solving `f(n)`, check if `dp[n]` exists
3. If yes → return it
4. If no → compute, store, return

---

### Java Code (Memoization)

```java
import java.util.*;

class Solution {
    public int fib(int n, int[] dp) {
        // Base case
        if (n <= 1) return n;

        // Already computed
        if (dp[n] != -1) return dp[n];

        // Compute and store
        dp[n] = fib(n - 1, dp) + fib(n - 2, dp);
        return dp[n];
    }
}

public class Main {
    public static void main(String[] args) {
        int n = 10;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        Solution sol = new Solution();
        System.out.println(sol.fib(n, dp));
    }
}
```

---

### Complexity

* **Time:** `O(n)`
* **Space:** `O(n)` (dp array + recursion stack)

---

### When to Use Memoization?

✔ When recursion is natural
✔ When converting recursion → DP easily
✔ When state transitions are complex

---

## 7. Part 2 – Tabulation (Bottom-Up DP)

### Idea

> **Solve smallest subproblems first and build up to the answer.**

---

### Steps to Convert Recursion → Tabulation

1. Create `dp[]` of size `n+1`
2. Initialize base cases
3. Iterate from smallest state → largest
4. Return final state

---

### Java Code (Tabulation)

```java
class Solution {
    public int fib(int n) {
        if (n <= 1) return n;

        int[] dp = new int[n + 1];

        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}

public class Main {
    public static void main(String[] args) {
        int n = 10;
        Solution sol = new Solution();
        System.out.println(sol.fib(n));
    }
}
```

---

### Complexity

* **Time:** `O(n)`
* **Space:** `O(n)`
* **No recursion stack** ✅

---

### When to Use Tabulation?

✔ Stack overflow risk
✔ Need better control over iteration
✔ Easy state transitions

---

## 8. Part 3 – Space Optimization

### Observation

```
dp[i] = dp[i-1] + dp[i-2]
```

👉 Only **last two values** are needed.

---

### Optimized Variables

* `prev` → `dp[i-1]`
* `prev2` → `dp[i-2]`

---

### Java Code (Space Optimized)

```java
class Solution {
    public int fib(int n) {
        if (n <= 1) return n;

        int prev2 = 0;
        int prev = 1;

        for (int i = 2; i <= n; i++) {
            int cur = prev + prev2;
            prev2 = prev;
            prev = cur;
        }

        return prev;
    }
}
```

---

### Complexity

* **Time:** `O(n)`
* **Space:** `O(1)` ✅

---

## 9. DP Mental Model (VERY IMPORTANT)

Whenever you see a DP problem, ask:

### 🔹 Step 1: Define the State

> What does `dp[i]` represent?

Example:

```
dp[i] = ith Fibonacci number
```

---

### 🔹 Step 2: Write the Transition

> How does current state depend on previous states?

```
dp[i] = dp[i-1] + dp[i-2]
```

---

### 🔹 Step 3: Base Cases

> Smallest problems that don’t need recursion

```
dp[0] = 0
dp[1] = 1
```

---

### 🔹 Step 4: Answer Location

> Where is the final result?

```
dp[n]
```

---

## 10. General DP Conversion Pattern

### Recursion → DP Checklist

| Step | Question                         |
| ---- | -------------------------------- |
| 1    | What are the changing variables? |
| 2    | What does dp[state] mean?        |
| 3    | Base cases?                      |
| 4    | Transition formula?              |
| 5    | Memo or Tabulation?              |
| 6    | Can space be optimized?          |

---

## 11. What Comes Next in DP (Roadmap)

You should now move in this order:

1️⃣ **1D DP**

* Fibonacci
* Climbing Stairs
* Frog Jump
* House Robber

2️⃣ **2D DP**

* Grid paths
* Unique paths
* Minimum path sum

3️⃣ **DP on Subsequences**

* LCS
* LIS
* Subset Sum
* Knapsack

4️⃣ **DP on Strings**

* Edit Distance
* Palindrome DP

5️⃣ **DP on Trees**

* Tree DP
* Binary Tree DP

---


