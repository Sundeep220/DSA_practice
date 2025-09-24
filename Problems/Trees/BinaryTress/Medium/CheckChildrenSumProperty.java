package Problems.Trees.BinaryTress.Medium;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;


public class CheckChildrenSumProperty {
    // Problem: Given a binary tree, check if the tree follows the Children Sum Property:
    // each parent node's value is equal to the sum of its children's values.

    boolean checkChildrenSum(TreeNode<Integer> root) {
        // Base case: empty tree OR leaf node satisfies property
        if (root == null || (root.left == null && root.right == null))
            return true;

        int sum = 0;

        if (root.left != null)
            sum += root.left.data;

        if (root.right != null)
            sum += root.right.data;

        // Current node must satisfy property AND both subtrees too
        return (root.data == sum)
                && checkChildrenSum(root.left)
                && checkChildrenSum(root.right);
    }
}
