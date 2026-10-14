# 09. Key-Value Store with TTL and get_average()

**Tier:** 4 (Design problems)
**Confluent tag:** Yes (reported in a phone screen)
**Link:** No direct LeetCode match. Similar: https://leetcode.com/problems/time-based-key-value-store/

## Problem

Data arrives as `(timestamp, key, value)` triples with strictly increasing timestamps. Support:

- `put(timestamp, key, value)`: store a value.
- `get(timestamp, key)`: return the latest value for the key if it has not expired. A value expires after a fixed TTL.
- `getAverage(timestamp)`: return the average of all non-expired values at that moment.

## Intuition

Timestamps arrive in order, so one global deque of entries is also sorted by time. Expired entries always sit at the front of that deque.

- **`getAverage`:** pop expired entries from the front and subtract them from a running sum. The average is `runningSum / size`.
- **`get`:** each key also has its own deque in time order. Walk backwards to the most recent entry at or before the query, then check whether it has expired.

When an entry expires, it is also the oldest entry for its key, so it can be removed from that key's deque at the same time. This keeps memory bounded by the number of live entries.

## Approach

1. `put`: append the entry to the key's deque and to the global deque, and add its value to `runningSum`.
2. `getAverage(now)`: while the global front has `now - timestamp >= ttl`, pop it, subtract its value, and pop the same entry from the front of its key's deque.
3. If the global deque is empty, throw. Otherwise return `runningSum / globalQueue.size()`.
4. `get(now, key)`: walk the key's deque from newest to oldest and find the first entry with `timestamp <= now`. Return its value if `now - timestamp < ttl`, otherwise `null`.

## Complexity

- **`put`:** O(1).
- **`getAverage`:** O(e) per call, where e is the number of entries expiring in that call. Amortised O(1).
- **`get`:** O(k), where k is the number of entries for that key walked before the first match. Binary search would need an indexable list, which this version does not use.
- **Space:** O(live entries).

## Edge cases

- TTL of zero: every entry is expired at the moment it is written, so `getAverage` throws.
- All entries expired before `getAverage`: throws `IllegalStateException`.
- Overwriting a key: both values stay in the global deque and both count toward the average, because the spec does not say an overwrite replaces the earlier value. Confirm this with the interviewer. If overwrites should replace, remove the old entry when the key is updated.
- Very large timestamps: `long` handles them, and the subtraction `now - timestamp` cannot overflow for realistic values.
- Assumes queries arrive in non-decreasing time order. Pruning in `getAverage` removes entries that a query with an earlier timestamp might still need.

## Code

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TtlKVStoreWithAverage {

    private static final class Entry {
        final long timestamp;
        final String key;
        final double value;

        Entry(long timestamp, String key, double value) {
            this.timestamp = timestamp;
            this.key = key;
            this.value = value;
        }
    }

    private final long ttlMillis;
    private final Map<String, Deque<Entry>> entriesByKey = new HashMap<>();
    private final Deque<Entry> globalQueue = new ArrayDeque<>();
    private double runningSum = 0.0;

    public TtlKVStoreWithAverage(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }

    public void put(long timestamp, String key, double value) {
        Entry entry = new Entry(timestamp, key, value);
        entriesByKey.computeIfAbsent(key, k -> new ArrayDeque<>()).addLast(entry);
        globalQueue.addLast(entry);
        runningSum += value;
    }

    public Double get(long queryTimestamp, String key) {
        Deque<Entry> keyEntries = entriesByKey.get(key);
        if (keyEntries == null) {
            return null;
        }

        Iterator<Entry> newestFirst = keyEntries.descendingIterator();
        while (newestFirst.hasNext()) {
            Entry entry = newestFirst.next();
            if (entry.timestamp <= queryTimestamp) {
                boolean notExpired = queryTimestamp - entry.timestamp < ttlMillis;
                return notExpired ? entry.value : null;
            }
        }
        return null;
    }

    public double getAverage(long queryTimestamp) {
        while (!globalQueue.isEmpty()
                && queryTimestamp - globalQueue.peekFirst().timestamp >= ttlMillis) {
            Entry expired = globalQueue.pollFirst();
            runningSum -= expired.value;
            entriesByKey.get(expired.key).pollFirst();
        }

        if (globalQueue.isEmpty()) {
            throw new IllegalStateException("No active entries, cannot compute average");
        }
        return runningSum / globalQueue.size();
    }
}
```

## Function explanations

Runnable file: `TtlKVStoreWithAverage.java` (includes a `main` demo with TTL 10).

### `Entry`

- **Holds timestamp, key, and value:** an expiring entry in the global queue must identify its key so the matching per-key queue can be updated.
- **`final` fields:** entries never change after creation.

### Fields

- **`globalQueue`:** all entries in time order, so expired entries are always at the front.
- **`entriesByKey`:** each key's entries in time order, so `get` can start from that key's newest entry.
- **`runningSum`:** the total of live values, so `getAverage` never re-adds them.

### `put`

- **Appends to both queues:** timestamps increase, so both queues stay sorted without extra work.
- **Adds to `runningSum`:** keeps the sum current for `getAverage`.

### `get`

- **`descendingIterator`:** the newest entries are at the back, so the first entry at or before the query is the most recent valid one.
- **TTL check after finding the entry:** an entry can exist but be too old. The `notExpired` check handles that case and returns `null`.
- **Returns `null` when missing:** a missing key and an expired value both mean "no value".
- **Cost:** `ArrayDeque` has no index, so binary search is not possible. The walk is O(k) for k entries of that key.

### `getAverage`

- **Pops expired entries from the front of the global queue:** these are the oldest, so they expire first.
- **Pops the same entry from the front of its key's queue:** the key's oldest entry is the one that just expired, so the two queues stay in step. This relies on both queues being in the same time order.
- **Subtracts from `runningSum`:** keeps the sum equal to the live values.
- **Throws when empty:** the average of zero values is undefined.
- **Divides by `globalQueue.size()`:** the live count, since expired entries have been removed.

## Interview one-liner

"Timestamps are in order, so the global deque is sorted and expired entries are always at the front. `getAverage` pops them and keeps a running sum, so each call is amortised O(1). `get` walks the key's own deque from the newest entry and checks the TTL."
