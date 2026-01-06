package Problems.Graphs.ShortestPathAlgoProbs.Medium;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathInBinaryMaze {
    // Problem: https://leetcode.com/problems/shortest-path-in-binary-matrix/

    // Time Complexity: O(n^2)
    // Space Complexity: O(n^2)

    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return -1;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 1});
        grid[0][0] = 1;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int curR = curr[0];
            int curC = curr[1];
            int dist = curr[2];

            if(curR == n - 1 && curC == n - 1)
                return dist;

            for(int i=-1; i <= 1; i++){
                for(int j=-1; j <= 1; j++){
                    int nr = curR + i;
                    int nc = curC + j;

                    if (nr >= 0 && nc >= 0 && nr < n && nc < n && grid[nr][nc] == 0) {
                        grid[nr][nc] = 1; // mark visited
                        queue.offer(new int[]{nr, nc, dist + 1});
                    }
                }
            }
        }

        return -1;
    }

    // Using Dijikstra's Algorithm
    // Time Complexity: O(n^2)
    // Space Complexity: O(n^2)
    public int shortestPathBinaryMatrixDijikstra(int[][] grid) {
        int n = grid.length;

        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return -1;
        }

        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        // 8 directions
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        Queue<int[]> queue = new LinkedList<>();

        dist[0][0] = 1;
        queue.offer(new int[]{0, 0});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int i = 0; i < 8; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nc >= 0 && nr < n && nc < n && grid[nr][nc] == 0) {

                    // Dijkstra-style relaxation
                    if (dist[nr][nc] > dist[r][c] + 1) {
                        dist[nr][nc] = dist[r][c] + 1;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }

        return dist[n - 1][n - 1] == Integer.MAX_VALUE ? -1 : dist[n - 1][n - 1];
    }
}
