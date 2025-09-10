package Problems.Trees.BinaryTress.Easy;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class CheckBalancedHeight {
    // Problem: https://leetcode.com/problems/balanced-binary-tree/

    // Brute Force: check for every node
    // Time Complexity: O(n²) time | O(n) space
    public boolean isBalanced(TreeNode<Integer> root) {
        if(root == null) return true;
        return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    int height(TreeNode<Integer> root) {
        if(root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    // Optimized: check for every node
    // Time Complexity: O(n) time | O(n) space
    // Idea:
    // We only return -1 if the current node height is unbalanced
    public boolean isBalancedOptimized(TreeNode<Integer> root) {
        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode<Integer> node) {
        if (node == null) return 0;

        int left = checkHeight(node.left);
        if (left == -1) return -1; // left subtree unbalanced

        int right = checkHeight(node.right);
        if (right == -1) return -1; // right subtree unbalanced

        if (Math.abs(left - right) > 1) return -1; // current node unbalanced

        return 1 + Math.max(left, right);
    }
}
