Below are **clean, exam-ready + interview-ready notes** for **Floyd–Warshall Algorithm (G-42)**, written in a way you can revise quickly before interviews or coding rounds.

---

## Floyd–Warshall Algorithm (All-Pairs Shortest Path)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20250414145110313876/Floyd-Warshall-Algorithm-01.webp)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/0%2AHU7U2POPHuRlDk6r.jpg)

![Image](https://deen3evddmddt.cloudfront.net/uploads/content-images/floyd-warshall-algorithm.webp)

![Image](https://favtutor.com/resources/images/uploads/floyd_warshall_algorithm.png)

---

## 📌 Problem Recap

* Given a **directed weighted graph**
* Vertices: `0 to V-1`
* Input: **Adjacency Matrix**

    * `matrix[i][j] = weight of edge i → j`
    * `matrix[i][j] = -1` → **no edge**
* Task: Find **shortest distance between every pair (i, j)**

---

## 🔹 Key Idea (One Line Intuition)

> Try **every vertex as an intermediate node** and check
> whether going **i → k → j** is shorter than **i → j**

This is a **Dynamic Programming** algorithm.

---

## 🔹 Why Floyd–Warshall?

| Feature                 | Supported |
| ----------------------- | --------- |
| All-pairs shortest path | ✅         |
| Directed graph          | ✅         |
| Negative weights        | ✅         |
| Detect negative cycles  | ✅         |
| Large graph efficient   | ❌ (O(V³)) |

---

## 🔹 Core Concept (Dynamic Programming)

Let
`dist[i][j]` = shortest distance from `i` to `j` using only nodes `{0 … k}` as intermediates

We update this step by step for each `k`.

---

## 🧠 Algorithm Explanation (Clean & Correct)

### **Step 1: Initialize Distance Matrix**

* Create `dist[][]`
* If `i == j` → `dist[i][j] = 0`
* If `matrix[i][j] != -1` → `dist[i][j] = matrix[i][j]`
* Else → `dist[i][j] = INF`

---

### **Step 2: Try Every Node as Intermediate**

For every `k` from `0 to V-1`
For every pair `(i, j)`:

```
If dist[i][k] + dist[k][j] < dist[i][j]
    dist[i][j] = dist[i][k] + dist[k][j]
```

💡 This checks:

> “Is the path i → k → j shorter than i → j?”

---

### **Step 3: Final Distance Matrix**

* `dist[i][j]` now stores **shortest path from i to j**
* If `dist[i][j] == INF` → unreachable → return `-1`

---

## 📊 Visual Intuition (Very Important for Understanding)

### Image 1 – Initial Graph

* Direct edges only
* Missing edges treated as infinity

### Image 2 – Intermediate Node Processing

* One node `k` is allowed as a “bridge”
* Paths update dynamically

### Image 3 – Final Matrix

* Best distances between **all pairs**

---

## 🔹 Pseudocode (Perfect for Notes)

```text
Initialize dist[][]

for k = 0 to V-1:
    for i = 0 to V-1:
        for j = 0 to V-1:
            if dist[i][k] + dist[k][j] < dist[i][j]:
                dist[i][j] = dist[i][k] + dist[k][j]
```

---

## ⏱️ Time & Space Complexity

| Metric           | Value     |
| ---------------- | --------- |
| Time Complexity  | **O(V³)** |
| Space Complexity | **O(V²)** |

---

## ⚠️ Negative Cycle Detection (Important Interview Point)

After algorithm:

```
If dist[i][i] < 0 for any i
→ Negative cycle exists
```

---

## 🔍 When to Use Floyd–Warshall?

✅ Need shortest paths between **all pairs**
✅ Graph has **negative weights**
❌ Graph is very large (`V > 500`)

---

## 🆚 Comparison with Other Algorithms

| Algorithm      | Use Case                           |
| -------------- | ---------------------------------- |
| Dijkstra       | Single source, no negative weights |
| Bellman–Ford   | Single source, negative weights    |
| Floyd–Warshall | **All pairs**, negative weights    |

---

## 📝 Final Exam-Ready Summary

> Floyd–Warshall is a dynamic programming algorithm that computes the shortest distances between every pair of vertices by iteratively improving the solution using intermediate vertices.


