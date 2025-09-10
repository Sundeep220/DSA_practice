package Problems.Trees.BinaryTress.Easy;
import java.util.*;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class IterativeInOrder {
    // Problem: https://leetcode.com/problems/binary-tree-inorder-traversal/

    public List<Integer> inOrder(TreeNode<Integer> root){
        List<Integer> nodes = new ArrayList<>();

        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> curr = root;

        while(curr != null || !stack.isEmpty()){

            // go to left most node
            while(curr != null){
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            nodes.add(curr.data);
            curr = curr.right;
        }

        return nodes;
    }
}
