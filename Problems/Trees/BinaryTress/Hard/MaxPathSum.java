package Problems.Trees.BinaryTress.Hard;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
public class MaxPathSum {
    // Problem: https://leetcode.com/problems/binary-tree-maximum-path-sum/

    // Using concept form diameter of binary tree
    public int maxPathSum(TreeNode<Integer> root) {
        int[] maxPath = new int[1];
        maxPath[0] = Integer.MIN_VALUE;
        dfs(root, maxPath);
        return maxPath[0];
    }

    private int dfs(TreeNode<Integer> node, int[] maxPath) {
        if (node == null) return 0;

        // compute max sum from left and right subtrees
        int left = Math.max(0, dfs(node.left, maxPath));
        int right = Math.max(0, dfs(node.right, maxPath));

        // update global max: path that passes through this node
        maxPath[0] = Math.max(maxPath[0], node.data + left + right);

        // return max single-branch path upwards
        return node.data + Math.max(left, right);
    }
}
