# LRU Cache: Thread-Safety Options

## 1. Context

The current `LRUCache` (`LRUCache.java`) is single-threaded. Its shared state is:

- **`nodeMap`**: a `HashMap` that is not safe for concurrent access.
- **The doubly linked list** (`head`, `tail`, and node `prev`/`next` pointers).
- **The size check** in `put`, which must be atomic with the insert and eviction it triggers.

Two properties make this harder than a typical cache:

- **`get` is a write.** It moves the node to the front, so every `get` mutates the list. A read-write lock gives no benefit, because readers still need exclusive access.
- **Eviction spans several steps.** Unlinking the tail, updating its predecessor, and removing the map entry must happen as one unit. Otherwise a thread can observe a list and a map that disagree.

Requirements to evaluate against:

- Correctness: `size <= capacity`, map and list always agree, no lost updates.
- Time complexity per operation.
- Code complexity: how easy it is to prove correct in an interview.
- Throughput under contention.

## 2. Approaches

### 2.1 Single global lock (`synchronized` or `ReentrantLock`)

Guard `get`, `put`, and `printCache` with the same lock.

- **Time per op:** O(1) under the lock.
- **Throughput:** operations are serialized, so the cache is a single bottleneck.
- **Code complexity:** very low. Three `synchronized` keywords or one lock.
- **Correctness risk:** very low. Every invariant is protected by one lock.
- **Notes:** `printCache` must return a copy, not a live view, or readers can see a half-updated map.

### 2.2 Read-write lock (`ReentrantReadWriteLock`)

Allow concurrent readers and exclusive writers.

- **Time per op:** O(1).
- **Why it fails here:** `get` reorders the list, so it is a writer. Using the read lock for `get` would race on `moveNodeToFront`. Using the write lock for `get` makes it equivalent to 2.1, with more overhead.
- **Verdict:** only useful if `get` does not update recency, which breaks LRU semantics.

### 2.3 Synchronized `LinkedHashMap` (`accessOrder = true`)

Use `LinkedHashMap` with `accessOrder` and `removeEldestEntry`, wrapped with `Collections.synchronizedMap` or guarded by a lock.

- **Time per op:** O(1) average.
- **Throughput:** same as 2.1, since the wrapper locks the whole map.
- **Code complexity:** lowest of all options (about 10 lines).
- **Correctness risk:** low for the map and list, but you rely on the library's internal ordering and do not show the list logic.
- **Notes:** the synchronized wrapper does not lock across compound operations (`get` then `put`), so you still need an external lock for multi-step logic.
- **Verdict:** excellent production shortcut. Weak in an interview, where the manual list is expected.

### 2.4 `ConcurrentHashMap` for the map plus a lock for the list

Replace `HashMap` with `ConcurrentHashMap` and use a separate lock for list operations.

- **Time per op:** O(1).
- **Why it fails here:** the map is not the bottleneck. Every `get` and `put` still takes the list lock to reorder, so the lock is still global. The map's concurrency is wasted.
- **Correctness risk:** higher. Two locks or two data structures must stay consistent: a key can be in the map but not yet in the list, or the reverse.
- **Verdict:** rejected. More complexity with no throughput gain.

### 2.5 Lock striping or sharding

Split the key space across N independent LRU caches. Each shard is a complete `LRUCache` with its own lock and its own share of the capacity. A key always maps to the same shard, so operations on different shards never contend.

**How it works**

1. Compute `shardIndex = Math.floorMod(hash(key), N)`. Use `floorMod`, not `%`, so negative hashes stay in range.
2. Route `get` and `put` to `shards[shardIndex]`.
3. Each shard runs the existing `synchronized` logic, so no new locking code is needed inside a shard.

**Capacity distribution**

Split `capacity` so the shard capacities sum exactly to the total:

- `base = capacity / N` goes to every shard.
- `remainder = capacity % N` shards get one extra slot, so the first `remainder` shards have `base + 1`.

Require `capacity >= N`. If a shard had capacity 0, every key routed to it would be evicted immediately.

**Code sketch**

Reuses the single-threaded `LRUCache` class from `LRUCache.java` as a building block:

```java
public class ShardedLRUCache {
    private final LRUCache[] shards;

    public ShardedLRUCache(int capacity, int shardCount){
        if(shardCount <= 0 || capacity < shardCount){
            throw new IllegalArgumentException("capacity must be >= shardCount > 0");
        }
        shards = new LRUCache[shardCount];
        int base = capacity / shardCount;
        int remainder = capacity % shardCount;
        for(int i = 0; i < shardCount; i++){
            shards[i] = new LRUCache(base + (i < remainder ? 1 : 0));
        }
    }

    private LRUCache shardFor(int key){
        return shards[Math.floorMod(key, shards.length)];
    }

    public int get(int key){
        return shardFor(key).get(key);
    }

    public void put(int key, int val){
        shardFor(key).put(key, val);
    }
}
```

For generic keys, replace `key` in `shardFor` with `Objects.hashCode(key)`. `Objects.hashCode` returns `0` for `null`, so `null` keys go to shard 0.

**Worked example of the approximation**

Capacity 4, 2 shards, so each shard holds 2 entries:

- Shard 0 receives keys `0`, `2`, `4`.
- Shard 1 is empty.
- Putting key `4` evicts key `0` from shard 0, even though the cache holds only 2 entries overall and has room for 2 more.

A single global LRU would have kept `0` in that situation, because the global LRU order is determined by recency across all keys.

**Complexity**

- **Time per op:** O(1) for the hash, plus the O(1) of the shard operation.
- **Space:** O(capacity) in total, plus N lock objects and N map instances of overhead.

**Testing**

- Each shard's invariants still hold independently, so the stress check from section 7 applies per shard.
- Total entries across shards must never exceed `capacity`.
- To measure the approximation, compare hit rates against the global LRU on the same access trace.

**Tradeoffs**

- **Gain:** threads working on different shards do not block each other.
- **Cost:** global recency order is lost, and a hot shard can evict while others have spare room.
- **Skew:** if a few keys dominate traffic, one shard becomes the bottleneck. Choose a good hash and monitor per-shard load.
- **Code complexity:** medium. Reuse the single-threaded cache as a building block.
- **Verdict:** the right upgrade when profiling shows lock contention. Requires stating the approximation explicitly.

### 2.6 Lazy, buffered reordering (Caffeine-style)

`get` records the access in a per-thread or striped buffer and tries a non-blocking lock. Reordering is applied in batches by whichever thread wins the lock.

- **Time per op:** O(1) amortized.
- **Throughput:** reads avoid an exclusive lock most of the time.
- **Code complexity:** high. Buffer draining, stale reads, and eviction timing all need careful design.
- **Correctness risk:** medium to high. Recency is slightly stale by design.
- **Verdict:** a production-grade design. Too complex for an interview unless you are asked about it.

### 2.7 Lock-free doubly linked list (CAS / `AtomicReference`)

Use compare-and-swap on each `prev`/`next` pointer, following techniques such as the Harris linked list.

- **Time per op:** O(1) expected.
- **Code complexity:** very high. Doubly linked lock-free lists are notoriously hard to get right, especially with deletions.
- **Correctness risk:** high. Subtle ABA and memory-ordering bugs are likely.
- **Verdict:** rejected. Not practical to implement or verify in a round.

### 2.8 Copy-on-write or immutable snapshots

Create a new cache state on every write.

- **Time per op:** O(n) per write, because the state is copied.
- **Verdict:** rejected. It breaks the O(1) requirement.

### 2.9 Single-threaded executor (actor model)

Route all operations through one thread via a queue.

- **Time per op:** O(1) plus queue overhead, with added latency.
- **Code complexity:** medium. Futures or callbacks for results.
- **Correctness risk:** low, since one thread owns the state.
- **Verdict:** a valid design when callers are async. For a synchronous `get`/`put` API, it adds latency and an extra moving part.

## 3. Comparison

| Approach | Time per op | Throughput | Code complexity | Correctness risk | Verdict |
|---|---|---|---|---|---|
| 2.1 Global lock | O(1) | Low under contention | Very low | Very low | **Chosen** |
| 2.2 Read-write lock | O(1) | No gain (`get` writes) | Low | Medium | Rejected |
| 2.3 Synchronized `LinkedHashMap` | O(1) avg | Low under contention | Lowest | Low | Production shortcut |
| 2.4 `ConcurrentHashMap` + list lock | O(1) | Low under contention | High | High | Rejected |
| 2.5 Sharded LRU | O(1) | High (N-way) | Medium | Low per shard, approximate LRU | Upgrade path |
| 2.6 Lazy buffered reordering | O(1) amortized | High for reads | High | Medium to high | Production, too complex here |
| 2.7 Lock-free list | O(1) expected | High | Very high | High | Rejected |
| 2.8 Copy-on-write | O(n) per write | Low | Medium | Low | Rejected |
| 2.9 Single-thread executor | O(1) + queue | Medium | Medium | Low | Only for async APIs |

## 4. Decision

**Use a single global lock (2.1) for this implementation.**

Reasons:

- **Time complexity is unchanged.** Every operation stays O(1) inside the critical section.
- **Critical sections are tiny.** A `get` or `put` only relinks a few pointers and updates one map entry, so lock hold time is very short.
- **Correctness is easy to argue.** One lock protects the map, the list, and the size check together, so the invariants hold trivially.
- **Code complexity is minimal.** It fits an interview and is easy to explain line by line.
- **The upgrade path is clear.** If profiling shows contention, move to sharding (2.5). That is the next step to discuss, not the first.

Rejected for this round: read-write locks (because `get` writes), `ConcurrentHashMap` plus a separate list lock (two sources of truth), and lock-free lists (too risky to verify).

## 5. Implementation notes for `LRUCache.java`

- Make `get` and `put` `synchronized`, or guard them with one `ReentrantLock` held in a `try`/`finally`.
- Make `printCache` `synchronized` and return a copy (for example `new HashMap<>(nodeMap)`), not a view over the live map.
- Keep the lock private to the class; do not expose it.
- Nothing else needs to change. The helpers remain private and are only called while the lock is held.

## 6. Interview talking points

- State that the original design is single-threaded and list the shared state.
- Explain why a read-write lock doesn't help, since `get` mutates recency.
- Justify the global lock by correctness and simplicity, and name its throughput limit.
- Offer sharding as the scaling step, and state that it changes LRU to approximate LRU.
- Mention lazy buffered reordering as the production-grade design, without implementing it.

## 7. Verification plan

- **Single-threaded checks:** keep the existing `main` assertions.
- **Stress test:** run several threads calling `put` and `get` on random keys with a small capacity, then check:
  - `nodeMap.size() <= capacity` at all times;
  - the list length from `head` to `tail` equals `nodeMap.size()`;
  - walking the list backward matches walking it forward;
  - no `NullPointerException` or `AssertionError` occurs.
- **Run with:** `javac` and `java` as before, using a temp output directory.

## 8. Interview follow-up answers

### Invariants to restate first

- **Capacity:** `size <= capacity` at every point a lock is released.
- **Consistency:** the map and the list describe the same set of nodes. Every map value is in the list, every list node is in the map, and `prev`/`next` agree in both directions.
- **No lost updates:** each `put` or `get` takes effect exactly once, and its result reflects all completed operations before it.

### Concurrency follow-ups

**Why a global lock?**

- **Bottleneck:** every `get` and `put` mutates the shared list, so they must be serialized. The lock is the point where that serialization happens.
- **Why it is acceptable:** the critical section is a few pointer updates and one map operation, so the lock is held for nanoseconds to microseconds.
- **Alternatives:**
  - *Sharding* gives N-way parallelism but makes LRU approximate.
  - *Lazy buffered reordering* lets reads avoid the exclusive lock, but recency is slightly stale.
  - *`ConcurrentHashMap` with a separate list lock* keeps the global list lock, so the map's concurrency is wasted, and the two structures can drift apart.
- **Answer:** correctness first, then measure. Move to sharding only when profiling shows lock contention.

**Sharded version: what happens to LRU order and capacity?**

- **Order:** recency is only tracked within a shard. A key can be evicted while another shard has room, so eviction is not global LRU.
- **Capacity:** the shard capacities sum to the total, but they are fixed. A hot shard cannot borrow slots from an idle one without coordination.
- **How to describe it:** "The cache is per-shard LRU. Its hit rate approaches global LRU when keys hash uniformly and each shard's capacity is large compared with the hot working set. The approximation is a property of the partitioning, not a bug."
- **Measurement:** replay the same access trace against both designs and compare hit rates.

**Read-heavy workload: can you use a read-write lock?**

- **No, not for `get`.** `get` moves the node to the front, which writes `head`, `tail`, and neighbour pointers. Two threads holding the read lock would run `moveNodeToFront` at the same time and corrupt the list.
- **What you can do:**
  - Use a read lock only for `containsKey`-style checks, then take the write lock to reorder. A hit still needs the write lock, so there is no gain for the common case.
  - Switch to an algorithm where a hit is a single atomic write, such as CLOCK, which sets a reference bit instead of moving a node. Reads then avoid the exclusive lock. The trade-off is weaker LRU behaviour.
  - Use lazy buffered reordering (2.6), which records the access and applies it later.

**Async API: callbacks, and calling back into the cache while the lock is held**

- **Where callbacks run:** a `CompletableFuture` runs its dependent stages on whichever thread completes it. If the future is completed inside the `synchronized` block, callbacks run while the lock is held.
- **Rule:** never run user code while holding the lock. Compute the result under the lock, release it, then complete the future.
- **Re-entrancy:** Java monitors are re-entrant, so a callback on the same thread can re-enter `get` without deadlocking. But it would run in the middle of a `put` that is still relinking nodes, which can corrupt state.
- **Cross-thread deadlock:** if a callback blocks waiting for another thread that needs the lock, and the lock holder is waiting on the callback, the cache deadlocks.
- **Answer:** keep callbacks outside the critical section, and state that rule in the design.

**Fairness and starvation: can a thread starve? Would you use a fair lock?**

- **Yes, in theory:** `synchronized` and the default `ReentrantLock` are non-fair. A thread that keeps arriving can barge ahead of one that has been waiting, so that thread can starve.
- **Fair lock:** `new ReentrantLock(true)` grants the lock in FIFO order and prevents starvation, but each handoff costs more, so throughput drops.
- **Answer:** keep the default lock unless tail latency is a stated requirement. If it is, use a fair lock and measure the throughput cost.

### Behaviour and API follow-ups

**Generics: `LRUCache<K, V>`. What changes for null keys and values?**

- **Types:** `Node<K, V>`, `Map<K, Node<K, V>>`. The node still stores the key so eviction can remove the map entry.
- **Null keys:** `HashMap` allows one `null` key, but `ConcurrentHashMap` does not. Decide explicitly. The simplest choice is `Objects.requireNonNull` on both key and value.
- **Null values:** `get` returns `-1` for a missing key in the current design. With generics there is no sentinel, so `get` must return `null` for a miss, which is ambiguous if `null` is a valid value. Options:
  - Reject `null` values, so `null` always means a miss.
  - Return `Optional<V>`, which is explicit but allocates.
  - Use `containsKey` plus `get` under the same lock.
- **Answer:** reject nulls for simplicity, and state the reason.

**Eviction callback: notify a listener. Should it run inside the lock?**

- **No.** A slow listener would block every operation, and a listener that calls the cache would re-enter it mid-operation.
- **Design:** under the lock, remove the entry and record it in a local variable. After releasing the lock, invoke the listener with the evicted key and value.
- **Trade-offs:**
  - Notifications may arrive out of order across threads.
  - By the time the listener runs, the entry is already gone from the cache.
  - Exceptions from the listener must be caught, so a bad listener cannot break the cache.
- **Distinguish:** eviction (capacity), expiry (TTL), and explicit removal are different events. A listener may need a cause parameter.

**Bulk operations: `putAll`, `clear`, `remove(key)`. How do they affect size and the list?**

- **`putAll`:** each entry is a `put`. Under a single lock acquisition the batch is atomic. If the batch is larger than capacity, earlier entries are evicted by later ones, so the result is the last `capacity` entries in insertion order.
- **`remove(key)`:** unlink the node from the list, remove the map entry, and decrement size. No eviction happens. Return the removed value or a miss indicator.
- **`clear()`:** reset `head.next = tail` and `tail.prev = head`, then clear the map. The unlinked nodes are garbage-collected. Size becomes 0.
- **Invariant after each:** `map.size()` equals the number of nodes between the sentinels.

**Iteration: from most to least recently used. What if the cache changes during iteration?**

- **Walk:** start at `head.next` and follow `next` until `tail`.
- **Problem:** `get` reorders the list, so even reads change the iteration order. Iterating without the lock races with `put`, `get`, and eviction. A node can be unlinked while the iterator holds it, and its `next` may point at a stale node.
- **Options:**
  - *Snapshot under the lock:* copy keys and values into a list, release the lock, and iterate the copy. Consistent, but O(n) memory and time.
  - *Weakly consistent iterator:* reflects some state of the cache, may skip or repeat entries, never throws. Harder to get right.
  - *Fail-fast iterator:* detect a modification count and throw `ConcurrentModificationException`. Simple, but it still needs the lock to read the count.
- **Answer:** snapshot is the safest choice in a round. Say why, and offer the weakly consistent variant if memory matters.

**Capacity by weight: evict until the total weight fits.**

- **State:** keep `totalWeight` as a field, updated under the lock. Invariant: `totalWeight == sum(weights)` and `totalWeight <= maxWeight`.
- **`put` of a new key:** add its weight, then evict from the tail while `totalWeight > maxWeight`.
- **`put` of an existing key:** subtract the old weight, add the new one, then evict as above.
- **Edge case:** an entry heavier than `maxWeight` can never fit. Reject it (throw or return false) before evicting anything, so the cache is not emptied for nothing.
- **Cost:** one `put` may evict many entries. Amortized it is still O(1) per entry evicted.

### Time-based follow-ups

**TTL expiry: lazy on access, or a background sweeper?**

- **Lazy:** check the expiry timestamp in `get` and remove the entry if it has expired. No extra thread, and the cost is paid by the caller. Downside: expired entries keep memory until someone reads them.
- **Background sweeper:** a `ScheduledExecutorService` periodically takes the lock and removes expired entries. Bounded memory, but it adds a thread and lock contention on each sweep.
- **Eviction interaction:** when full, `put` should first remove expired entries before evicting the LRU live entry. Otherwise a live entry is evicted while a dead one occupies a slot.
- **Answer:** lazy removal plus a periodic sweep, and state the trade-off. Use a monotonic clock (`System.nanoTime`) for expiry, not wall-clock time, which can jump.

**Clock injection: how do you make expiry deterministic in tests?**

- Inject a `Clock` or a `LongSupplier` for "now". Tests pass a mutable fake clock and advance it explicitly, instead of calling `Thread.sleep`.
- This matches the `Clock` injection in `TtlKeyValueStore` from the addendum.
- Production uses the real monotonic source, and tests use a fake one. Name the difference: `Clock.systemUTC()` is wall-clock, and expiry should use a monotonic source.

### Scale and production follow-ups

**Memory: each node has four references plus the map entry. How do you reduce overhead?**

- **Current cost:** roughly 80 to 90 bytes per entry on a 64-bit JVM with compressed references. That covers the node object, the `HashMap` entry, and boxed `Integer` keys and values. Measure with a profiler before optimizing.
- **Reductions:**
  - Use primitive arrays with indices instead of node objects: `int[] prev`, `int[] next`, `int[] keys`, `int[] vals`. This removes object headers and boxing.
  - Replace `HashMap<Integer, Node>` with a primitive open-addressing map from key to index, such as a library map for primitives.
  - Store the key only once.
- **Trade-off:** more code and more error-prone index handling. In an interview, mention it as the path to a compact design and keep the object version for clarity.

**Metrics: track hits, misses, and evictions. Where do the counters live?**

- **Inside the lock (recommended here):** `get` and `put` already hold the lock when they decide hit, miss, or eviction. Plain `long` fields updated there cost nothing extra. Read them under the lock too.
- **Outside the lock:** `AtomicLong` is simple but every thread writes the same cache line, which is contention. `LongAdder` spreads writes across cells and sums on read, so it is better for write-heavy counters.
- **Answer:** since the decision is already inside the critical section, use plain fields there. Use `LongAdder` only if counters must be updated outside the lock.

**Distributed cache: runs across multiple servers. What changes, and what do you give up?**

- **What changes:**
  - Partitioning: consistent hashing maps keys to nodes, and rebalancing moves keys when nodes join or leave.
  - Replication and failover: a node failure loses its entries unless replicated, and cache misses spike.
  - Network: every operation has latency and can time out, so callers need timeouts and retries.
  - Serialization: values cross the network as bytes.
  - Invalidation: updates on one node must reach copies on others, or clients see stale data.
- **What you give up:**
  - Strict global LRU order. Real systems use approximate LRU, such as Redis's sampled eviction.
  - Atomic multi-key operations across nodes.
  - Local-call latency and simple failure semantics.
- **Answer:** the single-process lock becomes network protocol and failure handling. The cache semantics weaken to eventually consistent or approximate.

**Persistence: survive restarts. What do you write, and when?**

- **Usually not needed:** a cache is rebuildable from its source of truth, so most designs accept a cold start and warm up on demand.
- **If required:**
  - *Write-ahead log:* append each `put` and `remove` before acknowledging it. Replay on restart. Durable, but every write pays an I/O cost.
  - *Periodic snapshot:* write entries from tail to head, so replaying the snapshot in order restores recency. Cheap, but recent writes can be lost.
  - *Access recency:* logging every `get` is expensive. Losing recency on restart is usually acceptable for a cache.
- **Answer:** state that a cache normally does not need durability, then offer snapshots as the cheap option.

### What a strong answer includes

- **Invariants first:** restate `size <= capacity`, map and list agreement, and no lost updates before answering any follow-up.
- **Trade-offs for every option:** name the cost of the option you reject, not only the one you pick.
- **Tests first:** the stress check from section 7, the LeetCode eviction trace for order, the capacity-1 and overwrite cases for edge handling, and a fake clock for expiry.
