package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class MergeTwoBSTs {


    // Naive Solution: Insert all nodes of one tree into the other tree and then balance the tree
    // Time Complexity: O(n^2) Space Complexity: O(h)
    public TreeNode mergeBSTs(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        insertAll(root1, root2);
        return root2;
    }

    private void insertAll(TreeNode root, TreeNode target) {
        if (root == null) return;

        insert(target, (Integer) root.data);
        insertAll(root.left, target);
        insertAll(root.right, target);
    }

    private TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        if (val < (Integer) root.data)
            root.left = insert(root.left, val);
        else
            root.right = insert(root.right, val);

        return root;
    }


    // Optimal Solution: Using BST Property of InOrder Traversal
    // Time Complexity: O(n + m) Space Complexity: O(n + m )
    public TreeNode mergeBSTsOptimal(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        inorder(root1, list1);
        inorder(root2, list2);

        List<Integer> merged = merge(list1, list2);
        return buildBST(merged, 0, merged.size() - 1);
    }

    private TreeNode buildBST(List<Integer> list, int l, int r) {
        if (l > r) return null;

        int mid = (l + r) / 2;
        TreeNode root = new TreeNode(list.get(mid));

        root.left = buildBST(list, l, mid - 1);
        root.right = buildBST(list, mid + 1, r);

        return root;
    }

    private void inorder(TreeNode root, List<Integer> list) {
        if (root == null) return;
        inorder(root.left, list);
        list.add((Integer) root.data);
        inorder(root.right, list);
    }

    private List<Integer> merge(List<Integer> a, List<Integer> b) {
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;

        while (i < a.size() && j < b.size()) {
            if (a.get(i) < b.get(j))
                res.add(a.get(i++));
            else
                res.add(b.get(j++));
        }

        while (i < a.size()) res.add(a.get(i++));
        while (j < b.size()) res.add(b.get(j++));

        return res;
    }

}
