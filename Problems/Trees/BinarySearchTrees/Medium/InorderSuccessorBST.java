package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.*;
public class InorderSuccessorBST {
    // Problem: Problem to find the inorder successor
    //Problem Statement: Given a Binary Search Tree and a ‘key’ value which represents the data
    // of a node in this tree. Return the inorder predecessor and successor of the given node in the BST.
    // Note:
    //1. The predecessor of a node in BST is that node that will be visited just before the given node in the
    // inorder traversal of the tree. Return nullptr if the given node is the one that is visited first in the inorder traversal.
    //2. The successor of a node in BST is that node that will be visited immediately after the given node in
    // the inorder traversal of the tree. Return nullptr if the given node is visited last in the inorder traversal.

    public TreeNode inorderSuccessor(TreeNode root, TreeNode x) {
        List<TreeNode> inorderList = new ArrayList<>();
        inorderTraversal(root, inorderList);

        int index = binarySearch(inorderList, (Integer) x.data);
        if (index == -1 || index == inorderList.size() - 1)
            return null;

        return inorderList.get(index + 1);
    }

    private void inorderTraversal(TreeNode root, List<TreeNode> list) {
        if (root == null) return;

        inorderTraversal(root.left, list);
        list.add(root);
        inorderTraversal(root.right, list);
    }

    private int binarySearch(List<TreeNode> list, int target) {
        int low = 0, high = list.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int val = (int) list.get(mid).data;

            if (val == target)
                return mid;
            else if (val < target)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return -1;
    }

    // Optimal Solution: Using BST Property
    public TreeNode inorderSuccessorOptimal(TreeNode root, TreeNode x) {
        TreeNode successor = null;

        while (root != null) {
            if ((Integer) x.data < (Integer) root.data) {
                successor = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return successor;
    }



}
