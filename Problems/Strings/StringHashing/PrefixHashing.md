# Prefix Hashing (Polynomial Rolling Hash) – Complete Interview Notes

---

# 1. What is Prefix Hashing?

**Prefix Hashing** is a preprocessing technique that allows us to compute the **hash of any substring in O(1)** after an initial **O(N)** preprocessing.

It is an extension of the **Polynomial Rolling Hash** used in Rabin-Karp.

---

## Why do we need it?

Suppose we have

```text
String = abcdefghijklmnop
```

Now we receive queries like

```text
Compare

substring(2,6)

with

substring(10,14)
```

Naively

```text
Compare each character

O(length)
```

If we have

```text
100000 substring queries
```

Time becomes

```text
O(N²)
```

Too slow.

Instead we'd like

```text
Hash(substring1)

==

Hash(substring2)
```

in

```text
O(1)
```

This is exactly what Prefix Hashing provides.

---

# 2. Prerequisite: Polynomial Hash

We first assign every character a value.

Example

```text
a = 1
b = 2
c = 3
...
z = 26
```

Choose

```text
BASE = 31
```

(Hashing lowercase English letters; for larger alphabets, other bases like 131 or 911382323 modulo a prime are also common.)

Polynomial Hash

```text
hash("abc")

=

1×31²

+

2×31¹

+

3×31⁰
```

Equivalent iterative implementation

```java
hash = 0;

for every character

hash = hash * BASE + value(character);
```

---

# 3. Limitation of Normal Polynomial Hash

Suppose

```text
Hash("abcdefgh")
```

is already computed.

Now someone asks

```text
Hash("cdef")
```

Without Prefix Hash

You must compute

```text
c

↓

d

↓

e

↓

f
```

again.

Time

```text
O(length)
```

We want

```text
O(1)
```

---

# 4. Prefix Hash Idea

Exactly like Prefix Sum.

Prefix Sum

```text
1 4 2 8 5
```

stores

```text
1

5

7

15

20
```

Now

```text
sum(l,r)
```

becomes

```text
prefix[r]

-

prefix[l-1]
```

Prefix Hash follows the same philosophy.

Instead of storing sums,

store hashes.

---

# 5. Prefix Hash Definition

For

```text
abcdef
```

Define

```text
prefixHash[i]
```

as

```text
Hash of

s[0...i]
```

Example

```text
a

ab

abc

abcd

abcde

abcdef
```

Every prefix hash is built from the previous one.

---

# 6. Prefix Hash Formula

Let

```text
BASE = 31
```

Then

```text
prefix[0]

=

value(first character)
```

For every next character

```text
prefix[i]

=

prefix[i-1]

×

BASE

+

value(current character)
```

Exactly the same recurrence used in Rabin-Karp.

---

# 7. Example

String

```text
abcd
```

Values

```text
a=1

b=2

c=3

d=4
```

Compute

### Index 0

```text
1
```

---

### Index 1

```text
1×31+2

=

33
```

---

### Index 2

```text
33×31+3

=

1026
```

---

### Index 3

```text
1026×31+4

=

31810
```

Final Prefix Hash

| Index | 0 | 1  | 2    | 3     |
| ----- | - | -- | ---- | ----- |
| Hash  | 1 | 33 | 1026 | 31810 |

---

# 8. Why Can't We Simply Subtract?

Suppose

```text
abc
```

Hash is

```text
1×31²

+

2×31

+

3
```

Hash of

```text
ab
```

is

```text
1×31

+

2
```

Notice

```text
abc

≠

ab + c
```

The powers of BASE are different.

Therefore

```text
prefix[r]

-

prefix[l-1]
```

does **NOT** produce the substring hash.

We must first align the polynomial powers.

---

# 9. Power Array

Precompute

```text
power[i]

=

BASE^i
```

Example

```text
BASE=31
```

Power Array

```text
1

31

961

29791

923521

...
```

Now we can correctly remove the unwanted prefix.

---

# 10. Substring Hash Formula

Suppose

```text
l = left index

r = right index
```

If

```text
l==0
```

then

```text
hash

=

prefix[r]
```

Otherwise

```text
hash(l,r)

=

prefix[r]

-

prefix[l-1]

×

power[r-l+1]
```

This is the most important formula in Prefix Hashing.

---

# 11. Why Does This Formula Work?

Suppose

```text
abcdef
```

Need

```text
cde
```

Prefix

```text
abcdef
```

contains

```text
ab

+

cde
```

But

```text
ab
```

is multiplied by additional powers.

Multiplying

```text
prefix[l-1]
```

by

```text
BASE^(substring length)
```

aligns it perfectly with the corresponding terms inside `prefix[r]`, so subtracting removes the prefix and leaves only the desired substring hash.

---

# 12. Building Prefix Hash

```java
private static final long BASE = 31;

private long[] buildPrefixHash(String s) {

    int n = s.length();

    long[] prefix = new long[n];

    prefix[0] = value(s.charAt(0));

    for (int i = 1; i < n; i++) {

        prefix[i] = prefix[i - 1] * BASE + value(s.charAt(i));

    }

    return prefix;
}
```

---

# 13. Building Power Array

```java
private static final long BASE = 31;

private long[] buildPower(int n) {

    long[] power = new long[n];

    power[0] = 1;

    for (int i = 1; i < n; i++) {

        power[i] = power[i - 1] * BASE;

    }

    return power;
}
```

---

# 14. Substring Hash Function

```java
private long getHash(
        int left,
        int right,
        long[] prefix,
        long[] power
) {

    if (left == 0)
        return prefix[right];

    return prefix[right]
            - prefix[left - 1]
            * power[right - left + 1];
}
```

Time Complexity

```text
O(1)
```

---

# 15. Complete Java Utility

```java
class PrefixHash {

    private static final long BASE = 31;

    private final long[] prefix;
    private final long[] power;

    public PrefixHash(String s) {

        int n = s.length();

        prefix = new long[n];
        power = new long[n];

        power[0] = 1;

        for (int i = 1; i < n; i++)
            power[i] = power[i - 1] * BASE;

        prefix[0] = value(s.charAt(0));

        for (int i = 1; i < n; i++)
            prefix[i] = prefix[i - 1] * BASE + value(s.charAt(i));
    }

    public long getHash(int left, int right) {

        if (left == 0)
            return prefix[right];

        return prefix[right]
                - prefix[left - 1]
                * power[right - left + 1];
    }

    private int value(char c) {
        return c - 'a' + 1;
    }
}
```

Usage

```java
PrefixHash hash = new PrefixHash("abcdef");

long h1 = hash.getHash(2,4); // cde
long h2 = hash.getHash(2,4); // cde

System.out.println(h1 == h2);
```

---

# 16. Time Complexity

| Operation            | Complexity |
| -------------------- | ---------- |
| Build Prefix Hash    | O(N)       |
| Build Power Array    | O(N)       |
| Substring Hash       | O(1)       |
| Substring Comparison | O(1)       |

Space

```text
O(N)
```

---

# 17. Applications

## Compare Two Substrings

```text
hash(l1,r1)

==

hash(l2,r2)
```

---

## Longest Happy Prefix

LeetCode

```text
1392
```

Compare

```text
Prefix Hash

=

Suffix Hash
```

---

## Shortest Palindrome

LeetCode

```text
214
```

Compare

```text
Forward Hash

with

Reverse Hash
```

---

## Longest Duplicate Substring

Rolling Hash

*

Binary Search

---

## Rabin-Karp

Sliding Window

*

Rolling Hash

---

# 18. Prefix Hash vs Rolling Hash

| Prefix Hash                                                   | Rolling Hash                   |
| ------------------------------------------------------------- | ------------------------------ |
| Hash any substring                                            | Hash sliding window            |
| O(1) substring queries                                        | O(1) window update             |
| Uses Prefix Array                                             | Uses previous window hash      |
| Applications: Palindrome, Prefix/Suffix, Duplicate Substrings | Applications: Pattern Matching |

---

# 19. Prefix Hash vs KMP

| KMP                  | Prefix Hash                                 |
| -------------------- | ------------------------------------------- |
| Exact Pattern Search | Substring Comparison                        |
| Uses LPS             | Uses Polynomial Hash                        |
| No Collisions        | Possible Collisions                         |
| Deterministic        | Probabilistic (unless using double hashing) |
| O(N+M)               | O(N) preprocessing + O(1) queries           |

---

# 20. Interview Tips

### Why do we need Prefix Hash?

To answer multiple substring comparison queries efficiently in O(1).

---

### Why precompute powers?

Because subtraction alone cannot remove the prefix due to different polynomial powers. Multiplying by the appropriate power aligns the terms before subtraction.

---

### What is the limitation?

Hash collisions.

Two different strings may produce the same hash.

---

### How do we avoid collisions?

Use:

* Modulo arithmetic (e.g., (10^9+7) or (10^9+9))
* **Double Hashing** (two different base/modulus pairs), which makes collisions extremely unlikely.

---

# 21. Mental Model

```text
String
  │
  ▼
Polynomial Hash
  │
  ▼
Prefix Hash Array
  │
  ├───────────────► O(1) Substring Hash
  │
  ├───────────────► Compare Substrings
  │
  ├───────────────► Longest Happy Prefix
  │
  ├───────────────► Reverse Hash
  │                  │
  │                  ▼
  │             Palindrome Checking
  │
  └───────────────► Rolling Hash
                     │
                     ▼
                Rabin-Karp
```

## Key Takeaways

* Prefix Hash extends polynomial hashing to support **O(1) substring hash queries**.
* A **power array** is required to correctly align polynomial terms when extracting substring hashes.
* Prefix Hash is the foundation for advanced string algorithms involving substring comparison, palindrome detection, duplicate substring search, and rolling hash techniques.
* In practice, **modular arithmetic** and often **double hashing** are used to minimize the risk of hash collisions.
