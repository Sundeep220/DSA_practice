# 🧠 Tarjan’s Algorithm (DFS + Low-Link Values)

---

## 1️⃣ What is Tarjan’s Algorithm?

**Tarjan’s Algorithm** is a DFS-based graph algorithm used to find **critical structures** in a graph such as:

* 🔗 **Bridges** (critical connections)
* 🚦 **Articulation Points** (cut vertices)
* 🔁 **Strongly Connected Components (SCCs)** *(directed graphs)*

> In undirected graphs, Tarjan’s algorithm helps identify **points or edges whose removal breaks connectivity**.

---

## 2️⃣ Why Do We Need It?

Many graph problems ask:

* Which edge failure disconnects the network?
* Which node is a single point of failure?
* How resilient is a network?

Naive approach:

* Remove each node/edge → run DFS/BFS again
  ❌ **O(N × (N + E))** → too slow

Tarjan solves it in:

> ✅ **O(N + E)** using **one DFS traversal**

---

## 3️⃣ Core DFS Concepts Used

Tarjan is built on **DFS tree properties**.

### DFS Tree

* DFS forms a **tree structure**
* Edges are classified as:

    * **Tree edges**
    * **Back edges** (connect to ancestor)

---

## 4️⃣ Two Key Arrays (MOST IMPORTANT)

### 🔹 1. Discovery Time (`tin[]`)

```
tin[u] = time when node u is first visited in DFS
```

* Assigned incrementally
* Represents DFS order

Example:

```
DFS order: 0 → 1 → 2 → 3
tin[] = [0, 1, 2, 3]
```

---

### 🔹 2. Low-Link Value (`low[]`)

```
low[u] = minimum discovery time reachable from u
         (including back edges and subtree edges)
```

In other words:

> How far **up the DFS tree** can we reach from `u`?

---

## 5️⃣ How is `low[]` Calculated?

During DFS at node `u`:

### Case 1: Tree Edge (`u → v`)

```
DFS(v)
low[u] = min(low[u], low[v])
```

### Case 2: Back Edge (`u → v`, v already visited & not parent)

```
low[u] = min(low[u], tin[v])
```

---

## 6️⃣ Why Low-Link Values Matter

Low values tell us:

* Whether a subtree has **alternate paths**
* Whether removing a node/edge **disconnects the graph**

This single insight powers:

* Bridges
* Articulation points
* SCC detection

---

## 7️⃣ Bridge Detection (Undirected Graph)

### 🔹 Definition

An edge is a **bridge** if removing it increases connected components.

### 🔹 Condition

For edge `(u, v)` where `v` is DFS child of `u`:

```
if low[v] > tin[u] → (u, v) is a BRIDGE
```

### 🔹 Intuition

* `v`’s subtree **cannot reach u or any ancestor**
* Edge `(u, v)` is the **only connection**

---

## 8️⃣ Articulation Point Detection

### 🔹 Definition

A node is an articulation point if removing it increases connected components.

---

### Case 1: Root Node

```
If root has more than one DFS child → articulation point
```

Why?

* Each child subtree is independent

---

### Case 2: Non-root Node

For node `u` and child `v`:

```
if low[v] >= tin[u] → u is an articulation point
```

Why?

* `v`’s subtree cannot bypass `u`
* Removing `u` disconnects `v`’s subtree

---

## 9️⃣ Bridge vs Articulation (CRUCIAL COMPARISON)

| Feature           | Bridge            | Articulation Point |
| ----------------- | ----------------- | ------------------ |
| Works on          | Edge              | Vertex             |
| Condition         | `low[v] > tin[u]` | `low[v] >= tin[u]` |
| Root special case | ❌ No              | ✅ Yes              |
| Disconnects       | Edge removal      | Node removal       |

---

## 🔟 Example to Visualize

```
    0
   / \
  1---2
  |
  3
```

* `(1,3)` → Bridge
* `1` → Articulation point
* `0` → Not articulation (root with 1 child)

---

## 1️⃣1️⃣ Why One DFS is Enough?

Because:

* DFS already explores **all paths**
* `low[]` compresses all alternate paths info
* Backtracking propagates connectivity info upward

---

## 1️⃣2️⃣ Time & Space Complexity

| Metric | Value                         |
| ------ | ----------------------------- |
| Time   | **O(N + E)**                  |
| Space  | **O(N + E)** (stack + arrays) |

---

## 1️⃣3️⃣ Common Mistakes ❌

1. Using `>` instead of `>=` for articulation points
2. Forgetting root special case
3. Not skipping parent edge
4. Running DFS from only node `0` (graph may be disconnected)
5. Mixing directed & undirected logic

---

## 1️⃣4️⃣ Where Tarjan is Used in Real Systems

* Network fault tolerance
* Microservice dependency graphs
* Infrastructure single-point-of-failure detection


## 1️⃣ What is Tarjan’s Algorithm?

**Tarjan’s Algorithm** is a **DFS-based graph algorithm** used to find **critical structural elements** in a graph in **linear time**.

Depending on the condition used, it helps identify:

| Problem               | What it Finds                 |
| --------------------- | ----------------------------- |
| Bridges               | Critical edges                |
| Articulation Points   | Critical vertices             |
| SCCs (directed graph) | Strongly connected components |

In **undirected graphs**, we mainly use it for:

* **Bridges**
* **Articulation Points**

---

## 2️⃣ Core Idea (Single Unified Intuition)

> While doing DFS, for every node we try to answer:
> **“Can this node or its subtree reach an ancestor using a back edge?”**

To answer this efficiently, we track **two timestamps**.

---

## 3️⃣ Key Concepts (VERY IMPORTANT)

### 🔹 1. Discovery Time (`tin[u]`)

* Time when node `u` is first visited in DFS
* Increases monotonically

```
tin[u] = time when DFS enters u
```

---

### 🔹 2. Low Value (`low[u]`)

* Earliest (minimum) discovery time reachable from `u`
* Includes:

    * Itself
    * DFS children
    * Back edges

```
low[u] = min(
    tin[u],
    tin[x] for any back-edge u → x,
    low[v] for any DFS child v
)
```

---

### 🔹 3. DFS Tree vs Back Edge

| Edge Type | Meaning                   |
| --------- | ------------------------- |
| Tree Edge | Part of DFS recursion     |
| Back Edge | Connects node to ancestor |

Back edges are what **prevent disconnection**.

---

## 4️⃣ Why Tarjan Works

If a subtree **cannot reach any ancestor**, then:

* An **edge** becomes critical → Bridge
* A **vertex** becomes critical → Articulation Point

This is detected using comparisons between `low[]` and `tin[]`.

---

## 5️⃣ Tarjan Conditions Summary

### 🔹 A. Bridge (Edge)

For DFS edge `(u → v)`:

```
if low[v] > tin[u] → (u, v) is a bridge
```

**Meaning:**
`v`’s subtree cannot reach `u` or above.

---

### 🔹 B. Articulation Point (Vertex)

#### Case 1: Root Node

```
If root has more than 1 DFS child → articulation point
```

#### Case 2: Non-Root Node

```
If low[v] >= tin[u] → u is articulation point
```

---

## 6️⃣ Pseudocode (Generic Tarjan Template)

```text
timer = 0

DFS(u, parent):
    visited[u] = true
    tin[u] = low[u] = timer++
    children = 0

    for each v in adj[u]:
        if v == parent:
            continue

        if not visited[v]:
            DFS(v, u)
            low[u] = min(low[u], low[v])
            children++

            // Bridge condition
            if low[v] > tin[u]:
                (u, v) is a bridge

            // Articulation condition (non-root)
            if parent != -1 AND low[v] >= tin[u]:
                u is articulation point
        else:
            // Back edge
            low[u] = min(low[u], tin[v])

    // Articulation condition (root)
    if parent == -1 AND children > 1:
        u is articulation point
```

---

## 7️⃣ Time & Space Complexity

| Metric    | Value        |
| --------- | ------------ |
| Time      | **O(V + E)** |
| Space     | **O(V + E)** |
| DFS Stack | O(V)         |

---

## 8️⃣ Java Code — Bridges (Tarjan, No Globals)

```java
class Solution {

    static class State {
        int timer = 0;
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (List<Integer> e : connections) {
            adj.get(e.get(0)).add(e.get(1));
            adj.get(e.get(1)).add(e.get(0));
        }

        int[] tin = new int[n];
        int[] low = new int[n];
        boolean[] visited = new boolean[n];

        List<List<Integer>> bridges = new ArrayList<>();
        State state = new State();

        dfs(0, -1, adj, tin, low, visited, bridges, state);
        return bridges;
    }

    private void dfs(
            int u,
            int parent,
            List<List<Integer>> adj,
            int[] tin,
            int[] low,
            boolean[] visited,
            List<List<Integer>> bridges,
            State state
    ) {
        visited[u] = true;
        tin[u] = low[u] = state.timer++;

        for (int v : adj.get(u)) {
            if (v == parent) continue;

            if (!visited[v]) {
                dfs(v, u, adj, tin, low, visited, bridges, state);
                low[u] = Math.min(low[u], low[v]);

                if (low[v] > tin[u]) {
                    bridges.add(List.of(u, v));
                }
            } else {
                low[u] = Math.min(low[u], tin[v]);
            }
        }
    }
}
```

---

## 9️⃣ Java Code — Articulation Points

```java
class Solution {

    static class State {
        int timer = 0;
    }

    public List<Integer> articulationPoints(int V, List<List<Integer>> adj) {

        int[] tin = new int[V];
        int[] low = new int[V];
        boolean[] visited = new boolean[V];
        boolean[] isAP = new boolean[V];

        State state = new State();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, -1, adj, tin, low, visited, isAP, state);
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (isAP[i]) res.add(i);
        }

        return res.isEmpty() ? List.of(-1) : res;
    }

    private void dfs(
            int u,
            int parent,
            List<List<Integer>> adj,
            int[] tin,
            int[] low,
            boolean[] visited,
            boolean[] isAP,
            State state
    ) {
        visited[u] = true;
        tin[u] = low[u] = state.timer++;
        int children = 0;

        for (int v : adj.get(u)) {
            if (v == parent) continue;

            if (!visited[v]) {
                children++;
                dfs(v, u, adj, tin, low, visited, isAP, state);
                low[u] = Math.min(low[u], low[v]);

                if (parent != -1 && low[v] >= tin[u]) {
                    isAP[u] = true;
                }
            } else {
                low[u] = Math.min(low[u], tin[v]);
            }
        }

        if (parent == -1 && children > 1) {
            isAP[u] = true;
        }
    }
}
```

---

## 🔟 Bridge vs Articulation (Quick Revision Table)

| Feature   | Bridge            | Articulation Point |
| --------- | ----------------- | ------------------ |
| Removes   | Edge              | Vertex             |
| Condition | `low[v] > tin[u]` | `low[v] >= tin[u]` |
| Root rule | ❌                 | children > 1       |
| Graph     | Undirected        | Undirected         |

---

## 1️⃣1️⃣ Interview One-Liners (Must Remember)

* **Tarjan’s algorithm uses DFS timestamps to detect graph separation points**
* **low[] tells how far a subtree can reach upward**
* **Bridges use strict `>`; articulation points use `>=`**
* **Root articulation is a special case**

---

## 1️⃣2️⃣ Where Tarjan is Used in Real Systems

* Network failure detection
* Distributed systems topology
* Circuit design
* Social network analysis
* Infrastructure dependency graphs

---
