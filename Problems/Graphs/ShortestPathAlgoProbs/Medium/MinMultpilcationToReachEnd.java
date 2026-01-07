package Problems.Graphs.ShortestPathAlgoProbs.Medium;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MinMultpilcationToReachEnd {
    // Problem:


    // Brute Force: Brute Force (DFS / Backtracking) ❌
    //💡 Idea
    //Try all possible multiplication sequences until:
        //You reach end, or
        //You go too deep
    //Time Complexity: O(4^k)
    //Space Complexity: O(k)
    int minimumMultiplications(int[] arr, int start, int end) {
        int[] ans = new int[1];
        ans[0] = Integer.MAX_VALUE;
        dfs(start, end, 0, ans, arr);
        return ans[0] == Integer.MAX_VALUE ? -1 : ans[0];
    }

    void dfs(int curr, int end, int steps, int[] ans, int[] arr) {
        if (curr == end) {
            ans[0] = Math.min(ans[0], steps);
            return;
        }
        if (steps > ans[0]) return;

        for (int m : arr) {
            dfs((curr * m) % 100000, end, steps + 1, ans, arr);
        }
    }

    //Better Approach (Graph + BFS) ✅
    //💡 Key Insight
    //
    //Each multiplication = 1 step
    //
    //We want minimum steps
    //
    //Graph edges are unweighted
    //
    //👉 BFS is guaranteed to find shortest path
    //
    //| Metric | Value           |
    //| ------ | --------------- |
    //| States | 100000          |
    //| Time   | `O(100000 * n)` |
    //| Space  | `O(100000)`     |

    public int minimumMultiplicationsBetter(int[] arr, int start, int end) {
        int MOD = 100000;
        boolean[] visited = new boolean[MOD];

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{start, 0});
        visited[start] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int node = curr[0];
            int steps = curr[1];

            if (node == end) return steps;

            for (int m : arr) {
                int next = (node * m) % MOD;
                if (!visited[next]) {
                    visited[next] = true;
                    q.offer(new int[]{next, steps + 1});
                }
            }
        }
        return -1;
    }

    // Optimal Approach (Graph + Dijkstra) ✅

    public int minimumMultiplicationsOptimal(int[] arr, int start, int end) {
        int MOD = 100000;
        int[] dist = new int[MOD];
        Arrays.fill(dist, Integer.MAX_VALUE);

        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        dist[start] = 0;

        while (!q.isEmpty()) {
            int curr = q.poll();

            for (int m : arr) {
                int next = (curr * m) % MOD;

                if (dist[next] > dist[curr] + 1) {
                    dist[next] = dist[curr] + 1;

                    if (next == end) return dist[next];

                    q.offer(next);
                }
            }
        }

        return dist[end] == Integer.MAX_VALUE ? -1 : dist[end];
    }


}
