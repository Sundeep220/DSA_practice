package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.*;

public class KthSmallestNode {
    // Problem: leetcode 230. Kth Smallest Element in a BST - https://leetcode.com/problems/kth-smallest-element-in-a-bst/


    // Naive Solutions:
    // 1.  Using Traversal and Sorting
    // Time Complexity: O(nlogn) Space Complexity: O(n)
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        traverse(root, list);
        Collections.sort(list);
        return list.get(k - 1);
    }

    private void traverse(TreeNode root, List<Integer> list) {
        if (root == null) return;
        list.add((Integer) root.data);
        traverse(root.left, list);
        traverse(root.right, list);
    }


    // 2. Using Inorder Traversal Property of BST
    // Time Complexity: O(n) Space Complexity: O(n)
    public int kthSmallestInorder(TreeNode root, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        return inorder.get(k - 1);
    }

    private void inorderTraversal(TreeNode root, List<Integer> inorder) {
        if (root == null) return;
        inorderTraversal(root.left, inorder);
        inorder.add((Integer) root.data);
        inorderTraversal(root.right, inorder);
    }

    // Better Solution #3: Inorder Traversal with Counter (Early Stop)
    // Time Complexity: O(n) Space Complexity: O(h) Recursion Stack
    int count = 0;
    int result = -1;

    public int kthSmallestInorderBetter(TreeNode root, int k) {
        inorder(root, k);
        return result;
    }

    private void inorder(TreeNode root, int k) {
        if (root == null) return;

        inorder(root.left, k);

        count++;
        if (count == k) {
            result = (int) root.data;
            return;
        }

        inorder(root.right, k);
    }

    // Better Solution #4: Using Inorder Iterative Solution
    // Time Complexity: O(n) Space Complexity: O(h)
    public int kthSmallestInorderIterative(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();

        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            k--;

            if (k == 0) return (int) root.data;

            root = root.right;
        }
    }

    // Better Solution: Using Min Heap
    // Time Complexity: O(nlogn) Space Complexity: O(n)
    public int kthSmallestMinHeap(TreeNode root, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        dfs(root, minHeap);

        while (k-- > 1) {
            minHeap.poll();
        }
        return minHeap.peek();
    }

    private void dfs(TreeNode root, PriorityQueue<Integer> heap) {
        if (root == null) return;
        heap.offer((Integer) root.data);
        dfs(root.left, heap);
        dfs(root.right, heap);
    }

    // Better Solution: Using Max Heap
    // Time Complexity: O(nlogk) Space Complexity: O(k)
    public int kthSmallestMaxHeap(TreeNode root, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        dfs(root, maxHeap, k);
        return maxHeap.peek();
    }

    private void dfs(TreeNode root, PriorityQueue<Integer> heap, int k) {
        if (root == null) return;

        heap.offer((Integer) root.data);
        if (heap.size() > k) {
            heap.poll();  // remove largest
        }

        dfs(root.left, heap, k);
        dfs(root.right, heap, k);
    }


    // Optimal Solution #5: Using Morris Traversal
    // Time Complexity: O(n) Space Complexity: O(1)
    public int kthSmallestOptimal(TreeNode root, int k) {
        int count = 0;   // Counts how many nodes have been visited in inorder

        // Continue traversal until all nodes are processed
        while (root != null) {

            // Case 1: No left child
            // This means current node is the next inorder node
            if (root.left == null) {
                count++;                     // Visit current node
                if (count == k)
                    return (int) root.data;  // kth smallest found

                root = root.right;           // Move to right subtree
            }
            else {
                // Case 2: Left child exists
                // Find inorder predecessor (rightmost node in left subtree)
                TreeNode prev = root.left;
                while (prev.right != null && prev.right != root) {
                    prev = prev.right;
                }

                // Case 2a: First time visiting this node
                // Create a temporary thread back to current node
                if (prev.right == null) {
                    prev.right = root;   // Create thread
                    root = root.left;    // Move to left subtree
                }
                // Case 2b: Thread already exists (left subtree fully processed)
                else {
                    prev.right = null;   // Remove the thread (restore tree)

                    count++;             // Visit current node
                    if (count == k)
                        return (int) root.data;

                    root = root.right;   // Move to right subtree
                }
            }
        }

        // If k is larger than number of nodes
        return -1;
    }


}
