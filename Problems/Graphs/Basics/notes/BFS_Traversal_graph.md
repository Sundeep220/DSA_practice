Great — this is **classic BFS**, and it directly connects to what you already know as **level order traversal in trees**.
I’ll explain it **from intuition → algorithm → Java code → dry run**, exactly matching your notes.

---

# 🌊 Breadth First Search (BFS)

### (Level Order Traversal in Graphs)

---

## 1️⃣ What is BFS?

**Breadth First Search (BFS)** traverses a graph:

> **Level by level**, visiting all neighbors first, then their neighbors.

📌 In trees, this is called **level order traversal**.
📌 In graphs, we call it **BFS**.

---

## 2️⃣ Key Difference from Trees ⚠️

In **trees**:

* No cycles
* No need for `visited[]`

In **graphs**:

* Cycles exist
* Multiple paths to same node
* 👉 **visited[] is mandatory**

---

## 3️⃣ Data Structures Used

| Structure          | Purpose                          |
| ------------------ | -------------------------------- |
| **Queue (FIFO)**   | Ensures level-by-level traversal |
| **visited[]**      | Prevents infinite loops          |
| **Adjacency List** | Stores graph                     |

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20240216084522/bfs-vs-dfs-%281%29.png)

![Image](https://he-s3.s3.amazonaws.com/media/uploads/fdec3c2.jpg)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20240215173832/BFS_1tree.png)

---

## 4️⃣ Problem Statement (Restated)

> Given an **undirected graph**, return the list of nodes visited using **BFS traversal**, starting from a given node.

---

## 5️⃣ BFS Algorithm (Core Logic)

### Initial Configuration

* Create a **queue**
* Create a **visited array**
* Create a **result list**

---

### Algorithm Steps

1. Start from a node `start`
2. Mark it as visited
3. Push it into the queue
4. While queue is not empty:

    * Pop front node `v`
    * Add `v` to result
    * For every adjacent node of `v`:

        * If unvisited → mark visited → push to queue

---

## 6️⃣ BFS Java Code (Single Component)

```java
import java.util.*;

public class BFSGraph {

    static List<Integer> bfs(int start, List<List<Integer>> adj, int V) {
        boolean[] visited = new boolean[V + 1];
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> bfsTraversal = new ArrayList<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            bfsTraversal.add(node);

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return bfsTraversal;
    }
}
```

---

## 7️⃣ BFS for Entire Graph (Multiple Components) ✅

This is **VERY IMPORTANT** for interviews.

```java
static List<Integer> bfsOfGraph(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V + 1];
    List<Integer> result = new ArrayList<>();

    for (int i = 1; i <= V; i++) {
        if (!visited[i]) {
            Queue<Integer> queue = new LinkedList<>();
            visited[i] = true;
            queue.offer(i);

            while (!queue.isEmpty()) {
                int node = queue.poll();
                result.add(node);

                for (int neighbor : adj.get(node)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
        }
    }
    return result;
}
```

---

## 8️⃣ Dry Run (Small Example)

Graph:

```
1 ─ 2 ─ 5
│
3 ─ 4
```

Adjacency List:

```
1 → [2,3]
2 → [1,5]
3 → [1,4]
4 → [3]
5 → [2]
```

Start BFS from `1`

---

### Step-by-Step Execution

| Queue | Visited | Output      |
| ----- | ------- | ----------- |
| [1]   | 1       | []          |
| [2,3] | 1,2,3   | [1]         |
| [3,5] | 1,2,3,5 | [1,2]       |
| [5,4] | +4      | [1,2,3]     |
| [4]   | —       | [1,2,3,5]   |
| []    | —       | [1,2,3,5,4] |

✅ Output:

```
1 2 3 5 4
```

---

## 9️⃣ Why Queue Works (Important Intuition)

Queue = **FIFO**

* Nodes discovered first are processed first
* Ensures **level-wise traversal**
* Guarantees shortest path in unweighted graphs

---

## 🔟 Time & Space Complexity

* **Time:** `O(V + E)`
* **Space:** `O(V)`

---

## 1️⃣1️⃣ Common Mistakes ⚠️

❌ Forgetting `visited[]`
❌ Marking visited **after** popping instead of before pushing
❌ Calling BFS only once (ignoring components)
❌ Using stack instead of queue

---

## 🔑 One-Line Summary (Memorize This)

> BFS explores a graph **level by level** using a **queue** and a **visited array**.

---
