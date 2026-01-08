package Problems.Graphs.ShortestPathAlgoProbs.Medium;

import java.util.*;

public class NetworkDelayTime {
    // Problem: https://leetcode.com/problems/network-delay-time/

    // Time Complexity: O(ElogV)
    // Space Complexity: O(V + E)
    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] time : times) {
            adj.get(time[0] - 1).add(new int[]{time[1] - 1, time[2]});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k - 1] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(
                Comparator.comparingInt(a -> a[1])
        );

        pq.offer(new int[]{k - 1, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int currTime = curr[1];

            if (currTime > dist[node]) continue;

            for (int[] nei : adj.get(node)) {
                int next = nei[0];
                int time = nei[1];

                if (dist[next] > currTime + time) {
                    dist[next] = currTime + time;
                    pq.offer(new int[]{next, dist[next]});
                }
            }
        }

        int ans = 0;
        for (int d : dist) {
            if (d == Integer.MAX_VALUE) return -1;
            ans = Math.max(ans, d);
        }
        return ans;
    }

}
