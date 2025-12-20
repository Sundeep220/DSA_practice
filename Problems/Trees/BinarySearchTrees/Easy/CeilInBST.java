package Problems.Trees.BinarySearchTrees.Easy;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation;

public class CeilInBST {
    // Time Complexity: O(logN)

    public int ceilInBST(binaryTreeImplementation.TreeNode<Integer> root, int val) {
        int ceil = -1;
        while (root != null) {
            if (root.data == val) {
                return root.data;
            }
            else if (val < root.data) {
                ceil = root.data;      // possible ceil
                root = root.left;      // try to find smaller one
            }
            else {
                root = root.right;     // go to bigger values
            }
        }
        return ceil;
    }
}
