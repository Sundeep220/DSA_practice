package Problems.Graphs.ShortestPathAlgoProbs.Medium;

import java.util.*;

public class FindCityWithLeastNeighbours {
    // Problem: https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/description/

    // Floyd Warshal Algorithm
    // Time COmplexity: O(N^3)
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int INF = (int) 1e9;
        int[][] dist = new int[n][n];

        // init
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        // edges
        for (int[] e : edges) {
            dist[e[0]][e[1]] = e[2];
            dist[e[1]][e[0]] = e[2];
        }

        // Floyd–Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(
                            dist[i][j],
                            dist[i][k] + dist[k][j]
                    );
                }
            }
        }

        int answer = -1;
        int minCount = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) {
                    count++;
                }
            }

            // IMPORTANT: tie → larger index
            if (count <= minCount) {
                minCount = count;
                answer = i;
            }
        }

        return answer;
    }

    // Optimal Solution Using Dijikstra
    // Time: O(n × (E log V))
    //Space: O(V + E)
    public int findTheCityOptimal(int n, int[][] edges, int distanceThreshold) {

        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] e : edges) {
            adj.get(e[0]).add(new int[]{e[1], e[2]});
            adj.get(e[1]).add(new int[]{e[0], e[2]});
        }

        int minCount = Integer.MAX_VALUE;
        int answer = -1;

        for (int src = 0; src < n; src++) {
            int reachable = dijkstra(src, n, adj, distanceThreshold);

            if (reachable <= minCount) {
                minCount = reachable;
                answer = src;
            }
        }

        return answer;
    }

    private int dijkstra(int sourceCity, int totalCities, List<List<int[]>> adjacencyList, int distanceThreshold) {

        int[] shortestDistance = new int[totalCities];
        Arrays.fill(shortestDistance, Integer.MAX_VALUE);
        shortestDistance[sourceCity] = 0;

        // Min-heap: {currentDistance, city}
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        minHeap.offer(new int[]{0, sourceCity});

        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int currentDistance = current[0];
            int currentCity = current[1];

            // Skip outdated entries
            if (currentDistance > shortestDistance[currentCity]) {
                continue;
            }

            for (int[] neighbor : adjacencyList.get(currentCity)) {
                int neighborCity = neighbor[0];
                int edgeWeight = neighbor[1];

                int newDistance = currentDistance + edgeWeight;

                if (newDistance < shortestDistance[neighborCity]) {
                    shortestDistance[neighborCity] = newDistance;
                    minHeap.offer(new int[]{newDistance, neighborCity});
                }
            }
        }

        int reachableCityCount = 0;
        for (int city = 0; city < totalCities; city++) {
            if (city != sourceCity && shortestDistance[city] <= distanceThreshold) {
                reachableCityCount++;
            }
        }

        return reachableCityCount;
    }

}
