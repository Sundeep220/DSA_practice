package Problems.Graphs.BFSDFSProbs.Medium;
import java.util.*;

public class ZeroOneMatrix {
   // Problem: https://leetcode.com/problems/01-matrix/
    // Optimal Solution: Multi-Source BFS (Using visited[])
    // Time Complexity: O(m × n)
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;

            int[][] dist = new int[m][n];
            boolean[][] visited = new boolean[m][n];

            Queue<int[]> queue = new LinkedList<>();

            // Step 1: Initialize BFS with all 0 cells
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] == 0) {
                        queue.offer(new int[]{i, j});
                        visited[i][j] = true;
                        dist[i][j] = 0;
                    }
                }
            }

            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

            // Step 2: Multi-source BFS
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];

                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];

                    if (nr >= 0 && nr < m &&
                            nc >= 0 && nc < n &&
                            !visited[nr][nc]) {

                        visited[nr][nc] = true;
                        dist[nr][nc] = dist[r][c] + 1;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }

            return dist;
        }


        // More Optimal: Without using visited array
        public int[][] updateMatrixOptimal(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;

            int[][] dist = new int[m][n];
            for (int i = 0; i < m; i++) {
                Arrays.fill(dist[i], -1);
            }

            Queue<int[]> queue = new LinkedList<>();

            // Step 1: Push all 0s
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] == 0) {
                        dist[i][j] = 0;
                        queue.offer(new int[]{i, j});
                    }
                }
            }

            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

            // Step 2: BFS
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];

                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];

                    if (nr >= 0 && nr < m &&
                            nc >= 0 && nc < n &&
                            dist[nr][nc] == -1) {

                        dist[nr][nc] = dist[r][c] + 1;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            return dist;
        }

}
