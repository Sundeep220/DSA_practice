Below are **clean, exam-ready + interview-ready notes** on **Prim’s Algorithm**, written so you can **directly paste into your notebook**.
I’ve structured it as **Concept → Intuition → Steps → Pseudocode → Java Code → Complexity → Common Mistakes**.

---

## 🌳 Prim’s Algorithm (Minimum Spanning Tree)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20250225154939293361/Prims-Algorithm-1.webp)

![Image](https://images.tpointtech.com/core/images/prims-algorithm-java.png)

![Image](https://tutorialhorizon.com/static/media/algorithms/2015/06/PQ-9-15.png)

---

## 1️⃣ What is Prim’s Algorithm?

**Prim’s Algorithm** is a **greedy algorithm** used to find the **Minimum Spanning Tree (MST)** of a **connected, weighted, undirected graph**.

> It grows the MST **one vertex at a time**, always choosing the **minimum weight edge** that connects a vertex **inside the MST** to a vertex **outside the MST**.

---

## 2️⃣ Key Idea (Intuition)

Think of Prim’s as:

> “Start from any node, and **keep expanding the tree** by adding the **cheapest edge available** that doesn’t form a cycle.”

### Important Insight

* At every step, **the MST remains connected**
* We **never connect two already-visited nodes**
* Greedy choice → locally minimum edge → globally optimal MST

---

## 3️⃣ When Can Prim’s Be Used?

✅ Graph must be:

* **Connected**
* **Undirected**
* **Weighted**

❌ Not suitable for:

* Directed graphs
* Disconnected graphs (unless run per component)

---

## 4️⃣ Algorithm Steps (High-Level)

1. Pick **any node** as starting point
2. Maintain:

    * `visited[]` → nodes already in MST
    * `minHeap` → edges ordered by weight
3. Push `(weight, node)` into heap
4. While heap is not empty:

    * Extract edge with **minimum weight**
    * If node is already visited → skip
    * Otherwise:

        * Mark node as visited
        * Add edge weight to MST sum
        * Push all adjacent edges into heap
5. When all nodes are visited → MST complete

---

## 5️⃣ Pseudocode (Clean & Exam Friendly)

```text
PrimMST(graph):
    visited[] = false for all vertices
    minHeap = empty priority queue (weight, node)
    
    push (0, startNode) into minHeap
    mstWeight = 0

    while minHeap is not empty:
        (wt, u) = extractMin(minHeap)

        if visited[u]:
            continue

        visited[u] = true
        mstWeight += wt

        for each (v, edgeWeight) adjacent to u:
            if not visited[v]:
                push (edgeWeight, v) into minHeap

    return mstWeight
```

---

## 6️⃣ Java Implementation (Priority Queue – Optimal)

### Adjacency List Representation

```java
class Pair {
    int node;
    int weight;

    Pair(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}
```

### Prim’s Algorithm Code

```java
public static int primMST(int V, List<List<Pair>> adj) {

    boolean[] visited = new boolean[V];

    PriorityQueue<Pair> pq =
            new PriorityQueue<>((a, b) -> a.weight - b.weight);

    // Start from node 0 (can start from any node)
    pq.offer(new Pair(0, 0));

    int mstSum = 0;

    while (!pq.isEmpty()) {

        Pair curr = pq.poll();
        int node = curr.node;
        int wt = curr.weight;

        // If already part of MST, skip
        if (visited[node]) continue;

        // Include node in MST
        visited[node] = true;
        mstSum += wt;

        // Explore neighbors
        for (Pair nbr : adj.get(node)) {
            if (!visited[nbr.node]) {
                pq.offer(new Pair(nbr.node, nbr.weight));
            }
        }
    }

    return mstSum;
}
```

---

## 7️⃣ Why This Works (Greedy Proof – Short)

* At each step, we pick the **minimum weight edge crossing the cut**
* This follows the **Cut Property**:

  > The lightest edge crossing any cut is always part of the MST
* Hence, Prim’s always produces an **optimal MST**

---

## 8️⃣ Time & Space Complexity

| Implementation                  | Time Complexity |
| ------------------------------- | --------------- |
| Priority Queue + Adjacency List | **O(E log V)**  |
| Adjacency Matrix (Brute)        | **O(V²)**       |

**Space Complexity**:
`O(V + E)` (graph + heap + visited array)

---

## 9️⃣ Prim vs Kruskal (Quick Compare)

| Prim                  | Kruskal                |
| --------------------- | ---------------------- |
| Vertex-based          | Edge-based             |
| Best for dense graphs | Best for sparse graphs |
| Uses PQ               | Uses DSU               |
| Starts from one node  | Sorts all edges        |

---

## 🔴 Common Mistakes (Very Important)

❌ Applying Prim on **directed graph**
❌ Forgetting to check `visited[]` → cycles
❌ Assuming starting node affects MST cost (it doesn’t)
❌ Using Prim for **shortest path** (it is NOT Dijkstra)

---

## 🧠 One-Line Summary (Interview Gold)

> **Prim’s Algorithm builds the MST by repeatedly choosing the minimum weight edge that expands the current tree without forming a cycle.**

---