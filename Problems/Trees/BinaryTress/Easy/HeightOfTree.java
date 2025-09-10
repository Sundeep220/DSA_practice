package Problems.Trees.BinaryTress.Easy;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class HeightOfTree {
    // Problem: https://leetcode.com/problems/maximum-depth-of-binary-tree/


    public int maxDepth(TreeNode<Integer> root) {
        if(root == null) return 0;

        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }
}
