package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Medium;

import java.util.List;

public class KruskalsAlgorithm {

    // Algorithm steps"
    // 1. Sort all edges in non-decreasing order of their weight.
    // 2. Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far. If cycle is not formed, include this edge. Else, discard it.
    // 3. Repeat step 2 until there are (V-1) edges in the spanning tree.

    // Time Complexity
    //| Step           | Complexity           |
    //| -------------- | -------------------- |
    //| Sorting edges  | **O(E log E)**       |
    //| DSU operations | **O(E α(V)) ≈ O(E)** |
    //| **Total**      | **O(E log E)**       |

    // Space Complexity
    //| Component            | Space        |
    //| -------------------- | ------------ |
    //| Edge list            | O(E)         |
    //| Parent & Rank arrays | O(V)         |
    //| **Total**            | **O(V + E)** |
    static class DisjointSet {
        int[] parent, rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int findUPar(int node) {
            if (node == parent[node])
                return node;
            return parent[node] = findUPar(parent[node]);
        }

        void unionByRank(int u, int v) {
            int pu = findUPar(u);
            int pv = findUPar(v);

            if (pu == pv) return;

            if (rank[pu] < rank[pv]) {
                parent[pu] = pv;
            } else if (rank[pv] < rank[pu]) {
                parent[pv] = pu;
            } else {
                parent[pv] = pu;
                rank[pu]++;
            }
        }
    }

    static class Edge {
        int u, v, wt;
        Edge(int u, int v, int wt) {
            this.u = u;
            this.v = v;
            this.wt = wt;
        }
    }

    public static int kruskalMST(int V, List<Edge> edges) {

        // 1. Sort edges by weight
        edges.sort((a, b) -> a.wt - b.wt);

        DisjointSet ds = new DisjointSet(V);
        int mstWt = 0;

        // 2. Process edges
        for (Edge e : edges) {
            int u = e.u;
            int v = e.v;
            int wt = e.wt;

            if (ds.findUPar(u) != ds.findUPar(v)) {
                mstWt += wt;
                ds.unionByRank(u, v);
            }
        }
        return mstWt;
    }

}
