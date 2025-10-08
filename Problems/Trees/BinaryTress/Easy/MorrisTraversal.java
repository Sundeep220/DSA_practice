package Problems.Trees.BinaryTress.Easy;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class MorrisTraversal {
    // Perform Inorder Traversal without stack or recursion
    public void morrisInorder(TreeNode root) {
        TreeNode current = root;

        while (current != null) {
            if (current.left == null) {
                // Case 1: No left child — visit and move right
                System.out.print(current.data + " ");
                current = current.right;
            } else {
                // Case 2: Has a left child — find inorder predecessor
                TreeNode predecessor = current.left;

                // Move to the rightmost node in left subtree
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Create temporary thread to return later
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Thread already exists → left subtree done
                    predecessor.right = null; // remove thread
                    System.out.print(current.data + " ");
                    current = current.right;
                }
            }
        }
    }

    public void morrisPreorder(TreeNode root) {
        TreeNode current = root;

        while (current != null) {
            if (current.left == null) {
                System.out.print(current.data + " ");
                current = current.right;
            } else {
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Print before creating thread (Preorder)
                    System.out.print(current.data + " ");
                    predecessor.right = current;
                    current = current.left;
                } else {
                    predecessor.right = null;
                    current = current.right;
                }
            }
        }
    }
}
