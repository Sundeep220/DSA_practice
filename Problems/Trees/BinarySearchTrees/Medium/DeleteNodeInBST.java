package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.Objects;

public class DeleteNodeInBST {
    // Problem: leetcode 450 https://leetcode.com/problems/delete-node-in-a-bst/

    public TreeNode<Integer> deleteNode(TreeNode<Integer> root, int key) {
        if(root == null) return root;
        if(Objects.equals(root.data, key))
            return helper(root);
        TreeNode<Integer> curr = root;
        while(curr != null){
            if(curr.data > key){
                if(curr.left != null && curr.left.data == key){
                    curr.left = helper(curr.left);
                    break;
                }else{
                    curr = curr.left;
                }
            }else {
                if(curr.right != null && curr.right.data == key){
                    curr.right = helper(curr.right);
                    break;
                }else{
                    curr = curr.right;
                }
            }
        }

        return root;
    }

    public TreeNode<Integer> helper(TreeNode<Integer> root){
        if(root.left == null)
            return root.right;
        else if(root.right == null)
            return root.left;

        TreeNode<Integer> rightChild = root.right;
        TreeNode<Integer> rightMostLeftChild = findRightMostLeftChild(root.left);
        rightMostLeftChild.right = rightChild;
        return root.left;
    }

    public TreeNode<Integer> findRightMostLeftChild(TreeNode<Integer> root){
        if(root.right == null)
            return root;

        return findRightMostLeftChild(root.right);
    }
}
