package Problems.Arrays.Medium;

import java.util.Arrays;

public class SpiralPrinting {
    // Problem: https://leetcode.com/problems/spiral-matrix/
    // Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
    // Time Complexity: O(m*n) Space Complexity: O(1)
    // Ex: Input: matrix = [[1,2,3],[4,5,6],[7,8,9]] Output: [1,2,3,6,9,8,7,4,5]
    public static int[] spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] result = new int[m * n];
        int top = 0, bottom = m - 1, left = 0, right = n - 1;
        int c = 0;
        while(top <= bottom && left <= right){
            // print left - > right first
            for(int i=left; i<=right; i++)
                result[c++] = matrix[top][i];
            top++;
            // print top -> bottom
            for(int i=top; i <= bottom; i++)
                result[c++] = matrix[i][right];
            right--;

            // print right -> left now, but here check if there are any rows left or not
            if(top <= bottom){
                for(int i=right; i>=left; i--)
                    result[c++] = matrix[bottom][i];
                bottom--;
            }
            // print bottom -> top, but here check if any columns left to print
            if(left <= right){
                for(int i=bottom; i>=top; i--)
                    result[c++] = matrix[i][left];
                left++;
            }

        }
        return result;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 11, 12 },
                { 13, 14, 15, 16 }
        };
        System.out.println(Arrays.toString(spiralOrder(matrix)));
    }
}
