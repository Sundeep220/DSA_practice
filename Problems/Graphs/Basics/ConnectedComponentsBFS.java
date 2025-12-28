package Problems.Graphs.Basics;

import java.util.*;

public class ConnectedComponentsBFS {

    static void bfs(int start, List<List<Integer>> adj, boolean[] visited) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int node = q.poll();

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.offer(neighbor);
                }
            }
        }
    }

    static int countConnectedComponents(int V, List<List<Integer>> adj) {
        boolean[] visited = new boolean[V + 1];
        int components = 0;

        for (int i = 1; i <= V; i++) {
            if (!visited[i]) {
                bfs(i, adj, visited);
                components++;
            }
        }

        return components;
    }

    public static void main(String[] args) {
        int V = 10;

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            adj.add(new ArrayList<>());
        }

        addEdge(adj, 1, 2);
        addEdge(adj, 1, 3);
        addEdge(adj, 2, 4);
        addEdge(adj, 4, 3);

        addEdge(adj, 5, 6);
        addEdge(adj, 5, 7);
        addEdge(adj, 6, 7);

        addEdge(adj, 8, 9);

        System.out.println("Connected Components = " +
                countConnectedComponents(V, adj));
    }

    static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
}
