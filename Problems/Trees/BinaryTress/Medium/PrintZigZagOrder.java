package Problems.Trees.BinaryTress.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.*;

public class PrintZigZagOrder {
    // Problem: https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
    // Time Complexity: O(n) Space Complexity: O(n)
    // Ex: Input: root = [3,9,20,null,null,15,7] Output: [[3],[20,9],[15,7]]

    public List<List<Integer>> zigzagLevelOrder(TreeNode<Integer> root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> temp = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                TreeNode<Integer> node = queue.poll();
                temp.add(node.data);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            if (!leftToRight) {
                Collections.reverse(temp); // reverse only when right-to-left
            }
            res.add(temp);
            leftToRight = !leftToRight; // flip direction for next level
        }

        return res;
    }
}
