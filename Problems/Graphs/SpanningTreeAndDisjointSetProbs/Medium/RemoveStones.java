package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Medium;

import java.util.HashSet;
import java.util.Set;

public class RemoveStones {
    // Problem:https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/description/

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

    // Why 1001 offset and 2005 size?
    // Because max row & column constraints are <= 10000
    // So we need to separate rows & columns to avoid conflicts
    // So we use 10001 as offset and 20005 as size, row = 1000, col = maxRow + 1 => 1000 + 1 => 1001
    // We use 2005 as a safe si, we could also use 2002 here but it would be too tight;

    public int removeStones(int[][] stones) {
        int n = stones.length;

        // Max row & column constraints are <= 10000
        int OFFSET = 10001; // to separate rows & columns
        DisjointSet ds = new DisjointSet(20005);

        Set<Integer> usedNodes = new HashSet<>();

        for (int[] s : stones) {
            int row = s[0];
            int col = s[1] + OFFSET;

            ds.union(row, col);

            usedNodes.add(row);
            usedNodes.add(col);
        }

        // Count unique connected components
        Set<Integer> components = new HashSet<>();
        for (int node : usedNodes) {
            components.add(ds.find(node));
        }

        return n - components.size();
    }


}
