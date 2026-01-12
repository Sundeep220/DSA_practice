package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Medium;

public class DisjointSetByRank {

    // Time Complexity: O(α(N))
    // α(N) = Inverse Ackermann (≤ 4 for all practical N)
    static class DisjointSet{
        int[] parent;
        int[] rank;

        DisjointSet(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];

            for (int i = 0; i <= n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        // Path Compression: Finding Ultimate Parent and compressing path
        int findUPar(int node) {
            if (node == parent[node])
                return node;

            parent[node] = findUPar(parent[node]);
            return parent[node];
        }

        // Union by Rank
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

    public static void main(String[] args) {
        DisjointSet graph = new DisjointSet(7);
        graph.unionByRank(1,2);
        graph.unionByRank(2,3);
        graph.unionByRank(4,5);
        graph.unionByRank(6,7);
        graph.unionByRank(5,6);

        // if 3 and 7 are in same component or now
        if(graph.findUPar(3) == graph.findUPar(7))
            System.out.println("Same Component");
        else
            System.out.println("Not Same Component");

        // connect them
        graph.unionByRank(3,7);

        // if 3 and 7 are in same component or now
        if(graph.findUPar(3) == graph.findUPar(7))
            System.out.println("Same Component");
        else
            System.out.println("Not Same Component");
    }
}
