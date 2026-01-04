package Problems.Graphs.TopoSortProbs.Medium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventualSafeState {

    // Problem: https://leetcode.com/problems/find-eventual-safe-states/

    // Using DFS Cycle Detection
    // States:
    // 1. 0 -> Unvisited
    // 2. 1 -> Visiting the node
    // 3. 2 -> Marked Safe Node
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int V = graph.length;
        int[] state = new int[V];
        List<Integer> result = new ArrayList<>();
        for(int i=0; i<V; i++){
            if(dfsCheck(graph, i, state))
                result.add(i);
        }

        return result;
    }

    public boolean dfsCheck(int[][] graph, int node, int[] state){
        if(state[node] != 0){
            return state[node] == 2;
        }

        state[node] = 1;

        for(int neighbour: graph[node]){
            if(!dfsCheck(graph, neighbour, state))
                return false;
        }

        state[node] = 2;
        return true;
    }


    // Using BFS Topo SOrt
    // Intuition: Instead of finding cycles, find safe nodes first.
    //🪜 BFS Steps
    //Build reverse graph
    //Compute outdegree of each node
    //Push nodes with outdegree = 0 into queue
    //BFS:
        //Reduce outdegree of parents
        //If parent outdegree becomes 0 → safe
        //Sort result (LeetCode expects sorted order)
    public List<Integer> eventualSafeNodesBFS(int[][] graph) {
        int n = graph.length;
        int[] outdegree = new int[n];
        List<List<Integer>> reverseGraph = new ArrayList<>();
        for(int i=0; i<n; i++){
            reverseGraph.add(new ArrayList<>());
        }

        // Build reverse graph and outdegree
        for(int i=0; i<n; i++){
            outdegree[i] = graph[i].length;
            for(int neighbour: graph[i]){
                reverseGraph.get(neighbour).add(i);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0; i<n; i++){
            if(outdegree[i] == 0)
                queue.offer(i);
        }

        boolean[] safe = new boolean[n];
        while (!queue.isEmpty()) {
            int node = queue.poll();
            safe[node] = true;

            for (int parent : reverseGraph.get(node)) {
                outdegree[parent]--;
                if (outdegree[parent] == 0) {
                    queue.offer(parent);
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (safe[i]) {
                result.add(i);
            }
        }
        return result;
    }

}
