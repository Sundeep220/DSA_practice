package Problems.Graphs.Basics;


import java.util.ArrayList;
import java.util.List;

public class DFSTraversal {

        static void dfsComponent(
                int node,
                List<List<Integer>> adj,
                boolean[] visited,
                List<Integer> result
        ) {
            visited[node] = true;
            result.add(node);

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    dfsComponent(neighbor, adj, visited, result);
                }
            }
        }

        static List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
            boolean[] visited = new boolean[V + 1];
            List<Integer> result = new ArrayList<>();

            for (int i = 1; i <= V; i++) {
                if (!visited[i]) {
                    dfsComponent(i, adj, visited, result);
                }
            }
            return result;
        }

        static void addEdge(List<List<Integer>> adj, int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        public static void main(String[] args) {
            int V = 7;
            List<List<Integer>> adj = new ArrayList<>();

            for (int i = 0; i <= V; i++) {
                adj.add(new ArrayList<>());
            }

            addEdge(adj, 1, 2);
            addEdge(adj, 1, 3);
            addEdge(adj, 2, 4);
            addEdge(adj, 5, 6);
            addEdge(adj, 5, 7);

            System.out.println(dfsOfGraph(V, adj));
        }

}
