package Problems.Graphs.BFSDFSProbs.Medium;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CountDistinctIslands {

    // DFS + Shape Encoding
    //🔹 High-Level Algorithm
        //Traverse the grid
        //When you find an unvisited 1:
            //Start DFS
            //Record each cell’s position relative to the start cell
        //Store the shape (list of relative positions) in a Set
        //Return size of the set
    //Time Complexity: O(n × m)
    //Space Complexity: O(n × m)
    int countDistinctIslands(int[][] grid) {
        // Your Code here
        int n = grid.length;
        int m = grid[0].length;

        boolean[][] visited = new boolean[n][m];
        Set<List<String>> shapes = new HashSet<>();


        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(grid[i][j] == 1 && !visited[i][j]){
                    List<String> shape = new ArrayList<>();
                    dfs(grid, visited, i, j, i, j, shape);
                    shapes.add(shape);
                }
            }
        }
        return shapes.size();
    }

    public void dfs(int[][] grid, boolean[][] visited, int r, int c, int baseR, int baseC, List<String> shape){
        visited[r][c] = true;

        shape.add((r - baseR) + "," + (c - baseC));

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        for (int k = 0; k < 4; k++) {
            int nr = r + dr[k];
            int nc = c + dc[k];

            if (nr >= 0 && nr < grid.length &&
                    nc >= 0 && nc < grid[0].length &&
                    grid[nr][nc] == 1 &&
                    !visited[nr][nc]) {

                dfs(grid, visited, nr, nc, baseR, baseC, shape);
            }
        }
    }
}
