package Problems.Recursion.Hard;

import java.util.ArrayList;
import java.util.List;

public class MColoring {
    // Problem: https://www.geeksforgeeks.org/problems/m-coloring-problem-1587115620/1

    // Note this problems involve graphs
    // If adjancy list is given, then the problem can be solved this solution
    public static boolean mColoring(int[][] graph, int m) {
        int[] nodeColors = new int[graph.length];  // color[i] is the color assigned to vertex i in graph
        return colorNodes(graph, nodeColors, 0, m);

    }

    public static boolean colorNodes(int[][] graph, int[] nodeColors, int node, int m) {
        if(node == graph.length) return true; // all nodes have been colored

        for(int color = 1; color <= m; color++) {
            if(isSafe(graph, nodeColors, node, color)) {
                nodeColors[node] = color;   // color the current node
                if(colorNodes(graph, nodeColors, node + 1, m)) return true; // recursively color the rest of the nodes and check if the coloring is possible
                // if the coloring is not possible, reset the color
                nodeColors[node] = 0;  // backtrack
            }
        }
        // if the coloring is not possible for any color, return false
        return false;
    }

    public static boolean isSafe(int[][] graph, int[] nodeColors, int node, int color) {
        for(int i = 0; i < graph.length; i++) {
            if(graph[node][i] == 1 && nodeColors[i] == color) return false;
        }
        return true;
    }

    // If edge list is given, then first convert it to adjancy list
    // Edge list: [[0, 1], [1, 3], [2, 3], [3, 0], [0, 2]]

    public static boolean graphColoringII(int V, int[][] edges, int m) {
        // Step 1: Convert edge list to adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]); // because undirected
        }

        // print the adjacency list
        System.out.println("Adjacency List:");
        for (int i = 0; i < V; i++) {
            System.out.print(i + " -> ");
            for (int neighbor : adj.get(i)) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }

        int[] color = new int[V];
        return solveII(0, V, adj, color, m);
    }

    private static boolean solveII(int node, int V, List<List<Integer>> adj, int[] color, int m) {
        if (node == V) return true;

        for (int c = 1; c <= m; c++) {
            if (isSafeII(node, adj, color, c)) {
                color[node] = c;
                if (solveII(node + 1, V, adj, color, m)) return true;
                color[node] = 0; // backtrack
            }
        }

        return false;
    }

    private static boolean isSafeII(int node, List<List<Integer>> adj, int[] color, int col) {
        for (int neighbor : adj.get(node)) {
            if (color[neighbor] == col) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 1},
                {1, 3},
                {2, 3},
                {3, 0},
                {0, 2}
        };
        System.out.println(graphColoringII(5, graph, 3));
//        System.out.println(mColoring(graph, 3));
    }
}
