package Problems.Graphs.BFSDFSProbs.Medium;

import java.util.List;

public class CycleDetectionInDirectedGraph {

    // In DFS of a directed graph:
        //visited[node] → node has been visited at least once
        //pathVisited[node] → node is currently in DFS call stack
    //👉 If you reach a node that is already pathVisited → cycle detected

    public boolean isCyclic(int V, List<List<Integer>> adj) {
        boolean[] visited = new boolean[V];
        boolean[] pathVisited = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (dfs(i, adj, visited, pathVisited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(int node, List<List<Integer>> adj,
                        boolean[] visited, boolean[] pathVisited) {

        visited[node] = true;
        pathVisited[node] = true;

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                if (dfs(neighbor, adj, visited, pathVisited)) {
                    return true;
                }
            } else if (pathVisited[neighbor]) {
                return true; // 🔁 cycle
            }
        }

        pathVisited[node] = false; // backtrack
        return false;
    }

}
