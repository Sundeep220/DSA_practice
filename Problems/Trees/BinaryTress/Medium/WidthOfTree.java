package Problems.Trees.BinaryTress.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.*;

public class WidthOfTree {
    // Problem: https://leetcode.com/problems/maximum-width-of-binary-tree/

    static class Pair {
        TreeNode node;
        int idx;
        Pair(TreeNode node, int idx) {
            this.node = node;
            this.idx = idx;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(root, 0));
        int maxWidth = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            int minIdx = q.peek().idx; // normalize index
            int first = 0, last = 0;

            for (int i = 0; i < size; i++) {
                Pair p = q.poll();
                int currIdx = p.idx - minIdx; // avoid overflow
                if (i == 0) first = currIdx;
                if (i == size - 1) last = currIdx;

                if (p.node.left != null) {
                    q.offer(new Pair(p.node.left, currIdx * 2));
                }
                if (p.node.right != null) {
                    q.offer(new Pair(p.node.right, currIdx * 2 + 1));
                }
            }
            maxWidth = Math.max(maxWidth, last - first + 1);
        }
        return maxWidth;
    }

}
