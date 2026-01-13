Below is a **clean, interview-ready, notes-style explanation of Kruskal’s Algorithm**, exactly in the format you can revise before interviews or exams. I’ll go **step by step**, then give **pseudocode**, **Java code with Disjoint Set**, **comparison with Prim’s**, **time & space complexity**, and finally **important interview questions**.

---

## Kruskal’s Algorithm (Minimum Spanning Tree)

![Image](https://he-s3.s3.amazonaws.com/media/uploads/6322896.jpg)

![Image](https://miro.medium.com/1%2AF7nGVH-SVrfhlpPNJ5JpEg.jpeg)

![Image](https://algs4.cs.princeton.edu/43mst/images/kruskal.png)

### What is Kruskal’s Algorithm?

Kruskal’s Algorithm is a **greedy algorithm** used to find the **Minimum Spanning Tree (MST)** of a **connected, undirected, weighted graph**.

👉 **Key idea:**
Always pick the **smallest weight edge** that **does NOT form a cycle**.

---

## Why do we need Kruskal’s Algorithm?

* To connect all vertices with **minimum total edge weight**
* Used in:

    * Network design (LAN, fiber cables)
    * Road & railway planning
    * Cluster analysis
* Works very well when **edges are fewer** or graph is **sparse**

---

## Core Intuition (Very Important for Interviews)

1. Sort all edges by weight
2. Start picking edges from the smallest
3. Add an edge **only if it doesn’t form a cycle**
4. Stop when we have **V − 1 edges**

👉 **Cycle detection is done using Disjoint Set (Union-Find)**

---

## Algorithm Steps (As Notes)

### Step 1: Prepare Edge List

* Extract edges in the form:

```
(wt, u, v)
```

### Step 2: Sort Edges

* Sort edges by **increasing weight**

### Step 3: Initialize Disjoint Set

* Each node is its own parent initially

### Step 4: Process Edges

For each edge `(wt, u, v)`:

* If `find(u) != find(v)`

    * Add `wt` to MST
    * `union(u, v)`
* Else

    * Skip (it forms a cycle)

### Step 5: Result

* MST is formed when **V − 1 edges** are selected
* Sum of selected edge weights = MST weight

---

## Pseudocode (Interview Perfect)

```text
KRUSKAL(V, edges):
    sort edges by weight
    initialize DSU for V nodes
    mstWeight = 0
    edgesUsed = 0

    for each (wt, u, v) in edges:
        if find(u) != find(v):
            union(u, v)
            mstWeight += wt
            edgesUsed++

        if edgesUsed == V - 1:
            break

    return mstWeight
```

---

## Java Implementation (Using Disjoint Set)

### Disjoint Set (Union by Rank + Path Compression)

```java
class DisjointSet {
    int[] parent, rank;

    DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    int findUPar(int node) {
        if (node == parent[node])
            return node;
        return parent[node] = findUPar(parent[node]);
    }

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

### Kruskal’s Algorithm Code

```java
class Edge {
    int u, v, wt;
    Edge(int u, int v, int wt) {
        this.u = u;
        this.v = v;
        this.wt = wt;
    }
}

public int kruskalMST(int V, List<Edge> edges) {

    // 1. Sort edges by weight
    edges.sort((a, b) -> a.wt - b.wt);

    DisjointSet ds = new DisjointSet(V);
    int mstWt = 0;

    // 2. Process edges
    for (Edge e : edges) {
        int u = e.u;
        int v = e.v;
        int wt = e.wt;

        if (ds.findUPar(u) != ds.findUPar(v)) {
            mstWt += wt;
            ds.unionByRank(u, v);
        }
    }
    return mstWt;
}
```

---

## Why Disjoint Set is Mandatory?

Without DSU:

* Cycle detection → **O(V + E)** using DFS/BFS
  With DSU:
* Cycle detection → **Almost O(1)**

---

## Time & Space Complexity

### Time Complexity

| Step           | Complexity           |
| -------------- | -------------------- |
| Sorting edges  | **O(E log E)**       |
| DSU operations | **O(E α(V)) ≈ O(E)** |
| **Total**      | **O(E log E)**       |

> α(V) = Inverse Ackermann function ≈ constant

---

### Space Complexity

| Component            | Space        |
| -------------------- | ------------ |
| Edge list            | O(E)         |
| Parent & Rank arrays | O(V)         |
| **Total**            | **O(V + E)** |

---

## Kruskal vs Prim (Very Important)

| Feature            | Kruskal                                  | Prim                  |
| ------------------ | ---------------------------------------- | --------------------- |
| Approach           | Edge-based                               | Node-based            |
| Data Structure     | Disjoint Set                             | Priority Queue        |
| Sorting Needed     | Yes                                      | No                    |
| Best For           | Sparse graphs                            | Dense graphs          |
| Graph Connectivity | Can work on disconnected graphs (forest) | Needs connected graph |
| Time Complexity    | O(E log E)                               | O(E log V)            |

### When to Use Which?

* **Sparse graph** → Kruskal
* **Dense graph** → Prim
* **Edge list given** → Kruskal
* **Adjacency list given** → Prim

---

## Common Interview Questions

### 1. Why does Kruskal always produce MST?

Because it satisfies:

* **Greedy choice property**
* **Cut property**: The smallest edge crossing any cut is always part of MST

---

### 2. Can Kruskal handle disconnected graphs?

Yes.
It produces a **Minimum Spanning Forest**.

---

### 3. Why do we stop at V−1 edges?

Because:

* A tree with V vertices always has **V−1 edges**
* More edges ⇒ cycle

---

### 4. Why is Disjoint Set better than DFS for cycle detection?

| Method | Time     |
| ------ | -------- |
| DFS    | O(V + E) |
| DSU    | ~O(1)    |

---

### 5. Can Kruskal work with negative weights?

✅ **Yes**
Sorting still works correctly.

---

## Final One-Line Summary (For Interviews)

> **Kruskal’s Algorithm builds MST by greedily picking the smallest edge that does not form a cycle, using Disjoint Set for efficient cycle detection, with time complexity O(E log E).**

---
