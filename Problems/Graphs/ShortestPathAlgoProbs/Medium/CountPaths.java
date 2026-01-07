package Problems.Graphs.ShortestPathAlgoProbs.Medium;

import java.util.*;

public class CountPaths {
    // Problem: https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/description/


    // Time Complexity: O(ElogV)
    // Space Complexity: O(V + E)
    public int countPaths(int n, int[][] roads) {
        int MOD = 1_000_000_007;

        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        // Undirected graph
        for (int[] r : roads) {
            adj.get(r[0]).add(new int[]{r[1], r[2]});
            adj.get(r[1]).add(new int[]{r[0], r[2]});
        }

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        int[] ways = new int[n];
        ways[0] = 1;

        PriorityQueue<long[]> pq =
                new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            int node = (int) curr[0];
            long currDist = curr[1];

            if (currDist > dist[node]) continue;

            for (int[] nei : adj.get(node)) {
                int next = nei[0];
                long wt = nei[1];

                if (dist[next] > currDist + wt) {
                    dist[next] = currDist + wt;
                    ways[next] = ways[node];
                    pq.offer(new long[]{next, dist[next]});
                }
                else if (dist[next] == currDist + wt) {
                    ways[next] = (ways[next] + ways[node]) % MOD;
                }
            }
        }

        return ways[n - 1];
    }

}
