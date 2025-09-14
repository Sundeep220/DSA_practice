package Problems.Trees.BinaryTress.Hard;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.*;


public class RightViewOfBinaryTree {
    // Problem: https://leetcode.com/problems/binary-tree-right-side-view/

    public List<Integer> rightSideView(TreeNode<Integer> root) {
        if (root == null) return new ArrayList<>();

        List<Integer> res = new ArrayList<>();
        Queue<TreeNode<Integer>> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            TreeNode<Integer> rightMost = null;

            for (int i = 0; i < size; i++) {
                TreeNode<Integer> curr = q.poll();
                rightMost = curr;  // overwrite until last in this level

                if (curr.left != null) q.offer(curr.left);
                if (curr.right != null) q.offer(curr.right);
            }

            res.add(rightMost.data); // last node in this level
        }

        return res;
    }


    // More elegant code: DFS
    public List<Integer> rightSideViewII(TreeNode<Integer> root) {
        List<Integer> res = new ArrayList<>();
        dfs(root, 0, res);
        return res;
    }

    private void dfs(TreeNode<Integer> node, int depth, List<Integer> res) {
        if (node == null) return;

        // If this is the first time visiting this depth, add the node
        if (depth == res.size()) {
            res.add(node.data);
        }

        // Visit right child first to ensure rightmost node is taken
        dfs(node.right, depth + 1, res);
        dfs(node.left, depth + 1, res);
    }
}
