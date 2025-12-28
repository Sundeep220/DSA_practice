package Problems.Graphs.Basics;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSTraversal {
    static void bfsComponent(
            int start,
            List<List<Integer>> adj,
            boolean[] visited,
            List<Integer> result
    ) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }

    static List<Integer> bfsOfGraph(int V, List<List<Integer>> adj) {
        boolean[] visited = new boolean[V + 1];
        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= V; i++) {
            if (!visited[i]) {
                bfsComponent(i, adj, visited, result);
            }
        }

        return result;
    }

}
