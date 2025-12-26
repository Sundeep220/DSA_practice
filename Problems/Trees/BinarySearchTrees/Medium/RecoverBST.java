package Problems.Trees.BinarySearchTrees.Medium;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class RecoverBST {
    // Problem: https://leetcode.com/problems/recover-binary-search-tree/

    // Naive Solution: Using InOrder Traversal
    // Time Complexity: O(nlogn) Space Complexity: O(n)
    public void recoverTreeNaive(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        inorder(root, nodes);

        List<Integer> dataues = new ArrayList<>();
        for (TreeNode n : nodes) dataues.add((Integer) n.data);

        Collections.sort(dataues);

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).data != dataues.get(i)) {
                nodes.get(i).data = dataues.get(i);
            }
        }
    }

    private void inorder(TreeNode root, List<TreeNode> list) {
        if (root == null) return;
        inorder(root.left, list);
        list.add(root);
        inorder(root.right, list);
    }

    // Better: Inorder Traversal + detecting violations
    // Time Complexity: O(n) Space Complexity: O(h)
    TreeNode first = null, second = null, prev = null;

    public void recoverTreeBetterI(TreeNode root) {
        inorder(root);
        int temp = (Integer) first.data;
        first.data = second.data;
        second.data = temp;
    }

    private void inorder(TreeNode root) {
        if (root == null) return;

        inorder(root.left);

        if (prev != null && (Integer) prev.data > (Integer) root.data) {
            if (first == null)
                first = prev;
            second = root;
        }
        prev = root;

        inorder(root.right);
    }

    // Better: Same approach in Iterative
    // Time Complexity: O(n) Space Complexity: O(h)
    public void recoverTreeBetterII(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode first = null, second = null, prev = null;

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();

            if (prev != null && (Integer) prev.data > (Integer) root.data) {
                if (first == null)
                    first = prev;
                second = root;
            }
            prev = root;

            root = root.right;
        }

        int temp = first.data;
        first.data = second.data;
        second.data = temp;
    }

    // Optimal Solution: Using Morris Traversal but same approach
    // Time Complexity: O(n) Space Complexity: O(1)
    public void recoverTree(TreeNode root) {
        TreeNode first = null, second = null, prev = null;
        TreeNode curr = root;

        while (curr != null) {
            if (curr.left == null) {
                // Visit node
                if (prev != null && (Integer) prev.data > (Integer) curr.data) {
                    if (first == null)
                        first = prev;
                    second = curr;
                }
                prev = curr;
                curr = curr.right;
            } else {
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr)
                    pred = pred.right;

                if (pred.right == null) {
                    pred.right = curr;
                    curr = curr.left;
                } else {
                    pred.right = null;

                    // Visit node
                    if (prev != null && (Integer) prev.data > (Integer) curr.data) {
                        if (first == null)
                            first = prev;
                        second = curr;
                    }
                    prev = curr;
                    curr = curr.right;
                }
            }
        }

        int temp = (Integer) first.data;
        first.data = second.data;
        second.data = temp;
    }

}
