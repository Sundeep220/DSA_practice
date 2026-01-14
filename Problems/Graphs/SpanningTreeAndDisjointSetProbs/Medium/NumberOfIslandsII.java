package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberOfIslandsII {
    // Problem: You are given:
    //An empty m x n grid initially filled with water (0).
    //A list of operations positions, where each operation converts a water cell into land (1).
    //After each operation, you must report the number of islands currently in the grid.
    //Each island is defined as connected land cells (4 directions: up, down, left, right).
    // INPUT: m = 3, n = 3
    //positions = [[0,0],[0,1],[1,2],[2,1]]
    //OUTPUT: [1, 1, 2, 3]
    //Explanation:
    //After making (0,0) land → 1 island
    //After making (0,1) land → still 1 (they join)
    //After making (1,2) land → new island (2)
    //After making (2,1) land → new island (3)

    // Optimal Solution: Using Disjoint Set
    //| Component    | Complexity                           |
    //| ------------ | ------------------------------------ |
    //| Time (total) | `O(k × α(m*n))`                      |
    //| Space        | `O(m × n)` (DSU arrays + grid state) |
    static class DisjointSet {
        int[] parent;
        int[] size;

        DisjointSet(int n) {
            parent = new int[n];
            size = new int[n];
            Arrays.fill(parent, -1); // -1 means "not yet activated"
        }

        // Activate a node (convert water -> land)
        void makeSet(int x) {
            parent[x] = x;
            size[x] = 1;
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // path compression
            }
            return parent[x];
        }

        boolean union(int a, int b) {
            int pa = find(a);
            int pb = find(b);

            if (pa == pb) return false;

            // union by size
            if (size[pa] < size[pb]) {
                parent[pa] = pb;
                size[pb] += size[pa];
            } else {
                parent[pb] = pa;
                size[pa] += size[pb];
            }
            return true;
        }

        boolean isActive(int x) {
            return parent[x] != -1;
        }
    }

    public List<Integer> numIslands2(int m, int n, int[][] positions) {

        DisjointSet ds = new DisjointSet(m * n);
        List<Integer> result = new ArrayList<>();
        int islands = 0;

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] pos : positions) {
            int r = pos[0];
            int c = pos[1];
            int idx = r * n + c;

            // If already land, island count doesn't change
            if (ds.isActive(idx)) {
                result.add(islands);
                continue;
            }

            // Convert water -> land
            ds.makeSet(idx);
            islands++;

            // Try to union with neighbors
            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;

                int nIdx = nr * n + nc;
                if (ds.isActive(nIdx)) {
                    if (ds.union(idx, nIdx)) {
                        islands--; // merged two islands
                    }
                }
            }

            result.add(islands);
        }
        return result;
    }

}
