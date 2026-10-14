public class SudokuSolver {

    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] != '.') {
                    continue;
                }

                for (char digit = '1'; digit <= '9'; digit++) {
                    if (isPlacementValid(board, row, col, digit)) {
                        board[row][col] = digit;

                        if (solve(board)) {
                            return true;
                        }

                        board[row][col] = '.';
                    }
                }

                return false;
            }
        }

        return true;
    }

    private boolean isPlacementValid(char[][] board, int row, int col, char digit) {
        int boxStartRow = (row / 3) * 3;
        int boxStartCol = (col / 3) * 3;

        for (int index = 0; index < 9; index++) {
            if (board[row][index] == digit) return false;
            if (board[index][col] == digit) return false;

            int boxRow = boxStartRow + index / 3;
            int boxCol = boxStartCol + index % 3;
            if (board[boxRow][boxCol] == digit) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        new SudokuSolver().solveSudoku(board);
        for (char[] row : board) {
            System.out.println(new String(row));
        }
    }
}
