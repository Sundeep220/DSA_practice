package Problems.Graphs.BFSDFSProbs.Medium;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DetectCycleBFS {
    // Detecting cycle in graph using BFS
    // Intuition: When visiting nodes using BFS, if we find that an adjacent node
    // has already been visited and it's not the parent of the current node,
    // then we've found a cycle in the graph.

    // Time Complexity: O(V + E) Space Complexity: O(V)
    public boolean isCycle(int V, List<List<Integer>> adj) {
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (bfs(i, adj, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean bfs(int start, List<List<Integer>> adj, boolean[] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start, -1}); // {node, parent}
        visited[start] = true;


        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int node = curr[0];
            int parent = curr[1];

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(new int[]{neighbor, node});
                }
                // visited and not parent → cycle
                else if (neighbor != parent) {
                    return true;
                }
            }
        }
        return false;
    }
}
