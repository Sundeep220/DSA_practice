package Problems.Trees.BinarySearchTrees.Easy;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation;

public class FloorInBST {
    // Time Complexity: O(logN)
    public int floorInBST(binaryTreeImplementation.TreeNode<Integer> root, int val) {
        int floor = -1;
        while (root != null) {
            if (root.data == val) {
                return root.data;
            }
            else if (val > root.data) {
                floor = root.data;     // possible floor
                root = root.right;     // try to find bigger one
            }
            else {
                root = root.left;      // go to smaller values
            }
        }
        return floor;
    }

}
