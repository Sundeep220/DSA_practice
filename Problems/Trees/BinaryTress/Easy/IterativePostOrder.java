package Problems.Trees.BinaryTress.Easy;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.*;
public class IterativePostOrder {
    // Problem: https://leetcode.com/problems/binary-tree-postorder-traversal/


    // Using Two Stacks
    public List<Integer> postorderTraversal(TreeNode<Integer> root) {
        List<Integer> res = new ArrayList<Integer>();
        if(root == null) return res;
        Stack<TreeNode<Integer>> stack1 = new Stack<>();
        Stack<TreeNode<Integer>> stack2 = new Stack<>();

        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode<Integer> curr = stack1.pop();
            stack2.push(curr);

            if (curr.left != null) stack1.push(curr.left);
            if (curr.right != null) stack1.push(curr.right);
        }

        while (!stack2.isEmpty()) {
            res.add(stack2.pop().data);
        }
        return res;
    }

    // Using One Stack
    public List<Integer> postOrderIterativeOneStack(TreeNode<Integer> root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> curr = root, prev = null;

        while (curr != null || !stack.isEmpty()) {
            if(curr != null){
                stack.push(curr);
                curr = curr.left;
            }else{
                curr = stack.peek();
                if(curr.right == null || curr.right == prev){
                    curr = stack.pop();
                    res.add(curr.data);
                    prev = curr;
                    curr = null;
                }else{
                    curr = curr.right;
                }
            }
        }
        return res;
    }



}
