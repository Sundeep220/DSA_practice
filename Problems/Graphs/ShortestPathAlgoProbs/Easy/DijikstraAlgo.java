package Problems.Graphs.ShortestPathAlgoProbs.Easy;

import java.util.*;

public class DijikstraAlgo {
    // Problem: https://www.geeksforgeeks.org/problems/shortest-path-in-undirected-graph/1

    // Using Dijkstra's Algorithm with Priority Queue
    // Time Complexity: O((V + E) log V)
    // Space Complexity: O(V + E)

    static class Pair {
        int node, wt;
        Pair(int node, int wt) {
            this.node = node;
            this.wt = wt;
        }
    }

    public int[] shortestPath(int V, int E, int[][] edges) {

        // 1. Build adjacency list (UNDIRECTED)
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            adj.get(u).add(new Pair(v, wt));
            adj.get(v).add(new Pair(u, wt)); // undirected
        }

        // 2. Distance array
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;

        // 3. Min-heap (distance, node)
//        PriorityQueue<Pair> pq =
//                new PriorityQueue<>((a, b) -> a.wt - b.wt);
        PriorityQueue<Pair> pq =
                new PriorityQueue<>((a, b) -> Integer.compare(a.wt, b.wt));


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
                    pq.offer(new Pair(v, dist[v]));
                }
            }
        }

        // 5. Convert unreachable nodes to -1
        for (int i = 0; i < V; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                dist[i] = -1;
            }
        }

        return dist;
    }


    // Faster approach: Using Set with Dijkstra's Algorithm
    // Intuition:
        //Store (distance, node) in a TreeSet
        //Always pick the minimum distance element
        //If a shorter path is found:
            //Remove old (dist[v], v)
            //Insert updated (newDist, v)
        //This mimics a true decrease-key operation.
    // Time Complexity: O((V + E) log V)
    // Space Complexity: O(V + E)
    public int[] shortestPathBetter(int V, int E, int[][] edges) {

        // Build adjacency list (undirected)
        ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        for (int[] e : edges) {
            int u = e[0], v = e[1], wt = e[2];
            adj.get(u).add(new Pair(v, wt));
            adj.get(v).add(new Pair(u, wt));
        }

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;

        // TreeSet acts like min-heap with decrease-key
        TreeSet<Pair> set = new TreeSet<>(
                (a, b) -> {
                    if (a.wt != b.wt) return a.node - b.node;
                    return a.node - b.node;
                }
        );

        set.add(new Pair(0, 0));

        while (!set.isEmpty()) {
            Pair curr = set.pollFirst();
            int u = curr.node;

            for (Pair nbr : adj.get(u)) {
                int v = nbr.node;
                int wt = nbr.wt;

                if (dist[v] > dist[u] + wt) {

                    // Remove old entry if present
                    if (dist[v] != Integer.MAX_VALUE) {
                        set.remove(new Pair(v, dist[v]));
                    }

                    dist[v] = dist[u] + wt;
                    set.add(new Pair(v, dist[v]));
                }
            }
        }

        // Convert unreachable nodes to -1
        for (int i = 0; i < V; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                dist[i] = -1;
            }
        }

        return dist;
    }

}
