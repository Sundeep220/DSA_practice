package Problems.Graphs.BFSDFSProbs.Medium;

import java.util.LinkedList;
import java.util.Queue;

public class SurroundOs {


    // Problem: https://leetcode.com/problems/surrounded-regions/

    // Better Solution: DFS Traversal
    // Time Complexity: O(m*n)
    // Space Complexity: O(m*n)

    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;

        boolean[][] visited = new boolean[m][n];

        // Step 1: DFS from boundary
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') dfs(board, i, 0, visited);
            if (board[i][n - 1] == 'O') dfs(board, i, n - 1, visited);
        }

        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') dfs(board, 0, j, visited);
            if (board[m - 1][j] == 'O') dfs(board, m - 1, j, visited);
        }

        // Step 2: Capture surrounded regions
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && !visited[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] board, int r, int c, boolean[][] visited) {
        if (r < 0 || r >= board.length ||
                c < 0 || c >= board[0].length ||
                board[r][c] != 'O' ||
                visited[r][c]) {
            return;
        }

        visited[r][c] = true;

        dfs(board, r + 1, c, visited);
        dfs(board, r - 1, c, visited);
        dfs(board, r, c + 1, visited);
        dfs(board, r, c - 1, visited);
    }


    // Optimal SOlution: USing BFS (As no stack overflow risk)
    public void solveBFS(char[][] board) {
        int m = board.length;
        int n = board[0].length;

        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();

        // Step 1: Add boundary 'O's
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                queue.offer(new int[]{i, 0});
                visited[i][0] = true;
            }
            if (board[i][n - 1] == 'O') {
                queue.offer(new int[]{i, n - 1});
                visited[i][n - 1] = true;
            }
        }

        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                queue.offer(new int[]{0, j});
                visited[0][j] = true;
            }
            if (board[m - 1][j] == 'O') {
                queue.offer(new int[]{m - 1, j});
                visited[m - 1][j] = true;
            }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // Step 2: BFS to mark safe 'O's
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];

            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < m &&
                        nc >= 0 && nc < n &&
                        board[nr][nc] == 'O' &&
                        !visited[nr][nc]) {

                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        // Step 3: Capture surrounded regions
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && !visited[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }
}
