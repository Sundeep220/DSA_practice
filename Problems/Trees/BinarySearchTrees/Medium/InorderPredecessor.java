package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.*;
public class InorderPredecessor {
    // Same as InOrderSuccessor

    // Naive Solution:

    public TreeNode inorderPredecessor1(TreeNode root, TreeNode x) {
        List<TreeNode> list = new ArrayList<>();
        inorderTraversal(root, list);

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) == x)
                return list.get(i - 1);
        }
        return null;
    }

    private void inorderTraversal(TreeNode root, List<TreeNode> list) {
        if (root == null) return;

        inorderTraversal(root.left, list);
        list.add(root);
        inorderTraversal(root.right, list);
    }

    // Optimal: Using BST proptery

    public TreeNode inorderPredecessor2(TreeNode root, TreeNode x) {
        TreeNode predecessor = null;

        while (root != null) {
            if ((Integer) x.data > (Integer) root.data) {
                predecessor = root;
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return predecessor;
    }

}
