# String Hashing (Rolling Hash) - Complete Interview Notes

## 1. Why Do We Need String Hashing?

Suppose we need to compare two substrings repeatedly.

``` text
String = "abcdefghijk..."

Compare:
s[100...500]
s[700...1100]
```

A character-by-character comparison costs **O(length)**.

If there are thousands of such queries, this becomes expensive.

**Idea:** Convert each string into a single integer called a **hash**.

Then comparison becomes:

``` text
hash(substring1) == hash(substring2)
```

Time: **O(1)** (after preprocessing).

------------------------------------------------------------------------

## 2. Real Life Analogy

An Aadhaar number uniquely represents a person.

Instead of comparing: - Name - Address - Phone - DOB

we compare one number.

String hashing does the same for strings.

------------------------------------------------------------------------

## 3. Naive Hash (Why It Fails)

Map:

``` text
a=1
b=2
c=3
```

Hash:

``` text
abc = 1+2+3 = 6
cab = 3+1+2 = 6
```

Different strings → Same hash ❌

Reason: Position is ignored.

------------------------------------------------------------------------

## 4. Polynomial Hash

Assign every position a weight.

``` text
abc

= 1×31² + 2×31¹ + 3×31⁰
```

General formula:

    Hash(s)= Σ value(s[i]) × P^i

where:

-   P = Base (31, 37, 53...)
-   value(a)=1 ... value(z)=26

Example:

``` text
abc

1×31² + 2×31 + 3

=961+62+3

=1026
```

Now:

``` text
abc != cab
```

------------------------------------------------------------------------

## 5. Why Base 31?

Common choices:

-   31
-   37
-   53
-   131
-   257

Prime bases reduce collisions.

------------------------------------------------------------------------

## 6. Why Modulo?

Without modulo:

    31^100000

is enormous.

So we compute:

``` java
hash = (hash * base + value) % MOD;
```

Common MOD values:

``` text
1_000_000_007
1_000_000_009
998244353
```

------------------------------------------------------------------------

## 7. Hash Collision

Collision means:

    Different strings
    ↓

    Same hash

Example:

    abc -> 928374
    xyz -> 928374

Rare, but possible.

### Reduce Collisions

-   Double Hashing
-   Large prime MOD
-   Different bases

------------------------------------------------------------------------

## 8. Building a Hash (Java)

``` java
String s = "abc";

long hash = 0;
long base = 31;
long mod = 1_000_000_007;

for(char c : s.toCharArray()) {
    hash = (hash * base + (c - 'a' + 1)) % mod;
}

System.out.println(hash);
```

Time: O(n)

------------------------------------------------------------------------

## 9. Rolling Hash

Suppose:

    abcdef

    Window = 3

    abc
    bcd
    cde
    def

### Naive

Compute every window again.

    abc -> O(k)
    bcd -> O(k)
    ...

Total:

    O(nk)

### Rolling Hash

Reuse previous hash.

-   Remove outgoing character
-   Shift
-   Add incoming character

Now each window:

    O(1)

------------------------------------------------------------------------

## 10. Example (Concept)

Current window:

    abc

Next:

    bcd

Instead of recomputing:

    Remove a
    Shift remaining hash
    Add d

Exactly like Sliding Window.

------------------------------------------------------------------------

## 11. Prefix Hashing

Exactly like Prefix Sum.

Instead of

    prefixSum

store

    prefixHash

Then

    Hash(l,r)

    =

    prefix[r]

    -

    prefix[l-1]

(with proper power adjustment)

Substring hash becomes O(1).

------------------------------------------------------------------------

## 12. Character Frequency Hashing vs String Hashing

### Character Frequency Hashing

``` java
int[] freq = new int[26];
```

Used for:

-   Anagrams
-   Sliding Window
-   Character counting

------------------------------------------------------------------------

### String Hashing

``` text
leetcode

↓

837492834
```

Used for:

-   Rabin-Karp
-   Duplicate substrings
-   Prefix Hash
-   Rolling Hash

------------------------------------------------------------------------

## 13. Complexity

### Building Hash

Time: O(n)

Space: O(1)

### Prefix Hash

Preprocessing: O(n)

Substring Query: O(1)

### Rolling Hash

Each slide: O(1)

Total: O(n)

------------------------------------------------------------------------

## 14. Interview Applications

-   Rabin-Karp
-   Duplicate Substring
-   Longest Duplicate Substring
-   Longest Common Substring
-   DNA Matching
-   Palindrome Queries
-   Plagiarism Detection

------------------------------------------------------------------------

## 15. LeetCode Roadmap

### Beginner

-   28. Find the Index of the First Occurrence in a String (Rabin-Karp)
-   686. Repeated String Match

### Intermediate

-   187. Repeated DNA Sequences
-   1044. Longest Duplicate Substring

### Advanced

-   1062. Longest Repeating Substring
-   214. Shortest Palindrome
-   1316. Distinct Echo Substrings

------------------------------------------------------------------------

## 16. Interview Memory Sheet

Remember:

-   Position matters → Polynomial Hash.
-   Use a prime base (31/37).
-   Use modulo to avoid overflow.
-   Different strings can collide.
-   Rolling Hash updates in O(1).
-   Prefix Hash answers substring queries in O(1).

------------------------------------------------------------------------

## 17. Cheat Sheet

  Concept        Key Idea
  -------------- ------------------------------
  Hash           Convert string to integer
  Base           Gives positional weight
  Mod            Prevent overflow
  Collision      Different strings, same hash
  Rolling Hash   Update window in O(1)
  Prefix Hash    Compare substrings in O(1)
  Double Hash    Reduce collision probability

------------------------------------------------------------------------

## 18. What's Next?

Study order:

1.  Rabin-Karp Algorithm
2.  Rolling Hash Implementation
3.  Prefix Hash
4.  Double Hashing
5.  Binary Search + Hashing
6.  Longest Duplicate Substring


# String Hashing & Rolling Hash - LeetCode Roadmap

This roadmap is designed to build your understanding **from scratch**, progressing from basic string matching to advanced rolling hash and prefix hash techniques used in interviews.

---

# Phase 0 – Warm-up (No Hashing Yet)

**Goal:** Understand the problem of substring matching and why hashing is needed.

| LeetCode | Problem | Difficulty | Learn |
|----------|---------|------------|-------|
| 28 | Find the Index of the First Occurrence in a String | Easy | Naive substring search |
| 686 | Repeated String Match | Medium | Motivation for efficient string matching |

---

# Phase 1 – Learn Rolling Hash (Rabin-Karp)

**Goal:** Learn how to convert strings into hashes and update hashes in O(1).

| LeetCode | Problem | Difficulty | Learn |
|----------|---------|------------|-------|
| 28 | Find the Index of the First Occurrence in a String | Easy | Polynomial Hash + Rolling Hash + Rabin-Karp |
| 686 | Repeated String Match | Medium | Apply Rabin-Karp on repeated strings |

Concepts Covered:

- Polynomial Hash
- Rolling Hash
- Window Hash
- Collision Checking
- Rabin-Karp Algorithm

---

# Phase 2 – Fixed Window Rolling Hash

**Goal:** Detect repeated fixed-length substrings efficiently.

| LeetCode | Problem | Difficulty | Learn |
|----------|---------|------------|-------|
| 187 | Repeated DNA Sequences | Medium | Rolling Hash + HashSet |

Concepts Covered:

- Fixed-length Rolling Hash
- Duplicate Detection
- HashSet with Rolling Hash

---

# Phase 3 – Prefix Hashing

**Goal:** Answer substring hash queries in O(1).

(No dedicated LeetCode yet)

Topics:

- Prefix Hash Array
- Power Array
- Substring Hash Formula
- Modular Arithmetic
- Normalizing Hashes

---

# Phase 4 – Prefix Hash Applications

| LeetCode | Problem | Difficulty | Learn |
|----------|---------|------------|-------|
| 214 | Shortest Palindrome | Hard | Forward & Reverse Hash |
| 1392 | Longest Happy Prefix | Hard | Prefix Hash |

Concepts Covered:

- Prefix Hash
- Reverse Hash
- Palindrome Checking

---

# Phase 5 – Binary Search + Hashing

**Goal:** Combine Binary Search with Rolling Hash.

| LeetCode | Problem | Difficulty | Learn |
|----------|---------|------------|-------|
| 1044 | Longest Duplicate Substring | Hard | Binary Search + Rolling Hash |
| 1062 | Longest Repeating Substring | Medium | Binary Search + Hashing |

Concepts Covered:

- Binary Search on Answer
- Duplicate Detection
- HashSet
- Collision Handling

---

# Phase 6 – Advanced String Hashing

| LeetCode | Problem | Difficulty | Learn |
|----------|---------|------------|-------|
| 1316 | Distinct Echo Substrings | Hard | Prefix Hash |
| 1923 | Longest Common Subpath | Hard | Multi-String Rolling Hash |
| 2156 | Find Substring With Given Hash Value | Hard | Reverse Rolling Hash |

Concepts Covered:

- Multi-string Hashing
- Reverse Rolling Hash
- Advanced Sliding Hash
- Multiple Hash Sets

---

# Phase 7 – Optional (Competitive Programming)

Topics:

- Double Hashing
- Triple Hashing
- Modular Inverse
- Prefix Hash with Division
- Dynamic String Hashing
- Hashing on Trees
- Hashing on Graphs
- 2D Rolling Hash

---

# Learning Order

```
String Matching
        │
        ▼
Naive Search
        │
        ▼
Polynomial Hash
        │
        ▼
Rolling Hash
        │
        ▼
Rabin-Karp
        │
        ▼
Repeated String Match
        │
        ▼
Repeated DNA Sequences
        │
        ▼
Prefix Hash
        │
        ▼
Substring Hash Queries
        │
        ▼
Palindrome Hashing
        │
        ▼
Binary Search + Hashing
        │
        ▼
Longest Duplicate Substring
        │
        ▼
Advanced Hashing
```

---

# Concepts Checklist

## Fundamentals

- [ ] Character Mapping
- [ ] Polynomial Hash
- [ ] Choosing Base
- [ ] Choosing Mod
- [ ] Hash Collision
- [ ] Double Hashing

---

## Rolling Hash

- [ ] Window Hash
- [ ] Sliding Window Update
- [ ] Remove Character
- [ ] Add Character
- [ ] Rabin-Karp

---

## Prefix Hash

- [ ] Prefix Hash Array
- [ ] Power Array
- [ ] Substring Hash
- [ ] Reverse Hash
- [ ] Palindrome Hash

---

## Advanced

- [ ] Binary Search + Hashing
- [ ] Duplicate Substrings
- [ ] Longest Repeating Substring
- [ ] Longest Common Subpath
- [ ] Echo Substrings

---

# Interview Preparation Order

1. ✅ 28 - Find the Index of the First Occurrence in a String
2. ✅ 686 - Repeated String Match
3. ✅ 187 - Repeated DNA Sequences
4. ✅ Prefix Hash Theory
5. ✅ 214 - Shortest Palindrome
6. ✅ 1392 - Longest Happy Prefix
7. ✅ 1044 - Longest Duplicate Substring
8. ✅ 1062 - Longest Repeating Substring
9. ✅ 1316 - Distinct Echo Substrings
10. ✅ 2156 - Find Substring With Given Hash Value
11. ✅ 1923 - Longest Common Subpath

---

# Goal

By completing this roadmap, you will be comfortable with:

- Rabin-Karp Algorithm
- Rolling Hash
- Prefix Hash
- Palindrome Hashing
- Binary Search + Hashing
- Duplicate Substring Problems
- Advanced Interview Questions
- Competitive Programming String Hashing