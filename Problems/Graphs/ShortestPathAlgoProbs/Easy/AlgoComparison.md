Below is a **complete, interview-grade comparison** of **Dijkstra, Bellman–Ford, and Floyd–Warshall**—covering **properties, constraints, complexity, use-cases, and decision rules**.

This is the kind of table + reasoning **interviewers expect you to articulate clearly**.

---

# 🔍 Shortest Path Algorithms — Full Comparison

## 1️⃣ High-Level Purpose

| Algorithm          | What it Solves               |
| ------------------ | ---------------------------- |
| **Dijkstra**       | Single-source shortest paths |
| **Bellman–Ford**   | Single-source shortest paths |
| **Floyd–Warshall** | **All-pairs shortest paths** |

---

## 2️⃣ Core Capability Comparison (MOST IMPORTANT)

| Property                 | Dijkstra              | Bellman–Ford          | Floyd–Warshall       |
| ------------------------ | --------------------- | --------------------- | -------------------- |
| Graph Type               | Directed / Undirected | Directed / Undirected | Directed             |
| Edge Weights             | **Non-negative only** | **Negative allowed**  | **Negative allowed** |
| Negative Cycle Detection | ❌ No                  | ✅ Yes                 | ✅ Yes                |
| Single Source            | ✅ Yes                 | ✅ Yes                 | ❌ No                 |
| All Pairs                | ❌ No                  | ❌ No                  | ✅ Yes                |
| Works on Dense Graph     | ⚠️ Slower             | ⚠️ Slower             | ✅ Designed for it    |
| Works on Sparse Graph    | ✅ Excellent           | ⚠️ OK                 | ❌ Bad                |

---

## 3️⃣ Time & Space Complexity

| Algorithm      | Time Complexity      | Space Complexity |
| -------------- | -------------------- | ---------------- |
| Dijkstra (PQ)  | **O((V + E) log V)** | O(V + E)         |
| Bellman–Ford   | **O(V × E)**         | O(V)             |
| Floyd–Warshall | **O(V³)**            | O(V²)            |

---

## 4️⃣ Algorithmic Nature (How they think)

| Algorithm      | Paradigm                           |
| -------------- | ---------------------------------- |
| Dijkstra       | Greedy                             |
| Bellman–Ford   | Dynamic Programming (Relaxation)   |
| Floyd–Warshall | Dynamic Programming (All-pairs DP) |

---

## 5️⃣ Why & When Each Algorithm Fails

### ❌ Dijkstra Fails When:

* Any edge has **negative weight**
* Greedy choice becomes invalid

👉 **Reason**: Once a node is finalized, Dijkstra never revisits it.

---

### ❌ Bellman–Ford Fails When:

* Graph is **too large**
* Performance becomes unacceptable

👉 Still correct, but **slow**

---

### ❌ Floyd–Warshall Fails When:

* `V > 400–500`
* Graph is sparse but huge

👉 O(V³) kills performance

---

## 6️⃣ Negative Cycle Handling

| Algorithm      | How Negative Cycle is Detected    |
| -------------- | --------------------------------- |
| Dijkstra       | ❌ Cannot detect                   |
| Bellman–Ford   | Extra relaxation on Vth iteration |
| Floyd–Warshall | Check `dist[i][i] < 0`            |

---

## 7️⃣ Practical Use-Cases (REAL WORLD)

### ✅ Use **Dijkstra** when:

* No negative edges
* Need fastest performance
* Navigation, maps, routing

**Examples**

* Google Maps
* Network routing
* Game AI shortest paths

---

### ✅ Use **Bellman–Ford** when:

* Negative edges exist
* Need negative cycle detection
* Graph is moderately sized

**Examples**

* Currency arbitrage detection
* Constraint systems
* Financial graphs

---

### ✅ Use **Floyd–Warshall** when:

* Need distances between **every pair**
* Graph is small (≤ 400)
* Multiple queries on same graph

**Examples**

* City-to-city distance matrix
* Dense graphs
* Transitive closure problems

---

## 8️⃣ Decision Tree (VERY IMPORTANT)

```
Need all-pairs shortest path?
    → YES → Floyd–Warshall

Need single source shortest path?
    → YES

        Has negative edges?
            → YES → Bellman–Ford
            → NO  → Dijkstra
```

---

## 9️⃣ Interview One-Liners (Memorize)

* **Dijkstra**: “Fastest single-source shortest path without negative edges.”
* **Bellman–Ford**: “Handles negative weights and detects negative cycles.”
* **Floyd–Warshall**: “All-pairs shortest paths using dynamic programming.”

---

## 10️⃣ Side-by-Side Summary Table (Quick Revision)

| Feature          | Dijkstra | Bellman–Ford | Floyd–Warshall |
| ---------------- | -------- | ------------ | -------------- |
| Negative Weights | ❌        | ✅            | ✅              |
| Negative Cycles  | ❌        | ✅            | ✅              |
| All Pairs        | ❌        | ❌            | ✅              |
| Speed            | ⭐⭐⭐⭐     | ⭐⭐           | ⭐              |
| Graph Size       | Large    | Medium       | Small          |
| Implementation   | Medium   | Easy         | Very Easy      |

---

## 11️⃣ Common Interview Traps ⚠️

❌ “Use Dijkstra with negative weights”
❌ “Early stop Floyd–Warshall on distance decrease”
❌ “Bellman–Ford is faster than Dijkstra”

---

## ✅ Final Takeaway

> **Dijkstra = fastest but strict**
> **Bellman–Ford = flexible but slow**
> **Floyd–Warshall = powerful but heavy**

---
