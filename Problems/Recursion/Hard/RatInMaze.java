package Problems.Recursion.Hard;

import java.util.ArrayList;
import java.util.List;

public class RatInMaze {
    // Problem: https://www.naukri.com/code360/problems/rat-in-a-maze_1215030

    public static List<String> findPath(int[][] maze, int n){
        List<String> ans = new ArrayList<>();
        int[][] visited = new int[n][n];
        if(maze[0][0] == 1)
            findPathUtil(maze, n, 0, 0, "", visited, ans);
        return ans;
    }

    public static void findPathUtil(int[][] maze, int n, int i, int j, String path, int[][] visited, List<String> ans) {
        if(i == n - 1 && j == n - 1) {
            ans.add(path);
            return;
        }

        // downward
        if (i + 1 < n && visited[i + 1][j] == 0 && maze[i + 1][j] == 1) {
            visited[i][j] = 1;
            findPathUtil(maze, n, i + 1, j, path + 'D', visited, ans);
            visited[i][j] = 0;
        }

        // left
        if (j - 1 >= 0 && visited[i][j - 1] == 0 && maze[i][j - 1] == 1) {
            visited[i][j] = 1;
            findPathUtil(maze, n, i, j - 1, path + 'L', visited, ans);
            visited[i][j] = 0;
        }

        // right 
        if (j + 1 < n && visited[i][j + 1] == 0 && maze[i][j + 1] == 1) {
            visited[i][j] = 1;
            findPathUtil(maze, n, i, j + 1, path + 'R', visited, ans);
            visited[i][j] = 0;
        }

        // upward
        if (i - 1 >= 0 && visited[i - 1][j] == 0 && maze[i - 1][j] == 1) {
            visited[i][j] = 1;
            findPathUtil(maze, n, i - 1, j, path + 'U', visited, ans);
            visited[i][j] = 0;
        }
    }

    // Better Solution: Making changes in the original matrix
    public static List<String> findPathBetter(int[][] maze, int n) {
        List<String> ans = new ArrayList<>();
        if (maze[0][0] == 1)
            findPathBetter(maze, n, 0, 0, "", ans);
        return ans;
    }

    public static void findPathBetter(int[][] maze, int n, int i, int j, String path, List<String> ans) {
        if (i == n - 1 && j == n - 1) {
            ans.add(path);
            return;
        }

        // Mark current cell as visited
        maze[i][j] = -1;

        // D - down
        if (i + 1 < n && maze[i + 1][j] == 1) {
            findPathBetter(maze, n, i + 1, j, path + 'D', ans);
        }

        // L - left
        if (j - 1 >= 0 && maze[i][j - 1] == 1) {
            findPathBetter(maze, n, i, j - 1, path + 'L', ans);
        }

        // R - right
        if (j + 1 < n && maze[i][j + 1] == 1) {
            findPathBetter(maze, n, i, j + 1, path + 'R', ans);
        }

        // U - up
        if (i - 1 >= 0 && maze[i - 1][j] == 1) {
            findPathBetter(maze, n, i - 1, j, path + 'U', ans);
        }

        // Backtrack - unmark the cell
        maze[i][j] = 1;
    }

    // Most Optimal Solution
    public static List<String> findPathMostOptimal(int[][] maze, int n) {
        List<String> ans = new ArrayList<>();
        if (maze[0][0] == 1 && maze[n - 1][n - 1] == 1)
            findPathUtilOptimal(maze, n, 0, 0, "", ans);
        return ans;
    }

    public static void findPathUtilOptimal(int[][] maze, int n, int i, int j, String path, List<String> ans) {
        if (i == n - 1 && j == n - 1) {
            ans.add(path);
            return;
        }

        int[] dx = {1, 0, 0, -1};
        int[] dy = {0, 1, -1, 0};
        char[] dir = {'D', 'R', 'L', 'U'};

        // Mark current cell as visited
        maze[i][j] = -1;

        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];

            if (isSafe(maze, x, y, n)) {
                findPathUtilOptimal(maze, n, x, y, path + dir[k], ans);
            }
        }

        // Unmark current cell (backtrack)
        maze[i][j] = 1;
    }


    public static boolean isSafe(int[][] maze, int x, int y, int n){
        return x >= 0 && x < n && y >= 0 && y < n && maze[x][y] == 1;
    }

    // With visited array
    public static List<String> findPathMostOptimal2(int[][] maze, int n) {
        List<String> ans = new ArrayList<>();
        boolean[][] visited = new boolean[n][n];

        if (maze[0][0] == 1 && maze[n - 1][n - 1] == 1)
            findPathUtilOptimal2(maze, n, 0, 0, "", ans, visited);

        return ans;
    }

    public static void findPathUtilOptimal2(int[][] maze, int n, int i, int j,
                                           String path, List<String> ans, boolean[][] visited) {
        if (i == n - 1 && j == n - 1) {
            ans.add(path);
            return;
        }

        int[] dx = {1, 0, 0, -1};
        int[] dy = {0, 1, -1, 0};
        char[] dir = {'D', 'R', 'L', 'U'};

        visited[i][j] = true;

        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];

            if (isSafe2(maze, visited, x, y, n)) {
                findPathUtilOptimal2(maze, n, x, y, path + dir[k], ans, visited);
            }
        }

        visited[i][j] = false;
    }

    public static boolean isSafe2(int[][] maze, boolean[][] visited, int i, int j, int n) {
        return i >= 0 && i < n && j >= 0 && j < n &&
                maze[i][j] == 1 && !visited[i][j];
    }



    public static void main(String[] args) {
        int[][] maze = {{1, 0, 0, 0}, {1, 1, 0, 1}, {1, 1, 0, 0}, {0, 1, 1, 1}};
        System.out.println(findPath(maze, 4));
        System.out.println(findPathBetter(maze, 4));
        System.out.println(findPathMostOptimal(maze, 4));
        System.out.println(findPathMostOptimal2(maze, 4));
    }
}
