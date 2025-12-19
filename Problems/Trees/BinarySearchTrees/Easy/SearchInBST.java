package Problems.Trees.BinarySearchTrees.Easy;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
public class SearchInBST {

    // Recursive Solution
    // Time Complexity: O(logN)
    public TreeNode<Integer> searchBST(TreeNode<Integer> root, int val) {
        if (root == null) return null;

        if (val == root.data) return root;
        else if (val < root.data) return searchBST(root.left, val);
        else return searchBST(root.right, val);
    }

    // Iterative Solution
    // Time Complexity: O(logN)
    public TreeNode<Integer> searchBSTIterative(TreeNode<Integer> root, int val) {
        while (root != null) {
            if (val == root.data) return root;
            else if (val < root.data) root = root.left;
            else root = root.right;
        }
        return null;
    }
}
