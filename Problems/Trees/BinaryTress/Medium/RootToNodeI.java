package Problems.Trees.BinaryTress.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.*;


public class RootToNodeI {
    // Problem: Given a binary tree with distinct nodes(no two nodes have the same data values).
    // The problem is to print the path from root to a given node x.
    // If node x is not present then return empty list

    public static List<Integer> findPath(TreeNode<Integer> root, int x) {
        List<Integer> path = new ArrayList<>();
        if (dfs(root, x, path)) {
            return path;
        }
        return new ArrayList<>(); // empty if not found
    }

    private static boolean dfs(TreeNode<Integer> node, int x, List<Integer> path) {
        if (node == null) return false;

        // Add current node to path
        path.add(node.data);

        // Check if current node is the target
        if (node.data == x) return true;

        // Search in left or right
        if (dfs(node.left, x, path) || dfs(node.right, x, path)) {
            return true;
        }

        // Backtrack if not found
        path.removeLast();
        return false;
    }
}
