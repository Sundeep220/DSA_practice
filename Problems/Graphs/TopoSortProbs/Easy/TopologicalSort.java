package Problems.Graphs.TopoSortProbs.Easy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TopologicalSort {
    // Problem: https://www.geeksforgeeks.org/problems/topological-sort/1

    // DFS Solution:
    // Time Complexity: O(V + E) Space Complexity: O(V)
    public ArrayList<Integer> topoSort(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        // Build adjacency list
        for(int i = 0; i < V; i++){
            adj.add(new ArrayList<>());
        }

        for(int[] edge: edges){
            adj.get(edge[0]).add(edge[1]);
        }
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < V; i++){
            if(!visited[i]){
                dfs(adj, i, visited, stack);
            }
        }

//        ArrayList<Integer> result = new ArrayList<>();
//        while(!stack.isEmpty()){
//            result.add(stack.pop());
//        }
        ArrayList<Integer> result = (ArrayList<Integer>) IntStream.range(0, stack.size())
                .mapToObj(i -> stack.pop())
                .collect(Collectors.toList());

        return result;
    }

    public void dfs(List<List<Integer>> adj, int node, boolean[] visited, Stack<Integer> stack){
        visited[node] = true;

        for(Integer neighbour: adj.get(node)){
            if(!visited[neighbour]){
                dfs(adj, neighbour, visited, stack);
            }
        }
        stack.push(node);
    }

    // BFS Solution: Modified BFS using Kahn's Algorithm
    // 🧠 Core Intuition (MOST IMPORTANT)
        //A node with indegree = 0 has no dependencies, so it can be placed first in topological order.
    //We repeatedly:
        //Pick nodes with indegree 0
        //Remove them from the graph
        //Reduce indegree of their neighbors
    //If we can remove all nodes, the graph is a DAG and the order is valid.

    // Time Complexity: O(V + E) Space Complexity: O(V)
    public ArrayList<Integer> topoSortBFS(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        int[] indegree = new int[V];

        // Step 1: Build graph
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            indegree[e[1]]++;
        }

        // Step 2: Initialize queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        while(!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            for(int neighbour: adj.get(node)){
                indegree[neighbour]--;
                if(indegree[neighbour] == 0){
                    queue.offer(neighbour);
                }
            }
        }

        return result;
    }
}
