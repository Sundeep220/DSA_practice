package Problems.Trees.BinaryTress.Hard;

import java.util.*;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;


public class BottomViewOfBinaryTree {
    // Problem: https://www.naukri.com/code360/problems/bottom-view-of-binary-tree_893110?leftPanelTabValue=SUBMISSION
        static class Pair {
            TreeNode<Integer> node;
            int col;

            Pair(int col, TreeNode<Integer> node) {
                this.node = node;
                this.col = col;
            }
        }

        public static List<Integer> bottomView(TreeNode<Integer> root) {
            if (root == null) return new ArrayList<>();

            // Column -> Node value
            TreeMap<Integer, Integer> map = new TreeMap<>();
            Queue<Pair> q = new LinkedList<>();
            q.offer(new Pair(0, root));

            while (!q.isEmpty()) {
                Pair p = q.poll();
                TreeNode<Integer> curr = p.node;
                int line = p.col;

                // For Bottom View: overwrite with the latest node at this column
                map.put(line, curr.data);

                if (curr.left != null)
                    q.offer(new Pair(line - 1, curr.left));

                if (curr.right != null)
                    q.offer(new Pair(line + 1, curr.right));
            }

            // Collect values in order of columns
            return new ArrayList<>(map.values());
        }
}
