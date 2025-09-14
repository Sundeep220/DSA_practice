package Problems.Trees.BinaryTress.Hard;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.*;

public class VerticalOrderTraversal {
    // Problem: https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/


    // Simpler version with level order traversal
    static class Tuple{
        TreeNode<Integer> node;
        int row;
        int col;

        Tuple(TreeNode<Integer> node, int row, int col){
            this.node = node;
            this.row = row;
            this.col = col;
        }
    }
    public List<List<Integer>> verticalTraversal(TreeNode<Integer> root) {
        // col -> (row -> minHeap of values)
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();
        Queue<Tuple> nodes = new LinkedList<>();
        nodes.add(new Tuple(root, 0, 0));

        while (!nodes.isEmpty()) {
            Tuple t = nodes.poll();
            TreeNode<Integer> curr = t.node;
            int col = t.row;  // x-axis
            int row = t.col;  // y-axis

            // Ensure column exists
            map.putIfAbsent(col, new TreeMap<>());

            // Ensure row exists inside column
            map.get(col).putIfAbsent(row, new PriorityQueue<>());

            // Add current value
            map.get(col).get(row).offer(curr.data);

            // Left child
            if (curr.left != null) {
                nodes.offer(new Tuple(curr.left, col - 1, row + 1));
            }
            // Right child
            if (curr.right != null) {
                nodes.offer(new Tuple(curr.right, col + 1, row + 1));
            }
        }

        // Build result
        List<List<Integer>> res = new ArrayList<>();
        for (TreeMap<Integer, PriorityQueue<Integer>> rows : map.values()) {
            List<Integer> colList = new ArrayList<>();
            for (PriorityQueue<Integer> pq : rows.values()) {
                while (!pq.isEmpty()) {
                    colList.add(pq.poll());
                }
            }
            res.add(colList);
        }

        return res;
    }

    // Little Faster using DFS
    static class NodeInfo {
        int col, row, val;
        NodeInfo(int col, int row, int val) {
            this.col = col;
            this.row = row;
            this.val = val;
        }
    }

    public List<List<Integer>> verticalTraversalII(TreeNode<Integer> root) {
        List<NodeInfo> nodes = new ArrayList<>();
        dfs(root, 0, 0, nodes);

        // Sort by col, then row, then value
        nodes.sort((a, b) -> {
            if (a.col != b.col) return a.col - b.col;
            if (a.row != b.row) return a.row - b.row;
            return a.val - b.val;
        });

        // Final result: list of lists (each inner list = one column)
        List<List<Integer>> result = new ArrayList<>();

        // Track the last column we processed
        int prevCol = Integer.MIN_VALUE;

        // Temporary list to store all values for the current column
        List<Integer> colList = new ArrayList<>();

        // Loop through all nodes (already sorted by col, then row, then val)
        for (NodeInfo n : nodes) {

            // If we encounter a new column
            if (n.col != prevCol) {
                // Save the previous column's list into result (if not empty)
                if (!colList.isEmpty()) result.add(colList);

                // Start a fresh list for this new column
                colList = new ArrayList<>();

                // Update tracker to current column
                prevCol = n.col;
            }

            // Add the node's value into the current column's list
            colList.add(n.val);
        }

        // After the loop, we still have one column list left → add it
        result.add(colList);

        return result;
    }

    private void dfs(TreeNode<Integer> node, int col, int row, List<NodeInfo> nodes) {
        if (node == null) return;
        nodes.add(new NodeInfo(col, row, node.data));
        dfs(node.left, col - 1, row + 1, nodes);
        dfs(node.right, col + 1, row + 1, nodes);
    }
}
