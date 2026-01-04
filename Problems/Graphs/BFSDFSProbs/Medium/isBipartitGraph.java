package Problems.Graphs.BFSDFSProbs.Medium;
import java.util.*;
public class isBipartitGraph {
    // Problem:


    // Time Complexity: O(V + E)
    // Spcae: O(V)
    public boolean isBipartiteBFS(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);

        for (int i = 0; i < n; i++) {
            if (color[i] == -1) {
                if (!bfs(graph, i, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bfs(int[][] graph, int start, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        color[start] = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int neighbor : graph[node]) {
                if (color[neighbor] == -1) {
                    color[neighbor] = 1 - color[node];
                    queue.offer(neighbor);
                } else if (color[neighbor] == color[node]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Time Complexity: O(V + E)
    // Spcae: O(V)
    public boolean isBipartiteDFS(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);

        for (int i = 0; i < n; i++) {
            if (color[i] == -1) {
                if (!dfs(graph, i, 0, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int node, int currColor, int[] color) {
        color[node] = currColor;

        for (int neighbor : graph[node]) {
            if (color[neighbor] == -1) {
                if (!dfs(graph, neighbor, 1 - currColor, color)) {
                    return false;
                }
            } else if (color[neighbor] == currColor) {
                return false;
            }
        }
        return true;
    }

}
