package Problems.Trees.BinarySearchTrees.Hard;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation;

public class MaximumSumInBST {

    // Problem: https://leetcode.com/problems/maximum-sum-bst-in-binary-tree/
    // Time: O(N) because every node is processed once.
    //Space: O(H)

    int maxSum = 0;

    static class Info {
        boolean isBST;
        int min;
        int max;
        int sum;

        Info(boolean isBST, int min, int max, int sum) {
            this.isBST = isBST;
            this.min = min;
            this.max = max;
            this.sum = sum;
        }
    }

    public int maxSumBST(binaryTreeImplementation.TreeNode<Integer> root) {
        dfs(root);
        return maxSum;
    }

    private Info dfs(binaryTreeImplementation.TreeNode<Integer> node) {

        // Empty tree is a dataid BST
        if (node == null) {
            return new Info(
                    true,
                    Integer.MAX_VALUE,
                    Integer.MIN_VALUE,
                    0
            );
        }

        // Postorder
        Info left = dfs(node.left);
        Info right = dfs(node.right);

        // Current subtree is a BST
        if (left.isBST && right.isBST && left.max < node.data && node.data < right.min) {

            int sum = left.sum + right.sum + node.data;

            maxSum = Math.max(maxSum, sum);

            int min = Math.min(left.min, node.data);
            int max = Math.max(right.max, node.data);

            return new Info(
                    true,
                    min,
                    max,
                    sum
            );
        }

        // Current subtree is NOT a BST
        return new Info(
                false,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                0
        );
    }
}
