# 3. Infinite Stream and Bounded Queue

**Category:** Machine coding / Design

---

## Problem

Process an endless stream of integer values using a fixed-capacity queue. Support:

- `offer(int value)` — adds the value if the queue is not full, drops it if full.
- `poll()` — removes and returns the front value, or throws if empty.
- `peek()` — returns the front value without removing it.
- `isFull()` / `isEmpty()` — state queries.
- `size()` / `capacity()` — size queries.

The queue must be backed by a **circular array** (ring buffer), not a linked list. No `java.util.Queue` or `java.util.ArrayDeque` internally.

---

## What the interviewer is really testing

1. Can you implement a ring buffer with a fixed array, `head`, `tail`, and `size`?
2. Do you know how to wrap indices with `% capacity` instead of shifting elements?
3. Do you handle full vs empty correctly without confusing the two?
4. Can you explain the trade-off between a linked list and an array-based approach?

---

## Design choices for an interview

### Choice 1: Ring buffer — use a fixed array with head, tail, and size

A naive queue shifts all elements on dequeue: O(n). A ring buffer reuses space by wrapping indices.

```
capacity = 5, head = 1, tail = 4, size = 3

index:  0    1    2    3    4
value: [ ]  [A]  [B]  [C]  [ ]
             ^              ^
            head           tail (next write goes here)
```

- `offer`: write at `tail`, then `tail = (tail + 1) % capacity`, increment `size`.
- `poll`: read from `head`, then `head = (head + 1) % capacity`, decrement `size`.
- Both are O(1).

### Choice 2: Track fullness with size, not by leaving a slot empty

Two common approaches:
| Approach | Cost |
|---|---|
| Leave one slot empty to distinguish full/empty | Wastes one slot; logic is tricky |
| Keep an explicit `size` field | No wasted slot; `isFull = size == capacity`, `isEmpty = size == 0` |

The `size` field is cleaner to write and explain in an interview. Use it.

### Choice 3: offer drops silently on full — say this explicitly

The problem says the stream is infinite, so blocking is not an option for the basic version. `offer` returns `false` when full and the value is dropped. Mention this out loud:

> "Since the stream never stops, I return false and drop the value when the queue is full. If blocking were required I'd use `wait/notifyAll` or `BlockingQueue`, but that's a separate variant."

### Choice 4: What to do on empty poll — throw, not return -1

Same rationale as problem 2: throw `NoSuchElementException`. Offer to change to `Optional<Integer>` if asked.

### Choice 5: Linked list vs array — know the trade-off

| | Array (ring buffer) | Linked list |
|---|---|---|
| Memory | Fixed, allocated upfront | Grows per element |
| Cache | Sequential, cache-friendly | Pointer chasing |
| Capacity | Fixed | Unbounded |
| Use when | Bounded and high-throughput | Unbounded or unknown size |

In this problem, capacity is fixed, so the array is the right choice.

---

## Class skeleton (say this before writing)

> "I'll have a fixed `int[] buffer` of the given capacity, an `int head` pointing to the next read, an `int tail` pointing to the next write, and an `int size`. offer writes at tail and wraps, poll reads from head and wraps. Full when `size == capacity`, empty when `size == 0`."

---

## Complexity

| Operation | Time | Space |
|---|---|---|
| `offer` | O(1) | — |
| `poll` | O(1) | — |
| `peek` | O(1) | — |
| Overall space | — | O(capacity) |

---

## Edge cases to mention

- `offer` when full → returns `false`, value dropped, queue unchanged.
- `poll` when empty → throws `NoSuchElementException`.
- Capacity 1 → after one `offer`, the queue is full; after one `poll`, it is empty and the same slot is reused.
- Wraparound → `tail` reaches the end of the array and wraps to index 0 while `head` is still in the middle.
- All values the same → no uniqueness constraint here, unlike problem 2.

---

## Follow-up questions the interviewer may ask

- **Make it blocking (producer-consumer).** Use `synchronized` + `wait/notifyAll` on full/empty, or replace with `java.util.concurrent.ArrayBlockingQueue`. This is problem 8 in the list.
- **Make it generic.** Replace `int[]` with `Object[]` and cast on read.
- **Make it thread-safe without blocking.** Use `AtomicInteger` for size and `AtomicInteger` for head/tail — but point out that a lock-free ring buffer is non-trivial and prone to ABA problems.
- **What if capacity is 0?** Throw `IllegalArgumentException` in the constructor.
