package Problems.StacksAndQueue.Hard;

import java.util.Stack;

public class LargestRectangle {
    // Problem: https://leetcode.com/problems/maximal-rectangle/description/

    // This problem is similar to LargestRectangleInHistogram problem, just we have to see through the problem carefully,
    // for each row we can create the histograms by calulcating prefixsum of each row.
    // then for each row in this prefix sum, we calculate largest area using histogram problem

    public static int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] prefixSum = new int[m][n];
        // iterating columnwise and adding prefix sum of 1s
        for(int i = 0; i < n; i++){
            int sum = 0;
            for(int j = 0; j < m; j++){
                sum += matrix[j][i] - '0';
                if(matrix[j][i] == '0')
                    sum = 0;
                prefixSum[j][i] = sum;
            }
        }

        int maxArea = 0;
        // Now for each row we calculated prefix sums of 1, this now makes the problem largest histogram
        for(int i = 0; i < m; i++){
            maxArea = Math.max(maxArea, largestHistogram(prefixSum[i]));
        }

        return maxArea;
    }

    public static int largestHistogram(int[] arr){
        int n = arr.length;
        int maxA = 0;
        Stack<Integer> stack = new Stack<>();
        for(int i =0; i<= n; i++){
            int current = (i == n) ? 0 : arr[i];
            while(!stack.isEmpty() && arr[stack.peek()] > current){
                int height = arr[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxA = Math.max(maxA, height * width);
            }

            stack.push(i);
        }

        return maxA;
    }

    public static void main(String[] args) {
        char[][] matrix = {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
        System.out.println(maximalRectangle(matrix));
    }
}
