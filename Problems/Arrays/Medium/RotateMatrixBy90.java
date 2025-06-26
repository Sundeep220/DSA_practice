package Problems.Arrays.Medium;

public class RotateMatrixBy90 {
    // https://leetcode.com/problems/rotate-image/

    //Brute Force Approach: Take a dummy matrix and store the rotated matrix in it and print it
    // Time Complexity: O(n^2) + O(n^2) => O(n^2)
    // Space Complexity: O(n^2)
    public static void rotateBruteForce(int[][] matrix) {
        int[][] dummyMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                dummyMatrix[j][matrix.length - 1 - i] = matrix[i][j]; // storing  first row of the matrix and put it in the last column of the dummy matrix
            }
        }
        for (int[] ints : dummyMatrix) {
            for (int j = 0; j < dummyMatrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    // Optimal Approach:
    // First transpose the matrix and then reverse each row
    // Time Complexity: O(n^2) + O(n) => O(n^2)
    // Space Complexity: O(1)
    public static void rotate(int[][] matrix) {
        transpose(matrix); // transpose the matrix  O(n^2)
        System.out.println("After Transpose");
        for (int[] item : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(item[j] + " ");
            }
            System.out.println();
        }

        for (int[] value : matrix) {
            reverse(value); // reverse each row  O(n)
        }

        System.out.println("After Reverse");
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    private static  void transpose(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j <= i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    private static void reverse(int[] arr) {
        int left = 0, right = arr.length - 1;
        while(left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++; right--;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
//        rotate(matrix);
        rotateBruteForce(matrix);
    }
}
