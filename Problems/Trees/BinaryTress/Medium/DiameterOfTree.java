package Problems.Trees.BinaryTress.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
public class DiameterOfTree {
    // Problem: https://leetcode.com/problems/diameter-of-binary-tree/

    // Brute Force: Going through each node and calculating:
    // height(left) + height(right)
    // Time Complexity: O(n) time | O(n) space

    public int diameterOfBinaryTree(TreeNode<Integer> root) {
        if(root == null) return 0;

        // diameter through root
        int option1 = height(root.left) + height(root.right);

        // diameter in left subtree
        int option2 = diameterOfBinaryTree(root.left);

        // diameter in right subtree
        int option3 = diameterOfBinaryTree(root.right);

        return Math.max(option1, Math.max(option2, option3));
    }

    public int height(TreeNode<Integer> root) {
        if(root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }


    // Optimized: Updating diameter on the go
    private int diameter = 0;  // global tracker

    public int diameterOfBinaryTreeOptimized(TreeNode<Integer> root) {
        heightII(root);
        return diameter;
    }

    private int heightII(TreeNode<Integer> node) {
        if (node == null) return 0;

        int left = heightII(node.left);
        int right = heightII(node.right);

        // Update diameter at this node
        diameter = Math.max(diameter, left + right);

        return 1 + Math.max(left, right);
    }


    // Without using global variable
    public int diameterOfBinaryTreeOptimizedIII(TreeNode<Integer> root) {
        int[] diameter = new int[1]; // method variable to track max diameter
        heightIII(root, diameter);
        return diameter[0];
    }

    private int heightIII(TreeNode<Integer> node, int[] diameter) {
        if (node == null) return 0;

        int left = heightIII(node.left, diameter);
        int right = heightIII(node.right, diameter);

        // update diameter at this node
        diameter[0] = Math.max(diameter[0], left + right);

        return 1 + Math.max(left, right); // return height
    }
}
