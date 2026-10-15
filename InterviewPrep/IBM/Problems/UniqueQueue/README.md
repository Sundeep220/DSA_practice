# 2. Queue Using a Linked List (Unique Enqueue)

**Category:** Machine coding / Design
**Confluent tag:** Unique variation — confirm the exact rejection rule before starting.

---

## Problem

Implement a queue backed by a singly linked list where `enqueue` silently rejects a value that is already present in the queue. `dequeue` removes and returns the front element. The queue must support:

- `enqueue(int value)` — adds the value to the back if not already present.
- `dequeue()` — removes and returns the front value, or throws/returns sentinel if empty.
- `peek()` — returns the front value without removing it.
- `isEmpty()` — returns true if the queue has no elements.

---

## What the interviewer is really testing

1. Can you implement a linked list from scratch (no `LinkedList` from stdlib)?
2. Do you know when to add a second data structure to solve a membership check in O(1)?
3. Do you handle edge cases (empty dequeue, duplicate enqueue) cleanly?
4. Can you articulate your design choices out loud?

---

## Design choices for an interview

### Choice 1: Linked list node — write it from scratch

Use a plain inner `Node` class with `int value` and `Node next`. Do **not** use `java.util.LinkedList`. The interviewer wants to see you build the list manually.

```
head -> [A] -> [B] -> [C] -> null
tail  --------------------------------^
```

Keep a `head` pointer for O(1) dequeue and a `tail` pointer for O(1) enqueue. Without `tail`, enqueue is O(n).

### Choice 2: Duplicate check — use a HashSet alongside the list

A naïve approach scans the list for duplicates: O(n) per enqueue. In an interview you can do better in one sentence: *"I'll keep a `HashSet<Integer>` as a side structure. Enqueue checks the set in O(1) before adding to the list. Dequeue removes from both."*

This is the interview-feasible choice. A pure linked-list solution with O(n) check is acceptable only if the interviewer explicitly says no extra memory.

| Approach | Enqueue | Dequeue | Extra space |
|---|---|---|---|
| Scan the list | O(n) | O(1) | O(1) |
| HashSet side index | O(1) avg | O(1) | O(n) |

### Choice 3: Dequeue on empty — throw, not return -1

Throw `NoSuchElementException` (same as `java.util.Queue`). This makes misuse visible immediately. If the interviewer wants a sentinel, switch to returning `-1` or `Optional<Integer>` — say so out loud.

### Choice 4: Generic vs int — keep it `int` first

Start with `int` to avoid generic syntax noise. Offer to make it `<T>` at the end if asked.

### Choice 5: Thread safety — do not add it unless asked

Do not reach for `synchronized` upfront. If asked, wrap each public method with `synchronized` and explain the trade-off. This keeps the core logic readable during the interview.

---

## Class skeleton (say this before writing)

> "I'll have a private `Node` inner class, a `head` and `tail` pointer, a `size` field, and a `HashSet<Integer>` called `seen`. Enqueue checks `seen`, adds to the tail, and updates `seen`. Dequeue removes the head, decrements size, and removes from `seen`."

---

## Complexity

| Operation | Time | Space |
|---|---|---|
| `enqueue` | O(1) avg | O(1) per element |
| `dequeue` | O(1) | — |
| `peek` | O(1) | — |
| Overall space | — | O(n) |

---

## Edge cases to mention in the interview

- Enqueue a duplicate → silently ignored, queue unchanged.
- Dequeue from an empty queue → throw `NoSuchElementException`.
- Enqueue, dequeue, then enqueue the same value again → must be accepted the second time because it is no longer in the queue.
- Single element → after dequeue, `head` and `tail` are both null.
- All duplicates → queue stays at size 0.

---

## Follow-up questions the interviewer may ask

- **Make it thread-safe.** Synchronize all public methods or use `ReentrantLock`. Point out that `HashSet` is not thread-safe either, so you'd swap it for `Collections.synchronizedSet` or a `ConcurrentHashMap` keyset.
- **Make it generic.** Replace `int` with `<T>` and `HashSet<Integer>` with `HashSet<T>`.
- **What if values are objects, not primitives?** Duplicate detection depends on correct `equals`/`hashCode`. State this assumption.
- **Return the duplicate to the caller instead of silently dropping it.** Change the return type of `enqueue` to `boolean`.
