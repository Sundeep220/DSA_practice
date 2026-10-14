# DSA LeetCode Practice Catalog (Java 17)

> Official LeetCode URLs are linked directly. A web search was used to cross-check the pattern taxonomy and selected topic references; this catalog has **not** undergone an automated URL-by-URL status check, so links are not represented as individually verified.

Priority: **Must Do** = core interview foundation; **Should Do** = useful breadth; **Advanced** = specialist or hard variation.

## Arrays, Hashing & Prefix Sums

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Two Sum](https://leetcode.com/problems/two-sum/) | Easy | Hash map | complement lookup | Must Do |
| [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) | Easy | Hash set | duplicate detection | Must Do |
| [Valid Anagram](https://leetcode.com/problems/valid-anagram/) | Easy | Frequency counting | character counts | Must Do |
| [Group Anagrams](https://leetcode.com/problems/group-anagrams/) | Medium | Hashing | canonical frequency/sort key | Must Do |
| [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/) | Medium | Prefix/suffix products | exclude current index without division | Must Do |
| [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/) | Medium | Kadane / DP | best contiguous sum | Must Do |
| [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/) | Medium | Prefix sum + HashMap | count prior prefix sums | Must Do |
| [Continuous Subarray Sum](https://leetcode.com/problems/continuous-subarray-sum/) | Medium | Prefix remainder | modular prefix states | Should Do |
| [Subarray Sums Divisible by K](https://leetcode.com/problems/subarray-sums-divisible-by-k/) | Medium | Prefix sum + modulo | count equal remainders | Should Do |
| [Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/) | Easy | Prefix sum | many static range queries | Must Do |
| [Range Sum Query 2D - Immutable](https://leetcode.com/problems/range-sum-query-2d-immutable/) | Medium | 2D prefix sum | rectangle sum in O(1) | Should Do |
| [Corporate Flight Bookings](https://leetcode.com/problems/corporate-flight-bookings/) | Medium | Difference array | many range updates | Should Do |
| [Contiguous Array](https://leetcode.com/problems/contiguous-array/) | Medium | Prefix sum + HashMap | equal zeros and ones | Should Do |

## Two Pointers & Sliding Window

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Valid Palindrome](https://leetcode.com/problems/valid-palindrome/) | Easy | Two pointers | compare inward | Must Do |
| [Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | Medium | Two pointers | sorted pair sum | Must Do |
| [3Sum](https://leetcode.com/problems/3sum/) | Medium | Sort + two pointers | deduplicate triplets | Must Do |
| [Container With Most Water](https://leetcode.com/problems/container-with-most-water/) | Medium | Two pointers + greedy proof | discard shorter boundary | Must Do |
| [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/) | Hard | Two pointers / monotonic stack | water bounded by sides | Must Do |
| [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) | Medium | Variable sliding window | maintain unique characters | Must Do |
| [Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/) | Medium | Sliding window + frequency | window size minus max frequency | Must Do |
| [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/) | Hard | Frequency window | smallest covering substring | Must Do |
| [Permutation in String](https://leetcode.com/problems/permutation-in-string/) | Medium | Fixed-size frequency window | anagram-sized window | Should Do |
| [Find All Anagrams in a String](https://leetcode.com/problems/find-all-anagrams-in-a-string/) | Medium | Fixed-size window | all matching frequency windows | Should Do |
| [Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii/) | Medium | Sliding window | at most k zeroes | Must Do |
| [Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum/) | Medium | Sliding window | positive values enable monotone shrinking | Must Do |
| [Subarray Product Less Than K](https://leetcode.com/problems/subarray-product-less-than-k/) | Medium | Sliding window | positive product threshold | Should Do |

## Binary Search

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Binary Search](https://leetcode.com/problems/binary-search/) | Easy | Binary search | sorted search space | Must Do |
| [Search Insert Position](https://leetcode.com/problems/search-insert-position/) | Easy | Lower bound | first valid insertion index | Must Do |
| [Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) | Medium | Boundary binary search | two monotone boundaries | Must Do |
| [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) | Medium | Modified binary search | one half is sorted | Must Do |
| [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/) | Medium | Modified binary search | find rotation pivot | Should Do |
| [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/) | Medium | Binary search on answer | minimum feasible speed | Must Do |
| [Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/) | Medium | Binary search on answer + greedy | feasibility is monotone | Should Do |
| [Split Array Largest Sum](https://leetcode.com/problems/split-array-largest-sum/) | Hard | Binary search on answer + greedy | minimize maximum partition sum | Advanced |

## Stacks, Monotonic Structures & Queues

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) | Easy | Stack | nested matching | Must Do |
| [Min Stack](https://leetcode.com/problems/min-stack/) | Medium | Stack design | track minimum in O(1) | Should Do |
| [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) | Medium | Monotonic stack | next warmer index | Must Do |
| [Next Greater Element II](https://leetcode.com/problems/next-greater-element-ii/) | Medium | Monotonic stack | circular next-greater | Should Do |
| [Largest Rectangle in Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/) | Hard | Monotonic stack | nearest smaller boundaries | Must Do |
| [Sum of Subarray Minimums](https://leetcode.com/problems/sum-of-subarray-minimums/) | Medium | Monotonic stack + counting | contribution by ownership spans | Advanced |
| [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/) | Hard | Monotonic deque | max of each window | Must Do |
| [Shortest Subarray with Sum at Least K](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/) | Hard | Prefix sum + monotonic deque | negative values break ordinary window | Advanced |
| [Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/) | Medium | Stack | postfix expression evaluation | Should Do |

## Linked Lists

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) | Easy | Pointer reversal | iterative link rewiring | Must Do |
| [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) | Easy | Fast/slow pointers | cycle detection | Must Do |
| [Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/) | Medium | Fast/slow pointers | find cycle entry | Should Do |
| [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/) | Easy | Dummy node + merge | stable pointer merge | Must Do |
| [Remove Nth Node From End of List](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) | Medium | Two pointers | fixed gap | Must Do |
| [Reorder List](https://leetcode.com/problems/reorder-list/) | Medium | Middle + reverse + merge | combine three subpatterns | Should Do |
| [LRU Cache](https://leetcode.com/problems/lru-cache/) | Medium | HashMap + doubly linked list | O(1) lookup and recency updates | Must Do |

## Trees & BSTs

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) | Easy | Tree DFS/BFS | height recurrence | Must Do |
| [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) | Medium | BFS | level-by-level processing | Must Do |
| [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) | Medium | DFS with bounds | global ordering invariant | Must Do |
| [Lowest Common Ancestor of a Binary Tree](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) | Medium | Tree DFS | combine target findings | Must Do |
| [Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/) | Hard | Tree DP | global answer plus one-branch return | Advanced |
| [Serialize and Deserialize Binary Tree](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) | Hard | Tree traversal / design | preserve shape and nulls | Should Do |
| [Kth Smallest Element in a BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/) | Medium | Inorder traversal | sorted order of BST | Should Do |
| [Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) | Medium | Recursion + index map | root partitions subtrees | Should Do |

## Heaps & Priority Queues

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/) | Medium | Quickselect / heap | selection without full sorting | Must Do |
| [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) | Medium | HashMap + heap / bucket sort | frequency ranking | Must Do |
| [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/) | Hard | Two heaps | maintain lower and upper halves | Should Do |
| [Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) | Hard | Min-heap | merge k frontiers | Must Do |
| [Task Scheduler](https://leetcode.com/problems/task-scheduler/) | Medium | Greedy + heap / counting | cooldown and maximum frequency | Should Do |
| [Meeting Rooms III](https://leetcode.com/problems/meeting-rooms-iii/) | Hard | Two heaps + simulation | availability and delayed meetings | Advanced |

## Graphs

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Number of Islands](https://leetcode.com/problems/number-of-islands/) | Medium | Grid DFS/BFS | connected components | Must Do |
| [Clone Graph](https://leetcode.com/problems/clone-graph/) | Medium | DFS/BFS + map | preserve graph identity and cycles | Should Do |
| [Course Schedule](https://leetcode.com/problems/course-schedule/) | Medium | Cycle detection / topological sort | prerequisite dependencies | Must Do |
| [Course Schedule II](https://leetcode.com/problems/course-schedule-ii/) | Medium | Topological sort | produce valid ordering | Must Do |
| [Rotting Oranges](https://leetcode.com/problems/rotting-oranges/) | Medium | Multi-source BFS | minimum time layers | Must Do |
| [Network Delay Time](https://leetcode.com/problems/network-delay-time/) | Medium | Dijkstra | non-negative weighted shortest paths | Should Do |
| [Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/) | Medium | Bounded shortest path / DP | edge-count constraint matters | Advanced |
| [Redundant Connection](https://leetcode.com/problems/redundant-connection/) | Medium | Union-Find | detect cycle in undirected graph | Should Do |
| [Accounts Merge](https://leetcode.com/problems/accounts-merge/) | Medium | Union-Find / graph traversal | merge connected identities | Should Do |
| [Word Ladder](https://leetcode.com/problems/word-ladder/) | Hard | BFS / implicit graph | fewest transformations | Advanced |
| [Critical Connections in a Network](https://leetcode.com/problems/critical-connections-in-a-network/) | Hard | Tarjan low-link DFS | bridges in undirected graph | Advanced |

## Backtracking & Recursion

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Subsets](https://leetcode.com/problems/subsets/) | Medium | Backtracking / bitmask | include-exclude choices | Must Do |
| [Permutations](https://leetcode.com/problems/permutations/) | Medium | Backtracking | order-sensitive arrangements | Must Do |
| [Combination Sum](https://leetcode.com/problems/combination-sum/) | Medium | Backtracking | reusable choices with pruning | Must Do |
| [Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) | Medium | Backtracking + invariant | never close more than opened | Should Do |
| [Word Search](https://leetcode.com/problems/word-search/) | Medium | Grid DFS + backtracking | mark and restore path | Must Do |
| [Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/) | Medium | Backtracking + palindrome check | enumerate valid cuts | Should Do |
| [N-Queens](https://leetcode.com/problems/n-queens/) | Hard | Backtracking + constraints | columns and diagonals | Advanced |

## Dynamic Programming

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) | Easy | 1D DP | overlapping recurrence | Must Do |
| [House Robber](https://leetcode.com/problems/house-robber/) | Medium | 1D DP / take-skip | adjacent choices conflict | Must Do |
| [Coin Change](https://leetcode.com/problems/coin-change/) | Medium | Unbounded knapsack DP | minimum number of coins | Must Do |
| [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) | Medium | 0/1 knapsack DP | subset sum to half | Must Do |
| [Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/) | Medium | DP / binary search | subsequence optimization | Must Do |
| [Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) | Medium | 2D DP | matching prefixes | Should Do |
| [Word Break](https://leetcode.com/problems/word-break/) | Medium | DP + dictionary | prefix reachability | Must Do |
| [Decode Ways](https://leetcode.com/problems/decode-ways/) | Medium | 1D DP | valid one/two digit transitions | Should Do |
| [Edit Distance](https://leetcode.com/problems/edit-distance/) | Medium | 2D DP | insert/delete/replace transitions | Should Do |
| [Burst Balloons](https://leetcode.com/problems/burst-balloons/) | Hard | Interval DP | choose last operation in interval | Advanced |
| [Regular Expression Matching](https://leetcode.com/problems/regular-expression-matching/) | Hard | 2D DP | state transitions for . and * | Advanced |
| [Best Time to Buy and Sell Stock with Cooldown](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/) | Medium | State-machine DP | hold/sell/cooldown states | Advanced |

## Greedy, Intervals & Sweep Line

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Merge Intervals](https://leetcode.com/problems/merge-intervals/) | Medium | Sort + interval merge | overlap after sorting starts | Must Do |
| [Insert Interval](https://leetcode.com/problems/insert-interval/) | Medium | Interval scan | merge one new range | Should Do |
| [Non-overlapping Intervals](https://leetcode.com/problems/non-overlapping-intervals/) | Medium | Greedy interval scheduling | earliest finish minimizes conflicts | Must Do |
| [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) | Medium | Sweep line / heap | maximum concurrent intervals | Should Do |
| [Jump Game](https://leetcode.com/problems/jump-game/) | Medium | Greedy reachability | maintain farthest reachable index | Must Do |
| [Jump Game II](https://leetcode.com/problems/jump-game-ii/) | Medium | Greedy BFS-layer view | minimum jumps in unweighted index graph | Should Do |
| [Gas Station](https://leetcode.com/problems/gas-station/) | Medium | Greedy invariant | global feasibility and reset point | Should Do |
| [Candy](https://leetcode.com/problems/candy/) | Hard | Greedy two passes | local neighbor constraints | Advanced |

## Tries & String Algorithms

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/) | Medium | Trie | shared prefixes | Should Do |
| [Word Search II](https://leetcode.com/problems/word-search-ii/) | Hard | Trie + DFS/backtracking | prune repeated prefix searches | Advanced |
| [Find the Index of the First Occurrence in a String](https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/) | Easy | KMP / string matching | linear pattern matching | Should Do |
| [Repeated Substring Pattern](https://leetcode.com/problems/repeated-substring-pattern/) | Easy | String periodicity | prefix function idea | Advanced |
| [Longest Happy Prefix](https://leetcode.com/problems/longest-happy-prefix/) | Hard | KMP prefix function | proper prefix also suffix | Advanced |
| [Distinct Echo Substrings](https://leetcode.com/problems/distinct-echo-substrings/) | Hard | Rolling hash / string matching | compare repeated substrings | Advanced |

## Bit Manipulation & Bitmasking

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Single Number](https://leetcode.com/problems/single-number/) | Easy | XOR | pairs cancel | Must Do |
| [Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/) | Easy | Bit counting | clear lowest set bit | Must Do |
| [Counting Bits](https://leetcode.com/problems/counting-bits/) | Easy | DP + bit manipulation | reuse smaller integer counts | Should Do |
| [Reverse Bits](https://leetcode.com/problems/reverse-bits/) | Easy | Bit manipulation | shift and accumulate | Should Do |
| [Missing Number](https://leetcode.com/problems/missing-number/) | Easy | XOR / arithmetic | recover omitted value | Must Do |
| [Subsets](https://leetcode.com/problems/subsets/) | Medium | Bitmask enumeration | one bit per inclusion decision | Should Do |
| [Maximum XOR of Two Numbers in an Array](https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/) | Medium | Trie + bits | greedy high-bit maximization | Advanced |

## Math & Number Theory

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Sqrt(x)](https://leetcode.com/problems/sqrt-x/) | Easy | Binary search / integer math | floor square root without floating error | Must Do |
| [Pow(x, n)](https://leetcode.com/problems/pow-x-n/) | Medium | Fast exponentiation | halve exponent | Must Do |
| [Count Primes](https://leetcode.com/problems/count-primes/) | Medium | Sieve of Eratosthenes | many primality checks below n | Should Do |
| [Greatest Common Divisor of Strings](https://leetcode.com/problems/greatest-common-divisor-of-strings/) | Easy | GCD / string structure | shared repeated unit | Advanced |
| [Ugly Number II](https://leetcode.com/problems/ugly-number-ii/) | Medium | Heap / DP / prime factors | generate ordered smooth numbers | Advanced |
| [Excel Sheet Column Number](https://leetcode.com/problems/excel-sheet-column-number/) | Easy | Base conversion | positional numeral system | Should Do |
| [Palindrome Number](https://leetcode.com/problems/palindrome-number/) | Easy | Digit manipulation | reverse digits safely | Should Do |

## Range Queries & Advanced

| Problem | Difficulty | Primary pattern | Identification clue / learning goal | Priority |
|---|---|---|---|---|
| [Range Sum Query - Mutable](https://leetcode.com/problems/range-sum-query-mutable/) | Medium | Fenwick / segment tree | point update + range sum | Advanced |
| [Count of Smaller Numbers After Self](https://leetcode.com/problems/count-of-smaller-numbers-after-self/) | Hard | Fenwick / merge sort | count prior smaller values | Advanced |
| [Reverse Pairs](https://leetcode.com/problems/reverse-pairs/) | Hard | Merge sort / range counting | count cross-half inequalities | Advanced |
| [My Calendar III](https://leetcode.com/problems/my-calendar-iii/) | Hard | Sweep line / ordered map | maximum overlap over updates | Advanced |
| [The Skyline Problem](https://leetcode.com/problems/the-skyline-problem/) | Hard | Sweep line + heap | active heights and event ordering | Advanced |

## Mathematics-linked practice

| Topic | Problem | What it reinforces |
|---|---|---|
| Base conversion | [Excel Sheet Column Number](https://leetcode.com/problems/excel-sheet-column-number/) | positional arithmetic |
| Integer square root | [Sqrt(x)](https://leetcode.com/problems/sqrtx/) | binary search and overflow-safe comparisons |
| Fast exponentiation | [Pow(x, n)](https://leetcode.com/problems/powx-n/) | logarithmic exponentiation |
| Prime sieve | [Count Primes](https://leetcode.com/problems/count-primes/) | sieve and complexity |
| GCD structure | [Greatest Common Divisor of Strings](https://leetcode.com/problems/greatest-common-divisor-of-strings/) | Euclidean algorithm intuition |
| XOR | [Single Number](https://leetcode.com/problems/single-number/) | cancellation invariant |
| Digit manipulation | [Palindrome Number](https://leetcode.com/problems/palindrome-number/) | arithmetic and overflow |

## Recommended solving order

1. Solve all Must Do Easy/Medium problems in Arrays, Two Pointers/Windows, Binary Search, Stacks, Trees, Graphs, and DP.
2. Re-solve missed problems without looking at notes after 1, 3, 7, and 14 days.
3. Mix patterns: choose 3 problems at random and do not reveal the topic before solving.
4. Attempt Advanced problems only after you can explain the baseline pattern and its limitations.
