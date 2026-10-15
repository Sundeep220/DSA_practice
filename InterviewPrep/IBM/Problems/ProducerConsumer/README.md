# 8. Producer-Consumer Bounded Queue

**Category:** Machine coding / Concurrency

---

## Problem

Implement a thread-safe bounded queue where:

- `put(value)` — blocks the calling thread until space is available, then inserts.
- `take()` — blocks the calling thread until an element is available, then removes and returns it.
- `offer(value)` — non-blocking insert; returns `false` if full.
- `poll()` — non-blocking remove; returns `null` if empty.

Internal storage must be a **ring buffer** (no `java.util.Queue` internally).

---

## What the interviewer is really testing

1. Do you use `while` (not `if`) around `wait()` to guard against spurious wakeups?
2. Do you call `notifyAll()` (not `notify()`) so both producers and consumers can be woken?
3. Is every read/write of shared state inside `synchronized`?
4. Can you explain *why* `while` is required instead of `if`?
5. Can you name the library alternative (`ArrayBlockingQueue`) and explain when to use it?

---

## Design choices for an interview

### Choice 1: `synchronized` + `wait/notifyAll` — the manual implementation

The whole queue object is the monitor. Every method that touches shared state is `synchronized`.

```
put:
  synchronized (this) {
      while (size == capacity) wait();     // release lock, sleep until notified
      // ... insert ...
      notifyAll();                          // wake any waiting takers
  }

take:
  synchronized (this) {
      while (size == 0) wait();            // release lock, sleep until notified
      // ... remove ...
      notifyAll();                          // wake any waiting putters
  }
```

### Choice 2: `while` not `if` around wait()

Two reasons to always use `while`:

| Reason | Explanation |
|---|---|
| Spurious wakeup | The JVM may wake a thread without a notify. Re-checking the condition catches this. |
| Lost condition | Multiple threads may be notified; by the time this thread runs, the condition may be false again. |

### Choice 3: `notifyAll` not `notify`

`notify` wakes exactly one thread. If you have both producers and consumers waiting and you `notify` the wrong one, neither can make progress → **deadlock**. `notifyAll` wakes everyone and lets them re-check their condition.

### Choice 4: `ReentrantLock` + two `Condition` objects — fine-grained wakeup

```java
private final ReentrantLock lock = new ReentrantLock();
private final Condition notFull  = lock.newCondition();
private final Condition notEmpty = lock.newCondition();

put(value):
    lock.lock();
    try {
        while (size == capacity) notFull.await();
        // ... insert ...
        notEmpty.signal();   // wake exactly one consumer
    } finally { lock.unlock(); }

take():
    lock.lock();
    try {
        while (size == 0) notEmpty.await();
        // ... remove ...
        notFull.signal();    // wake exactly one producer
    } finally { lock.unlock(); }
```

**Key differences from `wait/notifyAll`:**

| | `wait/notifyAll` | `ReentrantLock` + Conditions |
|---|---|---|
| Wakeup target | All waiters (producers + consumers) | Targeted: `notFull` or `notEmpty` only |
| Signal granularity | `notifyAll` — coarse | `signal()` — wakes exactly one thread |
| Spurious wakeup | Need `while` loop | Still need `while` loop (`await` can also spuriously wake) |
| Fairness | No | Yes (`new ReentrantLock(true)`) |
| Lock interruptibility | No (can't interrupt `synchronized`) | Yes (`lockInterruptibly()`) |
| Code volume | Less | More explicit |

`signal()` is safe here (not `signalAll()`) because each condition is waited on by only one *role* — producers wait on `notFull`, consumers wait on `notEmpty` — so waking one is sufficient.

### Choice 6: Two semaphores — the OS-theory alternative

Three primitives replace `wait/notifyAll` entirely:

```java
Semaphore canProduce = new Semaphore(capacity); // free slots; blocks producers at 0
Semaphore canConsume = new Semaphore(0);         // available items; blocks consumers at 0
Semaphore mutex      = new Semaphore(1);         // binary semaphore guards the ring buffer

put(value):
    canProduce.acquire();   // block until a slot is free (decrements free-slot count)
    mutex.acquire();
    // ... insert into ring buffer ...
    mutex.release();
    canConsume.release();   // signal a consumer (increments item count)

take():
    canConsume.acquire();   // block until an item exists (decrements item count)
    mutex.acquire();
    // ... remove from ring buffer ...
    mutex.release();
    canProduce.release();   // signal a producer (increments free-slot count)
```

**Why this works without `while`:** `acquire()` is atomic and counted — it only unblocks when a permit is genuinely available, so there is no spurious wakeup problem and no need to re-check a condition.

**Critical ordering rule:** Always acquire `canProduce`/`canConsume` **before** `mutex`. Acquiring `mutex` first and then blocking on a counting semaphore inside the critical section causes deadlock.

| | `wait/notifyAll` | Two semaphores |
|---|---|---|
| Spurious wakeup | Need `while` loop | Not possible — permits are counted |
| Condition check | Explicit `while` guard | Implicit in `acquire()` |
| Fairness | No | Yes (`new Semaphore(n, true)`) |
| Interview signal | Shows monitor knowledge | Shows OS / concurrency theory |
| Code clarity | Moderate | Very readable — intent is explicit |

### Choice 6: Library alternative — `ArrayBlockingQueue`

```java
BlockingQueue<Integer> q = new ArrayBlockingQueue<>(capacity);
q.put(value);    // blocks if full
q.take();        // blocks if empty
q.offer(value);  // non-blocking
q.poll();        // non-blocking
```

| | Manual (wait/notifyAll) | ArrayBlockingQueue |
|---|---|---|
| Code volume | ~40 lines | ~2 lines |
| Correctness | Easy to get wrong | Battle-tested |
| Fairness option | No | Yes (fair=true constructor arg) |
| Interview value | High — shows you understand monitors | Good for discussion |

---

## Class skeleton (say this before writing)

> "I'll use a ring buffer backed by an `Object[]`, with `head`, `tail`, and `size`. The class itself is the monitor. `put` loops on `wait()` while full and calls `notifyAll()` after inserting. `take` loops on `wait()` while empty and calls `notifyAll()` after removing. I'll use `while` not `if` to handle spurious wakeups and the case where multiple threads are notified."

---

## Complexity

| Operation | Time | Space |
|---|---|---|
| `put` / `take` (uncontended) | O(1) | — |
| `put` / `take` (contended) | O(1) amortized | — |
| Overall space | — | O(capacity) |

---

## Edge cases to mention

- **put when full** → thread blocks until a consumer calls `take`.
- **take when empty** → thread blocks until a producer calls `put`.
- **Multiple producers and consumers** → `notifyAll` ensures all waiters re-check and one eligible thread proceeds.
- **Capacity 1** → buffer oscillates between full and empty; correctness depends on the `while` loop.
- **InterruptedException** → propagated to the caller; thread's interrupt flag is set.

---

## Follow-up questions the interviewer may ask

- **Use `ReentrantLock` with two conditions.** Separate `notFull` and `notEmpty` conditions so producers only wake consumers and vice versa.
- **Make it generic.** Replace `Object[]` with `T[]` (cast through `Object`).
- **Timeout version.** Use `poll(timeout, unit)` or `offer(value, timeout, unit)` from `BlockingQueue`.
- **What if a thread is interrupted while waiting?** Re-throw `InterruptedException` to let the caller decide. Never swallow it silently.
- **Why not `LinkedList` as the buffer?** An array ring buffer has better cache locality. If unbounded capacity is needed, `LinkedBlockingQueue` is the library answer.
