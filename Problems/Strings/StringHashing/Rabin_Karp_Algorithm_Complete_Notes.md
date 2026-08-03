# Rabin-Karp Algorithm (Rolling Hash) - Complete Interview Notes

## Overview

Rabin-Karp is a string matching algorithm that searches for a pattern in
a text using **hashing** instead of comparing every character in every
window.

Core idea:

1.  Compute hash of the pattern.
2.  Compute hash of the first window of the text.
3.  Compare hashes.
4.  If hashes match, verify characters.
5.  Roll the window by one character and update the hash in O(1).

------------------------------------------------------------------------

## Why Not Brute Force?

Brute force compares every character of every window.

Example:

Text: `abracadabra`

Pattern: `cada`

It repeatedly compares characters, giving a worst-case complexity of
**O(N × M)**.

------------------------------------------------------------------------

## Intuition

Instead of comparing:

    hello
    hello

compare:

    93847291
    93847291

The integer is the string's fingerprint (hash).

Only when fingerprints match do we compare the characters.

------------------------------------------------------------------------

## Real World Analogies

### Airport Security

Hash = Boarding pass ID.

Security checks the boarding pass first.

Only if it matches the records are detailed checks performed.

### PDF Checksums

Instead of reading two huge PDF files page by page, compare their
SHA-256 hashes.

Same idea.

------------------------------------------------------------------------

## Polynomial Hash

Character mapping:

    a=1
    b=2
    ...
    z=26

Formula:

    Hash = value1×BASE^(m−1) + value2×BASE^(m−2) + ...

Usually computed iteratively:

``` java
hash = (hash * BASE + value) % MOD;
```

Common values:

-   BASE = 31
-   MOD = 1_000_000_007

------------------------------------------------------------------------

## Rolling Hash

Old window:

    abc

New window:

    bcd

Instead of recomputing:

1.  Remove left character.
2.  Shift remaining hash.
3.  Add new character.

This is exactly like Sliding Window.

Each update takes O(1).

------------------------------------------------------------------------

## Rabin-Karp Algorithm

1.  Compute pattern hash.
2.  Compute first window hash.
3.  Compare hashes.
4.  If equal, verify characters.
5.  Update rolling hash.
6.  Repeat.

------------------------------------------------------------------------

## Java Implementation

``` java
public int rabinKarp(String text, String pattern) {

    int n = text.length();
    int m = pattern.length();

    if (m > n)
        return -1;

    long BASE = 31;
    long MOD = 1_000_000_007;

    long patternHash = 0;
    long windowHash = 0;
    long power = 1;

    for (int i = 0; i < m - 1; i++)
        power = (power * BASE) % MOD;

    for (int i = 0; i < m; i++) {
        patternHash = (patternHash * BASE + (pattern.charAt(i) - 'a' + 1)) % MOD;
        windowHash = (windowHash * BASE + (text.charAt(i) - 'a' + 1)) % MOD;
    }

    for (int i = 0; i <= n - m; i++) {

        if (patternHash == windowHash) {

            boolean same = true;

            for (int j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    same = false;
                    break;
                }
            }

            if (same)
                return i;
        }

        if (i < n - m) {

            windowHash = (windowHash -
                    (text.charAt(i) - 'a' + 1) * power) % MOD;

            if (windowHash < 0)
                windowHash += MOD;

            windowHash = (windowHash * BASE) % MOD;

            windowHash = (windowHash +
                    (text.charAt(i + m) - 'a' + 1)) % MOD;
        }
    }

    return -1;
}
```

------------------------------------------------------------------------

## Dry Run

Text = `abcdef`

Pattern = `cde`

Windows:

    abc -> hash != pattern

    bcd -> hash != pattern

    cde -> hash == pattern

    Verify -> Match

    Return 2

------------------------------------------------------------------------

## Complexity

  Operation           Complexity
  ------------------- ------------
  Pattern Hash        O(M)
  First Window Hash   O(M)
  Rolling Window      O(N)
  Hash Update         O(1)

Average: **O(N + M)**

Worst: **O(N × M)** due to collisions.

------------------------------------------------------------------------

## Advantages

-   Average O(N+M)
-   O(1) rolling updates
-   Foundation for many interview problems

## Disadvantages

-   Hash collisions
-   Requires modulo arithmetic

------------------------------------------------------------------------

## Interview Cheat Sheet

    Pattern
     ↓
    Hash Pattern
     ↓
    Hash First Window
     ↓
    Hashes Equal?
     ↓
    Yes → Verify Characters
     ↓
    No → Roll Window
     ↓
    Repeat

Memory Trick:

**Rabin-Karp = Sliding Window + Fingerprint**
