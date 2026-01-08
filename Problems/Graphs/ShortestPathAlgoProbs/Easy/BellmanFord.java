package Problems.Graphs.ShortestPathAlgoProbs.Easy;

import java.util.Arrays;

public class BellmanFord {
    // Problem: https://www.geeksforgeeks.org/problems/distance-from-the-source-bellman-ford-algorithm/1

    // Bellman Ford Algorithm
    // Time: O(V * E)
    // Space: O(V)
    public int[] bellmanFord(int V, int[][] edges, int src) {
        int INF = (int)1e8;
        int[] dist = new int[V];

        // Step 1: Initialize distances
        Arrays.fill(dist, INF);
        dist[src] = 0;

        // Step 2: Relax edges V-1 times
        for (int i = 1; i <= V - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int wt = edge[2];

                if (dist[u] != INF && dist[u] + wt < dist[v]) {
                    dist[v] = dist[u] + wt;
                }
            }
        }

        // Step 3: Check for negative cycle
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            if (dist[u] != INF && dist[u] + wt < dist[v]) {
                return new int[]{-1};
            }
        }


        // Step 4: Return result
        return dist;

    }
}
