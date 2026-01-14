package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Hard;

import java.util.HashSet;
import java.util.Set;

public class MakeLargeIsland {
    // Problem: https://leetcode.com/problems/making-a-large-island/description/

    // Time: O(N^2)
    // Space: O(N^2)
    class DisjointSet {
        int[] parent;
        int[] size;

        public DisjointSet(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int node) {
            if (parent[node] != node) {
                parent[node] = find(parent[node]);
            }
            return parent[node];
        }

        public void unionBySize(int u, int v) {
            int pu = find(u);
            int pv = find(v);
            if (pu == pv) return;

            if (size[pu] < size[pv]) {
                parent[pu] = pv;
                size[pv] += size[pu];
            } else {
                parent[pv] = pu;
                size[pu] += size[pv];
            }
        }

        public int getSize(int node) {
            return size[find(node)];
        }
    }

    public boolean isValid(int row, int col, int n){
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    public int largestIsland(int[][] grid) {
        int n = grid.length;
        DisjointSet ds = new DisjointSet(n * n);
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        boolean hasZero = false;

        // Phase 1: Union existing land
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (grid[row][col] == 1) {
                    for (int[] dir : dirs) {
                        int nr = row + dir[0];
                        int nc = col + dir[1];
                        if (isValid(nr, nc, n) && grid[nr][nc] == 1) {
                            ds.unionBySize(row * n + col, nr * n + nc);
                        }
                    }
                } else {
                    hasZero = true;
                }
            }
        }

        int maxIsland = 0;

        // Phase 2: Flip one zero
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (grid[row][col] == 0) {
                    Set<Integer> components = new HashSet<>();
                    int islandSize = 1;

                    for (int[] dir : dirs) {
                        int nr = row + dir[0];
                        int nc = col + dir[1];
                        if (isValid(nr, nc, n) && grid[nr][nc] == 1) {
                            components.add(ds.find(nr * n + nc));
                        }
                    }

                    for (int root : components) {
                        islandSize += ds.getSize(root);
                    }
                    maxIsland = Math.max(maxIsland, islandSize);
                }
            }
        }

        // All 1s case
        return hasZero ? maxIsland : n * n;
    }


}
