package Problems.Recursion.Medium;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {
    // Problem: https://leetcode.com/problems/valid-sudoku/

    // Brute Force:
    // Time Complexity: O(81 * 27) => O(1)
    // Space Complexity: O(1)
    public boolean isValidSudoku(char[][] board) {

        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {

                if(board[row][col] != '.') {

                    char current = board[row][col];
                    board[row][col] = '.';

                    if(!isValid(board, row, col, current))
                        return false;

                    board[row][col] = current;
                }
            }
        }

        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char c) {

        for(int i = 0; i < 9; i++) {

            if(board[row][i] == c)
                return false;

            if(board[i][col] == c)
                return false;

            if(board[3 * (row / 3) + i / 3]
                    [3 * (col / 3) + i % 3] == c)
                return false;
        }

        return true;
    }

    // Better: Using Single HashSet
    // Time Complexity: O(81)
    // Space Complexity: O(1

    public boolean isValidSudokuHashSet(char[][] board) {

        Set<String> seen = new HashSet<>();

        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {

                char num = board[row][col];

                if(num == '.') continue;

                if(!seen.add(num + "row" + row) ||
                        !seen.add(num + "col" + col) ||
                        !seen.add(num + "box" + row/3 + "-" + col/3))
                    return false;
            }
        }

        return true;
    }

    // Optimal: Using Boolean Arrays
    // Time Complexity: O(81)
    // Space Complexity: O(1)
    public boolean isValidSudokuOptimal(char[][] board) {

        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];

        for(int row = 0; row < 9; row++) {

            for(int col = 0; col < 9; col++) {

                if(board[row][col] == '.')
                    continue;

                int num = board[row][col] - '1';

                int box = (row / 3) * 3 + col / 3;

                if(rows[row][num] ||
                        cols[col][num] ||
                        boxes[box][num])
                    return false;

                rows[row][num] = true;
                cols[col][num] = true;
                boxes[box][num] = true;
            }
        }

        return true;
    }

}
