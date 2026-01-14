package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Hard;

import java.util.*;

public class SwimInRisingWater {
    // Problem: https://leetcode.com/problems/swim-in-rising-water/description/

    // Using Dijikstra Algorithm
    // Only catch here is: we need to minimise the maximum time taken to swim
    // So instaed of : distance[v] = min(sum of weights)
    // We do: time[v] = min(max elevation on path to v)
    // State in PQ: (time_so_far, row, col)

    // Time: O(n² log n²) ≈ O(n² log n)
    // Space: O(n²)
    public boolean isValid(int row, int col, int n){
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        boolean[][] visited = new boolean[n][n];

        // MinHeap: {time, row, col}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparing(a -> a[0]));
        pq.add(new int[] {grid[0][0], 0, 0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int time = curr[0];
            int row = curr[1];
            int col = curr[2];

            if(visited[row][col]) continue;

            if(row == n-1 && col == n-1) return time;
            visited[row][col] = true;

            for(int[] dir: dirs){
                int nRow = row + dir[0];
                int nCol = col + dir[1];

                if(isValid(nRow, nCol, n) && !visited[nRow][nCol]){
                    int newTime = Math.max(time, grid[nRow][nCol]);
                    pq.add(new int[] {newTime, nRow, nCol});
                }
            }
        }
        return -1;
    }

    // Using Disjoint Set
    // Algorithm (Step-by-Step)
        //Step 1: Flatten the grid
            //Treat each cell (r,c) as a node:
                //nodeId = r * n + c
        //Step 2: Sort all cells by elevation
            // Create a list of:
                //elevation, row, col)
            //Sort ascending.
        //Step 3: Gradually “flood” the grid
            //Maintain: DSU of size n * n
                    //boolean[][] isActive → whether the cell is flooded/usable
                //For each cell in sorted order:
                    // Mark cell as active
                    //Union it with active neighbors
                    //After each union, check:
                    //if find(start) == find(end)
                        //    return current elevation
                    //That elevation is the minimum time required.
    // Time Complexity: O(n² log n)
    // Space Complexity: O(n²)
    class DisjointSet {
        int[] parent;
        int[] size;

        DisjointSet(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int a, int b) {
            int pa = find(a);
            int pb = find(b);
            if (pa == pb) return;

            if (size[pa] < size[pb]) {
                parent[pa] = pb;
                size[pb] += size[pa];
            } else {
                parent[pb] = pa;
                size[pa] += size[pb];
            }
        }
    }

    public int swimInWaterDS(int[][] grid) {
        int n = grid.length;
        int totalCells = n * n;

        // (elevation, row, col)
        List<int[]> cells = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                cells.add(new int[]{grid[r][c], r, c});
            }
        }

        // Sort by elevation
        cells.sort(Comparator.comparingInt(a -> a[0]));

        DisjointSet ds = new DisjointSet(totalCells);
        boolean[][] active = new boolean[n][n];

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        int start = 0;                  // (0,0)
        int end = n * n - 1;            // (n-1,n-1)

        for (int[] cell : cells) {
            int elevation = cell[0];
            int row = cell[1];
            int col = cell[2];

            active[row][col] = true;
            int currId = row * n + col;

            // Union with active neighbors
            for (int[] d : dirs) {
                int nr = row + d[0];
                int nc = col + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && active[nr][nc]) {

                    int neighborId = nr * n + nc;
                    ds.union(currId, neighborId);
                }
            }

            // Check connectivity
            if (ds.find(start) == ds.find(end)) {
                return elevation;
            }
        }

        return -1; // logically unreachable
    }

    // Another Optimal Approach: Using Binary Search and BFS
    private static final int[][] DIRS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public int swimInWaterBS(int[][] grid) {
        int n = grid.length;

        // Minimum possible time must allow start & end
        int left = Math.max(grid[0][0], grid[n - 1][n - 1]);
        int right = n * n - 1; // max elevation
        int answer = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canReachEnd(grid, mid)) {
                answer = mid;        // feasible, try smaller time
                right = mid - 1;
            } else {
                left = mid + 1;      // not feasible, need more water
            }
        }
        return answer;
    }

    // Feasibility check: can we reach (n-1,n-1) with water level = time?
    private boolean canReachEnd(int[][] grid, int time) {
        int n = grid.length;

        // If start itself is not reachable, fail early
        if (grid[0][0] > time) return false;

        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];

            if (row == n - 1 && col == n - 1) {
                return true;
            }

            for (int[] dir : DIRS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (isValid(newRow, newCol, n)
                        && !visited[newRow][newCol]
                        && grid[newRow][newCol] <= time) {

                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        return false;
    }

    private boolean canReachEndDFS(int[][] grid, int time) {
        int n = grid.length;

        // Early rejection: start itself not accessible
        if (grid[0][0] > time) return false;

        boolean[][] visited = new boolean[n][n];
        return dfs(0, 0, grid, time, visited);
    }

    private boolean dfs(int row, int col, int[][] grid, int time, boolean[][] visited) {
        int n = grid.length;

        // Reached destination
        if (row == n - 1 && col == n - 1) {
            return true;
        }

        visited[row][col] = true;

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        for (int[] dir : dirs) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValid(newRow, newCol, n)
                    && !visited[newRow][newCol]
                    && grid[newRow][newCol] <= time) {

                if (dfs(newRow, newCol, grid, time, visited)) {
                    return true;
                }
            }
        }
        return false;
    }




}
