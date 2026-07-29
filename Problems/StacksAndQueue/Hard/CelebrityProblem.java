package Problems.StacksAndQueue.Hard;

import java.util.Stack;

public class CelebrityProblem {
    // Problem: https://www.geeksforgeeks.org/the-celebrity-problem/
    // Given a square matrix of n people (numbered 0 to n-1) who knows each other, where mat[i][j] == 1 means person i knows person j,
    // and mat[i][j] == 0 means person i does not know person j, find the celebrity.
    // A celebrity is a person who is known to all other n-1 people, but does not know anyone in return.
    // If there is no celebrity in the party, return -1.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int celebrity(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[] knowMe = new int[n];
        int[] iKnow = new int[n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == 1) {
                    knowMe[j] += 1;
                    iKnow[i] += 1;
                }
            }
        }

        for(int i = 0; i < n; i++) {
            if(knowMe[i] == n - 1 && iKnow[i] == 0) {
                return i;
            }
        }
        return -1;
    }


    // Better  Solution: Using Two Pointers
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int celebrityOptimal(int[][] matrix) {
        int n = matrix.length;
        int top = 0, bottom = n - 1;

        // Step 1: Find the potential celebrity
        while (top < bottom) {
            if (matrix[top][bottom] == 1) {
                // top knows bottom → top not celebrity
                top++;
            } else {
                // top does not know bottom → bottom not celebrity
                bottom--;
            }
        }

        // Step 2: Verify candidate
        int candidate = top;
        for (int i = 0; i < n; i++) {
            if (i == candidate) continue;

            // Candidate should not know i, and everyone should know candidate
            if (matrix[candidate][i] == 1 || matrix[i][candidate] == 0) {
                return -1;
            }
        }
        return candidate;
    }

    /*
     * Intuition:
     * Repeatedly compare two people.
     * Eliminate one of them.
     *
     * Verify the final candidate.
     *
     * Time Complexity:
     * O(N)
     *
     * Space Complexity:
     * O(N)
     */

    public static int celebrityOptimalII(int[][] mat) {

        int n = mat.length;

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++)
            stack.push(i);

        while (stack.size() > 1) {

            int a = stack.pop();
            int b = stack.pop();

            if (mat[a][b] == 1)
                stack.push(b);
            else
                stack.push(a);
        }

        int candidate = stack.pop();

        for (int i = 0; i < n; i++) {

            if (i == candidate)
                continue;

            if (mat[candidate][i] == 1)
                return -1;

            if (mat[i][candidate] == 0)
                return -1;
        }

        return candidate;
    }
}
