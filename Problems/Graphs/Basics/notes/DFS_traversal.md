Perfect 👍
We’ll do **DFS exactly in the same clean style as BFS**:

* DFS logic in **one function**
* Outer loop handles **connected components**
* Clear intuition + code + dry run

---

# 🌲 Depth First Search (DFS) in Graphs

## 1️⃣ What is DFS?

**Depth First Search (DFS)** explores a graph by:

> Going **as deep as possible** along one path before backtracking.

📌 Think of it as:

* Go to a neighbor
* Then neighbor’s neighbor
* Keep going until you can’t

DFS in graphs is very similar to **preorder traversal in trees**, but with `visited[]`.

![Image](https://www.interviewbit.com/blog/wp-content/uploads/2021/12/DFS-Algorithm.png)

![Image](https://he-s3.s3.amazonaws.com/media/uploads/9fa1119.jpg)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/geekfofgeek_dfs_graph.png)

---

## 2️⃣ DFS vs BFS (Quick Intuition)

| BFS                        | DFS                    |
| -------------------------- | ---------------------- |
| Level by level             | Depth by depth         |
| Uses Queue                 | Uses Recursion / Stack |
| Shortest path (unweighted) | Structure exploration  |
| Wider first                | Deeper first           |

---

## 3️⃣ Why `visited[]` is Mandatory ⚠️

Graphs can have:

* Cycles
* Back edges
* Multiple paths

Without `visited[]` → **infinite recursion** 🚨

---

## 4️⃣ DFS for **ONE Connected Component**

This function only explores **one component**.

### ✅ DFS Component Function (Recursive)

```java
static void dfsComponent(
        int node,
        List<List<Integer>> adj,
        boolean[] visited,
        List<Integer> result
) {
    visited[node] = true;
    result.add(node);

    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            dfsComponent(neighbor, adj, visited, result);
        }
    }
}
```

📌 Responsibility:

* Start from `node`
* Visit everything reachable from it
* Stop automatically when stuck

---

## 5️⃣ DFS for **Entire Graph** (Multiple Components)

This is where **connected components** are handled.

```java
static List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V + 1];
    List<Integer> result = new ArrayList<>();

    for (int i = 1; i <= V; i++) {
        if (!visited[i]) {
            dfsComponent(i, adj, visited, result);
        }
    }

    return result;
}
```

📌 Same pattern as BFS:

* Loop over all nodes
* Call DFS when node is unvisited

---

## 6️⃣ Complete Runnable Example

```java
import java.util.*;

public class DFSGraph {

    static void dfsComponent(
            int node,
            List<List<Integer>> adj,
            boolean[] visited,
            List<Integer> result
    ) {
        visited[node] = true;
        result.add(node);

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                dfsComponent(neighbor, adj, visited, result);
            }
        }
    }

    static List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
        boolean[] visited = new boolean[V + 1];
        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= V; i++) {
            if (!visited[i]) {
                dfsComponent(i, adj, visited, result);
            }
        }
        return result;
    }

    static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    public static void main(String[] args) {
        int V = 7;
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i <= V; i++) {
            adj.add(new ArrayList<>());
        }

        addEdge(adj, 1, 2);
        addEdge(adj, 1, 3);
        addEdge(adj, 2, 4);
        addEdge(adj, 5, 6);
        addEdge(adj, 5, 7);

        System.out.println(dfsOfGraph(V, adj));
    }
}
```

---

## 7️⃣ Dry Run (Important)

Graph:

```
1 ─ 2 ─ 4

|
3

5 ─ 6
|
7
```

### Execution:

* `i = 1` → DFS(1)

    * 1 → 2 → 4 (backtrack)
    * → 3
* `i = 5` → DFS(5)

    * 5 → 6
    * → 7

### Output (one possible order):

```
[1, 2, 4, 3, 5, 6, 7]
```

📌 DFS order depends on adjacency list order — **that’s OK**.

---

## 8️⃣ Iterative DFS (Using Stack) – FYI

Sometimes recursion is avoided.

```java
static void dfsIterative(int start, List<List<Integer>> adj, boolean[] visited, List<Integer> result) {
    Stack<Integer> stack = new Stack<>();
    stack.push(start);

    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited[node]) continue;

        visited[node] = true;
        result.add(node);

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                stack.push(neighbor);
            }
        }
    }
}
```

---

## 9️⃣ Common Mistakes ⚠️

❌ Forgetting `visited[]`
❌ Calling DFS only once
❌ Creating `visited[]` inside dfsComponent
❌ Expecting fixed traversal order

---

## 🔑 Golden Rule (Very Important)

> **DFS explores depth first, component by component**

Outer loop → components
Inner DFS → traversal

---
