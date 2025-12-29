package Problems.Graphs.Medium;

import java.util.List;

public class DetectCycleDFS {
        // Intuition is Same as used in BFS Solution
        //Time Complexity: O(V + E)
            //Each vertex visited once
            //Each edge checked once
        //Space Complexity: O(V)
            //visited[] array
            //Recursion stack (worst case)
        public boolean isCycle(int V, List<List<Integer>> adj) {
            boolean[] visited = new boolean[V];

            // Handle disconnected components
            for (int i = 0; i < V; i++) {
                if (!visited[i]) {
                    if (dfs(i, -1, visited, adj)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean dfs(int node, int parent, boolean[] visited, List<List<Integer>> adj) {
            visited[node] = true;

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    if (dfs(neighbor, node, visited, adj)) {
                        return true;
                    }
                }
                // visited and not parent → cycle
                else if (neighbor != parent) {
                    return true;
                }
            }
            return false;
        }

}
