# Reverse Hash & O(1) Palindrome Checking – Complete Interview Notes

---

# 1. What is Reverse Hash?

A **Reverse Hash** is simply a **Prefix Hash built on the reversed string**.

Instead of hashing

```text
abcdef
```

we hash

```text
fedcba
```

By combining:

* **Forward Prefix Hash**
* **Reverse Prefix Hash**

we can determine whether any substring is a palindrome in **O(1)**.

---

# 2. What Problem Does It Solve?

Suppose we have

```text
abacaba
```

Now we receive queries like

```text
Is substring(1,5) a palindrome?
```

which is

```text
bacab
```

Naively

```text
Compare

b == b

a == a

c == c
```

Time

```text
O(length)
```

If there are

```text
100000
```

queries

Total

```text
O(N²)
```

Too slow.

Instead we want

```text
Palindrome Query

↓

O(1)
```

---

# 3. Big Idea

A string is a palindrome if

```text
Forward

=

Reverse
```

Example

```text
racecar
```

Forward

```text
racecar
```

Reverse

```text
racecar
```

Same

↓

Palindrome

---

Another

```text
abcdef
```

Reverse

```text
fedcba
```

Different

↓

Not Palindrome

---

# 4. How Do We Check a Substring?

Suppose

```text
String

abacaba
```

Need

```text
Substring

bacab
```

Instead of comparing characters,

compare

```text
Forward Hash(bacab)

==

Reverse Hash(bacab)
```

If equal

↓

Palindrome

---

# 5. Data Structures Required

We build

```text
Original String

↓

Forward Prefix Hash
```

and

```text
Reverse String

↓

Reverse Prefix Hash
```

We also need

```text
Power Array
```

Exactly the same one used in Prefix Hashing.

---

# 6. Building the Reverse String

Original

```text
abcdef
```

Reverse

```text
fedcba
```

Nothing special.

```java
String reverse =
new StringBuilder(s)
.reverse()
.toString();
```

---

# 7. Forward Prefix Hash

Already learned.

Example

```text
abcdef
```

Store

```text
a

ab

abc

abcd

abcde

abcdef
```

Each prefix has a hash.

---

# 8. Reverse Prefix Hash

Now build the same prefix hashes on

```text
fedcba
```

Store

```text
f

fe

fed

fedc

fedcb

fedcba
```

---

# 9. Index Mapping (Most Important Concept)

Suppose

```text
Original

abcdefg

Index

0 1 2 3 4 5 6
```

Reverse

```text
gfedcba

Index

0 1 2 3 4 5 6
```

Suppose we need

```text
Substring

cde

Original

2..4
```

Where is it inside the reverse string?

Reverse contains

```text
edc
```

Indices?

---

## Formula

If

```text
Original

l...r
```

then

```text
Reverse Left

=

n-1-r
```

```text
Reverse Right

=

n-1-l
```

This formula is extremely important.

---

## Example

Original

```text
abcdefg

0123456
```

Need

```text
cde

2..4
```

Length

```text
n=7
```

Reverse Left

```text
7-1-4

=

2
```

Reverse Right

```text
7-1-2

=

4
```

Correct.

---

# 10. Palindrome Check Formula

Suppose

```text
Substring

l...r
```

Forward Hash

```text
forwardHash(l,r)
```

Reverse Hash

```text
reverseHash

(

n-1-r,

n-1-l

)
```

If

```text
forwardHash

==

reverseHash
```

↓

Palindrome.

---

# 11. Building Power Array

Exactly same as Prefix Hash.

```java
private long[] buildPower(int n){

    long[] power = new long[n];

    power[0] = 1;

    for(int i=1;i<n;i++)
        power[i] = power[i-1] * BASE;

    return power;
}
```

---

# 12. Building Forward Prefix Hash

```java
private long[] buildPrefixHash(String s){

    int n = s.length();

    long[] prefix = new long[n];

    prefix[0] = value(s.charAt(0));

    for(int i=1;i<n;i++){

        prefix[i] =
                prefix[i-1] * BASE
                + value(s.charAt(i));
    }

    return prefix;
}
```

---

# 13. Building Reverse Prefix Hash

```java
private long[] buildReverseHash(String s){

    String rev =
            new StringBuilder(s)
            .reverse()
            .toString();

    return buildPrefixHash(rev);
}
```

Notice

Same function.

Different string.

---

# 14. Getting Substring Hash

Exactly same function.

```java
private long getHash(
        int left,
        int right,
        long[] prefix,
        long[] power
){

    if(left==0)
        return prefix[right];

    return prefix[right]
            -
            prefix[left-1]
            *
            power[right-left+1];
}
```

Works for

* Forward Hash

and

* Reverse Hash

---

# 15. Palindrome Check Function

```java
private boolean isPalindrome(
        int left,
        int right,
        long[] forward,
        long[] reverse,
        long[] power,
        int n
){

    long hash1 =
            getHash(left,right,
                    forward,power);

    int reverseLeft =
            n-1-right;

    int reverseRight =
            n-1-left;

    long hash2 =
            getHash(
                    reverseLeft,
                    reverseRight,
                    reverse,
                    power);

    return hash1 == hash2;
}
```

---

# 16. Complete Utility Class

```java
class PalindromeHash {

    private static final long BASE = 31;

    private final long[] forward;
    private final long[] reverse;
    private final long[] power;
    private final int n;

    public PalindromeHash(String s){

        n = s.length();

        power = buildPower(n);

        forward =
                buildPrefixHash(s);

        reverse =
                buildReverseHash(s);
    }

    public boolean isPalindrome(
            int left,
            int right
    ){

        long hash1 =
                getHash(
                        left,
                        right,
                        forward,
                        power
                );

        int reverseLeft =
                n-1-right;

        int reverseRight =
                n-1-left;

        long hash2 =
                getHash(
                        reverseLeft,
                        reverseRight,
                        reverse,
                        power
                );

        return hash1 == hash2;
    }

    private long[] buildPower(int n){

        long[] power =
                new long[n];

        power[0] = 1;

        for(int i=1;i<n;i++)
            power[i] =
                    power[i-1] * BASE;

        return power;
    }

    private long[] buildPrefixHash(
            String s
    ){

        int n = s.length();

        long[] prefix =
                new long[n];

        prefix[0] =
                value(s.charAt(0));

        for(int i=1;i<n;i++){

            prefix[i] =
                    prefix[i-1] * BASE
                    + value(s.charAt(i));
        }

        return prefix;
    }

    private long[] buildReverseHash(
            String s
    ){

        String rev =
                new StringBuilder(s)
                        .reverse()
                        .toString();

        return buildPrefixHash(rev);
    }

    private long getHash(
            int left,
            int right,
            long[] prefix,
            long[] power
    ){

        if(left==0)
            return prefix[right];

        return prefix[right]
                -
                prefix[left-1]
                        * power[right-left+1];
    }

    private int value(char c){

        return c-'a'+1;
    }
}
```

---

# 17. Dry Run

String

```text
abacaba
```

Need

```text
Substring

bacab

1..5
```

Forward

```text
Hash(1,5)
```

Reverse

Indices

```text
left

=

7-1-5

=

1
```

```text
right

=

7-1-1

=

5
```

Reverse Hash

```text
Hash(1,5)
```

Equal

↓

Palindrome

---

Another

```text
abcdef
```

Need

```text
bcd
```

Forward

```text
Hash(bcd)
```

Reverse

```text
Hash(dcb)
```

Different

↓

Not Palindrome

---

# 18. Complexity

| Operation             | Complexity |
| --------------------- | ---------- |
| Power Array           | O(N)       |
| Forward Prefix Hash   | O(N)       |
| Reverse Prefix Hash   | O(N)       |
| Each Palindrome Query | O(1)       |
| Space                 | O(N)       |

---

# 19. Applications

### LeetCode 214

**Shortest Palindrome**

Need

```text
Longest Palindromic Prefix
```

Compare

```text
Forward Hash

=

Reverse Hash
```

---

### Palindrome Queries

Given

```text
Q = 100000
```

Queries

```text
Is

l..r

Palindrome?
```

Each query

```text
O(1)
```

---

### Longest Palindromic Prefix

Binary Search

*

Reverse Hash

---

### Longest Palindromic Suffix

Same idea.

---

### Longest Palindromic Substring

Can combine

```text
Binary Search

+

Hash
```

(Though **Manacher's Algorithm** is the optimal linear-time algorithm.)

---

# 20. Reverse Hash vs Other Algorithms

| Algorithm                     | Purpose                       | Time                        | Collision | Best Use                |
| ----------------------------- | ----------------------------- | --------------------------- | --------- | ----------------------- |
| **Reverse Hash**              | Palindrome queries            | O(N) preprocess, O(1) query | Possible  | Many palindrome checks  |
| **Manacher's Algorithm**      | Longest palindromic substring | O(N)                        | None      | Longest palindrome      |
| **KMP**                       | Pattern matching              | O(N+M)                      | None      | Exact pattern search    |
| **Z-Algorithm**               | Prefix matching               | O(N)                        | None      | Prefix-related problems |
| **Rolling Hash (Rabin-Karp)** | Pattern matching              | O(N+M) average              | Possible  | Sliding window search   |

---

# 21. Interview Questions

### Why do we need Reverse Hash?

To compare a substring with its reversed version in **O(1)**.

---

### Why build a reversed string?

Because a prefix hash can only hash prefixes efficiently. Building a prefix hash on the reversed string lets us obtain hashes of reversed substrings using the same machinery.

---

### Why is index mapping needed?

The substring `s[l...r]` appears at different positions in the reversed string. We convert:

```text
(l, r)

↓

(n-1-r, n-1-l)
```

so we can query the corresponding reversed substring.

---

### Is comparing hashes always correct?

Not always. Different strings can theoretically produce the same hash (**collision**).

Use:

* Modulo arithmetic
* Double hashing

to make collisions extremely unlikely.

---

# 22. Mental Model

```text
                 Original String
                        │
        ┌───────────────┴───────────────┐
        ▼                               ▼
 Forward Prefix Hash             Reverse String
        │                               │
        ▼                               ▼
 O(1) Forward Hash             Reverse Prefix Hash
        │                               │
        └───────────────┬───────────────┘
                        ▼
          Compare Forward & Reverse Hash
                        │
              Equal? → Palindrome
```

## Key Takeaways

* Reverse Hash is simply a **Prefix Hash built on the reversed string**.
* Any substring `s[l...r]` maps to `(n - 1 - r, n - 1 - l)` in the reversed string.
* A substring is a palindrome if its forward hash equals the hash of its corresponding reversed substring.
* After **O(N)** preprocessing, each palindrome query takes **O(1)** time.
* This technique is a core building block for problems such as **LeetCode 214 (Shortest Palindrome)** and other palindrome query problems.
