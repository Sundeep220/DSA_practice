package Problems.Graphs.ShortestPathAlgoProbs.Easy;

public class FloydWarshall {

    // Problem: https://www.geeksforgeeks.org/problems/implementing-floyd-warshall2042/1

    // Time Complexity: O(V^3)
    // Space Complexity: O(V^2)
    public void floydWarshall(int[][] dist) {
        int n = dist.length;
        int INF = 100000000; // 10^8

        // Try every node as intermediate
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    // If either path is unreachable, skip
                    if (dist[i][k] == INF || dist[k][j] == INF) continue;

                    // Relaxation step
                    dist[i][j] = Math.min(
                            dist[i][j],
                            dist[i][k] + dist[k][j]
                    );
                }
            }
        }
    }


    //| Metric | Value               |
    //| ------ | ------------------- |
    //| Time   | **O(V³)**           |
    //| Space  | **O(1)** (in-place) |
    public int[][] floydWarshallWithNegativeCycle(int[][] matrix) {

        int n = matrix.length;
        int INF = (int) 1e9;

        // Step 1: Initialize distances
        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1) {
                    dist[i][j] = INF;
                } else {
                    dist[i][j] = matrix[i][j];
                }

                if (i == j) {
                    dist[i][j] = 0;
                }
            }
        }

        // Step 2: Floyd–Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (dist[i][k] != INF && dist[k][j] != INF) {
                        dist[i][j] = Math.min(
                                dist[i][j],
                                dist[i][k] + dist[k][j]
                        );
                    }
                }
            }
        }

        // Step 3: Detect Negative Cycle
        for (int i = 0; i < n; i++) {
            if (dist[i][i] < 0) {
                return new int[][]{{-1}};  // Negative cycle detected
            }
        }

        // Step 4: Convert INF back to -1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == INF) {
                    dist[i][j] = -1;
                }
            }
        }

        return dist;
    }


}
