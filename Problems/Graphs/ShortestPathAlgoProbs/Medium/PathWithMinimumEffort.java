package Problems.Graphs.ShortestPathAlgoProbs.Medium;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathWithMinimumEffort {
    // Problem: https://leetcode.com/problems/path-with-minimum-effort/

    // Time Complexity: O(ElogV) => O((n*m)log(n*m))
    // Space Complexity: O(V) => O(nm)
    public int minimumEffortPath(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;

        int[][] effortMat = new int[n][m];
        for(int[] row: effortMat){
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        effortMat[0][0] = 0;
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        q.offer(new int[]{0, 0, 0}); // src will have 0 effort

        int[][] dirs = {{-1,0}, {1, 0}, {0,1}, {0, -1}};
        while(!q.isEmpty()){
            int[] curr = q.poll();
            int curR = curr[1];
            int curC = curr[2];
            int currDiff = curr[0];

            if(curR == n - 1 && curC == m - 1)
                return currDiff;

            for(int[] dir: dirs){
                int nr = curR + dir[0];
                int nc = curC + dir[1];

                if(nr >=0 && nr < n && nc >= 0 && nc <m){
                    int newEffort = Math.max(currDiff, Math.abs(heights[nr][nc] - heights[curR][curC]));
                    if(newEffort < effortMat[nr][nc]){
                        effortMat[nr][nc] = newEffort;
                        q.offer(new int[]{newEffort, nr, nc});
                    }
                }
            }
        }

        return 0;
    }

    // Approach 2: Binary Search + BFS (Optimal)
    //Key Idea
    //The answer (minimum effort) lies between 0 and maxHeightDiff
    //For a given effort mid, check:
        //Can we reach bottom-right using only edges with height difference ≤ mid?
        //This is a graph connectivity check
    // Time Complexity: O(ElogV) => O((n*m)log(maxHeightDiff))
    // Space Complexity: O(V) => O(nm)

    public int minimumEffortPathBS(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;

        int low = 0;
        int high = 0;

        // Find upper bound for binary search
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i > 0)
                    high = Math.max(high, Math.abs(heights[i][j] - heights[i - 1][j]));
                if (j > 0)
                    high = Math.max(high, Math.abs(heights[i][j] - heights[i][j - 1]));
            }
        }

        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (canReach(heights, mid, dirs)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private boolean canReach(int[][] heights, int maxEffort, int[][] dirs) {
        int n = heights.length;
        int m = heights[0].length;

        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            if (r == n - 1 && c == m - 1)
                return true;

            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc]) {
                    int diff = Math.abs(heights[nr][nc] - heights[r][c]);
                    if (diff <= maxEffort) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }

        return false;
    }
}
