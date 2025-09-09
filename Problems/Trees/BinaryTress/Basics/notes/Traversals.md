Perfect timing 👌 — after learning **types of binary trees**, the **next big concept is traversal** (visiting nodes in some order).

There are **two main types of traversals** in a **Binary Tree (BT):**

---

# 🌳 1. **Depth-First Traversal (DFS)**

👉 Explore as far down a branch as possible before backtracking.

There are **3 common DFS orders**:

### (a) **Inorder (Left → Root → Right)**

* Visit left subtree
* Visit root
* Visit right subtree
  ✅ In a **BST**, inorder gives nodes in **sorted order**.

Example tree:

```
       1
      / \
     2   3
    / \
   4   5
```

Inorder = `4, 2, 5, 1, 3`

---

### (b) **Preorder (Root → Left → Right)**

* Visit root
* Visit left subtree
* Visit right subtree
  ✅ Useful for **copying/cloning** a tree or **serializing**.

Preorder = `1, 2, 4, 5, 3`

---

### (c) **Postorder (Left → Right → Root)**

* Visit left subtree
* Visit right subtree
* Visit root
  ✅ Useful for **deleting/freeing** a tree or evaluating expressions.

Postorder = `4, 5, 2, 3, 1`

---

### 🔹 DFS Code in Java

```java
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

public class BinaryTreeDFS {
    // Inorder
    void inorder(TreeNode node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.val + " ");
        inorder(node.right);
    }

    // Preorder
    void preorder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.val + " ");
        preorder(node.left);
        preorder(node.right);
    }

    // Postorder
    void postorder(TreeNode node) {
        if (node == null) return;
        postorder(node.left);
        postorder(node.right);
        System.out.print(node.val + " ");
    }
}
```

---

# 🌳 2. **Breadth-First Traversal (BFS)**

👉 Visit nodes **level by level** (top to bottom, left to right).

* Uses a **queue** data structure.
* Also called **Level Order Traversal**.

✅ Example (same tree):

```
       1
      / \
     2   3
    / \
   4   5
```

Level-order = `1, 2, 3, 4, 5`

---

### 🔹 BFS Code in Java

```java
import java.util.*;

public class BinaryTreeBFS {
    void levelOrder(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");

            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
    }
}
```

---

# ✅ Traversal Summary

| Traversal   | Type | Order               | Use-case                    |
| ----------- | ---- | ------------------- | --------------------------- |
| Inorder     | DFS  | Left → Root → Right | Sorted order in BST         |
| Preorder    | DFS  | Root → Left → Right | Clone/serialize tree        |
| Postorder   | DFS  | Left → Right → Root | Delete/evaluate expressions |
| Level-order | BFS  | Level by level      | Shortest path, BFS problems |

---

👉 So in short:

* **DFS** → Inorder, Preorder, Postorder
* **BFS** → Level Order

---

Would you like me to next show you **iterative versions of DFS traversals (using stack)**, since that’s a common interview follow-up after recursion?
