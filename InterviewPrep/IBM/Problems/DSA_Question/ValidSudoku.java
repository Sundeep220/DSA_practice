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
        System.out.println(new ValidSudoku().isValidSudoku(board));
    }
}
