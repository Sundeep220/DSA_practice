package Problems.Trees.BinaryTress.Medium;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.ArrayList;
import java.util.List;


public class LowestCommonAncestor {
    // Problem: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

    // Brute Force:
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    // Using Root to Node Path concept

    public TreeNode<Integer> lowestCommonAncestor(TreeNode<Integer> root, TreeNode<Integer> p, TreeNode<Integer> q) {
        if(root == null) return null;
        List<TreeNode<Integer>>  path1 = new ArrayList<>();
        List<TreeNode<Integer>> path2 = new ArrayList<>();

        getPath(root, p, path1);
        getPath(root, q, path2);

        int i = 0, j = 0, n = Math.min(path1.size(), path2.size()); // n is the min size of the paths
        TreeNode<Integer> lca = null;
        while (i < n && j < n && path1.get(i) == path2.get(j)) { // compare the paths until they are not equal
            lca = path1.get(i);
            i++; j++; // move to the next nodes
        }

        return lca; // return the node that is common to both paths
    }

    private boolean getPath(TreeNode<Integer> root, TreeNode<Integer> target, List<TreeNode<Integer>> path) {
        if (root == null) return false;

        path.add(root);

        if (root == target) return true;

        if (getPath(root.left, target, path) || getPath(root.right, target, path)) {
            return true;
        }

        path.removeLast(); // backtrack
        return false;
    }


    // Optimal Solution:
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public TreeNode<Integer> lowestCommonAncestorOptimal(TreeNode<Integer> root, TreeNode<Integer> p, TreeNode<Integer> q) {
        if(root == null || root == p || root == q) return root;

        // Get the left and right nodes by searching them
        TreeNode<Integer> left = lowestCommonAncestorOptimal(root.left, p, q);
        TreeNode<Integer> right = lowestCommonAncestorOptimal(root.right, p, q);

        if(left == null) return right;
        if(right == null) return left;

        // Return the root if both left and right are not null, i.e., we found the LCA
        return root;
    }
}
