package Problems.Graphs.TopoSortProbs.Easy;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CycleDetectInDirectedBFS {
    // Using Topological SOrt

    public boolean isCyclic(int V, List<List<Integer>> adj) {
        int[] indegree = new int[V];

        // Step 1: Calculate indegrees
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj.get(i)) {
                indegree[neighbor]++;
            }
        }

        // Step 2: Push nodes with indegree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        // Step 3: BFS
        int processed = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            processed++;

            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // Step 4: Check
        return processed != V; // true means cycle exists
    }
}
