# KMP (Knuth-Morris-Pratt) Algorithm - Complete Interview Notes

---

# 1. What is KMP?

KMP (Knuth-Morris-Pratt) is a **linear-time string pattern matching algorithm** used to efficiently find occurrences of a pattern inside a text.

Instead of restarting the search after every mismatch (like the naive approach), KMP **reuses information from previous matches** to skip unnecessary comparisons.

**Time Complexity:** `O(N + M)`

* `N` = Length of Text
* `M` = Length of Pattern

---

# 2. What Problem Does It Solve?

Given

```text
Text    = ABABDABACDABABCABAB
Pattern = ABABCABAB
```

Find

* First occurrence
* All occurrences
* Count occurrences
* Check if pattern exists

without repeatedly comparing already matched characters.

---

# 3. Why Do We Need KMP?

## Naive Algorithm

Every mismatch starts matching from the beginning.

Example

```text
Text

ABABABABC

Pattern

ABABC
```

Comparison

```text
ABAB✓
C ✗
```

Naive algorithm again starts from

```text
ABABC
 ^
```

Although

```text
AB
```

was already known to match.

This causes

```text
Worst Case = O(N × M)
```

Example

```text
AAAAAAAAAAAAAAAAAB

AAAAAAAB
```

Nearly every comparison is repeated.

---

# 4. Core Idea of KMP

Instead of asking

> "Where should I restart?"

KMP asks

> "How much of what I've already matched is still useful?"

It finds the answer using the **LPS Array**.

---

# 5. What is LPS?

LPS =

> **Longest Proper Prefix which is also a Suffix**

Proper Prefix

* Starts from beginning
* Cannot be the entire string

Suffix

* Ends at the last character

Example

```text
ABAB
```

Prefixes

```text
A
AB
ABA
```

Suffixes

```text
B
AB
BAB
```

Longest Common

```text
AB
```

Length

```text
2
```

Therefore

```text
LPS = 2
```

---

# 6. What Does the LPS Array Store?

For every index

```text
i
```

LPS[i] stores

> Length of the longest proper prefix which is also a suffix for the substring `pattern[0...i]`.

Example

Pattern

```text
ABABAC
```

LPS

```text
0 0 1 2 3 0
```

Meaning

| Substring | LPS |
| --------- | --- |
| A         | 0   |
| AB        | 0   |
| ABA       | 1   |
| ABAB      | 2   |
| ABABA     | 3   |
| ABABAC    | 0   |

---

# 7. Why Does LPS Help?

Suppose we've matched

```text
ABABA
```

Mismatch occurs.

Instead of restarting

```text
A
```

LPS tells us

```text
ABA
```

is already guaranteed to match.

So we continue from there.

No repeated comparisons.

---

# 8. KMP Algorithm Steps

## Step 1

Construct LPS array

Time

```text
O(M)
```

---

## Step 2

Search Pattern

Maintain

```text
i -> Text

j -> Pattern
```

If characters match

```text
i++
j++
```

If mismatch

If

```text
j == 0
```

Move text

```text
i++
```

Else

```text
j = lps[j-1]
```

Notice

```text
Text pointer never moves backward.
```

---

# 9. Building the LPS Array

Maintain

```text
len = previous LPS length

i = current character
```

Algorithm

```
Start

len = 0

i = 1

While i < pattern.length

    if pattern[i] == pattern[len]

        len++

        lps[i]=len

        i++

    else

        if len !=0

            len=lps[len-1]

        else

            lps[i]=0

            i++
```

---

# 10. Java Code (LPS)

```java
public int[] buildLPS(String pattern) {

    int n = pattern.length();
    int[] lps = new int[n];

    int len = 0;
    int i = 1;

    while (i < n) {

        if (pattern.charAt(i) == pattern.charAt(len)) {

            len++;
            lps[i] = len;
            i++;

        } else {

            if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }

        }
    }

    return lps;
}
```

---

# 11. Java Code (KMP Search)

```java
public int search(String text, String pattern) {

    int[] lps = buildLPS(pattern);

    int i = 0;
    int j = 0;

    while (i < text.length()) {

        if (text.charAt(i) == pattern.charAt(j)) {
            i++;
            j++;
        }

        if (j == pattern.length())
            return i - j;

        else if (i < text.length() &&
                text.charAt(i) != pattern.charAt(j)) {

            if (j != 0)
                j = lps[j - 1];
            else
                i++;
        }
    }

    return -1;
}
```

---

# 12. Time Complexity

Building LPS

```text
O(M)
```

Searching

```text
O(N)
```

Overall

```text
O(N + M)
```

Space

```text
O(M)
```

---

# 13. Why Isn't LPS O(M²)?

Although there appears to be nested logic, both pointers (`i` and `len`) only move a limited number of times.

* `i` only moves forward.
* `len` moves backward using previously computed LPS values and can only increase up to `M`.

Therefore, the total work is linear.

---

# 14. Interview Problems Using KMP

## Direct Pattern Matching

* Find substring
* Count occurrences
* First occurrence
* All occurrences

---

## Longest Prefix = Suffix

Examples

```text
Longest Happy Prefix
```

---

## Repeated Substring Pattern

If

```text
lps[n-1] > 0
```

then string may be built from repeated blocks.

---

## String Rotation

Search

```text
s2

inside

s1+s1
```

using KMP.

---

## Shortest Palindrome

Uses LPS on

```text
S + "#" + reverse(S)
```

---

## Minimum Characters to Add

Uses longest prefix-suffix information.

---

# 15. Common Interview Questions

### Why does

```text
len=lps[len-1]
```

work?

Because the previous LPS tells us the next largest prefix that could still match after a mismatch.

---

### Why doesn't text move backward?

Because every fallback happens inside the pattern.

---

### Why store lengths instead of strings?

Lengths are enough to know where to continue matching.

---

### Why "Proper" Prefix?

If the whole string were allowed, every string would match itself, making the information useless.

---

# 16. Comparison with Other String Matching Algorithms

| Algorithm        | Time Complexity                    | Space       | Best Use Case                                   | Advantages                                               | Disadvantages                                                |
| ---------------- | ---------------------------------- | ----------- | ----------------------------------------------- | -------------------------------------------------------- | ------------------------------------------------------------ |
| **Naive**        | O(N × M)                           | O(1)        | Small inputs                                    | Very simple                                              | Repeats comparisons                                          |
| **KMP**          | O(N + M)                           | O(M)        | Exact pattern matching                          | Guaranteed linear time, no backtracking in text          | LPS preprocessing can be less intuitive                      |
| **Rabin-Karp**   | Average: O(N + M), Worst: O(N × M) | O(1)        | Multiple pattern matching, plagiarism detection | Hashing makes comparisons fast on average                | Hash collisions require verification                         |
| **Z-Algorithm**  | O(N + M)                           | O(N + M)    | Pattern matching, prefix-related problems       | Elegant for prefix computations and many string problems | Requires building a combined string (`pattern + '$' + text`) |
| **Boyer-Moore**  | Best: Sublinear, Worst: O(N × M)   | O(Alphabet) | Very large texts                                | Skips many characters, often fastest in practice         | More complex preprocessing                                   |
| **Aho-Corasick** | O(N + Total Pattern Length)        | High        | Searching many patterns simultaneously          | Finds multiple patterns in one pass                      | More complex data structure (Trie + Failure Links)           |

---

# 17. KMP vs Z Algorithm

| Feature              | KMP                                 | Z Algorithm                                  |
| -------------------- | ----------------------------------- | -------------------------------------------- |
| Preprocessing        | LPS Array                           | Z Array                                      |
| Works On             | Pattern                             | Combined String (`Pattern + '$' + Text`)     |
| Main Idea            | Reuse matched prefix after mismatch | Find longest prefix match at every position  |
| Space                | O(M)                                | O(N + M)                                     |
| Pattern Matching     | Excellent                           | Excellent                                    |
| Prefix Problems      | Good                                | Excellent                                    |
| Easier to Understand | Usually after learning LPS          | Simpler once the Z-box concept is understood |
| Interview Frequency  | Very High                           | High                                         |

### Rule of Thumb

* **Need efficient exact pattern searching with minimal preprocessing?** → **KMP**
* **Need to solve prefix-based string problems (e.g., longest prefix matches, pattern concatenation tricks)?** → **Z-Algorithm**

---

# 18. KMP vs Rabin-Karp

| Feature           | KMP                 | Rabin-Karp                               |
| ----------------- | ------------------- | ---------------------------------------- |
| Technique         | Prefix-Suffix (LPS) | Rolling Hash                             |
| Worst Case        | O(N + M)            | O(N × M)                                 |
| Average Case      | O(N + M)            | O(N + M)                                 |
| Hashing Needed    | No                  | Yes                                      |
| Collision Risk    | None                | Yes                                      |
| Multiple Patterns | Less suitable       | Better with hashing variants             |
| Deterministic     | Yes                 | No (unless every hash match is verified) |

**Rule of Thumb**

* Use **KMP** when you need guaranteed worst-case linear performance for exact matching.
* Use **Rabin-Karp** when hashing is advantageous, such as detecting many potential matches or duplicate substrings.

---

# 19. Key Takeaways

* KMP avoids rechecking characters by preprocessing the pattern into an **LPS array**.
* LPS stores the length of the **longest proper prefix that is also a suffix** for every prefix of the pattern.
* During a mismatch, the **text pointer never moves backward**; only the pattern pointer falls back using the LPS values.
* LPS construction takes **O(M)**, searching takes **O(N)**, giving an overall complexity of **O(N + M)**.
* KMP is one of the most important deterministic string matching algorithms and is frequently tested in interviews, along with the **Z-Algorithm** and **Rabin-Karp**.


Perfect. The **dry run** is the most important part of KMP because once you understand how the pointers move, you'll never have to memorize the algorithm again.

We'll do **two dry runs**:

1. **Building the LPS Array** (the preprocessing step)
2. **KMP Search** (using the LPS array)

---

# Dry Run 1: Building the LPS Array

## Pattern

```text
Pattern = A B A B A C
Index     0 1 2 3 4 5
```

We want to compute

```text
LPS = ?
```

Initially

```text
lps[0] = 0

len = 0
i = 1
```

Here,

* `len` = length of the current longest prefix that is also a suffix.
* `i` = current character we are computing the LPS for.

---

## Iteration 1

```
i = 1
len = 0
```

Current characters

```
pattern[i]   = B
pattern[len] = A
```

Compare

```
B == A ?

No
```

Since

```
len == 0
```

there is no smaller prefix to try.

So

```
lps[1] = 0
```

Move

```
i++
```

State

```
LPS

0 0 _ _ _ _
```

---

## Iteration 2

```
i = 2
len = 0
```

Characters

```
pattern[2] = A
pattern[0] = A
```

Match!

Increase

```
len++

len = 1
```

Store

```
lps[2] = 1
```

Move

```
i++
```

Current

```
LPS

0 0 1 _ _ _
```

Meaning

Substring

```
ABA
```

Longest prefix = suffix

```
A
```

Length = 1

---

## Iteration 3

```
i = 3
len = 1
```

Characters

```
pattern[3] = B
pattern[1] = B
```

Match

```
len++

len = 2
```

Store

```
lps[3] = 2
```

Move

```
i++
```

Current

```
LPS

0 0 1 2 _ _
```

Meaning

Substring

```
ABAB
```

Longest prefix-suffix

```
AB
```

---

## Iteration 4

```
i = 4
len = 2
```

Characters

```
pattern[4] = A
pattern[2] = A
```

Match

```
len = 3

lps[4] = 3
```

Move

```
i++
```

Current

```
LPS

0 0 1 2 3 _
```

Meaning

Substring

```
ABABA
```

Longest prefix-suffix

```
ABA
```

---

## Iteration 5

Current

```
i = 5
len = 3
```

Characters

```
pattern[5] = C

pattern[3] = B
```

Mismatch.

This is where KMP becomes interesting.

---

### First Fallback

Instead of

```
len = 0
```

we ask

> What is the next longest prefix that could still match?

Answer

```
len = lps[len-1]

= lps[2]

=1
```

Now

```
len =1
```

Try again.

Compare

```
pattern[5]=C

pattern[1]=B
```

Still mismatch.

---

### Second Fallback

Again

```
len=lps[0]

=0
```

Now

```
len=0
```

Compare

```
pattern[5]=C

pattern[0]=A
```

Mismatch.

Since

```
len==0
```

Store

```
lps[5]=0
```

Move

```
i++
```

Finished.

Final

```
Pattern

A B A B A C

LPS

0 0 1 2 3 0
```

---

# Visualizing the Fallback

Instead of

```
ABABA
```

we think

```
ABABA

Prefixes

A

AB

ABA

ABAB
```

Suffixes

```
A

BA

ABA

BABA
```

Longest common

```
ABA
```

Length

```
3
```

So after mismatch,

instead of restarting

```
A
```

we continue from

```
ABA
```

If that also fails

Try

```
A
```

If that fails

Restart.

---

# Dry Run 2: KMP Search

Now use

```
Text

ABABDABACDABABCABAB
```

Pattern

```
ABABCABAB
```

LPS

```
0 0 1 2 0 1 2 3 4
```

Maintain

```
i -> text

j -> pattern
```

Initially

```
i=0

j=0
```

---

## Step 1

```
Text

A

Pattern

A
```

Match

```
i=1

j=1
```

---

## Step 2

```
B == B

✓
```

```
i=2

j=2
```

---

## Step 3

```
A==A

✓
```

```
i=3

j=3
```

---

## Step 4

```
B==B

✓
```

```
i=4

j=4
```

Matched so far

```
ABAB
```

---

## Step 5

Current

```
Text

D

Pattern

C
```

Mismatch.

Current

```
j=4
```

Instead of

```
j=0
```

Use

```
j=lps[3]

=2
```

Notice

```
i

does NOT move.
```

Still

```
i=4
```

---

### Try Again

Compare

```
Text

D

Pattern index 2

A
```

Mismatch again.

Fallback

```
j=lps[1]

=0
```

Again

```
i

doesn't move.
```

---

Try again

```
Text

D

Pattern

A
```

Mismatch

Now

```
j==0
```

Finally

```
i++
```

Now

```
i=5
```

---

Notice something amazing.

Naive algorithm would have restarted from scratch.

KMP only changed

```
j

4

↓

2

↓

0
```

without touching

```
i
```

This is why

```
O(N)
```

is possible.

---

## Continue

Eventually

```
i = 19

j = 9
```

Since

```
j==pattern.length()
```

Pattern found.

Answer

```
Start Index

= i-j

=19-9

=10
```

---

# Pointer Movement Visualization

```
Text

ABABDABACDABABCABAB

^

i


Pattern

ABABCABAB

^

j
```

After several matches

```
Text

ABABDABACDABABCABAB

    ^

    D

Pattern

ABABCABAB

    ^

    C
```

Mismatch

Instead of moving

```
i
```

back,

KMP moves only

```
j

↓

4

↓

2

↓

0
```

Text pointer never changes.

---

# The Three Cases to Remember

Every iteration of the search loop falls into exactly one of these cases:

### Case 1: Characters Match ✅

```java
if (text.charAt(i) == pattern.charAt(j)) {
    i++;
    j++;
}
```

Example:

```
Text:     A B A
          ^
Pattern:  A B A
          ^
```

Both pointers move because the current characters are part of a valid match.

---

### Case 2: Mismatch, but Some Prefix Can Still Match 🔄

```java
j = lps[j - 1];
```

Example:

```
Matched: ABAB
Mismatch at next character

ABAB
 ||
ABAB

LPS says: "AB" is still a valid prefix-suffix.

Instead of restarting:
j = 2
```

Only the **pattern pointer** moves back. The **text pointer stays where it is**.

---

### Case 3: Mismatch and No Prefix Can Be Reused ❌

```java
if (j == 0)
    i++;
```

Example:

```
Text:     D
Pattern:  A
```

Since no part of the pattern has matched (`j == 0`), there is nothing to reuse. We simply move to the next text character.

---

# The One-Line Intuition

> **KMP preprocesses the pattern into an LPS array so that after a mismatch, it knows exactly how much of the previous match can still be reused, allowing the text pointer to never move backward and achieving O(N + M) time.**

This is the explanation interviewers are usually looking for because it captures the essence of why KMP is more efficient than the naive approach.
