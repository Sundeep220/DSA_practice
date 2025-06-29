package Problems.Arrays.Medium;

public class SetMatrixZeroes {
    // Problem Link: https://leetcode.com/problems/set-matrix-zeroes/
    // Given a m x n matrix. If an element is 0, set its entire row and column to 0. Do it in place.

    // Brute Force: The idea is to traverse the matrix and mark the rows and columns which are to be set to 0 by -1
    // But here's the catch, for arr[i][j] == 0, we will only mark rows and columns which contain non-zero elements
    // and leave the row and column which contain only 0s as it is.
    // Time Complexity:  O((N*M)*(N + M)) + O(N*M), where N = no. of rows in the matrix and M = no. of columns in the matrix.
    //Reason: Firstly, we are traversing the matrix to find the cells with the value 0. It takes O(N*M). Now, whenever we find any such cell we mark that row and column with -1. This process takes O(N+M). So, combining this the whole process, finding and marking, takes O((N*M)*(N + M)).
    //Another O(N*M) is taken to mark all the cells with -1 as 0 finally.
    // Space Complexity: O(1)
    public static void SetZeroesBruteForce(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] == 0){
                    setrowZero(matrix, i, j, n);
                    setcolZero(matrix, i, j, m);
                }
            }
        }

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] == -1){
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public static void setrowZero(int[][] matrix, int i, int j, int n){
        for(int k = 0; k < n; k++){
            if(matrix[i][k] != 0){
                matrix[i][k] = -1;
            }
        }
    }

    public static void setcolZero(int[][] matrix, int i, int j, int m){
            for(int k = 0; k < m; k++){
                if(matrix[k][j] != 0){
                    matrix[k][j] = -1;
                }
            }
    }


    // Better Approach: Instead of traversing marking the array with -1, we can use two arrays
    // one for row and one for column to mark the elements which are to be set to 0.
    // Time Complexity: O(2*(N*M)), where N = no. of rows in the matrix and M = no. of columns in the matrix.
    // Space Complexity: O(m+n)
    public static void SetZeroesBetterApproach(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] == 0){
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(row[i] || col[j]){
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // Optimal Approach: Using the first row and column of the matrix to mark the elements which are to be set to 0.
    // Instead of using two arrays, we can use the first row and first column of the matrix to mark the elements which are to be set to 0.
    // But here's the catch, for arr[0][0] will be set twice, so to we maintain another variable col0 to mark the first column.
    // Also we will first mark the element from (1,1) to (n, m) and then mark the first row and first column.
    // As we are using first row and col to mark, we should not update the first row and first column.
    // WE will update those row based on values of col0 and a[0][0] variable.
    // Time Complexity: O(N*M), where N = no. of rows in the matrix and M = no. of columns in the matrix.
    // Space Complexity: O(1)
    public static void SetZeroesOptimalApproach(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean firstRowHasZero = false;
        boolean firstColHasZero = false;

        // Step 1: Check first column of each row
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColHasZero = true;
                break;
            }
        }
        // Step 1: Check first row of each column
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowHasZero = true;
                break;
            }
        }

        // Step 2: Use first row and column to mark zeroes
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // Step 3: Set zeroes based on marks
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Step 4: Zero first row/column if needed
        if (firstRowHasZero) {
            // Mark first row as zero
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstColHasZero) {
            // Mark first column as zero
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }



    public static void main(String[] args) {
        int[][] matrix = {{1,1,1},{1,0,1},{1,1,1}};
        SetZeroesBruteForce(matrix);
//        SetZeroesBetterApproach(matrix);
//        SetZeroesOptimalApproach(matrix);
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }
}
