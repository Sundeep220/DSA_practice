# 7. Rate Limiter

**Category:** Machine coding / Design

---

## Problem

Implement a rate limiter that allows at most `maxRequests` requests per user per time window. Support:

- `boolean allowRequest(String userId)` — `true` if the request is within the limit, `false` if rate-limited.

---

## What the interviewer is really testing

1. Do you know the four main algorithms and when to use each?
2. Do you apply the **Strategy pattern** to keep algorithms swappable?
3. Do you use `ConcurrentHashMap` + `synchronized(perUserObject)` for thread safety?
4. Do you inject `Clock` instead of calling `System.currentTimeMillis()` directly?
5. Can you reason about the boundary burst problem and memory cost out loud?

---

## Design choices for an interview

### Choice 1: Strategy pattern — keeps algorithms swappable

Define one interface:

```java
interface RateLimitStrategy {
    boolean allowRequest(String userId);
}
```

The `RateLimiter` context takes any `RateLimitStrategy` at construction and delegates. You can swap `FixedWindowRateLimiter` for `TokenBucketRateLimiter` without changing the caller. Say this out loud:

> "I'll define a `RateLimitStrategy` interface. Each algorithm is a separate class. The `RateLimiter` just delegates — the algorithm is swappable without touching it."

### Choice 2: Inject `Clock` instead of calling `System.currentTimeMillis()` directly

```java
public FixedWindowRateLimiter(int maxRequests, long windowSizeMillis, Clock clock)
```

- **Production:** pass `Clock.systemUTC()`
- **Tests:** pass a `MutableClock` and call `clock.set(t)` to control time deterministically without sleeping

### Choice 3: `ConcurrentHashMap` + `synchronized(perUserObject)`

- `ConcurrentHashMap` avoids locking the whole map on lookup.
- `computeIfAbsent` creates per-user state atomically.
- `synchronized(window/bucket)` locks only that user's object — other users are unblocked.

This is the standard interview answer for per-user thread safety. Say:

> "I lock per user, not globally. `ConcurrentHashMap` gives me safe lookup; then I synchronize on the user's own state object."

### Choice 4: Which algorithm to start with

Start with **Fixed Window** — it is the simplest. Then proactively say:

> "Fixed Window has a boundary burst flaw — a user can hit 2× the limit across a window edge. Sliding Window fixes this at the cost of more memory. Token Bucket is better for APIs that allow controlled bursts. Leaky Bucket is best when downstream needs a steady rate."

---

## Algorithm 1: Fixed Window Counter

### How it works

Divide time into fixed buckets of `windowSizeMillis`. Each user gets a counter. Reset on a new bucket.

```
windowSizeMillis = 10s, maxRequests = 3

[0ms ──── 10000ms)    [10000ms ──── 20000ms)
   count = 3               count = 0
```

### State per user

```java
class Window {
    int requestCount;
    long windowStartTime;
}
```

### Algorithm

```
if now - windowStartTime >= windowSize:
    reset windowStartTime = now, requestCount = 0
if requestCount >= maxRequests:
    deny
else:
    requestCount++, allow
```

### Complexity

| | Time | Space |
|---|---|---|
| `allowRequest` | O(1) | O(1) per user |

### Pros
- Simplest to implement — just a counter and a start time.
- O(1) time and memory.
- Easy to distribute: use Redis `INCR` + `EXPIRE`.

### Cons
- **Boundary burst flaw:** a user can send `maxRequests` at t=9999ms and another `maxRequests` at t=10000ms — that is `2×limit` in milliseconds.

```
Window 0 [0-10s):   req at 8s, 9s, 9.5s → 3 allowed
Window 1 [10-20s):  req at 10s, 11s, 12s → 3 allowed again
Total in ~4 seconds: 6 = 2×limit
```

### When to use
- Daily/monthly API quotas where a small boundary inaccuracy is acceptable.
- Simple login attempt limits (e.g. 5 attempts per 15 minutes).
- Large-scale distributed counters where Redis atomicity is needed.

---

## Algorithm 2: Sliding Window Log

### How it works

Store the exact timestamp of every request in a deque. On each request, evict timestamps older than `now - windowSize`. Count what remains.

```
windowSizeMillis = 10s, maxRequests = 3
log: [1000, 3000, 5000]

new request at t=7000:
  cutoff = 7000 - 10000 = -3000 → nothing to evict
  size = 3 >= 3 → DENY

new request at t=12000:
  cutoff = 12000 - 10000 = 2000 → evict 1000
  log: [3000, 5000]
  size = 2 < 3 → add 12000, ALLOW
```

### State per user

```java
class UserWindow {
    Deque<Long> requestTimes;
}
```

### Algorithm

```
cutoff = now - windowSize
evict front entries where entry < cutoff
if size >= maxRequests: deny
else: add now, allow
```

### Complexity

| | Time | Space |
|---|---|---|
| `allowRequest` | O(maxRequests) worst case (eviction) | O(maxRequests) per user |

### Pros
- Exact rolling-window enforcement — no boundary burst.
- Simple to reason about: "how many requests in the last N ms?"

### Cons
- Memory grows with `maxRequests` — storing every timestamp.
- Not practical when `maxRequests` is very large (e.g. 100,000/min).
- Harder to distribute: requires sorted sets in Redis.

### When to use
- Security-sensitive operations: OTP generation, password reset, login attempts.
- Strict "no more than N in any rolling window" enforcement where `maxRequests` is small.

---

## Algorithm 3: Token Bucket

### How it works

Each user has a bucket with up to `capacity` tokens. Tokens refill at `refillRatePerSecond`. Each request consumes one token. If the bucket is empty, deny.

```
capacity = 3, refillRate = 1 token/sec

t=0ms:    bucket=3 → req → bucket=2, ALLOW
t=0ms:    bucket=2 → req → bucket=1, ALLOW
t=0ms:    bucket=1 → req → bucket=0, ALLOW
t=0ms:    bucket=0 → DENY
t=2000ms: elapsed=2s, refill 2 tokens → bucket=2
t=2000ms: bucket=2 → req → bucket=1, ALLOW
```

### State per user

```java
class Bucket {
    double tokens;
    long lastRefillTime;
}
```

### Algorithm (lazy refill — no background thread)

```
elapsed = now - lastRefillTime
tokens = min(capacity, tokens + elapsed * refillRatePerMillis)
lastRefillTime = now
if tokens < 1: deny
else: tokens--, allow
```

### Complexity

| | Time | Space |
|---|---|---|
| `allowRequest` | O(1) | O(1) per user |

### Pros
- Allows **controlled bursts**: idle users accumulate tokens up to `capacity` and can burst that many requests instantly.
- Two separate knobs: `capacity` (burst size) and `refillRate` (long-term throughput).
- O(1) time and memory. Easy to reason about.

### Cons
- Two parameters to tune (`capacity` and `refillRate`) — slightly more to explain.
- Burst may still overwhelm downstream if `capacity` is set too high.

### When to use
- **General-purpose API rate limiting** — the most common production choice (AWS API Gateway, Stripe).
- When short bursts from legitimate users should be allowed (e.g. app startup, batch jobs).
- When you want to separate burst capacity from long-term throughput.

---

## Algorithm 4: Leaky Bucket

### How it works

Requests fill a bucket (`waterLevel`). The bucket drains at a fixed `leakRate`. If the bucket is full, deny.

```
capacity = 3, leakRate = 1 req/sec

t=0ms:    level=0 → req → level=1, ALLOW
t=0ms:    level=1 → req → level=2, ALLOW
t=0ms:    level=2 → req → level=3=capacity, ALLOW
t=0ms:    level=3 → DENY (full)
t=1000ms: elapsed=1s, leak 1 → level=2
t=1000ms: level=2 → req → level=3, ALLOW
```

### State per user

```java
class Bucket {
    double waterLevel;
    long lastLeakTime;
}
```

### Algorithm (lazy drain — no background thread)

```
elapsed = now - lastLeakTime
waterLevel = max(0, waterLevel - elapsed * leakRatePerMillis)
lastLeakTime = now
if waterLevel >= capacity: deny
else: waterLevel++, allow
```

### Complexity

| | Time | Space |
|---|---|---|
| `allowRequest` | O(1) | O(1) per user |

### Pros
- **Smooth, constant output rate** — even if 100 requests arrive at once, they are processed at `leakRate`.
- Protects downstream systems from bursts.

### Cons
- No burst support — even a legitimate sudden spike is throttled.
- Semantically different from the others: it shapes traffic rather than just rejecting.

### When to use
- When the downstream system needs a **steady, predictable rate** (e.g. payment processor, SMS gateway, database writer).
- Network packet shaping.
- Message queues where you want to process at exactly N messages/sec.

---

## Algorithm comparison

| Algorithm | Burst allowed | Memory per user | Time | Best for |
|---|---|---|---|---|
| Fixed Window | Yes (boundary burst) | O(1) | O(1) | Simple quotas, distributed counters |
| Sliding Window Log | No | O(maxRequests) | O(maxRequests) | Exact rolling-window, security ops |
| Token Bucket | Yes (controlled) | O(1) | O(1) | General API limiting, burst-tolerant |
| Leaky Bucket | No | O(1) | O(1) | Smooth downstream rate, traffic shaping |

---

## Class skeleton (say this before writing)

> "I'll define a `RateLimitStrategy` interface. `RateLimiter` is the context — it takes the strategy and delegates. Each algorithm is a separate class. All use `ConcurrentHashMap` for per-user state and `synchronized` on the per-user object. `Clock` is injected so tests can control time without sleeping."

---

## Edge cases to mention

- First request from a new user → always allowed (fresh state).
- Exactly `maxRequests` in a window → all allowed, next denied.
- Boundary burst (fixed window only) → up to `2×maxRequests` possible across two adjacent windows.
- Idle user with token bucket → tokens accumulate to `capacity`, then burst.
- All requests at t=0 with leaky bucket → fills up instantly, subsequent ones denied until drained.

---

## Follow-up questions

- **Distributed rate limiter.** Use Redis `INCR`+`EXPIRE` for fixed window; sorted sets (`ZRANGEBYSCORE`) for sliding window; Lua scripts for token bucket atomicity.
- **Cleanup of inactive users.** Background sweep or `LinkedHashMap` with access-order eviction (similar to LRU).
- **Per-IP vs per-user vs global.** Only the key in the map changes; the algorithm stays the same.
- **Sliding Window Counter.** A memory-efficient approximation combining two fixed windows — `prevCount × overlap% + currCount`. Sits between Fixed Window and Sliding Window Log in accuracy vs memory.
