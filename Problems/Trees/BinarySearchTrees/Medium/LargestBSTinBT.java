package Problems.Trees.BinarySearchTrees.Medium;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class LargestBSTinBT {
    // Problem: https://leetcode.com/problems/largest-bst-subtree/
    // Given a binary tree, find the largest subtree which is a Binary Search Tree (BST),
    // where largest means subtree with largest number of nodes in it. Return the size of the largest BST.

    // Naive Solution: Brute Force Subtree Check using ValidBST problem
    // Time: O(n^2) Space: O(h)
    public int largestBSTSubtreeNaive(TreeNode root) {
        if (root == null) return 0;

        // Check if entire tree rooted here is BST
        if (isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
            return size(root);
        }

        // Otherwise check left and right subtrees
        return Math.max(
                largestBSTSubtreeNaive(root.left),
                largestBSTSubtreeNaive(root.right)
        );
    }

    private boolean isBST(TreeNode node, int min, int max) {
        if (node == null) return true;
        if ((Integer) node.data <= min || (Integer) node.data >= max) return false;
        return isBST(node.left, min, (Integer) node.data)
                && isBST(node.right, (Integer) node.data, max);
    }

    private int size(TreeNode node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }


    // Optimal Solution: DFS with Bound Checks
    // We At each node, compute and return 3 pieces of information about its subtree:
    //minVal – minimum value in the subtree
    //maxVal – maximum value in the subtree
    //size – size of the subtree if it is a BST
    // For a subtree to be BST: leftSubtree.max < node.val < rightSubtree.min
    // Time: O(n) Space: O(h)
    private static class Info {
        int size;   // size of largest BST in this subtree
        int min;    // minimum value in subtree
        int max;    // maximum value in subtree

        Info(int size, int min, int max) {
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }

    private int maxBST = 0;

    public int largestBSTSubtree(TreeNode root) {
        dfs(root);
        return maxBST;
    }

    private Info dfs(TreeNode node) {
        // Base case: empty tree is a valid BST
        if (node == null) {
            return new Info(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        Info left = dfs(node.left);
        Info right = dfs(node.right);

        // Check BST condition
        if ((Integer) left.max < (Integer) node.data && (Integer) node.data < (Integer) right.min) {
            int size = left.size + right.size + 1;
            maxBST = Math.max(maxBST, size);

            return new Info(
                    size,
                    Math.min((Integer) left.min, (Integer) node.data),
                    Math.max((Integer) right.max, (Integer) node.data)
            );
        }

        // Not a BST → poison the range
        return new Info(
                Math.max(left.size, right.size),
                Integer.MIN_VALUE,
                Integer.MAX_VALUE
        );
    }
}
