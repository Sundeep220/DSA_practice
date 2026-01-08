-Perfect — **Dijkstra’s Algorithm** is the next natural step after BFS (unit weight) and DAG shortest path 👍
Let’s document it **cleanly, systematically, and interview-ready**.

---

# 📘 Dijkstra’s Algorithm (Shortest Path in Weighted Graph)

![Image](https://graphicmaths.com/img/computer-science/graph-theory/dijkstras-algorithm/graph.png)

![Image](https://www.researchgate.net/profile/Mohammed-Al-Ibadi/publication/271518595/figure/fig1/AS%3A360670886416384%401463002048984/a-Network-topology-b-Steps-of-Dijkstra-algorithm.png)

![Image](https://www.researchgate.net/publication/382193848/figure/fig4/AS%3A11431281260077625%401720804178334/Dijkstra-algorithm-visualization-Weights-of-edges-are-written-on-them-Numbers-on.jpg)

---

## 1. Overview

**Dijkstra’s Algorithm** is used to find the **shortest distance from a source node to all other nodes** in a **weighted graph** where:

> ⚠️ **All edge weights are NON-negative**

It works for:

* Directed graphs
* Undirected graphs

---

## 2. When to Use Dijkstra

Use Dijkstra when:

| Graph Type               | Edge Weights | Algorithm    |
| ------------------------ | ------------ | ------------ |
| Unweighted / Unit weight | 1            | BFS          |
| DAG (any weight)         | Any          | Topo + Relax |
| General graph            | Non-negative | **Dijkstra** |
| Negative weights         | Allowed      | Bellman-Ford |

---

## 3. Core Idea (Intuition)

> Always expand the **closest unprocessed node** first.

Once the shortest distance to a node is finalized:

* It **never changes again**

This greedy choice is valid **only because weights are non-negative**.

---

## 4. Algorithm Steps

### Step 1: Initialization

* `dist[]` = ∞ for all nodes
* `dist[src] = 0`
* Min-Heap / Priority Queue → `(distance, node)`

---

### Step 2: Greedy Selection

While PQ is not empty:

1. Extract node `u` with **minimum distance**
2. For every edge `u → v (weight w)`:

    * If `dist[v] > dist[u] + w`

        * Update distance
        * Push `(dist[v], v)` into PQ

---

### Step 3: Completion

* When PQ is empty → shortest distances are finalized

---

## 5. Why Priority Queue?

Without PQ:

* Finding minimum takes `O(V)`
* Total complexity becomes `O(V²)`

With PQ:

* Extract-min: `O(log V)`
* Insert: `O(log V)`
* Total: `O((V + E) log V)`

---

## 6. Pseudocode

```text
dist[src] = 0
priorityQueue = (0, src)

while priorityQueue not empty:
    (d, u) = extractMin()

    if d > dist[u]:
        continue

    for each (u → v, weight w):
        if dist[v] > dist[u] + w:
            dist[v] = dist[u] + w
            push (dist[v], v)
```

---

## 7. Java Implementation (Standard & Safe)

```java
class Solution {

    static class Pair {
        int node, dist;
        Pair(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    public static int[] dijkstra(
            int V,
            List<List<Pair>> adj,
            int src
    ) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<Pair> pq =
                new PriorityQueue<>((a, b) -> a.dist - b.dist);

        pq.offer(new Pair(src, 0));

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int u = curr.node;
            int d = curr.dist;

            // Skip outdated entries
            if (d > dist[u]) continue;

            for (Pair edge : adj.get(u)) {
                int v = edge.node;
                int wt = edge.dist;

                if (dist[v] > dist[u] + wt) {
                    dist[v] = dist[u] + wt;
                    pq.offer(new Pair(v, dist[v]));
                }
            }
        }

        return dist;
    }
}
```

---

## 8. Example

Graph:

```
0 --1--> 1
0 --4--> 2
1 --2--> 2
1 --5--> 3
2 --1--> 3
```

Source = `0`

Output:

```
dist = [0, 1, 3, 4]
```

---

## 9. Time & Space Complexity

### ⏱ Time

```
O((V + E) log V)
```

### 🧠 Space

```
O(V + E)
```

---

## 10. Important Constraints ⚠️

❌ Does NOT work with **negative weights**

Why?

* Greedy choice breaks
* A shorter path may appear later

---

## 11. Common Mistakes (Interview Traps)

❌ Forgetting `if (d > dist[u]) continue`
❌ Using Dijkstra for unit-weight graph
❌ Using it for negative edges
❌ Using adjacency matrix unnecessarily

---

## 12. Interview One-Liners 🏆

* “Dijkstra is a greedy algorithm that always expands the closest unvisited node.”
* “It works only with non-negative weights.”
* “Using a priority queue reduces complexity to O((V + E) log V).”

---

## 13. Comparison Summary

| Scenario                | Best Algorithm |
| ----------------------- | -------------- |
| Unit weights            | BFS            |
| DAG                     | Topological DP |
| Weighted (non-negative) | **Dijkstra**   |
| Negative weights        | Bellman-Ford   |

---
