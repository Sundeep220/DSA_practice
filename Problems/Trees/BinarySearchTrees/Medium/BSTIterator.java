package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.Stack;
class BSTIterator {
    // Problem: https://leetcode.com/problems/binary-search-tree-iterator/
    private Stack<TreeNode> nodes;

    public BSTIterator(TreeNode root) {
        nodes = new Stack<>();
        pushAll(root); // ✅ initialize iterator
    }

    public int next() {
        TreeNode curr = nodes.pop();
        pushAll(curr.right); // ✅ process right subtree
        return (int) curr.data;
    }

    public boolean hasNext() {
        return !nodes.isEmpty();
    }

    private void pushAll(TreeNode root) {
        while (root != null) {
            nodes.push(root);
            root = root.left;
        }
    }
}
