package Problems.Trees.BinaryTress.Medium;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PreInPostTraversalSingleIteration {
    // problem: Generate the Preorder, Inorder and Postorder traversal of a tree using a single iteration

    // Using Stack of pairs:
    //Each stack element is (node, state) where state tells us where we are in traversal for that node:
        //State 1 → Preorder (Root)
        //State 2 → Inorder (Left → Root)
        //State 3 → Postorder (Right → Root)

    static class Pair{
        TreeNode<Integer> node;
        int state;

        Pair(TreeNode<Integer> node, int state){
            this.node = node;
            this.state = state;
        }
    }

    List<Integer> preOrder = new ArrayList<>();
    List<Integer> inOrder = new ArrayList<>();
    List<Integer> postOrder = new ArrayList<>();

    public void preInPostTraversal(TreeNode<Integer> root) {
        Stack<Pair> stack = new Stack<>();

        stack.push(new Pair(root, 1));

        while(!stack.isEmpty()){
            Pair curr = stack.pop();

            if (curr.state == 1) {
                // Preorder
                preOrder.add(curr.node.data);
                curr.state++;
                stack.push(curr); // push back with next state
                if (curr.node.left != null) {
                    stack.push(new Pair(curr.node.left, 1));
                }
            } else if (curr.state == 2) {
                // Inorder
                inOrder.add(curr.node.data);
                curr.state++;
                stack.push(curr); // push back with next state
                if (curr.node.right != null) {
                    stack.push(new Pair(curr.node.right, 1));
                }
            } else {
                // Postorder
                postOrder.add(curr.node.data);
            }
        }
    }

}
