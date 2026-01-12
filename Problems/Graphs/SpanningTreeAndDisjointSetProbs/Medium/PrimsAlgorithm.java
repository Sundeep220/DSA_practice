package Problems.Graphs.SpanningTreeAndDisjointSetProbs.Medium;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimsAlgorithm {
    // Prims Algorithms

    // Algorithm:
    // 1. Pick any node as starting point
        //Maintain:
        //visited[] → nodes already in MST
        //minHeap → edges ordered by weight
    // 2. Add it to MST
    // 3. Add all its neighbors to priority queue
    // 4. Pick the minimum weight edge and add it to MST
    // 5. Repeat until all nodes are added to MST
    // Time Complexity: O(E log V)
    // Space Complexity: O(V + E)
    static class Pair {
        int node;
        int weight;

        Pair(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    public int spanningTree(int V, int[][] edges) {

        // 1. Build adjacency list
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int wt = e[2];

            adj.get(u).add(new Pair(v, wt));
            adj.get(v).add(new Pair(u, wt)); // undirected
        }

        // 2. Prim's Algorithm
        boolean[] visited = new boolean[V];

        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
//        PriorityQueue<Pair> pq =
//                new PriorityQueue<>((a, b) -> a.weight - b.weight);

        // Start from node 0 (any node works)
        pq.offer(new Pair(0, 0));

        int mstSum = 0;

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int node = curr.node;
            int wt = curr.weight;

            // Skip if already included
            if (visited[node]) continue;

            // Include node
            visited[node] = true;
            mstSum += wt;

            // Add adjacent edges
            for (Pair nbr : adj.get(node)) {
                if (!visited[nbr.node]) {
                    pq.offer(new Pair(nbr.node, nbr.weight));
                }
            }
        }

        return mstSum;
    }
}
