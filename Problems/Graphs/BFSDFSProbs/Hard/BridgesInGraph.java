package Problems.Graphs.BFSDFSProbs.Hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BridgesInGraph {
    // Problem: https://leetcode.com/problems/critical-connections-in-a-network/description/

    /*
 CORE INTUITION (Critical Connections / Bridges)

 We are given an undirected, connected graph.
 A "critical connection" (bridge) is an edge which, if removed,
 disconnects the graph.

 KEY IDEA:
 During DFS, we track:
 1) tin[u]  -> time when node u is first visited
 2) low[u]  -> the minimum discovery time reachable from u
              (including back edges)

 WHAT low[] REPRESENTS:
 low[u] tells us how far "up" the DFS tree we can go from u
 without using the parent edge.

 BRIDGE CONDITION:
 For an edge (u, v), where v is a DFS child of u:
   If low[v] > tin[u]
   => v (and its subtree) cannot reach u or any ancestor of u
   => removing (u, v) disconnects the graph
   => (u, v) is a BRIDGE

 WHY THIS WORKS:
 - Back edges reduce low[] values
 - If no back edge exists from v's subtree to u or above,
   then that edge is the only connection

 APPROACH:
 1) Build adjacency list
 2) Run DFS from any node (graph is connected)
 3) Maintain tin[], low[], visited[]
 4) Apply bridge condition during DFS backtracking

 TIME COMPLEXITY: O(N + E)
 SPACE COMPLEXITY: O(N + E)
*/

    List<List<Integer>> bridges;
    List<List<Integer>> adj;
    int[] tin, low;
    boolean[] visited;
    int timer = 0;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        bridges = new ArrayList<>();
        adj = new ArrayList<>();

        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        // Build undirected graph
        for (List<Integer> edge : connections) {
            int u = edge.get(0);
            int v = edge.get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        tin = new int[n];
        low = new int[n];
        visited = new boolean[n];

        dfs(0, -1);  // start DFS from node 0
        return bridges;
    }

    private void dfs(int u, int parent) {
        visited[u] = true;
        tin[u] = low[u] = timer++;

        for (int v : adj.get(u)) {
            if (v == parent) continue;  // skip parent edge

            if (!visited[v]) {
                dfs(v, u);

                // update low value after visiting child
                low[u] = Math.min(low[u], low[v]);

                // bridge check
                if (low[v] > tin[u]) {
                    bridges.add(Arrays.asList(u, v));
                }
            } else {
                // back edge found
                low[u] = Math.min(low[u], tin[v]);
            }
        }
    }

}
