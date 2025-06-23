package Problems.Arrays.Easy;

import java.util.Arrays;

public class LeftRotateByDElements {
    // Time Complexity: O(n)
    // Space Complexity: O(d)
    // Brute Force using temp array
    // We can opttimse this by using concept
    // as number of elements to be rotated is d
    // if d > number of elements in the array
    // then d = d%n, as we have to rotate the array by d elements, and rotating array n times(if d > n) will give same result
    // if d < number of elements in the array
    // then d = d
    public static void rotateByD(int arr[], int n, int d) {
        d = d % n;
        int temp[] = new int[d];
        System.arraycopy(arr, 0, temp, 0, d);
        for (int i = d; i < n; i++) {
            arr[i - d] = arr[i];
        }
        for(int i=n-d; i<n; i++) {
            arr[i] = temp[i-(n-d)];
        }
    }
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    // Optimal Solution: Using Reversal Concept
    // lets ex: arr[] = {1, 2, 3, 4, 5, 6, 7}, d = 3,the output should be {4, 5, 6, 7, 1, 2, 3}
    // first we reverse the first d elements {3, 2, 1, 4, 5, 6, 7}
    // then we reverse the n-d elements {3, 2, 1, 7, 6, 5, 4}
    // then we reverse the whole array {4, 5, 6, 7, 1, 2, 3} => Output!!
    public static void rotateByD2(int arr[], int n, int d) {
        d = d % n;
        reverse(arr, 0, d - 1); // for rotating right use n - d
        reverse(arr, d, n - 1);
        reverse(arr, 0, n - 1);
    }

    public static void reverse(int arr[], int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int arr[] = { 1, 2, 3, 4, 5, 6, 7 };
        int n = arr.length;
        int d = 3;

        rotateByD(arr, n, d);
        System.out.println("\nBrute Force Solution: ");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }

        int[]  arr2 = { 1, 2, 3, 4, 5, 6, 7 };
        int m = arr2.length;
        rotateByD2(arr2, m, d);
        System.out.println("\nOptimal Solution: ");
        for (int i = 0; i < m; i++) {
            System.out.print(arr2[i] + " ");
        }
    }
}
