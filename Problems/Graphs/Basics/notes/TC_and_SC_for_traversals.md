Great question 👍
This is **mandatory interview knowledge**, so let’s be **very precise and clear**.

---

# ⏱️ Time & Space Complexity of Graph Traversals

## (BFS and DFS)

We’ll analyze **both traversals** under the **same conditions** so you can compare them easily.

---

## 🔢 Notations Used

| Symbol | Meaning                    |
| ------ | -------------------------- |
| `V`    | Number of vertices (nodes) |
| `E`    | Number of edges            |

We assume:

* Graph is stored using **Adjacency List**
* Graph may be **disconnected**

---

## 1️⃣ BFS (Breadth First Search)

### 🔹 Time Complexity

**Key idea**:

* Every vertex is visited **once**
* Every edge is explored **once**

👉 For an **undirected graph**, each edge appears **twice** in adjacency list
(still linear overall)

### ✅ Time Complexity:

```
O(V + E)
```

✔ Visiting all vertices → `O(V)`
✔ Traversing all adjacency lists → `O(E)`

---

### 🔹 Space Complexity

Space is used by:

1. `visited[]` array → `O(V)`
2. `Queue` → worst case can store all vertices → `O(V)`
3. Adjacency list → `O(V + E)` (input storage)

### ✅ Auxiliary Space (excluding input):

```
O(V)
```

### ✅ Total Space (including graph storage):

```
O(V + E)
```

---

## 2️⃣ DFS (Depth First Search)

### 🔹 Time Complexity

**Same reasoning as BFS**:

* Each vertex visited once
* Each edge explored once

### ✅ Time Complexity:

```
O(V + E)
```

---

### 🔹 Space Complexity (IMPORTANT DIFFERENCE)

DFS uses:

1. `visited[]` → `O(V)`
2. **Recursion stack** → worst case `O(V)` (skewed graph)
3. Adjacency list → `O(V + E)`

### ✅ Auxiliary Space (Recursive DFS):

```
O(V)
```

### ✅ Total Space:

```
O(V + E)
```

📌 If using **iterative DFS (stack)**:

* Stack size → `O(V)`
* Same complexity

---

## 3️⃣ Side-by-Side Comparison (Interview Gold 🥇)

| Aspect          | BFS                         | DFS                           |
| --------------- | --------------------------- | ----------------------------- |
| Traversal order | Level-wise                  | Depth-wise                    |
| Time complexity | `O(V + E)`                  | `O(V + E)`                    |
| Extra space     | `O(V)`                      | `O(V)`                        |
| Data structure  | Queue                       | Recursion / Stack             |
| Risk            | Memory heavy in wide graphs | Stack overflow in deep graphs |
| Best used for   | Shortest path (unweighted)  | Cycle detection, components   |

---

## 4️⃣ Special Cases You Should Mention in Interviews

### 🔹 Adjacency Matrix

If graph is stored as a matrix:

* Time Complexity:

```
O(V²)
```

Because:

* For each vertex, we scan all `V` neighbors

---

## 5️⃣ One-Line Interview Answer (Memorize This)

> **Both BFS and DFS have time complexity `O(V + E)` and auxiliary space `O(V)` when using adjacency lists.**

---

## 6️⃣ Common Interview Traps ⚠️

❌ Saying BFS is faster than DFS
❌ Ignoring recursion stack in DFS
❌ Forgetting adjacency matrix case
❌ Saying space is `O(E)` for queue/stack

---

## 🔑 Final Mental Model

* **Time** → “How many nodes + edges do I touch?”
* **Space** → “What extra memory do I need to remember visited nodes?”

---
