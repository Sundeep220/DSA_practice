package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
public class ValidBST {
    // Problem: https://leetcode.com/problems/validate-binary-search-tree/

    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;

        if ((Long) node.data <= min || (Long) node.data >= max) return false;

        return validate(node.left, min, (Long) node.data) &&
                validate(node.right, (Long) node.data, max);
    }
}
