package Problems.Graphs.ShortestPathAlgoProbs.Easy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortestPathBwSrcEnd {

    // Problem: https://www.geeksforgeeks.org/problems/shortest-path-in-undirected-graph/1
    // Time Complexity: O(V + E)
    // Space Complexity: O(V)
    public static int[] shortestPath(
            int V,
            List<List<Integer>> adj,
            int src
    ) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        Queue<Integer> q = new LinkedList<>();
        dist[src] = 0;
        q.offer(src);

        while (!q.isEmpty()) {
            int node = q.poll();

            for (int neighbor : adj.get(node)) {
                if (dist[neighbor] > dist[node] + 1) {
                    dist[neighbor] = dist[node] + 1;
                    q.offer(neighbor);
                }
            }
        }

        return dist;
    }

}
