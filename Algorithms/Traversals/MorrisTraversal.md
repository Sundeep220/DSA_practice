# 🌳 What Is Morris Traversal?

Morris Traversal is a method to traverse a **binary tree** (usually **inorder**) **without using recursion or a stack**,
i.e. it achieves **O(1) extra space**.

It temporarily modifies the tree’s structure during traversal (by creating and later removing *threads* — temporary right pointers).

---

## 🧠 The Core Idea (Inorder Traversal)

Recall normal **inorder traversal**:

```
Left → Root → Right
```

In recursion or stack-based inorder traversal, you store the path to return back to the parent after finishing the left subtree.

👉 Morris traversal avoids storing anything by creating a temporary link (thread) back to the parent.

---

### 🔹 The Trick

For each node:

1. If **no left child** →
   → Print current node and go right.
2. If **left child exists** →
   → Find the **rightmost node in the left subtree** (called the *inorder predecessor*).

    * If predecessor’s `right` pointer is **null**, set it to current node (thread), and move to left child.
    * If predecessor’s `right` pointer is **current node**, it means left subtree is done → break the thread, print current, move right.

---

### 🧩 Visual Example

Tree:

```
        1
       / \
      2   3
     / \
    4   5
```

**Inorder (expected output):** `4 2 5 1 3`

#### Step-by-step:

1. Start at 1 → has left (2)
2. Go to predecessor of 1 → rightmost of left subtree = 5
   → Set 5.right = 1 (thread)
3. Move to left child → 2
4. Predecessor of 2 = 5.right = null
   → Set 5.right = 2, move to left → 4
5. 4 has no left → print 4, move right (null)
6. Backtrack via thread → 2
   → remove thread, print 2
   → move right → 5
   → print 5, follow thread → 1
   → remove thread, print 1
   → move right → 3
   → print 3 ✅

---

## ✅ Java Code — Inorder Morris Traversal

```java
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

public class MorrisTraversal {

    // Perform Inorder Traversal without stack or recursion
    public void morrisInorder(TreeNode root) {
        TreeNode current = root;

        while (current != null) {
            if (current.left == null) {
                // Case 1: No left child — visit and move right
                System.out.print(current.val + " ");
                current = current.right;
            } else {
                // Case 2: Has a left child — find inorder predecessor
                TreeNode predecessor = current.left;

                // Move to the rightmost node in left subtree
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Create temporary thread to return later
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Thread already exists → left subtree done
                    predecessor.right = null; // remove thread
                    System.out.print(current.val + " ");
                    current = current.right;
                }
            }
        }
    }
}
```

---

## 🔍 Output Example

For this tree:

```
        1
       / \
      2   3
     / \
    4   5
```

**Output:**

```
4 2 5 1 3
```

✅ Matches the inorder traversal.

---

## ⚙️ Complexity Analysis

| Metric             | Value                              | Why                             |
| ------------------ | ---------------------------------- | ------------------------------- |
| **Time**           | O(n)                               | Each edge visited at most twice |
| **Space**          | O(1)                               | No recursion or stack used      |
| **Modifies tree?** | Temporarily, but restores it fully |                                 |

---

## 💡 Variants

Morris Traversal can be adapted for:

| Traversal     | Order     | Key Modification               |
| ------------- | --------- | ------------------------------ |
| **Inorder**   | L → N → R | Standard algorithm             |
| **Preorder**  | N → L → R | Print before going left        |
| **Postorder** | L → R → N | More complex; needs dummy node |

---

## 🧠 Why It's Special

* Uses **no stack or recursion**
* **Restores tree structure** after traversal
* Perfect for **space-constrained environments**
* Commonly asked in **interviews for advanced candidates** (Amazon, Google)

---

## 🧩 Bonus — Preorder Version (for completeness)

```java
public void morrisPreorder(TreeNode root) {
    TreeNode current = root;

    while (current != null) {
        if (current.left == null) {
            System.out.print(current.val + " ");
            current = current.right;
        } else {
            TreeNode predecessor = current.left;
            while (predecessor.right != null && predecessor.right != current) {
                predecessor = predecessor.right;
            }

            if (predecessor.right == null) {
                // Print before creating thread (Preorder)
                System.out.print(current.val + " ");
                predecessor.right = current;
                current = current.left;
            } else {
                predecessor.right = null;
                current = current.right;
            }
        }
    }
}
```

---
