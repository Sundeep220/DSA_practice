package Problems.Recursion.Medium;

import java.util.HashSet;
import java.util.Set;

public class AllWordsFromGrid {

    public static Set<String> getAllWords(char[][] board) {
        Set<String> result = new HashSet<>();
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, visited, i, j, "", result);
            }
        }

        return result;
    }

    private static void dfs(char[][] board, boolean[][] visited, int row, int col,
                            String path, Set<String> result) {

        int m = board.length, n = board[0].length;

        if (row < 0 || col < 0 || row >= m || col >= n || visited[row][col]) return;

        path += board[row][col];
        result.add(path); // every non-empty prefix is a valid word

        visited[row][col] = true;

        // Explore all 4 directions
        dfs(board, visited, row - 1, col, path, result); // up
        dfs(board, visited, row + 1, col, path, result); // down
        dfs(board, visited, row, col - 1, path, result); // left
        dfs(board, visited, row, col + 1, path, result); // right

        visited[row][col] = false; // backtrack
    }

    public static void main(String[] args) {
        char[][] board = {{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
        System.out.println(getAllWords(board).size());
    }
}
