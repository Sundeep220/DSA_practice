package Problems.Trees.BinaryTress.Medium;

import java.util.ArrayList;
import java.util.List;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class RootToNodeII {
    // Problem: Given the root of a binary tree. Return all the root-to-leaf paths in the binary tree.

    public static List<List<Integer>> findPath(TreeNode<Integer> root, Integer data) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        dfs(root, new ArrayList<>(), res);
        return res;
    }

    public static void dfs(TreeNode<Integer> node, List<Integer> path, List<List<Integer>> res) {
        if(node == null) return;

        path.add(node.data);

        // If its leaf node, then add the path to the result list
        if(node.left == null && node.right == null) {
            res.add(new ArrayList<>(path)); // deep copy
        } else {
            // If it's not a leaf node, then go left and right and update path accordingly
            dfs(node.left, path, res);
            dfs(node.right, path, res);
        }

        // Backtrack
        path.removeLast();
    }
}
