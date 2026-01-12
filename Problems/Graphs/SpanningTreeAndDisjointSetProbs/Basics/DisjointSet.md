Below is a **clean, interview-ready + notes-style deep dive** into **Disjoint Set (DSU / Union–Find)** with **Union by Rank**, **Union by Size**, and **Path Compression**, written so you can **directly add this to your DSA notes**.

---

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AecQEL6cJA6I-RflbxbociA.png)

![Image](https://cp-algorithms.com/data_structures/DSU_path_compression.png)

![Image](https://static.takeuforward.org/wp/uploads/2022/12/Screenshot-2022-12-14-184818.png)

![Image](https://tutorialhorizon.com/static/media/algorithms/2018/04/Union-by-Rank.png)

# Disjoint Set (Union–Find) – Complete Notes (G-46)

## 1. Why do we need Disjoint Set?

### Problem

Given an **undirected graph**, answer queries like:

> Are node **u** and node **v** in the **same connected component**?

### Brute Force

* Use **DFS / BFS**
* Time per query: **O(N + E)**
* ❌ Too slow if graph keeps changing

### Disjoint Set (DSU)

* Handles **dynamic graphs** (edges added over time)
* Answers:

    * `find(u)` → which component u belongs to
    * `union(u, v)` → connect two components
* Time per operation: **Almost O(1)** ✅

---

## 2. Dynamic Graph Intuition

Edges added one by one:

```
{1–2}, {2–3}, {4–5}, {6–7}, {5–6}, {3–7}
```

* After 4 edges → `{1,2,3}` and `{4,5}` and `{6,7}`
* After all edges → **all nodes connected**

DSU can answer connectivity **after every edge addition** efficiently.

---

## 3. Core Concepts

### Parent

* `parent[x]` → immediate parent of node `x`

### Ultimate Parent (Root)

* The **topmost parent** of a node
* Defines the component

### Rank

* Approximate **height of the tree**
* Used only for **balancing**

### Size

* Number of nodes in a component
* Used as an alternative to rank

---

## 4. Why Ultimate Parent Matters?

Two nodes are in the same component **iff**:

```
find(u) == find(v)
```

Immediate parents may differ,
but **ultimate parents decide connectivity**.

---

## 5. findPar() + Path Compression

### Basic find (slow)

```
u → parent → parent → parent → root
```

### Path Compression (fast)

* While finding root, directly attach nodes to root
* Flattens the tree

### Algorithm

1. If `node == parent[node]`, return node
2. Else:

    * `parent[node] = find(parent[node])`
    * Return parent[node]

### Effect

* Tree height becomes very small
* Future finds become **O(1)**

---

## 6. Union by Rank

### Idea

Always attach **smaller height tree** under **larger height tree**

### Why?

* Keeps tree **shallow**
* Faster `find()` operations

### Rules

1. Find roots `pu`, `pv`
2. If ranks differ → attach smaller rank under larger rank
3. If equal → attach any one & **increase rank by 1**

---

## 7. Java Code – Union by Rank + Path Compression

```java
class DisjointSet {
    int[] parent;
    int[] rank;

    DisjointSet(int n) {
        parent = new int[n + 1];
        rank = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Path Compression
    int findUPar(int node) {
        if (node == parent[node])
            return node;

        parent[node] = findUPar(parent[node]);
        return parent[node];
    }

    // Union by Rank
    void unionByRank(int u, int v) {
        int pu = findUPar(u);
        int pv = findUPar(v);

        if (pu == pv) return;

        if (rank[pu] < rank[pv]) {
            parent[pu] = pv;
        } else if (rank[pv] < rank[pu]) {
            parent[pv] = pu;
        } else {
            parent[pv] = pu;
            rank[pu]++;
        }
    }
}
```

---

## 8. Why Smaller Rank → Larger Rank?

If we do the opposite:

* Tree height increases
* More traversal during `find`
* Path compression cost increases

Correct approach:

* Attach **shorter tree under taller**
* Keeps depth minimal

---

## 9. Rank Gets Distorted ❗

After path compression:

* Actual height changes
* `rank[]` no longer reflects real depth

➡️ Hence **Union by Size** is often preferred.

---

## 10. Union by Size

### Idea

Attach **smaller component** under **larger component**

### Advantages

* More intuitive
* No distortion problem
* Works perfectly with path compression

---

## 11. Union by Size Algorithm

### Initialization

```text
parent[i] = i
size[i] = 1
```

### Steps

1. Find roots `pu`, `pv`
2. Attach smaller size component under larger
3. Update size

---

## 12. Java Code – Union by Size (Recommended)

```java
class DisjointSet {
    int[] parent;
    int[] size;

    DisjointSet(int n) {
        parent = new int[n + 1];
        size = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    int findUPar(int node) {
        if (node == parent[node])
            return node;

        parent[node] = findUPar(parent[node]);
        return parent[node];
    }

    void unionBySize(int u, int v) {
        int pu = findUPar(u);
        int pv = findUPar(v);

        if (pu == pv) return;

        if (size[pu] < size[pv]) {
            parent[pu] = pv;
            size[pv] += size[pu];
        } else {
            parent[pv] = pu;
            size[pu] += size[pv];
        }
    }
}
```

---

## 13. Time Complexity (Interview Gold ⭐)

* `find()` with path compression: **O(α(N))**
* `union()`: **O(α(N))**
* `α(N)` = Inverse Ackermann (≤ 4 for all practical N)

➡️ **Considered constant time**

---

## 14. When to Use Disjoint Set?

* Connected Components
* Cycle Detection (Undirected Graph)
* Kruskal’s MST
* Dynamic connectivity queries
* Grid problems (islands, regions)
* Network connectivity

---

## 15. Rank vs Size – Which is Better?

| Feature                      | Union by Rank | Union by Size |
| ---------------------------- | ------------- | ------------- |
| Intuition                    | Medium        | Very High     |
| Affected by path compression | Yes           | No            |
| Preferred in practice        | ❌             | ✅             |
| Interview safe               | ✅             | ✅             |

---

### 📌 Final Recommendation

> **Use Union by Size + Path Compression** in production & interviews.

---

# Why Disjoint Set Runs in ~O(1)

## (Derivation of `O(α(N))` Time Complexity)

---

## 1. What are we analyzing exactly?

For **Disjoint Set (DSU)** with:

* **Union by Rank / Size**
* **Path Compression**

We want to understand the time complexity of:

* `find()`
* `union()`

👉 Claim:

```
Time per operation = O(α(N))
```

Where **α(N)** is the **Inverse Ackermann function**
(≤ 4 for any real-world N)

---

## 2. First, analyze WITHOUT optimizations

### Case 1: No rank, no compression

Worst case:

```
1 → 2 → 3 → 4 → ... → N
```

* Height = N
* `find()` = O(N)
* `union()` = O(N)

❌ Too slow

---

## 3. Add Union by Rank ONLY

### What union by rank guarantees

* Tree height is **logarithmic**
* Because smaller trees attach under bigger trees

### Why height ≤ log N?

Each time rank increases:

* Tree size at least **doubles**

Example:

```
rank 0 → size ≥ 1
rank 1 → size ≥ 2
rank 2 → size ≥ 4
rank 3 → size ≥ 8
...
```

So:

```
max rank ≤ log₂N
```

### Complexity now

* `find()` = O(log N)
* `union()` = O(log N)

✅ Better, but still not constant

---

## 4. Add Path Compression ONLY

### What path compression does

Before:

```
7 → 6 → 5 → 4 → 3 → 2 → 1
```

After one `find(7)`:

```
7
│
├── 6
├── 5
├── 4
├── 3
├── 2
└── 1
```

All nodes point **directly to root**

### Effect

* First `find()` may be expensive
* Subsequent `find()` calls are **O(1)**

But…
❌ No strict upper bound alone

---

## 5. Combine BOTH (Key Insight)

When we use:

* **Union by Rank** → keeps trees shallow
* **Path Compression** → flattens trees aggressively

Something magical happens:

> The tree becomes *almost flat*, permanently.

This combination is what leads to **α(N)**.

---

## 6. Where does α(N) come from?

### Step 1: Ackermann Function (Grows Insanely Fast)

You do **NOT** need the full definition in interviews, but intuition matters.

Ackermann grows faster than:

* exponential
* power towers
* factorials

Even small inputs give huge values.

Example intuition:

```
A(4, 4) > number of atoms in the universe
```

---

### Step 2: Inverse Ackermann α(N)

α(N) answers:

> “How many times do I need to apply Ackermann to reach N?”

Since Ackermann grows insanely fast,
its inverse grows **insanely slowly**.

---

## 7. Practical Values of α(N)

This is the **most important interview takeaway**:

| N    | α(N) |
| ---- | ---- |
| 10   | 3    |
| 10⁶  | 4    |
| 10¹⁸ | 4    |
| 10⁸⁰ | 5    |

👉 For **any conceivable input size**,
**α(N) ≤ 4**

That’s why we treat it as **constant time**.

---

## 8. Intuition Behind the Derivation (Interview-Friendly)

Instead of math symbols, say this:

> Each node’s parent pointer only moves **upwards**,
> and after path compression, it jumps **very close to the root**.
>
> A node can only move “up” a very limited number of times
> before it becomes a direct child of the root forever.

So:

* Total pointer changes across **all operations** is very small
* Amortized cost per operation becomes **almost constant**

---

## 9. Amortized Analysis (Important Word)

Key phrase to use:

> **Amortized time complexity**

Meaning:

* Some operations are expensive
* But averaged over all operations → constant

DSU analysis is **amortized**, not per single operation.

---

## 10. Final Formal Result

For **N elements** and **M operations**:

```
Total Time = O(M · α(N))
```

Since:

```
α(N) ≤ 4
```

We say:

```
≈ O(M)
≈ O(1) per operation
```

---

## 11. Why Interviewers Don’t Expect Full Proof

* Full proof involves **Tarjan’s analysis**
* Heavy recursive mathematics
* Not expected unless PhD-level algorithms

What **is expected**:

* Log N height due to rank
* Tree flattening due to path compression
* α(N) grows extremely slowly

---

## 12. One-Line Interview Answer ⭐

> *“Using union by rank ensures logarithmic height, and path compression flattens the tree aggressively. Together, the amortized complexity becomes O(α(N)), where α(N) is the inverse Ackermann function, which is ≤ 4 for all practical values of N, so operations are effectively constant time.”*

---
