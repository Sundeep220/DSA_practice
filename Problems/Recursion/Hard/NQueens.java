package Problems.Recursion.Hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class NQueens {
    // Problem: https://leetcode.com/problems/n-queens/

    // Brute Force Recursion and Backtracking
    // Using Recursion and Backtracking
    // Time Complexity: O(N!) × O(N)
    // Space Complexity: O(1)
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];
        for(char[] row : board) Arrays.fill(row, '.');

        backtrack(board, 0, ans);
        return ans;
    }

    public static void backtrack(char[][] board, int col, List<List<String>> ans) {
        if(col == board.length) {
            // We found a solution
            List<String> solution = new ArrayList<>();
            for (char[] r : board) solution.add(new String(r));
            ans.add(solution);
            return;
        }
        // Go through each row of the given column to check if it is valid
        for(int row = 0; row < board.length; row++) {
            if(isValid(board, row, col)) {
                board[row][col] = 'Q';
                backtrack(board, col + 1, ans);
                board[row][col] = '.'; // Backtrack
            }
        }

    }

    public static boolean isValid(char[][] board, int row, int col) {
        int dupRow = row, dupCol = col;

        // Check left upper diagonal
        while(row >= 0 && col >= 0) {
            if(board[row][col] == 'Q') return false;
            row--; col--;
        }

        // Check left side
        row = dupRow; col = dupCol;
        while(col >= 0) {
            if(board[row][col] == 'Q') return false;
            col--;
        }

        // Check left lower diagonal
        row = dupRow; col = dupCol;
        while(row < board.length && col >= 0) {
            if(board[row][col] == 'Q') return false;
            row++; col--;
        }
        return true;
    }

    // Optimal Solution1: Just optimizing the isValid() function using HashSet
    // Time Complexity: O(N!)
    // Space Complexity: O(N)
    public static List<List<String>> solveNQueensOptimal(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];
        for(char[] row : board) Arrays.fill(row, '.');
        HashSet<Integer> leftRow = new HashSet<>(), leftUpperDiagonal = new HashSet<>(), leftLowerDiagonal = new HashSet<>();

        backtrackOptimal(board, 0, leftRow, leftUpperDiagonal, leftLowerDiagonal, ans);
        return ans;
    }

    public static void backtrackOptimal(char[][] board, int col, HashSet<Integer> leftRow, HashSet<Integer> leftUpperDiagonal, HashSet<Integer> leftLowerDiagonal, List<List<String>> ans) {
        if(col == board.length) {
            List<String> solution = new ArrayList<>();
            for (char[] r : board) solution.add(new String(r));
            ans.add(solution);
            return;
        }
        for(int row = 0; row < board.length; row++) {
            if(leftRow.contains(row) || leftUpperDiagonal.contains(row + col) || leftLowerDiagonal.contains(col - row)) continue;

            board[row][col] = 'Q';
            leftRow.add(row);
            leftUpperDiagonal.add(row + col);
            leftLowerDiagonal.add(col - row);

            backtrackOptimal(board, col + 1, leftRow, leftUpperDiagonal, leftLowerDiagonal, ans);

            // Backtrack
            leftRow.remove(row);
            leftUpperDiagonal.remove(row + col);
            leftLowerDiagonal.remove(col - row);
            board[row][col] = '.';
        }
    }

    // Optimal Solution2: Just optimizing the isValid() funtion using boolean[] hashing Fastest
    // Time Complexity: O(N!)
    // Space Complexity: O(N)
    public static List<List<String>> solveNQueensOptimal2(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');

        boolean[] rowsUsed = new boolean[n];
        boolean[] upperDiagUsed = new boolean[2 * n - 1]; // row - col + (n - 1)
        boolean[] lowerDiagUsed = new boolean[2 * n - 1]; // row + col

        backtrackOptimal2(0, n, board, ans, rowsUsed, upperDiagUsed, lowerDiagUsed);
        return ans;
    }

    private static void backtrackOptimal2(int col, int n,
                                  char[][] board,
                                  List<List<String>> ans,
                                  boolean[] rowsUsed,
                                  boolean[] upperDiagUsed,
                                  boolean[] lowerDiagUsed) {
        if (col == n) {
            List<String> solution = new ArrayList<>();
            for (char[] row : board) {
                solution.add(new String(row));
            }
            ans.add(solution);
            return;
        }

        for (int row = 0; row < n; row++) {
            int upperIndex = row - col + (n - 1);
            int lowerIndex = row + col;

            if (!rowsUsed[row] && !upperDiagUsed[upperIndex] && !lowerDiagUsed[lowerIndex]) {
                board[row][col] = 'Q';
                rowsUsed[row] = true;
                upperDiagUsed[upperIndex] = true;
                lowerDiagUsed[lowerIndex] = true;

                backtrackOptimal2(col + 1, n, board, ans, rowsUsed, upperDiagUsed, lowerDiagUsed);

                // Backtrack
                board[row][col] = '.';
                rowsUsed[row] = false;
                upperDiagUsed[upperIndex] = false;
                lowerDiagUsed[lowerIndex] = false;
            }
        }
    }


    public static void main(String[] args) {
        int n = 4;
        System.out.println(solveNQueens(n));
        System.out.println(solveNQueensOptimal(n));
    }
}
