package Problems.Trees.BinaryTress.Hard;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class FlattenBinaryTree {
    // Problem: https://leetcode.com/problems/flatten-binary-tree-to-linked-list/

    public void flatten(TreeNode root) {
        flattenHelper(root, null);
    }

    private TreeNode flattenHelper(TreeNode root, TreeNode next) {
        // Base case
        if (root == null) return next;

        // First flatten right subtree, passing current 'next'
        next = flattenHelper(root.right, next);

        // Then flatten left subtree, passing updated 'next'
        next = flattenHelper(root.left, next);

        // Rewire pointers
        root.right = next;
        root.left = null;

        // Current node becomes the new 'next'
        return root;
    }
}
