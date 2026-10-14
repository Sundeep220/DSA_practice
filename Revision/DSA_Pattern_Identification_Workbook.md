# DSA Pattern Identification Workbook

**Instructions:** For each prompt, write (1) likely pattern, (2) a clue, (3) a tempting but flawed approach, (4) time complexity target, and (5) one edge case. Do not read the answer key until you finish.

## Exercises (shuffled; no topic labels)

1. Given an integer array that may contain negatives, count contiguous ranges whose sum equals `k`.
2. Given sorted integers, return unique triplets whose sum is zero.
3. Given a list of jobs with start/end times, find the minimum number of rooms required.
4. Given a directed prerequisite list, determine whether every course can be completed.
5. Given a stream of integers, report the median after every insertion.
6. Given a string and a target multiset of characters, find the shortest substring containing all target characters.
7. Given an integer x, return the smallest integer speed that completes a set of piles within h hours.
8. Given an array, return the next strictly greater value to the right for every index.
9. Given an undirected network, repeatedly add connections and answer whether two nodes are connected.
10. Given an array of non-negative values and many static range-sum queries, answer each query quickly.
11. Given a string, enumerate every possible partition where each part is a palindrome.
12. Given a graph with non-negative edge weights, find shortest distances from one source.
13. Given an array, return the kth largest value without needing the entire array sorted.
14. Given a set of numbers, determine whether some subset sums to a target.
15. Given a sequence and frequent point updates plus range-sum queries, support both efficiently.
16. Given a grid of land and water, count connected land regions.
17. Given a string, determine whether it can be segmented into dictionary words.
18. Given many range increments, apply them all and output the final array.
19. Given a binary tree, compute the maximum path sum where a path may start and end anywhere.
20. Given a number n, count primes smaller than n.
21. Given a list of intervals, remove the fewest intervals so the rest do not overlap.
22. Given an array, count how many smaller values appear to the right of each element.
23. Given a sequence of daily temperatures, determine how many days until a warmer temperature.
24. Given a string pattern and a large text, find all occurrences in linear time.
25. Given a set of at most 20 tasks, assign each task once while minimizing total cost.
26. Given a positive integer exponent, compute a power modulo m efficiently.
27. Given a tree, return node values level by level.
28. Given a sorted array with duplicates, find the first position whose value is at least target.
29. Given a set of events with start/end times, find the maximum number active at once.
30. Given a linked list, detect whether it contains a cycle and return its entry node.

## Answer key (read only after attempting)

| # | Primary pattern | Why / key clue | Typical target |
|---:|---|---|---|
| 1 | Prefix sum + HashMap | negatives invalidate normal sum-window monotonicity | O(n) |
| 2 | Sort + two pointers | sorted order permits inward movement; deduplicate | O(n²) |
| 3 | Sweep line or min-heap | overlapping intervals / earliest finishing room | O(n log n) |
| 4 | Topological sort or cycle detection | dependency graph | O(V+E) |
| 5 | Two heaps | lower and upper halves maintained balanced | O(log n) per insert |
| 6 | Variable sliding window | frequency deficit shrinks monotonically | O(n + alphabet/map work) |
| 7 | Binary search on answer | feasible speed is monotone | O(n log maxPile) |
| 8 | Monotonic stack | unresolved indices are popped by greater values | O(n) |
| 9 | Union-Find | incremental undirected connectivity | near O(1) amortized |
| 10 | Prefix sum | static range queries after preprocessing | O(n+q) |
| 11 | Backtracking | enumerate valid cuts and undo choices | output-sensitive |
| 12 | Dijkstra | non-negative weighted shortest path | O((V+E) log V) |
| 13 | Quickselect or heap | one kth order statistic | O(n) average / O(n log k) |
| 14 | Subset-sum DP or backtracking | choice per element; target state | O(n·target) for DP |
| 15 | Fenwick / segment tree | updates make static prefix sums expensive | O(log n) per operation |
| 16 | DFS/BFS | connected components in grid | O(rows·cols) |
| 17 | DP / memoized DFS | overlapping prefix segmentation states | roughly O(n·dictionary checks) |
| 18 | Difference array | range updates are accumulated at boundaries | O(n+q) |
| 19 | Tree DP | return best one-branch gain; global answer may join two branches | O(n) |
| 20 | Sieve of Eratosthenes | many primality queries below a bound | O(n log log n) |
| 21 | Greedy interval scheduling | retain earliest-ending compatible intervals | O(n log n) |
| 22 | Fenwick after coordinate compression or merge-sort counting | count values smaller to the right | O(n log n) |
| 23 | Monotonic stack | next greater element index | O(n) |
| 24 | KMP or Z algorithm | linear pattern matching without repeated comparisons | O(n+m) |
| 25 | Bitmask DP | small n, subset is state | O(n·2^n) or O(n²·2^n) |
| 26 | Fast exponentiation + modulo | exponent halves each iteration | O(log exponent) |
| 27 | BFS | queue and level boundaries | O(n) |
| 28 | Lower-bound binary search | first index satisfying monotone predicate | O(log n) |
| 29 | Sweep line / ordered events | active count changes at boundaries | O(n log n) |
| 30 | Fast/slow pointers | cycle entry follows Floyd's meeting invariant | O(n) time, O(1) space |

## Reflection prompts
- Which exercises tempted you to use sliding window when negative values were present?
- Which tasks required proving monotonicity before binary search?
- Which tasks were about finding one answer versus counting or enumerating many?
- Which data structures were needed because updates were frequent?
- For every wrong answer, write the specific assumption that failed.
