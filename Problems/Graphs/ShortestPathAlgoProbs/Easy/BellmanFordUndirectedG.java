package Problems.Graphs.ShortestPathAlgoProbs.Easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BellmanFordUndirectedG {


    static int[] bellman_ford_undirected(int V, int[][] edges, int src) {

        int INF = (int)1e8;

        // Convert undirected → directed
        List<int[]> edgeList = new ArrayList<>();

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int w = e[2];

            edgeList.add(new int[]{u, v, w});
            edgeList.add(new int[]{v, u, w});
        }

        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        // Relax edges V-1 times
        for (int i = 1; i <= V - 1; i++) {
            for (int[] e : edgeList) {
                int u = e[0];
                int v = e[1];
                int w = e[2];

                if (dist[u] != INF && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        // Negative cycle detection
        for (int[] e : edgeList) {
            int u = e[0];
            int v = e[1];
            int w = e[2];

            if (dist[u] != INF && dist[u] + w < dist[v]) {
                return new int[]{-1};
            }
        }

        return dist;
    }
}
