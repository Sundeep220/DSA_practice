package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.ArrayList;
import java.util.List;
public class LCAOfBST {

    // Problem Link: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/

    // Naive Solution: Find the paths of both nodes and find the first common node in both paths. This is a O(n) solution.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public TreeNode lowestCommonAncestorNaivePath(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathP = new ArrayList<>();
        List<TreeNode> pathQ = new ArrayList<>();

        findPath(root, p, pathP);
        findPath(root, q, pathQ);

        int i = 0;
        while (i < pathP.size() && i < pathQ.size() && pathP.get(i) == pathQ.get(i)) {
            i++;
        }
        return pathP.get(i - 1);
    }

    private boolean findPath(TreeNode root, TreeNode target, List<TreeNode> path) {
        if (root == null) return false;

        path.add(root);
        if (root == target) return true;

        if (findPath(root.left, target, path) || findPath(root.right, target, path))
            return true;

        path.removeLast();
        return false;
    }

    // Naive Solution: Treating BST as a Binary Tree. Find the LCA in a Binary Tree. This is a O(n) solution.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public TreeNode lowestCommonAncestorNaive(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestorNaive(root.left, p, q);
        TreeNode right = lowestCommonAncestorNaive(root.right, p, q);

        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    // Better Solution: Use BST Property Recursively
    // Time Complexity: O(h) h is the height of the tree
    // Space Complexity: O(h)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        if ((Integer) p.data < (Integer) root.data && (Integer) q.data < (Integer) root.data)
            return lowestCommonAncestor(root.left, p, q);

        if ((Integer) p.data > (Integer) root.data && (Integer) q.data > (Integer) root.data)
            return lowestCommonAncestor(root.right, p, q);

        return root; // split point
    }

    // Optimal Solution: Use BST Property Iteratively
    // Time Complexity: O(h) h is the height of the tree
    // Space Complexity: O(1)
    public TreeNode lowestCommonAncestorOptimal(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if ((Integer) p.data < (Integer) root.data && (Integer) q.data < (Integer) root.data)
                root = root.left;
            else if ((Integer) p.data > (Integer) root.data && (Integer) q.data > (Integer) root.data)
                root = root.right;
            else break;
        }
        return root;
    }
}
