# 07. Sudoku Solver

**Tier:** 3 (Backtracking)
**Confluent tag:** Yes
**Link:** https://leetcode.com/problems/sudoku-solver/

## Problem

Fill the empty cells (`'.'`) of a partially filled 9x9 Sudoku board so that every row, column, and 3x3 box contains the digits 1 to 9 exactly once. Modify the board in place. Exactly one solution is guaranteed.

## Intuition

Scan cells in order. At the first empty cell, try digits `'1'` to `'9'`. For each digit that is valid for that cell, place it and recurse to solve the rest. If the recursion fails, undo the placement and try the next digit. If no digit works, return `false` so the caller backtracks.

The validity check is the same row, column, and box test used in Valid Sudoku (note 02).

## Approach

1. Find the first empty cell scanning row by row.
2. For each digit `'1'` to `'9'` that passes `isPlacementValid`:
   - Place the digit.
   - Recurse. If it returns `true`, return `true`.
   - Otherwise reset the cell to `'.'`.
3. If no digit works, return `false`.
4. If no empty cell is found, the board is solved, so return `true`.

## Complexity

- **Time:** O(9^m) in the worst case, where m is the number of empty cells. Constraint checks prune most branches in practice.
- **Space:** O(m) for the recursion stack.

## Edge cases

- Already complete board: returns immediately.
- Minimal clues: the search may explore many branches before finding the solution.
- Each placement check scans 27 cells, which is constant work per cell.

## Code

```java
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
}
```

## Function explanations

Runnable file: `SudokuSolver.java` (includes a `main` demo that solves the sample board and prints it).

### `solveSudoku`

- **Public `void` entry point:** matches the LeetCode signature, which edits the board in place. The recursive `solve` returns a `boolean`, which this method hides.

### `solve`

- **Finds the first empty cell in row order:** fixing one cell at a time keeps the search tree manageable.
- **Tries `'1'` to `'9'`:** only digits that pass `isPlacementValid` are placed, so the board stays consistent at every step.
- **Resets to `'.'` after a failed recursion:** undoes the placement so the next digit is tried on a clean board.
- **`return false` after the digit loop:** no digit fits this cell under the current board, so an earlier placement must be wrong. Returning lets the caller try its next digit. Scanning on would be wrong, because this cell still needs a value.
- **`return true` when no empty cell is found:** every cell is filled and valid, so the puzzle is solved.

### `isPlacementValid`

- **One loop for row, column, and box:** each `index` from 0 to 8 checks one cell in the row, one in the column, and one in the box, so 27 checks run in 9 iterations.
- **Box cell from `boxStartRow + index / 3` and `boxStartCol + index % 3`:** walks the 3x3 box row by row from its top-left corner.

## Interview one-liner

"I fill the first empty cell with each digit that passes the row, column, and box check, recurse, and undo on failure. The search is exponential in the number of empty cells in theory, but the constraints prune it heavily in practice."
