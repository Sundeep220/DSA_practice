# IBM Senior Software Engineer --- Machine Coding & LLD Practice Addendum

**Purpose:** Backup preparation for a live coding round described as
DSA + scenario-based coding.\
**Language:** Java 17\
**Important:** Public candidate experiences reviewed do not establish
machine coding as a standard round for every IBM Senior Software
Engineer role. Treat this as prudent backup preparation, not a confirmed
IBM question list.

## 1. What is the difference?

-   **DSA coding:** Solve a focused algorithm problem, usually in 20--40
    minutes.
-   **Scenario-based coding:** Translate a real-world situation into
    data structures and an algorithm, then explain edge cases and scale.
-   **Machine coding / low-level design (LLD):** Build a small working
    application with multiple classes, clear responsibilities,
    interfaces, state management, and testable behaviour---often in
    45--120 minutes.

Because your interviewer said "DSA and scenario-based coding," prepare
the first two most heavily. Use the machine-coding list below as
insurance.

## 2. Highest-priority machine-coding problems

  -------------------------------------------------------------------------------
  Priority          Problem                What to implement   Key concepts
  ----------------- ---------------------- ------------------- ------------------
  1                 **LRU Cache**          `get(key)`,         HashMap + doubly
                                           `put(key,value)`,   linked list, O(1)
                                           eviction at         
                                           capacity            

  2                 **In-memory key-value  `put`, `get`,       HashMap, clock
                    store with TTL**       `delete`, expiry    abstraction,
                                                               expiry semantics

  3                 **Rate Limiter**       Allow/reject        Sliding window /
                                           requests per client token bucket,
                                           within a time       timestamps
                                           window              

  4                 **Parking Lot**        Vehicle entry/exit, OOP, interfaces,
                                           spot assignment,    enums, state
                                           ticket, fee         

  5                 **Task Scheduler**     Add jobs, priority, PriorityQueue,
                                           execute next        comparator, job
                                           eligible job        state

  6                 **Producer--Consumer / Thread-safe `put`   `wait/notifyAll`
                    Bounded Queue**        and `take`, block   or
                                           when full/empty     `BlockingQueue`,
                                                               concurrency

  7                 **Elevator             Requests,           State modelling,
                    Controller**           direction, floor    queues, strategy
                                           movement and        
                                           scheduling          

  8                 **Library Management   Search books,       OOP, maps, domain
                    System**               checkout/return,    modelling
                                           due dates           

  9                 **Expense Splitter**   Add expense, split  Maps, validation,
                                           equally/by exact    clean modelling
                                           amounts, balances   

  10                **Notification         Email/SMS/push      Strategy pattern,
                    Service**              interface and retry interfaces,
                                           policy              retries

  11                **Movie Ticket         Show seats,         State,
                    Booking**              reserve,            concurrency,
                                           confirm/cancel      avoiding double
                                                               booking

  12                **File/Log             Process events,     Streaming, maps,
                    Aggregator**           group counts, query heaps, bounded
                                           top K               memory
  -------------------------------------------------------------------------------

### Links to learn and practise

-   [LeetCode --- LRU Cache](https://leetcode.com/problems/lru-cache/)
-   [LeetCode --- Design Circular
    Queue](https://leetcode.com/problems/design-circular-queue/)
-   [LeetCode --- Design Hit
    Counter](https://leetcode.com/problems/design-hit-counter/)
-   [LeetCode --- Logger Rate
    Limiter](https://leetcode.com/problems/logger-rate-limiter/)
-   [LeetCode --- Time Based Key-Value
    Store](https://leetcode.com/problems/time-based-key-value-store/)
-   [LeetCode --- Find Median from Data
    Stream](https://leetcode.com/problems/find-median-from-data-stream/)
-   [LeetCode --- Task
    Scheduler](https://leetcode.com/problems/task-scheduler/)
-   [LeetCode --- Design
    Twitter](https://leetcode.com/problems/design-twitter/)
-   [GeeksforGeeks --- Parking Lot System
    design](https://www.geeksforgeeks.org/system-design/design-parking-lot-using-object-oriented-principles/)
-   [GeeksforGeeks --- Elevator System
    design](https://www.geeksforgeeks.org/system-design/elevator-system-design/)
-   [Refactoring.Guru --- Design
    Patterns](https://refactoring.guru/design-patterns)
-   [Java BlockingQueue
    documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/BlockingQueue.html)

## 3. What a good machine-coding answer should contain

Before coding, clarify: 1. Which features are required and which can be
ignored? 2. Is this single-threaded or concurrent? 3. What are the
expected data size and performance requirements? 4. What happens for
invalid input, duplicates, missing records and failures? 5. Is
persistence/networking required, or is in-memory behaviour sufficient?

Then: - Define the core domain classes and responsibilities. - Use
interfaces where implementations may vary. - Keep business logic out of
`main`. - Implement the simplest correct version first. - Add validation
and edge cases. - Demonstrate with a small driver or unit tests. -
Explain time/space complexity and what would change for production.

Avoid overengineering: do not add Spring Boot, a database, Kafka or REST
endpoints unless the prompt requires them.

## 4. Example implementation: in-memory TTL key-value store

**Requirements:** Store string key/value pairs with an optional
time-to-live. Expired entries should not be returned. This is a good
compact scenario-based/machine-coding exercise.

``` java
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TtlKeyValueStore {
    private static final class Entry {
        private final String value;
        private final long expiresAtMillis; // Long.MAX_VALUE means no expiry

        private Entry(String value, long expiresAtMillis) {
            this.value = value;
            this.expiresAtMillis = expiresAtMillis;
        }
    }

    private final Map<String, Entry> data = new HashMap<>();
    private final Clock clock;

    public TtlKeyValueStore() {
        this(Clock.systemUTC());
    }

    // Injecting Clock makes expiry deterministic in tests.
    public TtlKeyValueStore(Clock clock) {
        this.clock = clock;
    }

    public void put(String key, String value) {
        requireKey(key);
        data.put(key, new Entry(value, Long.MAX_VALUE));
    }

    public void put(String key, String value, long ttlMillis) {
        requireKey(key);
        if (ttlMillis <= 0) {
            data.remove(key);
            return;
        }

        long now = clock.millis();
        long expiresAt = ttlMillis > Long.MAX_VALUE - now
                ? Long.MAX_VALUE : now + ttlMillis;
        data.put(key, new Entry(value, expiresAt));
    }

    public Optional<String> get(String key) {
        requireKey(key);
        Entry entry = data.get(key);
        if (entry == null) return Optional.empty();

        if (clock.millis() >= entry.expiresAtMillis) {
            data.remove(key);
            return Optional.empty();
        }
        return Optional.of(entry.value);
    }

    public boolean delete(String key) {
        requireKey(key);
        return data.remove(key) != null;
    }

    private static void requireKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Key must not be blank");
        }
    }
}
```

**Complexity:** `put`, `get`, and `delete` are O(1) average. Space is
O(n). Expired entries are removed lazily on access; a production
implementation may also need background cleanup or a delay queue. This
basic implementation is **not thread-safe**---state that clearly and
discuss synchronization or a concurrent design if asked.

### Test cases to explain

-   Get a missing key.
-   Put with no expiry.
-   Put with TTL and read before expiry.
-   Read after expiry.
-   Overwrite a key with a different TTL.
-   TTL of zero or a negative TTL.
-   Concurrent `get` and `put` if concurrency is required.

## 5. Example implementation: simple fixed-window rate limiter

**Requirements:** Allow at most `limit` requests per client per fixed
time window. This is a simple in-memory interview implementation, not a
distributed production limiter.

``` java
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

public class FixedWindowRateLimiter {
    private static final class Window {
        long startMillis;
        int count;

        Window(long startMillis) {
            this.startMillis = startMillis;
            this.count = 0;
        }
    }

    private final int limit;
    private final long windowMillis;
    private final Clock clock;
    private final Map<String, Window> windows = new HashMap<>();

    public FixedWindowRateLimiter(int limit, long windowMillis, Clock clock) {
        if (limit <= 0 || windowMillis <= 0) {
            throw new IllegalArgumentException("Limit and window must be positive");
        }
        this.limit = limit;
        this.windowMillis = windowMillis;
        this.clock = clock;
    }

    public boolean allowRequest(String clientId) {
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("clientId must not be blank");
        }

        long now = clock.millis();
        long windowStart = now - Math.floorMod(now, windowMillis);
        Window window = windows.computeIfAbsent(clientId, id -> new Window(windowStart));

        // Start a fresh window once the fixed boundary advances.
        if (window.startMillis != windowStart) {
            window.startMillis = windowStart;
            window.count = 0;
        }

        if (window.count >= limit) return false;
        window.count++;
        return true;
    }
}
```

**Complexity:** O(1) average per request, O(number of active client IDs)
space. Discuss the boundary burst problem of fixed windows, cleanup of
inactive clients, concurrency, and how a token bucket or sliding-window
approach changes behaviour.

## 6. How to approach a Parking Lot machine-coding prompt

Do not memorise a huge solution. Practise designing a minimal working
version with these responsibilities:

``` java
enum VehicleType { MOTORCYCLE, CAR, TRUCK }
enum SpotType { MOTORCYCLE, COMPACT, LARGE }

final class Vehicle {
    private final String registrationNumber;
    private final VehicleType type;
    // constructor + getters
}

final class ParkingSpot {
    private final int id;
    private final SpotType type;
    private Vehicle parkedVehicle;
    // canFit(Vehicle), park(Vehicle), removeVehicle()
}

final class ParkingTicket {
    private final String ticketId;
    private final String registrationNumber;
    private final long entryTimeMillis;
    // constructor + getters
}

interface FeeCalculator {
    long calculateFee(ParkingTicket ticket, long exitTimeMillis);
}

final class ParkingLot {
    // spot inventory, active tickets
    // park(Vehicle), unpark(ticketId), availableSpots()
}
```

Be ready to explain: - How to choose the smallest compatible spot. - How
to reject an already-parked vehicle. - How to validate ticket IDs and
prevent double exits. - How to calculate fees and represent currency
safely (for example, integer minor units). - How to make parking
allocation atomic under concurrent requests.

## 7. Two-day plan including machine-coding insurance

### Day 1

-   70% of time: DSA problems from your main workbook.
-   30% of time: implement LRU Cache and the TTL key-value store above.

### Day 2

-   50% of time: DSA and scenario-based coding, including queue and
    infinite-stream questions.
-   25% of time: implement a fixed-window rate limiter and discuss its
    limitations.
-   25% of time: design Parking Lot on paper and practise explaining
    producer-consumer / bounded queue.

If your interviewer confirms a 60-minute algorithmic session, focus back
on DSA. If they say "implement a working design," "machine coding," or
"LLD," use the machine-coding practice list.

## 8. Final priority order

Done: LRU Cache (with thread safety and TTL).

1.  Queue using linked list + unique queue variation
2.  Infinite-stream scenario and bounded queue
3.  TTL key-value store (section 4)
4.  Time-Based Key-Value Store (Problem 3)
5.  KV Store with TTL and get_average() (Problem 9)
6.  Rate limiter (section 5)
7.  Sliding window maximum and longest subarray within budget (Problems 5 and 4)
8.  Producer--Consumer
9.  Task Scheduler
10. Parking Lot (section 6)
11. Elevator or Movie Ticket Booking
12. Expense Splitter / Notification Service

**Bottom line:** Public reports support coding rounds and real-world
coding scenarios at IBM, but I did not find sufficient evidence to claim
a separate machine-coding round is standard for this role. Your stated
interview format makes DSA + scenarios the main focus; the design exercises
above, interleaved with DSA problems, are sensible insurance.

---

## 9. DSA Problem Bank — Ordered by Difficulty and Building Blocks

> Problems are grouped so each tier introduces one new concept that the
> next tier builds on. Solve them in order on your first pass, then
> random-shuffle for the second pass.
>
> **Confluent tag** marks problems explicitly reported from Confluent / IBM-Confluent interviews.

---

### TIER 1 — Foundation: Hash Tables and Arrays

---

#### Problem 1 — Two Sum

**Description:** Given an integer array `nums` and a target integer, return the indices of the two numbers that add up to the target. Each input has exactly one solution and you may not use the same element twice.

**Link:** https://leetcode.com/problems/two-sum/

**Intuition:** A brute-force double loop is O(n²). The key observation is: while we walk the array, the "complement" of the current number (`target - nums[i]`) is either already in a hash map we built so far, or it will appear later. By storing every value we have seen together with its index in a `HashMap`, we can answer "have I already seen the complement?" in O(1). We never need to look at pairs we already checked.

```java
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    /**
     * Walk the array once. For each element, compute what its partner
     * must be (complement = target - current). Check the map first. If
     * the partner is already in the map we are done; otherwise record
     * the current element so that future elements can find it.
     *
     * Time  : O(n)  — single pass
     * Space : O(n)  — hash map can hold up to n entries
     */
    public int[] twoSum(int[] nums, int target) {
        // Maps a number we have already visited to its array index.
        Map<Integer, Integer> seenNumbers = new HashMap<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            int currentNumber = nums[currentIndex];
            int neededPartner = target - currentNumber;

            // Has the partner already been recorded in a previous iteration?
            if (seenNumbers.containsKey(neededPartner)) {
                int partnerIndex = seenNumbers.get(neededPartner);
                return new int[]{partnerIndex, currentIndex};
            }

            // Partner not seen yet — record current number so later
            // elements can use it as their partner.
            seenNumbers.put(currentNumber, currentIndex);
        }

        // The problem guarantees exactly one solution, so this is
        // unreachable in a valid input.
        throw new IllegalArgumentException("No two-sum solution found");
    }
}
```

**Edge cases to discuss:** negative numbers, duplicate values in the array, target = 0.

---

#### Problem 2 — Valid Sudoku *(Confluent tag)*

**Description:** Determine if a 9×9 Sudoku board is valid. A board is valid if each row, each column, and each of the nine 3×3 sub-grids contains the digits 1–9 with no repetition. Empty cells are represented by `'.'` and do not need to satisfy the constraint.

**Link:** https://leetcode.com/problems/valid-sudoku/

**Intuition:** The naive approach would be three separate passes (rows, columns, boxes). The insight is that a single pass over all 81 cells is enough if we maintain three separate sets of "digits seen" for each row, each column, and each 3×3 box. For a cell at `(row, col)`, the box index is `(row / 3) * 3 + (col / 3)`. If any digit is encountered twice in the same row-set, column-set, or box-set, the board is invalid.

```java
import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {

    /**
     * Use three arrays of HashSets (one array per constraint type).
     * rowSets[r]   = digits already seen in row r
     * colSets[c]   = digits already seen in column c
     * boxSets[b]   = digits already seen in 3x3 box b  (b = (r/3)*3 + c/3)
     *
     * Time  : O(81) = O(1) — the board is always 9x9
     * Space : O(81) = O(1) — bounded by board size
     */
    public boolean isValidSudoku(char[][] board) {
        // 9 sets for rows, 9 for columns, 9 for the 3x3 boxes
        Set<Character>[] rowSets = new HashSet[9];
        Set<Character>[] colSets = new HashSet[9];
        Set<Character>[] boxSets = new HashSet[9];

        for (int index = 0; index < 9; index++) {
            rowSets[index] = new HashSet<>();
            colSets[index] = new HashSet<>();
            boxSets[index] = new HashSet<>();
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                char cell = board[row][col];

                // Skip empty cells
                if (cell == '.') {
                    continue;
                }

                // Compute which of the nine 3×3 boxes this cell belongs to
                int boxIndex = (row / 3) * 3 + (col / 3);

                // Try to insert the digit into each relevant set.
                // Set.add() returns false if the element was already present.
                boolean isNewInRow = rowSets[row].add(cell);
                boolean isNewInCol = colSets[col].add(cell);
                boolean isNewInBox = boxSets[boxIndex].add(cell);

                if (!isNewInRow || !isNewInCol || !isNewInBox) {
                    return false;
                }
            }
        }
        return true;
    }
}
```

**Edge cases to discuss:** board with all dots (valid), board that is numerically filled but violates a box, partially filled board.

---

#### Problem 3 — Time Based Key-Value Store *(Confluent tag)*

**Description:** Design a data structure that stores key-value pairs along with a timestamp. Support two operations: `set(key, value, timestamp)` stores the value for the key at the given time. `get(key, timestamp)` returns the most recent value whose timestamp is less than or equal to the given timestamp, or an empty string if none exists.

**Link:** https://leetcode.com/problems/time-based-key-value-store/

**Intuition:** For each key, timestamps are always increasing (problem guarantee), so we can store a sorted list of `(timestamp, value)` pairs. A `get` then becomes a binary search for the largest timestamp `≤` the query timestamp — this is the classic "rightmost element ≤ target" binary search pattern.

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeBasedKeyValueStore {

    // Represents a single (timestamp, value) pair stored for a key.
    private static final class TimestampedValue {
        final int timestamp;
        final String value;

        TimestampedValue(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    // For each key, we maintain a chronologically ordered list of entries.
    // Because set() is always called with increasing timestamps, appending
    // to this list keeps it sorted automatically — no explicit sort needed.
    private final Map<String, List<TimestampedValue>> store = new HashMap<>();

    /**
     * Stores value under key at the given timestamp.
     * Time  : O(1) amortised (ArrayList append)
     * Space : O(n) total entries
     */
    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new ArrayList<>())
             .add(new TimestampedValue(timestamp, value));
    }

    /**
     * Returns the value with the largest timestamp <= the requested timestamp.
     * We perform a binary search over the sorted list for this key.
     *
     * Time  : O(log n) where n is the number of entries for this key
     */
    public String get(String key, int timestamp) {
        List<TimestampedValue> entries = store.get(key);

        // Key was never stored
        if (entries == null || entries.isEmpty()) {
            return "";
        }

        // Binary search: find the rightmost entry whose timestamp <= query
        int low = 0;
        int high = entries.size() - 1;
        String bestValueSoFar = "";

        while (low <= high) {
            int mid = low + (high - low) / 2;
            TimestampedValue midEntry = entries.get(mid);

            if (midEntry.timestamp <= timestamp) {
                // This entry is a valid candidate; remember it and try to find
                // a later one (move right).
                bestValueSoFar = midEntry.value;
                low = mid + 1;
            } else {
                // This entry is too late; search left half.
                high = mid - 1;
            }
        }

        return bestValueSoFar;
    }
}
```

**Edge cases to discuss:** query timestamp earlier than all stored entries, query for a key never set, multiple values at the same timestamp (problem says timestamps are strictly increasing per key).

---

### TIER 2 — Sliding Window / Two Pointers

---

#### Problem 4 — Longest Subarray with Sum ≤ Budget *(Confluent tag — reported as "longest continuous segment whose cost ≤ B")*

**Description:** Given an array of non-negative integers `costs[]` and a budget `B`, find the length of the longest contiguous subarray whose sum does not exceed `B`.

**Link:** https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/ (similar pattern — for the exact variant, practise on paper)

**Intuition:** Because all values are **non-negative**, the window sum only grows when we extend to the right and only shrinks when we remove from the left. This monotonic property lets us use a sliding window without backtracking. We maintain a `left` pointer and a `right` pointer. Whenever the running sum exceeds `B`, we advance `left` to shrink the window. We never need to move `left` backwards.

```java
public class LongestSubarrayWithBudget {

    /**
     * Sliding window — valid only when all values are non-negative,
     * because then sum never decreases as we expand right.
     *
     * Time  : O(n) — each index is added and removed at most once
     * Space : O(1) — only a handful of integer variables
     */
    public int longestSubarray(int[] costs, int budget) {
        int left = 0;              // left boundary of the current window
        long currentWindowSum = 0; // sum of elements in [left, right]
        int maxLength = 0;         // best answer found so far

        for (int right = 0; right < costs.length; right++) {
            // Expand window by including costs[right]
            currentWindowSum += costs[right];

            // If the window is now over-budget, shrink from the left
            // until it is within budget again.
            while (currentWindowSum > budget) {
                currentWindowSum -= costs[left];
                left++;
            }

            // At this point [left, right] is a valid window.
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }
        }

        return maxLength;
    }
}
```

**Edge cases to discuss:** empty array, all elements exceed budget (answer = 0), budget = 0, single-element arrays.

---

#### Problem 5 — Sliding Window Maximum

**Description:** Given an integer array and a window size `k`, return the maximum value within each sliding window of size `k` as the window moves from left to right across the array.

**Link:** https://leetcode.com/problems/sliding-window-maximum/

**Intuition:** A naive approach re-scans each window in O(k) giving O(n·k) total. The key insight uses a **monotonic deque** (double-ended queue). The deque stores array *indices* and is kept in strictly decreasing order of their corresponding values. When we add a new element, we pop from the back any element smaller than the newcomer — those smaller elements can never become the window maximum while the newcomer is still inside the window. The front of the deque is always the index of the current window's maximum. We also pop the front when its index slides out of the window.

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {

    /**
     * Monotonic decreasing deque of indices.
     * Invariant: deque front is always the index of the window maximum.
     *
     * Time  : O(n) — each index is pushed and popped at most once
     * Space : O(k) — deque holds at most k indices
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }

        int numberOfWindows = nums.length - k + 1;
        int[] result = new int[numberOfWindows];

        // Stores indices; front = index of current max, back = index of smallest.
        Deque<Integer> deque = new ArrayDeque<>();

        for (int right = 0; right < nums.length; right++) {

            // Step 1: Remove indices that are out of the current window's range.
            while (!deque.isEmpty() && deque.peekFirst() < right - k + 1) {
                deque.pollFirst();
            }

            // Step 2: Maintain the decreasing order — remove from the back any
            // index whose value is smaller than nums[right], because those
            // elements are now useless (right is at least as large and will
            // stay in the window longer).
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[right]) {
                deque.pollLast();
            }

            // Step 3: Add current index to the back of the deque.
            deque.offerLast(right);

            // Step 4: Once we have processed at least k elements, record the
            // front of the deque as the max for the window ending at 'right'.
            if (right >= k - 1) {
                int windowIndex = right - k + 1;
                result[windowIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
```

**Edge cases to discuss:** k = 1, k = n (single window), all elements equal, array with duplicates.

---

### TIER 3 — Backtracking

---

#### Problem 6 — Combination Sum

**Description:** Given an array of distinct positive integers and a target, return all unique combinations of candidates that sum to the target. The same number may be used an unlimited number of times. The solution set must not contain duplicate combinations.

**Link:** https://leetcode.com/problems/combination-sum/

**Intuition:** This is a standard **backtracking** search. We explore a decision tree: at each step, either include the current candidate again (use it repeatedly) or move to the next candidate. We pass a `startIndex` so we never revisit earlier candidates, which naturally avoids duplicate combinations. We stop a branch early (prune) when the remaining target drops below zero.

```java
import java.util.ArrayList;
import java.util.List;

public class CombinationSum {

    /**
     * Entry point: sort candidates to enable pruning, then start backtracking.
     *
     * Time  : O(n^(T/min)) in the worst case, where T is the target and
     *         min is the smallest candidate (height of the recursion tree).
     * Space : O(T/min) for the recursion stack depth.
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> allCombinations = new ArrayList<>();
        java.util.Arrays.sort(candidates); // optional but helps with pruning
        backtrack(candidates, target, 0, new ArrayList<>(), allCombinations);
        return allCombinations;
    }

    /**
     * Recursive helper.
     *
     * @param candidates     the available numbers
     * @param remainingTarget how much more we still need to reach the target
     * @param startIndex     first index we may still pick (avoids re-using
     *                       earlier candidates → prevents duplicate combos)
     * @param currentPath    the numbers chosen so far in this branch
     * @param allCombinations accumulates completed valid combinations
     */
    private void backtrack(int[] candidates,
                           int remainingTarget,
                           int startIndex,
                           List<Integer> currentPath,
                           List<List<Integer>> allCombinations) {

        // Base case: we hit exactly the target — record a snapshot
        if (remainingTarget == 0) {
            allCombinations.add(new ArrayList<>(currentPath));
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            int candidate = candidates[i];

            // Pruning: if this candidate already exceeds the remaining target,
            // all further candidates (which are >= this one after sorting)
            // will also exceed it, so we stop early.
            if (candidate > remainingTarget) {
                break;
            }

            // Choose this candidate
            currentPath.add(candidate);

            // Recurse — note we pass i (not i+1) because we can reuse the
            // same candidate as many times as needed.
            backtrack(candidates, remainingTarget - candidate, i, currentPath, allCombinations);

            // Undo the choice (backtrack)
            currentPath.remove(currentPath.size() - 1);
        }
    }
}
```

**Edge cases to discuss:** no valid combination exists, single candidate equals the target, very large target with small candidates (deep recursion).

---

#### Problem 7 — Sudoku Solver *(Confluent tag)*

**Description:** Given a partially filled 9×9 Sudoku board, fill in the empty cells (marked `'.'`) so that the completed board satisfies all Sudoku rules. Exactly one solution is guaranteed.

**Link:** https://leetcode.com/problems/sudoku-solver/

**Intuition:** This is a **constraint-satisfaction backtracking** problem. We scan cells from top-left to bottom-right. When we reach an empty cell, we try placing digits `'1'` through `'9'`. For each digit, we first verify it does not violate any row, column, or box constraint (reuse the validation logic from Problem 2). If valid, we place it and recurse to the next empty cell. If recursion eventually fails (returns `false`), we undo the placement and try the next digit. If all nine digits fail, we return `false` to the parent, which then tries a different digit at the previous cell.

```java
public class SudokuSolver {

    /**
     * Solves the board in-place using backtracking.
     * Time  : O(9^m) in the worst case, where m is the number of empty cells.
     *         In practice, constraint propagation prunes most branches quickly.
     * Space : O(m) for the recursion stack.
     */
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    /**
     * Returns true when the board is completely and correctly filled.
     * Returns false when no digit works in the current empty cell,
     * signalling the caller to backtrack.
     */
    private boolean solve(char[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                // Skip cells that are already filled
                if (board[row][col] != '.') {
                    continue;
                }

                // Try every digit for this empty cell
                for (char digit = '1'; digit <= '9'; digit++) {
                    if (isPlacementValid(board, row, col, digit)) {
                        // Place the digit
                        board[row][col] = digit;

                        // Recurse to fill the rest of the board
                        boolean restOfBoardSolved = solve(board);
                        if (restOfBoardSolved) {
                            return true;
                        }

                        // This digit led to a dead end — undo (backtrack)
                        board[row][col] = '.';
                    }
                }

                // No digit worked for this cell — signal failure upwards
                return false;
            }
        }

        // We visited every cell without hitting an empty one — board is solved
        return true;
    }

    /**
     * Checks whether placing 'digit' at (row, col) violates any constraint.
     * Same logic as Valid Sudoku (Problem 2).
     */
    private boolean isPlacementValid(char[][] board, int row, int col, char digit) {
        int boxStartRow = (row / 3) * 3;
        int boxStartCol = (col / 3) * 3;

        for (int index = 0; index < 9; index++) {
            // Check the entire row
            if (board[row][index] == digit) return false;
            // Check the entire column
            if (board[index][col] == digit) return false;
            // Check the 3x3 box
            int boxRow = boxStartRow + index / 3;
            int boxCol = boxStartCol + index % 3;
            if (board[boxRow][boxCol] == digit) return false;
        }

        return true;
    }
}
```

**Edge cases to discuss:** already complete board (return immediately), boards with minimal clues (17 clues is the theoretical minimum for a unique-solution Sudoku).

---

### TIER 4 — Design Problems (LRU Cache and Variants)

---

#### Problem 8 — LRU Cache *(Confluent tag)*

**Description:** Design a data structure that operates as a Least Recently Used (LRU) cache with a fixed capacity. Support `get(key)` in O(1) — returns -1 if the key does not exist; and `put(key, value)` in O(1) — if the cache is at capacity, evict the least recently used entry before inserting.

**Link:** https://leetcode.com/problems/lru-cache/

**Intuition:** We need two properties simultaneously: O(1) lookup by key (hash map) and O(1) ordered eviction of the least recently used element. A **doubly linked list** gives O(1) insertion, deletion, and movement of any node — as long as we have a direct pointer to it. Combining a `HashMap` (key → node) with a doubly linked list (ordered by recency) gives both properties. The head of the list = most recently used; the tail = least recently used. Every `get` and `put` moves the accessed node to the head.

```java
import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    // Internal doubly linked list node
    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    // Maps each key to its node so we can locate it in O(1)
    private final Map<Integer, Node> keyToNode = new HashMap<>();

    // Sentinel (dummy) head and tail nodes eliminate null checks at the
    // boundaries of the list.
    // Layout:  dummyHead <-> [MRU node] <-> ... <-> [LRU node] <-> dummyTail
    private final Node dummyHead = new Node(0, 0);
    private final Node dummyTail = new Node(0, 0);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    /**
     * Returns the value for key, or -1 if absent.
     * Promotes the accessed node to the front (most recently used).
     * Time: O(1)
     */
    public int get(int key) {
        Node node = keyToNode.get(key);
        if (node == null) {
            return -1;
        }
        moveToFront(node);
        return node.value;
    }

    /**
     * Inserts or updates a key-value pair.
     * If the cache is full after insertion, evicts the LRU entry.
     * Time: O(1)
     */
    public void put(int key, int value) {
        Node existingNode = keyToNode.get(key);

        if (existingNode != null) {
            // Key already exists — update the value and promote to front
            existingNode.value = value;
            moveToFront(existingNode);
        } else {
            // Brand new key
            Node newNode = new Node(key, value);
            keyToNode.put(key, newNode);
            addToFront(newNode);

            // Evict the LRU entry if we are over capacity
            if (keyToNode.size() > capacity) {
                Node lruNode = dummyTail.prev; // node just before the dummy tail
                removeNode(lruNode);
                keyToNode.remove(lruNode.key);
            }
        }
    }

    // ── Internal helpers ────────────────────────────────────────────────────

    /** Detaches a node from wherever it sits in the list. */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /** Inserts a node immediately after the dummy head (= most recently used). */
    private void addToFront(Node node) {
        node.next = dummyHead.next;
        node.prev = dummyHead;
        dummyHead.next.prev = node;
        dummyHead.next = node;
    }

    /** Convenience: remove from current position and add to front. */
    private void moveToFront(Node node) {
        removeNode(node);
        addToFront(node);
    }
}
```

**Edge cases to discuss:** capacity = 1, put on existing key (should not evict), interleaved gets and puts, get on evicted key returns -1.

---

#### Problem 9 — KV Store with TTL and get_average() *(Confluent tag — reported in phone screen)*

**Description:** Design an in-memory key-value store where data arrives as a stream of `(timestamp, key, value)` triples in strictly increasing timestamp order. Support:
- `put(timestamp, key, value)` — stores or overwrites the value.
- `get(timestamp, key)` — returns the value only if not expired; a value expires after a fixed TTL window.
- `getAverage(timestamp)` — returns the average of all non-expired numeric values at the given moment.

**Link:** No direct LeetCode link — exact Confluent interview problem. See similar: https://leetcode.com/problems/time-based-key-value-store/

**Intuition:** Because timestamps arrive in increasing order, we can maintain a queue (or sorted structure) of entries ordered by insertion time. For `get`, do a binary search on the key's timestamp list (same as Problem 3) but also reject values older than `now - TTL`. For `getAverage`, we need the sum and count of all non-expired entries — maintain a running `totalSum` and `activeCount`, and use the sorted queue to lazily expire old entries (remove all entries with `timestamp < now - TTL` from the front of the queue before computing the average).

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
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

    // For get(key) queries: maps key -> list of (timestamp, value) in
    // insertion order (already sorted because timestamps are increasing).
    private final Map<String, Deque<Entry>> entriesByKey = new HashMap<>();

    // Global ordered queue of ALL entries, oldest first — used by getAverage
    // to expire old entries efficiently.
    private final Deque<Entry> globalQueue = new ArrayDeque<>();

    // Running totals maintained so getAverage is O(expired + 1) per call.
    private double runningSum = 0.0;
    private int activeCount = 0;

    public TtlKVStoreWithAverage(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }

    /**
     * Stores the value under key with the given timestamp.
     * Time: O(1) amortised
     */
    public void put(long timestamp, String key, double value) {
        Entry entry = new Entry(timestamp, key, value);

        entriesByKey.computeIfAbsent(key, k -> new ArrayDeque<>()).addLast(entry);
        globalQueue.addLast(entry);

        runningSum += value;
        activeCount++;
    }

    /**
     * Returns the most recent value for key that is not yet expired,
     * or null if no such value exists.
     * Time: O(log n) conceptually; O(n) with a deque (binary search needs List).
     * For interview: iterate from back of key's deque — most recent first.
     */
    public Double get(long queryTimestamp, String key) {
        Deque<Entry> keyEntries = entriesByKey.get(key);
        if (keyEntries == null || keyEntries.isEmpty()) {
            return null;
        }

        // Walk from most recent entry backwards; return first non-expired value.
        // (In a production version we would use a List for binary search.)
        for (Entry entry : (Iterable<Entry>) keyEntries::descendingIterator) {
            if (entry.timestamp <= queryTimestamp) {
                boolean notExpired = (queryTimestamp - entry.timestamp) < ttlMillis;
                return notExpired ? entry.value : null;
            }
        }
        return null;
    }

    /**
     * Returns the average of all stored values that have not yet expired
     * at queryTimestamp.
     * Time: O(e) where e = number of entries expiring in this call (amortised O(1)).
     */
    public double getAverage(long queryTimestamp) {
        // Lazily expire entries that are now too old.
        // The global queue is in insertion order = timestamp order, so we
        // simply drain expired entries from the front.
        while (!globalQueue.isEmpty()) {
            Entry oldest = globalQueue.peekFirst();
            boolean hasExpired = (queryTimestamp - oldest.timestamp) >= ttlMillis;
            if (hasExpired) {
                globalQueue.pollFirst();
                runningSum -= oldest.value;
                activeCount--;
            } else {
                // All remaining entries are newer, so none are expired.
                break;
            }
        }

        if (activeCount == 0) {
            throw new IllegalStateException("No active entries — cannot compute average");
        }
        return runningSum / activeCount;
    }
}
```

**Edge cases to discuss:** TTL of zero, all entries expired before getAverage call, overwriting a key (old entry remains in the global queue — discuss how to handle or simplify for the interview), very large timestamps.

---

### TIER 5 — Confluent Scenario-Based Problems

---

#### Problem 10 — Word and Phrase Search in Documents *(Confluent tag — reported in multiple rounds)*

**Description:** Given a list of documents (each document is a list of words), implement a search API with two operations:
1. `searchWord(word)` — returns the indices of all documents that contain the given word.
2. `searchPhrase(phrase)` — returns the indices of all documents that contain all words of the phrase in the exact consecutive order.

**Link:** No direct LeetCode equivalent — Confluent interview problem. Related: https://leetcode.com/problems/search-a-2d-matrix/

**Intuition:**
- **Word search:** Build an **inverted index** during construction: map each word to the set of document indices where it appears. Then `searchWord` is a simple O(1) map lookup.
- **Phrase search:** A phrase is two or more words in consecutive order. Start with the candidate document set from the inverted index of the first phrase word. For each candidate document, walk through the document's word list with a sliding window of size `phraseLength` and check if the window matches the phrase exactly. Alternatively, for each occurrence position of the first word, check that subsequent positions exist for subsequent words.

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DocumentSearch {

    // Stores the original documents so phrase checking can access them.
    private final List<List<String>> documents;

    // Inverted index: word -> set of document indices that contain this word.
    private final Map<String, Set<Integer>> invertedIndex = new HashMap<>();

    /**
     * Builds the inverted index during construction.
     * Time  : O(total words across all documents)
     * Space : O(total words) for the index
     */
    public DocumentSearch(List<List<String>> documents) {
        this.documents = documents;

        for (int docIndex = 0; docIndex < documents.size(); docIndex++) {
            List<String> document = documents.get(docIndex);
            for (String word : document) {
                String normalizedWord = word.toLowerCase();
                invertedIndex
                    .computeIfAbsent(normalizedWord, w -> new HashSet<>())
                    .add(docIndex);
            }
        }
    }

    /**
     * Returns all document indices containing the given word.
     * Time  : O(1) hash map lookup
     */
    public List<Integer> searchWord(String word) {
        Set<Integer> matchingDocIndices = invertedIndex.getOrDefault(
            word.toLowerCase(), Set.of()
        );
        return new ArrayList<>(matchingDocIndices);
    }

    /**
     * Returns all document indices where the phrase words appear
     * consecutively in the same order.
     *
     * Strategy:
     * 1. Use the inverted index to get candidate docs for the first phrase word.
     * 2. For each candidate doc, check whether any position in the doc
     *    has all subsequent phrase words immediately following.
     *
     * Time  : O(candidates * docLength * phraseLength)
     *         In practice, the inverted index prunes most documents.
     */
    public List<Integer> searchPhrase(List<String> phraseWords) {
        List<String> normalizedPhrase = phraseWords.stream()
            .map(String::toLowerCase)
            .toList();

        if (normalizedPhrase.isEmpty()) {
            return List.of();
        }

        // Start with documents that contain the very first phrase word.
        Set<Integer> candidateDocIndices = invertedIndex.getOrDefault(
            normalizedPhrase.get(0), Set.of()
        );

        List<Integer> results = new ArrayList<>();

        for (int docIndex : candidateDocIndices) {
            List<String> document = documents.get(docIndex);

            if (documentContainsPhrase(document, normalizedPhrase)) {
                results.add(docIndex);
            }
        }

        return results;
    }

    /**
     * Returns true if the phrase appears as a consecutive subsequence
     * anywhere in the given document.
     */
    private boolean documentContainsPhrase(List<String> document, List<String> phrase) {
        int phraseLength = phrase.size();
        int documentLength = document.size();

        // We need at least phraseLength words remaining from position i.
        for (int startPosition = 0;
             startPosition <= documentLength - phraseLength;
             startPosition++) {

            String docWordAtStart = document.get(startPosition).toLowerCase();
            if (!docWordAtStart.equals(phrase.get(0))) {
                continue; // quick skip: first words must match
            }

            // First word matched — check the rest of the phrase
            boolean entirePhraseMatches = true;
            for (int offset = 1; offset < phraseLength; offset++) {
                String docWord = document.get(startPosition + offset).toLowerCase();
                if (!docWord.equals(phrase.get(offset))) {
                    entirePhraseMatches = false;
                    break;
                }
            }

            if (entirePhraseMatches) {
                return true;
            }
        }

        return false;
    }
}
```

**Edge cases to discuss:** empty phrase, word with different capitalisation, phrase longer than any document, same word appearing multiple times in one document, large document sets (discuss sharding the index).

---

#### Problem 11 — Implement tail -n (Read last N lines of a large file) *(Confluent tag — reported in onsite rounds)*

**Description:** Implement a method `tail(filePath, n)` that returns the last `n` lines of a file. The file may be larger than available memory so you cannot load it all at once. If the file has fewer than `n` lines, return all lines.

**Link:** No LeetCode equivalent — system programming problem. Reported directly in Confluent interviews.

**Intuition:** Reading forwards requires loading the entire file to find where the last `n` lines start. Instead, **seek to the end** using `RandomAccessFile`, then read backwards in fixed-size blocks (e.g. 4 KB). Count newline characters as we scan backwards. Once we have counted `n` newlines (which mark the starts of the last `n` lines), we know the byte offset of the (`n+1`)th line from the end. We then seek to that offset and read forwards normally to produce the output. This is exactly how the UNIX `tail` command works.

```java
import java.io.RandomAccessFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TailUtility {

    private static final int BLOCK_SIZE = 4096; // Read 4 KB at a time backwards

    /**
     * Returns the last n lines of the file at filePath.
     * Does not load the entire file into memory.
     *
     * Time  : O(n * averageLineLength) — reads only as far back as needed
     * Space : O(n * averageLineLength) — holds only the returned lines
     *
     * @param filePath absolute path to the file
     * @param n        number of lines to return from the end
     * @return list of lines, in the order they appear in the file
     */
    public static List<String> tail(String filePath, int n) throws IOException {
        if (n <= 0) {
            return Collections.emptyList();
        }

        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long fileLength = file.length();

            if (fileLength == 0) {
                return Collections.emptyList();
            }

            // We will scan backwards counting newlines.
            // newlinesFound tracks how many complete line endings we have passed.
            int newlinesFound = 0;

            // Current read position (starts at the last byte).
            long readPosition = fileLength - 1;

            // Buffer to accumulate the tail content as we scan backwards.
            // We will reverse it at the end.
            StringBuilder reversedContent = new StringBuilder();

            // Scan backwards in blocks until we have found n newlines
            // OR we have reached the beginning of the file.
            outer:
            while (readPosition >= 0) {
                // How many bytes to read in this block?
                long blockStart = Math.max(0, readPosition - BLOCK_SIZE + 1);
                int blockLength = (int) (readPosition - blockStart + 1);

                byte[] buffer = new byte[blockLength];
                file.seek(blockStart);
                file.readFully(buffer);

                // Walk the buffer from right to left (reverse order).
                for (int bufferIndex = blockLength - 1; bufferIndex >= 0; bufferIndex--) {
                    char character = (char) buffer[bufferIndex];
                    reversedContent.append(character);

                    if (character == '\n') {
                        newlinesFound++;

                        // We count the newline at the very end of the file as
                        // a trailing newline (not a new line), so we subtract 1.
                        long isTrailingNewline = (blockStart + bufferIndex == fileLength - 1) ? 1 : 0;
                        if (newlinesFound - isTrailingNewline >= n) {
                            // We have collected enough lines — stop scanning.
                            break outer;
                        }
                    }
                }

                readPosition = blockStart - 1;
            }

            // reversedContent contains the tail bytes in reverse order.
            // Reverse it to restore the correct line order.
            String tailContent = reversedContent.reverse().toString();

            // Split into lines and return.
            String[] lines = tailContent.split("\n", -1);

            List<String> result = new ArrayList<>();
            for (String line : lines) {
                if (!line.isEmpty()) { // skip empty string from trailing newline
                    result.add(line);
                }
            }

            return result;
        }
    }
}
```

**Edge cases to discuss:** `n = 0` (return empty list), `n` larger than total line count (return all lines), file with no trailing newline, file with only one line, file that is exactly one block in size, Windows line endings (`\r\n`).

---

### TIER 6 — Hard: Dynamic Programming and Pattern Matching

---

#### Problem 12 — Regular Expression Matching *(Confluent tag — reported in hard rounds)*

**Description:** Given a string `s` and a pattern `p`, implement regular expression matching with support for `'.'` (matches any single character) and `'*'` (matches zero or more of the preceding element). The matching must cover the entire input string.

**Link:** https://leetcode.com/problems/regular-expression-matching/

**Intuition:** Recursion with memoisation works well here. Define `matches(sIndex, pIndex)` = can `s[sIndex..]` be matched by `p[pIndex..]`? Two cases:
1. **`p[pIndex+1]` is NOT `'*'`**: the current characters must match directly (either equal or `p[pIndex] == '.'`), then recurse on the next positions.
2. **`p[pIndex+1]` is `'*'`**: we try using the `x*` segment zero times (skip two pattern positions) OR one or more times (consume one matching character from `s` and stay at the same pattern position).

```java
import java.util.HashMap;
import java.util.Map;

public class RegularExpressionMatching {

    // Memoisation cache: key = "sIndex,pIndex", value = match result
    private final Map<String, Boolean> memo = new HashMap<>();

    public boolean isMatch(String s, String p) {
        memo.clear();
        return matches(s, p, 0, 0);
    }

    /**
     * Returns true if s[sIndex..] is fully matched by p[pIndex..].
     *
     * Time  : O(|s| * |p|) — each (sIndex, pIndex) pair computed once
     * Space : O(|s| * |p|) — memoisation table
     */
    private boolean matches(String s, String p, int sIndex, int pIndex) {
        // Base case: pattern exhausted — string must also be exhausted
        if (pIndex == p.length()) {
            return sIndex == s.length();
        }

        // Check memo
        String cacheKey = sIndex + "," + pIndex;
        if (memo.containsKey(cacheKey)) {
            return memo.get(cacheKey);
        }

        // Does the current single character match?
        boolean currentCharacterMatches =
            sIndex < s.length() &&
            (p.charAt(pIndex) == '.' || p.charAt(pIndex) == s.charAt(sIndex));

        boolean result;

        if (pIndex + 1 < p.length() && p.charAt(pIndex + 1) == '*') {
            // The next pattern char is '*', so we handle x* as a unit.
            // Option A: use x* zero times — skip both the 'x' and '*' in pattern
            boolean useZeroTimes = matches(s, p, sIndex, pIndex + 2);
            // Option B: use x* one-or-more times — consume one matching char in s,
            // but stay at the same pIndex (allowing further repeats of x*)
            boolean useOnceOrMore = currentCharacterMatches &&
                matches(s, p, sIndex + 1, pIndex);

            result = useZeroTimes || useOnceOrMore;
        } else {
            // No '*' following: must match one character exactly
            result = currentCharacterMatches && matches(s, p, sIndex + 1, pIndex + 1);
        }

        memo.put(cacheKey, result);
        return result;
    }
}
```

**Edge cases to discuss:** empty string against `.*` pattern (valid match), pattern with consecutive `*` characters (invalid regex — problem guarantees this won't happen), `.*` in the middle of the pattern.

---

#### Problem 13 — Wildcard Matching *(Confluent tag)*

**Description:** Given a string `s` and a pattern `p` with `'?'` (matches any single character) and `'*'` (matches any sequence of characters, including empty), determine if `p` matches `s` in full.

**Link:** https://leetcode.com/problems/wildcard-matching/

**Intuition:** Identical recursion structure to Problem 12 but `'*'` here is a true wildcard that can match zero or more characters of any type. When we encounter `'*'` in the pattern, we have two choices: skip it (match zero characters) or consume one character from `s` and keep `'*'` active (match more). A bottom-up DP table avoids repeated work.

```java
public class WildcardMatching {

    /**
     * Bottom-up DP approach.
     * dp[i][j] = can s[0..i-1] be fully matched by p[0..j-1]?
     *
     * Time  : O(|s| * |p|)
     * Space : O(|s| * |p|)
     */
    public boolean isMatch(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();

        // dp[i][j] = true means the first i chars of s match the first j chars of p
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

        // Empty string matches empty pattern
        dp[0][0] = true;

        // An empty string s can be matched by a pattern consisting only of '*'s.
        // dp[0][j] is true only while all preceding pattern chars are '*'.
        for (int j = 1; j <= pLen; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1]; // '*' matches empty, so inherit
            }
        }

        for (int i = 1; i <= sLen; i++) {
            for (int j = 1; j <= pLen; j++) {
                char sChar = s.charAt(i - 1);
                char pChar = p.charAt(j - 1);

                if (pChar == '*') {
                    // '*' matches empty sequence: take dp[i][j-1]
                    // '*' matches one more char: take dp[i-1][j]
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (pChar == '?' || pChar == sChar) {
                    // Single character match — inherit from diagonal
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // else dp[i][j] remains false (initialised that way)
            }
        }

        return dp[sLen][pLen];
    }
}
```

**Edge cases to discuss:** pattern `"*"` matches any string including empty, pattern with multiple consecutive `*` (they collapse to one), empty both (true), empty string with non-`*` pattern (false).

---

#### Problem 14 — Minimum Time to Eat All Grains *(Confluent tag — reported as a hard problem)*

**Description:** There are `n` hens and `m` grains on a number line. Hen `i` is at position `hens[i]` and grain `j` is at position `grains[j]`. Each minute each hen can move one position left or right. All hens eat grains simultaneously. Assign grains to hens such that every grain is eaten and the total time (the time until the last hen finishes) is minimised. Return the minimum time.

**Link:** https://leetcode.com/problems/minimum-time-to-eat-all-grains/

**Intuition:** Binary search on the answer. Ask: "Can all grains be eaten within `T` minutes?" For a given `T`, sort both hens and grains. Use a two-pointer sweep: for each hen (in order), assign the furthest consecutive grains the hen can reach within `T` minutes. If a grain is to the **left** of the hen, the hen must walk left to it and back (or the hen goes left first then right, whichever is shorter). This greedy assignment works because sorting ensures we never skip a grain that a later hen could reach more cheaply.

```java
import java.util.Arrays;

public class MinTimeToEatAllGrains {

    /**
     * Binary search on the answer T (time).
     * Binary search range: [0, max possible distance].
     * For each candidate T, check greedily if all grains can be covered.
     *
     * Time  : O((n + m) log(max_position))
     * Space : O(1) after sorting
     */
    public int minimumTime(int[] hens, int[] grains) {
        Arrays.sort(hens);
        Arrays.sort(grains);

        int low = 0;
        int high = Math.abs(hens[0] - grains[grains.length - 1])
                 + Math.abs(hens[hens.length - 1] - grains[0])
                 + grains[grains.length - 1]; // generous upper bound

        int answer = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canEatAllGrains(hens, grains, mid)) {
                answer = mid;
                high = mid - 1; // try to do it even faster
            } else {
                low = mid + 1; // need more time
            }
        }

        return answer;
    }

    /**
     * Greedy check: can all grains be eaten within 'timeLimit' minutes?
     * Uses two pointers — one over hens, one over grains.
     *
     * For each hen (left to right), determine the rightmost grain it can
     * eat within timeLimit. Advance the grain pointer accordingly.
     */
    private boolean canEatAllGrains(int[] hens, int[] grains, int timeLimit) {
        int grainPointer = 0; // index of the next un-assigned grain

        for (int hen : hens) {
            if (grainPointer >= grains.length) {
                // All grains are already assigned — success
                return true;
            }

            // The current grain is to the left, right, or exactly at the hen.
            int leftDistance = Math.max(0, hen - grains[grainPointer]);  // must go left
            int rightDistance = Math.max(0, grains[grainPointer] - hen); // must go right

            // If the first unassigned grain is unreachable even for this hen,
            // no hen to the right can reach it either (sorted order), so fail.
            if (leftDistance > timeLimit && rightDistance > timeLimit) {
                return false;
            }

            // Advance grainPointer past all grains this hen can eat within timeLimit.
            // The hen may go left first then right, or right only — two strategies:
            // strategy A: go left first then right — cost = leftDistance + 2*rightDistance
            // strategy B: go right first then left — cost = 2*leftDistance + rightDistance
            // The hen picks whichever lets it eat as many grains as possible.
            while (grainPointer < grains.length) {
                int newLeft = Math.max(0, hen - grains[grainPointer]);
                int newRight = Math.max(0, grains[grainPointer] - hen);

                // Minimum cost to reach this grain considering both direction strategies
                int minCostToReachThisGrain = Math.min(
                    newLeft + 2 * newRight,   // strategy A: left first
                    2 * newLeft + newRight    // strategy B: right first
                );

                // If the grain is only on one side the min formula still works:
                // if newLeft==0, cost = min(2*newRight, newRight) = newRight. Correct.

                if (minCostToReachThisGrain <= timeLimit) {
                    grainPointer++; // this hen eats this grain
                } else {
                    break; // this grain is too far; let the next hen handle it
                }
            }
        }

        return grainPointer >= grains.length;
    }
}
```

**Edge cases to discuss:** one hen and one grain, all grains on one side of all hens, `hens.length == grains.length`, duplicate positions.

---

## 10. Quick Revision Checklist (30 minutes before the interview)

| Concept | One-liner to say aloud |
|---------|----------------------|
| Hash map lookup | "O(1) average; I'll handle collision by chaining." |
| Sliding window validity | "Monotonically expanding — only valid when values are non-negative." |
| Backtracking | "I place, recurse, and undo — pruning when the constraint is already violated." |
| Doubly linked list + HashMap | "Hash map gives O(1) access; DLL gives O(1) insert/delete/move — together they power LRU." |
| Binary search on the answer | "I binary-search on the *value* of the answer, not on an array index." |
| Monotonic deque | "I keep it decreasing: pop the back when a larger element arrives, pop the front when it leaves the window." |
| Inverted index | "Map each token to the sorted set of documents that contain it." |
| RandomAccessFile / tail -n | "Seek to the end, read backwards in blocks, count newlines." |
| TTL expiry | "Lazy expiry on access; or eager expiry via a background queue draining the front." |

## 11. Updated Final Priority Order (Confluent-focused)

1. **LRU Cache** — almost certain to appear in some form
2. **Valid Sudoku + Sudoku Solver** — very frequently reported pair
3. **KV Store with TTL + get_average()** — unique Confluent phone-screen pattern
4. **Word / Phrase Search in Documents** — reported in Coding Round 1
5. **Sliding Window (Longest Subarray ≤ B)** — reported in onsite Round 1
6. **Time Based Key-Value Store** — related pattern to the KV store above
7. **Combination Sum (backtracking)** — backtracking warm-up before Sudoku Solver
8. **Implement tail -n** — reported in Confluent technical screen
9. **Two Sum** — foundational hash-map problem; fast warm-up
10. **Sliding Window Maximum** — hard sliding window with monotonic deque
11. **Regular Expression Matching** — reported in hard rounds
12. **Wildcard Matching** — close cousin of regex, DP practice
13. **Minimum Time to Eat All Grains** — binary search on answer, hardest pattern

---

## 12. DSA Patterns Cheat Sheet — Every Major Pattern

> For each pattern: **when to recognise it**, the **core Java template**, and **3–5 foundational LeetCode problems** ordered easy → hard.

---

### PATTERN 1 — Two Pointers

**Recognise when:**
- Input is a **sorted** array or string.
- Problem asks for a pair, triplet, or in-place modification.
- Keywords: "find two numbers", "reverse", "palindrome", "remove duplicates".

**Core template:**
```java
int left = 0;
int right = array.length - 1;

while (left < right) {
    if (conditionMet(array[left], array[right])) {
        // record answer or return
        left++;
        right--;
    } else if (needBigger(array[left], array[right])) {
        left++;   // move left pointer right to increase value
    } else {
        right--;  // move right pointer left to decrease value
    }
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Valid Palindrome | https://leetcode.com/problems/valid-palindrome/ | Two pointers inward; skip non-alphanumeric |
| 2 | Two Sum II — Input Array Is Sorted | https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/ | Classic two-pointer pair search |
| 3 | 3Sum | https://leetcode.com/problems/3sum/ | Fix one element, two-pointer scan for the other two |
| 4 | Container With Most Water | https://leetcode.com/problems/container-with-most-water/ | Greedy pointer movement |
| 5 | Remove Duplicates from Sorted Array | https://leetcode.com/problems/remove-duplicates-from-sorted-array/ | In-place write pointer |

---

### PATTERN 2 — Sliding Window

**Recognise when:**
- Problem involves a **contiguous subarray or substring**.
- Keywords: "longest", "shortest", "maximum/minimum subarray", "exactly k", "at most k".
- Fixed window size *k* → use simple expand-by-one approach; variable window → shrink left when constraint violated.

**Core template (variable window):**
```java
int left = 0;
int maxLength = 0;
// state tracks whatever the window's constraint needs (e.g. a frequency map)
Map<Character, Integer> windowFreq = new HashMap<>();

for (int right = 0; right < s.length(); right++) {
    // 1. Expand: add s[right] to the window state
    windowFreq.merge(s.charAt(right), 1, Integer::sum);

    // 2. Shrink: while the window violates the constraint, move left
    while (windowViolatesConstraint(windowFreq)) {
        windowFreq.merge(s.charAt(left), -1, Integer::sum);
        if (windowFreq.get(s.charAt(left)) == 0) windowFreq.remove(s.charAt(left));
        left++;
    }

    // 3. Record the best answer for this valid window
    maxLength = Math.max(maxLength, right - left + 1);
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Maximum Average Subarray I | https://leetcode.com/problems/maximum-average-subarray-i/ | Fixed window of size k |
| 2 | Longest Substring Without Repeating Characters | https://leetcode.com/problems/longest-substring-without-repeating-characters/ | Variable window, frequency map |
| 3 | Minimum Size Subarray Sum | https://leetcode.com/problems/minimum-size-subarray-sum/ | Shrink as soon as sum ≥ target |
| 4 | Fruit Into Baskets | https://leetcode.com/problems/fruit-into-baskets/ | At-most-2-distinct sliding window |
| 5 | Minimum Window Substring | https://leetcode.com/problems/minimum-window-substring/ | Hard — track required chars with two frequency maps |

---

### PATTERN 3 — Fast & Slow Pointers (Floyd's Cycle Detection)

**Recognise when:**
- Linked list and you suspect a **cycle**.
- Finding the **middle** of a linked list in one pass.
- Finding the start of a cycle.

**Core template:**
```java
ListNode slow = head;
ListNode fast = head;

// Phase 1: detect the cycle — fast moves 2 steps, slow moves 1
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;

    if (slow == fast) {
        // Cycle confirmed — move to phase 2 to find the cycle start
        ListNode pointer = head;
        while (pointer != slow) {
            pointer = pointer.next;
            slow = slow.next;
        }
        return pointer; // cycle start node
    }
}
return null; // no cycle
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Middle of the Linked List | https://leetcode.com/problems/middle-of-the-linked-list/ | Slow reaches middle when fast reaches end |
| 2 | Linked List Cycle | https://leetcode.com/problems/linked-list-cycle/ | Basic cycle detection |
| 3 | Linked List Cycle II | https://leetcode.com/problems/linked-list-cycle-ii/ | Find cycle start using phase 2 |
| 4 | Happy Number | https://leetcode.com/problems/happy-number/ | Cycle detection on a sequence of numbers |
| 5 | Find the Duplicate Number | https://leetcode.com/problems/find-the-duplicate-number/ | Array as implicit linked list with cycle |

---

### PATTERN 4 — Merge Intervals

**Recognise when:**
- Input is a list of **intervals** `[start, end]`.
- Keywords: "overlapping", "merge", "meeting rooms", "free time".
- Key step: **sort by start time** first.

**Core template:**
```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // sort by start

List<int[]> merged = new ArrayList<>();
int[] current = intervals[0];

for (int i = 1; i < intervals.length; i++) {
    int[] next = intervals[i];

    if (next[0] <= current[1]) {
        // Intervals overlap — extend current interval's end if needed
        current[1] = Math.max(current[1], next[1]);
    } else {
        // No overlap — save current and move to next
        merged.add(current);
        current = next;
    }
}
merged.add(current); // add the last interval
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Merge Intervals | https://leetcode.com/problems/merge-intervals/ | Core pattern |
| 2 | Insert Interval | https://leetcode.com/problems/insert-interval/ | Merge a new interval into sorted list |
| 3 | Non-overlapping Intervals | https://leetcode.com/problems/non-overlapping-intervals/ | Greedy — minimum removals |
| 4 | Meeting Rooms | https://leetcode.com/problems/meeting-rooms/ | Can one person attend all? |
| 5 | Meeting Rooms II | https://leetcode.com/problems/meeting-rooms-ii/ | Minimum rooms needed — heap or sort |

---

### PATTERN 5 — Modified Binary Search

**Recognise when:**
- Array is **sorted** (possibly rotated, partially sorted, infinite).
- Finding a **boundary**: first/last occurrence, leftmost/rightmost position.
- Binary search on the **answer value** (not an array index) — feasibility check.

**Core template (binary search on answer):**
```java
int low = minPossibleAnswer;
int high = maxPossibleAnswer;

while (low <= high) {
    int mid = low + (high - low) / 2;

    if (isFeasible(mid)) {
        answer = mid;
        high = mid - 1; // try to find something better (smaller)
    } else {
        low = mid + 1;  // mid is not feasible — need more
    }
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Binary Search | https://leetcode.com/problems/binary-search/ | Classic template |
| 2 | Search in Rotated Sorted Array | https://leetcode.com/problems/search-in-rotated-sorted-array/ | Determine which half is sorted |
| 3 | Find Minimum in Rotated Sorted Array | https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/ | Binary search for inflection point |
| 4 | Koko Eating Bananas | https://leetcode.com/problems/koko-eating-bananas/ | Binary search on the answer value |
| 5 | Capacity To Ship Packages Within D Days | https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/ | Classic "search on answer" with feasibility function |

---

### PATTERN 6 — Tree BFS (Level-Order Traversal)

**Recognise when:**
- Tree problem and you need **level-by-level** processing.
- Keywords: "level order", "zigzag", "right side view", "connect nodes at same level", "minimum depth".

**Core template:**
```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);

while (!queue.isEmpty()) {
    int levelSize = queue.size(); // number of nodes at this level
    List<Integer> currentLevel = new ArrayList<>();

    for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        currentLevel.add(node.val);

        if (node.left  != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }

    result.add(currentLevel);
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Binary Tree Level Order Traversal | https://leetcode.com/problems/binary-tree-level-order-traversal/ | Core BFS template |
| 2 | Binary Tree Zigzag Level Order Traversal | https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/ | Alternating direction per level |
| 3 | Binary Tree Right Side View | https://leetcode.com/problems/binary-tree-right-side-view/ | Take last node of each level |
| 4 | Maximum Depth of Binary Tree | https://leetcode.com/problems/maximum-depth-of-binary-tree/ | Count levels |
| 5 | Rotting Oranges | https://leetcode.com/problems/rotting-oranges/ | Multi-source BFS on a grid |

---

### PATTERN 7 — Tree DFS (Path and Subtree Problems)

**Recognise when:**
- Tree problem involving **paths**, **subtree properties**, or **comparisons** between subtrees.
- Keywords: "root to leaf", "path sum", "diameter", "lowest common ancestor".

**Core template (post-order for aggregation):**
```java
private int dfs(TreeNode node) {
    if (node == null) {
        return baseCase; // 0 for sum, -1 for depth, etc.
    }

    int leftResult  = dfs(node.left);
    int rightResult = dfs(node.right);

    // Process current node using children's results (post-order)
    int currentResult = combine(leftResult, rightResult, node.val);

    // Optionally update a global variable for the best answer seen so far
    globalBest = Math.max(globalBest, currentResult);

    // Return what the parent needs
    return returnToParent(currentResult);
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Path Sum | https://leetcode.com/problems/path-sum/ | Root-to-leaf DFS |
| 2 | Diameter of Binary Tree | https://leetcode.com/problems/diameter-of-binary-tree/ | Post-order: left depth + right depth |
| 3 | Binary Tree Maximum Path Sum | https://leetcode.com/problems/binary-tree-maximum-path-sum/ | Track global max during post-order |
| 4 | Validate Binary Search Tree | https://leetcode.com/problems/validate-binary-search-tree/ | Pass valid range down each path |
| 5 | Lowest Common Ancestor of a BST | https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/ | Use BST ordering to navigate |

---

### PATTERN 8 — Top K Elements (Heap)

**Recognise when:**
- Problem needs the **k largest**, **k smallest**, **k most frequent**, or a **running median**.
- Use a **min-heap of size k** for "top k largest" (the root is the smallest among the top k, so we can compare and evict cheaply).
- Use a **max-heap** for "k smallest".

**Core template (k largest with min-heap):**
```java
// PriorityQueue in Java is a min-heap by default
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

for (int number : numbers) {
    minHeap.offer(number);

    // Keep only k elements — the smallest among top-k is at the root
    if (minHeap.size() > k) {
        minHeap.poll(); // evict the current minimum (which is not top-k)
    }
}
// minHeap now contains exactly the k largest elements
// minHeap.peek() is the kth largest
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Kth Largest Element in an Array | https://leetcode.com/problems/kth-largest-element-in-an-array/ | Min-heap of size k |
| 2 | Top K Frequent Elements | https://leetcode.com/problems/top-k-frequent-elements/ | Frequency map + heap |
| 3 | K Closest Points to Origin | https://leetcode.com/problems/k-closest-points-to-origin/ | Max-heap of size k (evict farthest) |
| 4 | Task Scheduler | https://leetcode.com/problems/task-scheduler/ | Max-heap + greedy idle slots |
| 5 | Find Median from Data Stream | https://leetcode.com/problems/find-median-from-data-stream/ | Two heaps: max-heap (lower half) + min-heap (upper half) |

---

### PATTERN 9 — Backtracking (Generate All / Constraint Satisfaction)

**Recognise when:**
- Problem asks for **all combinations**, **all permutations**, or **all valid configurations**.
- Keywords: "generate", "find all", "enumerate", "solve puzzle".
- State space is a tree and you need to explore every valid path.

**Core template:**
```java
private void backtrack(/* problem state */, List<Integer> currentChoice,
                       List<List<Integer>> allResults) {

    // Base case: current choice is a complete valid answer
    if (isComplete(currentChoice)) {
        allResults.add(new ArrayList<>(currentChoice)); // snapshot!
        return;
    }

    for (each valid next choice) {
        // 1. Make the choice
        currentChoice.add(choice);

        // 2. Recurse with updated state
        backtrack(updatedState, currentChoice, allResults);

        // 3. Undo the choice (backtrack)
        currentChoice.remove(currentChoice.size() - 1);
    }
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Subsets | https://leetcode.com/problems/subsets/ | Include/exclude decision at each element |
| 2 | Permutations | https://leetcode.com/problems/permutations/ | Use a visited boolean array |
| 3 | Letter Combinations of a Phone Number | https://leetcode.com/problems/letter-combinations-of-a-phone-number/ | Iterate over mapped characters |
| 4 | Word Search | https://leetcode.com/problems/word-search/ | DFS on a grid, mark visited |
| 5 | N-Queens | https://leetcode.com/problems/n-queens/ | Row-by-row placement, check diagonals and columns |

---

### PATTERN 10 — Graph BFS / DFS

**Recognise when:**
- Problem involves a **grid** or **adjacency list**.
- Keywords: "connected components", "reachable", "shortest path (unweighted)", "islands", "spread".
- BFS → shortest path; DFS → connectivity, cycle detection.

**Core template (BFS for shortest path):**
```java
Queue<int[]> queue = new LinkedList<>();
boolean[][] visited = new boolean[rows][cols];
queue.offer(new int[]{startRow, startCol});
visited[startRow][startCol] = true;
int steps = 0;

int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};

while (!queue.isEmpty()) {
    int levelSize = queue.size();
    steps++;

    for (int i = 0; i < levelSize; i++) {
        int[] cell = queue.poll();
        int row = cell[0], col = cell[1];

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValid(newRow, newCol) && !visited[newRow][newCol]) {
                if (isTarget(newRow, newCol)) return steps;
                visited[newRow][newCol] = true;
                queue.offer(new int[]{newRow, newCol});
            }
        }
    }
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Number of Islands | https://leetcode.com/problems/number-of-islands/ | DFS/BFS flood fill to count components |
| 2 | Clone Graph | https://leetcode.com/problems/clone-graph/ | BFS with node map |
| 3 | Course Schedule | https://leetcode.com/problems/course-schedule/ | Cycle detection via topological sort (Kahn's BFS) |
| 4 | Pacific Atlantic Water Flow | https://leetcode.com/problems/pacific-atlantic-water-flow/ | Reverse BFS from both oceans |
| 5 | Word Ladder | https://leetcode.com/problems/word-ladder/ | BFS on an implicit graph |

---

### PATTERN 11 — Topological Sort (DAG ordering)

**Recognise when:**
- Directed graph with dependencies: "do X before Y".
- Keywords: "course prerequisites", "task ordering", "build order", "detect cycle in directed graph".

**Core template (Kahn's algorithm — BFS):**
```java
// Build in-degree count and adjacency list
int[] inDegree = new int[numNodes];
List<List<Integer>> adjacency = /* build from edges */;

// Start: all nodes with no prerequisites
Queue<Integer> queue = new LinkedList<>();
for (int node = 0; node < numNodes; node++) {
    if (inDegree[node] == 0) queue.offer(node);
}

List<Integer> order = new ArrayList<>();
while (!queue.isEmpty()) {
    int current = queue.poll();
    order.add(current);

    for (int neighbour : adjacency.get(current)) {
        inDegree[neighbour]--;
        if (inDegree[neighbour] == 0) {
            queue.offer(neighbour); // all prerequisites satisfied
        }
    }
}

// If order.size() != numNodes, there is a cycle — no valid ordering exists
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Course Schedule | https://leetcode.com/problems/course-schedule/ | Cycle detection — does valid order exist? |
| 2 | Course Schedule II | https://leetcode.com/problems/course-schedule-ii/ | Return the actual topological order |
| 3 | Alien Dictionary | https://leetcode.com/problems/alien-dictionary/ | Build graph from character ordering |
| 4 | Minimum Height Trees | https://leetcode.com/problems/minimum-height-trees/ | Peel leaves layer by layer |

---

### PATTERN 12 — Dynamic Programming — Linear / Fibonacci

**Recognise when:**
- "Number of ways", "can you reach", "minimum cost" on a 1D sequence.
- Each state depends on a fixed number of previous states.

**Core template:**
```java
int[] dp = new int[n + 1];
dp[0] = baseCase0;
dp[1] = baseCase1;

for (int i = 2; i <= n; i++) {
    // Current state is a function of one or two previous states
    dp[i] = dp[i - 1] + dp[i - 2]; // example: climbing stairs
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Climbing Stairs | https://leetcode.com/problems/climbing-stairs/ | Fibonacci recurrence |
| 2 | House Robber | https://leetcode.com/problems/house-robber/ | Skip adjacent; dp[i] = max(dp[i-1], dp[i-2]+num) |
| 3 | Decode Ways | https://leetcode.com/problems/decode-ways/ | Conditional transitions |
| 4 | Jump Game | https://leetcode.com/problems/jump-game/ | Greedy or DP: track max reachable index |
| 5 | Min Cost Climbing Stairs | https://leetcode.com/problems/min-cost-climbing-stairs/ | Cost at each step |

---

### PATTERN 13 — Dynamic Programming — 0/1 Knapsack and Subset Sum

**Recognise when:**
- "Can you form a target sum using a subset?", "partition equal subset".
- Each item is either **included or excluded** (0/1 decision).

**Core template (subset-sum — 1D optimisation):**
```java
boolean[] dp = new boolean[target + 1];
dp[0] = true; // empty subset sums to 0

for (int num : nums) {
    // Walk backwards to prevent using the same element twice
    for (int capacity = target; capacity >= num; capacity--) {
        dp[capacity] = dp[capacity] || dp[capacity - num];
    }
}
return dp[target];
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Partition Equal Subset Sum | https://leetcode.com/problems/partition-equal-subset-sum/ | Core 0/1 knapsack as subset sum |
| 2 | Target Sum | https://leetcode.com/problems/target-sum/ | Count ways to assign +/- signs |
| 3 | Coin Change | https://leetcode.com/problems/coin-change/ | Unbounded knapsack — minimum coins |
| 4 | Coin Change II | https://leetcode.com/problems/coin-change-ii/ | Count combinations (unbounded) |
| 5 | Last Stone Weight II | https://leetcode.com/problems/last-stone-weight-ii/ | Split into two groups with equal sum |

---

### PATTERN 14 — Dynamic Programming — LCS / LIS / Edit Distance

**Recognise when:**
- Two sequences and a relationship between elements at different indices.
- Keywords: "longest common", "edit distance", "minimum operations", "match characters".

**Core template (2D DP):**
```java
int[][] dp = new int[m + 1][n + 1];
// dp[i][j] = answer for s1[0..i-1] and s2[0..j-1]

for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            dp[i][j] = dp[i - 1][j - 1] + 1; // characters match — extend
        } else {
            dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // skip one character
        }
    }
}
return dp[m][n];
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Longest Common Subsequence | https://leetcode.com/problems/longest-common-subsequence/ | Core 2D DP |
| 2 | Longest Increasing Subsequence | https://leetcode.com/problems/longest-increasing-subsequence/ | 1D DP + binary search variant |
| 3 | Edit Distance | https://leetcode.com/problems/edit-distance/ | Three operations: insert, delete, replace |
| 4 | Distinct Subsequences | https://leetcode.com/problems/distinct-subsequences/ | Count ways, not length |
| 5 | Longest Palindromic Subsequence | https://leetcode.com/problems/longest-palindromic-subsequence/ | LCS of string and its reverse |

---

### PATTERN 15 — Monotonic Stack

**Recognise when:**
- "Next greater element", "previous smaller element", "span", "largest rectangle".
- You need to quickly find, for each element, the **nearest element that is larger or smaller**.

**Core template (next greater element — monotonic decreasing stack):**
```java
int[] result = new int[nums.length];
Arrays.fill(result, -1); // default: no greater element
Deque<Integer> stack = new ArrayDeque<>(); // stores indices

for (int i = 0; i < nums.length; i++) {
    // Pop elements whose "next greater" is nums[i]
    while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        int index = stack.pop();
        result[index] = nums[i]; // nums[i] is the next greater for nums[index]
    }
    stack.push(i);
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Next Greater Element I | https://leetcode.com/problems/next-greater-element-i/ | Monotonic stack basics |
| 2 | Daily Temperatures | https://leetcode.com/problems/daily-temperatures/ | Days until warmer temperature |
| 3 | Largest Rectangle in Histogram | https://leetcode.com/problems/largest-rectangle-in-histogram/ | Stack tracks left boundary for each bar |
| 4 | Trapping Rain Water | https://leetcode.com/problems/trapping-rain-water/ | Stack or two-pointer; water bounded by min of left/right max |
| 5 | Sum of Subarray Minimums | https://leetcode.com/problems/sum-of-subarray-minimums/ | Count contribution of each element as min |

---

### PATTERN 16 — Union-Find (Disjoint Set Union)

**Recognise when:**
- "Connected components", "merge groups", "detect cycle in undirected graph".
- Dynamic connectivity — edges are added over time.

**Core template:**
```java
int[] parent = new int[n];
int[] rank   = new int[n];

// Initialise: each node is its own root
for (int i = 0; i < n; i++) parent[i] = i;

// Find with path compression
int find(int x) {
    if (parent[x] != x) parent[x] = find(parent[x]); // path compression
    return parent[x];
}

// Union by rank
boolean union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX == rootY) return false; // already connected — cycle detected

    if (rank[rootX] < rank[rootY])  parent[rootX] = rootY;
    else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
    else { parent[rootY] = rootX; rank[rootX]++; }
    return true;
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Number of Connected Components in Undirected Graph | https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/ | Count distinct roots |
| 2 | Redundant Connection | https://leetcode.com/problems/redundant-connection/ | Return edge that forms cycle |
| 3 | Accounts Merge | https://leetcode.com/problems/accounts-merge/ | Union emails by shared account |
| 4 | Number of Islands | https://leetcode.com/problems/number-of-islands/ | DFS/BFS also works; UF alternative |
| 5 | Satisfiability of Equality Equations | https://leetcode.com/problems/satisfiability-of-equality-equations/ | Union equal pairs, check not-equal |

---

### PATTERN 17 — Trie (Prefix Tree)

**Recognise when:**
- "Prefix search", "autocomplete", "word dictionary", "longest word with common prefix".
- Multiple strings share common prefixes and you need fast lookups.

**Core template:**
```java
class TrieNode {
    TrieNode[] children = new TrieNode[26]; // for lowercase a-z
    boolean isEndOfWord = false;
}

class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) return false;
            node = node.children[index];
        }
        return node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) return false;
            node = node.children[index];
        }
        return true; // any word with this prefix can continue from here
    }
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Implement Trie (Prefix Tree) | https://leetcode.com/problems/implement-trie-prefix-tree/ | Core Trie template |
| 2 | Design Add and Search Words Data Structure | https://leetcode.com/problems/design-add-and-search-words-data-structure/ | Trie + DFS for wildcard `.` |
| 3 | Replace Words | https://leetcode.com/problems/replace-words/ | Trie for shortest matching root |
| 4 | Word Search II | https://leetcode.com/problems/word-search-ii/ | Trie + backtracking on a grid |

---

### PATTERN 18 — Greedy

**Recognise when:**
- Making a **locally optimal choice at each step** leads to a globally optimal result.
- Often works with **intervals** or **sequential decisions**.
- Ask yourself: "Can I prove that taking the greedy choice never makes the answer worse?"

**Core template (interval scheduling):**
```java
// Sort by end time — greedy: finish earliest first to leave room for others
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

int count = 1;
int lastEndTime = intervals[0][1];

for (int i = 1; i < intervals.length; i++) {
    int startTime = intervals[i][0];
    if (startTime >= lastEndTime) {
        // This interval starts after the last selected one ends — include it
        count++;
        lastEndTime = intervals[i][1];
    }
    // Otherwise skip — this interval overlaps and ends later
}
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Jump Game | https://leetcode.com/problems/jump-game/ | Track farthest reachable index |
| 2 | Jump Game II | https://leetcode.com/problems/jump-game-ii/ | Minimum jumps — greedy BFS |
| 3 | Gas Station | https://leetcode.com/problems/gas-station/ | Greedy: if total gain ≥ 0, solution exists; start after last deficit |
| 4 | Non-overlapping Intervals | https://leetcode.com/problems/non-overlapping-intervals/ | Maximise kept intervals by sorting on end time |
| 5 | Assign Cookies | https://leetcode.com/problems/assign-cookies/ | Sort both, match greedily |

---

### PATTERN 19 — Prefix Sum / Hash Map for Range Queries

**Recognise when:**
- "Subarray sum equals k", "number of subarrays with sum divisible by k".
- You need to quickly answer "what is the sum of elements from index i to j?"

**Core template (subarray sum equals k):**
```java
// Key insight: prefixSum[j] - prefixSum[i] = k  ⟹  prefixSum[i] = prefixSum[j] - k
// Store each prefix sum we have seen in a map so we can look up 'prefixSum[j] - k'.
Map<Integer, Integer> prefixSumCount = new HashMap<>();
prefixSumCount.put(0, 1); // empty prefix sums to 0

int runningSum = 0;
int totalSubarrays = 0;

for (int num : nums) {
    runningSum += num;
    int needed = runningSum - k;

    // How many times has this 'needed' prefix sum appeared before?
    totalSubarrays += prefixSumCount.getOrDefault(needed, 0);

    // Record the current prefix sum
    prefixSumCount.merge(runningSum, 1, Integer::sum);
}
return totalSubarrays;
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Range Sum Query — Immutable | https://leetcode.com/problems/range-sum-query-immutable/ | Basic prefix sum array |
| 2 | Subarray Sum Equals K | https://leetcode.com/problems/subarray-sum-equals-k/ | Prefix sum + hash map |
| 3 | Continuous Subarray Sum | https://leetcode.com/problems/continuous-subarray-sum/ | Prefix sum modulo k |
| 4 | Count Number of Nice Subarrays | https://leetcode.com/problems/count-number-of-nice-subarrays/ | Prefix sum of "is odd" |
| 5 | Subarray Sums Divisible by K | https://leetcode.com/problems/subarray-sums-divisible-by-k/ | Hash map of remainders |

---

### PATTERN 20 — Matrix / 2D DP

**Recognise when:**
- Grid-based problems: "minimum path", "unique paths", "number of paths".
- `dp[i][j]` depends on `dp[i-1][j]` (above) or `dp[i][j-1]` (left).

**Core template:**
```java
int[][] dp = new int[rows][cols];
dp[0][0] = initial;

// Fill first row and column (boundary conditions)
for (int c = 1; c < cols; c++) dp[0][c] = dp[0][c - 1] + something;
for (int r = 1; r < rows; r++) dp[r][0] = dp[r - 1][0] + something;

// Fill rest of the table
for (int r = 1; r < rows; r++) {
    for (int c = 1; c < cols; c++) {
        dp[r][c] = transition(dp[r - 1][c], dp[r][c - 1], grid[r][c]);
    }
}
return dp[rows - 1][cols - 1];
```

**Foundational problems:**

| # | Problem | Link | What it tests |
|---|---------|------|--------------|
| 1 | Unique Paths | https://leetcode.com/problems/unique-paths/ | Base 2D DP — count paths |
| 2 | Minimum Path Sum | https://leetcode.com/problems/minimum-path-sum/ | Cost accumulation |
| 3 | Maximal Square | https://leetcode.com/problems/maximal-square/ | dp[i][j] = side length of largest all-1 square ending here |
| 4 | Triangle | https://leetcode.com/problems/triangle/ | Bottom-up DP on triangle |
| 5 | Dungeon Game | https://leetcode.com/problems/dungeon-game/ | Reverse DP — start from bottom-right |

---

### PATTERN RECOGNITION MASTER TABLE

Use this table during your first minute of reading a new problem.

| Signal in the problem | Most likely pattern(s) |
|-----------------------|----------------------|
| Sorted array, find pair/triplet | Two Pointers |
| Subarray / substring, length or count | Sliding Window |
| Linked list, cycle, middle | Fast & Slow Pointers |
| List of intervals, merge/count | Merge Intervals |
| Sorted array search, rotated, "find minimum" | Modified Binary Search |
| "Binary search on the answer" (feasibility) | Modified Binary Search |
| Tree, level-by-level, shortest path in grid | BFS |
| Tree, path sum, ancestor, subtree property | DFS |
| Top K, K largest/smallest/frequent | Heap (Top K pattern) |
| Generate all, enumerate, constraint solve | Backtracking |
| Grid, islands, connected components | Graph BFS/DFS |
| Dependencies, course prerequisites, ordering | Topological Sort |
| 1D sequence, ways/min cost, Fibonacci-like | Linear DP |
| Pick items, max value with capacity | 0/1 Knapsack DP |
| Two sequences, match characters | LCS / 2D DP |
| Next greater/smaller element | Monotonic Stack |
| Merge groups, detect cycle (undirected) | Union-Find |
| Prefix lookup, word dictionary | Trie |
| Locally optimal → globally optimal | Greedy |
| Sum of subarray equals target | Prefix Sum + Hash Map |
| Grid minimum path, count paths | 2D DP |
