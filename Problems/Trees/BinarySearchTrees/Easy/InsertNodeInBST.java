package Problems.Trees.BinarySearchTrees.Easy;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation;

public class InsertNodeInBST {
    // Time Complexity: O(logN)

    public binaryTreeImplementation.TreeNode<Integer> insertNodeInBST(binaryTreeImplementation.TreeNode<Integer> root, int val) {
        if(root == null) return new binaryTreeImplementation.TreeNode<>(val);
        binaryTreeImplementation.TreeNode<Integer> node = root;
        while(true){
            if(val < node.data){
                if(node.left == null) {
                    node.left = new binaryTreeImplementation.TreeNode<>(val);
                    return root;
                }
                node = node.left;
            }
            else {
                if(node.right == null) {
                    node.right = new binaryTreeImplementation.TreeNode<>(val);
                    return root;
                }
                node = node.right;
            }
        }
    }
}
