package Problems.Trees.BinarySearchTrees.Easy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

public class TwoSumBst {
    // Problem: http://leetcode.com/problems/two-sum-iv-input-is-a-bst/

    // Naive Solution: Using InOrder Traversal
    // Time Complexity: O(n^2) Space Complexity: O(n)
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        inorder(root, list);

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) + list.get(j) == k)
                    return true;
            }
        }
        return false;
    }

    private void inorder(TreeNode root, List<Integer> list) {
        if (root == null) return;
        inorder(root.left, list);
        list.add((Integer) root.data);
        inorder(root.right, list);
    }

    // Better Solution: Using HashSet
    // Time Complexity: O(n) Space Complexity: O(n)
    public boolean findTargetBetterII(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return dfs(root, k, set);
    }

    private boolean dfs(TreeNode root, int k, Set<Integer> set) {
        if (root == null) return false;

        if (set.contains(k - (int) root.data))
            return true;

        set.add((int) root.data);

        return dfs(root.left, k, set) || dfs(root.right, k, set);
    }

    // Better Solution: Using Two Pointers
    // Time Complexity: O(n) Space Complexity: O(n)
    public boolean findTargetBetterIII(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        inorder(root, list);

        int i = 0, j = list.size() - 1;
        while (i < j) {
            int sum = list.get(i) + list.get(j);
            if (sum == k) return true;
            else if (sum < k) i++;
            else j--;
        }
        return false;
    }


    // Optimal Solution: Using BST Iterator
    // Time Complexity: O(n) Space Complexity: O(h)
    class BSTIterator {
        Stack<TreeNode> stack = new Stack<>();
        boolean reverse;

        BSTIterator(TreeNode root, boolean reverse) {
            this.reverse = reverse;
            pushAll(root);
        }

        private void pushAll(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = reverse ? node.right : node.left;
            }
        }

        int next() {
            TreeNode node = stack.pop();
            if (!reverse) pushAll(node.right);
            else pushAll(node.left);
            return (int) node.data;
        }
    }


    public boolean findTargetOptimal(TreeNode root, int k) {
        if (root == null) return false;

        BSTIterator left = new BSTIterator(root, false);
        BSTIterator right = new BSTIterator(root, true);

        int i = left.next();
        int j = right.next();

        while (i < j) {
            int sum = i + j;
            if (sum == k) return true;
            else if (sum < k) i = left.next();
            else j = right.next();
        }
        return false;
    }

}
