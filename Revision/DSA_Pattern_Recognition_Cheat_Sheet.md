# DSA Pattern Recognition Cheat Sheet

## First 60 seconds
1. What exactly is requested: existence, count, one optimum, all solutions, or repeated queries?
2. Is it contiguous (subarray/substring), order-preserving (subsequence), or arbitrary selection (subset)?
3. What do constraints permit?
4. Are values negative? Is the input sorted? Are updates frequent?
5. What invariant lets me discard candidates safely?

## Fast clues (hypotheses, not rules)

| Clue | Candidate pattern | Confirm by checking |
|---|---|---|
| Pair/triplet in sorted data | Two pointers | moving a pointer has a monotone effect |
| Longest/shortest contiguous range | Sliding window | validity changes monotonically; negatives may break it |
| Count subarrays with sum k | Prefix sum + HashMap | `prefix[r]-prefix[l]=k` |
| Many static range sums | Prefix sum | no updates after preprocessing |
| Many range additions | Difference array | updates can be accumulated then materialized |
| First/last true / minimum feasible X | Binary search on answer | feasibility predicate is monotone |
| Next greater/smaller, histogram | Monotonic stack | each index resolves once |
| Window maximum/minimum | Monotonic deque | candidates expire from front |
| Top K / repeatedly extract best | Heap | priority changes or online data |
| One kth element | Quickselect | average linear time acceptable |
| Shortest path, unweighted | BFS | each edge has equal cost |
| Shortest path, weighted non-negative | Dijkstra | weights non-negative |
| Prerequisites/order | Topological sort | directed graph; cycle means no valid order |
| Connectivity after merges | Union-Find | undirected component merges |
| All subsets/permutations/partitions | Backtracking | output itself may be exponential |
| Min/max/count ways, overlapping states | DP | state captures all future-relevant information |
| Locally optimal choice | Greedy candidate | prove exchange/stays-ahead property |
| Prefix dictionary queries | Trie | many words share prefixes |
| String pattern matching | KMP/Z/Rabin–Karp | need linear scan or repeated pattern queries |
| Point update + range sum | Fenwick | additive aggregation |
| Rich range operations | Segment tree | custom merge/range update requirements |
| Huge exponent | Fast exponentiation | halve exponent each step |
| Prime checks for many numbers ≤ N | Sieve | precompute primes |
| Small n (≈20) + subsets/states | Bitmask DP | state count 2^n is feasible |

## Constraint guide
| n | Candidate complexity |
|---|---|
| ≤ 10 | brute force, permutations/backtracking with pruning |
| ≤ 20 | 2^n or n·2^n may fit |
| ≤ 100 | O(n³) may fit |
| ≤ 1,000 | O(n²) often plausible |
| ≤ 100,000 | O(n log n) or O(n) usually expected |
| ≤ 1,000,000 | O(n) / carefully implemented O(n log n) |
| Huge / streaming | logarithmic, amortized, incremental, or math-based |

Always adjust for time limits, number of test cases, memory, and constants.

## Distinctions that change the answer
- **Negative values:** ordinary sum sliding window often fails; use prefix sums/maps or monotonic deque.
- **Subarray vs subsequence:** contiguous vs order-preserving with gaps.
- **Find vs count:** one witness may allow early exit; counting often needs frequencies/DP.
- **Static vs updated:** prefix sums for static queries; Fenwick/segment tree for updates.
- **All answers vs best answer:** backtracking may be necessary; optimization can use DP/greedy.
- **Unweighted vs weighted graph:** BFS vs Dijkstra; negative edges require another algorithm.
- **Exactly K:** sometimes `atMost(K) - atMost(K-1)`.
- **Duplicates:** define whether equal values pop in a monotonic stack and how sorted two-pointer outputs are deduplicated.
- **Overflow:** use `long` for large sums/products; cast before multiplying.

## Similar-pattern comparison
| A vs B | Use A when | Use B when |
|---|---|---|
| Sliding window vs prefix + map | monotone contiguous window | exact counts, negatives |
| Two pointers vs window | pair relation / sorted data | maintain contiguous range state |
| Greedy vs DP | proof supports locally optimal choice | future choices interact |
| BFS vs DFS vs Dijkstra | unweighted shortest path | exploration / components | non-negative weighted shortest path |
| Heap vs monotonic stack | top K / repeated best | next greater/smaller |
| Direct binary search vs answer search | sorted elements | monotone feasibility |
| Backtracking vs DP | enumerate paths/solutions | repeated subproblems |
| Fenwick vs segment tree | additive prefix/range query | richer range operations |
| DSU vs DFS | incremental connectivity | paths/traversal |
| Quickselect vs heap | one kth selection | streaming/repeated top K |

## Java safety reminders
- `PriorityQueue` is a min-heap by default.
- Use `ArrayDeque` for stack/queue; it does not accept null.
- Avoid comparator subtraction (`a - b`); use `Integer.compare(a,b)`.
- Use `lo + (hi - lo) / 2`.
- `Math.abs(Integer.MIN_VALUE)` overflows.
- For long bit masks use `1L << bit`.
- `x & (x - 1)` clears the lowest set bit.
- In prefix-sum count, seed `freq[0] = 1`.
- In BFS, mark visited when enqueuing.
- In backtracking, undo each choice.
