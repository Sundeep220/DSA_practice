package Problems.Graphs.Medium;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class FloodFill {
    // Problem: https://leetcode.com/problems/flood-fill/description/


    // Naive Solution: DFS Recursive Solution
    // Time Complexity: O(m*n) Space Complexity: O(m*n)

    public int[][] floodFillNaive(int[][] image, int sr, int sc, int color) {
        int originalColor = image[sr][sc];

        // Important optimization
        if (originalColor == color) return image;

        dfs(image, sr, sc, originalColor, color);
        return image;
    }

    private void dfs(int[][] image, int r, int c, int originalColor, int color) {
        // Base cases
        if (r < 0 || r >= image.length ||
                c < 0 || c >= image[0].length ||
                image[r][c] != originalColor) {
            return;
        }

        // Mark visited by recoloring
        image[r][c] = color;

        // Explore neighbors
        dfs(image, r + 1, c, originalColor, color);
        dfs(image, r - 1, c, originalColor, color);
        dfs(image, r, c + 1, originalColor, color);
        dfs(image, r, c - 1, originalColor, color);
    }

    // Better Solution: DFS Iterative soluiton
    // Time Complexity: O(m*n) Space Complexity: O(m*n)
    public int[][] floodFillBetter(int[][] image, int sr, int sc, int color){
        int originalColor = image[sr][sc];
        if(originalColor == color) return image;

        int n = image.length;
        int m = image[0].length;

        Stack<int[]> stack = new Stack<>();
        image[sr][sc] = color;
        stack.push(new int[]{sr, sc});

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while(!stack.isEmpty()){
            int[] curr = stack.pop();
            int r = curr[0];
            int c = curr[1];

            for(int[] dir: dirs){
                int nr = r + dir[0];
                int nc = c + dir[1];

                if(nr >= 0 && nr < n && nc >= 0 && nc < m && image[nr][nc] == originalColor){
                    image[nr][nc] = color;
                    stack.push(new int[]{nr, nc});
                }
            }
        }
        return image;
    }

    // Optimal Solution: Using BFS traversal
    // Time Complexity: O(m*n) Space Complexity: O(m*n)
    public int[][] floodFillOptimal(int[][] image, int sr, int sc, int color) {
        int originalColor = image[sr][sc];
        if (originalColor == color) return image;

        int m = image.length;
        int n = image[0].length;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        image[sr][sc] = color;

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0], c = cell[1];

            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < m &&
                        nc >= 0 && nc < n &&
                        image[nr][nc] == originalColor) {

                    image[nr][nc] = color;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return image;
    }


}
