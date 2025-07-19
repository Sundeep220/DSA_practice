package Problems.Recursion.Hard;

public class WordSearch {
    // https://leetcode.com/problems/word-search/

    public static boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(board[i][j] == word.charAt(0)) {
                    if(search(board, i, j, 0, word))
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean search(char[][] board, int row, int col, int idx, String word) {
        if(idx == word.length())
            return true;

        if(row < 0 || col < 0 || row >= board.length || col >= board[0].length
                || board[row][col] != word.charAt(idx))
            return false;

        char temp = board[row][col]; // Save the current character
        board[row][col] = '#';       // Mark as visited

        boolean found = search(board, row - 1, col, idx + 1, word) || // top
                search(board, row + 1, col, idx + 1, word) || // bottom
                search(board, row, col - 1, idx + 1, word) || // left
                search(board, row, col + 1, idx + 1, word);   // right

        board[row][col] = temp; // Backtrack

        return found;
    }

    public static void main(String[] args) {
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        String word = "ABCCED";
        System.out.println(exist(board, word));
    }
}
