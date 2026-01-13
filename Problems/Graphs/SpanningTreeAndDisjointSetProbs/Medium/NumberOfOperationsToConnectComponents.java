package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Medium;

import java.util.ArrayList;
import java.util.List;

public class NumberOfOperationsToConnectComponents {
    // Problem: https://leetcode.com/problems/number-of-operations-to-make-network-connected/

    // Brute Force: Using DFS or BFS Traversal

    // Time Complexity: O(V + E)
    // Space Complexity: O(V + E)
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) return -1;

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] e : connections) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }

        boolean[] visited = new boolean[n];
        int components = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, adj, visited);
                components++;
            }
        }

        return components - 1;
    }

    void dfs(int node, List<List<Integer>> adj, boolean[] visited) {
        visited[node] = true;
        for (int nb : adj.get(node)) {
            if (!visited[nb]) dfs(nb, adj, visited);
        }
    }

    // Optimal Solution: Using Disjoint Set
    class DisjointSet {
        int[] parent, rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return;

            if (rank[px] < rank[py]) parent[px] = py;
            else if (rank[px] > rank[py]) parent[py] = px;
            else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }

    public int makeConnectedOptimal(int n, int[][] connections) {
        // Step 1: Not enough cables
        if (connections.length < n - 1) return -1;

        DisjointSet ds = new DisjointSet(n);

        int extraEdges = 0;
        // Step 2: Union connections
        for (int[] c : connections) {
            if(ds.find(c[0]) == ds.find(c[1]))
                extraEdges++;
            ds.union(c[0], c[1]);
        }

        // Step 3: Count components
        int components = 0;
        for (int i = 0; i < n; i++) {
            if (ds.find(i) == i)
                components++;
        }

        if(extraEdges < components) return -1;
        // Step 4: Operations needed
        return components - 1;
    }

}
