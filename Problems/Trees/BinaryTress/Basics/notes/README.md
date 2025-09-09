# Trees in Java — a beginner-friendly deep dive

Nice — trees are a fundamental data structure and a great next step after arrays and linked lists. I’ll walk you through the core concepts, show clear Java examples (both binary trees and binary search trees), explain common operations and traversals (recursive + iterative), complexity, common pitfalls, and give practice problems you can implement.

---

# What is a tree? (analogy + definition)

Think of a family tree or an organizational chart. A **tree** is a hierarchical data structure made of **nodes** connected by edges. It has:

* a single **root** (top node),
* zero or more **children** per node,
* no cycles (you can't go back to the same node by following child links).

A **binary tree** is a tree where each node has at most two children (commonly called `left` and `right`).

---

# Basic terminology

* **Node**: element that stores value + references to children.
* **Root**: top node (no parent).
* **Child / Parent / Sibling**: relationship terms.
* **Leaf**: node with no children.
* **Depth**: number of edges from root to node.
* **Height**: maximum depth among all nodes (or number of edges on longest root→leaf path).
* **Subtree**: a node and all its descendants.

---

# Common tree types

* **Binary Tree** — at most two children.
* **Binary Search Tree (BST)** — binary tree with ordering: left subtree < node < right subtree (usually). Enables fast search.
* **Balanced trees** — AVL, Red-Black (self-balancing BSTs).
* **Heap** — complete binary tree used for priority queues (array-backed).
* **Trie (prefix tree)** — for strings/prefix operations.
* **Segment tree / Fenwick tree** — for range queries.

---

# How to represent a binary tree in Java

A minimal generic node:

```java
public class TreeNode<T> {
    T val;
    TreeNode<T> left;
    TreeNode<T> right;

    public TreeNode(T val) {
        this.val = val;
    }
}
```

For a **binary tree** (no ordering requirement) you typically just link nodes as above. For a **BST** you restrict `T` to `Comparable<T>` so you can compare values.

---

# Example: Binary Search Tree (BST) implementation (insert, search, delete)

```java
public class BST<T extends Comparable<T>> {
    private static class Node<T> {
        T val;
        Node<T> left, right;
        Node(T v) { val = v; }
    }

    private Node<T> root;

    // Search (recursive)
    public boolean contains(T key) {
        return contains(root, key);
    }
    private boolean contains(Node<T> node, T key) {
        if (node == null) return false;
        int cmp = key.compareTo(node.val);
        if (cmp == 0) return true;
        if (cmp < 0) return contains(node.left, key);
        else return contains(node.right, key);
    }

    // Insert (recursive)
    public void insert(T key) {
        root = insert(root, key);
    }
    private Node<T> insert(Node<T> node, T key) {
        if (node == null) return new Node<>(key);
        int cmp = key.compareTo(node.val);
        // policy for duplicates: put them to the right (or you can disallow)
        if (cmp <= 0) node.left = insert(node.left, key); // or use < to send duplicates right
        else node.right = insert(node.right, key);
        return node;
    }

    // Delete (recursive) - handles 3 cases
    public void delete(T key) {
        root = delete(root, key);
    }
    private Node<T> delete(Node<T> node, T key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.val);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            // node to delete found
            if (node.left == null) return node.right;   // no left child
            if (node.right == null) return node.left;   // no right child
            // two children: replace node with inorder successor (min in right subtree)
            Node<T> successor = min(node.right);
            successor.right = deleteMin(node.right);
            successor.left = node.left;
            node = successor;
        }
        return node;
    }
    private Node<T> min(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }
    private Node<T> deleteMin(Node<T> node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }
}
```

Notes:

* Deleting a node with two children: replace with the inorder successor (smallest node in right subtree) or inorder predecessor.
* You can write iterative versions too, but recursive is clearer for beginners.

---

# Tree traversals (what they visit and when)

Given a node `N`:

* **Preorder** (root, left, right): visit N, traverse left, traverse right.
* **Inorder** (left, root, right): traverse left, visit N, traverse right. (In BST, inorder gives sorted order.)
* **Postorder** (left, right, root): traverse left, traverse right, visit N.
* **Level-order** (breadth-first): visit nodes level by level, left→right (use a queue).

Example tree:

```
       8
     /   \
    3     10
   / \      \
  1   6      14
     / \     /
    4   7   13
```

Orders:

* Preorder: 8, 3, 1, 6, 4, 7, 10, 14, 13
* Inorder: 1, 3, 4, 6, 7, 8, 10, 13, 14  (sorted)
* Postorder: 1, 4, 7, 6, 3, 13, 14, 10, 8
* Level-order: 8, 3, 10, 1, 6, 14, 4, 7, 13

---

# Traversal code samples

### Recursive inorder (simple & common)

```java
public void inorder(TreeNode<Integer> node) {
    if (node == null) return;
    inorder(node.left);
    System.out.print(node.val + " ");
    inorder(node.right);
}
```

### Iterative inorder (using stack)

```java
public void inorderIterative(TreeNode<Integer> root) {
    Deque<TreeNode<Integer>> stack = new ArrayDeque<>();
    TreeNode<Integer> curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        System.out.print(curr.val + " ");
        curr = curr.right;
    }
}
```

### Level-order (BFS) using queue

```java
public void levelOrder(TreeNode<Integer> root) {
    if (root == null) return;
    Queue<TreeNode<Integer>> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
        TreeNode<Integer> node = q.poll();
        System.out.print(node.val + " ");
        if (node.left != null) q.add(node.left);
        if (node.right != null) q.add(node.right);
    }
}
```

---

# Complexity (quick summary)

* Traversal (any full traversal): **O(n)** time, **O(h)** extra space (recursion stack) where `h` is height.
* BST search/insert/delete: **O(h)** time.

    * If tree is balanced: `h = O(log n)` → operations `O(log n)`.
    * If degenerate (linked-list): `h = O(n)` → operations `O(n)`.
* Level-order uses `O(n)` space worst-case (when a level contains \~n/2 nodes).

---

# Array representation (for complete binary trees / heaps)

A complete binary tree can be stored in an array:

* For node at index `i` (0-based):

    * left child index = `2*i + 1`
    * right child index = `2*i + 2`
    * parent index = `(i-1)/2` (integer division)

This is how heaps (priority queues) are commonly implemented.

---

# Common interview tips & pitfalls

* **Draw the tree** before coding to understand traversal orders and cases.
* For recursion, always define clear base-case(s): `if (node == null) return;`.
* When implementing BST delete, handle the three cases carefully (0,1,2 children).
* Watch for **null pointer exceptions** when accessing `left`/`right`.
* Know both recursive and iterative traversals (stack for DFS). Iterative postorder is trickier — two stacks or modified one-stack approach.
* Inorder traversal of BST yields sorted order — useful for many problems.
* Think about **balanced** vs **unbalanced** trees and implications for complexity.
* For generics: use `T extends Comparable<T>` when you need comparisons.

---

# Practice problems (implement these)

1. Compute tree **height** / max depth.
2. Check if a binary tree is **balanced** (AVL condition).
3. **Lowest Common Ancestor (LCA)** of two nodes in a binary tree.
4. **Serialize / Deserialize** a binary tree (LeetCode 297).
5. Convert **sorted array to balanced BST**.
6. **Validate BST** — check whether a binary tree satisfies BST properties.
7. **Level-order zigzag** traversal (alternate direction per level).
8. **Kth smallest element** in BST (inorder traversal).
9. **Path sum** / print all root-to-leaf paths.
10. Implement **AVL** rotations (learn self-balancing techniques).

---

# Small worked example (BST + inorder output)

Insert sequence: `8, 3, 10, 1, 6, 14, 4, 7, 13` into an empty BST.
Inorder traversal will print: `1 3 4 6 7 8 10 13 14` — sorted sequence. This is a great quick check after implementing insertion + inorder.

---

# Next learning steps

1. Implement the BST code above and test using small sequences.
2. Implement iterative traversals (stack-based) and BFS (queue-based).
3. Study balanced BSTs: **AVL** and **Red-Black Tree**.
4. Learn **Tries** (prefix trees) and their use-cases (autocomplete, dictionary).
5. Solve the practice problems above — start simple, then move to medium/hard.

---

If you want, I can:

* Give you a downloadable Java project with `TreeNode`, `BinaryTree`, `BST`, and unit tests; **or**
* Walk through any one of the practice problems step-by-step (with full Java code); **or**
* Explain iterative postorder traversal in detail (it’s the trickiest).

Which of those would you like next?
