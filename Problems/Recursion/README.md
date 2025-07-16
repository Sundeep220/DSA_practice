# Types of Recursion in Programming

Understanding recursion types helps write efficient code and identify when optimization (e.g., memoization or dynamic programming) is needed.

---

## 1. Tail Recursion

* **Definition**: The recursive call is the **last operation** in the function.
* **Example**:

  ```java
  void tailRec(int n) {
      if (n == 0) return;
      System.out.println(n);
      tailRec(n - 1);
  }
  ```
* ✅ Can be optimized into a loop (in languages that support Tail Call Optimization).

---

## 2. Head Recursion

* **Definition**: The recursive call happens **before** any computation.
* **Example**:

  ```java
  void headRec(int n) {
      if (n == 0) return;
      headRec(n - 1);
      System.out.println(n);
  }
  ```
* ❌ Cannot be easily converted to iteration.

---

## 3. Tree Recursion

* **Definition**: A function makes **multiple recursive calls** in each invocation.
* **Example**:

  ```java
  int fib(int n) {
      if (n <= 1) return n;
      return fib(n - 1) + fib(n - 2);
  }
  ```
* ❗ Exponential time complexity.
* ✅ Use memoization to optimize.

---

## 4. Indirect Recursion

* **Definition**: Function A calls B, and B calls A.
* **Example**:

  ```java
  void A(int n) {
      if (n <= 0) return;
      System.out.print(n + " ");
      B(n - 1);
  }

  void B(int n) {
      if (n <= 0) return;
      System.out.print(n + " ");
      A(n / 2);
  }
  ```

---

## 5. Nested Recursion

* **Definition**: Recursive call is passed as an argument to itself.
* **Example**:

  ```java
  int nested(int n) {
      if (n > 100) return n - 10;
      return nested(nested(n + 11));
  }
  ```
* ❗ Rare and tricky to debug.

---

## 6. Linear Recursion

* **Definition**: Only one recursive call per invocation. No branching.
* **Example**:

  ```java
  int fact(int n) {
      if (n == 1) return 1;
      return n * fact(n - 1);
  }
  ```

---

## 7. Backtracking Recursion

* **Definition**: Try → Recurse → Undo (DFS-style recursion).
* **Example**:

  ```java
  void solve(int idx, List<Integer> current) {
      if (idx == nums.length) {
          result.add(new ArrayList<>(current));
          return;
      }

      // include
      current.add(nums[idx]);
      solve(idx + 1, current);

      // exclude (backtrack)
      current.remove(current.size() - 1);
      solve(idx + 1, current);
  }
  ```

---

## Summary Table

| Type         | Key Feature                       | Example Use Case         |
| ------------ | --------------------------------- | ------------------------ |
| Tail         | Last call in function             | Tail-optimized factorial |
| Head         | Call happens before computation   | Reverse printing         |
| Tree         | Multiple recursive calls per call | Fibonacci, subsets       |
| Indirect     | Mutual recursion                  | Alternating recursion    |
| Nested       | Recursive call inside another     | McCarthy 91              |
| Linear       | Single recursive call             | Factorial, sum           |
| Backtracking | Explore all options, undo changes | Subsets, N-Queens        |

---

# Example: Count Good Numbers (Tree Recursion)

## Problem

Count how many good digit strings of length `n` exist.

* Even indices (0-based): digits must be even → 5 options
* Odd indices: digits must be prime → 4 options

## Brute-Force Tree Recursive Code:

```java
public static int helperBrute(int idx, long n) {
    if (idx == n) return 1;
    int options = (idx % 2 == 0) ? 5 : 4;
    int count = 0;
    for (int i = 0; i < options; i++) {
        count = (count + helperBrute(idx + 1, n)) % MOD;
    }
    return count;
}
```

## Recursion Tree for n = 3 (Tree Recursion)

```
helperBrute(0)
├── helperBrute(1)
│   ├── helperBrute(2)
│   │   ├── helperBrute(3) → return 1
│   │   ├── helperBrute(3) → return 1
│   │   ├── helperBrute(3) → return 1
│   │   ├── helperBrute(3) → return 1
│   │   └── helperBrute(3) → return 1
│   ├── helperBrute(2) → again
│   ├── helperBrute(2) → again
│   └── helperBrute(2) → again
├── helperBrute(1) → again (×5 total)
└── Total return = 5 × 4 × 5 = 100
```

---

# Your Optimized Recursive Version (Linear Recursion)

## Code

```java
public static int countGoodNumbersRecursive(long n) {
    return helperRec(0, n);
}

public static int helperRec(int idx, long n) {
    if(idx == n) return 1;
    int options = (idx % 2 == 0) ? 5 : 4;
    int count = (options * helperRec(idx + 1, n)) % MOD;
    return count;
}
```

## Recursion Tree for n = 3 (Linear Recursion)

```
helperRec(0)
└── helperRec(1)
    └── helperRec(2)
        └── helperRec(3) → return 1
```

* At `idx = 2` (even): 5 × return value of helperRec(3)
* At `idx = 1` (odd): 4 × result of helperRec(2)
* At `idx = 0` (even): 5 × result of helperRec(1)

## Total return = 5 × 4 × 5 = 100

### Why It's Linear Recursion

* Each call makes only **1 recursive call**, then multiplies by options.
* No repeated calls to the same index.
* No branching, so no need fo
