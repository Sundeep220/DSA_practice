package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConstructBSt {
    // Problem: https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/

    // Naive Solution: Building tree by inserting nodes one by one. Time Complexity: O(n^2) Space Complexity: O(n)
    public TreeNode bstFromPreorder(int[] preorder) {
        TreeNode root = null;
        for (int val : preorder) {
            root = insert(root, val);
        }
        return root;
    }

    private TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        if (val < (Integer) root.data)
            root.left = insert(root.left, val);
        else
            root.right = insert(root.right, val);

        return root;
    }

    // Naive Solution: Sorting PreOrder to get InOrder. Then using both to build tree
    // Time Complexity: O(nlogn) Space Complexity: O(n)
    public TreeNode bstFromPreorder2(int[] preorder) {
        int[] inorder = preorder.clone();
        Arrays.sort(inorder);

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return build(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1, map);
    }

    private TreeNode build(int[] preorder, int ps, int pe,
                           int[] inorder, int is, int ie,
                           Map<Integer, Integer> map) {

        if (ps > pe) return null;

        TreeNode root = new TreeNode(preorder[ps]);
        int inIndex = map.get(root.data);
        int leftSize = inIndex - is;

        root.left = build(preorder, ps + 1, ps + leftSize,
                inorder, is, inIndex - 1, map);

        root.right = build(preorder, ps + leftSize + 1, pe,
                inorder, inIndex + 1, ie, map);

        return root;
    }

    // Optimal Solution: Using upper bound
    // Time Complexity: O(n) Space Complexity: O(n)
    public TreeNode bstFromPreorderOptimal(int[] preorder) {
        int[] index = {0};  // index in preorder
        return build(preorder, index, Integer.MAX_VALUE);
    }

    private TreeNode build(int[] preorder, int[] index, int bound) {
        if (index[0] == preorder.length || preorder[index[0]] > bound)
            return null;

        TreeNode root = new TreeNode(preorder[index[0]++]);
        root.left = build(preorder, index, (Integer) root.data);
        root.right = build(preorder, index, bound);

        return root;
    }


}
