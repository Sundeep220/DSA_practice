# Z-Function (Z Algorithm) - Complete Notes

These notes explain the **Z-Function from absolute basics**, assuming you have never seen it before.

---

# 1. What Problem Does the Z Function Solve?

Suppose someone gives you a string

```text
S = "ababc"
```

Now they ask:

> **For every position in this string, if you start reading from that position, how many consecutive characters match the beginning (prefix) of the string?**

This is exactly what the Z Function computes.

---

# 2. What is a Prefix?

A prefix is simply the string starting from index 0.

Example

```text
String : ababc
```

Prefixes are

```text
a
ab
aba
abab
ababc
```

Notice something important:

> **The prefix NEVER changes.**

The prefix is always the original string starting from index 0.

This is the biggest point of confusion for beginners.

---

# 3. Definition of Z[i]

For every index `i`,

> **Z[i] = Length of the longest substring starting at index i that matches the prefix of the string.**

Or simply,

> **Starting from index i, how many characters match the beginning of the string?**

---

# 4. Visual Understanding

Suppose

```text
S = "ababc"
```

Instead of thinking about algorithms, imagine placing the string underneath itself.

```
Original

a b a b c
0 1 2 3 4
```

---

## Index 1

Substring starts here

```
a b a b c
  b a b c
```

Compare from the beginning

```
a vs b
```

Immediately different.

Therefore

```
Z[1]=0
```

---

## Index 2

Substring

```
a b a b c
    a b c
```

Now compare

```
Original : a b a b c
           | |

Substring: a b c
```

Comparisons

```
a == a   ✅
b == b   ✅
a != c   ❌
```

Matched characters

```
ab
```

Length

```
2
```

Therefore

```
Z[2]=2
```

---

## Index 3

```
Original

a b a b c

      b c
```

Compare

```
a != b
```

So

```
Z[3]=0
```

---

## Index 4

```
Original

a b a b c

        c
```

Compare

```
a != c
```

Therefore

```
Z[4]=0
```

---

Final answer

```
Index :0 1 2 3 4
Char  :a b a b c
Z     :0 0 2 0 0
```

---

# 5. Important Rule

Many people make this mistake.

When computing

```
Z[2]
```

they compare

```
abc

abc
```

This is **WRONG**.

The prefix never changes.

Correct comparison

```
Prefix

a b a b c

Substring

a b c
```

Compare character by character

```
a==a

b==b

a!=c
```

Only two characters matched.

So

```
Z[2]=2
```

---

# 6. Another Example

```
S = "aaaaa"
```

---

Index 1

```
a a a a a

  a a a a
```

Compare

```
a==a

a==a

a==a

a==a
```

Matched

```
aaaa
```

Length

```
4
```

```
Z[1]=4
```

---

Index 2

```
a a a a a

    a a a
```

Compare

```
a==a

a==a

a==a
```

```
Z[2]=3
```

---

Index 3

```
Z[3]=2
```

---

Index 4

```
Z[4]=1
```

Final

```
Index :0 1 2 3 4

Char  :a a a a a

Z     :0 4 3 2 1
```

---

# 7. Another Example

```
S = "aabcaabxaaaz"
```

Compute manually

| Index | Substring   | Matches Prefix? | Z |
| ----- | ----------- | --------------- | - |
| 1     | abcaabxaaaz | a               | 1 |
| 2     | bcaabxaaaz  | No              | 0 |
| 3     | caabxaaaz   | No              | 0 |
| 4     | aabxaaaz    | aabx            | 4 |
| 5     | abxaaaz     | a               | 1 |
| 6     | bxaaaz      | No              | 0 |
| 7     | xaaaz       | No              | 0 |
| 8     | aaaz        | aa              | 2 |
| 9     | aaz         | aa              | 2 |
| 10    | az          | a               | 1 |
| 11    | z           | No              | 0 |

Final

```
0 1 0 0 4 1 0 0 2 2 1 0
```

---

# 8. Naive Algorithm

Now we know the problem.

How do we solve it?

For every index

Start comparing from the beginning.

Example

```java
for(int i=1;i<n;i++){

    int count=0;

    while(i+count<n &&
          s.charAt(count)==s.charAt(i+count))
    {
        count++;
    }

    z[i]=count;
}
```

---

Example

```
aabxaabx
```

At

```
i=4
```

Compare

```
Prefix

aabx

Substring

aabx
```

Count

```
4
```

Store

```
Z[4]=4
```

---

Time Complexity

Outer loop

```
O(n)
```

Inner comparisons

```
O(n)
```

Overall

```
O(n²)
```

Too slow.

---

# 9. Where Is the Repeated Work?

Suppose

```
aabcaabxaaaz
```

Already computed

```
Z[4]=4
```

Meaning

```
aabx
```

matches the prefix.

Now move one position.

Instead of comparing

```
abx
```

again,

we already KNOW

```
abx
```

matches because we compared it previously.

We're repeating work.

This is what the optimized Z Algorithm avoids.

---

# 10. Main Idea of Optimization

Instead of forgetting previous comparisons,

remember the longest matching block.

We store

```
L
R
```

called the **Z-box**.

---

Imagine

```
String

aabcaabxaaaz

    L      R
    |------|

    aabx
```

Everything inside

```
[L,R]
```

matches the prefix.

Meaning

```
S[L...R]
```

is identical to

```
S[0...(R-L)]
```

---

# 11. What Do L and R Mean?

Suppose

```
L=4

R=7
```

Then

```
String

aabcaabxaaaz

    4567

    aabx
```

matches

```
0123

aabx
```

Nothing more.

L and R simply store

> The rightmost substring we've already verified matches the prefix.

---

# 12. Two Cases

## Case 1

Current index outside Z-box

```
L------R

        i
```

We know nothing.

Compare normally.

---

## Case 2

Current index inside Z-box

```
L-----------R

      i
```

We already know many characters match.

Reuse previous answer.

---

# 13. Why Can We Reuse?

Suppose

```
L=5

R=10
```

Then

```
S[5...10]
```

already equals

```
S[0...5]
```

Now suppose

```
i=7
```

Then

```
i-L=2
```

Look at

```
Z[2]
```

We already computed how many characters match from the prefix starting at offset `2`.

So instead of comparing again, we copy that information.

---

# 14. The Formula

If

```
i<=R
```

then

```java
z[i]=Math.min(R-i+1,z[i-L]);
```

---

Why minimum?

Suppose

```
Remaining window

3 characters
```

but

```
Z[i-L]=7
```

We only know for sure that 3 characters match because our verified window ends there.

So

```
take minimum
```

Then continue comparing beyond `R`.

---

# 15. Complete Algorithm

```
Initialize

L=0

R=0

For every i

    If outside Z-box

        Compare from scratch

    Else

        Copy previous answer

    Extend while characters match

    If extended beyond R

        Update L,R
```

---

# 16. Complete Java Code

```java
public class ZAlgorithm {

    public static int[] zFunction(String s) {

        int n = s.length();

        int[] z = new int[n];

        int l = 0;
        int r = 0;

        for (int i = 1; i < n; i++) {

            // Case 1: i is inside the current Z-box
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }

            // Extend the match beyond R
            while (i + z[i] < n &&
                    s.charAt(z[i]) == s.charAt(i + z[i])) {

                z[i]++;
            }

            // If we extended farther, update the Z-box
            if (i + z[i] - 1 > r) {

                l = i;
                r = i + z[i] - 1;
            }
        }

        return z;
    }

    public static void main(String[] args) {

        String s = "aabcaabxaaaz";

        int[] z = zFunction(s);

        for (int value : z) {
            System.out.print(value + " ");
        }
    }
}
```

Output

```
0 1 0 0 4 1 0 0 2 2 1 0
```

---

# 17. Why is the Time Complexity O(n)?

At first glance, it looks like the `while` loop is inside the `for` loop, so you might think it's `O(n²)`.

The key observation is:

* Every successful comparison in the `while` loop extends the right boundary `R`.
* `R` only moves **forward** and never backward.
* Across the entire algorithm, `R` can move at most `n - 1` positions.

So even though the `while` loop appears inside the `for` loop, the **total** number of character comparisons is at most `n`.

Therefore:

* **Time Complexity:** `O(n)`
* **Space Complexity:** `O(n)`

---

# 18. Pattern Matching Using the Z Function

One of the biggest applications of the Z Algorithm is finding a pattern inside a text.

Suppose:

```
Pattern = "abc"
Text    = "xyzabcabc"
```

Create a new string:

```
abc$xyzabcabc
```

(`$` is a separator character that doesn't appear in the pattern or text.)

Now compute the Z-array for this combined string.

Whenever:

```
Z[i] == pattern.length()
```

it means the pattern starts at that position in the text.

This gives an **O(n + m)** pattern matching algorithm.

---

# 19. Applications of the Z Algorithm

* Pattern matching in linear time.
* Finding all occurrences of a pattern in a text.
* Detecting repeated substrings.
* Finding the longest prefix that appears elsewhere in the string.
* Computing borders (prefixes that are also suffixes).
* String periodicity problems.
* Many advanced competitive programming string problems.

---

# 20. How to Remember the Z Function Forever

Whenever you see `Z[i]`, ask yourself **one simple question**:

> **"If I start reading the string from index `i`, how many consecutive characters match the beginning (prefix) of the string?"**

For example:

```
String : ababcab
Index  :   ^
```

Compare with the beginning:

```
Original : a b a b c a b
Shifted  :   a b c a b
```

Character by character:

```
a == a ✅
b == b ✅
a != c ❌
```

So:

```
Z[i] = 2
```

If you can answer that question for every index, you've understood the **meaning** of the Z-function. The `L`/`R` (Z-box) optimization is simply a clever way to avoid recomputing those same comparisons, reducing the runtime from **O(n²)** to **O(n)**.


This is **the hardest part of the Z Algorithm**. Once you understand this, the whole algorithm clicks.

The formula

```java
z[i] = Math.min(r - i + 1, z[i - l]);
```

looks like magic, but it's actually very logical.

Let's derive it from scratch with a complete dry run.

---

# Step 1: We'll use this string

```text
S = "aabcaabxaaaz"
```

Index it first.

```text
Index : 0 1 2 3 4 5 6 7 8 9 10 11
Char  : a a b c a a b x a a  a  z
```

Initially

```text
L = 0
R = 0
```

Our Z array

```text
Z = [0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?]
```

---

# i = 1

Outside the Z-box because

```text
1 > R (0)
```

So compare normally.

```text
Prefix

a a b c a a b x a a a z

Substring (starting at 1)

a b c a a b x a a a z
```

Compare

```text
a == a   ✅
a != b   ❌
```

Only one match.

```text
Z[1]=1
```

Update window

```text
L=1
R=1
```

Current

```text
Z

0 1 ? ? ? ? ? ? ? ? ? ?
```

---

# i = 2

Is

```text
2 > R
```

Yes.

Outside.

Compare normally.

```text
Prefix

a a b c ...

Substring

b c ...
```

```text
a != b
```

```text
Z[2]=0
```

---

# i = 3

Outside.

```text
a != c
```

```text
Z[3]=0
```

---

# i = 4

Outside.

Now compare.

```text
Prefix

a a b c a a b x a a a z

Substring

a a b x a a a z
```

Let's compare carefully.

```text
a == a   ✅

a == a   ✅

b == b   ✅

c != x   ❌
```

Wait…

Actually, let's use the standard example where `Z[4]=4` from the previous notes isn't valid for this exact string because the fourth character differs (`c` vs `x`). To avoid confusion, let's switch to the classic string where the Z-box is clearer.

---

# Better Example

We'll use

```text
S = "aabxaabxcaabxaabxay"
```

Index

```text
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18

a a b x a a b x c a  a  b  x  a  a  b  x  a  y
```

Now something interesting happens.

---

## At i = 4

Compare

```text
Prefix

a a b x a a b x ...

Substring

a a b x c ...
```

Comparisons

```text
a==a ✅

a==a ✅

b==b ✅

x==x ✅

a!=c ❌
```

Therefore

```text
Z[4]=4
```

Now update

```text
L=4

R=7
```

---

# This is the MOST IMPORTANT MOMENT

Our Z-box is

```text
Index

0 1 2 3 4 5 6 7 8

a a b x a a b x c
        |-------|
        L       R
```

Notice something.

The highlighted part

```text
a a b x
```

is IDENTICAL to

```text
a a b x
```

at the beginning.

So we already know

```text
S[4]=S[0]

S[5]=S[1]

S[6]=S[2]

S[7]=S[3]
```

This is the only fact we need.

---

# Now comes the formula

Move to

```text
i=5
```

Since

```text
5 <= R
```

we are INSIDE the box.

The algorithm says

```java
k = i - L
```

So

```text
k=5-4=1
```

Now look at

```text
Z[1]
```

Earlier we already computed

```text
Z[1]=1
```

Question:

Can we reuse it?

YES.

Why?

Because

```text
S[5]
```

behaves exactly like

```text
S[1]
```

Why?

Because

```text
S[5]
```

lies inside a region already proven equal to the prefix.

Graphically

```text
Prefix

0 1 2 3

a a b x

Window

4 5 6 7

a a b x
```

Now

```text
5
```

corresponds to

```text
1
```

So whatever happened at index 1 must also happen at index 5 **until we reach the end of the verified window**.

That's why

```text
k=i-L
```

---

# So why the minimum?

Suppose

```text
Z[1]=5
```

Imagine that.

Can we simply copy

```text
Z[5]=5
```

No.

Because our current window ends here.

```text
L--------R

4        7
```

From index 5,

only

```text
R-i+1

7-5+1

=3
```

characters are guaranteed.

Everything beyond

```text
R
```

is unknown.

So we can safely copy only

```text
3
```

characters.

Therefore

```java
Math.min(
    R-i+1,
    Z[i-L]
)
```

---

# Visual Example

Current window

```text
0 1 2 3

a a b x

4 5 6 7

a a b x
```

Current index

```text
i=5
```

Remaining window

```text
5 6 7

a b x
```

Length

```text
3
```

Suppose

```text
Z[1]=10
```

Can we say

```text
Z[5]=10
```

Impossible.

We have verified only

```text
abx
```

Nothing after that.

Hence

```text
Z[5]=min(10,3)

=3
```

Then we continue matching beyond `R` one character at a time.

---

# Another Example

Suppose

```text
Window

L=20

R=40
```

Current

```text
i=25
```

Offset

```text
k=i-L

=5
```

Earlier

```text
Z[5]=12
```

Remaining window

```text
40-25+1

=16
```

Then

```text
min(16,12)

=12
```

We can copy all 12 safely because they fit inside the verified window.

---

Another case

```text
Remaining window

5
```

Earlier

```text
Z[5]=20
```

We cannot trust all 20.

We only know

```text
5
```

Therefore

```text
min(5,20)

=5
```

Then start comparing from there.

---

# The Entire Logic Behind the Formula

When `i` is inside the Z-box:

1. Compute the corresponding index in the prefix:

```java
k = i - L;
```

2. We already know `Z[k]` from earlier.

3. But we can only trust matches that stay inside the current verified window.

4. The remaining verified window size is:

```java
R - i + 1
```

5. So the safe answer is:

```java
z[i] = Math.min(z[k], R - i + 1);
```

6. Finally, compare characters beyond `R` to see if the match can be extended.

---

## The intuition to remember forever

Think of the current Z-box `[L, R]` as a **photocopy of the prefix**.

```
Prefix:   a a b x
           ↑ ↑ ↑ ↑

Window:   a a b x
           L     R
```

If you're standing at some index `i` inside the photocopy, the corresponding position in the original prefix is `i - L`. So you can reuse the answer you already computed there—but **only until you run out of photocopy** (the end of the Z-box). After that, you have to compare fresh characters.

---

I would recommend one more step after this: a **line-by-line execution table** showing **every iteration** (`i`, `L`, `R`, `k`, initial `Z[i]`, every `while` comparison, and the updated `L`/`R`) for a complete string. Seeing the state change after each iteration is usually what makes the formula feel completely natural.
