package Problems.StacksAndQueue.Hard;

import java.util.Stack;

public class LargestRectangleHistogram {
    // Problem: https://leetcode.com/problems/largest-rectangle-in-histogram/

    // Brute Force: At each index, we expand left and right till we get a height >= current height and then calculate the area at that index
    // Time Complexity: O(n^2) Space Complexity: O(1)
    public static int largestRectangleAreaBrute(int[] heights) {
        int maxArea = 0;
        int n = heights.length;

        for(int i = 0; i < n; i++){
            int height = heights[i];
            int left = i;
            // see left where are can find greater height
            while(left > 0 && heights[left - 1] >= heights[i])
                left--;

            int right = i;
            while(right < n - 1 && heights[right + 1] >= heights[i])
                right++;

            int width = right - left + 1;
            int area = height * width;
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    // Using Monotonic Stack
    // Time Complexity: O(n) Space Complexity: O(n)
    // Here we will use NSE and PSE to get the index of heights and then calculate the area at each index
    public static int largestRectangleAreaBetter(int[] heights) {
        int n = heights.length;
        int[] nse = new int[n];  // Next Smaller Element index
        int[] pse = new int[n];  // Previous Smaller Element index

        Stack<Integer> stack = new Stack<>();

        // 1. Compute NSE (Next Smaller Element)
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            nse[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        stack.clear();

        // 2. Compute PSE (Previous Smaller Element)
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            pse[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        // 3. Calculate max area
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int width = nse[i] - pse[i] - 1;
            int area = heights[i] * width;
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    // Optimzed Solution
    // Using single pass
    // Time Complexity: O(n) Space Complexity: O(n)
    public static int largestRectangleAreaOptimized(int[] heights) {
        int n = heights.length;
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int nse, pse;
        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                nse = i;
                pse = stack.isEmpty() ? -1 : stack.peek();
                int width = nse - pse - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }


        return maxArea;
    }

    public static void main(String[] args) {
        int[] heights = {2,1,5,6,2,3};
        System.out.println(largestRectangleAreaBrute(heights)); // 10
        System.out.println(largestRectangleAreaBetter(heights)); // 10
    }

}
