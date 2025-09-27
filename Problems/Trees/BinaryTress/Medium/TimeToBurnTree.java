package Problems.Trees.BinaryTress.Medium;
import java.util.*;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode<Integer>;
public class TimeToBurnTree {
    // Problem: https://leetcode.com/problems/time-to-burn-tree-to-forest/

    public static int minTime(TreeNode<Integer> root, int target) {
        Map<TreeNode<Integer>, TreeNode<Integer>> parents = new HashMap<>();
        Queue<TreeNode<Integer>> q = new LinkedList<>();

        // Step 1: Build parent map and locate target
        TreeNode<Integer> targetN = markParentsAndFindNode(root, target, parents);

        // Step 2: BFS from target
        Set<TreeNode<Integer>> visited = new HashSet<>();
        q.offer(targetN);
        visited.add(targetN);

        int minTime = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            boolean burned = false;

            for (int i = 0; i < size; i++) {
                TreeNode<Integer> curr = q.poll();

                // left
                if (curr.left != null && visited.add(curr.left)) {
                    burned = true;
                    q.offer(curr.left);
                }

                // right
                if (curr.right != null && visited.add(curr.right)) {
                    burned = true;
                    q.offer(curr.right);
                }

                // parent
                TreeNode<Integer> parent = parents.get(curr);
                if (parent != null && visited.add(parent)) {
                    burned = true;
                    q.offer(parent);
                }
            }

            if (burned) minTime++;
        }

        return minTime;
    }

    public static TreeNode<Integer> markParentsAndFindNode(TreeNode<Integer> root, int target, Map<TreeNode<Integer>, TreeNode<Integer>> parents) {
        if (root == null) return null;

        TreeNode<Integer> found = null;
        Queue<TreeNode<Integer>> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode<Integer> curr = q.poll();

            if (curr.data == target)
                found = curr;

            if (curr.left != null) {
                parents.put(curr.left, curr);
                q.offer(curr.left);
            }

            if (curr.right != null) {
                parents.put(curr.right, curr);
                q.offer(curr.right);
            }
        }

        return found;
    }
}
