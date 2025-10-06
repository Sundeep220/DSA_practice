package Problems.Trees.BinaryTress.Easy;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
public class CountNodesInCBT {
    // Problem: https://leetcode.com/problems/count-complete-tree-nodes/

    // Brute Force: Do any of traversal and count the number of nodes
    // TC: O(N)
    // SC: O(logN)

    public static int countNodes(TreeNode<Integer> root) {
        if(root == null) return 0;

        return 1 + countNodes(root.left) + countNodes(root.right);
    }


    // Optimal Solution: O(logN) time | O(1) space
    public static int countNodesOptimal(TreeNode<Integer> root) {
        if(root == null) return 0;

        int left = getLeftHeight(root);
        int right = getRightHeight(root);

        if(left == right){
            return (1 << left) - 1;
        }

        return 1 + countNodes(root.left) + countNodes(root.right);

    }

    public static int getLeftHeight(TreeNode node){
        int height = 0;
        while(node != null){
            height++;

            node = node.left;
        }

        return height;
    }


    public static int getRightHeight(TreeNode node){
        int height = 0;

        while(node != null){
            height++;

            node = node.right;
        }

        return height;

    }


    public static void main(String[] args) {
        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.left = new TreeNode<>(4);
        root.left.right = new TreeNode<>(5);
        root.right.left = new TreeNode<>(6);
        root.right.right = new TreeNode<>(7);
        root.left.left.left = new TreeNode<>(8);
        System.out.println("Count: " + countNodes(root));
    }
}
