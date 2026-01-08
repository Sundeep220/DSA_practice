Below are **clean, structured notes** on the **Bellman–Ford Algorithm**, written exactly in a **study + interview-ready format**. You can directly revise this before exams, interviews, or coding rounds.

---

## 📌 Bellman–Ford Algorithm (G-41)

![Image](https://www.simplilearn.com/ice9/free_resources_article_thumb/Bellman%20Ford%20Algorithm/Example-of-bellman-ford-algorithm1.png)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/bellmanford3.png)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20230904155614/Example2.png)

![Image](https://media.geeksforgeeks.org/wp-content/cdn-uploads/20200709132634/Example11.png)

---

## 1️⃣ What is Bellman–Ford?

**Bellman–Ford Algorithm** is a **single-source shortest path algorithm** that finds the shortest distance from a source vertex **S** to all other vertices in a **weighted, directed graph**.

### 🔹 Special Features

* Works with **negative edge weights**
* Can **detect negative weight cycles**
* Safer than Dijkstra when negative edges exist

---

## 2️⃣ Where Dijkstra Fails (Why Bellman–Ford?)

| Algorithm    | Negative Edges | Negative Cycle Detection |
| ------------ | -------------- | ------------------------ |
| Dijkstra     | ❌ No           | ❌ No                     |
| Bellman–Ford | ✅ Yes          | ✅ Yes                    |

🔴 **Dijkstra fails** because it assumes once a node is finalized, its shortest distance won’t change — which is **false with negative edges**.

---

## 3️⃣ Key Concepts

### 🔹 Relaxation

Relaxing an edge `(u → v, wt)` means:

```
If dist[u] + wt < dist[v]
→ update dist[v]
```

We keep improving distances until no better path exists.

---

### 🔹 Why Repeat Relaxation **V−1 Times**?

* A shortest path in a graph can have **at most V−1 edges**
* After V−1 iterations, all shortest paths must be found
* Any further improvement means a **negative cycle**

---

## 4️⃣ Negative Weight Cycle

### 🔴 What is it?

A **cycle whose total sum of weights is negative**.

### ❗ Why is it dangerous?

You can keep looping through the cycle to reduce the distance infinitely → **shortest path is undefined**

---

## 5️⃣ Bellman–Ford Algorithm — Step by Step

### 🟢 Step 1: Initialization

* `dist[S] = 0`
* `dist[all other nodes] = ∞`

---

### 🟢 Step 2: Relax All Edges (V−1 times)

For `i = 1 to V−1`
 For each edge `(u → v, wt)`
  If `dist[u] + wt < dist[v]`
   Update `dist[v]`

---

### 🟢 Step 3: Detect Negative Cycle

* Iterate over all edges **one more time**
* If **any distance still decreases**

    * ❌ Negative cycle exists → return `[-1]`

---

### 🟢 Step 4: Return Result

* If no negative cycle → return distance array

---

## 6️⃣ Pseudocode (Very Important for Interviews)

```text
BellmanFord(V, edges, S):
    dist[ ] = array of size V
    fill dist with ∞
    dist[S] = 0

    // Relax edges V-1 times
    for i = 1 to V-1:
        for each edge (u, v, wt):
            if dist[u] != ∞ and dist[u] + wt < dist[v]:
                dist[v] = dist[u] + wt

    // Check negative cycle
    for each edge (u, v, wt):
        if dist[u] != ∞ and dist[u] + wt < dist[v]:
            return [-1]

    return dist
```

---

## 7️⃣ Time & Space Complexity

| Metric               | Value        |
| -------------------- | ------------ |
| **Time Complexity**  | **O(V × E)** |
| **Space Complexity** | **O(V)**     |

📌 Slower than Dijkstra but **more powerful**

---

## 8️⃣ Directed vs Undirected Graphs

* Bellman–Ford is **naturally for directed graphs**
* For **undirected graphs**:

    * Convert each edge `(u, v)` into:

        * `(u → v)`
        * `(v → u)`

⚠️ Negative edge in undirected graph = **instant negative cycle**

---

## 9️⃣ When Should You Use Bellman–Ford?

✅ Use when:

* Graph has **negative weights**
* You must **detect negative cycles**
* Problem explicitly mentions **negative values**

❌ Avoid when:

* Large graph + no negative edges → use **Dijkstra**

---

## 🔟 Interview Tips & Traps

### ⚠️ Common Mistakes

* Forgetting `dist[u] != ∞` check
* Missing the final negative cycle detection loop
* Using Bellman–Ford unnecessarily when Dijkstra suffices

---

### 💡 One-Line Interview Explanation

> *Bellman–Ford repeatedly relaxes all edges V−1 times to compute shortest paths and uses one extra pass to detect negative cycles.*

---

## 1️⃣1️⃣ Summary (Perfect for Notes)

* Bellman–Ford handles **negative edges**
* Uses **edge relaxation**
* Runs **V−1 iterations**
* Extra iteration detects **negative cycle**
* Returns `[-1]` if negative cycle exists


