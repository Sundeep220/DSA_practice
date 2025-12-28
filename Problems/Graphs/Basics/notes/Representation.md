Perfect 👍
Graph **representation in Java** is the **most important foundation** before BFS / DFS / all graph problems.

We’ll go **step-by-step**, assuming **you know Java well**, but **graphs are new**.

---

# 📦 Graph Representation in Java

## Why Representation Matters

A graph is:

```
Vertices + Edges
```

But computers need a **data structure** to store this.

👉 **Choice of representation affects**:

* Time complexity
* Space usage
* Ease of traversal

---

## 1️⃣ Common Ways to Represent Graphs

There are **two standard representations**:

| Representation       | Used When                     |
| -------------------- | ----------------------------- |
| **Adjacency List** ✅ | Sparse graphs (MOST problems) |
| **Adjacency Matrix** | Dense graphs                  |

We’ll cover both.

---

## 2️⃣ Adjacency List (MOST IMPORTANT) ✅

### Idea

For each vertex, store a list of its **neighbors**.

Example graph:

```
0 ── 1
|    |
2 ── 3
```

Adjacency list:

```
0 → [1, 2]
1 → [0, 3]
2 → [0, 3]
3 → [1, 2]
```

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20230727155209/Graph-Representation-of-Directed-graph-to-Adjacency-List.png)

![Image](https://www.masaischool.com/blog/content/images/2022/07/Adjacencey-List.png)

---

## 3️⃣ Adjacency List in Java (Undirected Graph)

### Step 1: Data Structure

```java
int V = 4;  // number of vertices

List<List<Integer>> adj = new ArrayList<>();

for (int i = 0; i < V; i++) {
    adj.add(new ArrayList<>());
}
```

Why this?

* `adj.get(u)` → neighbors of `u`

---

### Step 2: Add Edges

```java
void addEdge(List<List<Integer>> adj, int u, int v) {
    adj.get(u).add(v);
    adj.get(v).add(u); // because undirected
}
```

Usage:

```java
addEdge(adj, 0, 1);
addEdge(adj, 0, 2);
addEdge(adj, 1, 3);
addEdge(adj, 2, 3);
```

---

### Step 3: Print Graph

```java
for (int i = 0; i < V; i++) {
    System.out.print(i + " -> ");
    for (int neighbor : adj.get(i)) {
        System.out.print(neighbor + " ");
    }
    System.out.println();
}
```

Output:

```
0 -> 1 2
1 -> 0 3
2 -> 0 3
3 -> 1 2
```

---

## 4️⃣ Directed Graph (Adjacency List)

### Difference?

➡️ Only **one direction** added.

```java
void addEdge(List<List<Integer>> adj, int u, int v) {
    adj.get(u).add(v); // u → v
}
```

Example:

```
0 → 1 → 2
```

Adj list:

```
0 → [1]
1 → [2]
2 → []
```

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20241022101636506544/graph-representation-of-directed-graph-to-adjacency-list.webp)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20240424142803/Adjacency-Matrix-for-Directed-and-Unweighted-graph.webp)

---

## 5️⃣ Weighted Graph (Adjacency List)

We now store:

```
(neighbor, weight)
```

### Create Edge Class

```java
class Edge {
    int to;
    int weight;

    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}
```

---

### Adjacency List for Weighted Graph

```java
List<List<Edge>> adj = new ArrayList<>();

for (int i = 0; i < V; i++) {
    adj.add(new ArrayList<>());
}
```

---

### Add Weighted Edge

```java
void addEdge(List<List<Edge>> adj, int u, int v, int w) {
    adj.get(u).add(new Edge(v, w));
    adj.get(v).add(new Edge(u, w)); // undirected
}
```

---

## 6️⃣ Adjacency Matrix

### Idea

2D matrix where:

```
matrix[i][j] = 1 (edge exists)
matrix[i][j] = weight (weighted)
```

Example:

```
   0 1 2 3
0 [0 1 1 0]
1 [1 0 0 1]
2 [1 0 0 1]
3 [0 1 1 0]
```

---

### Java Code

```java
int V = 4;
int[][] graph = new int[V][V];

// undirected edge
graph[0][1] = 1;
graph[1][0] = 1;
```

📌 **Drawback**

* Space = O(V²)
* Bad for large graphs

---

## 7️⃣ When to Use What?

| Scenario       | Best Choice           |
| -------------- | --------------------- |
| Sparse graph   | Adjacency List ✅      |
| Dense graph    | Adjacency Matrix      |
| Weighted graph | Adj List + Edge class |
| BFS / DFS      | Adjacency List        |

👉 **Interviews → Adjacency List**

---

## 8️⃣ Graph Input in Competitive Programming

Input example:

```
V = 5, E = 6
0 1
0 2
1 3
2 3
3 4
1 4
```

Code:

```java
Scanner sc = new Scanner(System.in);
int V = sc.nextInt();
int E = sc.nextInt();

List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

for (int i = 0; i < E; i++) {
    int u = sc.nextInt();
    int v = sc.nextInt();
    adj.get(u).add(v);
    adj.get(v).add(u);
}
```

---

## 9️⃣ Common Mistakes (VERY IMPORTANT ⚠️)

❌ Forgetting `visited[]` → infinite loop
❌ Forgetting bidirectional edge in undirected graph
❌ Using matrix for large graphs
❌ Wrong indexing (0-based vs 1-based)

---

## 🔑 Mental Model (Remember This)

> `adj.get(u)` tells you **where you can go from u**

That’s it.

---
