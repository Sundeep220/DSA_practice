package Problems.Graphs.BFSDFSProbs.Hard;

import java.util.ArrayList;
import java.util.List;

public class ArticulationPoints {
    // Problem: https://www.geeksforgeeks.org/problems/articulation-point-1/1
    /*
 =========================================================
 ARTICULATION POINT IN UNDIRECTED GRAPH (TARJAN'S ALGORITHM)
 =========================================================

 INTUITION:
 ----------
 An articulation point (cut vertex) is a node whose removal
 increases the number of connected components in the graph.

 Instead of removing each node and re-checking connectivity
 (which would be very slow), we use DFS + low-link values
 to detect such nodes in ONE traversal.

 The key idea is to understand whether a DFS subtree can
 reach any ancestor of the current node using back edges.

 ---------------------------------------------------------
 DFS CONCEPTS USED:
 ---------------------------------------------------------
 tin[u]  -> discovery time of node u in DFS
 low[u]  -> lowest discovery time reachable from u
            (using tree edges + back edges)

 ---------------------------------------------------------
 HOW low[] HELPS:
 ---------------------------------------------------------
 low[u] tells us how far "up" the DFS tree we can go from u.
 If a child subtree cannot reach above the current node,
 then the current node becomes a single point of failure.

 ---------------------------------------------------------
 ARTICULATION POINT CONDITIONS:
 ---------------------------------------------------------

 CASE 1: ROOT NODE
 ----------------
 If the DFS root has MORE THAN ONE child,
 then removing the root disconnects the graph.

 Condition:
   parent == -1 && children > 1

 CASE 2: NON-ROOT NODE
 --------------------
 For a node u (not root), if there exists a child v such that:
   low[v] >= tin[u]

 Then v's subtree cannot reach any ancestor of u,
 so removing u disconnects that subtree.

 ---------------------------------------------------------
 GRAPH MAY BE DISCONNECTED:
 ---------------------------------------------------------
 We must run DFS from every unvisited node.

 ---------------------------------------------------------
 TIME & SPACE COMPLEXITY:
 ---------------------------------------------------------
 Time  : O(V + E)
 Space : O(V + E)

 =========================================================
*/

    // Wrapper to avoid global variables
    static class State {
        int timer = 0;
    }

    public List<Integer> articulationPoints(int V, List<List<Integer>> adj) {

        int[] tin = new int[V];          // discovery time
        int[] low = new int[V];          // low-link values
        boolean[] visited = new boolean[V];
        boolean[] isArticulation = new boolean[V];

        State state = new State();

        // Graph can be disconnected
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, -1, adj, tin, low, visited, isArticulation, state);
            }
        }

        // Collect result
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (isArticulation[i]) result.add(i);
        }

        return result.isEmpty() ? List.of(-1) : result;
    }

    private void dfs(
            int u,
            int parent,
            List<List<Integer>> adj,
            int[] tin,
            int[] low,
            boolean[] visited,
            boolean[] isArticulation,
            State state
    ) {

        visited[u] = true;
        tin[u] = low[u] = state.timer++;

        int children = 0; // count DFS children

        for (int v : adj.get(u)) {

            // Ignore edge to parent
            if (v == parent) continue;

            if (!visited[v]) {
                children++;

                dfs(v, u, adj, tin, low, visited, isArticulation, state);

                // Update low value after returning from child
                low[u] = Math.min(low[u], low[v]);

                // CASE 2: Non-root articulation condition
                if (parent != -1 && low[v] >= tin[u]) {
                    isArticulation[u] = true;
                }
            } else {
                // Back edge case
                low[u] = Math.min(low[u], tin[v]);
            }
        }

        // CASE 1: Root articulation condition
        if (parent == -1 && children > 1) {
            isArticulation[u] = true;
        }
    }


}
