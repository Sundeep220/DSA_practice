package Problems.Graphs.TopoSortProbs.Medium;

import java.util.ArrayList;
import java.util.List;

public class AllTopoSorts {

    // Problem: Program to print all topological sorts of a graph
    // Time Complexity:
    //O(T × (V + E)), where
    //T = number of all possible topological sorts
    //V = number of vertices
    //E = number of edges
    // Worst case: O(V! * (V + E))
    //Space Complexity: O(V)
    public static List<List<Integer>> allTopologicalSorts(int V, List<List<Integer>> adj) {
        int[] indegree = new int[V];
        for (int u = 0; u < V; u++) {
            for (int v : adj.get(u)) {
                indegree[v]++;
            }
        }

        boolean[] visited = new boolean[V];
        List<Integer> current = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        backtrack(adj, indegree, visited, current, result, V);
        return result;
    }

    private static void backtrack(List<List<Integer>> adj, int[] indegree, boolean[] visited, List<Integer> current, List<List<Integer>> result, int V) {
        if (current.size() == V) {
            // Important: add a COPY
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < V; i++) {
            if (!visited[i] && indegree[i] == 0) {

                // Choose
                visited[i] = true;
                current.add(i);
                for (int nei : adj.get(i)) {
                    indegree[nei]--;
                }

                // Explore
                backtrack(adj, indegree, visited, current, result, V);

                // Backtrack
                visited[i] = false;
                current.removeLast();
                for (int nei : adj.get(i)) {
                    indegree[nei]++;
                }
            }
        }
    }

}
