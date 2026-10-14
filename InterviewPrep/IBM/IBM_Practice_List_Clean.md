# IBM Senior SWE: Machine Coding and DSA Practice List

**Purpose:** Backup preparation for a live round described as DSA and scenario-based coding. Public reports do not confirm machine coding as a standard round for this role, so Part 1 is insurance. Spend most of your time on Parts 2 and 3.

**Language:** Java 17

## Part 1: Design (machine coding) problems, in order

### 1. LRU Cache (done)

**Description:** Fixed-capacity cache with O(1) `get` and `put`. Evicts the least recently used entry. Includes thread-safe and TTL variants.

**Link:** https://leetcode.com/problems/lru-cache/

### 2. Queue using a linked list (unique variation)

**Description:** Implement a queue on a linked list where `enqueue` rejects values already in the queue. Confirm the exact rules before starting.

### 3. Infinite stream and bounded queue

**Description:** Process an endless stream of values with bounded memory, using a fixed-capacity queue.

### 4. Key-value store with TTL

**Description:** Store string key/value pairs with an optional time-to-live. Support `put`, `get`, and `delete`. Expired entries are never returned.

### 5. Time-based key-value store

**Description:** `set(key, value, timestamp)` and `get(key, timestamp)`, which returns the latest value at or before the timestamp. Same as DSA Problem 3 in Part 2.

**Link:** https://leetcode.com/problems/time-based-key-value-store/

### 6. Key-value store with TTL and get_average()

**Description:** Timestamped puts arrive in order. `get` respects the TTL, and `getAverage(timestamp)` averages the non-expired values. Same as DSA Problem 9 in Part 2 (Confluent phone-screen problem).

### 7. Rate limiter

**Description:** Allow at most `limit` requests per client per fixed time window. Discuss boundary bursts, cleanup of inactive clients, and how a token bucket or sliding window changes behaviour.

**Link:** https://leetcode.com/problems/logger-rate-limiter/ (related)

### 8. Producer-consumer bounded queue

**Description:** Thread-safe `put` and `take` that block when the queue is full or empty, using `wait/notifyAll` or `BlockingQueue`.

**Link:** https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/BlockingQueue.html

### 9. Task scheduler

**Description:** Add jobs with priorities and execute the next eligible job. Uses a priority queue and job state.

**Link:** https://leetcode.com/problems/task-scheduler/ (related; the design version is a different problem)

### 10. Parking lot

**Description:** Vehicle entry and exit, spot assignment, tickets, and fees. Focus on OOP (interfaces, enums), choosing the smallest compatible spot, preventing double exits, and integer money amounts.

**Link:** https://www.geeksforgeeks.org/system-design/design-parking-lot-using-object-oriented-principles/

### 11. Elevator controller

**Description:** Requests, direction, floor movement, and scheduling. Focus on state modelling and strategy.

**Link:** https://www.geeksforgeeks.org/system-design/elevator-system-design/

### 12. Movie ticket booking

**Description:** Show seats, reserve, confirm, and cancel. Focus on state and avoiding double booking under concurrency.

### 13. Library management system

**Description:** Search books, checkout and return, and due dates. Focus on OOP and domain modelling.

### 14. Expense splitter

**Description:** Add expenses, split equally or by exact amounts, and track balances. Focus on maps, validation, and integer money.

### 15. Notification service

**Description:** Email, SMS, and push behind one interface, with a retry policy. Focus on the strategy pattern and retries.

### 16. File or log aggregator

**Description:** Process events, group counts, and return the top K. Focus on streaming, maps, heaps, and bounded memory.

### Extra design practice

- **Design Circular Queue:** fixed-size ring buffer. https://leetcode.com/problems/design-circular-queue/
- **Design Hit Counter:** count hits in a sliding window. https://leetcode.com/problems/design-hit-counter/
- **Find Median from Data Stream:** two heaps. https://leetcode.com/problems/find-median-from-data-stream/
- **Design Twitter:** feeds and a follow graph. https://leetcode.com/problems/design-twitter/

## Part 2: DSA problem bank, in tier order

Each tier adds one concept that the next tier builds on.

### Tier 1: Hash tables and arrays

#### 1. Two Sum

**Description:** Return the indices of two numbers that add up to the target. Use a hash map of complements in one pass.

**Link:** https://leetcode.com/problems/two-sum/

#### 2. Valid Sudoku *(Confluent)*

**Description:** Check that each row, column, and 3x3 box has no repeated digits. Track digits seen per row, column, and box.

**Link:** https://leetcode.com/problems/valid-sudoku/

#### 3. Time-based key-value store *(Confluent)*

**Description:** `set` stores a value at a timestamp. `get` returns the latest value at or before a timestamp. Use sorted entries per key with binary search.

**Link:** https://leetcode.com/problems/time-based-key-value-store/

### Tier 2: Sliding window and two pointers

#### 4. Longest subarray with sum at most budget *(Confluent)*

**Description:** Given non-negative costs and a budget B, find the length of the longest contiguous segment whose cost is at most B.

**Link:** No exact match. Practise on paper.

#### 5. Sliding window maximum

**Description:** Return the maximum of each window of size k as it moves across the array. Use a monotonic deque.

**Link:** https://leetcode.com/problems/sliding-window-maximum/

### Tier 3: Backtracking

#### 6. Combination sum

**Description:** Return all unique combinations of candidates that sum to the target. A candidate may be reused. Backtrack with a start index and pruning.

**Link:** https://leetcode.com/problems/combination-sum/

#### 7. Sudoku solver *(Confluent)*

**Description:** Fill the empty cells so the board satisfies all Sudoku rules. Backtrack with constraint checks.

**Link:** https://leetcode.com/problems/sudoku-solver/

### Tier 4: Design problems

#### 8. LRU cache *(Confluent)*

**Description:** Covered in Part 1, item 1.

**Link:** https://leetcode.com/problems/lru-cache/

#### 9. Key-value store with TTL and get_average() *(Confluent)*

**Description:** Covered in Part 1, item 6. Timestamped puts, TTL-aware `get`, and `getAverage`.

**Link:** No direct match (Confluent phone-screen problem).

### Tier 5: Confluent scenario problems

#### 10. Word and phrase search in documents *(Confluent)*

**Description:** `searchWord` returns the documents that contain a word. `searchPhrase` returns the documents that contain the words in order. Build an inverted index for words, then check consecutive positions for phrases.

**Link:** No LeetCode equivalent.

#### 11. Implement `tail -n` *(Confluent)*

**Description:** Return the last N lines of a file that may be larger than memory. Seek from the end in blocks and count newlines.

**Link:** No LeetCode equivalent.

### Tier 6: Hard

#### 12. Regular expression matching

**Description:** Match a string against a pattern with `.` and `*`, covering the whole string. Use recursion with memoisation or DP.

**Link:** https://leetcode.com/problems/regular-expression-matching/

#### 13. Wildcard matching *(Confluent)*

**Description:** Match a string against a pattern with `?` and `*`, covering the whole string. Use bottom-up DP.

**Link:** https://leetcode.com/problems/wildcard-matching/

#### 14. Minimum time to eat all grains *(Confluent)*

**Description:** Hens on a number line move one step per minute to eat grains. Find the minimum time for all grains to be eaten. Binary search on the answer with a greedy check.

**Link:** https://leetcode.com/problems/minimum-time-to-eat-all-grains/

## Part 3: DSA patterns

Each pattern lists when to use it, then five practice problems from easiest to hardest. Some problems appear under two patterns on purpose.

### Pattern 1: Two pointers

**Use when:** the input is sorted, you need a pair or triplet, or you must modify in place.

1. **Valid Palindrome:** two pointers inward, skipping non-alphanumeric characters. https://leetcode.com/problems/valid-palindrome/
2. **Two Sum II (sorted input):** classic pair search. https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
3. **3Sum:** fix one element, then scan the rest with two pointers. https://leetcode.com/problems/3sum/
4. **Container With Most Water:** greedy pointer movement. https://leetcode.com/problems/container-with-most-water/
5. **Remove Duplicates from Sorted Array:** in-place write pointer. https://leetcode.com/problems/remove-duplicates-from-sorted-array/

### Pattern 2: Sliding window

**Use when:** the problem asks about a contiguous subarray or substring with "longest", "shortest", "at most k", or "exactly k".

1. **Maximum Average Subarray I:** fixed window of size k. https://leetcode.com/problems/maximum-average-subarray-i/
2. **Longest Substring Without Repeating Characters:** variable window with a frequency map. https://leetcode.com/problems/longest-substring-without-repeating-characters/
3. **Minimum Size Subarray Sum:** shrink as soon as the sum reaches the target. https://leetcode.com/problems/minimum-size-subarray-sum/
4. **Fruit Into Baskets:** at most two distinct values. https://leetcode.com/problems/fruit-into-baskets/
5. **Minimum Window Substring:** hard; two frequency maps. https://leetcode.com/problems/minimum-window-substring/

### Pattern 3: Fast and slow pointers

**Use when:** a linked list may contain a cycle, or you need its middle.

1. **Middle of the Linked List:** slow reaches the middle when fast reaches the end. https://leetcode.com/problems/middle-of-the-linked-list/
2. **Linked List Cycle:** basic cycle detection. https://leetcode.com/problems/linked-list-cycle/
3. **Linked List Cycle II:** find the cycle start. https://leetcode.com/problems/linked-list-cycle-ii/
4. **Happy Number:** cycle detection on a sequence of numbers. https://leetcode.com/problems/happy-number/
5. **Find the Duplicate Number:** array as an implicit linked list with a cycle. https://leetcode.com/problems/find-the-duplicate-number/

### Pattern 4: Merge intervals

**Use when:** the input is a list of intervals. Sort by start time first.

1. **Merge Intervals:** core pattern. https://leetcode.com/problems/merge-intervals/
2. **Insert Interval:** merge a new interval into a sorted list. https://leetcode.com/problems/insert-interval/
3. **Non-overlapping Intervals:** greedy, minimum removals. https://leetcode.com/problems/non-overlapping-intervals/
4. **Meeting Rooms:** can one person attend all? https://leetcode.com/problems/meeting-rooms/
5. **Meeting Rooms II:** minimum rooms needed. https://leetcode.com/problems/meeting-rooms-ii/

### Pattern 5: Modified binary search

**Use when:** the array is sorted or rotated, you need a boundary, or you can binary search on the answer.

1. **Binary Search:** classic template. https://leetcode.com/problems/binary-search/
2. **Search in Rotated Sorted Array:** determine which half is sorted. https://leetcode.com/problems/search-in-rotated-sorted-array/
3. **Find Minimum in Rotated Sorted Array:** binary search for the inflection point. https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
4. **Koko Eating Bananas:** binary search on the answer. https://leetcode.com/problems/koko-eating-bananas/
5. **Capacity To Ship Packages Within D Days:** search on the answer with a feasibility check. https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/

### Pattern 6: Tree BFS

**Use when:** you process a tree level by level, or find shortest paths on a grid.

1. **Binary Tree Level Order Traversal:** core BFS template. https://leetcode.com/problems/binary-tree-level-order-traversal/
2. **Binary Tree Zigzag Level Order Traversal:** alternate direction per level. https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
3. **Binary Tree Right Side View:** last node of each level. https://leetcode.com/problems/binary-tree-right-side-view/
4. **Maximum Depth of Binary Tree:** count levels. https://leetcode.com/problems/maximum-depth-of-binary-tree/
5. **Rotting Oranges:** multi-source BFS on a grid. https://leetcode.com/problems/rotting-oranges/

### Pattern 7: Tree DFS

**Use when:** the problem involves root-to-leaf paths, subtree properties, or ancestors.

1. **Path Sum:** root-to-leaf DFS. https://leetcode.com/problems/path-sum/
2. **Diameter of Binary Tree:** post-order, left depth plus right depth. https://leetcode.com/problems/diameter-of-binary-tree/
3. **Binary Tree Maximum Path Sum:** track the global maximum in post-order. https://leetcode.com/problems/binary-tree-maximum-path-sum/
4. **Validate Binary Search Tree:** pass a valid range down each path. https://leetcode.com/problems/validate-binary-search-tree/
5. **Lowest Common Ancestor of a BST:** use BST ordering to navigate. https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/

### Pattern 8: Top K with heaps

**Use when:** you need the k largest, k smallest, k most frequent, or a running median.

1. **Kth Largest Element in an Array:** min-heap of size k. https://leetcode.com/problems/kth-largest-element-in-an-array/
2. **Top K Frequent Elements:** frequency map plus heap. https://leetcode.com/problems/top-k-frequent-elements/
3. **K Closest Points to Origin:** max-heap of size k. https://leetcode.com/problems/k-closest-points-to-origin/
4. **Task Scheduler:** max-heap with greedy idle slots. https://leetcode.com/problems/task-scheduler/
5. **Find Median from Data Stream:** two heaps. https://leetcode.com/problems/find-median-from-data-stream/

### Pattern 9: Backtracking

**Use when:** the problem asks for all combinations, permutations, or valid configurations.

1. **Subsets:** include or exclude each element. https://leetcode.com/problems/subsets/
2. **Permutations:** use a visited array. https://leetcode.com/problems/permutations/
3. **Letter Combinations of a Phone Number:** iterate over mapped characters. https://leetcode.com/problems/letter-combinations-of-a-phone-number/
4. **Word Search:** DFS on a grid with visited marks. https://leetcode.com/problems/word-search/
5. **N-Queens:** row-by-row placement with column and diagonal checks. https://leetcode.com/problems/n-queens/

### Pattern 10: Graph BFS and DFS

**Use when:** the input is a grid or adjacency list, and you need connectivity or shortest unweighted paths.

1. **Number of Islands:** flood fill to count components. https://leetcode.com/problems/number-of-islands/
2. **Clone Graph:** BFS with a node map. https://leetcode.com/problems/clone-graph/
3. **Course Schedule:** cycle detection with Kahn's algorithm. https://leetcode.com/problems/course-schedule/
4. **Pacific Atlantic Water Flow:** reverse BFS from both oceans. https://leetcode.com/problems/pacific-atlantic-water-flow/
5. **Word Ladder:** BFS on an implicit graph. https://leetcode.com/problems/word-ladder/

### Pattern 11: Topological sort

**Use when:** tasks have dependencies, such as prerequisites or build order.

1. **Course Schedule:** does a valid order exist? https://leetcode.com/problems/course-schedule/
2. **Course Schedule II:** return the order. https://leetcode.com/problems/course-schedule-ii/
3. **Alien Dictionary:** build a graph from character ordering. https://leetcode.com/problems/alien-dictionary/
4. **Minimum Height Trees:** peel leaves layer by layer. https://leetcode.com/problems/minimum-height-trees/

### Pattern 12: Linear DP

**Use when:** the problem asks for a number of ways, reachability, or minimum cost on a 1D sequence.

1. **Climbing Stairs:** Fibonacci recurrence. https://leetcode.com/problems/climbing-stairs/
2. **House Robber:** skip adjacent houses. https://leetcode.com/problems/house-robber/
3. **Decode Ways:** conditional transitions. https://leetcode.com/problems/decode-ways/
4. **Jump Game:** track the farthest reachable index. https://leetcode.com/problems/jump-game/
5. **Min Cost Climbing Stairs:** cost at each step. https://leetcode.com/problems/min-cost-climbing-stairs/

### Pattern 13: 0/1 knapsack and subset sum

**Use when:** each item is either included or excluded, and you need to reach a target.

1. **Partition Equal Subset Sum:** core subset sum. https://leetcode.com/problems/partition-equal-subset-sum/
2. **Target Sum:** count ways to assign signs. https://leetcode.com/problems/target-sum/
3. **Coin Change:** unbounded knapsack, minimum coins. https://leetcode.com/problems/coin-change/
4. **Coin Change II:** count combinations. https://leetcode.com/problems/coin-change-ii/
5. **Last Stone Weight II:** split into two groups with near-equal sums. https://leetcode.com/problems/last-stone-weight-ii/

### Pattern 14: LCS, LIS, and edit distance

**Use when:** two sequences are compared, or the problem asks for a longest common, minimum operations, or matching characters.

1. **Longest Common Subsequence:** core 2D DP. https://leetcode.com/problems/longest-common-subsequence/
2. **Longest Increasing Subsequence:** 1D DP, with a binary search variant. https://leetcode.com/problems/longest-increasing-subsequence/
3. **Edit Distance:** insert, delete, and replace. https://leetcode.com/problems/edit-distance/
4. **Distinct Subsequences:** count ways. https://leetcode.com/problems/distinct-subsequences/
5. **Longest Palindromic Subsequence:** LCS of a string and its reverse. https://leetcode.com/problems/longest-palindromic-subsequence/

### Pattern 15: Monotonic stack

**Use when:** you need the nearest greater or smaller element for each position.

1. **Next Greater Element I:** monotonic stack basics. https://leetcode.com/problems/next-greater-element-i/
2. **Daily Temperatures:** days until a warmer day. https://leetcode.com/problems/daily-temperatures/
3. **Largest Rectangle in Histogram:** stack tracks the left boundary. https://leetcode.com/problems/largest-rectangle-in-histogram/
4. **Trapping Rain Water:** stack or two pointers. https://leetcode.com/problems/trapping-rain-water/
5. **Sum of Subarray Minimums:** contribution of each element as the minimum. https://leetcode.com/problems/sum-of-subarray-minimums/

### Pattern 16: Union-Find

**Use when:** you merge groups, count connected components, or detect a cycle in an undirected graph.

1. **Number of Connected Components in an Undirected Graph:** count distinct roots. https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/
2. **Redundant Connection:** return the edge that forms a cycle. https://leetcode.com/problems/redundant-connection/
3. **Accounts Merge:** union emails by shared account. https://leetcode.com/problems/accounts-merge/
4. **Number of Islands:** union-find alternative to BFS or DFS. https://leetcode.com/problems/number-of-islands/
5. **Satisfiability of Equality Equations:** union equal pairs, then check the not-equal pairs. https://leetcode.com/problems/satisfiability-of-equality-equations/

### Pattern 17: Trie

**Use when:** you need prefix search, autocomplete, or a word dictionary.

1. **Implement Trie (Prefix Tree):** core template. https://leetcode.com/problems/implement-trie-prefix-tree/
2. **Design Add and Search Words Data Structure:** trie with DFS for the `.` wildcard. https://leetcode.com/problems/design-add-and-search-words-data-structure/
3. **Replace Words:** shortest matching root. https://leetcode.com/problems/replace-words/
4. **Word Search II:** trie with backtracking on a grid. https://leetcode.com/problems/word-search-ii/

### Pattern 18: Greedy

**Use when:** a locally best choice at each step leads to a globally best result. Often works with intervals or sequential decisions.

1. **Jump Game II:** minimum jumps, greedy BFS. https://leetcode.com/problems/jump-game-ii/
2. **Gas Station:** if total gain is at least zero, start after the last deficit. https://leetcode.com/problems/gas-station/
3. **Non-overlapping Intervals:** keep the most intervals by sorting on end time. https://leetcode.com/problems/non-overlapping-intervals/
4. **Assign Cookies:** sort both and match greedily. https://leetcode.com/problems/assign-cookies/
5. **Jump Game:** track the farthest reachable index. https://leetcode.com/problems/jump-game/

### Pattern 19: Prefix sum and hash map

**Use when:** you need subarray sums, or range sums across many queries.

1. **Range Sum Query (Immutable):** basic prefix sum array. https://leetcode.com/problems/range-sum-query-immutable/
2. **Subarray Sum Equals K:** prefix sum plus hash map. https://leetcode.com/problems/subarray-sum-equals-k/
3. **Continuous Subarray Sum:** prefix sums modulo k. https://leetcode.com/problems/continuous-subarray-sum/
4. **Count Number of Nice Subarrays:** prefix count of odd numbers. https://leetcode.com/problems/count-number-of-nice-subarrays/
5. **Subarray Sums Divisible by K:** hash map of remainders. https://leetcode.com/problems/subarray-sums-divisible-by-k/

### Pattern 20: 2D DP

**Use when:** the problem is on a grid, and each cell depends on the cell above or to the left.

1. **Unique Paths:** count paths. https://leetcode.com/problems/unique-paths/
2. **Minimum Path Sum:** cost accumulation. https://leetcode.com/problems/minimum-path-sum/
3. **Maximal Square:** side length of the largest all-1 square ending at each cell. https://leetcode.com/problems/maximal-square/
4. **Triangle:** bottom-up DP. https://leetcode.com/problems/triangle/
5. **Dungeon Game:** reverse DP from the bottom-right. https://leetcode.com/problems/dungeon-game/
