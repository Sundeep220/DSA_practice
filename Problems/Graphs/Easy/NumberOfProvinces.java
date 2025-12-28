package Problems.Graphs.Easy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NumberOfProvinces {
    // Problem: https://leetcode.com/problems/number-of-provinces/description/

    public int findCircleNumDFS(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for(int i = 0; i < n; i++){
            if(!visited[i]){
                dfsTraversalHelper(i, isConnected, visited);
                provinces++;
            }
        }
        return provinces;
    }

    private void dfsTraversalHelper(int node, int[][] adjacencyMatrix, boolean[] visited){
        visited[node] = true;

        for(int j = 0; j < adjacencyMatrix.length; j++){
            if(adjacencyMatrix[node][j] == 1 && !visited[j]){
                dfsTraversalHelper(j, adjacencyMatrix, visited);
            }
        }
    }

    public int findCircleNumBfs(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                bfs(i, isConnected, visited);
                provinces++;
            }
        }
        return provinces;
    }

    private void bfs(int start, int[][] isConnected, boolean[] visited) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int city = q.poll();
            for (int j = 0; j < isConnected.length; j++) {
                if (isConnected[city][j] == 1 && !visited[j]) {
                    visited[j] = true;
                    q.offer(j);
                }
            }
        }
    }

}
