package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Medium;

public class DisjointSetBySize {
    static class DisjointSet {
        int[] parent;
        int[] size;

        DisjointSet(int n) {
            parent = new int[n + 1];
            size = new int[n + 1];

            for (int i = 0; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int findUPar(int node) {
            if (node == parent[node])
                return node;

            parent[node] = findUPar(parent[node]);
            return parent[node];
        }

        void unionBySize(int u, int v) {
            int pu = findUPar(u);
            int pv = findUPar(v);

            if (pu == pv) return;

            if (size[pu] < size[pv]) {
                parent[pu] = pv;
                size[pv] += size[pu];
            } else {
                parent[pv] = pu;
                size[pu] += size[pv];
            }
        }
    }

    public static void main(String[] args) {
        DisjointSet g = new DisjointSet(7);
        g.unionBySize(1,2);
        g.unionBySize(2,3);
        g.unionBySize(4,5);
        g.unionBySize(6,7);
        g.unionBySize(5,6);

        // if 3 and 7 are in same component or now
        if(g.findUPar(3) == g.findUPar(7))
            System.out.println("Same Component");
        else
            System.out.println("Not Same Component");

        // connect them
        g.unionBySize(3,7);

        // if 3 and 7 are in same component or now
        if(g.findUPar(3) == g.findUPar(7))
            System.out.println("Same Component");
        else
            System.out.println("Not Same Component");
    }

}
