package Problems.Graphs.BFSDFSProbs.Medium;
import java.util.*;

public class NumberOfEnclaves {
    // Problem:https://leetcode.com/problems/number-of-enclaves/description/
    // Problem is a sibling of Surround Os

    // Optimal Solution: BFS Traversal
    // Time Complexity: O(m*n)
        public int numEnclaves(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            boolean[][] visited = new boolean[m][n];
            Queue<int[]> queue = new LinkedList<>();

            // Step 1: Add boundary land cells
            for (int i = 0; i < m; i++) {
                if (grid[i][0] == 1) {
                    queue.offer(new int[]{i, 0});
                    visited[i][0] = true;
                }
                if (grid[i][n - 1] == 1) {
                    queue.offer(new int[]{i, n - 1});
                    visited[i][n - 1] = true;
                }
            }

            for (int j = 0; j < n; j++) {
                if (grid[0][j] == 1) {
                    queue.offer(new int[]{0, j});
                    visited[0][j] = true;
                }
                if (grid[m - 1][j] == 1) {
                    queue.offer(new int[]{m - 1, j});
                    visited[m - 1][j] = true;
                }
            }

            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

            // Step 2: BFS to mark non-enclave land
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int r = curr[0], c = curr[1];

                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];

                    if (nr >= 0 && nr < m &&
                            nc >= 0 && nc < n &&
                            grid[nr][nc] == 1 &&
                            !visited[nr][nc]) {

                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }

            // Step 3: Count enclave cells
            int count = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        count++;
                    }
                }
            }

            return count;
        }

    public int numEnclavesDFS(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];

        // Step 1: DFS from boundary land
        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 1) dfs(grid, i, 0, visited);
            if (grid[i][n - 1] == 1) dfs(grid, i, n - 1, visited);
        }

        for (int j = 0; j < n; j++) {
            if (grid[0][j] == 1) dfs(grid, 0, j, visited);
            if (grid[m - 1][j] == 1) dfs(grid, m - 1, j, visited);
        }

        // Step 2: Count enclaves
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(int[][] grid, int r, int c, boolean[][] visited) {
        if (r < 0 || r >= grid.length ||
                c < 0 || c >= grid[0].length ||
                grid[r][c] == 0 ||
                visited[r][c]) {
            return;
        }

        visited[r][c] = true;

        dfs(grid, r + 1, c, visited);
        dfs(grid, r - 1, c, visited);
        dfs(grid, r, c + 1, visited);
        dfs(grid, r, c - 1, visited);
    }

}
