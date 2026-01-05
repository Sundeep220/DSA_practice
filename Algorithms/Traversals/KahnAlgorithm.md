Below is a **clean, interview-ready documentation** for **Kahn’s Algorithm (Topological Sort using BFS)** that you can directly use in **notes, project docs, or explanations**.

---

# 📘 Kahn’s Algorithm (Topological Sort using BFS)

![Image](https://iq.opengenus.org/content/images/2020/03/topo1-1.png)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20251027161645789488/420046874.webp)

![Image](https://cdn-ilcidid.nitrocdn.com/UUGFLrvwXQisJMMAxvyGgbCYGRGywWEY/assets/images/optimized/rev-9840a99/cdn.prod.website-files.com/5d0dc87aac109e1ffdbe379c/60d346ceea3ab24618fa06ef_k7gwWr7LN-2_FoVGdvCSFwMblN3iCEpJhcYlRTxQDAP6bdDcfAcA4rIDjxFsLaKMM3on5ywI4h62-pMHllUvNfQLxkGFaCsePfYAeeJsGrdEbsa7Z_v-TlMH8ApkNvzYWfXLXL_h.png)

---

## 1. Overview

**Kahn’s Algorithm** is a **BFS-based algorithm** used to find a **topological ordering** of a **Directed Acyclic Graph (DAG)**.

A **topological order** is a linear ordering of vertices such that for every directed edge
`u → v`, vertex `u` comes **before** `v` in the ordering.

---

## 2. When to Use Kahn’s Algorithm

Use Kahn’s Algorithm when:

* The graph is **directed**
* You need a **topological sort**
* You want to **detect cycles** in a directed graph
* You prefer an **iterative (non-recursive)** approach

---

## 3. Core Idea

The algorithm works by repeatedly removing nodes that have **indegree = 0**.

> **Indegree** = number of incoming edges to a node

### Key Insight

* A node with indegree `0` has **no dependencies**
* Such nodes can safely appear next in the topological order

---

## 4. Algorithm Steps

### Step 1: Compute Indegree

* For every edge `u → v`, increment `indegree[v]`

---

### Step 2: Initialize Queue

* Push **all nodes with indegree = 0** into a queue

---

### Step 3: BFS Processing

While the queue is not empty:

1. Pop a node `u`
2. Add `u` to the topological order
3. For every neighbor `v` of `u`:

    * Decrease `indegree[v]`
    * If `indegree[v]` becomes `0`, push `v` into the queue

---

### Step 4: Cycle Detection

* If the number of processed nodes `< V`
* Then the graph contains a **cycle**

---

## 5. Pseudocode

```text
compute indegree[] for all vertices

queue = all vertices with indegree = 0
topo = empty list

while queue is not empty:
    u = queue.pop()
    topo.add(u)

    for each v in adj[u]:
        indegree[v]--
        if indegree[v] == 0:
            queue.push(v)

if topo.size() != V:
    graph has a cycle
else:
    topo is a valid topological order
```

---

## 6. Java Implementation

```java
public static List<Integer> kahnTopoSort(int V, List<List<Integer>> adj) {

    int[] indegree = new int[V];

    // Step 1: Compute indegree
    for (int u = 0; u < V; u++) {
        for (int v : adj.get(u)) {
            indegree[v]++;
        }
    }

    // Step 2: Push indegree-0 nodes
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < V; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);
        }
    }

    // Step 3: BFS
    List<Integer> topo = new ArrayList<>();

    while (!queue.isEmpty()) {
        int u = queue.poll();
        topo.add(u);

        for (int v : adj.get(u)) {
            indegree[v]--;
            if (indegree[v] == 0) {
                queue.offer(v);
            }
        }
    }

    // Step 4: Cycle check
    if (topo.size() != V) {
        return new ArrayList<>(); // cycle exists
    }

    return topo;
}
```

---

## 7. Time and Space Complexity

### Time Complexity

```
O(V + E)
```

* Each vertex is processed once
* Each edge is relaxed once

### Space Complexity

```
O(V)
```

* Indegree array
* Queue
* Result list

---

## 8. Advantages

✔ Simple and intuitive
✔ Iterative (no recursion stack)
✔ Detects cycles naturally
✔ Works well for large graphs

---

## 9. Limitations

❌ Works **only for DAGs**
❌ Does **not** give all topological orders
❌ Order depends on queue insertion order

---

## 10. Kahn vs DFS Topological Sort

| Feature         | Kahn’s Algorithm | DFS Topo Sort     |
| --------------- | ---------------- | ----------------- |
| Approach        | BFS              | DFS               |
| Uses Indegree   | ✅ Yes            | ❌ No              |
| Cycle Detection | Easy             | Needs extra logic |
| Recursive       | ❌ No             | ✅ Yes             |

---

## 11. Common Use Cases

* Task scheduling
* Course prerequisite problems
* Build systems / dependency resolution
* Job pipelines
* Deadlock detection

---

## 12. Interview One-Liner 🏆

> “Kahn’s Algorithm uses BFS and indegree counting to produce a topological order of a DAG in O(V + E) time, and it can also detect cycles.”

