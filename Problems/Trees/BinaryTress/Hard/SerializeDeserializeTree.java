package Problems.Trees.BinaryTress.Hard;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.LinkedList;
import java.util.Queue;


public class SerializeDeserializeTree {
    // Problem: https://leetcode.com/problems/serialize-and-deserialize-binary-tree/

    public String serialize(TreeNode<Integer> root) {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<Integer> node = queue.poll();
            if (node == null) {
                sb.append("#,");  // use '#' to denote null
                continue;
            }

            sb.append(node.data).append(",");
            queue.offer(node.left);
            queue.offer(node.right);
        }

        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode<Integer> deserialize(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] values = data.split(",");
        TreeNode<Integer> root = new TreeNode<>(Integer.parseInt(values[0]));
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1; // pointer in the array
        while (!queue.isEmpty() && i < values.length) {
            TreeNode<Integer> current = queue.poll();

            // Left child
            if (!values[i].equals("#")) {
                TreeNode<Integer> left = new TreeNode<>(Integer.parseInt(values[i]));
                current.left = left;
                queue.offer(left);
            }
            i++;

            // Right child
            if (i < values.length && !values[i].equals("#")) {
                TreeNode<Integer> right = new TreeNode<>(Integer.parseInt(values[i]));
                current.right = right;
                queue.offer(right);
            }
            i++;
        }

        return root;
    }
}
