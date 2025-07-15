package Problems.Arrays.Hard;

public class TrappingRainwater {
    // Problem: https://leetcode.com/problems/trapping-rain-water/

    // Brute Force: O(n^2) time | O(1) space
    public static int trapBrute(int[] height) {
        int n = height.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int leftMax = 0, rightMax = 0;
            for (int j = 0; j <= i; j++) leftMax = Math.max(leftMax, height[j]);
            for (int j = i; j < n; j++) rightMax = Math.max(rightMax, height[j]);
            ans += Math.min(leftMax, rightMax) - height[i];
        }
        return ans;
    }

    // Better Solution: O(n) time | O(n) space
    // Using Prefix Sum
    public static int trap(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // Store leftMax for each index
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) leftMax[i] = Math.max(leftMax[i - 1], height[i]);

        // Store rightMax for each index
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) rightMax[i] = Math.max(rightMax[i + 1], height[i]);

        int ans = 0;
        for (int i = 0; i < n; i++) ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        return ans;
    }

    // Optimal Solution: O(n) time | O(1) space
    // Using Two Pointers
    public static int trapOptimal(int[] height) {
        int n = height.length;
        int left = 0, right = n - 1, leftMax = 0, rightMax = 0, ans = 0;
        while (left <= right) {
            if (height[left] < height[right]) {
                if (height[left] > leftMax) leftMax = height[left];  // update leftMax
                else ans += leftMax - height[left];  // trapped water at left
                left++;
            } else {
                if (height[right] > rightMax) rightMax = height[right];  // update rightMax
                else ans += rightMax - height[right]; // trapped water at right
                right--;
            }
        }
        return ans;
    }
}
