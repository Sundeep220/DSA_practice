package Problems.Trees.BinaryTress.Hard;

import java.util.*;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class LeftViewOfBinaryTree {
    public List<Integer> leftSideView(TreeNode<Integer> root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode<Integer>> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                TreeNode<Integer> curr = q.poll();

                // First node of this level → leftmost
                if (i == 0) {
                    res.add(curr.data);
                }

                if (curr.left != null) q.offer(curr.left);
                if (curr.right != null) q.offer(curr.right);
            }
        }

        return res;
    }

    public List<Integer> leftSideViewII(TreeNode<Integer> root) {
        List<Integer> res = new ArrayList<>();
        dfs(root, 0, res);
        return res;
    }

    private void dfs(TreeNode<Integer> node, int depth, List<Integer> res) {
        if (node == null) return;

        // If first time visiting this depth → add node
        if (depth == res.size()) {
            res.add(node.data);
        }

        // Go left first to ensure leftmost is taken
        dfs(node.left, depth + 1, res);
        dfs(node.right, depth + 1, res);
    }
}
