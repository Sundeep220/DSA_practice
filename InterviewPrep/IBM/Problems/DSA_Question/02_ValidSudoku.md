# 02. Valid Sudoku

**Tier:** 1 (Hash tables and arrays)
**Confluent tag:** Yes
**Link:** https://leetcode.com/problems/valid-sudoku/

## Problem

Determine whether a 9x9 Sudoku board is valid. Each row, each column, and each of the nine 3x3 sub-boxes must contain the digits 1 to 9 without repetition. Empty cells are `'.'` and are ignored.

## Intuition

Checking rows, columns, and boxes in three separate passes works, but one pass is enough. Keep three sets of "digits seen" for each row, column, and box. When a digit appears, `Set.add` returns `false` if it was already in that set, which means the board is invalid.

The box index for cell `(row, col)` is `(row / 3) * 3 + (col / 3)`, which maps the 9 boxes to indices 0 to 8.

## Approach

1. Create 9 sets for rows, 9 for columns, and 9 for boxes.
2. Scan all 81 cells. Skip `'.'`.
3. For each digit, try to add it to the row set, column set, and box set.
4. If any `add` returns `false`, return `false`.
5. If the scan finishes, return `true`.

## Complexity

- **Time:** O(1). The board is always 9x9, so 81 cells are checked.
- **Space:** O(1). At most 27 sets with at most 9 digits each.

## Edge cases

- All dots: valid.
- A valid-looking row or column, but a duplicate inside one 3x3 box. The box set catches it.
- Partially filled boards: empty cells are skipped.

## Code

```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidSudoku {

    public boolean isValidSudoku(char[][] board) {
        List<Set<Character>> rowSets = new ArrayList<>();
        List<Set<Character>> colSets = new ArrayList<>();
        List<Set<Character>> boxSets = new ArrayList<>();

        for (int index = 0; index < 9; index++) {
            rowSets.add(new HashSet<>());
            colSets.add(new HashSet<>());
            boxSets.add(new HashSet<>());
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                char digit = board[row][col];

                if (digit == '.') {
                    continue;
                }

                int boxIndex = (row / 3) * 3 + (col / 3);

                boolean isNewInRow = rowSets.get(row).add(digit);
                boolean isNewInCol = colSets.get(col).add(digit);
                boolean isNewInBox = boxSets.get(boxIndex).add(digit);

                if (!isNewInRow || !isNewInCol || !isNewInBox) {
                    return false;
                }
            }
        }

        return true;
    }
}
```

## Function explanations

Runnable file: `ValidSudoku.java` (same folder, includes a `main` demo with a valid board).

### `isValidSudoku`

- **Three groups of 9 sets:** rows, columns, and boxes are independent rules. A digit must be unique within each, so each group needs its own set.
- **Created up front:** the board is always 9x9, so the 27 sets are fixed. Creating them first means the loop never checks for null.
- **Skip `'.'`:** empty cells impose no constraint, so they cannot conflict with anything.
- **`boxIndex = (row / 3) * 3 + (col / 3)`:** integer division groups cells into 3x3 blocks. Each block coordinate runs 0 to 2, and the formula combines them into one index from 0 to 8.
- **Three `add` results stored before the `if`:** `Set.add` both checks and records the digit. Storing each result in a named variable keeps the three checks readable in one condition.
- **Return `false` on the first conflict:** one repeated digit makes the board invalid, so there is no need to scan further.

## Interview one-liner

"I track digits seen per row, column, and box in one pass. A repeated digit in any set means the board is invalid. The board is fixed at 9x9, so it's O(1) time and space."
