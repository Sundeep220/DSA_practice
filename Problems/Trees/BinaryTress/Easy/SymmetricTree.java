package Problems.Trees.BinaryTress.Easy;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class SymmetricTree {
    // Problem: https://leetcode.com/problems/symmetric-tree/

    public boolean isSymmetric(TreeNode<Integer> root) {
        if (root == null) return true;
        return traversal(root.left, root.right);
    }

    public boolean traversal(TreeNode<Integer> l, TreeNode<Integer> r){
        // both null → symmetric
        if (l == null && r == null) return true;

        // only one null → not symmetric
        if (l == null || r == null) return false;

        // values differ → not symmetric
        if (!Objects.equals(l.data, r.data)) return false;

        // check mirror condition:
        // left.left ↔ right.right AND left.right ↔ right.left
        return traversal(l.left, r.right) && traversal(l.right, r.left);
    }

    // Iterative Solution: Level Order Traversal
    public boolean isSymmetricIterative(TreeNode<Integer> root) {
        if (root == null) return true;

        Queue<TreeNode<Integer>> q = new LinkedList<>();
        // push both children of root
        q.offer(root.left);
        q.offer(root.right);

        while (!q.isEmpty()) {
            TreeNode<Integer> left = q.poll();
            TreeNode<Integer> right = q.poll();

            // both null → symmetric at this point, continue
            if (left == null && right == null) continue;

            // only one is null → not symmetric
            if (left == null || right == null) return false;

            // values differ → not symmetric
            if (left.data != right.data) return false;

            // enqueue children in mirror order
            q.offer(left.left);
            q.offer(right.right);
            
            q.offer(left.right);
            q.offer(right.left);
        }

        return true;
    }
}
