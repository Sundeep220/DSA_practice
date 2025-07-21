package Problems.Recursion.Hard;

import java.util.*;

public class SudokuSolverII {

    public static List<char[][]> getAllSolutions(char[][] board) {
        List<char[][]> solutions = new ArrayList<>();
        solve(board, solutions);
        return solutions;
    }

    public static void solve(char[][] board, List<char[][]> solutions) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c;
                            solve(board, solutions); // Don't return early
                            board[i][j] = '.'; // Backtrack
                        }
                    }
                    return; // No valid number found here, backtrack
                }
            }
        }
        // Found a valid board, add deep copy
        solutions.add(copyBoard(board));
    }

    public static boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == c) return false;
            if (board[i][col] == c) return false;
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false;
        }
        return true;
    }

    private static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[9][9];
        for (int i = 0; i < 9; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }

    public static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("------+-------+------");
            }
            for (int j = 0; j < board[0].length; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Sample usage
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

        List<char[][]> solutions = getAllSolutions(board);
        System.out.println("Total Solutions Found: " + solutions.size());
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("\nSolution " + (i + 1) + ":");
            printBoard(solutions.get(i));
        }
    }
}
