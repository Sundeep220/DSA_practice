package Problems.Trees.BinaryTress.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class BoundaryTraversal {
    // Problem: https://leetcode.com/problems/boundary-of-binary-tree/
    //

    public List<Integer> boundaryOfBinaryTree(TreeNode<Integer> root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) return boundary;

        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        // 1. Left boundary (excluding leaves)
        addLeftBoundary(root.left, boundary);

        // 2. Leaves
        addLeaves(root, boundary);

        // 3. Right boundary (excluding leaves) in bottom-up order
        addRightBoundary(root.right, boundary);

        return boundary;
    }

    private boolean isLeaf(TreeNode<Integer> node) {
        return node != null && node.left == null && node.right == null;
    }

    private void addLeftBoundary(TreeNode<Integer> node, List<Integer> res) {
        TreeNode<Integer> curr = node;
        while (curr != null) {
            if (!isLeaf(curr)) {
                res.add(curr.data);
            }
            // go left if possible, else go right
            if (curr.left != null) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
    }

    private void addLeaves(TreeNode<Integer> node, List<Integer> res) {
        if (node == null) return;
        if (isLeaf(node)) {
            res.add(node.data);
        } else {
            addLeaves(node.left, res);
            addLeaves(node.right, res);
        }
    }

    private void addRightBoundary(TreeNode<Integer> node, List<Integer> res) {
        Stack<Integer> stack = new Stack<>();
        TreeNode<Integer> curr = node;
        while (curr != null) {
            if (!isLeaf(curr)) {
                stack.push(curr.data);
            }
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        // now pop stack to add in bottom-up order
        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }
    }
}
