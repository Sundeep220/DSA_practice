# 03. Time Based Key-Value Store

**Tier:** 1 (Hash tables and arrays)
**Confluent tag:** Yes
**Link:** https://leetcode.com/problems/time-based-key-value-store/

## Problem

Implement `set(key, value, timestamp)` and `get(key, timestamp)`. `get` returns the value whose timestamp is the largest value less than or equal to the query timestamp, or an empty string if none exists. Timestamps for each key are strictly increasing.

## Intuition

Because each key's timestamps arrive in increasing order, storing each key's entries in a list keeps that list sorted with no extra work. A `get` then means "find the rightmost entry with timestamp <= query". That is a binary search for the upper bound.

## Approach

1. Store `Map<String, List<TimestampedValue>>`.
2. `set` appends `(timestamp, value)` to that key's list.
3. `get` returns `""` if the key has no entries.
4. Otherwise, binary search. When `mid.timestamp <= query`, record `mid` as the best answer and search right. Otherwise search left.

## Complexity

- **`set`:** O(1) amortised, from appending to an `ArrayList`.
- **`get`:** O(log n), where n is the number of entries for that key.
- **Space:** O(total entries).

## Edge cases

- Query earlier than the first timestamp: returns `""`.
- Key never set: returns `""`.
- Query later than the last timestamp: returns the last value.

## Code

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeBasedKeyValueStore {

    private static final class TimestampedValue {
        final int timestamp;
        final String value;

        TimestampedValue(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private final Map<String, List<TimestampedValue>> store = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new ArrayList<>())
             .add(new TimestampedValue(timestamp, value));
    }

    public String get(String key, int timestamp) {
        List<TimestampedValue> entries = store.get(key);

        if (entries == null || entries.isEmpty()) {
            return "";
        }

        int low = 0;
        int high = entries.size() - 1;
        String bestValue = "";

        while (low <= high) {
            int mid = low + (high - low) / 2;
            TimestampedValue midEntry = entries.get(mid);

            if (midEntry.timestamp <= timestamp) {
                bestValue = midEntry.value;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return bestValue;
    }
}
```

## Function explanations

Runnable file: `TimeBasedKeyValueStore.java` (includes a `main` demo).

### `TimestampedValue`

- **Pairs timestamp with value:** `get` needs the timestamp for the binary search and the value to return. Keeping both in one object keeps them together in the list.
- **`final` fields:** entries never change after creation, and `final` prevents accidental edits.

### `set`

- **`computeIfAbsent`:** creates the list only the first time a key appears, in one call instead of a separate `containsKey` check.
- **`add` at the end:** timestamps arrive in increasing order, so appending keeps the list sorted. Inserting in the middle would cost O(n) and is not needed.

### `get`

- **Empty check returns `""`:** an unknown key and a key with no entries both mean "no value", so they share one return.
- **`mid = low + (high - low) / 2`:** avoids the overflow that `(low + high) / 2` can cause with large indices.
- **Move right when `midEntry.timestamp <= timestamp`:** the entry is valid, but a later valid entry may also exist. Searching right finds the latest one.
- **Move left otherwise:** the entry is too late, so every entry to its right is also too late.
- **`bestValue` is kept:** the loop ends when `low > high`, not on a match, so the last valid candidate is saved as the loop runs.

## Interview one-liner

"Timestamps arrive in order, so each key's list is already sorted. For `get`, I binary search for the rightmost timestamp at or before the query. That's O(log n) per get and O(1) amortised per set."

## Variant: timestamps arrive out of order

**Change:** `set` can receive any timestamp, so the per-key list is no longer sorted and binary search on a list is invalid. A `TreeMap<Integer, String>` per key keeps timestamps sorted on insert. `floorEntry(timestamp)` returns the greatest key less than or equal to the query in O(log n).

## Approach

1. Store `Map<String, TreeMap<Integer, String>>`.
2. `set` puts `timestamp -> value` into that key's `TreeMap`. Setting an existing timestamp overwrites it (last write wins).
3. `get` returns `""` if the key is unknown.
4. Otherwise, `floorEntry(timestamp)`. Return its value, or `""` if there is no entry at or before the query.

## Complexity

- **`set`:** O(log n) per key.
- **`get`:** O(log n) per key.
- **Space:** O(total entries).

## Edge cases

- Timestamps set out of order, such as `5`, then `1`, then `3`: `get(4)` returns the value at `3`.
- Same timestamp set twice: the second value replaces the first.
- Query earlier than all timestamps: returns `""`.

## Code

```java
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeBasedKeyValueStoreUnordered {

    private final Map<String, TreeMap<Integer, String>> store = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer, String> entries = store.get(key);

        if (entries == null) {
            return "";
        }

        Map.Entry<Integer, String> floor = entries.floorEntry(timestamp);
        return floor == null ? "" : floor.getValue();
    }
}
```

## Function explanations (variant)

Runnable file: `TimeBasedKeyValueStoreUnordered.java` (includes a `main` demo with out-of-order timestamps).

### `set`

- **`TreeMap` per key:** when timestamps can arrive out of order, a list would need sorting or O(n) insertion. A `TreeMap` keeps keys sorted on insert in O(log n).
- **`put` overwrites:** the same timestamp replaces the old value, which matches the "last write wins" rule in the approach.

### `get`

- **Null check:** an unknown key has no map, so return `""` before touching it.
- **`floorEntry(timestamp)`:** returns the greatest key at or before the query. It replaces the hand-written binary search from the ordered version.
- **Ternary for empty result:** `floorEntry` returns `null` when every stored timestamp is later than the query.

## Interview one-liner (variant)

"If timestamps can arrive out of order, I keep each key in a `TreeMap` so it stays sorted. `floorEntry` gives the latest value at or before the query in O(log n). The ordered-input version can be faster, since appending to a list is O(1), but this one handles any order."
