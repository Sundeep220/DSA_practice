package Problems.Trees.BinaryTress.Medium;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class ChildrenSumProperty {
    // Problem: https://www.naukri.com/code360/problems/childrensumproperty_790723

    // Given the tree, convert it to follow Children Sum Property.
    // You can only increase the value of a node, but not decrease it.
    // The property should be valid after conversion.
    // Dont change the structure of the tree.
    // Time Complexity: O(n) Space Complexity: O(n)
    public static void changeTree(TreeNode<Integer> root) {
        if (root == null) return;

        // Step 1: Push root value down if needed
        int childSum = 0;
        if (root.left != null) childSum += root.left.data;
        if (root.right != null) childSum += root.right.data;

        if (childSum < root.data) {
            if (root.left != null) root.left.data = root.data;
            if (root.right != null) root.right.data = root.data;
        }

        // Step 2: Recurse on children
        changeTree(root.left);
        changeTree(root.right);

        // Step 3: Update current node value (postorder adjustment)
        int total = 0;
        if (root.left != null) total += root.left.data;
        if (root.right != null) total += root.right.data;

        if (root.left != null || root.right != null)
            root.data = total;
    }
}
