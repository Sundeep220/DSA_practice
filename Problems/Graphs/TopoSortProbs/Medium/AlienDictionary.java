package Problems.Graphs.TopoSortProbs.Medium;
import java.util.*;
public class AlienDictionary {

    // | Metric | Value                |
    //| ------ | -------------------- |
    //| Time   | **O(N × L + K + E)** |
    //| Space  | **O(K + E)**         |
    private List<Integer> topoSort(int V, List<List<Integer>> adj) {
        int[] indegree = new int[V];

        // Calculate indegree
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj.get(i)) {
                indegree[neighbor]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        List<Integer> topo = new ArrayList<>();

        while (!q.isEmpty()) {
            int node = q.poll();
            topo.add(node);

            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    q.offer(neighbor);
                }
            }
        }

        return topo;
    }

    // Alien Dictionary
    public String findOrder(String[] dict, int N, int K) {

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            adj.add(new ArrayList<>());
        }

        // Build graph
        for (int i = 0; i < N - 1; i++) {
            String s1 = dict[i];
            String s2 = dict[i + 1];

            // ❗ Invalid prefix case
            if (s1.length() > s2.length() && s1.startsWith(s2)) {
                return "";
            }

            int len = Math.min(s1.length(), s2.length());
            for (int ptr = 0; ptr < len; ptr++) {
                if (s1.charAt(ptr) != s2.charAt(ptr)) {
                    adj.get(s1.charAt(ptr) - 'a')
                            .add(s2.charAt(ptr) - 'a');
                    break;
                }
            }
        }

        List<Integer> topo = topoSort(K, adj);

        // ❗ Cycle detection
        if (topo.size() < K) {
            return "";
        }

        StringBuilder ans = new StringBuilder();
        for (int node : topo) {
            ans.append((char)(node + 'a'));
        }

        return ans.toString();
    }

}
