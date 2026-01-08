package Problems.Graphs.ShortestPathAlgoProbs.Easy;
import java.util.*;

public class ShortestPathInUndirectedGraph {
    // Problem: Here we need to return the shortest path instead of value

    static class Pair {
        int node, wt;
        Pair(int node, int wt) {
            this.node = node;
            this.wt = wt;
        }
    }

    public List<Integer> shortestPathWithRoute(int V, int E, int[][] edges) {

        // 1. Build adjacency list (UNDIRECTED)
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];
            adj.get(u).add(new Pair(v, wt));
            adj.get(v).add(new Pair(u, wt));
        }

        // 2. Distance and parent arrays
        int[] dist = new int[V];
        int[] parent = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        for (int i = 0; i < V; i++) parent[i] = i;

        dist[0] = 0;

        // 3. Min-heap
        PriorityQueue<Pair> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a.wt));
//        PriorityQueue<Pair> pq =
//                new PriorityQueue<>((a, b) -> Integer.compare(a.wt, b.wt));

        pq.offer(new Pair(0, 0));

        // 4. Dijkstra
        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int u = curr.node;
            int currDist = curr.wt;

            if (currDist > dist[u]) continue;

            for (Pair nbr : adj.get(u)) {
                int v = nbr.node;
                int wt = nbr.wt;

                if (dist[v] > dist[u] + wt) {
                    dist[v] = dist[u] + wt;
                    parent[v] = u;          // ⭐ path tracking
                    pq.offer(new Pair(v, dist[v]));
                }
            }
        }

        // 5. If destination unreachable
        int dest = V - 1;
        if (dist[dest] == Integer.MAX_VALUE) {
            return new ArrayList<>(); // no path
        }

        // 6. Reconstruct path
        List<Integer> path = new ArrayList<>();
        int node = dest;

        while (parent[node] != node) {
            path.add(node);
            node = parent[node];
        }
        path.add(0); // source

        Collections.reverse(path);
        return path;
    }

}
