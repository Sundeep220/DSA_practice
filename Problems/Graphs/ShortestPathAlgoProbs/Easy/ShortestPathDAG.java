package Problems.Graphs.ShortestPathAlgoProbs.Easy;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ShortestPathDAG {
    // Problem: https://www.geeksforgeeks.org/problems/shortest-path-in-undirected-graph/1
    // Time Complexity: O(V + E)
    // Space Complexity: O(V)

    //Algorithm (High Level)
        //Topologically sort the DAG
        //Initialize dist[]:
            //dist[src] = 0
            //Others = ∞
        //Traverse nodes in topological order
        //For each edge u → v (weight w):

    // Why traversing nodes in Topological Order?
    //If we process nodes in topological order, then:
        //When we reach a node u
        //All paths that can reach u are already processed
        //So dist[u] is already final

    class Pair {
        int v, wt;
        Pair(int v, int wt) {
            this.v = v;
            this.wt = wt;
        }
    }


    public static int[] shortestPath(
            int V,
            List<List<Pair>> adj,
            int src
    ) {
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        // 1. Topological Sort (DFS)
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                topoDFS(i, adj, visited, stack);
            }
        }

        // 2. Initialize distances
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // 3. Relax edges in topo order
        while (!stack.isEmpty()) {
            int u = stack.pop();

            if (dist[u] != Integer.MAX_VALUE) {
                for (Pair p : adj.get(u)) {
                    int v = p.v;
                    int wt = p.wt;
                    if (dist[v] > dist[u] + wt) {
                        dist[v] = dist[u] + wt;
                    }
                }
            }
        }

        return dist;
    }

    private static void topoDFS(
            int node,
            List<List<Pair>> adj,
            boolean[] visited,
            Stack<Integer> stack
    ) {
        visited[node] = true;

        for (Pair p : adj.get(node)) {
            if (!visited[p.v]) {
                topoDFS(p.v, adj, visited, stack);
            }
        }

        stack.push(node);
    }
}
