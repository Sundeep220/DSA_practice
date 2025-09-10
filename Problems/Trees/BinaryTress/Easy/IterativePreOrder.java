package Problems.Trees.BinaryTress.Easy;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;


public class IterativePreOrder {
    // Problem: https://leetcode.com/problems/binary-tree-preorder-traversal/

    // Using Iterative Preorder Traversal
    public List<Integer> preOrderTraversal(TreeNode<Integer> root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;  // handle empty tree

        Stack<TreeNode<Integer>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<Integer> curr = stack.pop();
            res.add(curr.data);

            // Push right first so left is processed first
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
        }
        return res;
    }
}
